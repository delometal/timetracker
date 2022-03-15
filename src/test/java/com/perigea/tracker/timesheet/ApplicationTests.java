package com.perigea.tracker.timesheet;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.perigea.tracker.commons.utils.UsernameComparator;
import com.perigea.tracker.commons.utils.Utils;

//@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationTests {

	@Test	
	public void test() {
		String username = "pippo.paperino";
		String lastUsername = "pippo.paperino72";
		String refNum = lastUsername.substring(username.length(), lastUsername.length());
		Integer suffix = Integer.parseInt(refNum) + 1;
		assertTrue(suffix == 73);
	}
	
	@Test
	public void test2() {
		List<String> usernames = new ArrayList<String>();
		usernames.add("pippo.paperino1");
		usernames.add("pippo.paperino12");
		usernames.add("pippo.paperino22");
		usernames.add("pippo.paperino4");
		usernames.add("pippo.paperino72");
		Collections.sort(usernames, new UsernameComparator());
		String lastUsername = usernames.get(usernames.size() - 1);
		assertTrue(lastUsername.equals("pippo.paperino72"));
	}
	
	@Test
	public void test3() {
		int length = 8;
		String password = Utils.randomString(length);
		assertTrue(password.length()==length);
	}
}
