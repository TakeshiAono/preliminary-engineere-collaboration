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
import org.springframework.web.bind.annotation.RestController;


@Controller
public class ProjectManagementController {

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(Model model) {
        // ここで必要なデータを取得し、Modelに追加
        // 例: model.addAttribute("message", "Welcome to the admin dashboard!");

        // テンプレートの名前を返す（Thymeleafではtemplatesディレクトリ以下のHTMLファイルの名前）
        return "admin/dashboard";
    }

    @GetMapping("/admin/projects")
    public String showAdminProjects(Model model) {
        // ここで必要なデータを取得し、Modelに追加
        // 例: model.addAttribute("projects", projectService.getAllProjects());

        // テンプレートの名前を返す
        return "admin/projects";
    }

    // 他にも必要なエンドポイントや機能に応じて追加できます
}