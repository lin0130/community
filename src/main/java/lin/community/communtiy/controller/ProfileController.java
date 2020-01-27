package lin.community.communtiy.controller;

import lin.community.communtiy.dto.PaginationDTO;
import lin.community.communtiy.mapper.UserMapper;
import lin.community.communtiy.model.User;
import lin.community.communtiy.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest servletRequest,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size)
    {
        User user = null;
        Cookie[] cookies = servletRequest.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (user != null) {
                        servletRequest.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        if (user==null)
        {
            return "redirect:/";
        }
        if ("questions".equals(action))
        {
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");
        }else  if ("replies".equals(action))
        {
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
        model.addAttribute("paginationDTO",paginationDTO);
        return "profile";
    }

}