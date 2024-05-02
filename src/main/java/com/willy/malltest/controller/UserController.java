package com.willy.malltest.controller;


import com.willy.malltest.dto.UserDto;
import com.willy.malltest.model.User;
import com.willy.malltest.service.MailService;
import com.willy.malltest.service.SessionService;
import com.willy.malltest.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;



@RestController
@CrossOrigin(allowCredentials = "true", origins = { "http://localhost:5173","http://localhost:3000" })
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private SessionService sessionService;

    @PostMapping("/user/login")
    public UserDto login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session) {
        UserDto result = userService.login(email, password);
        if(result != null) {
            userService.updateLastloginTime(result.getUserId());
            session.setAttribute("loggedInUser", result);
            sessionService.saveSession("loggedInUser",result);
        }else {
            throw new RuntimeException("登入失敗，帳號或密碼錯誤");
        }
        return result;
    }

    @GetMapping("/user/logout")
    public boolean logout(HttpSession session) {
        session.invalidate();
        sessionService.deleteSession("loggedInUser");
        return true;
    }

    @PostMapping("/user/register")
    public User register(
            @RequestParam("name") String username,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("password") String password){
        boolean isExist = userService.checkIfUsernameExist(email);
        if(isExist) {
            throw new RuntimeException("此帳號已註冊");
        }else {
            User newUsers = new User();
            newUsers.setUserName(username);
            newUsers.setEmail(email);
            newUsers.setPhone(phone);
            newUsers.setPassword(password);
            newUsers.setAuthentication(2);

            Date today = new Date();
            newUsers.setRegisterDate(today);
            newUsers.setLastLoginTime(today);

            userService.addUsers(newUsers);
            return newUsers;
        }
    }
    @GetMapping("/user/check")
    public UserDto checkLogin(HttpSession session) {
        UserDto loggedInUser = sessionService.getSession("loggedInUser");
        return loggedInUser;
    }

    @GetMapping("/user/getSession")
    public UserDto getSession(HttpSession session) {
        return (UserDto) session.getAttribute("loggedInUser");
    }

    @PostMapping("/user/mail/pwd")
    public void sendPassword(@RequestParam("email") String email,
                             @RequestParam("phone") String phone) {
        mailService.sendPassword(email, phone);
    }

    @PostMapping("/user/mail/verify")
    public void sendVerifyCode(@RequestParam("email") String email,
                               @RequestParam("verificationCode") String verificationCode) {
        mailService.sendVerifyCode(email, verificationCode);
    }
    @PostMapping("/user/registerAdmin")
    public User registerAdmin(@RequestParam("name") String username, @RequestParam("email") String email, @RequestParam("phone") String phone, @RequestParam("password") String password) {
        boolean isExist = userService.checkIfUsernameExist(email);
        if (isExist) {
            throw new RuntimeException("此管理員已存在");
        } else {
            User newUsers = new User();
            newUsers.setUserName(username);
            newUsers.setEmail(email);
            newUsers.setPhone(phone);
            newUsers.setPassword(password);
            newUsers.setAuthentication(0);

            Date today = new Date();
            newUsers.setRegisterDate(today);
            newUsers.setLastLoginTime(today);

            userService.addUsers(newUsers);
            return newUsers;
        }
    }

    @GetMapping("/user/getAllUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/user/banUser")
    public String banUser(@RequestParam("id") Long id) {
        User user= userService.banUser(id);
        return "UserName:"+user.getUserName()+ " success ban!";
    }

    @PutMapping("/user/unbanUser")
    public String unbanUser(@RequestParam("id") Long id) {
        User user=userService.unbanUser(id);
        return "UserName:"+user.getUserName()+ " success restore!";
    }

    @DeleteMapping("/user/deleteUser")
    public String deleteUser(@RequestParam("id") Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/user/findNameById")
    public String findNameById(@RequestParam("id") Long id) {
        return userService.findNameById(id);
    }
}
