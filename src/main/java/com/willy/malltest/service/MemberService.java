package com.willy.malltest.service;

import com.willy.malltest.dto.MemberReDataDTO;
import com.willy.malltest.dto.MemberRePasswordDTO;
import com.willy.malltest.model.User;

import java.util.List;

public interface MemberService {

    public List<MemberReDataDTO> getAllMemberReDTOs();

    public MemberReDataDTO getShowMemberReDTOs(Long userId);

    public User updateMemberShowPasswordData(MemberRePasswordDTO memberRePasswordDTO);

    public Boolean memberInputPassword(MemberRePasswordDTO memberRePasswordDTO) ;

    public User updatememberdata(MemberReDataDTO memberReDataDTO);

    public MemberRePasswordDTO getShowpassworddata(Long userId);

}
