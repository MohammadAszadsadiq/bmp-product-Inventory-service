package com.dtag.bm.service.product.inventory.service.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dtag.bm.service.product.inventory.service.model.ProductCsv;

@Repository
public interface ProductCsvDAO extends MongoRepository<ProductCsv, String> {
	
	ProductCsv findById(String id);
	
	ProductCsv findByStatus(String status);

}
