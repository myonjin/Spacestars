package com.spacestar.back.auth.dto.req;

import com.spacestar.back.auth.domain.Member;
import com.spacestar.back.auth.enums.GenderType;
import com.spacestar.back.auth.enums.UnregisterType;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberJoinReqDto {

    private String email;
    private String nickname;
    private String imageUrl;
    private LocalDate birth;
    private GenderType gender;
    private boolean infoAgree;

    @Setter
    private String uuid;
    @Setter
    private Long memberId;


    public static Member toEntity(MemberJoinReqDto memberJoinReqDto) {

        return Member.builder()
                .uuid(memberJoinReqDto.getUuid())
                .email(memberJoinReqDto.getEmail())
                .birth(memberJoinReqDto.getBirth())
                .gender(memberJoinReqDto.getGender())
                .unregister(UnregisterType.MEMBER)
                .infoAgree(memberJoinReqDto.isInfoAgree())
                .build();
    }

    public static Member updateEntity(MemberJoinReqDto memberJoinReqDto) {

        return Member.builder()
                .id(memberJoinReqDto.getMemberId())
                .uuid(memberJoinReqDto.getUuid())
                .email(memberJoinReqDto.getEmail())
                .birth(memberJoinReqDto.getBirth())
                .gender(memberJoinReqDto.getGender())
                .unregister(UnregisterType.MEMBER)
                .infoAgree(memberJoinReqDto.isInfoAgree())
                .build();
    }

}
