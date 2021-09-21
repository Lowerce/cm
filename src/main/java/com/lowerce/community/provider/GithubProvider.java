package com.lowerce.community.provider;

import com.alibaba.fastjson.JSON;
import com.lowerce.community.dto.AccessTokenDto;
import com.lowerce.community.dto.UserDto;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDto accessTokenDto){
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {

            String string = response.body().string();
            String[] split= string.split("&");
            String token= split[0].split("=")[1];
            System.out.println(token);

            return token;
        } catch (IOException e) {

        }
        return null;
    }
    public UserDto getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token "+accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            UserDto userDto = JSON.parseObject(string, UserDto.class);
            System.out.println(userDto.getId());
            return  userDto;
        } catch (IOException e) {
        }
        return null;
    }

}
