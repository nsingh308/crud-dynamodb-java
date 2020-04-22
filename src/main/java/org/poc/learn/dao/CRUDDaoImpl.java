package org.poc.learn.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.poc.learn.entity.CRUDEntity;
import org.poc.learn.manager.DynamoDBManager;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.document.Attribute;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.TableStatus;

public class CRUDDaoImpl implements CRUDDao {

	private static CRUDDao instance;
	private static final DynamoDBMapper mapper = DynamoDBManager.mapperInstance();
	private static final AmazonDynamoDB dbClient  = DynamoDBManager.dbClientInstance();

	private CRUDDaoImpl() {
	}

	public static CRUDDao instance() {
		if (instance == null) {
			synchronized (CRUDDaoImpl.class) {
				instance = new CRUDDaoImpl();
			}
		}
		return instance;
	}

	public void createTable(String tableName) {
		try {
		 if (!hasTable(tableName)) {
			System.out.println("Creating table...");
			CreateTableRequest  createTableRequest = mapper.generateCreateTableRequest(CRUDEntity.class);
			createTableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
			CreateTableResult tableResult = dbClient.createTable(createTableRequest);
			waitForStatus(tableName, TableStatus.ACTIVE);
		 }
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private void waitForStatus(String tableName, TableStatus active) throws InterruptedException {
		DescribeTableResult dtr = dbClient.describeTable(tableName);
		TableDescription table = dtr.getTable();
		for(;;) {
			Thread.sleep(2000);
			dtr = dbClient.describeTable(tableName);
			table = dtr.getTable();
			if(table.getTableStatus().equals("ACTIVE")) {
				System.out.println("Table has been created. "+table.getTableName());
				break;
			}else {
				System.out.println("waiting for table to become active. Current Status is: "+table.getTableStatus());
			}
		}
	}

	private boolean hasTable(String tableName) {
		try {
			System.out.println("Do we have the table ...");
			DescribeTableResult dtr = dbClient.describeTable(tableName);
			TableDescription table = dtr.getTable();
			String status = table.getTableStatus();
			System.out.println("Status of table: "+status);
			return table.getTableStatus().equals("ACTIVE");
		}catch(ResourceNotFoundException e) {
			System.out.println("Resource Not Found"+e.getMessage());
			return false;
		}
		
	}

	@Override
	public void insertRecords() {
		mapper.batchWrite(prepareDummyData(), new ArrayList<>());
	}

	public PaginatedQueryList<CRUDEntity> queryItem(String userId, String fileId) {
		DynamoDBQueryExpression<CRUDEntity> queryExpression = new DynamoDBQueryExpression<>();
		CRUDEntity crudEntity = new CRUDEntity();
		crudEntity.setUserId(userId);
		crudEntity.setFileId(fileId);
		Condition condition = new Condition();
		
		Collection<AttributeValue> attribute = new ArrayList<>();
		attribute.add(new AttributeValue(fileId));
		condition.setComparisonOperator(ComparisonOperator.EQ);
		condition.setAttributeValueList(attribute);
		Map<String, Condition> rangeKeyCondition = new HashMap<>();
		rangeKeyCondition.put("fileid",condition);
		queryExpression.setRangeKeyConditions(rangeKeyCondition);
		queryExpression.setHashKeyValues(crudEntity);
		return mapper.query(CRUDEntity.class, queryExpression);
	}
	
	private List<CRUDEntity> prepareDummyData() {
		List<CRUDEntity> itemList =  new ArrayList<>();
		for(int i = 0;i<=99;i++) {
			String fileId= "5b83e784-903d-445f-a762-d8549d8235ee_0000";
			CRUDEntity item = new CRUDEntity("1",fileId+i, "RECORD_INSERTED");
			itemList.add(item);
		}
		return itemList;
	}
	
}
