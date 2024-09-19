package com.keduit.shop.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class MemberDTO {
//  spring 에서 유효성 검사 하는법 Maven에서 추가해야함
//  @으로 간단하게 가능
//  (message = "") 으로 위배되는경우 메세지 출력

    @NotBlank(message = "이름은 필수 입력입니다.")
//  null체크, 길이가 0인 문자열, 빈 문자열
    private String name;

    @NotEmpty(message = "이메일은 필수 입력입니다.")
//  null체크 길이가 0인 문자열
    @Email(message = "이메일 형식으로 입력해주세요")
    private  String email;

    @NotEmpty(message = "비밀번호는 필수 입력입니다.")
//  입력값의 길이 지정
    @Length(min = 4, max=16, message = "비밀번호는 4자이상 16자 이하로 입력해주세요")
    private String password;

    @NotEmpty(message = "주소는 필수 입력입니다.")
    private String address;

}
