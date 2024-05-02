package com.willy.malltest.service;

import com.willy.malltest.dto.OrdersDetailDTO;
import com.willy.malltest.dto.ReceiveOrdersDTO;
import com.willy.malltest.dto.ReceiveOrdersDetailDTO;
import com.willy.malltest.model.Orders;

import java.util.List;

public interface OrdersManagementService {

    List<Orders> findAll();

    List<OrdersDetailDTO> findOrdersDetailDTOs();

    String insertOrdersDetail(ReceiveOrdersDetailDTO receiveOrdersDetailDTO);

    String insertOrders(ReceiveOrdersDTO receiveOrdersDTO);

    String insertOrdersCart(ReceiveOrdersDTO receiveOrdersDTO);

    Orders findOrderById(Integer orderId);

    Orders updateOrderByOrderId(Integer orderId, ReceiveOrdersDTO receiveOrdersDTO);

    String updateOrdersDetailByOrdersDetailId(Integer ordersDetailId, ReceiveOrdersDetailDTO receiveOrdersDetailDTO);

    void deleteOrdersDetailByOrdersDetailId(Integer ordersDetailId);

    Orders updateOrderStatusByOrderId(Integer orderId, String orderStatus);

}
