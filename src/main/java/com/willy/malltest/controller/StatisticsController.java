package com.willy.malltest.controller;

import com.willy.malltest.dto.StatisticsMemberDTO;
import com.willy.malltest.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:5173", "http://localhost:3000"})
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/orders/findAllStatisticsMemberDTOs")
    public List<StatisticsMemberDTO> findAllStatisticsMemberDTOs() {
        return statisticsService.findStatisticsMemberDTOs();
    }

}
