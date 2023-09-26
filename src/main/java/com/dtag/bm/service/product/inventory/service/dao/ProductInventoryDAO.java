package com.dtag.bm.service.product.inventory.service.dao;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dtag.bm.service.product.inventory.service.model.Product;

@Repository
public interface ProductInventoryDAO extends MongoRepository<Product, String> {

	Collection<Product> findByRelatedPartyRole(String role);

	Collection<Product> findByRelatedPartyId(String id);

	Product findById(String id);

}