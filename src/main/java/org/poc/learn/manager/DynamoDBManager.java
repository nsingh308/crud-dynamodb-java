package org.poc.learn.manager;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class DynamoDBManager {

	 private static volatile DynamoDBManager instance;
	    private DynamoDBMapper mapper;
	    private AmazonDynamoDB client;
	    
	    private DynamoDBManager() {
	    	ProfileCredentialsProvider pcp = new ProfileCredentialsProvider("nsingh");
	    	client = AmazonDynamoDBClientBuilder.standard()
	    			.withCredentials(pcp)
	    			.build();
	        mapper = new DynamoDBMapper(client);
	    }

	    public static DynamoDBMapper mapperInstance() {
	        synchronized(DynamoDBManager.class) {
	            if (instance == null)
	                instance = new DynamoDBManager();
	        }
	        return instance.mapper;
	    }
	    
	    public static AmazonDynamoDB dbClientInstance() {
	        synchronized(DynamoDBManager.class) {
	            if (instance == null)
	                instance = new DynamoDBManager();
	        }
	        return instance.client;
	    }

}
