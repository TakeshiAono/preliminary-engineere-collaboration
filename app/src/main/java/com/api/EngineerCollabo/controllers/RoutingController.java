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
        User user_test = new User();
        user_test.setName("testUser");
        userRepository.save(user_test);
        Project project = projectRepository.findById(18);
        return "indexc";
    }
}
