package com.willy.malltest.controller;

import com.willy.malltest.dto.CustomerFeedbackDTO;
import com.willy.malltest.dto.MemberShowOrdersDTO;
import com.willy.malltest.dto.ShowCustomerFeedbackDTO;
import com.willy.malltest.service.MemberOrders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(allowCredentials = "true", origins = { "http://localhost:5173","http://localhost:3000" })

public class MemberShowOrdersController {

    @Autowired
    private MemberOrders memberOrders;

    @GetMapping("/memberOrders/showAllOrders")
    public List<MemberShowOrdersDTO> getShowOrders(){
        return memberOrders.getAllMemberOrders();
    }

    @GetMapping("/memberOrders/showAllUserOrders")
    public List<MemberShowOrdersDTO> getShowAllFeedbacks(@RequestParam("userId") Long userId){
        return memberOrders.getAllUserMemberOrders(userId);
    }

    @DeleteMapping("/delete/UserOrders/{orderId}")
    public void deleteUserOrders(@PathVariable  int orderId){
        memberOrders.deleteMemberOrders(orderId);
    }

}
