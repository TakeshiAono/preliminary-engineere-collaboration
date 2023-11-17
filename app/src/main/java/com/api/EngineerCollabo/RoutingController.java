package com.api.EngineerCollabo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;


@Controller
@RequestMapping("routing")
public class RoutingController {

    @Autowired
    private UserRepository userRepository;
    

    // routing/index の処理(GET)
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView indexGet(ModelAndView model) {
        // System.out.println("ID=" + id);
        model.setViewName("indexc");
        System.out.println("Hello");
        System.out.println("Im fine");
        User user = new User();
        user.setName("testUser");
        user.setIconUrl("testURL");

        userRepository.save(user);

        return model;
    }

    // // routing/index の処理(POST)
    // @RequestMapping(value = "/index", method = RequestMethod.POST)
    // public Model indexPost(Model model, @ModelAttribute IndexForm form) {
    //     System.out.println("form.name=" + form.name);

    //     return model;
    // }

    // /**
    //  * POST値保持用のフォームクラス
    //  */
    // public class IndexForm {
    //     public String name ;

    //     public String getName() {
    //         return name;
    //     }

    //     public void setName(String name) {
    //         this.name = name;
    //     }

    // }
}
