package com.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RboardModiReq {
	@NotBlank
	private String rnum;
	@NotBlank
	private String bnum;
	@NotBlank
	private String rcontent;
}
