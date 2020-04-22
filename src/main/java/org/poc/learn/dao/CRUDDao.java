package org.poc.learn.dao;

import org.poc.learn.entity.CRUDEntity;

import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;

public interface CRUDDao {

	public void createTable(String tableName);

	public void insertRecords();
	
	public PaginatedQueryList<CRUDEntity> queryItem(String userId, String fileId);
}
