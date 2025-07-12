package com.aiary.be.admin.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class adminController {
    @GetMapping
    public String adminLoginPage() {
        return "login.html";
    }
    
    @GetMapping("/main")
    public String adminPage() {
        return "main.html";
    }
    
    @GetMapping("/user")
    public String adminUserPage() {
        return "user.html";
    }
    
    @GetMapping("/mission")
    public String adminMissionPage() {
        return "mission.html";
    }
}
