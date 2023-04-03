--테이블 및 시퀀스 생성
drop table member;   --회원 테이블 삭제

drop table BUSINESS; --기업 테이블 삭제

drop table businessfile; --"기업" 이미지 업로드 테이블 삭제

drop table board; --구인 게시판 테이블 삭제

drop table boardfile; -- "구인" 게시판 이미지 업로드 테이블 삭제

drop table review_comments; --구인 게시판 후기글 테이블 삭제

drop table reviewfile; --구인 게시판 "후기글" 이미지 업로드 테이블 삭제

drop table board_favorites; -- 구인 게시글 즐겨찾기 테이블 삭제

drop table grievance_board; --고충 게시판 테이블 삭제

drop table grievance_comments -- 고충 게시판 댓글 테이블 삭제


--회원 Table
create table member(
  id varchar2(20), --회원 아이디
  pw varchar2 (16) not null, --회원 비밀번호
  tel varchar2(16) not null, --회원 전화번호
  email varchar2(30) not null, --회원 이메일
  name varchar2(15) not null,  --회원 이름
  nickname varchar2(30) not null, --회원 닉네임
  gender char(5), --회원 성별 check 필수 남자 or 여자
  address varchar2(40) not null, --회원 주소
  birth varchar2(10) not null, --회원 생일
  cdate timestamp DEFAULT systimestamp not null, --가입 생성일
  udate timestamp, --회원 정보 수정일
  fsize varchar2(45), --파일 크기 정보
  ftype varchar2(50), --파일의 타입 jpeg,png
  store_fname varchar2(150), --서버에 저장된 파일 이름
  upload_fname varchar2(150), --파일의 원래 이름
  constraint MEMBER_ID_PK primary key(id),
  constraint MEMBER_gender_ck check(gender in('남자','여자')),
  constraint MEMBER_ftype_ck check(ftype like 'image/%')   --image file만 올 수 있게 검증
);

--기업 Table
create table business(
  bnum -- 기업 식별 번호
  bid  varchar2(20) not null,-- 기업 아이디
  bpw  varchar2(20) not null,--기업 비밀번호
  bname number(8) not null, --기업명
  btel varchar2(20) not null, --기업 전화번호
  baddress varchar2(150) not null, --기업 주소
  bemail varchar2(40) not null, -- 기업 이메일
  bfsize varchar2(45), --기업 사진
  bftype varchar2(50), --기업 사진 타입
  bcdate timestamp DEFAULT systimestamp not null, --기업 가입 생성일
  budate timestamp,  --기업 가입 수정일
  constraint business_bid_PK primary key(bnum) --기업 식별 번호 Primary key.
  constraint business_ftype_ck check(ftype like 'image/%') --image file만 올 수 있게 검증
);
-- 기업 이미지 업로드 Table
create table businessfile(
  fnum number(10), -- 파일 식별 번호
  bnum number (8), -- 기업 식별 번호
  store_fname varchar2(50), -- 서버에 저장된 파일 이름
  upload_fname varchar2(50), -- 업로드한 파일의 원래 이름
  fsize varchar2(45), -- 파일 크기
  ftype varchar2(50), -- 파일 타입 (MIME 타입)
  cdate timestamp default systimestamp, -- 파일 생성일
  udate timestamp, -- 파일 수정일
  constraint businessfile_fnum_pk primary key(fnum), -- 파일 식별 번호 Primary key
  constraint businessfile_bnum_fk foreign key(bnum) references business(bnum) on delete cascade -- 기업 식별 번호 Foreign key
);


--구인 게시글 Table
create table board(
  bnum number(8), --게시글 식별 번호
  btitle varchar2(150) not null, --게시글 제목
  id varchar2(40) not null, --작성자 아이디
  bcdate timestamp DEFAULT systimestamp not null, --게시글 생성일
  budate timestamp DEFAULT systimestamp, --게시글 수정일
  bhit number(5) DEFAULT 0 not null, --조회수
  bgood number(5) DEFAULT 0 not null, --좋아요 수
  breply number(5) DEFAULT 0 not null, --댓글 수
  bcontent clob not null, --게시글 내용
  pbnum number(10), --부모게시글번호
  bgroup number(5), --계층적 게시글 그룹 번호
  bstep number(3),  --계층적 게시글 순서
  bindent number(3), --계층적 게시글 들여쓰기 레벨
  constraint BOARD_BNUM_PK primary key(bnum), --게시글 식별 번호 Primary Key
);
--구인 게시판 이미지 업로드 Table
create table boardfile(
  fnum number(10), -- 파일 식별 번호
  bnum number (8), -- 게시글 식별 번호
  store_fname varchar2(50), -- 서버에 저장된 파일 이름
  upload_fname varchar2(50), -- 업로드한 파일의 원래 이름
  fsize varchar2(45), -- 파일 크기
  ftype varchar2(50), -- 파일 타입 (MIME 타입)
  cdate timestamp default systimestamp, -- 파일 생성일
  udate timestamp, -- 파일 수정일
  constraint boardfile_fnum_pk primary key(fnum), -- 파일 식별 번호 Primary key
  constraint boardfile_bnum_fk foreign key(bnum) references board(bnum) on delete cascade -- 게시글 식별 번호 Foreign key
);

--구인 게시글 리뷰(후기글) Table
create table review_comments(
  rnum number(10), -- 댓글 식별 번호
  bnum number(8), -- 게시글 식별 번호
  id varchar2(40) not null, -- 작성자 아이디
  rcontent clob not null, -- 댓글 내용
  rcdate timestamp DEFAULT systimestamp not null, -- 댓글 생성일
  rudate timestamp, -- 댓글 수정일
  rgood number(5) DEFAULT 0 not null, -- 좋아요 수
  prnum number(10), -- 부모 댓글 번호
  rgroup number(5), -- 계층적 댓글 그룹 번호
  rstep number(3), -- 계층적 댓글 순서
  rindent number(3), -- 계층적 댓글 들여쓰기 레벨
  constraint REVIEW_RNUM_PK primary key(rnum), -- 댓글 식별 번호 Primary Key
  constraint REVIEW_BNUM_FK foreign key(bnum) references board(bnum) on delete cascade -- 게시글 식별 번호 Foreign Key
);

--구인 게시글 리뷰(후기글) 이미지 업로드 Table
create table reviewfile(
  fnum number(10), -- 파일 식별 번호
  rnum number (10), -- 댓글 식별 번호
  store_fname varchar2(50), -- 서버에 저장된 파일 이름
  upload_fname varchar2(50), -- 업로드한 파일의 원래 이름
  fsize varchar2(45), -- 파일 크기
  ftype varchar2(50), -- 파일 타입 (MIME 타입)
  cdate timestamp default systimestamp, -- 파일 생성일
  udate timestamp, -- 파일 수정일
  constraint reviewfile_fnum_pk primary key(fnum), -- 파일 식별 번호 Primary key
  constraint reviewfile_rnum_fk foreign key(rnum) references review_comments(rnum) on delete cascade -- 댓글 식별 번호 Foreign key
);

--회원의 구인 게시글 즐겨찾기 테이블
create table board_favorites(
  fnum number(10), -- 즐겨찾기 식별 번호
  id varchar2(40) not null, -- 사용자 아이디
  bnum number(8), -- 게시글 식별 번호
  fcdate timestamp DEFAULT systimestamp not null, -- 즐겨찾기 추가일
  constraint BOARD_FAVORITES_FNUM_PK primary key(fnum), -- 즐겨찾기 식별 번호 Primary Key
  constraint BOARD_FAVORITES_ID_FK foreign key(id) references member(id), -- 사용자 아이디 Foreign Key
  constraint BOARD_FAVORITES_BNUM_FK foreign key(bnum) references board(bnum), -- 게시글 식별 번호 Foreign Key
  constraint BOARD_FAVORITES_UNIQUE unique(id, bnum) -- 사용자별 게시글 즐겨찾기 중복 방지
);

-- 고충 게시판 테이블
create table grievance_board(
  gnum number(10), -- 게시글 식별 번호
  id varchar2(40) not null, -- 작성자 아이디
  gtitle varchar2(150) not null, -- 게시글 제목
  gcontent clob not null, -- 게시글 내용
  gcdate timestamp DEFAULT systimestamp not null, -- 게시글 생성일
  gudate timestamp DEFAULT systimestamp, -- 게시글 수정일
  ghit number(5) DEFAULT 0 not null, -- 조회수
  constraint GRIEVANCE_BOARD_GNUM_PK primary key(gnum), -- 게시글 식별 번호 Primary Key
  constraint GRIEVANCE_BOARD_ID_FK foreign key(id) references member(id) -- 작성자 아이디 Foreign Key
);


-- 고충 게시판 댓글 테이블
create table grievance_comments(
  cnum number(10), -- 댓글 식별 번호
  gnum number(10) not null, -- 게시글 식별 번호
  id varchar2(40) not null, -- 작성자 아이디
  ccontent varchar2(400) not null, -- 댓글 내용
  ccdate timestamp DEFAULT systimestamp not null, -- 댓글 생성일
  cudate timestamp DEFAULT systimestamp, -- 댓글 수정일
  constraint GRIEVANCE_COMMENTS_CNUM_PK primary key(cnum), -- 댓글 식별 번호 Primary Key
  constraint GRIEVANCE_COMMENTS_ID_FK foreign key(id) references member(id), -- 작성자 아이디 Foreign Key
  constraint GRIEVANCE_COMMENTS_GNUM_FK foreign key(gnum) references grievance_board(gnum) -- 게시글 식별 번호 Foreign Key
);



-- 시퀀스 삭제
DROP SEQUENCE member_id_seq; --회원 시퀀스 삭제
DROP SEQUENCE business_bnum_seq;  --기업 시퀀스 삭제
DROP SEQUENCE board_bnum_seq; --구인 게시판 시퀀스 삭제
DROP SEQUENCE boardfile_fnum_seq; --구인 게시판 파일 테이블 시퀀스 삭제
DROP SEQUENCE review_comments_fnum_seq; -- 구인 후기글 시퀀스 삭제
DROP SEQUENCE reviewfile_fnum_seq;  -- 구인 후기글 파일 테이블 시퀀스 삭제
DROP SEQUENCE favorite_fnum_seq; --회원 구인 게시글 즐겨찾기 시퀀스 삭제
DROP SEQUENCE grievance_board_gnum_seq; -- 고충 게시판 시퀀스 삭제
DROP SEQUENCE grievance_comments; --고충 게시판 댓글 시퀀스 삭제


-- 시퀀스 생성
CREATE SEQUENCE member_id_seq; -- 회원 시퀀스 생성
CREATE SEQUENCE business_bnum_seq; -- 기업 시퀀스 생성
CREATE SEQUENCE board_bnum_seq; -- 구인 게시판 시퀀스 생성
CREATE SEQUENCE boardfile_fnum_seq; -- 구인 게시판 파일 테이블 시퀀스 생성
CREATE SEQUENCE review_comments_fnum_seq; -- 구인 후기글 시퀀스 생성
CREATE SEQUENCE reviewfile_fnum_seq; -- 구인 후기글 파일 테이블 시퀀스 생성
CREATE SEQUENCE favorite_fnum_seq; -- 회원 구인 게시글 즐겨찾기 시퀀스 생성
CREATE SEQUENCE grievance_board_gnum_seq; -- 고충 게시판 시퀀스 생성
CREATE SEQUENCE grievance_comments_cnum_seq; -- 고충 게시판 댓글 시퀀스 생성


commit;

