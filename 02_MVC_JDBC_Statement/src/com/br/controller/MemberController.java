package com.br.controller;

import java.util.ArrayList;

import com.br.model.dao.MemberDao;
import com.br.model.vo.Member;
import com.br.view.MemberMenu;

// Controller : 사용자가 요청한 기능에 대해서 처리
//				해당 메소드로 전달된 데이터 [가공처리] => Dao 메소드 호출
//              Dao로부터 결과 받기 성공/실패 판단 후 => 응답뷰(View)
public class MemberController {

	/**
	 * 사용자의 회원 추가 요청을 처리해주는 메소드
	 * @param userId ~ hobby - 사용자가 입력했던 정보들이 담겨있음
	 */
	public void insertMember(String userId, String userPwd, String userName, 
							 String gender, String age, String email,
							 String phone, String address, String hobby) {
		
		// 전달값들을 어딘가(Member객체)에 주섬주섬 담아서 통째로 dao로 전달
		// 방법1) 기본생성자로 생성 => setter메소드
		// 방법2) 매개변수 생성자로 생성과 동시에 초기화
		Member m = new Member(userId, userPwd, userName, gender, Integer.parseInt(age), email, phone, address, hobby);
		
		int result = new MemberDao().insertMember(m);
		
		// 돌아오는 값으로 성공인지 실패인지 판단
		if(result > 0) {
			new MemberMenu().displaySuccess("성공적으로 회원 추가되었습니다.");
		} else {
			new MemberMenu().displayFail("회원 추가를 실패했습니다.");
		}
		
	}
	
	public void selectList() {
		
		ArrayList<Member> list = new MemberDao().selectList();
		
		// 조회결과가 있는지 없는지 판단 후 => 사용자가 보게 될 응답뷰 지정
		if(list.isEmpty()) { // 조회결과가 없을 경우
			new MemberMenu().displayNoData("전체 조회 결과가 없습니다.");
		} else { // 조회결과가 있을 경우
			new MemberMenu().displayMemberList(list);
		}
	
	}
	
	public void selectByUserId(String userId) {
		Member m = new MemberDao().selectByUserId(userId);
		
		if(m == null) { // 검색결과가 없을 경우
			new MemberMenu().displayNoData(userId + "에 대한 검색 결과가 없습니다.");
		} else { // 검색결과가 있을 경우
			new MemberMenu().displayMember(m);
		}
		
	}
	
	
	
	
	
	
}
