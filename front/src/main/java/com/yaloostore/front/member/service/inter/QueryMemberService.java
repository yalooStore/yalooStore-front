package com.yaloostore.front.member.service.inter;

import com.yaloostore.front.member.dto.response.MemberDuplicateDto;

public interface QueryMemberService {


    MemberDuplicateDto checkNickname(String nickname);
    MemberDuplicateDto checkPhoneNumber(String phone);
    MemberDuplicateDto checkEmail(String email);

}
