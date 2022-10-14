package com.jdc.project.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.jdc.project.model.service.ProjectService;

@TestMethodOrder(OrderAnnotation.class)
@SpringJUnitConfig(locations = "classpath:application.xml")
@Sql("classpath:/projects.sql")
public class TempTest {
	
	@Autowired
	private ProjectService service;
	
	@Test
	@Order(6)
	void test() {
		
		var list = service.search(null, null, null, null);
		
		assertNotNull(list);
		assertEquals(6, list.size());
		
		list = service.search("project", null, null, null);
		
		assertNotNull(list);
		assertEquals(1, list.size());
		
		list = service.search(null, "Aung", null, null);
		
		assertNotNull(list);
		assertEquals(5, list.size());
		
		list = service.search(null, null, LocalDate.parse("2022-05-01"), null);
		
		assertNotNull(list);
		assertEquals(3, list.size());
		
		list = service.search(null, null, null, LocalDate.parse("2022-05-01"));
		
		assertNotNull(list);
		assertEquals(4, list.size());
		
	}
}
