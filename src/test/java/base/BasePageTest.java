package base;

import static org.junit.Assert.*;

import org.junit.Test;

public class BasePageTest {

	@Test
	public void testGetValueFromJson() {
	
		//Given
		String path=System.getProperty("user.dir") + "\\src\\test\\data\\data.json";
		String key="email";
		//When
		String value=BasePage.getValueFromJson(path,key);
		//Then
		assertEquals("error", value);
	}

}
