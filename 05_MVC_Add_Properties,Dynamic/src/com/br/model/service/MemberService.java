package com.br.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import static com.br.common.JDBCTemplate.*;

import com.br.model.dao.MemberDao;
import com.br.model.vo.Member;

public class MemberService {
	
	// 1. 회원 추가
	public int insertMember(Member m) {
		
		// 1) jdbc driver 등록
		// 2) Connection 객체 생성 (DB에 접속)
		/*
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		*/
		Connection conn = /*JDBCTemplate.*/getConnection();
		
		int result = new MemberDao().insertMember(conn, m);
		
		// 6) 트랜잭션 처리
		if(result > 0) {
			// commit 처리
			/*
			try {
				if(conn != null && !conn.isClosed()); {
					conn.commit();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			*/
			/*JDBCTemplate.*/commit(conn);
		} else {
			// rollback 처리
			/*
			try {
				if(conn != null & !conn.isClosed()) {
					conn.rollback();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			*/
			/*JDBCTemplate.*/rollback(conn);
		}
		// conn 자원반납
		/*
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
		/*JDBCTemplate.*/close(conn);
		
		return result;
	}
	
	// 2. 회원 전체 조회
	public ArrayList<Member> selectList() {
		
		Connection conn = /*JDBCTemplate.*/getConnection();
		
		ArrayList<Member> list = new MemberDao().selectList(conn);
		
		/*JDBCTemplate.*/close(conn);
		
		return list;
	}
	
	// 3. 회원 아이디 검색
	public Member selectByUserId(String userId) {
		
		Connection conn = getConnection();
		
		Member m = new MemberDao().selectByUserId(conn, userId);
		
		close(conn);
		
		return m;
		
	}
	
	// 4. 회원 이름으로 키워드 검색
	public ArrayList<Member> selectByUserName(String keyword) {
		
		Connection conn = getConnection();
		
		ArrayList<Member> list = new MemberDao().selectByUserName(conn, keyword);
		
		close(conn);
		
		return list;
	}
	
	
	// 5. 회원 정보 변경
	public int updateMember(Member m) {
		Connection conn = getConnection();
		int result = new MemberDao().updateMember(conn, m);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		return result;
	}

	
	// 6. 회원 탈퇴
	public int deleteMember(String userId) {
		Connection conn = getConnection();
		int result = new MemberDao().deleteMember(conn, userId);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		return result;
		
	}
	
	// 7. 로그인
	public Member loginMember(String userId, String userPwd) {
		
		Connection conn = getConnection();
		
		Member m = new MemberDao().loginMember(conn, userId, userPwd);
		
		close(conn);
		
		return m;
		
	}
	
}
