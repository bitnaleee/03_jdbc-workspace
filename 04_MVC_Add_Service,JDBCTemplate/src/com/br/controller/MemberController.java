package com.br.controller;

import java.util.ArrayList;

import com.br.model.service.MemberService;
import com.br.model.vo.Member;
import com.br.view.MemberMenu;

public class MemberController {

	// 1. 회원 추가
	public void insertMember(String userId, String userPwd, String userName,
							String gender, String age, String email,
							String phone, String address, String hobby) {
		
		Member m = new Member(userId, userPwd, userName, gender, Integer.parseInt(age), email, phone, address, hobby);
		int result = new MemberService().insertMember(m);
		
		if(result > 0) {
			new MemberMenu().displaySuccess("성공적으로 회원추가 되었습니다.");
		} else {
			new MemberMenu().displayFail("회원 추가에 실패했습니다.");
		}
		
	}
	
	// 2. 회원 전체 조회
	public void selectList() {
		
		ArrayList<Member> list = new MemberService().selectList();
		
		if(list.isEmpty()) {
			new MemberMenu().displayNoData("회원 전체 조회 결과가 없습니다.");
		} else {
			new MemberMenu().displayMemberList(list);
		}
		
	}
			
	// 3. 회원 아이디 검색
	public void selectByUserId(String userId) {
		
		Member m = new MemberService().selectByUserId(userId);
		
		if (m == null) {
			new MemberMenu().displayNoData("조회하신 " + userId + "는 없는 아이디입니다.");
		} else {
			new MemberMenu().displayMember(m);
		}
	
	}
	
	// 4. 회원 이름으로 키워드 검색
	public void selectByUserName(String keyword) {
		
		ArrayList<Member> list = new MemberService().selectByUserName(keyword);
		
		if(list.isEmpty()) {
			new MemberMenu().displayNoData(keyword + "에 해당하는 검색 결과가 없습니다.");
		} else {
			new MemberMenu().displayMemberList(list);
		}
		
	}
	
	// 5. 회원 정보 변경
	public void updateMember(String userId, String userPwd, String email, String phone, String address) {
		
		Member m = new Member(userId, userPwd, email, phone, address);
		
		int result = new MemberService().updateMember(m);
		
		if(result > 0) {
			new MemberMenu().displaySuccess("성공적으로 변경되었습니다.");
		} else {
			new MemberMenu().displayFail("정보 변경에 실패했습니다.");
		}
				
	
	}
	
	// 6. 회원 탈퇴
	public void deleteMember(String userId) {
		
		int result = new MemberService().deleteMember(userId);
		
		if(result > 0) {
			new MemberMenu().displaySuccess("성공적으로 회원 탈퇴 되었습니다.");
		} else {
			new MemberMenu().displayFail("회원 탈퇴에 실패했습니다.");
		}
		
	}
			
}
