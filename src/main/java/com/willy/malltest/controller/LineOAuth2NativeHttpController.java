package com.willy.malltest.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.willy.malltest.config.LineOauthConfig;
import com.willy.malltest.dto.UserDto;
import com.willy.malltest.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Controller
public class LineOAuth2NativeHttpController {

    @Autowired
    private UserService userService;

    @Autowired
    private LineOauthConfig lineOauthConfig;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/line-login")
    public String lineLogin(HttpServletResponse response) {

        // LINE 登入的授權端點
        String authUrl = "https://access.line.me/oauth2/v2.1/authorize?" +
                "response_type=code" +
                "&client_id=" + lineOauthConfig.getClientId() +
                "&redirect_uri=" + lineOauthConfig.getRedirectUri() +
                "&state=login" +
                "&scope=openid%20profile%20email";

        return "redirect:" + authUrl;
    }

    @GetMapping("/line-callback")
    public String lineCallback(@RequestParam(required = false) String code, HttpSession httpSession)
            throws IOException {
        if (code == null) {
            String authUri = "https://access.line.me/oauth2/v2.1/authorize?" +
                    "response_type=code" +
                    "&client_id=" + lineOauthConfig.getClientId() +
                    "&redirect_uri=" + lineOauthConfig.getRedirectUri() +
                    "&state=login" +
                    "&scope=openid%20profile%20email";
            return "redirect:" + authUri;
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>(); // key 可能重複再用
            map.add("grant_type", "authorization_code");
            map.add("code", code);
            map.add("redirect_uri", lineOauthConfig.getRedirectUri());
            map.add("client_id", lineOauthConfig.getClientId());
            map.add("client_secret", lineOauthConfig.getClientSecret());

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

            ResponseEntity<String> response = restTemplate.postForEntity("https://api.line.me/oauth2/v2.1/token", request,
                    String.class);
            String credentials = response.getBody();

            JsonNode jsonNode = new ObjectMapper().readTree(credentials);
            String accessToken = jsonNode.get("access_token").asText();
            String idToken = jsonNode.get("id_token").asText();



            HttpHeaders headers2 = new HttpHeaders();
            headers2.setBearerAuth(accessToken);

            HttpEntity<String> entity = new HttpEntity<>(headers2);
            ResponseEntity<String> response2 = restTemplate.exchange(
                    "https://api.line.me/v2/profile",
                    HttpMethod.GET,
                    entity,
                    String.class);

            String profileResponse = response2.getBody();

            JsonNode profileJsonNode = new ObjectMapper().readTree(profileResponse);

            // Extract data from profileJsonNode and process it

            System.out.println(profileJsonNode.get("userId"));
            System.out.println(profileJsonNode.get("displayName"));

            String payloadLineId = profileJsonNode.get("userId").asText();
            String payloadName = profileJsonNode.get("displayName").asText();
            String payloadEmail;

            String url = "https://api.line.me/oauth2/v2.1/verify";

            // 建立 URL 物件
            URL requestUrl = new URL(url);

            // 建立 HttpURLConnection 物件
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();

            // 設置請求方法為 POST
            connection.setRequestMethod("POST");

            // 啟用輸出流以寫入請求參數
            connection.setDoOutput(true);

            // 設置請求參數
            String postData = "id_token=" + idToken + "&client_id=" + lineOauthConfig.getClientId();
            byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);

            // 寫入請求參數到輸出流
            try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                outputStream.write(postDataBytes);
            }

            // 讀取服務器響應
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response3 = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response3.append(line);
                }
                // 輸出響應
                System.out.println(response3);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode2 = objectMapper.readTree(String.valueOf(response3));

                payloadEmail = jsonNode2.get("email").asText();
            }

            // 關閉連接
            connection.disconnect();

            if(payloadEmail!=null)
            {
                UserDto loggedInUser;
                // 將使用者放到資料庫
                boolean isRigister = userService.checkIfUsernameExist(payloadEmail);

                if(isRigister){
                    loggedInUser = userService.loginOAuth2(payloadEmail);
                }else{
                    loggedInUser = userService.addUsersOAuth2(payloadLineId,payloadName,payloadEmail,"Line");
                }

                // 將 USER 放到 SESSION 內
                httpSession.setAttribute("loggedInUser", loggedInUser);
            }else{
                return "redirect:http://localhost:5173/";
            }

        }

        // Redirect to success page
        return "redirect:http://localhost:5173/OLoginSuccess"; // Update the redirect URL as needed
    }



}
