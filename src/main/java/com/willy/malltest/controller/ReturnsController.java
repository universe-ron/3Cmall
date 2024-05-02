package com.willy.malltest.controller;


import com.willy.malltest.model.Returns;
import com.willy.malltest.repository.ReturnsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ReturnsController {

    @Autowired
    private ReturnsRepository returnsRepo;

    @GetMapping("/returns/list")
    public String findAllReturns(Model model) {
        List<Returns> returnsList = returnsRepo.findAll();
        model.addAttribute("returnsList", returnsList);
        return "returns/showPage";
    }

    @DeleteMapping("/returns/delete")
    public String deleteReturns(@RequestParam Integer id) {
        returnsRepo.deleteById(id);
        return "returns/showPage";
    }
}
