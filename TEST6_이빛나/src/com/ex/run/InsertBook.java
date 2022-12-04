package com.ex.run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import com.ex.model.vo.Book;

public class InsertBook {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("< 추가하는 도서 정보 >");
		
		System.out.print("도서명 : ");
		String title = sc.nextLine();
		
		System.out.print("저자 : ");
		String author = sc.nextLine();
		
		System.out.print("출판 : ");
		String publisher = sc.nextLine();
		
		System.out.print("가격 : ");
		String price = sc.nextLine();
		
		System.out.print("재고수량 : ");
		String stock = sc.nextLine();
		
		Book b = new Book(title, author, publisher, Integer.parseInt(price), Integer.parseInt(stock));
		
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO BOOK VALUES(SEQ_BOOKNO.NEXTVAL, ?, ?, ?, ?, ?, SYSDATE)";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, b.getTitle());
			pstmt.setString(2, b.getAuthor());
			pstmt.setString(3, b.getPublisher());
			pstmt.setInt(4, b.getPrice());
			pstmt.setInt(5, b.getStock());
			
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
		
		if(result > 0) {
			System.out.print("성공적으로 추가되었습니다.");
		} else {
			System.out.print("도서 추가에 실패했습니다.");
		}
	}

	
	
	
	
	
}
