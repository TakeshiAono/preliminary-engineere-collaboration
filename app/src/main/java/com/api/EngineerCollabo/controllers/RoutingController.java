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

    @Autowired
    private UserService userService;

    // routing/index の処理(GET)
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String indexGet(Model model) {
        System.out.println("Hello");
        System.out.println("Im fine");
        // User user_aono = new User();
        // user.getId();

        // Project project = new Project();
        // project.setName("testUser2aaaaa");
        // project.setIconUrl("testURL2aaaa");
        // project.setDescription("testDescriptioaaaaaan");
        try {
            User user_aono = new User();
            user_aono.setName("aono");
            // UserService userService;
            userService.getScoutedUser(user_aono);
            // User findUser = userRepository.findById(21);
            // Project project1 = userRepository.findProjectById(21);
            // user_aono.setProject(project);
            // List<User> userList = new ArrayList();
            // userList.add(user_aono);
            // ProjectRepository projectRepository = new ProjectRepository();
            // List<Project> users = projectRepository.findByIdAndUsersIsNotNull(18);
            // project.setUsers(userList);
            userRepository.save(user_aono);

            // Project savedProject = projectRepository.save(project);
            // List<User> users = savedProject.getUsers();
            // User user = users.get(0);
            // System.out.println("テスト");
            // System.out.println(users.get(0).getId());
            // model.addAttribute("test", users.get(0).getId());
            Project project = projectRepository.findById(18);
            // List<User> users = userRepository.findByProject(project);
            // model.addAttribute("test", users.get(0).getName());
            // model.addAttribute("test", projectRepository.findByUser(findUser).getName());
            System.out.println("Hello22");
            // System.out.println(project.getName());
            model.addAttribute("test", userRepository.findById(4).getName());

        } catch(Exception e) {
            System.out.println("エラー");
            System.out.println(e);


        }
        return "indexc";
    }
}
