package com.br.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.br.model.vo.Member;

// Dao : DB에 직접 접근해서 사용자의 요청에 맞는 sql문 실행 후 결과 받기 (JDBC과정)
//       결과를 Controller로 반환
public class MemberDao {
	
	/*
	 * * Statement와 PreparedStatement의 특징
	 * - 둘 다 sql문을 실행하고 결과를 받아내는 객체 (둘 중에 하나를 이용하면 됨)
	 * - Statement가 PreparedStatement의 부모 (상속구조)
	 * 
	 * * Statement와 PreparedStatement의 차이점
	 * - Statement 같은 경우 sql문을 바로 전달하면서 실행시키는 객체
	 *   (즉, sql문을 완성형태로 만들어 둬야됨!! == 사용자가 입력한 값들이 다 채워진 형태로)
	 *   
	 *   1) Connection 객체를 통해 Statement 객체 생성
	 *   	> stmt = conn.createStatement();
	 *   2) Statement 객체를 통해 sql문 실행 결과 받기
	 *   	> 결과 = stmt.secuteXXX(완성된 sql문);
	 *   
	 * - PreparedStatement 같은 경우 "미완성된 sql문"을 잠시 보관해둘 수 있는 객체
	 *   (즉, 사용자가 입력한 값들을 채워두지 않고 각각 들어갈 공간만 미리 확보해놔도 됨)
	 *   
	 *   1) Connection 객체를 통해 PreparedStatement 객체 생성
	 *   	> pstmt = conn.prepareStatement([미]완성된 sql문);
	 *   2) pstmt에 담긴 sql문이 미완성된 상태일 경우 우선은 완성시켜야 됨
	 *   	> pstmt.setXXX(1, "대체할값");
	 *   	> pstmt.setXXX(2, "대체할값");
	 *   			...
	 *   3) 해당 완성된 sql문 실행 결과 받기
	 *   	> 결과 = pstmt.executeXXX();
	 */
	
	// 1. 회원 추가
	public int insertMember(Member m) {
		
		// insert문 => 처리된 행수 => 트랜잭션 처리
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// 실행할 sql문 (미완성된 형태로 둘 수 있음)
		// INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, 'XXXX', 'XXXXX', 'XXX', 'X', XX, 'XXXXX', 'XXXXXX', 'XXXXX', 'XXXXXX', SYSDATE)
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
		// 미리 사용자가 입력한 값들이 들어갈 수 있게 공간 확보 (? == 홀더)만 해두면 됨
		
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2) Connection 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			// 3) PreparedStatement 생성
			pstmt = conn.prepareStatement(sql);	// 애초에 객체 생성시 sql문을 담은 채로 생성 (하필 미완성된 sql문)
			
			// 미완성된 sql문이 담겨 있을 경우
			// > 빈 공간(?)을 실제값 (사용자가 입력한 값)으로 채워준 후 
			// pstmt.setString(홀더순번, 대체할값); => '대체할값' (양옆에 홀따옴표 감싸준 상태로 들어감)
			// pstmt.setInt(홀더순번, 대체할값);	   =>  대체할값
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby());
			
			//4, 5) sql문 실행 및 결과 받기
			result = pstmt.executeUpdate();
			
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
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
		
		return result;
	}
	
	// 2. 회원 전체 조회
	public ArrayList<Member> selectList() {
		
		// select문(여러행) => ResultSet => ArrayList담기
		ArrayList<Member> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER"; // 애초에 완성된 SQL문
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			pstmt = conn.prepareStatement(sql); // 애초에 완성된 sql문을 담음 => 곧바로 실행
			
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
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return list;
	}
	
	// 3. 회원 아이디 검색
	public Member selectByUserId(String userId) {
		
		// select문(한행) => ResultSet => Member
		Member m = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USER_ID = ?";
		
		try {
			// 1)
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			// 3)
			pstmt = conn.prepareStatement(sql); // 미완성된 sql문
			
			pstmt.setString(1, userId); // 완성된 형태로
			
			// 4,5)
			rset = pstmt.executeQuery();
			// 6)
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
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return m;
	}
	
	// 4. 회원 이름으로 키워드 검색
	public ArrayList<Member> selectByUserName(String keyword) {
		
		// select문(여러행) => ResultSet => ArrayList
		ArrayList<Member> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// SELECT * FROM MEMBER WHERE USER_NAME LIKE '%XXX%'
		
		//String sql = "SELECT * FROM MEMBER WHERE USER_NAME LIKE '%?%'";
		
		// 위의 sql문 제시한 후 pstmt.setString(1, "강");
		// 이 때 완성될 sql문 : SELECT * FROM MEMBER WHERE USER_NAME LIKE '%'강'%'
		
		// 해결방법1.
		//String sql = "SELECT * FROM MEMBER WHERE USER_NAME LIKE ?";
		// 해결방법2.
		String sql = "SELECT * FROM MEMBER WHERE USER_NAME LIKE '%' || ? || '%'";
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			pstmt = conn.prepareStatement(sql); //미완성된 sql문
			
			// 해결방법1의 sql문일 경우
			//pstmt.setString(1, "%" + keyword + "%"); // "%강%" => '%강%'
			
			// 해결방법2의 sql문일 경우
			pstmt.setString(1, keyword);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {list.add(new Member(rset.getInt("user_no"),
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
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				rset.close();
				pstmt.close();
				conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return list;
	}
	
	// 5. 회원 정보 변경
	public int updateMember(Member m) {
		
		// update문 => 처리된 행수(int) => 트랜잭션 처리
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE MEMBER SET USER_PWD=?, EMAIL=?, PHONE=?, ADDRESS=? WHERE USER_ID = ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			pstmt = conn.prepareStatement(sql); // 하필 담은 sql문이 미완성된 sql문
			
			// 실행전 완성시키기
			pstmt.setString(1, m.getUserPwd());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getUserId());
			
			result = pstmt.executeUpdate();
			
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
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return result; 
	}
	
	// 6. 회원 탈퇴
	public int deleteMember(String userId) {
		
		// delete문 => 처리된행수(int) => 트랜잭션 처리
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM MEMBER WHERE USER_ID = ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			pstmt = conn.prepareStatement(sql); // 미완성된 sql문
			
			// 완성시키기
			pstmt.setString(1, userId);
			
			result = pstmt.executeUpdate();
			
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
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return result;
	}
	
}