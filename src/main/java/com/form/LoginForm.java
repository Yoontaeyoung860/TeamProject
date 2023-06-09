package com.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginForm {
	
	@NotBlank
	@Email
	private String id;
	@NotBlank
	private String pw;

	//자동로그인 체크여부
	private boolean autologincheck;
}
