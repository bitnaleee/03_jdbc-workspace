package com.br.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.br.model.vo.Member;

// Dao : DB에 직접 접근해서 사용자의 요청에 맞는 sql문 실행 후 결과 받기 (JDBC과정)
//       결과를 Controller로 반환
public class MemberDao {

	public int insertMember(Member m) {
		
		// insert문 => 처리된행수(int) => 트랜잭션처리
		
		// 필요한 변수들 세팅
		int result = 0;
		Connection conn = null;
		Statement stmt = null;
		
		// 실행할 sql문 (완성형태로 만들기 => Statement : sql문이 완성형여야만됨)
		// INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, 'XXX', 'XXXX', 'XXX', 'X', XX, 'XXXXX', 'XXXXXXXX', 'XXXX', SYSDATE)
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, "
				                      + "'" + m.getUserId()   + "', " 
			                          + "'" + m.getUserPwd()  + "', "
			                          + "'" + m.getUserName() + "', "
			                          + "'" + m.getGender()   + "', "
			                          	    + m.getAge()      + ", "
			                          + "'" + m.getEmail()    + "', "
			                          + "'" + m.getPhone()    + "', "
			                          + "'" + m.getAddress()  + "', "
			                          + "'" + m.getHobby()    + "', SYSDATE)";
		//System.out.println(sql);
		
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2) Connection 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			// 3) Statement 생성
			stmt = conn.createStatement();
			// 4,5) sql문 실행 후 결과 받기
			result = stmt.executeUpdate(sql);
			// 6) 트랜잭션 처리
			if(result > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return result;	// 1 아니면 0 반환
	
	}
	
	public ArrayList<Member> selectList() {
		// select문(여러행 조회) => ResultSet객체 => ArrayList에 차곡차곡 담기
		
		ArrayList<Member> list = new ArrayList<>();	//텅 빈 리스트
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		// 실행할 sql문
		String sql = "SELECT * FROM MEMBER";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(sql);
			
			while(rset.next()) {
				
				//현재 rset의 커서가 기리키고 있는 해당 행의 모든 컬럼값을 뽑아서 Member객체 주섬주섬 담기
				Member m = new Member(rset.getInt("user_no"),
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
				list.add(m);
				
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return list;	// 텅 빈 리스트 || 조회된 결과가 담겨있는 리스트
		
	}
	
	public Member selectByUserId(String userId) {
		
		// select문(한행) => ResultSet객체 => Member객체
		
		Member m = null;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		// "SELECT * FROM MEMBER WHERE USER_ID = 'XXXXX'"
		String sql = "SELECT * FROM MEMBER WHERE USER_ID = '" + userId + "'";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(sql);
			
			if(rset.next()) {
				// 조회됐다면 해당 조회된 한 행의 모든 컬럼값들을 뽑아서 Member객체에 담기
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
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return m;	// 조회결과가 없을 경우 null || 조회결과가 있을 경우 생성된 Member객체
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
