package org.poc.learn.service;

import java.util.List;

import org.poc.learn.constant.TableConstant;
import org.poc.learn.dao.CRUDDao;
import org.poc.learn.dao.CRUDDaoImpl;
import org.poc.learn.entity.CRUDEntity;

public class CrudService {

	private CRUDDao crudDao = CRUDDaoImpl.instance();
	public CrudService(){
	}
	public void createTableIfNotCreated() {
		crudDao.createTable(TableConstant.CRUD_TBL);
	}
	public void insertRecords() {
		crudDao.insertRecords();
	}
	
	public void queryRecords() throws InterruptedException {
		for(int i = 0;i<=99;i++) {
			System.out.println("quering..."+i);
			Thread.sleep(1000);
			String fileId="5b83e784-903d-445f-a762-d8549d8235ee_0000";
			List<CRUDEntity> list = crudDao.queryItem("1", fileId+i);
			if(!list.isEmpty()) {
				System.out.println("Total Size: "+list.size()+" i="+i+"first item:"+list.get(0).getFileId());
			}else {
				System.out.println("Could not find data");
			}
		}
	}
}
