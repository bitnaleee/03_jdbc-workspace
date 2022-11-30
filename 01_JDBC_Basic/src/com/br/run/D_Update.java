package com.br.run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class D_Update {

	public static void main(String[] args) {

		// update문 => 처리된 행수 결과 => 트랜잭션 처리
		
		int result = 0;
		
		Connection conn = null;
		Statement stmt = null;
		/*
		String sql = "UPDATE TEST "
				   +    "SET TNAME = '안녕' "
				   +  "WHERE TNO = 4";
		// 공백중요 
		 */
		Scanner sc = new Scanner(System.in);
		System.out.print("수정하고자하는 번호 : ");
		int no = sc.nextInt();
		sc.nextLine();
		System.out.print("수정할 내용 : ");
		String name = sc.nextLine();
		
		String sql = "UPDATE TEST "
				   +    "SET TNAME = '" + name + "' "
				   +  "WHERE TNO = " + no;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			stmt = conn.createStatement();
			
			result = stmt.executeUpdate(sql);
			
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
		
		if (result > 0) {
			System.out.println("성공적으로 수정되었습니다.");
		} else {
			System.out.println("수정하는데 실패했습니다.");
		}
		
	}

}
