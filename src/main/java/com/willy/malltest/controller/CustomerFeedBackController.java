package com.willy.malltest.controller;


import com.willy.malltest.dto.CustomerFeedbackDTO;
import com.willy.malltest.dto.ShowCustomerFeedbackDTO;
import com.willy.malltest.service.CustomerFeedback;
import com.willy.malltest.service.impl.MailServiceImpl;
import com.willy.malltest.service.UserService;
import com.willy.malltest.utils.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(allowCredentials = "true", origins = { "http://localhost:5173","http://localhost:3000" })

public class CustomerFeedBackController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CustomerFeedback customerFeedback;
    @Autowired
    private MailServiceImpl mailService;
    @GetMapping("/feedbacks")
    public List<CustomerFeedbackDTO> getAllFeedbacks(){
        return customerFeedback.getAllFeedbacksDTO();
    }

    @GetMapping("/feedbacks/customerFeedbacks")
    public List<CustomerFeedbackDTO> getshowFeedbacks(@RequestParam ("userId") Long userId){
        return customerFeedback.getShowFeedbacksDTO(userId);
    }

    @GetMapping("/feedbacks/showCustomerFeedbacks")
    public List<ShowCustomerFeedbackDTO> getShowAllFeedbacks(@RequestParam ("userId") Long userId){
        return customerFeedback.getShowAllFeedbacksDTO(userId);
    }

    @PostMapping("/create/customerFeedbacks")
    public com.willy.malltest.model.CustomerFeedback createCustomerFeedbacks(@RequestBody CustomerFeedbackDTO customerFeedbackDTO){
        return customerFeedback.addFeedbacksDTO(customerFeedbackDTO);

    }

    @PutMapping("/update/customerFeedbacks")
    public com.willy.malltest.model.CustomerFeedback updateCustomerFeedbacks(@RequestBody CustomerFeedbackDTO customerFeedbackDTO){
        return customerFeedback.updateFeedbacksDTO(customerFeedbackDTO);
    }

    @PutMapping("/update/customerFeedbacksStatus")
    public com.willy.malltest.model.CustomerFeedback test(@RequestBody CustomerFeedbackDTO customerFeedbackDTO){
        return customerFeedback.test(customerFeedbackDTO);
    }
    @DeleteMapping("/delete/customerFeedbacks")
    public void deleteCustomerFeedbacks(@RequestBody CustomerFeedbackDTO customerFeedbackDTO){
        customerFeedback.deleteCustomerFeedback(customerFeedbackDTO);
    }

    @PutMapping("/sendFeedbackEmail")
    public String sendFeedbackEmail(@RequestParam Integer feedbackId,@RequestParam String message) {
        try {
            mailService.sendFeedbackEmailReturn(feedbackId, message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "success send email! FeedbackId: " + feedbackId;
    }
    @GetMapping("/unreadFeedbacks")
    public List<CustomerFeedbackDTO> unreadFeedbacks() {
        List<CustomerFeedbackDTO> customerFeedbacks = customerFeedback.getAllFeedbacksDTO();

        // 使用Stream API篩選符合條件的CustomerFeedbackDTO物件

        return customerFeedbacks.stream()
                .filter(feedback -> "等待回覆中".equals(feedback.getCustomerFeedbackStatus()))
                .collect(Collectors.toList());
    }
}
