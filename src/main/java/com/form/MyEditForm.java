package com.form;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MyEditForm {
	
	  private String id;
	  private String pw;
	  private String name;
	  private String nickname;
	  private LocalDate birth;
	  private String gender;
	  //TODO
	  private String tel;
	  private String tel2;
	  private String tel3;
	  private String email;
	  private String address;
	  private String mtype;

}
