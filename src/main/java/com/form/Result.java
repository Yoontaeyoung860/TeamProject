package com.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * HTTP API 응답메세지 구조
 * @author mypc
 * 
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
	private String rtcd;
	private String rtmsg;
	private T data;

	};




/*9위 코드는 HTTP API 응답메시지의 구조를 정의하는 "Result" 클래스입니다. 해당 클래스는 제네릭 타입 T를 사용하여 데이터를 제네릭 형태로 처리할 수 있습니다.

private String rtcd : API 요청 결과 코드를 나타내는 문자열 변수입니다.
private String rtmsg : API 요청 결과 메시지를 나타내는 문자열 변수입니다.
private T data : API 요청 결과로 반환할 데이터를 나타내는 제네릭 타입 변수입니다.
@Data, @AllArgsConstructor, @NoArgsConstructor 어노테이션은 롬복(Lombok) 어노테이션입니다. 각각의 어노테이션은 다음과 같은 역할을 수행합니다.

@Data : Getter, Setter, toString 등의 메소드를 자동으로 생성해주는 어노테이션입니다.
@AllArgsConstructor : 모든 필드 값을 인자로 받는 생성자를 자동으로 생성해주는 어노테이션입니다.
@NoArgsConstructor : 매개변수가 없는 기본 생성자를 자동으로 생성해주는 어노테이션입니다.
		따라서, 해당 클래스를 사용하여 HTTP API 요청 결과를 나타낼 때, rtcd와 rtmsg 변수를 사용하여 결과 코드와 메시지를 나타내고, 제네릭 타입인 data 변수를 사용하여 요청 결과로 반환할 데이터를 담을 수 있습니다.*/