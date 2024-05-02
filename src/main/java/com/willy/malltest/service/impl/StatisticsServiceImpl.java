package com.willy.malltest.service.impl;

import com.willy.malltest.dto.StatisticsMemberDTO;
import com.willy.malltest.model.User;
import com.willy.malltest.repository.UsersRepository;
import com.willy.malltest.service.StatisticsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<StatisticsMemberDTO> findStatisticsMemberDTOs() {
        List<User> users = usersRepository.findAll();
        List<StatisticsMemberDTO> statisticsMemberDTOs = new ArrayList<>();//初始化空的OrdersDetailDTO集合

        for (User user : users) { //遍歷每個User對象
            StatisticsMemberDTO statisticsMemberDTO = new StatisticsMemberDTO();
            BeanUtils.copyProperties(user, statisticsMemberDTO);
            statisticsMemberDTOs.add(statisticsMemberDTO);
        }

        return statisticsMemberDTOs;
    }

}
