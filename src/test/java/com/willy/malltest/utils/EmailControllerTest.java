package com.willy.malltest.utils;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

@WebMvcTest(EmailController.class)
public class EmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService emailService;

    @InjectMocks
    private EmailController emailController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        // 假定發送郵件的行為沒有拋出異常
        doNothing().when(emailService).sendSimpleMessage(anyString(), anyString(), anyString());
    }

    @Test
    public void register_ShouldSendVerificationCode() throws Exception {
        // 創建一個請求主體
        String email = "test@example.com";
        String requestBody = objectMapper.writeValueAsString(Map.of("email", email));

        // 執行POST請求並驗證結果
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

        // 驗證是否調用了EmailService的sendSimpleMessage方法
        verify(emailService).sendSimpleMessage(anyString(), anyString(), anyString());
    }
}
