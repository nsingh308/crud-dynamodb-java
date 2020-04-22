package org.poc.learn;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.poc.learn.function.CRUDHandler;
import org.poc.learn.helpers.MockLambdaContext;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.util.IOUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCase {

	static Context context;
	static CRUDHandler handler;
	static ClassLoader classLoader;
	@Before
	public void setUp() throws Exception {
		context = new MockLambdaContext();
		handler = new CRUDHandler();
		classLoader = getClass().getClassLoader();
	}
	
	@Test
	public void testA_createTable() throws IOException {
		InputStream inputStream = classLoader.getResourceAsStream("create-table.json");
		String input =IOUtils.toString(inputStream);
		handler.handleRequest(input, context);
	}
	
	@Test
	public void testB_insertRecords() throws IOException {
		InputStream inputStream = classLoader.getResourceAsStream("insert-records.json");
		String input =IOUtils.toString(inputStream);
		handler.handleRequest(input, context);
	}
	@Test
	public void testC_queryRecords() throws IOException {
		InputStream inputStream = classLoader.getResourceAsStream("query-records.json");
		String input =IOUtils.toString(inputStream);
		handler.handleRequest(input, context);
	}
}
