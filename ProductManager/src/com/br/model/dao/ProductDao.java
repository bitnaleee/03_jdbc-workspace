package com.br.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.br.common.JDBCTemplate.*;
import com.br.model.vo.Product;

public class ProductDao {
	
	// 1. 상품 전체 조회
	public ArrayList<Product> selectList(Connection conn) {
		
		ArrayList<Product> list = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM PRODUCT";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				list.add(new Product(rset.getString("product_id"),
									 rset.getString("p_name"),
									 rset.getInt("price"),
									 rset.getString("description"),
									 rset.getInt("stock")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	
	}
	
	// 2. 상품 추가
	public int inputProduct(Connection conn, Product p) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO PRODUCT VALUES(?, ?, ?, ?, ?)";
			
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, p.getProductId());
			pstmt.setString(2, p.getpName());
			pstmt.setInt(3, p.getPrice());
			pstmt.setString(4, p.getDescription());
			pstmt.setInt(5, p.getStock());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
	}
	
	// 3. 상품 수정
	public int updateProduct(Connection conn, Product p) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE PRODUCT SET P_NAME=?, PRICE=?, DESCRIPTION=?, STOCK=? WHERE PRODUCT_ID=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, p.getpName());
			pstmt.setInt(2, p.getPrice());
			pstmt.setString(3, p.getDescription());
			pstmt.setInt(4, p.getStock());
			pstmt.setString(5, p.getProductId());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
	}
	
	// 4. 상품 삭제
	public int deleteProduct(Connection conn, String productId) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM PRODUCT WHERE PRODUCT_ID = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, productId);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
