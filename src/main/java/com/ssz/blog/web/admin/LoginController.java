package com.ssz.blog.web.admin;

import com.ssz.blog.pojo.User;
import com.ssz.blog.service.UserService;
import com.ssz.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author sushuaizhen
 * @date 2020/7/25
 */
@Controller
@RequestMapping("/admin")
/*@RequestMapping(value="admin", method= RequestMethod.GET)*/
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username, password);
        if(user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "redirect:/admin/index";
        }else{
            attributes.addFlashAttribute("message","用户名和密码错误");
            return"redirect:/admin";
        }
    }

    @RequestMapping("/index")
    public String index(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "admin/index";
    }


        @GetMapping("/logout")
        public String logout(HttpSession session){
            session.removeAttribute("user");
            return "redirect:/admin";
}

        //登录页面跳转到注册页面
        @GetMapping("/register")
        public String register(Model model){
        model.addAttribute("user", new User());
        return "admin/register";
        }

        //在注册页面填写信息后进行注册
        @PostMapping("/register_in")
        public String register_in(@Valid User user, BindingResult result, RedirectAttributes redirectAttributes){
        User user1 = userService.getUserByName(user.getUsername());
        if(user1 != null){
            redirectAttributes.addFlashAttribute("message","用户名重复");
            result.rejectValue("username", "nameError", "用户名重复");
            return "admin/register";
        }
        if(result.hasErrors()){
            return "admin/register";
        }
        //将密码加密
        user.setPassword(MD5Utils.code(user.getPassword()));
        user.setAvatar("/images/p1.jpg");
        user.setNickname(user.getUsername());
        User user2 = userService.saveUser(user);
        if(user2 != null){
            redirectAttributes.addFlashAttribute("message", "注册成功,请登录");
            return "admin/login";
        }else
            redirectAttributes.addFlashAttribute("message", "注册失败，请重试");
        return "redirect:/admin/rigister_in";
        }
}
