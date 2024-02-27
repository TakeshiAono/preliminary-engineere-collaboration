package com.api.EngineerCollabo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.api.EngineerCollabo.services.UserService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.User;
import com.api.EngineerCollabo.repositories.ProjectRepository;
import com.api.EngineerCollabo.repositories.UserRepository;
import com.api.EngineerCollabo.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("routing")
public class RoutingController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    // routing/index の処理(GET)
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String indexGet(Model model) {
        // springSecurityの設定でログイン前に/routing/indexに飛ぶと、2回下記のコードが実行され、2レコード保存されるが問題ないので気にしないで欲しい
        Project project = projectRepository.findById(1);
        User user_test = new User();
        user_test.setName("testUser");
        user_test.setEmail("testemail");
        user_test.setPassword("testpassword3");

        // user_test.setProject(project);
        userRepository.save(user_test);

        return "indexc";
    }
}
