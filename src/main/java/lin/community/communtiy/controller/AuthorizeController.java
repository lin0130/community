package lin.community.communtiy.controller;

import com.alibaba.fastjson.JSON;
import lin.community.communtiy.dto.AccessTokenDto;
import lin.community.communtiy.dto.GithubUser;
import lin.community.communtiy.mapper.UserMapper;
import lin.community.communtiy.model.User;
import lin.community.communtiy.provider.GithubProvider;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.clien.id}")
    private String client_id;
    @Value("${github.redirect.uri}")
    private String Redirect_uri;
    @Value("${github.client.secret}")
    private String client_secret;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest servletRequest)
    {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id("1aedee186d31295e9563");
        accessTokenDto.setRedirect_uri("http://localhost:8082/callback");
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setClient_secret("aed100222119bbf6c545728e2fe9e7646640fc82");
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser githubUser = githubProvider.getUser(accessToken);
//        System.out.println(user);
        if (githubUser != null) {
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            servletRequest.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else {
            return  "redirect:/";
        }
    }

}
