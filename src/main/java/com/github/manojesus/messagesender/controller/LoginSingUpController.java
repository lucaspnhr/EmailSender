package com.github.manojesus.messagesender.controller;

import com.github.manojesus.messagesender.model.User;
import com.github.manojesus.messagesender.service.FolderByUserService;
import com.github.manojesus.messagesender.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.github.manojesus.messagesender.util.constants.AttributesNames.LOGIN_FAIL;
import static com.github.manojesus.messagesender.util.constants.AttributesNames.USER_TO_REGISTER;
import static com.github.manojesus.messagesender.util.constants.UrlNames.*;
import static com.github.manojesus.messagesender.util.constants.ViewNames.LOGIN;
import static com.github.manojesus.messagesender.util.constants.ViewNames.SIGNUP;

@Controller
@AllArgsConstructor
public class LoginSingUpController {

    private final UserService userService;
    private final FolderByUserService folderByUserService;

    @GetMapping(LOGIN_URL)
    public String loginPage(Model model){
        return LOGIN.getName();
    }
    @GetMapping(LOGIN_FAILURE_URL)
    public String failToLogin(Model model){
        model.addAttribute(LOGIN_FAIL, true);
        return LOGIN.getName();
    }
    @GetMapping(SIGNUP_URL)
    public String singUpForm(Model model){
        User userToRegister =User.builder().build();
        model.addAttribute(USER_TO_REGISTER, userToRegister);
        return SIGNUP.getName();
    }
    @PostMapping(SIGNUP_URL)
    public String singUp(@ModelAttribute User userToRegister, Model model){
        model.addAttribute(USER_TO_REGISTER,userToRegister);
        folderByUserService.createDefaultFolders(userToRegister.getEmail());
        userService.signup(userToRegister);
        return "redirect:"+LOGIN_URL;
    }

}
