package lin.community.communtiy.controller;

import lin.community.communtiy.mapper.UserMapper;
import lin.community.communtiy.model.User;
import lin.community.communtiy.model.UserExample;
import lin.community.communtiy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login2")
    public String login2(HttpServletResponse servletResponse, @RequestParam("name") String name, @RequestParam("passWord") String passWord){
       if (""!=name) {
           UserExample userExample = new UserExample();
           userExample.createCriteria()
                   .andNameEqualTo(name);
           List<User> users = userMapper.selectByExample(userExample);

           if (users.size() != 0) {
               for (User user : users) {
                   String pw = user.getPassword();
//                   if (passWord == pw) {
                       String token = user.getToken();
                       servletResponse.addCookie(new Cookie("token", token));
                       return "redirect:/";
//                   }
               }
           }
       }
        System.out.println("登陆出错");
        return "redirect:/";

    }
}
