package lin.community.communtiy.controller;

import lin.community.communtiy.model.User;
import lin.community.communtiy.service.UserService;
import lin.community.communtiy.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;
import java.util.UUID;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/regist")
    public String login2(){

        return "regist";
    }

    @PostMapping("/regist2")
    public String regist(@RequestParam("name") String name,
                         @RequestParam("passWord") String passWord,
                         HttpServletResponse servletResponse){

        User user = new User();
        user.setName(name);
        user.setPassWord(passWord);
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user.setAvatarUrl("https://avatars0.githubusercontent.com/u/59856442?v=4");
        user.setAccountId(String.valueOf(returnRandomConfNumber(8)));
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        userService.CreateOrUpdate(user);
        servletResponse.addCookie(new Cookie("token", token));
        return "redirect:/";
    }

    String returnRandomConfNumber(int length)
    {
        Random random = new Random();
        return String.valueOf(random.nextLong()).substring(1, length + 1);
    }
}
