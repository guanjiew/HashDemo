package hash;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MyChainHashLinkedListTableTest {

	@Test
	void testTwiceLoadFactor() {
		MyChainHashLinkedListTable<Integer, String> htable = new MyChainHashLinkedListTable<>(1000000);
		for (int i = 0; i < 2000000; i++) {
			int id  = i;
			String str = "String element : " + i;
			htable.add(id, str);
		}
		assertEquals("String element : 0",htable.getValue(0));
		assertEquals(null ,htable.getValue(-3));
		assertEquals(null ,htable.getValue(2000002));
		assertEquals("String element : 9999",htable.getValue(9999));
		assertEquals("String element : 1900000",htable.getValue(1900000));
		assertEquals("String element : 1999999",htable.getValue(1999999));
		htable.remove(9999);
		assertEquals(null , htable.getValue(9999));
		
	}
	
	@Test
	void testHalfLoadFactor() {
		MyChainHashLinkedListTable<Integer, String> htable = new MyChainHashLinkedListTable<>(1000000);
		for (int i = 0; i < 500000 ; i++) {
			int id  = i;
			String str = "String element : " + i;
			htable.add(id, str);
		}
		assertEquals("String element : 0",htable.getValue(0));
		assertEquals(null ,htable.getValue(-3));
		assertEquals(null ,htable.getValue(500002));
		assertEquals("String element : 9999",htable.getValue(9999));
		assertEquals("String element : 499999",htable.getValue(499999));
		assertEquals("String element : 400002",htable.getValue(400002));
		htable.remove(9999);
		assertEquals(null , htable.getValue(9999));
	}
	
	@Test
	void testNull() {
		MyChainHashLinkedListTable<Integer, String> htable = new MyChainHashLinkedListTable<>(1000000);
		for (int i = 0; i < 2000000; i++) {
			int id  = i;
			String str = null;
			htable.add(id, str);
		}
		assertEquals(null,htable.getValue(0));
		assertEquals(null ,htable.getValue(-3));
		assertEquals(null ,htable.getValue(2000002));
		htable.remove(9999);
		assertEquals(null , htable.getValue(9999));
		
	}
	
	@Test
	void testStringLoadFactor() {
		MyChainHashLinkedListTable<String, Integer> htable = new MyChainHashLinkedListTable<>(1000000);
		for (int i = 0; i < 2000000; i++) {
			int id  = i;
			String str = "String element : " + i;
			htable.add(str, id);
		}
		assertEquals(0 ,htable.getValue("String element : 0"));
		assertEquals(null ,htable.getValue("String element : -300"));
		assertEquals(99999 ,htable.getValue("String element : 99999"));
		htable.remove("String element : 99999");
		assertEquals(null,htable.getValue("String element : 99999"));
		
	}
	
}
