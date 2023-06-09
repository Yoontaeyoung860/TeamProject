package com.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 댓글 등록시 리퀘스트 바디에 들어가는 정보
 * @author mypc
 *
 */

@Data
public class RboardAddReq {
	
	@NotBlank
	private String bnum;			//게시글번호
	@NotBlank
	private String rcontent;	//댓글내용
}
