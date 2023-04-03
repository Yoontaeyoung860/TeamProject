package com.form.board;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;
@Data
public class ModifyBoardForm {
	private int bnum;
	private int pbnum;
	private String id;
	@NotBlank(message = "카테고리를 선택해주세요.")
	private String bcategory;
	@NotBlank(message = "제목을 입력해주세요.")
	private String btitle;
	@NotBlank(message = "내용을 입력해주세요.")
	private String bcontent;
	private int bhit;
	private int bgood;
	private int breply;
	private int bgroup;
	
	private List<MultipartFile> files; //첨부파일
}
