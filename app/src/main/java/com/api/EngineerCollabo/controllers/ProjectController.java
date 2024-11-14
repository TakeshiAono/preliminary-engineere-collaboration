package com.api.EngineerCollabo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.EngineerCollabo.entities.Directory;
import com.api.EngineerCollabo.entities.File;
import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.ResponseDirectory;
import com.api.EngineerCollabo.entities.ResponseProject;
import com.api.EngineerCollabo.repositories.ProjectRepository;
import com.api.EngineerCollabo.repositories.UserRepository;
import com.api.EngineerCollabo.services.ProjectService;
import com.api.EngineerCollabo.util.HashUtil;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.List;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.util.Map;
import java.time.Duration;

import com.fasterxml.jackson.databind.JsonNode;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.PutBucketCorsRequest;
import software.amazon.awssdk.services.s3.model.CORSRule;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "http://localhost:5173")
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectService projectService;

    @Autowired
    UserRepository userRepository;

    // @Autowired
    // MemberRepository memberRepository;
    @GetMapping("/{id}")
    public ResponseProject responseProject(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            Project project = projectRepository.findById(id);
            return projectService.changeResponseProject(project);
        } else {
            return null;
        }
    }

    @GetMapping
    public List<ResponseProject> responseProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream().map((project) -> projectService.changeResponseProject(project)).toList();
    }


    @PatchMapping("/{id}")
    public void putProject(@PathVariable("id") Optional<Integer> ID, @RequestBody Project requestProject) {
        // TODO: サービスクラスに以下のロジックを移設したい。
        if (ID.isPresent()) {
            int id = ID.get();
            Project project = projectRepository.findById(id);

            String name = requestProject.getName();
            if (name != null) {
                project.setName(name);
            }

            String iconUrl = requestProject.getIconUrl();
            if (iconUrl != null) {
                project.setIconUrl(iconUrl);
            }

            String description = requestProject.getDescription();
            if (description != null) {
                project.setDescription(description);
            }

            Date deadline = requestProject.getDeadline();
            if (deadline != null) {
                project.setDeadline(deadline);
            }

            JsonNode recruitingMemberJob = requestProject.getRecruitingMemberJob();
            if (recruitingMemberJob != null) {
                project.setRecruitingMemberJob(recruitingMemberJob);
            }

            JsonNode useTechnology = requestProject.getUseTechnology();
            if (useTechnology != null) {
                project.setUseTechnology(useTechnology);
            }

            String recruitingText = requestProject.getRecruitingText();
            if (recruitingText != null) {
                project.setRecruitingText(recruitingText);
            }

            projectRepository.save(project);
        }
    }

    @GetMapping("/{id}/files")
    public List<String> getFiles(@PathVariable("id") Optional<Integer> ID) {
        if (!ID.isPresent()) {
            System.err.println("IDが存在しません");
            return new ArrayList<>();  // 空のリストを返す
        }

        try (S3Client s3Client = S3Client.builder().region(Region.US_EAST_1).build()) {
            Project project = projectRepository.findById(ID.get())
                .orElseThrow(() -> new RuntimeException("プロジェクトが見つかりませんでした: " + ID.get()));

            // `AtomicReference` を使用して `hashName` の代わりにする
            AtomicReference<String> hashNameRef = new AtomicReference<>("");
            this.createHashName(project.getName()).ifPresentOrElse(
                hashNameRef::set,
                () -> {
                    System.err.println("バケット名の生成に失敗しました。");
                }
            );

            // 生成されたバケット名が空でないことを確認
            String hashName = hashNameRef.get();
            if (hashName.isEmpty()) {
                return new ArrayList<>(); // ハッシュ生成に失敗した場合は空のリストを返す
            }

            // リクエストを作成してオブジェクト一覧を取得
            ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                .bucket(hashName)
                .build();

            ListObjectsV2Response response = s3Client.listObjectsV2(listObjectsRequest);
            
            // オブジェクトのキー（ファイル名）をリストにして返す
            System.out.println(response);
            return response.contents().stream()
                .map(S3Object::key)
                .collect(Collectors.toList());
        } catch (S3Exception e) {
            System.err.println("S3エラー: " + e.awsErrorDetails().errorMessage());
            return new ArrayList<>();  // エラー時は空のリストを返す
        }
    }

    @PostMapping("/{id}/use-share-files/{bucketName}")
    public void createBucket(@PathVariable("id") Optional<Integer> id, @PathVariable("bucketName") String bucketName) {
        Project project = projectRepository.findById(id.get())
                .orElseThrow(() -> new RuntimeException("プロジェクトが見つかりませんでした: " + id.get()));

        try (S3Client s3Client = S3Client.builder().region(Region.US_EAST_1).build()) {
            // `AtomicReference` を使用して、ラムダ式内で `createBucketRequest` を設定可能にする
            AtomicReference<CreateBucketRequest> createBucketRequestRef = new AtomicReference<>();

            this.createHashName(project.getName()).ifPresentOrElse(
                (hashName) -> {
                    System.out.println("バケット名: " + hashName);
                    createBucketRequestRef.set(CreateBucketRequest.builder()
                        .bucket(hashName)
                        .build());
                },
                () -> {
                    System.err.println("バケット名の生成に失敗しました。");
                }
            );

            // バケットの作成リクエストが存在する場合のみバケットを作成
            CreateBucketRequest createBucketRequest = createBucketRequestRef.get();
            if (createBucketRequest != null) {
                CreateBucketResponse createBucketResponse = s3Client.createBucket(createBucketRequest);
                System.out.println("バケットが作成されました: " + createBucketResponse.location());

                // CORSルールを定義
                List<CORSRule> corsRules = Arrays.asList(
                    CORSRule.builder()
                        .allowedHeaders("*")
                        .allowedMethods("GET", "PUT")
                        .allowedOrigins("*")
                        .exposeHeaders() // 必要であればここにヘッダーを追加
                        .build()
                );

                // CORS設定リクエストを作成
                PutBucketCorsRequest putBucketCorsRequest = PutBucketCorsRequest.builder()
                    .bucket(createBucketRequest.bucket())
                    .corsConfiguration(builder -> builder.corsRules(corsRules))
                    .build();

                // バケットにCORS設定を適用
                s3Client.putBucketCors(putBucketCorsRequest);
                System.out.println("CORS設定が適用されました。");
            } else {
                System.err.println("バケット作成に必要な情報が不足しています。");
            }
        } catch (S3Exception e) {
            System.err.println("バケット作成エラー: " + e.awsErrorDetails().errorMessage());
        }
    }

    @GetMapping("/{id}/use-share-files")
    public boolean isCreatedBucket(@PathVariable("id") Optional<Integer> id) {
        S3Client s3Client = S3Client.builder().region(Region.US_EAST_1).build();
        
        Project project = projectRepository.findById(id.get())
            .orElseThrow(() -> new RuntimeException("プロジェクトが見つかりませんでした: " + id.get()));
    
        // `AtomicReference` を使用して `encryptResult` の代わりにする
        AtomicReference<String> encryptResultRef = new AtomicReference<>("");
    
        this.createHashName(project.getName()).ifPresentOrElse(
            (hashName) -> {
                System.out.println("バケット名: " + hashName);
                encryptResultRef.set(hashName);
            },
            () -> {
                System.err.println("バケット名の生成に失敗しました。");
            }
        );
    
        // バケットが存在するか確認
        boolean exists = s3Client.listBuckets().buckets().stream()
                .anyMatch(bucket -> bucket.name().equals(encryptResultRef.get()));
        return exists;
    }

    private Optional<String> createHashName(String name) {
        String hashName;
        try {
            hashName = HashUtil.hashAndAdjustForBucketName(name)
                .toLowerCase()
                .replace("/", "-")  // スラッシュをハイフンに置き換え
                .replace("+", "a")  // プラスを別の文字（例: "a"）に置き換え
                .replace("=", "");  // イコールを削除（Base64の末尾によくある）
        } catch (NoSuchAlgorithmException e) {
            System.err.println("ハッシュ生成中にエラーが発生しました: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();  // ハッシュ生成に失敗した場合、存在しないと判断
        }
        return Optional.of(hashName);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            projectRepository.deleteById(id);
        }
    }

    @PostMapping("/create")
    public void createProject(@RequestBody Project requestProject) {
        projectRepository.save(requestProject);
    }

    @GetMapping("/search")
    public List<ResponseProject> searchProject(
        @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
        @RequestParam(value = "selectedSkills", required = false, defaultValue = "") String selectedSkills,

        @RequestParam(value = "fromDate", required = false, defaultValue = "2000-01-01") String fromDate,
        @RequestParam(value = "toDate", required = false, defaultValue = "2200-01-01") String toDate,
        @RequestParam(value = "projectMemberCount", required = false, defaultValue = "0") String projectMemberCount,
        @RequestParam(value = "selectedMeetingFrequency", required = false, defaultValue = "") String selectedMeetingFrequency,
        @RequestParam(value = "selectedSkills", required = false, defaultValue = "") String technology
    ) {
        List<String> selectedSkillsArrayList = Arrays.asList(selectedSkills.split(","));
        List<Project> projectList = null;
        List<ResponseProject> responseProjectList = new ArrayList<>();

        SimpleDateFormat fromDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat toDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = fromDateFormatter.parse(fromDate);
            Date endDate = toDateFormatter.parse(toDate);
            List<Project> results = projectRepository.findProjectSomeParams(keyword, startDate, endDate, Integer.parseInt(projectMemberCount));
            projectList = results.stream()
                .filter(record -> selectedSkillsArrayList.stream().allMatch(skill -> record.getUseTechnology().toString().contains(skill)))
                .filter(record -> {
                    if(selectedMeetingFrequency.equals("")) {return true;}
                    return record.getMeetingFrequencyCode().equals(selectedMeetingFrequency);})
                .collect(Collectors.toList());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (Project project : projectList) {
            responseProjectList.add(projectService.changeResponseProject(project));
        }

        return responseProjectList;
    }

    // TODO: そのファイルをダウンロードできるか資格があるかどうか認可が必ず必要
    // TODO: 必要最低限のポリシーでIAMを設定する
    @GetMapping("{id}/files/{fileName}/upload-signature-url")
    public String responseUploadSignatureUrl(
        @PathVariable("id")
        Optional<Integer> id,
        @PathVariable("fileName")
        String fileName,
        @RequestParam
        String fileType,
        @RequestParam
        String directoryName
    ) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", fileType);

        String joinFileName;
        if(directoryName.equals("undefined")) {
            joinFileName = fileName;
        } else {
            joinFileName = directoryName + "/" + fileName;
        }

        Project project = projectRepository.findById(id.get())
            .orElseThrow(() -> new RuntimeException("プロジェクトが見つかりませんでした: " + id.get()));

        System.out.println("かくにん");
        System.out.println(this.createHashName(project.getName()).get());
        return this.createPresignedUrl(this.createHashName(project.getName()).get(), joinFileName, metadata);
    }

    public String createPresignedUrl(String bucketName, String keyName, Map<String, String> metadata) {
        try (S3Presigner presigner = S3Presigner.create()) {
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .contentType(metadata.get("Content-Type"))
                    // .metadata(metadata)
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))  // The URL expires in 10 minutes.
                    .putObjectRequest(objectRequest)
                    .build();

            PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
            String myURL = presignedRequest.url().toString();

            return presignedRequest.url().toExternalForm();
        }
    }

    // @PostMapping("/{id}/members/add")
    // public void addMember(@PathVariable("id") Optional<Integer> ID, @RequestBody RequestMembers requestMembers) {
    //     if (ID.isPresent()) {
    //         int id = ID.get();
    //         Project project = projectRepository.findById(id);
    //         List<Integer> userIds = requestMembers.getUserIds();
    //         List<Member> joinedMembers = memberRepository.findByProjectId(id);

    //         // TODD: 以下の処理をserviceに移設しトランザクション化する。userがnullの場合にエラーをthrowしrollbackするようにする。
    //         for(Integer userId : userIds) {
    //             Member member = new Member();
    //             Optional<User> optionalUser = userRepository.findById(userId);
    //             if(!optionalUser.isPresent()) {return;}
    //             boolean isUserExist = joinedMembers.stream().anyMatch((memberItem) -> memberItem.getUser().getId() == userId);
    //             if(!isUserExist) {
    //                 member.setProject(project);
    //                 member.setUser(optionalUser.get());
    //                 memberRepository.save(member);
    //             }
    //         }
    //     }
    // }

    // @PatchMapping("/{id}/members/modify")
    // public void modifyMember(@PathVariable("id") Optional<Integer> ID, @RequestBody RequestMembers requestMembers) {
    //     // if(requestMembers.getUserIds().isEmpty()) {throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userIdsが空です。");}
    //     if (ID.isPresent()) {
    //         int id = ID.get();
    //         List<Integer> userIds = requestMembers.getUserIds();
    //         for(Integer userId : userIds) {
    //             Optional<User> optionalUser = userRepository.findById(userId);
    //             if(!optionalUser.isPresent()) {throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "存在しないユーザーをメンバーにはできません。");}
    //         }
    //         Project project = projectRepository.findById(id);
    //         List<Member> joinedMembers = memberRepository.findByProjectId(id);
    //         joinedMembers.stream().forEach(joinedMemberItem -> memberRepository.deleteById(joinedMemberItem.getId()));

    //         // TODD: 以下の処理をserviceに移設しトランザクション化する。userがnullの場合にエラーをthrowしrollbackするようにする。
    //         for(Integer userId : userIds) {
    //             Member member = new Member();
    //             System.out.println(userId);
    //             Optional<User> optionalUser = userRepository.findById(userId);
    //             if(!optionalUser.isPresent()) {return;}
    //             member.setProject(project);
    //             member.setUser(optionalUser.get());
    //             memberRepository.save(member);
    //         }
    //     }
    // }
}
