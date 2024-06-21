package com.spacestar.back.feignClient.service;

import com.spacestar.back.feignClient.controller.AuthClient;
import com.spacestar.back.feignClient.controller.OpenAiClient;
import com.spacestar.back.feignClient.controller.ProfileClient;
import com.spacestar.back.feignClient.dto.req.Message;
import com.spacestar.back.feignClient.dto.req.OpenAiReqDto;
import com.spacestar.back.feignClient.dto.res.AuthResDto;
import com.spacestar.back.feignClient.dto.res.OpenAiResDto;
import com.spacestar.back.feignClient.dto.res.ProfileResDto;
import com.spacestar.back.feignClient.dto.res.SwipeMemberInfoResDto;
import com.spacestar.back.feignClient.vo.res.SwipeMemberInfoResVo;
import com.spacestar.back.global.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeignClientServiceImpl implements FeignClientService {
    private final ProfileClient profileClient;
    private final AuthClient authClient;
    private final OpenAiClient openAiClient;
    @Value("${openai.key}")
    private String openAiKey;

    @Override
    public ProfileResDto getProfile(String memberUuid) {
        org.springframework.http.ResponseEntity<ResponseEntity<ProfileResDto>> response = profileClient.getProfile(memberUuid);
        ResponseEntity<ProfileResDto> body = response.getBody();
        if (body == null || body.result() == null) {
            System.out.println("Profile data is missing or null for memberUuid: " + memberUuid);
            return new ProfileResDto();  // 기본값 반환
        }
        return body.result();
    }

    @Override
    //FeignClient로 Auth 서비스 호출
    public AuthResDto getAuth(String memberUuid) {
        org.springframework.http.ResponseEntity<ResponseEntity<AuthResDto>> response = authClient.getAuth(memberUuid);
        ResponseEntity<AuthResDto> body = response.getBody();
        if (body == null || body.result() == null) {
            System.out.println("Auth data is missing or null for memberUuid: " + memberUuid);
            return new AuthResDto();  // 기본값 반환
        }
        return body.result();
    }

    @Override
    public List<SwipeMemberInfoResVo> getProfileList() {
        org.springframework.http.ResponseEntity<ResponseEntity<List<SwipeMemberInfoResVo>>> response = profileClient.getSwipeProfile();
        ResponseEntity<List<SwipeMemberInfoResVo>> body = response.getBody();
        assert body != null;
        return body.result();
    }

    @Override
    public String getOpenAi(String uuid) {
        List<SwipeMemberInfoResVo> profileList = getProfileList();
        String profileListStr = profileList.stream()
                .map(SwipeMemberInfoResVo::toString)
                .collect(Collectors.joining(", "));
        System.out.println("받은 사용자 목록 : " + profileListStr);
        String prompt = String.format("이 데이터들 중에 [%s] 이 데이터와 성향이 잘 맞는 순서대로 이 데이터를 제외하고 10명의 uuid를 출력해줘.반환 형식은 [3e0b5a99-d5fd-407c-8fde-59940dc512e4,9135f6d9-b4d4-4397-9b63-ff3369d531c2,jkl012] 이런식으로 해줘.", SwipeMemberInfoResDto.toDto(getProfile(uuid), uuid).toString());
        Message message = new Message("user", profileListStr + prompt);//유저 전체 데이터 추가
        System.out.println("gpt에게 물을 내용 " + List.of(message));

        OpenAiReqDto request = new OpenAiReqDto("gpt-3.5-turbo", List.of(message));

        OpenAiResDto chatGPTResponse = openAiClient.getChatCompletion(request);
        return chatGPTResponse.getChoices().get(0).getMessage().getContent();
    }
}
