package com.willy.malltest.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class EmailController {
    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    private final EmailService emailService;
    // 使用 ConcurrentHashMap 作為驗證碼的臨時儲存
    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/register")
    public Result register(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        try {
            logger.info("Received email: {}", email);

            // 生成驗證碼
            String code = Utils.generateVerificationCode();
            logger.info("Generated code: {}", code);
            // 將驗證碼儲存到內存中
            verificationCodes.put(email, code);

            executor.submit(() -> {
                try {
                    emailService.sendSimpleMessage(email, "驗證碼", "您的驗證碼是：" + code);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });

            // 發送註冊郵件
//            emailService.sendSimpleMessage(email, "驗證碼", "您的驗證碼是：" + code);
            logger.info("Verification code sent to: {}", email);

            return new Result(Result.SUCCESS, "驗證碼已發送至您的信箱");
        } catch (Exception e) {
            logger.error("Failed to send email to: {} with error:", email, e);
            return new Result(Result.ERROR, "發送驗證碼失敗");
        }
    }

    @PostMapping("/verifyCode")
    public Result verifyCode(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String userInputCode = requestBody.get("code");
        String storedCode = verificationCodes.get(email);
        if(storedCode != null && storedCode.equals(userInputCode)) {
            // 驗證碼比對正確，進行後續的業務處理
            logger.info("Verification code matched for email: {}", email);
            // 驗證成功後，可以從儲存中移除驗證碼
            verificationCodes.remove(email);
            return new Result(Result.SUCCESS, "驗證碼驗證成功");
        } else {
            // 驗證碼不匹配或已過期
            logger.info("Verification code mismatched or expired for email: {}", email);
            return new Result(Result.ERROR, "驗證碼驗證失敗");
        }
    }
}
