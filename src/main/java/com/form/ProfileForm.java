package com.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@NoArgsConstructor
public class ProfileForm {
	
	
	private String id;
	private String nickname;
	private int mileag;
	private MultipartFile file;
	
}
