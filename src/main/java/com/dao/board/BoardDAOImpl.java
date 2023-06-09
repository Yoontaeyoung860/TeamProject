package com.dao.board;

import com.dto.board.BoardDTO;
import com.dto.board.BoardFilter;
import com.dto.board.BoardReqDTO;
import com.dto.board.SearchDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class BoardDAOImpl implements BoardDAO {

	private final JdbcTemplate jt;

	//게시글등록
	@Override
	public BoardReqDTO addBoard(String id, BoardDTO boardDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into board(bnum,bcategory,id,btitle,bcontent,bgroup,bstep,bindent) ");
		sql.append("   values(board_bnum_seq.nextval,?,?,?,?,board_bnum_seq.currval,0,0) ");
		jt.update(sql.toString(),
					boardDTO.getBcategory(),
					id,
					boardDTO.getBtitle(),
					boardDTO.getBcontent());

		StringBuffer sql2 = new StringBuffer();
		sql2.append("select board_bnum_seq.currval from dual");
		Integer boardSeq = jt.queryForObject(sql2.toString(), Integer.class);

		log.info("boardDTO:{}",boardDTO.toString());

		return findBoardByBnum(boardSeq);

	}
	//답글등록
	@Override
	public int reply(String id,BoardDTO boardDTO) {
		updateStep(boardDTO.getBgroup(), boardDTO.getBstep());

		StringBuffer sql = new StringBuffer();
		sql.append(" insert into board(bnum,bcategory,id,btitle,bcontent,pbnum,bgroup,bstep,bindent) ");
		sql.append("   values(board_bnum_seq.nextval,?,?,?,?,?,?,?,?) ");

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jt.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(
						sql.toString(),
						new String[] {"bnum"});

				pstmt.setString(1, boardDTO.getBcategory());
				pstmt.setString(2, id);
				pstmt.setString(3, boardDTO.getBtitle());
				pstmt.setString(4, boardDTO.getBcontent());
				pstmt.setInt(5, boardDTO.getPbnum());
				pstmt.setInt(6, boardDTO.getBgroup());
				pstmt.setInt(7, boardDTO.getBstep() +1);
				pstmt.setInt(8, boardDTO.getBindent() +1);

				return pstmt;
			}
		}, keyHolder);


		return ((BigDecimal)keyHolder.getKeys().get("bnum")).intValue();
	}
	private void updateStep(int bgroup,int bstep) {
		StringBuilder sql = new StringBuilder();
		sql.append("update board ");
		sql.append(" set bstep = bstep+1 ");
		sql.append(" where bgroup =?");
		sql.append(" and bstep > ? ");

		jt.update(sql.toString(),bgroup,bstep);
	}
	//게시글조회
	@Override
	public BoardReqDTO findBoardByBnum(Integer bnum) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select b.bcategory,b.btitle,b.id,m.nickname,b.bcdate,b.bhit,b.bgood,b.breply,b.bcontent,b.bnum,b.bgroup,b.bstep,b.bindent,m.store_fname ");
		sql.append("      from board b, member m");
		sql.append("     where b.id =m.id ");
		sql.append("      and b.bnum=? ");

		BoardReqDTO boardReqDTO =
				jt.queryForObject(sql.toString(),
				new BeanPropertyRowMapper<>(BoardReqDTO.class),
											bnum);
		return boardReqDTO;
	}

	//조회수
	@Override
	public void upBhit(Integer bnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("update board ");
		sql.append("  set bhit=bhit+1 ");
		sql.append("  where bnum =? ");

		jt.update(sql.toString(),bnum);

	}
	//게시글검색(by id)
	@Override
	public List<BoardReqDTO> findBoardById(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select rownum, x.* ");
		sql.append("from (select bnum,bcategory,btitle,id,bcdate,bhit,bgood,breply,bcontent ");
		sql.append("	    from board ");
		sql.append("	   where id=? ");
		sql.append("    order by bnum desc) x ");

		List<BoardReqDTO> list = jt.query(sql.toString(),
												new BeanPropertyRowMapper<>(BoardReqDTO.class),
												id);

		return list;

	}
	//게시글검색(by btitle)
	@Override
	public List<BoardReqDTO> findBoardByBtitle(String bcategory,String btitle,int startRec, int endRec) {
		StringBuffer sql = new StringBuffer();

		sql.append("select b1.* ");
		sql.append("from( select row_number()over(order by t1.bnum desc) num, ");
		sql.append(" t1.bnum,t2.nickname,t1.bcategory,t1.btitle,t2.id,t1.bcdate,t1.bhit,t1.bgood,t1.breply,t1.bcontent ");
		sql.append(" from board t1, member t2 ");
		sql.append(" where t1.id = t2.id ");
		sql.append(" and t1.btitle like '%"+btitle+"%' ");
		sql.append(" and t1.bcategory=?) b1 ");
		sql.append(" where num between ? and ? ");


		List<BoardReqDTO> list = jt.query(sql.toString(),
				new BeanPropertyRowMapper<>(BoardReqDTO.class),
				bcategory,startRec,endRec);

		return list;

	}
	//게시글검색(by nickname)
	@Override
	public List<BoardReqDTO> findBoardByNickname(String bcategory,String nickname,int startRec, int endRec) {
		StringBuffer sql = new StringBuffer();
		sql.append("select b1.* ");
		sql.append("from( select row_number()over(order by t1.bnum desc) num, ");
		sql.append(" t1.bnum,t2.nickname,t1.bcategory,t1.btitle,t2.id,t1.bcdate,t1.bhit,t1.bgood,t1.breply,t1.bcontent ");
		sql.append(" from board t1, member t2 ");
		sql.append(" where t1.id = t2.id ");
		sql.append(" and t1.btitle like '%"+nickname+"%' ");
		sql.append(" and t1.bcategory=?) b1 ");
		sql.append(" where num between ? and ? ");

		List<BoardReqDTO> list = jt.query(sql.toString(),
				new BeanPropertyRowMapper<>(BoardReqDTO.class),
				bcategory,startRec,endRec);
		return list;
	}
	//게시글검색(by bcontent)
	@Override
	public List<BoardReqDTO> findBoardByBcontent(String bcategory,String bcontent,int startRec, int endRec) {

		StringBuffer sql = new StringBuffer();

		sql.append("select b1.* ");
		sql.append("from( select row_number()over(order by t1.bnum desc) num, ");
		sql.append(" t1.bnum,t2.nickname,t1.bcategory,t1.btitle,t2.id,t1.bcdate,t1.bhit,t1.bgood,t1.breply,t1.bcontent ");
		sql.append(" from board t1, member t2 ");
		sql.append(" where t1.id = t2.id ");
		sql.append(" and t1.btitle like '%"+bcontent+"%' ");
		sql.append(" and t1.bcategory=?) b1 ");
		sql.append(" where num between ? and ? ");




		List<BoardReqDTO> list = jt.query(sql.toString(),
				new BeanPropertyRowMapper<>(BoardReqDTO.class),
				bcategory,startRec,endRec);

		return list;

	}
	//게시글수정
	@Override
	public int modifyBoard(int bnum, BoardDTO boardDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("update board ");
		sql.append("set bcategory =?, ");
		sql.append("    btitle=?, ");
		sql.append("    budate= current_timestamp, ");
		sql.append("    bcontent =? ");
		sql.append("where bnum=? ");

		jt.update(sql.toString(),
								boardDTO.getBcategory(),
								boardDTO.getBtitle(),
								boardDTO.getBcontent(),
								bnum);
		return bnum;
	}
	//게시글삭제
	@Override
	public void deleteBoard(int bnum) {
		String sql ="delete board where bnum=?";
		jt.update(sql.toString(),bnum);

	}
	//게시글전체목록(by bcategory)
	@Override
	public List<BoardReqDTO> list(String bcategory) {
		StringBuffer sql = new StringBuffer();
		sql.append("select b.bnum,b.bhit,b.bgood,b.btitle,b.id,m.nickname,b.bcdate,b.bcategory,b.breply,b.bcontent,b.bgroup,b.bstep,b.bindent ");
		sql.append("  from board b, member m ");
		sql.append("  where b.id = m.id ");
		sql.append("   and bcategory=? ");
		sql.append(" order by bgroup desc, bstep asc ");

		List<BoardReqDTO> list = jt.query(sql.toString(),
										new BeanPropertyRowMapper<>(BoardReqDTO.class),
										bcategory);
		return list;
	}
	//게시글 전체목록(페이징)
	@Override
	public List<BoardReqDTO> list(String bcategory, int startRec, int endRec) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select t1.* ");
		sql.append(" from(select row_number() over (order by bgroup desc, bstep asc) num, ");
		sql.append("       b.bnum,b.bhit,b.bgood,b.btitle,b.id,m.nickname,b.bcdate,b.bcategory,b.breply,b.bcontent,b.bgroup,b.bstep,b.bindent ");
		sql.append("     from board b, member m ");
		sql.append("     where b.id = m.id ");
		sql.append("     and bcategory=?) t1 ");
		sql.append(" where num between ? and ? ");

		List<BoardReqDTO> list = jt.query(sql.toString(),
										new BeanPropertyRowMapper<>(BoardReqDTO.class),
										bcategory,startRec,endRec);
		return list;
	}

	//게시글 검색결과 목록
	@Override
	public List<BoardReqDTO> list(SearchDTO searchDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select t1.* ");
		sql.append(" from(select row_number() over (order by bgroup desc, bstep asc) num, ");
		sql.append("       b.bnum,b.bhit,b.bgood,b.btitle,b.id,m.nickname,b.bcdate,b.bcategory,b.breply,b.bcontent,b.bgroup,b.bstep,b.bindent ");
		sql.append("     from board b, member m ");
		sql.append("     where b.id = m.id ");
		sql.append("     and bcategory=? ");


		switch(searchDTO.getSearchType()) {
		case "btitle":
			sql.append(" and b.btitle like '%" + searchDTO.getKeyword() + "%' ");
			break;
		case "nickname":
			sql.append(" and m.nickname like '%" + searchDTO.getKeyword() + "%' ");
			break;
		case "bcontent":
			sql.append(" and b.bcontent like '%" + searchDTO.getKeyword() + "%' ");
			break;

			default:
				break;
		}
		sql.append(") t1 ");
		sql.append(" where num between ? and ? ");
		log.info(sql.toString());
		List<BoardReqDTO> list = jt.query(sql.toString(),
										new BeanPropertyRowMapper<>(BoardReqDTO.class),
										searchDTO.getBcategory(),searchDTO.getStartRec(),searchDTO.getEndRec());
		return list;
	}
	//게시글전체목록 조회수순 나열
	@Override
	public List<BoardReqDTO> bgoodList(String bcategory) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select t1.* ");
		sql.append(" from(select row_number() over (order by bhit desc) num, ");
		sql.append("       b.bnum,b.bhit,b.bgood,b.btitle,b.id,m.nickname,b.bcdate,b.bcategory,b.breply,b.bcontent,b.bgroup,b.bstep,b.bindent ");
		sql.append("     from board b, member m ");
		sql.append("     where b.id = m.id ");
		sql.append("     and b.bcdate > sysdate-7 ");
		sql.append("     and bcategory=?) t1 ");
		sql.append(" where num between 1 and 3 ");
		List<BoardReqDTO> list = jt.query(sql.toString(),
										new BeanPropertyRowMapper<>(BoardReqDTO.class),
										bcategory);

		return list;
	}

	//댓글수 증가
	@Override
	public void upRcount(Integer bnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("update board ");
		sql.append("  set breply=breply+1 ");
		sql.append("  where bnum =? ");
		log.info("breply+1");

		jt.update(sql.toString(),bnum);

	}

	//댓글수 감소
	@Override
	public void downRcount(Integer bnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("update board ");
		sql.append("  set breply=breply-1 ");
		sql.append("  where bnum =? ");
		log.info("breply-1");

		jt.update(sql.toString(),bnum);

	}

	@Override
	public Integer reqBreply(Integer bnum) {

		String sql = "select breply from board where bnum=?";

		Integer breply = jt.queryForObject(sql, Integer.class, bnum);

		return breply;
	}
	//공지추가
	@Override
	public void addNotice(int bnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("update board ");
		sql.append("	set bstatus='Y',");
		sql.append("	bndate = current_timestamp ");
		sql.append("	where bnum=? ");

		jt.update(sql.toString(),bnum);

	}
	//공지삭제
	@Override
	public void delNotice(int bnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("update board ");
		sql.append("	set bstatus='N' ");
		sql.append("	where bnum=? ");

		jt.update(sql.toString(),bnum);

	}
	//공지리스트
	@Override
	public List<BoardReqDTO> noticeList(String bcategory) {
		StringBuffer sql = new StringBuffer();

		sql.append("select t1.* ");
		sql.append("   from   (select row_number() over (order by bndate desc) num, ");
		sql.append("              b.bnum,b.bhit,b.bgood,b.btitle,b.id,m.nickname,b.bcdate,b.bcategory,b.breply,b.bcontent, b.bstatus ");
		sql.append("        from board b, member m ");
		sql.append("        where b.id = m.id ");
		sql.append("        and b.bstatus='Y' ");
		sql.append("        and bcategory=?) t1 ");
		sql.append("where num between 1 and 3 ");

		List<BoardReqDTO> list = jt.query(sql.toString(),
								new BeanPropertyRowMapper<>(BoardReqDTO.class),
								bcategory);

		return list;
	}

	//해당글 공지여부
	@Override
	public boolean isNotice(int bnum) {
		boolean isNotice = false;
		StringBuffer sql = new StringBuffer();

		 sql.append("select count(bnum) ");
    	 sql.append("	from board ");
    	 sql.append("	where bstatus='Y' ");
      	 sql.append("	and bnum=? ");

      	if(jt.queryForObject(sql.toString(), Integer.class,bnum) != 0) {
      		isNotice = true;
      	}

		return isNotice;
	}

		@Override
		public int totlaRecordCount() {
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) ");
			sql.append("	from board ");

			int totalCount = jt.queryForObject(sql.toString(), Integer.class);
			return totalCount;
		}
		@Override
		public int totalRecordCount(String bcategory) {
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) ");
			sql.append("	from board ");
			sql.append("	where bcategory=? ");

			int totalCount = jt.queryForObject(sql.toString(), Integer.class,bcategory);
			return totalCount;
		}
		//검색시 레코드 전체수
		@Override
		public int totalRecordCount(String bcategory,String searchType,String keyword) {
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) ");
			sql.append("	from board b,member m ");
			sql.append("	where bcategory=? ");
			sql.append("     and b.id = m.id ");

			switch(searchType) {
			case "btitle":
				sql.append("	and b.btitle like '%" + keyword+ "%' ");
				break;
			case "nickname":
				sql.append("	and m.nickname like '%" + keyword+ "%' ");
				break;
			case "bcontent":
				sql.append("	and b.bcontent like '%" + keyword+ "%' ");
				break;

				default:
					break;
			}

			int totalCount = jt.queryForObject(sql.toString(), Integer.class,bcategory);
			return totalCount;
		}

	@Override
	public List<BoardReqDTO> getFilteredJobPosts(BoardFilter boardFilter, String bcategory, int startRec, int endRec) {
		String sql = "SELECT * FROM (SELECT ROWNUM RNUM, T.* FROM (SELECT * FROM job_posts WHERE " +
				"bcategory = ? AND " +
				"work_period IN (SELECT * FROM TABLE(CAST(? AS WORKPERIOD_ARR))) AND " +
				"work_days IN (SELECT * FROM TABLE(CAST(? AS WORKDAYS_ARR))) AND " +
				"work_hours IN (SELECT * FROM TABLE(CAST(? AS WORKHOURS_ARR))) " +
				"ORDER BY bnum DESC) T) WHERE RNUM BETWEEN ? AND ?";

		Object[] args = {
				bcategory,
				boardFilter.getWorkPeriods(),
				boardFilter.getWorkDays(),
				boardFilter.getWorkHours(),
				startRec,
				endRec
		};

		RowMapper<BoardReqDTO> rowMapper = (rs, rowNum) -> {
			BoardReqDTO boardReqDTO = new BoardReqDTO();
			boardReqDTO.setBnum(rs.getInt("bnum"));
			boardReqDTO.setBcategory(rs.getString("bcategory"));
			boardReqDTO.setBtitle(rs.getString("btitle"));
			boardReqDTO.setBhit(rs.getInt("bhit"));
			boardReqDTO.setBgood(rs.getInt("good"));
			boardReqDTO.setBreply(rs.getInt("breply"));
			boardReqDTO.setBcontent(rs.getString("bcontent"));
			boardReqDTO.setPbnum(rs.getInt("pbnum"));
			boardReqDTO.setBgroup(rs.getInt("bgroup"));
			boardReqDTO.setBstep(rs.getInt("bstep"));
			boardReqDTO.setBindent(rs.getInt("bindent"));
			boardReqDTO.setBcdate(rs.getTimestamp("bcdate").toLocalDateTime().toLocalDate());
			boardReqDTO.setBudate(rs.getTimestamp("budate").toLocalDateTime().toLocalDate());
			return boardReqDTO;
		};

		return jt.query(sql, args, rowMapper);
	}


}
