package com.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardDTO {
	private Integer bnum; //구인게시글
	private String bcategory;
	private String btitle; //구인 게시글 제목
	private String cid; //기업아이디
	private int bhit;
	private int bgood;
	private int breply;
	private String bcontent;
	private int pbnum;
	private int bgroup;
	private int bstep;
	private int bindent;
  private LocalDateTime bcdate;
  private LocalDateTime budate;


  	private List<BoardUploadFileDTO> files;
	//private List<MultipartFile> files; //첨부파일
}

