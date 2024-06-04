package com.spacestar.back.auth.service;

import com.spacestar.back.auth.dto.req.MemberInfoReqDto;
import com.spacestar.back.auth.dto.req.MemberJoinReqDto;
import com.spacestar.back.auth.dto.req.MemberLoginReqDto;
import com.spacestar.back.auth.dto.res.MemberLoginResDto;
import com.spacestar.back.auth.dto.res.NicknameResDto;

public interface AuthService {
    void addMember(MemberJoinReqDto memberJoinReqDto);

    MemberLoginResDto kakaoLogin(MemberLoginReqDto memberLoginReqDto);

    void updateMemberInfo(String uuid, MemberInfoReqDto memberInfoReqDto);

    void withdrawal(String uuid);

    void withdrawalForce(String uuid);
}
