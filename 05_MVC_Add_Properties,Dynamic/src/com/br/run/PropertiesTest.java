package com.br.run;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesTest {

	public static void main(String[] args) {
		
		// Properties : map계열의 컬렉션으로 키+밸류 세트로 저장
		//              주로 키, 밸류 모두 문자열로 기술
		//              .properties 또는 .xml 파일과 작업
		Properties prop = new Properties();	// 텅 비어있는 상태
		try {
			//prop.load(new FileInputStream("resources/test.properties"));
			prop.loadFromXML(new FileInputStream("resources/test.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(prop);
		System.out.println(prop.getProperty("name"));
		System.out.println(prop.getProperty("classRoom"));

	}

}
