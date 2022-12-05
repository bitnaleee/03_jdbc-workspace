package com.br.model.dao;

import static com.br.common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.br.model.vo.Member;

public class MemberDao {
	
	// 1. 회원 추가
	public int insertMember(Connection conn, Member m) {
		
		// insert문 => 처리된행수(int)
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
		
		try {
			// 3) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);	// 미완성된 sql문
			
			pstmt. setString(1, m.getUserId());
			pstmt. setString(2, m.getUserPwd());
			pstmt. setString(3, m.getUserName());
			pstmt. setString(4, m.getGender());
			pstmt. setInt(5, m.getAge());
			pstmt. setString(6, m.getEmail());
			pstmt. setString(7, m.getPhone());
			pstmt. setString(8, m.getAddress());
			pstmt. setString(9, m.getHobby());
			
			// 4,5) sql문 실행 후 결과받기
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/*
			try {
				if(pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// conn은 아직 반납해서는 안됨
			*/
			/*JDBCTemplate.*/close(pstmt);
		}
		
		return result;
	}
	
	// 2. 회원 전체 조회
	public ArrayList<Member> selectList(Connection conn) {
		
		// select문(여러행) => ResultSet => ArrayList
		ArrayList<Member> list = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER";
		
		try {
			pstmt = conn.prepareStatement(sql);	// 완성된 sql문
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				// 한 행 => Member 객체 => list에 추가
				list.add(new Member(rset.getInt("user_no"),
						            rset.getString("user_id"),
						            rset.getString("user_pwd"),
						            rset.getString("user_name"),
						            rset.getString("gender"),
						            rset.getInt("age"),
						            rset.getString("email"),
						            rset.getString("phone"),
						            rset.getString("address"),
						            rset.getString("hobby"),
						            rset.getDate("enroll_date")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/*JDBCTemplate.*/close(rset);
			/*JDBCTemplate.*/close(pstmt);
		}
		
		return list;
	}
	
	// 3. 회원 아이디 검색
	public Member selectByUserId(Connection conn, String userId) {
		
		// select문(한행) => ResultSet => Member
		Member m = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USER_ID = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				m = new Member(rset.getInt("user_no"),
					           rset.getString("user_id"),
					           rset.getString("user_pwd"),
					           rset.getString("user_name"),
					           rset.getString("gender"),
					           rset.getInt("age"),
					           rset.getString("email"),
					           rset.getString("phone"),
					           rset.getString("address"),
					           rset.getString("hobby"),
					           rset.getDate("enroll_date"));
			} 
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return m;
	}

	// 4. 회원 이름으로 키워드 검색
	public ArrayList<Member> selectByUserName(Connection conn, String keyword) {
		
		// select문(여러행) => ResultSet => ArrayList<Member>
		ArrayList<Member> list = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USER_NAME LIKE '%' || ? ||'%'";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Member(rset.getInt("user_no"),
						            rset.getString("user_id"),
						            rset.getString("user_pwd"),
						            rset.getString("user_name"),
						            rset.getString("gender"),
						            rset.getInt("age"),
						            rset.getString("email"),
						            rset.getString("phone"),
						            rset.getString("address"),
						            rset.getString("hobby"),
						            rset.getDate("enroll_date")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}
	
	// 5. 회원 정보 변경
	public int updateMember(Connection conn, Member m) {
		
		// update문 => 처리된 행수
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE MEMBER SET USER_PWD=?, EMAIL=?, PHONE=?, ADDRESS=? WHERE USER_ID = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, m.getUserPwd());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getUserId());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	
	// 6. 회원 탈퇴
	public int deleteMember(Connection conn, String userId) {
		
		// delete문 => 처리된행수(int) => 트랜잭션처리
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM MEMBER WHERE USER_ID = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
	}
	
	
}