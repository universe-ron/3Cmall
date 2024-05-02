package com.willy.malltest.service;

import com.willy.malltest.dto.MemberShowOrdersDTO;

import java.util.List;

public interface MemberOrders {
    public List<MemberShowOrdersDTO> getAllMemberOrders();
    public List<MemberShowOrdersDTO> getAllUserMemberOrders(Long userId);
    public void deleteMemberOrders(int orderId);
}
