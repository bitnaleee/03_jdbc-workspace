package com.br.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.br.controller.ProductController;
import com.br.model.vo.Product;

public class ProductMenu {
		
		Scanner sc = new Scanner(System.in);
		ProductController pc = new ProductController();
		
		public void mainMenu() {
		
			while(true) {
				
				System.out.println("\n< 상품 관리 프로그램 >");
				System.out.println("1. 상품 전체 조회");
				System.out.println("2. 상품 추가");
				System.out.println("3. 상품 수정");
				System.out.println("4. 상품 삭제");
				System.out.println("5. 상품 검색");
				System.out.println("0. 프로그램 종료");
				
				System.out.print("> 메뉴 선택 : ");
				int menu = sc.nextInt();
				sc.nextLine();
				
				switch(menu) {
				case 1 : pc.selectList(); break;
				case 2 : inputProduct(); break;
				case 3 : updateProduct(); break;
				case 4 : pc.deleteProduct(inputProductId()); break;
				case 5 : break;
				case 0 : System.out.println("\n이용해주셔서 감사합니다. 프로그램을 종료합니다."); return;
				default : System.out.println("\n메뉴를 잘못 입력하셨습니다. 다시 입력해주세요."); continue;
				}
				
			}
		
		}
		
		// 2. 상품 추가
		public void inputProduct() {
			
			System.out.println("\n< 상품 추가 >");
			System.out.print("상품 아이디 : ");
			String productId = sc.nextLine();
			System.out.print("상품명 : ");
			String pName = sc.nextLine();
			System.out.print("상품 가격 : ");
			String price = sc.nextLine();
			System.out.print("상품상세정보 : ");
			String description = sc.nextLine();
			System.out.print("재고 : ");
			String stock = sc.nextLine();
			
			pc.inputProduct(productId, pName, price, description, stock);
			
		}
		
		// 3. 상품 수정
		public void updateProduct() {
			
			System.out.println("\n< 상품 수정 >");
			System.out.print("상품 아이디 : ");
			String productId = sc.nextLine();
			System.out.print("수정할 상품명 : ");
			String pName = sc.nextLine();
			System.out.print("수정할 상품 가격 : ");
			String price = sc.nextLine();
			System.out.print("수정할 상품상세정보 : ");
			String description = sc.nextLine();
			System.out.print("수정할 재고 : ");
			String stock = sc.nextLine();
			
			pc.updateProduct(productId, pName, price, description, stock);			
			
		}
		
		// 4. 상품 삭제
		public String inputProductId() {
			System.out.print("\n상품 아이디 입력 : ");
			return sc.nextLine();
		}
		
		
	//------------------------------------- 응답 화면 -------------------------------------------
		
		public void displaySuccess(String message) {
			System.out.println("\n성공 : " + message);
		}
		
		public void displayFail(String message) {
			System.out.println("\n실패 : " + message);
		}
		
		public void displayNoData(String message) {
			System.out.println("\n" + message);
		}
		
		public void displayProductList(ArrayList<Product> list) {
			System.out.println("\n 조회한 결과는 다음과 같습니다.\n");
			
			for(Product p : list) {
				System.out.println(p);
			}
		}
		
		
}
