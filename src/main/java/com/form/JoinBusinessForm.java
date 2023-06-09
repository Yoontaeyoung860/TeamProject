package com.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class JoinBusinessForm {
	private int bnum;
	@NotBlank
	@Size(max=20)
	private String bbnum;
	@NotBlank
	@Email
	@Size(max=40)
	private String id;
	@NotBlank
	private String bname;
	@NotBlank
	private String baddress;
	private String btel;
	/*
	 * @NotBlank private Integer btel2;
	 * 
	 * @NotBlank private Integer btel3;
	 */
	private String nitghtcare;
	private String rareani;
	private String visitcare;
	private String holidayopen;
	private String dental;
	private String openhours;
	
	private List<MultipartFile> files;
}

