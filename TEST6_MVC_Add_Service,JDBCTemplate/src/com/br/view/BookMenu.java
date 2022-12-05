package com.br.view;

import java.util.Scanner;

import com.br.controller.BookController;

public class BookMenu {

		private Scanner sc = new Scanner(System.in);
		private BookController bc = new BookController();
		
		public void mainMenu() {
			
			while(true) {
				
				System.out.println("< 도서 관리 프로그램 >");
				System.out.println("1. 도서 추가");
				System.out.println("2. 도서 전체 조회");
				System.out.println("3. 출판사 검색");
				System.out.println("4. 도서 제목으로 키워드 검색");
				System.out.println("5. 도서 정보 변경");
				System.out.println("6. 도서 삭제");
				System.out.println("0. 프로그램 종료");
				
				System.out.print("> 메뉴 선택 : ");
				int menu = sc.nextInt();
				sc.nextLine();
				
				switch(menu) {
				case 1 : inputBook(); break;
				case 2 : break;
				case 3 : break;
				case 4 : break;
				case 5 : break;
				case 6 : break;
				case 0 : System.out.println("\n이용해주셔서 감사합니다. 프로그램을 종료합니다."); return;
				default : System.out.println("\n메뉴를 잘못 입력하셨습니다. 다시 입력해주세요."); continue;
				}
				
			}
		
		}
		
		// 1. 도서 추가
		public void inputBook() {
			
			System.out.println("\n< 도서 추가 >");
			
			System.out.print("도서 제목 : ");
			String title = sc.nextLine();
			
			System.out.print("도서 작가 : ");
			String author = sc.nextLine();
			
			System.out.print("출판사 : ");
			String publisher = sc.nextLine();
			
			System.out.print("가격 : ");
			String price = sc.nextLine();
			
			System.out.print("재고 : ");
			String stock = sc.nextLine();
			
			bc.insertBook(title, author, publisher, price, stock);
		}
		
	
	
	
}
