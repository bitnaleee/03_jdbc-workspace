package com.ex.run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import com.ex.model.vo.Book;

public class UpdateBook {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("< 도서 정보 변경 >");
		
		System.out.print("수정하고자 하는 도서번호 : ");
		int no = sc.nextInt();
		
		sc.nextLine();
		
		System.out.print("변경할 도서명 : ");
		String title = sc.nextLine();
		
		System.out.print("변경할 저자명 : ");
		String author = sc.nextLine();
		
		System.out.print("변경할 출판사 : ");
		String publisher = sc.nextLine();
		
		System.out.print("변경할 가격 : ");
		String price = sc.nextLine();
		
		System.out.print("변경할 재고수량 : ");
		String stock = sc.nextLine();
		
		Book b = new Book(title, author, publisher, Integer.parseInt(price), Integer.parseInt(stock));
		
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE BOOK SET TITLE=?, AUTHOR=?, PUBLISHER=?, PRICE=?, STOCK=? WHERE BOOK_NO = ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, b.getTitle());
			pstmt.setString(2, b.getAuthor());
			pstmt.setString(3, b.getPublisher());
			pstmt.setInt(4, b.getPrice());
			pstmt.setInt(5, b.getStock());
			pstmt.setInt(6, no);
			
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
			System.out.print("성공적으로 수정했습니다.");
		} else {
			System.out.print("도서 수정에 실패했습니다.");
		}

	}

}
