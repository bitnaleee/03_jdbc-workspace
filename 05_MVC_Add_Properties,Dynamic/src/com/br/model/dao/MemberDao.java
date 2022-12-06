package com.br.model.dao;

import static com.br.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import com.br.model.vo.Member;

public class MemberDao {
	
	/*
	 * 기존의 방식 : dao 클래스에서 사용자가 요청할 때마다 실행되는 sql문을 자바코드상에 명시적으로 작성 => 정적코딩방식
	 * > 문제점 : sql문을 수정해야될 경우 자바코드를 수정해야됨 => 수정된 내용을 반영시키고자 할 경우 프로그램 재구동해야함
	 * > 해결방식 : sql문들을 별도로 관리하는 외부 파일(.xml)을 만들어서 "실시간"으로 그 파일에 기록된 sql문을 읽어들여서 실행될 수 있게 => 동적코딩방식
	 */
	
	private Properties prop = new Properties();
	
	// 사용자가 어떤 서비스 요청할 때마다 결국 new MemberDao().xxxxx(); 호출
	public MemberDao() {
			try {
				prop.loadFromXML(new FileInputStream("resources/query.xml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	// 1. 회원 추가
	public int insertMember(Connection conn, Member m) {

		// insert문 => 처리된행수(int)
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertMember");
		
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
		
		String sql = prop.getProperty("selectList");
		
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
		
		String sql = prop.getProperty("selectByUserId");
		
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
		
		String sql = prop.getProperty("selectByUserName");
		
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
		
		String sql = prop.getProperty("updateMember");
		
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
		
		String sql = prop.getProperty("deleteMember");
		
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
	
	// 7. 로그인
	public Member loginMember(Connection conn, String userId, String userPwd) {
		
		Member m = null;
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("loginMember");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);
			
			rset = pstmt.executeQuery();
			
		
			if(rset.next()) {
				m = new Member(rset.getString("user_name"),
						       rset.getString("email"),
						       rset.getString("phone"),
						       rset.getString("address"),
						       rset.getString("hobby"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return m;
		
	}
	
	
}