package com.api.EngineerCollabo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
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

    // routing/index の処理(GET)
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String indexGet(Model model) {
        System.out.println("Hello");
        System.out.println("Im fine");
        Project project = new Project();
        project.setName("testUser2aaaaa");
        project.setIconUrl("testURL2aaaa");
        project.setDescription("testDescriptioaaaaaan");
        User user_aono = new User();
        user_aono.setName("aono");
        user_aono.setProject(project);
        List<User> userList = new ArrayList();
        userList.add(user_aono);
        project.setUsers(userList);

        Project savedProject = projectRepository.save(project);
        List<User> users = savedProject.getUsers();
        User user = users.get(0);
        System.out.println("テスト");
        System.out.println(users.get(0).getId());
        model.addAttribute("test", userRepository.findByName("testUser2").get(1).getId());
        return "indexc";
    }
}
