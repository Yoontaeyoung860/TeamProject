package com.dto.board;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class BoardReqDTO {

	private Integer bnum;
	private String bcategory;
	private String btitle;
	private String id;
	private String nickname;
	private int bhit;
	private int bgood;
	private int breply;
	private String bcontent;
	private int pbnum;
	private int bgroup;
	private int bstep;
	private int bindent;
	private String bstatus;
	private LocalDate bndate;
	private LocalDate bcdate;
	private LocalDate budate;
	private String store_fname;
	private int rownum;
	
	private List<BoardUploadFileDTO> files;
	}

