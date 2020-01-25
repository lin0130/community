package lin.community.communtiy.controller;

import lin.community.communtiy.dto.QuestionDto;
import lin.community.communtiy.mapper.QuestionMapper;
import lin.community.communtiy.mapper.UserMapper;
import lin.community.communtiy.model.Question;
import lin.community.communtiy.model.User;
import lin.community.communtiy.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/")
    public String index(HttpServletRequest servletRequest,
    Model model)
    {
        Cookie[] cookies = servletRequest.getCookies();
        if (cookies!=null && cookies.length!=0)
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
        List<QuestionDto> questionList = questionService.list();
        model.addAttribute("questions",questionList);
        return "index";
    }
}
