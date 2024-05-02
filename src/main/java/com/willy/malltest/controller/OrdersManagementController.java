package com.willy.malltest.controller;

import com.willy.malltest.dto.OrdersDetailDTO;
import com.willy.malltest.dto.ReceiveOrdersDTO;
import com.willy.malltest.dto.ReceiveOrdersDetailDTO;
import com.willy.malltest.model.Orders;
import com.willy.malltest.repository.OrdersDetailRepository;
import com.willy.malltest.service.OrdersManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = {"http://localhost:5173","http://localhost:3000"}, allowCredentials = "true")
public class OrdersManagementController {

    @Autowired
    private OrdersManagementService ordersManagementService;

    @Autowired
    private OrdersDetailRepository ordersDetailRepository;

    @GetMapping("/orders/findAll")
    public List<Orders> findAllOrders() {
        return ordersManagementService.findAll();
    }

    @GetMapping("/orders/findAllOrdersDetailDTOs")
    public List<OrdersDetailDTO> findAllOrdersDetailDTOs() {
        return ordersManagementService.findOrdersDetailDTOs();
    }

    @PostMapping("/orders/insertOrdersDetail")
    public String insertOrdersDetail(@RequestBody ReceiveOrdersDetailDTO receiveOrdersDetailDTO) {
        return ordersManagementService.insertOrdersDetail(receiveOrdersDetailDTO);
    }
    @PostMapping("/orders/insertOrders")
    public String insertOrders(@RequestBody ReceiveOrdersDTO receiveOrdersDTO) {
        return ordersManagementService.insertOrders(receiveOrdersDTO);
    }

    @PostMapping("/orders/insertOrdersCart")
    public ResponseEntity<Map<String, String>> insertOrdersCart(@RequestBody ReceiveOrdersDTO receiveOrdersDTO) {
        String orderId =  ordersManagementService.insertOrdersCart(receiveOrdersDTO);
        if (orderId != null) {
            Map<String, String> response = new HashMap<>();
            response.put("orderId", orderId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/orders/updateOrder/{orderId}")
    public Orders updateOrderByOrderId(@PathVariable Integer orderId, @RequestBody ReceiveOrdersDTO receiveOrdersDTO) {
        return ordersManagementService.updateOrderByOrderId(orderId, receiveOrdersDTO);
    }
    @PutMapping("/orders/updateOrdersDetail/{ordersDetailId}")
    public String updateOrdersDetailByOrdersDetailId(@PathVariable Integer ordersDetailId, @RequestBody ReceiveOrdersDetailDTO receiveOrdersDetailDTO) {
        return ordersManagementService.updateOrdersDetailByOrdersDetailId(ordersDetailId, receiveOrdersDetailDTO);
    }

    @DeleteMapping("/orders/deleteOrdersDetail/{ordersDetailId}")
    public String deleteOrdersDetailByOrdersDetailId(@PathVariable Integer ordersDetailId) {
        ordersManagementService.deleteOrdersDetailByOrdersDetailId(ordersDetailId);
        return "success delete ordersDetail";
    }
    @PutMapping("/orders/updateOrderStatusByOrderId/{orderId}")
    public Orders updateOrderStatusByOrderId(@PathVariable Integer orderId, @RequestParam String orderStatus) {
        return ordersManagementService.updateOrderStatusByOrderId(orderId, orderStatus);
    }
}