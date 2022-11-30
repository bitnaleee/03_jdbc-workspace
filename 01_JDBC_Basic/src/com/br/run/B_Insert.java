package com.br.run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class B_Insert {

	public static void main(String[] args) {

		// insert문 => 처리된 행수 (int) => 트랜잭션 처리
		
		// 실행결과를 보관할 변수
		int result = 0;
		
		// JDBC 과정중에 필요한 객체
		Connection conn = null;
		Statement stmt = null;
		
		// 실행할 sql문
		//String sql = "INSERT INTO TEST VALUES(SEQ_TESTNO.NEXTVAL, '홍길동', SYSDATE)";
		
		Scanner sc = new Scanner(System.in);
		System.out.print("추가하고자하는 이름 : ");
		String name = sc.nextLine();
		String sql = "INSERT INTO TEST VALUES(SEQ_TESTNO.NEXTVAL, '" + name + "', SYSDATE)";
		
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 여기서 try/catch
			
			// 2) Connection 생성 == DB 접속
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			// 여기서 try/catch 2번째
			
			// 3) sql 실행을 위한 Statement 생성
			stmt = conn.createStatement();
			
			// 4, 5) sql문 실행 및 결과받기
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
				// 7) 다 쓴 자원반남
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		if(result > 0) {
			System.out.println("성공적으로 추가되었습니다.");
		} else {
			System.out.println("추가하는데 실패했습니다.");
		}
		
	}

}
