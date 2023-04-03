package com.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RboardReReplyReq {
	private Integer prnum;
	@NotNull
	private Integer bnum;
	@NotBlank
	private String rcontent;
}
