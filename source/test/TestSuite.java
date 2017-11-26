package test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestSuite {
	
	@Test
	void test(){
	    assertEquals( 2+2, 4);
	}
	
	@Test
	void test2(){
	    assertEquals( "", "");
	}
	
}
