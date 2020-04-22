package org.poc.learn.function;

import org.poc.learn.dao.CRUDDao;
import org.poc.learn.dao.CRUDDaoImpl;
import org.poc.learn.model.InputModel;
import org.poc.learn.service.CrudService;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

/**
 * Lambda function that simply prints "Hello World" if the input String is not
 * provided, otherwise, print "Hello " with the provided input String.
 */
public class CRUDHandler implements RequestHandler<String, String> {

	@Override
	public String handleRequest(String input, Context context) {
		String output = "Hello " + ((input != null && !input.isEmpty()) ? input : "World");
		context.getLogger().log(output);
		CrudService cs = new CrudService();
		Gson gson = new Gson();
		InputModel inputModel = gson.fromJson(input, InputModel.class);
		switch (inputModel.getAction()) {
			case "CREATE_TABLE":
				cs.createTableIfNotCreated();
			break;
			case "INSERT_RECORDS":
				cs.insertRecords();
				//System.out.println("Data will be inserted here.");
			break;
			case "QUERY_RECORDS":
			try {
				cs.queryRecords();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
				break;
		default:
			System.out.println("No default action will be taken");
			break;
		}
		return output;
	}
}