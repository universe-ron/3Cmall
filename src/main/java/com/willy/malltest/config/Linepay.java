package com.willy.malltest.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.willy.malltest.model.linepay.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

@Controller
public class Linepay {

    private String allTransactionId;

    @PostMapping("/linepay/submit")
    public String handleMessageSubmit(@RequestParam("text") String text, Model model) {
        model.addAttribute("lastestMsg", text); // 將數據傳遞到另一個 HTML 模板中
        return "other"; // 返回另一個 HTML 模板
    }
    
    @GetMapping("/payok")
    public String payok() {
    	return "payok"; // 重定向到 payok.html 頁面
    }


    @PostMapping("/linepay")
    public String sendlinepay() {
        CheckoutPaymentRequestForm form = new CheckoutPaymentRequestForm();
        form.setAmount(new BigDecimal("300"));  //Amount=Price*Quantity
        form.setCurrency("TWD");
        form.setOrderId("order_id1");  //這個不能一樣(範例:merchant_order_id1)

        ProductPackageForm productPackageForm = new ProductPackageForm();
        productPackageForm.setId("package_id");
        productPackageForm.setName("shop_name");
        productPackageForm.setAmount(new BigDecimal("300"));   //跟上面的Amount要一樣

        ProductForm productForm = new ProductForm();
        productForm.setId("product_id");
        productForm.setName("product_name");
        productForm.setImageUrl("");
        productForm.setQuantity(new BigDecimal("10"));
        productForm.setPrice(new BigDecimal("30"));
        productPackageForm.setProducts(Arrays.asList(productForm));
        form.setPackages(Arrays.asList(productPackageForm));

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setConfirmUrl("http://localhost:8080/payok"); //轉址的網址
        form.setRedirectUrls(redirectUrls);

        String ChannelId="";   //這個要用自己的
        String ChannelSecret = "";  //這個要用自己的
        String requestUri = "/v3/payments/request";
        String nonce = UUID.randomUUID().toString();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(form);
            SignatureUtil signatureUtil = new SignatureUtil();
            String signature = signatureUtil.encrypt(ChannelSecret, ChannelSecret + requestUri + requestBody + nonce);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-LINE-ChannelId", ChannelId);
            headers.set("X-LINE-Authorization-Nonce", nonce);
            headers.set("X-LINE-Authorization", signature);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            String linePayApiUrl = "https://sandbox-api-pay.line.me/v3/payments/request";
            String response = restTemplate.postForObject(linePayApiUrl, requestEntity, String.class);

            JsonNode jsonResponse = objectMapper.readTree(response);
            String paymentUrl = jsonResponse.get("info").get("paymentUrl").get("web").asText();
            String transactionId = jsonResponse.get("info").get("transactionId").asText();
            allTransactionId = transactionId;
            System.out.println("response => " + response);
            System.out.println("transactionId =>" + transactionId);

            return "redirect:" + paymentUrl;  // 轉址到獲取的 URL
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null; // 如果發生異常，返回 null 或者其他適當的值
    }

    @PostMapping("/confirm")
    public String sendconfirm() {
        //ConfirmAPI
        String ChannelId="";  //這個要用自己的
        String ChannelSecret = "";  //這個要用自己的
        String requestBody;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ConfirmData confirmData =new ConfirmData();
            confirmData.setAmount(new BigDecimal("200"));
            confirmData.setCurrency("TWD");
            String confirmNonce = UUID.randomUUID().toString();
            String confirmUrl ="/v3/payments/" + allTransactionId + "/confirm";
            requestBody = objectMapper.writeValueAsString(confirmData);
            SignatureUtil signatureUtil = new SignatureUtil();
            String confirmSignature = signatureUtil.encrypt(ChannelSecret, ChannelSecret + confirmUrl + requestBody + confirmNonce);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-LINE-ChannelId", ChannelId);
            headers.set("X-LINE-Authorization-Nonce", confirmNonce);
            headers.set("X-LINE-Authorization", confirmSignature);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            String linePayApiUrl = "https://sandbox-api-pay.line.me/v3/payments/" + allTransactionId + "/confirm"; //transactionId
            String response = restTemplate.postForObject(linePayApiUrl, requestEntity, String.class);

            return "sellerok";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null; // 如果發生異常，返回 null 或者其他適當的值
    }
}
