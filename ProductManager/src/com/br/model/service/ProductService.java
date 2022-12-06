package com.br.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import static com.br.common.JDBCTemplate.*;
import com.br.model.dao.ProductDao;
import com.br.model.vo.Product;

public class ProductService {
	
	// 1. 상품 전체 조회
	public ArrayList<Product> selectList() {
		
		Connection conn = getConnection();
		
		ArrayList<Product> list = new ProductDao().selectList(conn);
		
		close(conn);
		
		return list;
		
	}
	
	// 2. 상품 추가
	public int inputProduct(Product p) {
		
		Connection conn = getConnection();
		
		int result = new ProductDao().inputProduct(conn, p);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
		
	}
	
	// 3. 상품 수정
	public int updateProduct(Product p) {
		
		Connection conn = getConnection();
		
		int result = new ProductDao().updateProduct(conn, p);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
	
	// 4. 상품 삭제
	public int deleteProduct(String productId) {
		
		Connection conn = getConnection();
		
		int result = new ProductDao().deleteProduct(conn, productId);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
		
	}
	
	
	
	
	
	
	
	

}
