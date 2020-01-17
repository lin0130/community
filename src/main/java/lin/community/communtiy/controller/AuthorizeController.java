package lin.community.communtiy.controller;

import com.alibaba.fastjson.JSON;
import lin.community.communtiy.dto.AccessTokenDto;
import lin.community.communtiy.dto.GithubUser;
import lin.community.communtiy.provider.GithubProvider;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state)
    {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id("1aedee186d31295e9563");
        accessTokenDto.setRedirect_uri("http://localhost:8082/callback");
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setClient_secret("aed100222119bbf6c545728e2fe9e7646640fc82");
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user);

        return "index";
    }

}
