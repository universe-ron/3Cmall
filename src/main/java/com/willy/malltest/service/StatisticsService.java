package com.willy.malltest.service;

import com.willy.malltest.dto.StatisticsMemberDTO;

import java.util.List;

public interface StatisticsService {

    List<StatisticsMemberDTO> findStatisticsMemberDTOs();

}
