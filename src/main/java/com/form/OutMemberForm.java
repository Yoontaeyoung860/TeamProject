package com.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OutMemberForm {
	@NotBlank
	private String pwChk;
}
