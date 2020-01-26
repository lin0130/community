package lin.community.communtiy.controller;

import lin.community.communtiy.dto.PaginationDTO;
import lin.community.communtiy.dto.QuestionDTO;
import lin.community.communtiy.mapper.UserMapper;
import lin.community.communtiy.model.User;
import lin.community.communtiy.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {
        Cookie[] cookies = servletRequest.getCookies();
        if (cookies != null && cookies.length != 0)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        servletRequest.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        PaginationDTO paginationDTO = questionService.list(page,size);
        model.addAttribute("paginationDTO", paginationDTO);
        return "index";
    }
}
