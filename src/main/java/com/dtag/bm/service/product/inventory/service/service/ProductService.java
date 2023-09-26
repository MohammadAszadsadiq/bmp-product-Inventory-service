package com.dtag.bm.service.product.inventory.service.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dtag.bm.service.product.inventory.service.model.Product;
import com.dtag.bm.service.product.inventory.service.model.ProductCsv;
import com.dtag.bm.service.product.inventory.service.model.ProductRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.dtag.bm.service.product.inventory.service.model.RelationWithOfferCode;
import com.dtag.bm.service.product.inventory.service.model.CheckOfferDetailsRequest;
import com.dtag.bm.service.product.inventory.service.model.CustomerAssociatedSims;
import com.dtag.bm.service.product.inventory.service.model.CustomerAssociatedSlices;

public interface ProductService {

	public Product createProductInventory(Product request) throws ParseException;

	public Product updateProductInventory(Product request) throws ParseException;

	public List<Product> productsDetails(String filterKey, String filterValue);

	public List<Product> CustomerProductDetails(String filterKey, String filterValue, String cutomerId);

	public List<Product> retrieveProdDetailsByType(String token, String cutomerId);

	public List<Product> productsDetailsByRefferedType(String type, String relatedPartyRefId, String status,
			String relatedPartyreferredType);

	public List<Product> prodDetailsByIdandType(String token, String filterKey, String filterValue, String customerId);

	public List<Product> prodDetailsByIdandTypeTest(String token, String filterKey, String filterValue,
			String customerId);

	public List<Product> prodSliceDetails(String custId);

	public List<ProductRequest> prodDetailsByTokenAndIdOrId(String token, String customerId, String status);

	public HashMap<String, String> checkProdDetails(List<String> imsi, String customerId, String offerId);

	public HashMap<String, String> checkProdDetailssssss(List<String> imsi, String customerId, String offerId);

	public ResponseEntity<?> uploadCsv(List<ProductCsv> productCsv);

	public HashMap<String, String> checkOfferDetails(String custId, List<String> sliceOfferCodes,
			String productOfferingCode);

	public List<ProductCsv> GetCsvDetails(String token);

	public List<Product> getProductByExternalId(String externalId);
	
	public Product deleteById(String productId) throws JsonProcessingException;
	
	public List<Product> latestone(String token, String customerId, String status, String pageNumber,
			String pageSize,String filterKey, String filterValue);
			
	public Product updateProductInventoryStartDate(Product request) throws ParseException;
	
	public HashMap<String, String> checkOfferDetailsWithORNS(CheckOfferDetailsRequest checkOfferDetailsRequest) throws Exception;
	
	public CustomerAssociatedSims retriveImsisByCustId(String cutomerId);
	
	public List<CustomerAssociatedSlices> retrivesliceDetailsByCustId(String cutomerId);
	
	public List<Product> acticeServiceByImsi(String imsi);

	

}
