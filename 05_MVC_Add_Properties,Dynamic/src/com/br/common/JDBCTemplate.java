package com.br.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

// 공통 템플릿 (매번 반복적으로 작성될 코드를 메소드로 정의)
public class JDBCTemplate {

	// 1. Connection 객체 생성(DB와 접속)후 해당 Connection 반환해주는 메소드
	public static Connection getConnection() {
		
		/*
		 * 기존의 방식 : jdbc driver구문, 접속할 db의 url, 계정명, 비번이 자바코드내에 명시적으로 작성 => 정적코딩방식
		 * 
		 * > 문제점 : dbms가 변경되었을 경우, 접속할 db의 url, 계정명/비번이 변경될 경우 => 자바소스코드 수정
		 *          => 게다가 수정된 내용을 반영시키고자 한다면 프로그램 재 구동해야됨 (프로그램이 비정상적으로 종료됐다가 다시 구동)
		 *          => 사용자 입장에서 프로그램이 멈춰지는 상황
		 * 
		 * > 해결방식 : db관련된 구문들을 별도로 관리하는 외부 파일(.properties)을 만들어서 관리
		 *            외부파일로부터 읽어들여서 반영이 되도록 코드 짜놓으면 됨 => 동적코딩방식
		 *            => 수정될 일이 있다면 자바코드가 아닌 외부파일을 열어서 수정하면 됨 => 프로그램을 재구동시킬 필요 없음
		 */
		
		Properties prop = new Properties();
		Connection conn = null;
		try {
			prop.load(new FileInputStream("resources/driver.properties"));
			
			Class.forName(prop.getProperty("driver"));
			conn = DriverManager.getConnection(prop.getProperty("url"),
					                           prop.getProperty("username"), 
					                           prop.getProperty("password"));
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	// 2. commit 처리해주는 메소드 (Connection 전달받아서)
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()); {
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 	
	}
	
	// 3. rollback 처리해주는 메소드 (Connection 전달받아서)
	public static void rollback(Connection conn) {
		try {
			if(conn != null & !conn.isClosed()) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// 4. Connection 객체 전달받아서 반납시켜주는 메소드
	public static void close(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 5. Statement관련 객체 전달 받아서 반납시켜주는 메소드
	public static void close(Statement stmt) {
		
		try {
			if(stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// 6. ResultSet 객체 전달 받아서 반납시켜주는 메소드
	public static void close(ResultSet rset) {
		try {
			if(rset != null && !rset.isClosed()) {
				rset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
