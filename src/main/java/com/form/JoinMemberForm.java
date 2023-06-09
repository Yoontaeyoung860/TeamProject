package com.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class JoinMemberForm {
  
  @NotBlank
  @Email
  @Size(max=40)
  private String id;
  @NotBlank
  @Pattern(regexp ="^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z]).{9,12}$",
  message= "영문,숫자,특수문자 조합으로 9~12자리로 설정하시오")
  @Size(min=8, max=16)
  private String pw;
  @NotBlank
  private String pw2;
  private MultipartFile file;
  @NotBlank
  @Size(max=5)
  private String name;
  @NotNull
  @Past
  @DateTimeFormat(pattern ="yyyy-MM-dd")
  private LocalDate birth;
  @NotBlank
  private String gender;
  @NotBlank
  private String nickname;
  private String tel;
  private String tel2;
  private String tel3;
   @NotBlank
   @Email
  private String email;
   @NotBlank
  private String address;
 
//  private List<String> myAni;
  private String myAni;
  
}
