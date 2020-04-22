package org.poc.learn.entity;

import org.poc.learn.constant.TableConstant;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = TableConstant.CRUD_TBL)
public class CRUDEntity{

	@DynamoDBHashKey(attributeName = "userid")
	private String userId;
	@DynamoDBRangeKey(attributeName = "fileid")
	private String fileId;
	
	@DynamoDBAttribute(attributeName = "status")
	private String status;
	
	public CRUDEntity(String userId, String fileId, String status){
		this.userId= userId;
		this.fileId = fileId;
		this.status = status;
	}

	public CRUDEntity() {
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
