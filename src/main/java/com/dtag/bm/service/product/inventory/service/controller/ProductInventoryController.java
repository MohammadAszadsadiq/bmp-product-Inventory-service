package com.dtag.bm.service.product.inventory.service.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//import com.dtag.bm.order.service.model.ProductOrder;
import com.dtag.bm.service.product.inventory.service.Application;
import com.dtag.bm.service.product.inventory.service.dao.ProductCsvDAO;
import com.dtag.bm.service.product.inventory.service.dao.ProductInventoryDAO;
import com.dtag.bm.service.product.inventory.service.exceptions.ProductInventorycustomValidatorException;
import com.dtag.bm.service.product.inventory.service.model.CsvUtil;
import com.dtag.bm.service.product.inventory.service.model.CustomerAssociatedSims;
import com.dtag.bm.service.product.inventory.service.model.CustomerAssociatedSlices;
import com.dtag.bm.service.product.inventory.service.model.DirectSliceData;
import com.dtag.bm.service.product.inventory.service.model.Product;
import com.dtag.bm.service.product.inventory.service.model.ProductCsv;
import com.dtag.bm.service.product.inventory.service.model.ProductRequest;
import com.dtag.bm.service.product.inventory.service.model.UpdateTerminatedStatusRequest;
import com.dtag.bm.service.product.inventory.service.service.ProductServiceImpl;
import com.dtag.bm.service.product.inventory.service.type.ProductInventoryMain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ch.qos.logback.core.net.SocketConnector.ExceptionHandler;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/productInventoryManagement/v1")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductInventoryController {

	@Autowired
	ProductServiceImpl productservice;

	@Autowired
	ProductInventoryDAO dao;

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	ProductCsvDAO DAO;

	private static final String FILE_TYPE1 = "text/csv";
	private static final String FILE_TYPE2 = "application/vnd.ms-excel";
	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	/**
	 * @param product
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */

	@PostMapping(value = "/product")
	public Product CreateProducts(@RequestBody Product product) throws IOException, ParseException {
		/* UUID generation logic */
		UUID uuid = UUID.randomUUID();
		String Id = uuid.toString();
		product.setId(Id);
		if (product.getStatus() == null)
			throw new ProductInventorycustomValidatorException("Status Field Can't be null");
		if (product.getProductOfferingRef() == null && product.getProductSpecificationRef() == null)
			throw new ProductInventorycustomValidatorException(
					"ProductOffering and ProductSpecification both can't be null");
		if (product.getProductCharacteristic().isEmpty())
			throw new ProductInventorycustomValidatorException("Product Characteristic  can't be null");

		Resource<Product> resource = new Resource<Product>(product);
		ControllerLinkBuilder links = linkTo(methodOn(this.getClass()).productDetailsbyId(Id));
		resource.add(links.withRel("get-Product-details"));
		product.setHref(links.toString());

		LOGGER.info("Controller : calling post api for Product request  :\n " + product.toString());
		Product savedProduct = productservice.createProductInventory(product);
		return savedProduct;
	}

	@PostMapping(value = "/updateProductRemarkByProductInstanceID/{productInstanceID}/{remark}")
	public ResponseEntity<?> upDateProductRemarkByProductInstanceID(
			@PathVariable(value = "productInstanceID") String productInstanceID,

			@PathVariable(value = "remark") String remark) throws Exception {
		if (null != remark) {

			LOGGER.info("Controller : productInstanceID \n" + productInstanceID);
			LOGGER.info("Controller : remark \n" + remark);
			Product product = dao.findById(productInstanceID);
			if (null != product) {
				String imsi  = productservice.upDateProductRemarkByProductInstanceID(product, remark);
				return new ResponseEntity<>("Product having IMSI : " + imsi  +" updated successfully with remark : "+remark,
						HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Product with product id: " + productInstanceID + " not available",
						HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>("Product with product id: " + productInstanceID + "having empty remark",
					HttpStatus.OK);
		}

	}

	/**
	 * @param role
	 * @return
	 */
	@GetMapping("/product/byRole")
	public ResponseEntity<Collection<Product>> GetProducts(@RequestParam("role") String role) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Responded", "ProductInventoryController");
		return ResponseEntity.accepted().headers(headers).body(dao.findByRelatedPartyRole(role));
	}

	/**
	 * @param product
	 * @return
	 * @throws ParseException
	 */
	@PutMapping(value = "/product")
	public Product UpdateProdctInventory(@RequestBody Product product) throws ParseException {
		return productservice.updateProductInventoryStartDate(product);

	}

	/**
	 * @return
	 * @throws IOException
	 */

	@SuppressWarnings("unused")
	@GetMapping(value = "/product")
	public List<Product> getAllProductInventory() throws IOException {
		List<Product> listOfInventory = dao.findAll();
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "startDate"));
		return mongoTemplate.find(query, Product.class);
	}

	/**
	 * @param id
	 * @return
	 */
	@GetMapping("/product/{id}")
	public Product productDetailsbyId(@PathVariable(value = "id") String id) {
		return dao.findById(id);
	}

	/**
	 * @param filterKey
	 * @param filterValue
	 * @return
	 */
	@GetMapping("/product/{filterKey}/{filterValue}")
	public List<Product> productsDetails(@PathVariable(value = "filterKey") String filterKey,
			@PathVariable(value = "filterValue") String filterValue) {
		List<Product> product = productservice.productsDetails(filterKey, filterValue);
		
		
		return product;
	}

	/**
	 * @param status
	 * @param id
	 * @return
	 * @throws ParseException
	 */
	@PatchMapping("/product/ChangeStatus/{id}")
	public ResponseEntity<?> updateProdcutStatus(@PathVariable("id") String id) throws ParseException {
		Product ProductToBeUpdated = productDetailsbyId(id);
		if (ProductToBeUpdated != null) {
			ProductToBeUpdated.setStatus("InActive");
			productservice.updateProductInventory(ProductToBeUpdated);
			return new ResponseEntity<>("Product with product id: " + id + " updated successfully.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Product with product id: " + id + " not available", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/product/updateStatus/{id}/{ExternalId}")
	public ResponseEntity<?> updateStatus(@PathVariable("id") String id, @PathVariable("ExternalId") String ExternalId)
			throws ParseException {
		Product ProductToBeUpdated = dao.findById(id);
		if (ProductToBeUpdated != null) {

			ProductToBeUpdated.setStatus("InActive");
			ProductToBeUpdated.getProductOrderRef().forEach(i -> i.setId(ExternalId));
			productservice.updateProductInventory(ProductToBeUpdated);
			return new ResponseEntity<>("Product with product id: " + id + "  " + "and ORN Number :" + ExternalId
					+ " updated successfully.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(
					"Product with product id: " + id + "  " + "and ORN Number :" + ExternalId + " not available",
					HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * @param filterKey
	 * @param filterValue
	 * @param customerId
	 * @return
	 */
	@GetMapping("/product/customerId")
	public Collection<Product> CustomerproductsDetails(@RequestParam(required = false) String filterKey,
			@RequestParam(required = false) String filterValue,
			@RequestParam("RelatedPartyCustomerId") String customerId) {
		if ((filterKey != null) && (filterValue != null)) {
			return productservice.CustomerProductDetails(filterKey, filterValue, customerId);
		} else if (customerId != null) {
			return dao.findByRelatedPartyId(customerId);
		}
		return null;
	}

	/**
	 * @param token
	 * @param customerId
	 * @return
	 */
	@GetMapping("/product/retrieve/customerId")
	public Collection<Product> retrieveProdDetailsByType(@RequestParam String token,
			@RequestParam(required = false) String customerId) {
		if ((token != null) && (customerId != null)) {
			return productservice.retrieveProdDetailsByType(token, customerId);
		} else {
			return null;
		}

	}

	/**
	 * @description this method will return the list of products based on the
	 *              refferedType and status
	 * @param type
	 * @param relatedPartyRefId
	 * @param status
	 * @param relatedPartyreferredType
	 * @return List of products
	 */
	@GetMapping("/product/prodDetailsByType")
	public List<Product> productsDetailsByRefferedType(@RequestParam String type,
			@RequestParam String relatedPartyRefId, @RequestParam String status,
			@RequestParam String relatedPartyreferredType) {

		List<Product> product = productservice.productsDetailsByRefferedType(type, relatedPartyRefId, status,
				relatedPartyreferredType);
		return product;
	}

	@GetMapping("/product/prodDetails")
	public List<Product> prodDetailsByIdandType(@RequestParam String token,
			@RequestParam(required = false) String filterKey, @RequestParam(required = false) String filterValue,
			@RequestParam(required = false) String customerId) {

		List<Product> product = productservice.prodDetailsByIdandTypeTest(token, filterKey, filterValue, customerId);
		return product;
	}

	@GetMapping("/product/Slice")
	public List<Product> prodSliceDetails(@RequestParam(required = false) String custId) {
		List<Product> product = productservice.prodSliceDetails(custId);
		return product;
	}

	@GetMapping("/product/prodDetailsWithSlices")
	public List<ProductRequest> prodDetailsByIdandType1(@RequestParam String token,
			@RequestParam(required = false) String customerId, @RequestParam(required = false) String status) {		
		return productservice.prodDetailsByTokenAndIdOrId(token, customerId, status);
	}

	/**
	 * @description "This method will search the record with given inputs, if found
	 *              return the present else Not present.
	 * @param prodId
	 * @param customerId
	 * @param offcerId
	 * @return
	 */
	@GetMapping("/product/checkProdDetails")
	public HashMap<String, String> checkProdDetails(@RequestParam List<String> imsi, @RequestParam String customerId,
			@RequestParam String offerId) {
		HashMap<String, String> map = productservice.checkProdDetails(imsi, customerId, offerId);
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@PostMapping(value = "/productCsv")
	public ResponseEntity<List<ProductCsv>> uploadCsv(@RequestParam("file") MultipartFile file) throws IOException {

		ResponseEntity resp = null;
		String contentType = file.getContentType();
		try {
			if (file.isEmpty() || (!(FILE_TYPE1.equals(contentType)) && !(FILE_TYPE2.equals(contentType)))) {
				return new ResponseEntity("please select a valid csv file!", HttpStatus.OK);
			}

			productservice.uploadCsv(CsvUtil.readcsv1(ProductCsv.class, file.getInputStream()));
			HttpHeaders headers = new HttpHeaders();
			headers.add("Responded", "ProductInventoryController");

			List<ProductCsv> listOfInventory = DAO.findAll();
			Query query = new Query();
			query.with(new Sort(Sort.Direction.DESC, "Priority"));
			mongoTemplate.find(query, ProductCsv.class);

			// return new ResponseEntity("File Uploaded Successfully", HttpStatus.OK);
			return new ResponseEntity(listOfInventory, HttpStatus.OK);
		} catch (NumberFormatException ex) {

			ProductCsv prd1 = new ProductCsv();
			return new ResponseEntity(
					"Upload Failed Reason : Invalid data type " + ex.getMessage() + " expected Numeric Value.",
					HttpStatus.BAD_REQUEST);

		}

	}

	@GetMapping("/product/checkOfferDetails")
	public HashMap<String, String> checkOfferDetails(@RequestParam String custId,
			@RequestParam List<String> sliceOfferCodes, @RequestParam String productOfferingCode) {

		HashMap<String, String> result = productservice.checkOfferDetails(custId, sliceOfferCodes, productOfferingCode);

		return result;
	}

	/*
	 * @GetMapping("/CsvDetailsOnLoad") public ResponseEntity<List<ProductCsv>>
	 * GetCsvDetails() { List<ProductCsv> csv = productservice.GetCsvDetails();
	 * HttpHeaders headers = new HttpHeaders(); headers.add("Responded",
	 * "ProductInventoryController");
	 * 
	 * return ResponseEntity.accepted().headers(headers).body(csv); }
	 */

	@GetMapping("/CsvDetailsOnLoad")
	public ResponseEntity<List<ProductCsv>> GetCsvDetails(@RequestParam(required = false) String token) {
		List<ProductCsv> csv = productservice.GetCsvDetails(token);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Responded", "ProductInventoryController");

		return ResponseEntity.accepted().headers(headers).body(csv);
	}

	/**
	 * This will fetch ProductInventoryByExternalId
	 * 
	 * @return
	 * @throws com.fasterxml.jackson.core.JsonProcessingException
	 * 
	 */
	@SuppressWarnings("unused")
	@GetMapping("/getByExternalId/{ExternalId}")
	public ResponseEntity<?> getProductInventoryByExternalId(@PathVariable(value = "ExternalId") String ExternalId)
			throws com.fasterxml.jackson.core.JsonProcessingException {
		ResponseEntity<?> responseEntity = null;
		final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {

			List<Product> request = productservice.getProductByExternalId(ExternalId);
			LOGGER.info("size :"+request.size());
			
			if (request != null) {
				try {					
					request.get(0).getTerminationDate();
					
					LOGGER.info("Termination date :"+ request.get(0).getTerminationDate());
					
					String output = request.get(0).getTerminationDate().substring(0, 10);
					
					Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(output);
					
					LOGGER.info("parse date :"+ date1);
					
					 String str = String.format("%ts", date1 );
					 LOGGER.info("epoch date :"+ str);
					 request.get(0).setTerminationDate(str);
				}catch(Exception e) {
					e.printStackTrace();
				}			

				responseEntity = new ResponseEntity<>(request, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<>("Product with specified Id not found", HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseEntity;

	}

	/**
	 * @param id
	 * @return
	 */
	@DeleteMapping("/product/product/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(value = "id") String Id)
			throws com.fasterxml.jackson.core.JsonProcessingException {
		ResponseEntity<?> responseEntity = null;
		final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		try {

			Product response = productservice.deleteById(Id);
			if (response != null) {
				responseEntity = new ResponseEntity<>(" Successfully deleted  Product ", HttpStatus.OK);

			} else {
				responseEntity = new ResponseEntity<>("Product with Id " + Id + " not  found ", HttpStatus.NOT_FOUND);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseEntity;

	}

	@SuppressWarnings("unchecked")
	@PostMapping("/product/updateStatusOnterminated")
	public String updateStatusOnterminated(@RequestBody UpdateTerminatedStatusRequest updateStatus) throws IOException {
		updateStatus.getIds().forEach(idone -> {
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(idone));

			Update update = new Update();
			update.set("Status", updateStatus.getStatus());
			mongoTemplate.updateFirst(query, update, Product.class);

		});
		return "Product Temindated Successfully";
	}

	@GetMapping("/product/prodDetailsWithslicePerformance")
	public List<Product> prodDetailsByIdandType(@RequestParam String token,
			@RequestParam(required = false) String customerId, @RequestParam String status,
			@RequestParam(required = false) String pageNumber, @RequestParam(required = false) String pageSize,
			@RequestParam(required = false) String filterKey, @RequestParam(required = false) String filterValue) {
		
		return productservice.latestone(token, customerId, status, pageNumber, pageSize, filterKey, filterValue);

	}

	@GetMapping("/product/retrieveImsi/customerId")
	public CustomerAssociatedSims retriveImsisByCustId(@RequestParam String customerId) {
		if (customerId != null) {
			return productservice.retriveImsisByCustId(customerId);
		} else {
			return null;
		}
	}

	@GetMapping("/product/retrieveSlices/customerId")
	public List<CustomerAssociatedSlices> retrivesliceDetailsByCustId(@RequestParam String customerId) {
		if (customerId != null) {
			return productservice.retrivesliceDetailsByCustId(customerId);
		} else {
			return null;
		}

	}

	/**
	 * This will fetch ProductInventoryByExternalId
	 * 
	 * @return
	 * @throws com.fasterxml.jackson.core.JsonProcessingException
	 * 
	 */
	@SuppressWarnings("unused")
	@GetMapping("/activeServicesByImsi/{Imsi}")
	public ResponseEntity<?> getactiveServicesByImsi(@PathVariable(value = "Imsi") String Imsi)
			throws com.fasterxml.jackson.core.JsonProcessingException {
		ResponseEntity<?> responseEntity = null;
		final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {

			List<Product> request = productservice.acticeServiceByImsi(Imsi);
			if (request != null) {

				responseEntity = new ResponseEntity<>(request, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<>("Products with specified imsi not found", HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseEntity;

	}
	
	
	/**
	 * This will fetch ProductInventoryByExternalId
	 * 
	 * @return
	 * @throws com.fasterxml.jackson.core.JsonProcessingException
	 * 
	 */
	@SuppressWarnings("unused")
	@GetMapping("/getSimMaterialServiceByImsi/{Imsi}")
	public ResponseEntity<?> getSimMaterialServiceByImsi(@PathVariable(value = "Imsi") String Imsi)
			throws com.fasterxml.jackson.core.JsonProcessingException {
		ResponseEntity<?> responseEntity = null;
		final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {

			Product request = productservice.getSimMaterialServiceByImsi(Imsi);
			if (request != null) {

				responseEntity = new ResponseEntity<>(request, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<>("Products with specified imsi not found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseEntity;

	}
	
	
	/**
	 * This will fetch UDMProfileID by IMSI
	 * 
	 * @return
	 * @throws com.fasterxml.jackson.core.JsonProcessingException
	 * 
	 */
	@SuppressWarnings("unused")
	@GetMapping("/fetchUDMProfileIDByImsi/{imsi}")
	public ResponseEntity<?> getUDMProfileIDByImsi(@PathVariable(value = "imsi") String imsi)
			throws com.fasterxml.jackson.core.JsonProcessingException {
		ResponseEntity<?> responseEntity = null;
		final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {

			String response = productservice.getUDMProfileIDByImsi(imsi.trim());
			if (response != null) {

				responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<>("UDM profile ID with specified imsi not found", HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseEntity;

	}

	/**
	 * @param status
	 * @param id
	 * @return
	 * @throws ParseException
	 */
	@GetMapping("/product/activateProductSubscription/{ExternalId}")
	public ResponseEntity<?> activateProductSubscription(@PathVariable("ExternalId") String ExternalId)
			throws ParseException {
		// Product ProductToBeUpdated = dao.findById(id);
		List<Product> request = productservice.getProductByExternalId(ExternalId);
		if (!request.isEmpty()) {
			for (Product ProductToBeUpdated : request) {
				ProductToBeUpdated.setStatus("Active");
				ProductToBeUpdated.getProductOrderRef().forEach(i -> i.setId(ExternalId));
				productservice.activateProductSubscription(ProductToBeUpdated);
				return new ResponseEntity<>("ORN Number :" + ExternalId + " status updated successfully.",
						HttpStatus.OK);
			}

		} else {
			return new ResponseEntity<>(" ORN Number :" + ExternalId + " not available", HttpStatus.NOT_FOUND);
		}
		return null;

	}
	
	@GetMapping("/product/getRemarkByListIMSI")
	public HashMap<String, String> getRemarkByListIMSI(@RequestParam List<String> imsi) {
		HashMap<String, String> map = productservice.getRemarkByListIMSI(imsi);
		return map;
	}
	
	
	/**
	 * This will fetch SST,SD and PLMN by IMSI
	 * 
	 * @return
	 * @throws com.fasterxml.jackson.core.JsonProcessingException
	 * 
	 */
	@SuppressWarnings("unused")
	@GetMapping("/fetchDirectSliceDataByIMSI/{Imsi}")
	public ResponseEntity<?> fetchDirectSliceDataByIMSI(@PathVariable(value = "Imsi") String Imsi)
			throws com.fasterxml.jackson.core.JsonProcessingException {
		ResponseEntity<?> responseEntity = null;
		final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {

			List<DirectSliceData> response = productservice.fetchDirectSliceDataByIMSI(Imsi);
			if (response != null && !response.isEmpty()) {

				responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseEntity;

	}
	
	/**
	 * This will fetch  Product Inventory TMF formated data
	 * 
	 * @return
	 * @throws com.fasterxml.jackson.core.JsonProcessingException
	 * 
	 */
	@SuppressWarnings("unused")
	@GetMapping("/modifiedTMF/v1/{internalId}")
	public ResponseEntity<?> getNewProdInvenResp(@PathVariable(value = "internalId") String idORN) throws com.fasterxml.jackson.core.JsonProcessingException {
		ResponseEntity<?> responseEntity = null;
		final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			List<ProductInventoryMain> main = productservice.getNewProdInventResp(idORN.trim());
			responseEntity = new ResponseEntity<>(main, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseEntity;

	}
	
	
	/**
	 * This will fetch  ProductInventory by matching order with SST,SD,DNN,IMSI
	 * 
	 * @return
	 * @throws com.fasterxml.jackson.core.JsonProcessingException
	 * 
	 */
	@SuppressWarnings("unused")
	@GetMapping("/productInventoryManagement/v1/getMatchingOrderBySstSdDnnImsi")
	public ResponseEntity<?> getMatchingOrderBySstSdDnnImsi(@RequestParam String sst, @RequestParam String sd, @RequestParam String dnn ,@RequestParam String customerId,@RequestParam String imsi) throws Exception {
		ResponseEntity<?> responseEntity = null;
		final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		try {
			List<Product> product = productservice.getSstSdDnn(sst.trim(),sd.trim(),dnn.trim(),customerId.trim(),imsi.trim());
			responseEntity = new ResponseEntity<>(product, HttpStatus.OK);
		}catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
		}
		
		return responseEntity;

	}
	
	


}