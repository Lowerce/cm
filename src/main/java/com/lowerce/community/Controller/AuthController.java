package com.lowerce.community.Controller;

import com.lowerce.community.dto.AccessTokenDto;
import com.lowerce.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    private GithubProvider githubProvider;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id("ea245b7e824356a936a2");
        accessTokenDto.setClient_secret("3fe2c81f66f6d51fd476fb3b5305e9e9044acf61");
        accessTokenDto.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDto.setCode(code);
        String token=githubProvider.getAccessToken(accessTokenDto);
        githubProvider.getUser(token);
        return "index";
    }
}
