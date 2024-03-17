package com.example.controller;

import com.example.service.TokenService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class AppController {

    private static final String INFO_PAGE = "info_page";
    private static final String ABOUT_PAGE = "about_page";
    private static final String LOGIN_PAGE = "login_page";
    private static final String ADMIN_PAGE = "admin_page";
    private static final String TOKEN_URL_PAGE = "secret_url_page";
    private static final String SECRET_TOKEN_ATTR = "secretToken";

    UserService userService;

    TokenService tokenService;

    @Autowired
    public AppController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @GetMapping(value = "/info")
    public String infoPage() {
        return INFO_PAGE;
    }

    @GetMapping(value = "/about")
    public String aboutPage() {
        return ABOUT_PAGE;
    }

    @GetMapping(value = "/admin")
    public String adminPage() {
        return ADMIN_PAGE;
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return LOGIN_PAGE;
    }


    @GetMapping("/secret")
    public ModelAndView secret() {
        return new ModelAndView("secretToken");
    }

    @GetMapping("/secret/url")
    public ModelAndView secretUrl(String token, ModelMap map) {
        map.addAttribute(SECRET_TOKEN_ATTR, "localhost:8080\\secret\\" + tokenService.save(token));
        return new ModelAndView(TOKEN_URL_PAGE, map);
    }


    @GetMapping("/secret/{url}")
    public ModelAndView getSecret(@PathVariable String url) {
        String secret = tokenService.getByUrl(url);
        if (secret == null) {
            return new ModelAndView("incorrect_url");
        }
        ModelMap map = new ModelMap();
        map.addAttribute("secret", secret);
        return new ModelAndView("getSecret", map);
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

    @GetMapping("/getAllBlockedUser")
    public ModelAndView getAllBlockedUser() {
        List<String> blockedUsers = userService.getAllLockedAccountsEmail();
        ModelMap map = new ModelMap();
        map.addAttribute("blockedUsers", String.join(" - ", blockedUsers));
        return new ModelAndView("blocked_users", map);
    }

}
