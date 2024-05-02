package com.willy.malltest.config;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
@Configuration
@PropertySource("line-oauth2.properties")  // 把該檔案放到 resourse 底下
@Data
public class LineOauthConfig {
    @Value("${line_client_id}")
    private String clientId;

    @Value("${line_client_secret}")
    private String clientSecret;

    @Value("${line_redirect_uris}")
    private String redirectUri;
}
