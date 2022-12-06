package com.br.controller;

import java.util.ArrayList;

import com.br.model.service.ProductService;
import com.br.model.vo.Product;
import com.br.view.ProductMenu;

public class ProductController {
	
	// 1. 상품 전체 조회
	public void selectList() {
		
		ArrayList<Product> list = new ProductService().selectList();
		
		if(list.isEmpty()) {
			new ProductMenu().displayNoData("조회한 결과가 없습니다.");
		} else {
			new ProductMenu().displayProductList(list);
		}
		
	}
	
	// 2. 상품 추가
	public void inputProduct(String productId, String pName, String price, String description, String stock) {
		
		Product p = new Product(productId, pName, Integer.parseInt(price), description, Integer.parseInt(stock));
		
		int result = new ProductService().inputProduct(p);
		
		if(result > 0) {
			new ProductMenu().displaySuccess("상품추가 성공적으로 되었습니다.");
		} else {
			new ProductMenu().displayFail("상품추가가 되지 않았습니다.");
		}
		
	}
	
	// 3. 상품 수정
	public void updateProduct(String productId, String pName, String price, String description, String stock) {
		
		Product p = new Product();
		p.setProductId(productId);
		p.setpName(pName);
		p.setPrice(Integer.parseInt(price));
		p.setDescription(description);
		p.setStock(Integer.parseInt(stock));
		
		int result = new ProductService().updateProduct(p);
		
		if(result > 0) {
			new ProductMenu().displaySuccess("상품수정이 성공적으로 되었습니다. ");
		} else {
			new ProductMenu().displayFail("상품수정이 되지 않았습니다.");
		}
		
	}
	
	// 4. 상품 삭제
	public void deleteProduct(String userId) {
		
		int result = new ProductService().deleteProduct(userId);
		
		if(result > 0) {
			new ProductMenu().displaySuccess("성공적으로 상품 삭제되었습니다.");
		} else {
			new ProductMenu().displayFail("상품삭제에 실패했습니다.");
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
