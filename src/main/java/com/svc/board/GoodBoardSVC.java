package com.svc.board;

import com.dto.board.GoodBoardDTO;
import com.proj.animore.dto.board.GoodBoardDTO;

import java.util.List;

public interface GoodBoardSVC {

	//좋아요 등록
		void addGoodBoard(String id, Integer bnum);
		
		//좋아요 갯수 1증가
		void upGoodBoardCnt(Integer bnum);
		
		//좋아요 전체목록
		List<GoodBoardDTO> goodBoardList (String id);
		
		//좋아요 삭제
		void delGoodBoard(Integer bnum, String id);
		
		//좋아요 개수 1감소
		void downGoodBoardCnt(Integer bnum);
		
		//해당 글 좋아요 여부확인
		int isGoodBoard(Integer bnum, String id);
		
		//좋아요 갯수확인
		int GoodBoardCnt(Integer bnum);
}
