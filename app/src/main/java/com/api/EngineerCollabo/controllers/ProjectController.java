package com.api.EngineerCollabo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
