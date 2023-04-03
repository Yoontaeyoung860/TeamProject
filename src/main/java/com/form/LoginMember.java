package com.form;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 쿠키에 들어갈 정보
 * @author mypc
 *
 */
@Data
@AllArgsConstructor
public class LoginMember {
	private String id;
	private String nickname;
	private String mtype;
}
