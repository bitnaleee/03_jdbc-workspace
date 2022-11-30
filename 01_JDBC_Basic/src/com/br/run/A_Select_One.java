package com.br.run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.br.model.vo.Test;

public class A_Select_One {
	
	public static void main(String[] args) {
		
		/*
		 * * JDBC용 객체
		 * - DriverManager : Connection 객체를 생성하기 위한 객체
		 * - Connection : DB에 접속해서 DB의 연결정보를 담고있는 객체 (+ Statement 객체를 생성하기 위한 객체)
		 * - [Prepared]Statement : 연결된 DB에 sql문을 전달해서 실행하고 그 결과를 받아내는 객체 ***
		 * - ResultSet : select문 실행 후 조회된 결과물들이 담겨있는 객체 
		 * 
		 * * JDBC 과정(순서 중요)
		 * 1) jdbc driver 등록 : 해당 DBMS(오라클)가 제공하는 클래스 등록
		 * 2) Connection 생성 : 연결하고자 하는 DB정보를 제시해서 해당 DB와 연결된 Connection 생성
		 * 3) Statement 생성 : Connection 객체를 이용해서 생성 (sql문 실행 및 결과받는 객체)
		 * 4) sql문 전달하면서 실행 : Statement 객체를 이용해서 sql문 실행 
		 * 5) 결과 받기 
		 * 		> SELECT문 실행 => ResultSet객체 (조회된 데이터들이 담겨있음)	=> 6_1)
		 * 		>   DML 문 실행 => int (처리된 행 수)						=> 6_2)
		 * 
		 * 6_1) ResultSet에 담겨있는 데이터들을 하나씩 뽑아서 vo객체에 주섬주섬 옮겨 담기 [+ ArrayList에 차곡차곡 담기]
		 * 6_2) 트랜잭션 처리 (성공시 commit, 실패시 rollback)
		 * 
		 * 7) 다 사용한 JDBC용 객체들 반드시 자원 반납
		 */
		
		
		// * 내 pc(localhost) DB상 JDBC계정에 있는 TEST테이블에 1번 데이터 조회해보기
		//   SELECT TNO, TNAME, TDATE FROM TEST WHERE TNO = 1; (한 행 조회될 SQL문)
		
		// select문 => 실행결과 ResultSet에 먼저 받기 => ResultSet으로부터 데이터 하나씩 뽑아서 자바객체에 세팅
		
		
		// 조회된 데이터들을 담을 자바 객체 세팅 
		Test t = null;
		// JDBC에 필요한 객체 미리 세팅
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		// 앞으로 실행할 sql문 (문장 끝에 세미콜론 없어야됨****)
		//String sql = "SELECT TNO, TNAME, TDATE FROM TEST WHERE TNO = 1";
		
		// 사용자가 입력한 값으로 조회하기
		Scanner sc = new Scanner(System.in);
		System.out.print("조회하고자하는 번호 입력 : ");
		int no = sc.nextInt();
		String sql = "SELECT TNO, TNAME, TDATE FROM TEST WHERE TNO = " + no;
		
		try {
			// 1) jdbc driver 등록 
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// ClassNotFoundException 발생시 : ojdbc6.jar 파일을 추가안했을 경우 / 오타없는지 확인 
			
			// 2) Connection 객체 생성 == DB에 연결 (url, 계정명, 비밀번호)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			// 3) Statement 객체 생성 (sql문 실행을 위한 객체)
			stmt = conn.createStatement();
			
			// 4, 5) sql문을 전달하면서 실행 후 결과(조회된 결과) 받기 => ResultSet 객체에
			rset = stmt.executeQuery(sql);
			// 내가 실행할 sql문이 select문일 경우 => stmt.executeQuery(쿼리문) : ResultSet
			// 내가 실행할 sql문이 dml 문일 경우 => stmt.executeUpdate(dml문) : int
			
			// 6) ResultSet에 담겨있는 데이터들을 하나씩 뽑아서 vo객체의 각 필드에 주섬주섬 옮겨 담기
			if(rset.next()) { // 행 커서 움직여주는 역할, 뿐만 아니라 해당 행이 있으면 true 없으면 false 반환 => 조회된 행이 있을 경우
				
				// 현재 rset의 커서가 가리키고 있는 한 행의 데이터들을 싹다 뽑아서 Test객체 주섬주섬 담기
				// rset으로 부터 "어떤 컬럼"의 값을 "어떤 타입"으로 뽑을 건지 제시 
				// rset.getInt(컬럼명), rset.getString(컬럼명), rset.getDate(컬럼명)
				
				// - 기본생성자로 생성 후 setter메소드
				/*
				t = new Test();
				t.setTestNo(rset.getInt("TNO"));
				t.setTestName(rset.getString("tname")); // 대소문자 상관 없음 (컬럼명이기 때문)
				t.setTestDate(rset.getDate(3)); // 컬럼 순번도 제시 가능 
				*/
				
				// - 매개변수생성자로 생성과 동시에 초기화 
				t = new Test(rset.getInt("TNO"), rset.getString("tname"), rset.getDate(3));
				
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				// 7) 다 쓴 JDBC용 객체 반납 (생성된 역순으로 반납)
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// 조회된 결과 출력
		if(t == null) { // 생성안됨 == 조회결과가 없었을 경우 
			System.out.println("조회결과가 없습니다.");
		}else { // 생성됨 == 조회결과가 있었을 경우 
			System.out.println(t);
		}
		
	}

}

