package lin.community.communtiy.controller;

import lin.community.communtiy.mapper.UserMapper;
import lin.community.communtiy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/")
    public String index(HttpServletRequest servletRequest)
    {
        Cookie[] cookies = servletRequest.getCookies();
        if (cookies!=null)
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token"))
            {
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                if (user!=null)
                {
                    servletRequest.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        return "index";
    }
}
