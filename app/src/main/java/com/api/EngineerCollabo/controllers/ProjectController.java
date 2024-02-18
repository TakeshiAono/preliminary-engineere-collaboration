package com.api.EngineerCollabo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class ProjectController {


    @Autowired
    ProjectRepository projectRepository;

    @GetMapping
    @RequestMapping("/{id}")
    public Project responseProject(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            Project project = projectRepository.findById(id);
            return project;
        } else {
            return null;
        }
    }

    @PatchMapping("/{id}")
    public void putProject(@PathVariable("id") Optional<Integer> ID, @RequestBody Project requestProject) {
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
            projectRepository.save(project);
        }
    }

    // /**
    //  * ログインAPI
    //  * POST /user/login
    //  * 
    //  * @param requestLogin ログインAPIのリクエストボディ
    //  * @return responseLogin ログインAPIのレスポンスボディ
    //  */
    // @PostMapping("/login")
    // public ResponseLogin login(@RequestBody RequestLogin requestLogin) {

    //     // サービスクラスのログイン処理呼び出し
    //     ResponseLogin responseLogin = userService.login(requestLogin);

    //     // APIレスポンス
    //     return responseLogin;
    // }
}
