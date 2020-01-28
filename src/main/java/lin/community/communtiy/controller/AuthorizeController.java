package lin.community.communtiy.controller;

import lin.community.communtiy.dto.AccessTokenDTO;
import lin.community.communtiy.dto.GithubUser;
import lin.community.communtiy.model.User;
import lin.community.communtiy.provider.GithubProvider;
import lin.community.communtiy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private UserService userService;
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.clien.id}")
    private String client_id;
    @Value("${github.redirect.uri}")
    private String Redirect_uri;
    @Value("${github.client.secret}")
    private String client_secret;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest servletRequest,
                           HttpServletResponse servletResponse) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("1aedee186d31295e9563");
        accessTokenDTO.setRedirect_uri("http://localhost:8082/callback");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_secret("aed100222119bbf6c545728e2fe9e7646640fc82");
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);

        if (githubUser != null && githubUser.getId() != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarurl(githubUser.getAvatarUrl());
            userService.CreateOrUpdate(user);
            servletResponse.addCookie(new Cookie("token", token));

            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest servletRequest,
                         HttpServletResponse servletResponse) {
        servletRequest.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        servletResponse.addCookie(cookie);
        return "redirect:/";
    }

}
