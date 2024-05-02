package com.willy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.willy.malltest.MalltestApplication;
import com.willy.malltest.controller.CartController;
import com.willy.malltest.dto.AddCartDto;
import com.willy.malltest.model.CartItems;
import com.willy.malltest.model.ProductSpec;
import com.willy.malltest.model.User;
import com.willy.malltest.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {MalltestApplication.class})
@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    public void addToCart_ShouldAddItem() throws Exception {
        AddCartDto addCartDto = new AddCartDto(1L, "spec123", 2); // 使用 Long 型別的 userId
        CartItems expectedCartItems = new CartItems();
        expectedCartItems.setQuantity(2);
        User user = new User(1L);
        ProductSpec spec = new ProductSpec("spec123");
        expectedCartItems.setUser(user);
        expectedCartItems.setProductSpec(spec);

        when(cartService.addToCart(anyLong(), anyString(), anyInt())).thenReturn(expectedCartItems);

        mockMvc.perform(post("/cart/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(addCartDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.user.userId").value(1L))
                .andExpect(jsonPath("$.productSpec.specId").value("spec123"));    }
}
