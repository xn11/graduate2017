package test;

import org.slf4j.Logger;

public class LoggerTest {
	public LoggerTest(){
		new Foo().method();
	}

	public static void main(String[] args) {

		org.apache.log4j.Logger log=LoggerUtils.getLogger(Foo.class, "log1.txt", true);
		log.info("ssssssssssssssssssss");
	}
	
	class Foo{
		public Foo(){
			System.out.println("FOO");
		}
		
		public void method(){
			System.out.println("qqq");
		}
	}

}
