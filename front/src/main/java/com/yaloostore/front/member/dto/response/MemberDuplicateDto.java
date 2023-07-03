package com.yaloostore.front.member.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDuplicateDto {

    private boolean result;

    public MemberDuplicateDto(boolean result){
        this.result = result;
    }

}
