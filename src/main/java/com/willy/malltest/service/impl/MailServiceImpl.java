package com.willy.malltest.service.impl;

import com.willy.malltest.model.CustomerFeedback;
import com.willy.malltest.model.User;
import com.willy.malltest.repository.CustomerFeedbackRepository;
import com.willy.malltest.repository.UsersRepository;
import com.willy.malltest.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder pwdEncoder;
    @Autowired
    private CustomerFeedbackRepository customerFeedbackRepository;

    @Override
    public void sendPassword(String email, String phone) {



        User user = usersRepository.findByEmailAndPhone(email, phone);

        if (user==null){
            return;
        }

        //////////////////////////////////////////////

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 10;

        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            // 從字符集合中隨機選擇一個字符，並將其附加到結果字串中
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            stringBuilder.append(randomChar);
        }

        String newPwd = stringBuilder.toString();
        String content = "親愛的用戶您好，您的臨時密碼為： "+newPwd+" ，請盡速登入系統並更改密碼以確保帳戶安全。";
        String subject = "Apple Tree 忘記密碼 - 新密碼通知";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom("Apple Tree<project.3c.mall@gmail.com>");


        mailSender.send(message);
        //////////////////////////////////////////////
        String encodedPwd = pwdEncoder.encode(newPwd); // 加密
        user.setPassword(encodedPwd);
        usersRepository.save(user);

    }

    @Override
    public void sendVerifyCode(String email, String verificationCode) {
        System.out.println("VerificationCode:"+verificationCode);
        String content = "歡迎加入Apple Tree 會員，您的驗證碼為："+verificationCode+"，請於10分鐘內輸入以完成註冊，10分鐘後此驗證碼將會失效！";
        String subject = "Apple Tree 註冊驗證碼";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom("Apple Tree<project.3c.mall@gmail.com>");

        mailSender.send(message);
    }

    @Override
    public void sendFeedbackEmailCreate(Integer feedbackId, String email) {

    }

    @Override
    public void sendFeedbackEmailReturn(Integer feedbackId, String returnMessage) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        CustomerFeedback customerFeedback = customerFeedbackRepository.findByFeedbackID(feedbackId);
        User user = customerFeedback.getUser();
        String email = user.getEmail();
        String userName = user.getUserName();
        Date time = customerFeedback.getFeedbackDate();
        String phone = user.getPhone();
        String description = customerFeedback.getDescription();
        // 指定日期時間格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm EEEE", Locale.TAIWAN);
        // 格式化日期時間
        String formattedDate = sdf.format(time);
        customerFeedback.setCustomerFeedbackStatus("已回覆");
        customerFeedbackRepository.save(customerFeedback);



        // 建立郵件內容
        String htmlContent = "<html><body>" +
                "<p>親愛的 <b>"+userName+"</b> 先生/女士，</p>" +
                "<p>您好！我是樹編，我已收到您的問題反饋，非常感謝您的回報。</p><br/>" +
                "<p>" + returnMessage + "</p>" +
                "<br/>"+
                "<p>如果您對我們的服務有任何疑問或需要進一步的幫助，請隨時聯繫我們的<b>Apple Tree</b>客服團隊，我們將竭誠為您服務。</p>" +
                "<p>如果問題已解決，請至會員中心點擊「<a href=\"http://localhost:5173/MemberCenter/MemberFeedback/\">問題已解決</a>」，謝謝您的配合</p>"+
                "<p><b>Apple Tree</b> 客戶服務中心 敬上</p>" +
                "<br>時間: " +formattedDate+ "</br>" +
                "<br>姓名: " + userName + "</br>" +
                "<br>電話: " + phone + "</br>" +
                "<br>E-mail: " + email + "</br>" +
                "<br>回報問題: " + description + "</br>" +
                "</body></html>";

        // 設置郵件主題
        String subject = "Apple Tree 反饋通知";

        // 設置郵件內容和收件人
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        message.setFrom("Apple Tree<project.3c.mall@gmail.com>");

        // 發送郵件
        try {
            mailSender.send(message);
        } catch (MailException e) {
            // 郵件發送失敗，處理異常情況
            // 這裡可以添加適當的日誌記錄或其他處理邏輯
            e.printStackTrace();
            // 或者您可以向用戶發送錯誤通知，例如：throw new MessagingException("郵件發送失敗");
        }
    }


    @Override
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendHtmlMail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        // 設置HTML格式內容
        String htmlContent = "<html><body><p style=\"font-weight: bold;\">您好，這是一封加粗的郵件內容。</p></body></html>";
        helper.setText(htmlContent, true);
        mailSender.send(message);
    }

}
