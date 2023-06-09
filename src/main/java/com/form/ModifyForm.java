package com.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class ModifyForm {
	
	private String id;
	@NotBlank
	private String name;
	@NotBlank
	private String nickname;
	@NotNull
  	@Past
  	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private LocalDate birth;
	private String tel;
	private String tel2;
	private String tel3;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String address;
	@NotBlank
	private String pwChk;
}
