package com.dtag.bm.service.product.inventory.service.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dtag.bm.service.product.inventory.service.constants.PIConstants;
import com.dtag.bm.service.product.inventory.service.dao.ProductCsvDAO;
import com.dtag.bm.service.product.inventory.service.dao.ProductInventoryDAO;
import com.dtag.bm.service.product.inventory.service.exceptions.ProductInventoryValidatorException;
import com.dtag.bm.service.product.inventory.service.exceptions.ProductInventorycustomValidatorException;
import com.dtag.bm.service.product.inventory.service.model.CheckOfferDetailsRequest;
import com.dtag.bm.service.product.inventory.service.model.CustomerAssociatedSims;
import com.dtag.bm.service.product.inventory.service.model.CustomerAssociatedSlices;
import com.dtag.bm.service.product.inventory.service.model.DirectSliceData;
import com.dtag.bm.service.product.inventory.service.model.Product;
import com.dtag.bm.service.product.inventory.service.model.ProductCharacteristic;
import com.dtag.bm.service.product.inventory.service.model.ProductCount;
import com.dtag.bm.service.product.inventory.service.model.ProductCsv;
import com.dtag.bm.service.product.inventory.service.model.ProductOrder;
import com.dtag.bm.service.product.inventory.service.model.ProductOrderNotification;
import com.dtag.bm.service.product.inventory.service.model.ProductPrice;
import com.dtag.bm.service.product.inventory.service.model.ProductRequest;
import com.dtag.bm.service.product.inventory.service.model.RelatedParty;
import com.dtag.bm.service.product.inventory.service.type.Price_;
import com.dtag.bm.service.product.inventory.service.type.ProductCharacteristic_;
import com.dtag.bm.service.product.inventory.service.type.ProductInventoryMain;
import com.dtag.bm.service.product.inventory.service.type.ProductOffering_;
import com.dtag.bm.service.product.inventory.service.type.ProductPrice_;
import com.dtag.bm.service.product.inventory.service.type.ProductSpecification_;
import com.dtag.bm.service.product.inventory.service.type.Product_;
import com.dtag.bm.service.product.inventory.service.type.RelatedParty_;
import com.dtag.bm.service.product.inventory.service.utility.Utility;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.uuid.Generators;
import com.google.common.base.Stopwatch;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class ProductServiceImpl implements ProductService, ApplicationEventPublisherAware {

	private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	ProductInventoryDAO dao;

	@Autowired
	ProductCsvDAO DAO;

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	Utility productInventoryUtility;

	@Value("${sim.material.type}")
	private String simMaterialType;

	@Value("${sim.direct.slice.access}")
	private String simDirectSliceAccess;

	@Value("${sim.product.slice.access}")
	private String simProductSliceAccess;

	@Value("${slice}")
	private String slice;

	@Value("${other.onap.services}")
	private String otherOnapServices;

	@Value("${date.format}")
	private String dateFormat;

	@Value("${timeZone}")
	private String timeZone;

	@Value("${aml}")
	private String aml;

	@Value("${date.format}")
	private String format;

	@Value("${sim.connectivity}")
	private String simconnectivity;

	@Value("${spring.data.mongodb.host}")
	private String hostName;

	@Value("${spring.data.mongodb.port}")
	private int portNumber;

	@Value("${spring.data.mongodb.database}")
	private String dbName;

	private ApplicationEventPublisher publisher;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

	/**
	 * @param Product_
	 * @description it get the product details from Mu-dynamics and save into
	 *              ProductInventoryDetails Collections
	 * @return It return the saved data.
	 * @throws ParseException
	 */
	@Override
	public Product createProductInventory(Product request) throws ParseException {
		if (request.getStatus() == null)
			throw new ProductInventoryValidatorException("Status Field Can't be null");
		if (request.getProductOfferingRef() == null && request.getProductSpecificationRef() == null)
			throw new ProductInventoryValidatorException(
					"ProductOfferingRef and ProductSpecificationRef both can't be null");

		DateFormat formatter = new SimpleDateFormat(format);
		formatter.setTimeZone(TimeZone.getTimeZone(timeZone));

		if ((null != request.getStartDate() && !request.getStartDate().isEmpty())
				&& (null != request.getTerminationDate() && !request.getTerminationDate().isEmpty())) {
			LOGGER.info("getStartDate " + request.getStartDate());
			request.setStartDate(request.getStartDate());

			LOGGER.info("New code for check epoch date");
			LOGGER.info("getTerminationDate " + request.getTerminationDate());
			request.setTerminationDate(request.getTerminationDate());
			// Epoch format conversion

			if (request.getRelatedPartyRef().get(0).getName().equalsIgnoreCase("thirdparty")) {

				String output = request.getTerminationDate().substring(0, 10);

				Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(output);

				LOGGER.info("parse date :" + date1);

				String str = String.format("%ts", date1);
				LOGGER.info("epoch date :" + str);

				request.setTerminationDate(str);

			}

			LOGGER.info("startDate request" + request.getStartDate() + "Tertmination Date request"
					+ request.getTerminationDate());
		} else {
			String epochDate = setEpochDateAtStartDate();
			request.setStartDate(epochDate);
			LOGGER.info("epochDate " + epochDate);
		}

		// Epoch format conversion

		LOGGER.info("Product request before saving into db :\n " + request.toString());

		Product product = new Product();
		try {
			product = dao.insert(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.info("Product saved succefully :\n " + product.toString());
		// Creating notification event

		UUID uuid = Generators.timeBasedGenerator().generate();
		long ids = uuid.node();
		String EventId = Long.toString(ids);
		String eventDate = LocalDateTime.now().toString();

		ProductOrderNotification event = new ProductOrderNotification(this, EventId, eventDate,
				PIConstants.CREATE_NOTIFICATION, product);

		LOGGER.info("ProductOrderNotification event :\n " + event.toString());

		publisher.publishEvent(event);

		return product;
	}

	/**
	 * @return
	 * @throws ParseException
	 */
	private String setEpochDate() throws ParseException {
		Date today = Calendar.getInstance().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		String currentTime = dateFormat.format(today);
		Date date = dateFormat.parse(currentTime);
		Long epochTime = date.getTime();
		String epochDate = Long.toString(epochTime);
		return epochDate;
	}

	private String setEpochDateAtStartDate() throws ParseException {
		Date today = Calendar.getInstance().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String currentTime = dateFormat.format(today);
		Date date = dateFormat.parse(currentTime);
		Long epochTime = date.getTime() / 1000;
		String epochDate = Long.toString(epochTime);
		return epochDate;

	}

	@Override
	public Product updateProductInventory(Product request) throws ParseException {
		DateFormat formatter = new SimpleDateFormat(format);
		formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
		String epochDate = setEpochDate();
		// request.setStartDate(epochDate);
		request.setTerminationDate(epochDate);
		return dao.save(request);
	}

	@Override
	public Product updateProductInventoryStartDate(Product request) throws ParseException {
		LOGGER.info("Products Inventory PUT request" + request);
		return dao.save(request);
	}

	@Override
	public List<Product> productsDetails(String filterKey, String filterValue) {
		Query query = new Query();
		if (filterKey.equalsIgnoreCase("ProductInstanceId")) {
			query.addCriteria(Criteria.where("_id").is(filterValue));
			return mongoTemplate.find(query, Product.class);
		} else if (filterKey.equalsIgnoreCase("ProductOrderRefOrderId")) {
			query.addCriteria(Criteria.where("productOrder._id").is(filterValue));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("ProductOfferingRefOfferName")) {
			query.addCriteria(Criteria.where("productOffering.name").is(filterValue));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("RelatedPartyRefCustomerId")) {
			query.addCriteria(Criteria.where("relatedParty._id").is(filterValue));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("RelatedPartyRefCustomerName")) {
			query.addCriteria(Criteria.where("relatedParty.name").is(filterValue));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("status")) {
			query.addCriteria(Criteria.where("status").is(filterValue));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("startOfSubscription")) {

			long unix_seconds = Long.parseLong(filterValue);
			Date date = new Date(unix_seconds * 1000L);
			SimpleDateFormat storedFormat = new SimpleDateFormat("dd-MM-yyyy");
			String formatedDate = storedFormat.format(date);
			query.addCriteria(Criteria.where("startDate").is(formatedDate));
			return mongoTemplate.find(query, Product.class);
		}

		return null;
	}

	@Override
	public List<Product> CustomerProductDetails(String filterKey, String filterValue, String cutomerId) {
		Query query = new Query();
		if (filterKey.equalsIgnoreCase("ProductInstanceId")) {
			query.addCriteria(Criteria.where("_id").is(filterValue).and("relatedParty._id").is(cutomerId));
			return mongoTemplate.find(query, Product.class);
		} else if (filterKey.equalsIgnoreCase("ProductOrderRefOrderId")) {
			query.addCriteria(Criteria.where("productOrder._id").is(filterValue).and("relatedParty._id").is(cutomerId));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("ProductOfferingRefOfferName")) {
			query.addCriteria(
					Criteria.where("productOffering.name").is(filterValue).and("relatedParty._id").is(cutomerId));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("RelatedPartyRefCustomerName")) {
			query.addCriteria(
					Criteria.where("relatedParty.name").is(filterValue).and("relatedParty._id").is(cutomerId));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("status")) {
			query.addCriteria(Criteria.where("status").is(filterValue).and("relatedParty._id").is(cutomerId));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("startOfSubscription")) {

			long unix_seconds = Long.parseLong(filterValue);
			Date date = new Date(unix_seconds * 1000L);
			SimpleDateFormat storedFormat = new SimpleDateFormat("dd-MM-yyyy");
			String formatedDate = storedFormat.format(date);
			query.addCriteria(Criteria.where("startDate").is(formatedDate).and("relatedParty._id").is(cutomerId));
			return mongoTemplate.find(query, Product.class);
		}
		return null;
	}

	@Override
	public List<Product> retrieveProdDetailsByType(String token, String cutomerId) {

		Query query = new Query();
		if (token.equalsIgnoreCase("sim product")) {
			query.addCriteria(Criteria.where("type").is("sim").and("relatedParty._id").is(cutomerId));
			return mongoTemplate.find(query, Product.class);
		} else if (token.equalsIgnoreCase("AML")) {
			query.addCriteria(Criteria.where("type").is("AML").and("relatedParty._id").is(cutomerId));
			return mongoTemplate.find(query, Product.class);
		} else if (token.equalsIgnoreCase("Non sim product")) {
			query.addCriteria(Criteria.where("type").is("Non sim product").and("relatedParty._id").is(cutomerId));
			return mongoTemplate.find(query, Product.class);

		}
		return mongoTemplate.find(query, Product.class);
	}

	@Override
	public List<Product> productsDetailsByRefferedType(String type, String relatedPartyRefId, String status,
			String relatedPartyreferredType) {

		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("relatedParty._id").is(relatedPartyRefId).and("relatedParty.referredType")
					.is(relatedPartyreferredType).and("status").is(status).and("type").is(type));
			return mongoTemplate.find(query, Product.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings({ "unused", "null" })
	@Override
	public List<Product> prodDetailsByIdandType(String token, String filterKey, String filterValue, String customerId) {

		Query query = new Query();

		if (token != null && token.equalsIgnoreCase("sim product")) {
			query.addCriteria(Criteria.where("type").is("sim"));
			return mongoTemplate.find(query, Product.class);
		} else if (token != null && token.equalsIgnoreCase("ALM")) {
			query.addCriteria(Criteria.where("type").is("ALM"));
			return mongoTemplate.find(query, Product.class);
		} else if (token != null && token.equalsIgnoreCase("Non sim product")) {
			query.addCriteria(Criteria.where("type").is("Non sim product"));
			return mongoTemplate.find(query, Product.class);
		} else if (customerId != null && filterKey == null) {
			if (token.equalsIgnoreCase("simproducts")) {
				query.addCriteria(Criteria.where("relatedParty._id").is(customerId).and("type").is("sim"));
				return mongoTemplate.find(query, Product.class);
			} else if (token.equalsIgnoreCase("ALM")) {
				query.addCriteria(Criteria.where("relatedParty._id").is(customerId).and("type").is("ALM"));
				return mongoTemplate.find(query, Product.class);
			} else if (token.equalsIgnoreCase("Non sim product")) {
				query.addCriteria(Criteria.where("type").is("Non sim product").and("relatedParty._id").is(customerId));
				return mongoTemplate.find(query, Product.class);
			}

		} else if (customerId == null && filterKey == null) {
			return mongoTemplate.find(query, Product.class);
			// Get the details for Admin on page loading

		} else if (customerId != null && (filterKey != null && filterValue != null)) {
			query.addCriteria(Criteria.where("relatedParty._id").is(customerId));
			return mongoTemplate.find(query, Product.class);
			// Return the details for self-care

		} else if (filterKey != null && filterValue != null) {
			return mongoTemplate.find(query, Product.class);
			// Return the details for Admin

		} else if (filterKey.equalsIgnoreCase("ProductInstanceId")) {
			query.addCriteria(Criteria.where("_id").is(filterValue).and("relatedParty._id").is(customerId));
			return mongoTemplate.find(query, Product.class);
		} else if (filterKey.equalsIgnoreCase("ProductOrderRefOrderId")) {
			query.addCriteria(
					Criteria.where("productOrder._id").is(filterValue).and("relatedParty._id").is(customerId));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("ProductOfferingRefOfferName")) {
			query.addCriteria(
					Criteria.where("productOffering.name").is(filterValue).and("relatedParty._id").is(customerId));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("RelatedPartyRefCustomerName")) {
			query.addCriteria(
					Criteria.where("relatedParty.name").is(filterValue).and("relatedParty._id").is(customerId));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("status")) {
			query.addCriteria(Criteria.where("status").is(filterValue).and("relatedParty._id").is(customerId));
			return mongoTemplate.find(query, Product.class);

		} else if (filterKey.equalsIgnoreCase("startOfSubscription")) {

			long unix_seconds = Long.parseLong(filterValue);
			Date date = new Date(unix_seconds * 1000L);
			SimpleDateFormat storedFormat = new SimpleDateFormat("dd-MM-yyyy");
			String formatedDate = storedFormat.format(date);
			query.addCriteria(Criteria.where("startDate").is(formatedDate).and("relatedParty._id").is(customerId));
			return mongoTemplate.find(query, Product.class);
		}

		return mongoTemplate.find(query, Product.class);

	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public List<Product> prodDetailsByIdandTypeTest(String token, String filterKey, String filterValue,
			String customerId) {

		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "startDate"));
		if (token != null) {

			query.addCriteria(Criteria.where("type").is(token));
		}

		if (customerId != null) {
			query.addCriteria(Criteria.where("relatedParty._id").is(customerId));
		}

		if (filterKey != null && filterValue != null) {
			if (filterKey.equalsIgnoreCase("startOfSubscription")) {

				long unix_seconds = Long.parseLong(filterValue);
				Date date = new Date(unix_seconds * 1000L);
				SimpleDateFormat storedFormat = new SimpleDateFormat("dd-MM-yyyy");
				String formatedDate = storedFormat.format(date);
				query.addCriteria(Criteria.where("startDate").is(formatedDate));

			} else {
				query.addCriteria(Criteria.where(returnKey(filterKey)).is(filterValue));
			}
		}
		return mongoTemplate.find(query, Product.class);
	}

	private String returnKey(String key) {
		if (key.equals("productInstanceId"))
			return "_id";
		else if (key.equals("ProductOrderRefOrderId"))
			return "productOrder._id";
		else if (key.equals("ProductOfferingRefOfferName"))
			return "productOffering.name";
		else if (key.equals("RelatedPartyRefCustomerName"))
			return "relatedParty.name";
		else if (key.equals("status"))
			return "status";
		else if (key.equals("customerId"))
			return "relatedParty._id";
		else if (key.equals("referredType"))
			return "relatedParty.referredType";
		else if (key.equals("startOfSubscription"))
			return "startDate";
		else if (key.equals("productCharacteristicName"))
			return "productCharacteristic.name";
		return "Successfully";

	}

	@SuppressWarnings("unused")
	@Override
	public List<Product> prodSliceDetails(String custId) {

		String str1 = "openness";
		String str2 = "SliceAccessType";
		LOGGER.info("Products Details Are" + custId);

		Query query = new Query();
		try {
			if (custId != null && str1 != null) {
				query.addCriteria(Criteria.where("productCharacteristic.name").is(str1).and("relatedParty._id")
						.is(custId).and("type").is("product"));
				return mongoTemplate.find(query, Product.class);
			} else if (custId != null && str2 != null) {
				query.addCriteria(Criteria.where("productCharacteristic.name").is(str2).and("relatedParty._id")
						.is(custId).and("type").is("product"));
				return mongoTemplate.find(query, Product.class);
			}

			else {
				if (str1 == null && str2 == null)
					;
				query.addCriteria(Criteria.where("productCharacteristic.name").is(str1).and("type").is("product"));
				LOGGER.info("productCharacteristics with openness and SliceAccessType successfully Retrieved");
				return mongoTemplate.find(query, Product.class);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unused")
	@Override
	public List<ProductRequest> prodDetailsByTokenAndIdOrId(String token, String customerId, String status) {

		Stopwatch stopwatch = Stopwatch.createStarted();
		stopwatch.stop();
		Long long1 = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		String s2 = Long.toString(long1);
		LOGGER.info("searchCriteria + ", s2);

		Set<String> technologySet = new HashSet<>(Arrays.asList("SLICE", "OTHER ONAP SERVICE"));
		Set<String> accessSet = new HashSet<>(Arrays.asList("Product_Slice_Access", "Direct_Slice_Access"));
		Set<String> simSet = new HashSet<>(Arrays.asList("SIM_Material", "SIM Connectivity"));
		Set<String> inActiveSimSet = new HashSet<>(
				Arrays.asList("Product_Slice_Access", "Direct_Slice_Access", "SIM_Material", "SIM Connectivity"));
		LOGGER.info("Products Details Are" + token + customerId);

		try {

			// Onloading for selfcare and DT admin in SIM_PRODUCTS
			if (token.equalsIgnoreCase(PIConstants.ALL) && status.equalsIgnoreCase(PIConstants.ACTIVE)) {

				List<ProductRequest> productRequestList = new ArrayList<ProductRequest>();

				Query query81 = new Query();
				if (customerId != null) {
					query81.addCriteria(Criteria.where("status").is("Active").and(PIConstants.TYPE).is(simMaterialType)
							.and("relatedParty._id").is(customerId));
					// query81.with(new Sort(Sort.Direction.DESC, "startDate"));
				} else {
					query81.addCriteria(
							Criteria.where("status").is("Active").and(PIConstants.TYPE).is(simMaterialType));
					// query81.with(new Sort(Sort.Direction.DESC, "startDate"));
				}
				List<Product> products = null;
				products = mongoTemplate.find(query81, Product.class);
				// Parent Sims list
				for (Product product2 : products) {
					ProductRequest productRequest12 = null;
					for (int i = 0; i < product2.getProductCharacteristic().size(); i++) {
						if (product2.getProductCharacteristic().get(i).getName().equalsIgnoreCase("imsi")) {
							String simCustId = product2.getRelatedPartyRef().get(0).getId();
							productRequest12 = new ProductRequest();
							BeanUtils.copyProperties(product2, productRequest12);
							String imsiValue = product2.getProductCharacteristic().get(i).getValue().toString();
							Query query1 = new Query();
							query1.addCriteria(Criteria.where("productCharacteristic.value").is(imsiValue)
									.and(PIConstants.TYPE).is(simDirectSliceAccess).and("status").is(PIConstants.ACTIVE)
									.and("relatedParty._id").is(simCustId));
							// query1.with(new Sort(Sort.Direction.DESC, "startDate"));

							List<Product> sliceProducts = mongoTemplate.find(query1, Product.class);
							productRequest12.setRelatedProductInventory(sliceProducts);
							productRequestList.add(productRequest12);

							LOGGER.info("Products Details Are " + productRequest12);
						}
					}

				}

				// It is sim level product which is having type =
				// PRODUCT_SLICE_ACCESS
				Criteria criteriaSim = new Criteria();
				if (customerId != null) {
					criteriaSim
							.orOperator(Criteria.where(PIConstants.TYPE).is(simProductSliceAccess),
									Criteria.where(PIConstants.TYPE).is(simconnectivity))
							.and("status").is(PIConstants.ACTIVE).and("relatedParty._id").is(customerId);
				} else {
					criteriaSim
							.orOperator(Criteria.where(PIConstants.TYPE).is(simProductSliceAccess),
									Criteria.where(PIConstants.TYPE).is(simconnectivity))
							.and("status").is(PIConstants.ACTIVE);
				}

				// Criteria criteriaSim = new Criteria();

				Query queryGetProductSlice1 = new Query(criteriaSim);
				queryGetProductSlice1.with(new Sort(Sort.Direction.DESC, "startDate"));

				List<Product> productSliceAccessList1 = mongoTemplate.find(queryGetProductSlice1, Product.class);

				ProductRequest productAccessRequest2 = null;
				for (Product productTemp : productSliceAccessList1) {
					productAccessRequest2 = new ProductRequest();
					BeanUtils.copyProperties(productTemp, productAccessRequest2);
					productRequestList.add(productAccessRequest2);

					LOGGER.info("Products Details Are " + productAccessRequest2);
				}

				return productRequestList;

			}

			/*
			 * It return the all slices and it will display on Non-Sim products tab(slice)
			 * 
			 */

			if (technologySet.contains(token) && null == customerId && status.equalsIgnoreCase(PIConstants.ACTIVE)) {
				List<ProductRequest> productRequestList = new ArrayList<ProductRequest>();
				Stopwatch stopwatch3 = Stopwatch.createStarted();
				stopwatch3.stop();
				LOGGER.info(customerId, token, stopwatch.elapsed(TimeUnit.MILLISECONDS), 200);
				Query query4 = new Query();
				query4.addCriteria(Criteria.where("status").is(PIConstants.ACTIVE).and("productRelationship").is(null)
						.orOperator(Criteria.where(PIConstants.TYPE).is(otherOnapServices),
								Criteria.where(PIConstants.TYPE).is(slice)));
				query4.with(new Sort(Sort.Direction.DESC, "startDate"));

				List<Product> productRequestList3 = mongoTemplate.find(query4, Product.class);
				ProductRequest productRequest3 = null;
				for (Product product : productRequestList3) {
					productRequest3 = new ProductRequest();
					BeanUtils.copyProperties(product, productRequest3);
					String productOfferCode = productRequest3.getProductOfferingRef().getId();
					String productCustID = productRequest3.getRelatedPartyRef().get(0).getId();
					Query query6 = new Query();

					query6.addCriteria(Criteria.where("status").is(PIConstants.ACTIVE).and("relatedParty._id")
							.is(productCustID).and("productRelationship")
							.elemMatch((Criteria.where("productRef._id")).is(productOfferCode))
							.orOperator(Criteria.where(PIConstants.TYPE).is(otherOnapServices),
									Criteria.where(PIConstants.TYPE).is(slice)));
					query6.with(new Sort(Sort.Direction.DESC, "startDate"));

					List<Product> productListwithRealtionOffer = mongoTemplate.find(query6, Product.class);

					productRequest3.setRelatedProductInventory(productListwithRealtionOffer);
					productRequestList.add(productRequest3);

					LOGGER.info("Products Details Are" + productRequest3);
					Long long2 = stopwatch.elapsed(TimeUnit.MILLISECONDS);
					String s3 = Long.toString(long1);
					LOGGER.info("searchCriteria + ", s3);
				}

				// AML related
				Query query20 = new Query();
				query20.addCriteria(Criteria.where("status").is(PIConstants.ACTIVE).and(PIConstants.TYPE).is(aml));
				query20.with(new Sort(Sort.Direction.DESC, "startDate"));

				List<Product> productRequestList20 = mongoTemplate.find(query20, Product.class);
				if (!productRequestList20.isEmpty()) {
					for (Product product : productRequestList20) {
						ProductRequest productRequest4 = new ProductRequest();
						BeanUtils.copyProperties(product, productRequest4);
						productRequestList.add(productRequest4);

						Stopwatch stopwatch5 = Stopwatch.createStarted();
						stopwatch5.stop();
						LOGGER.info(customerId, token, stopwatch.elapsed(TimeUnit.MILLISECONDS));
					}
				}
				// return productRequestList;
				/*
				 * Comparator<ProductRequest> c = (p1,p2) -> {
				 * 
				 * int p1Date = Integer.parseInt(p1.getStartDate()); int p2Date =
				 * Integer.parseInt(p2.getStartDate()); if(p1Date<p2Date) return -1; else
				 * if(p1Date>p2Date) return +1; else return 0 ;
				 * 
				 * }; return productRequestList.stream().sorted(c).collect(Collectors.toList());
				 */
				return productRequestList.stream()
						.sorted((o1, o2) -> (Integer.parseInt(o2.getStartDate()) - Integer.parseInt(o1.getStartDate())))
						.collect(Collectors.toList());

			}

			/*
			 * It return the slices for specific customer and it will display on Non-Sim
			 * products tab
			 */
			if (technologySet.contains(token) && customerId != null && status.equalsIgnoreCase(PIConstants.ACTIVE)) {
				List<ProductRequest> productRequestList = new ArrayList<ProductRequest>();
				Criteria criteria = new Criteria();
				criteria.orOperator(Criteria.where(PIConstants.TYPE).is(slice),
						Criteria.where(PIConstants.TYPE).is(otherOnapServices));
				Query query4 = new Query(criteria);
				query4.addCriteria(Criteria.where("relatedParty._id").is(customerId).and("status")
						.is(PIConstants.ACTIVE).and("productRelationship").is(null));
				query4.with(new Sort(Sort.Direction.DESC, "startDate"));
				List<Product> productRequestList3 = mongoTemplate.find(query4, Product.class);
				ProductRequest productRequest3 = null;
				for (Product product : productRequestList3) {
					productRequest3 = new ProductRequest();
					BeanUtils.copyProperties(product, productRequest3);
					String parentOfferCode = productRequest3.getProductOfferingRef().getId();
					Query query11 = new Query();
					query11.addCriteria(Criteria.where("relatedParty._id").is(customerId).and("status")
							.is(PIConstants.ACTIVE).and("productRelationship")
							.elemMatch((Criteria.where("productRef._id")).is(parentOfferCode))
							.orOperator(Criteria.where(PIConstants.TYPE).is(otherOnapServices),
									Criteria.where(PIConstants.TYPE).is(slice)));
					query11.with(new Sort(Sort.Direction.DESC, "startDate"));

					List<Product> productListwithRealtionOffer = mongoTemplate.find(query11, Product.class);
					productRequest3.setRelatedProductInventory(productListwithRealtionOffer);
					productRequestList.add(productRequest3);

					LOGGER.info("Products Details Are" + productRequest3);
				}

				// AML related
				Query query21 = new Query();
				query21.addCriteria(Criteria.where("relatedParty._id").is(customerId).and("status")
						.is(PIConstants.ACTIVE).and(PIConstants.TYPE).is(aml));
				query21.with(new Sort(Sort.Direction.DESC, "startDate"));
				List<Product> productRequestList21 = mongoTemplate.find(query21, Product.class);
				if (!productRequestList21.isEmpty()) {
					for (Product product : productRequestList21) {
						ProductRequest productRequest4 = new ProductRequest();
						BeanUtils.copyProperties(product, productRequest4);
						productRequestList.add(productRequest4);

					}
				}

				// return productRequestList;

				/*
				 * Comparator<ProductRequest> c = (p1,p2) -> {
				 * 
				 * int p1Date = Integer.parseInt(p1.getStartDate()); int p2Date =
				 * Integer.parseInt(p2.getStartDate()); if(p1Date<p2Date) return -1; else
				 * if(p1Date>p2Date) return +1; else return 0 ;
				 * 
				 * }; return productRequestList.stream().sorted(c).collect(Collectors.toList());
				 */
				return productRequestList.stream()
						.sorted((o1, o2) -> (Integer.parseInt(o2.getStartDate()) - Integer.parseInt(o1.getStartDate())))
						.collect(Collectors.toList());
			}
			/*
			 * It return the slices, which are customer's private slice and other public
			 * slices
			 */
			if (token.equalsIgnoreCase(PIConstants.ASSIGN_DIRECT_SLICE) && customerId != null) {
				List<ProductRequest> productRequestList = new ArrayList<ProductRequest>();
				Query query11 = new Query();
				Stopwatch stopwatch1 = Stopwatch.createStarted();
				stopwatch1.stop();
				LOGGER.info(customerId, token, stopwatch.elapsed(TimeUnit.MILLISECONDS), 200);
				query11.addCriteria(Criteria.where("status").is(PIConstants.ACTIVE).and("relatedParty._id")
						.is(customerId)
						.orOperator(Criteria.where(PIConstants.TYPE).is(otherOnapServices),
								Criteria.where(PIConstants.TYPE).is(slice))
						.andOperator(Criteria.where("productCharacteristic.name").is(PIConstants.SLICE_OPENNESS),
								Criteria.where("productCharacteristic.value").regex("public", "i"),
								Criteria.where("productCharacteristic.name").is("Slice AccessType"),
								Criteria.where("productCharacteristic.value").ne("Product")));// Slice AccessType
				query11.with(new Sort(Sort.Direction.DESC, "startDate"));

				List<Product> productRequestList41 = mongoTemplate.find(query11, Product.class);
				ProductRequest productRequest41 = null;
				for (Product product : productRequestList41) {
					productRequest41 = new ProductRequest();
					BeanUtils.copyProperties(product, productRequest41);
					productRequestList.add(productRequest41);
					LOGGER.info(" customer public slices ORN : " + product.getProductOrderRef().get(0).getId());
				}

				LOGGER.info("Products with customer public slices Are : "
						+ new ObjectMapper().writeValueAsString(productRequest41));

				Query query10 = new Query();
				Stopwatch stopwatch3 = Stopwatch.createStarted();
				stopwatch3.stop();
				LOGGER.info(customerId, token, stopwatch3.elapsed(TimeUnit.MILLISECONDS), 200);
				query10.addCriteria(Criteria.where("status").is(PIConstants.ACTIVE).and("relatedParty._id")
						.is(customerId)
						.orOperator(Criteria.where(PIConstants.TYPE).is(otherOnapServices),
								Criteria.where(PIConstants.TYPE).is(slice))
						.andOperator(Criteria.where("productCharacteristic.name").is(PIConstants.SLICE_OPENNESS),
								Criteria.where("productCharacteristic.value").regex("private", "i"),
								Criteria.where("productCharacteristic.name").is("Slice AccessType"),
								Criteria.where("productCharacteristic.value").ne("Product")));
				query10.with(new Sort(Sort.Direction.DESC, "startDate"));

				List<Product> productRequestList10 = mongoTemplate.find(query10, Product.class);
				ProductRequest productRequest10 = null;
				for (Product product : productRequestList10) {
					productRequest10 = new ProductRequest();
					BeanUtils.copyProperties(product, productRequest10);
					productRequestList.add(productRequest10);
					LOGGER.info("customer private slices ORN : " + product.getProductOrderRef().get(0).getId());
				}

				LOGGER.info("Products with customer private slices Are : "
						+ new ObjectMapper().writeValueAsString(productRequest10));

				Query query12 = new Query();
				query12.addCriteria(Criteria.where("status").is(PIConstants.ACTIVE).and("relatedParty._id")
						.ne(customerId)
						.orOperator(Criteria.where(PIConstants.TYPE).is(otherOnapServices),
								Criteria.where(PIConstants.TYPE).is(slice))
						.andOperator(Criteria.where("productCharacteristic.name").is(PIConstants.SLICE_OPENNESS),
								Criteria.where("productCharacteristic.value").regex("public", "i"),
								Criteria.where("productCharacteristic.name").is("Slice AccessType"),
								Criteria.where("productCharacteristic.value").ne("Product")));
				query12.with(new Sort(Sort.Direction.DESC, "startDate"));

				List<Product> productRequestList5 = mongoTemplate.find(query12, Product.class);
				ProductRequest productRequest5 = null;
				for (Product product : productRequestList5) {
					productRequest5 = new ProductRequest();
					BeanUtils.copyProperties(product, productRequest5);
					productRequestList.add(productRequest5);

					Long long2 = stopwatch.elapsed(TimeUnit.MILLISECONDS);

					LOGGER.info("other customer public slices ORN : " + product.getProductOrderRef().get(0).getId());
				}
				LOGGER.info("Products with other customer public slices Are : "
						+ new ObjectMapper().writeValueAsString(productRequest5));
				LOGGER.info("All Products Are : " + new ObjectMapper().writeValueAsString(productRequestList));

				return productRequestList.stream()
						.sorted((o1, o2) -> (Integer.parseInt(o2.getStartDate()) - Integer.parseInt(o1.getStartDate())))
						.collect(Collectors.toList());
			}

			if (token.equalsIgnoreCase("SIM_Material") && status.equalsIgnoreCase(PIConstants.ACTIVE)) {
				LOGGER.info("SIM material Active CH2");
				List<ProductRequest> productRequestList = new ArrayList<ProductRequest>();

				Query query81 = new Query();
				if (customerId != null) {
					query81.addCriteria(Criteria.where("status").is("Active").and(PIConstants.TYPE).is(simMaterialType)
							.and("relatedParty._id").is(customerId));
					query81.with(new Sort(Sort.Direction.DESC, "startDate"));

				} else {
					query81.addCriteria(
							Criteria.where("status").is("Active").and(PIConstants.TYPE).is(simMaterialType));
					query81.with(new Sort(Sort.Direction.DESC, "startDate"));
				}
				List<Product> products = null;
				products = mongoTemplate.find(query81, Product.class);
				// Parent Sims list
				for (Product product2 : products) {
					ProductRequest productRequest12 = null;
					for (int i = 0; i < product2.getProductCharacteristic().size(); i++) {
						if (product2.getProductCharacteristic().get(i).getName().equalsIgnoreCase("imsi")) {
							String simCustId = product2.getRelatedPartyRef().get(0).getId();
							productRequest12 = new ProductRequest();
							BeanUtils.copyProperties(product2, productRequest12);
							String imsiValue = product2.getProductCharacteristic().get(i).getValue().toString();
							Query query1 = new Query();
							query1.addCriteria(Criteria.where("productCharacteristic.value").is(imsiValue)
									.and(PIConstants.TYPE).is(simDirectSliceAccess).and("status").is(PIConstants.ACTIVE)
									.and("relatedParty._id").is(simCustId));
							query1.with(new Sort(Sort.Direction.DESC, "startDate"));

							List<Product> sliceProducts = mongoTemplate.find(query1, Product.class);
							productRequest12.setRelatedProductInventory(sliceProducts);

							productRequestList.add(productRequest12);

							LOGGER.info("Products Details Are " + productRequest12);
						}
					}

				}

				return productRequestList.stream()
						.sorted((o1, o2) -> (Integer.parseInt(o2.getStartDate()) - Integer.parseInt(o1.getStartDate())))
						.collect(Collectors.toList());
			}

			/* InActive status for Sim drop down */

			if (inActiveSimSet.contains(token) && status.equalsIgnoreCase(PIConstants.INACTIVE)) {

				List<ProductRequest> productRequestList = new ArrayList<ProductRequest>();
				Query queryAccess = new Query();

				if (simSet.contains(token)) {
					if (customerId == null) {
						queryAccess.addCriteria(Criteria.where("status").is(PIConstants.INACTIVE).orOperator(
								Criteria.where(PIConstants.TYPE).is(simMaterialType),
								Criteria.where(PIConstants.TYPE).is(simconnectivity)));
						queryAccess.with(new Sort(Sort.Direction.DESC, "startDate"));
					} else {
						queryAccess
								.addCriteria(Criteria.where("status").is(PIConstants.INACTIVE).and("relatedParty._id")
										.is(customerId).orOperator(Criteria.where(PIConstants.TYPE).is(simMaterialType),
												Criteria.where(PIConstants.TYPE).is(simconnectivity)));
						queryAccess.with(new Sort(Sort.Direction.DESC, "startDate"));
					}
					List<Product> productRequestListAccess = mongoTemplate.find(queryAccess, Product.class);
					ProductRequest productRequestAccess = null;
					for (Product product : productRequestListAccess) {
						productRequestAccess = new ProductRequest();
						BeanUtils.copyProperties(product, productRequestAccess);
						productRequestList.add(productRequestAccess);
						LOGGER.info("Products Details Are" + productRequestList);
					}

				}
				if (accessSet.contains(token)) {
					if (customerId == null) {
						queryAccess.addCriteria(
								Criteria.where("status").is(PIConstants.INACTIVE).and(PIConstants.TYPE).is(token));
						queryAccess.with(new Sort(Sort.Direction.DESC, "startDate"));
					} else {
						queryAccess.addCriteria(Criteria.where("status").is(PIConstants.INACTIVE).and(PIConstants.TYPE)
								.is(token).and("relatedParty._id").is(customerId));
						queryAccess.with(new Sort(Sort.Direction.DESC, "startDate"));
					}
					List<Product> productRequestListAccess = mongoTemplate.find(queryAccess, Product.class);
					ProductRequest productRequestAccess = null;
					for (Product product : productRequestListAccess) {
						productRequestAccess = new ProductRequest();
						BeanUtils.copyProperties(product, productRequestAccess);
						productRequestList.add(productRequestAccess);
						LOGGER.info("Products Details Are" + productRequestList);

					}
				}

				return productRequestList.stream()
						.sorted((o1, o2) -> (Integer.parseInt(o2.getStartDate()) - Integer.parseInt(o1.getStartDate())))
						.collect(Collectors.toList());
			}

			// InActive Non sim related

			if (technologySet.contains(token) && status.equalsIgnoreCase(PIConstants.INACTIVE)) {

				List<ProductRequest> productRequestList = new ArrayList<ProductRequest>();
				Query queryAccess = new Query();
				if (customerId == null) {
					queryAccess.addCriteria(
							Criteria.where("status").is(PIConstants.INACTIVE).and(PIConstants.TYPE).is("SLICE"));
					queryAccess.with(new Sort(Sort.Direction.DESC, "startDate"));
				} else {
					queryAccess.addCriteria(Criteria.where("status").is(PIConstants.INACTIVE).and("relatedParty._id")
							.is(customerId).and(PIConstants.TYPE).is("SLICE"));
					queryAccess.with(new Sort(Sort.Direction.DESC, "startDate"));

				}
				List<Product> productRequestListAccess = mongoTemplate.find(queryAccess, Product.class);

				ProductRequest productRequestAccess = null;
				for (Product product : productRequestListAccess) {
					productRequestAccess = new ProductRequest();
					BeanUtils.copyProperties(product, productRequestAccess);
					productRequestList.add(productRequestAccess);
					LOGGER.info("Products Details Are" + productRequestList);

				}
				return productRequestList.stream()
						.sorted((o1, o2) -> (Integer.parseInt(o2.getStartDate()) - Integer.parseInt(o1.getStartDate())))
						.collect(Collectors.toList());
			}

			/*
			 * If token is Direct_Slice_Access/Product_Slice_Access and customer Id is not
			 * NULL
			 */
			if (accessSet.contains(token) && customerId != null) {
				List<ProductRequest> productRequestList = new ArrayList<ProductRequest>();
				Query queryAccess = new Query();
				queryAccess.addCriteria(Criteria.where("status").is(PIConstants.ACTIVE).and("relatedParty._id")
						.is(customerId).and(PIConstants.TYPE).is(token));
				queryAccess.with(new Sort(Sort.Direction.DESC, "startDate"));
				List<Product> productRequestListAccess = mongoTemplate.find(queryAccess, Product.class);
				ProductRequest productRequestAccess = null;
				for (Product product : productRequestListAccess) {
					productRequestAccess = new ProductRequest();
					BeanUtils.copyProperties(product, productRequestAccess);
					productRequestList.add(productRequestAccess);
					LOGGER.info("Products Details Are" + productRequestList);

				}
				return productRequestList.stream()
						.sorted((o1, o2) -> (Integer.parseInt(o2.getStartDate()) - Integer.parseInt(o1.getStartDate())))
						.collect(Collectors.toList());
			}

			/*
			 * If token is Direct_Slice_Access/Product_Slice_Access and customer Id is not
			 * NULL
			 */
			if (accessSet.contains(token) && customerId == null) {
				List<ProductRequest> productRequestList = new ArrayList<ProductRequest>();
				Query queryAccess = new Query();
				queryAccess
						.addCriteria(Criteria.where("status").is(PIConstants.ACTIVE).and(PIConstants.TYPE).is(token));
				queryAccess.with(new Sort(Sort.Direction.DESC, "startDate"));

				List<Product> productRequestListAccess = mongoTemplate.find(queryAccess, Product.class);
				ProductRequest productRequestAccess = null;
				for (Product product : productRequestListAccess) {
					productRequestAccess = new ProductRequest();
					BeanUtils.copyProperties(product, productRequestAccess);
					productRequestList.add(productRequestAccess);
					LOGGER.info("Products Details Are" + productRequestList);

				}
				return productRequestList.stream()
						.sorted((o1, o2) -> (Integer.parseInt(o2.getStartDate()) - Integer.parseInt(o1.getStartDate())))
						.collect(Collectors.toList());
			}

			/*
			 * When user select the sim connectivity with customer, It returns only sim
			 * connectivity details
			 */
			if (token.equalsIgnoreCase(simconnectivity) && status.equalsIgnoreCase(PIConstants.ACTIVE)) {
				List<ProductRequest> productRequestList111 = new ArrayList<ProductRequest>();
				Query queryAccess = new Query();
				if (customerId != null) {
					queryAccess.addCriteria(Criteria.where("status").is(PIConstants.ACTIVE).and(PIConstants.TYPE)
							.is(simconnectivity).and("relatedParty._id").is(customerId));
					queryAccess.with(new Sort(Sort.Direction.DESC, "startDate"));

				} else {
					queryAccess.addCriteria(
							Criteria.where("status").is(PIConstants.ACTIVE).and(PIConstants.TYPE).is(simconnectivity));
					queryAccess.with(new Sort(Sort.Direction.DESC, "startDate"));
				}
				List<Product> productRequestListAccess111 = mongoTemplate.find(queryAccess, Product.class);

				ProductRequest productRequestAccess123 = null;
				for (Product product : productRequestListAccess111) {
					productRequestAccess123 = new ProductRequest();
					BeanUtils.copyProperties(product, productRequestAccess123);
					productRequestList111.add(productRequestAccess123);
					LOGGER.info("Products Details Are" + productRequestList111);

				}
				return productRequestList111.stream()
						.sorted((o1, o2) -> (Integer.parseInt(o2.getStartDate()) - Integer.parseInt(o1.getStartDate())))
						.collect(Collectors.toList());
			}

			/* InActive status for Sim drop down */

			if (inActiveSimSet.contains(token) && status.equalsIgnoreCase(PIConstants.INACTIVE)) {

				List<ProductRequest> productRequestList = new ArrayList<ProductRequest>();
				Query queryAccess = new Query();

				if (simSet.contains(token)) {
					if (customerId == null) {
						queryAccess.addCriteria(
								Criteria.where("status").is(PIConstants.INACTIVE).and(PIConstants.TYPE).is(token));
						queryAccess.with(new Sort(Sort.Direction.DESC, "startDate"));
					} else {

						queryAccess.addCriteria(Criteria.where("status").is(PIConstants.INACTIVE).and(PIConstants.TYPE)
								.is(token).and("relatedParty._id").is(customerId));
						queryAccess.with(new Sort(Sort.Direction.DESC, "startDate"));

					}
					List<Product> productRequestListAccess = mongoTemplate.find(queryAccess, Product.class);
					ProductRequest productRequestAccess = null;
					for (Product product : productRequestListAccess) {
						productRequestAccess = new ProductRequest();
						BeanUtils.copyProperties(product, productRequestAccess);
						productRequestList.add(productRequestAccess);
						LOGGER.info("Products Details Are" + productRequestList);
					}

				}
				if (accessSet.contains(token) && status.equalsIgnoreCase(PIConstants.INACTIVE)) {
					if (customerId == null) {
						queryAccess.addCriteria(
								Criteria.where("status").is(PIConstants.INACTIVE).and(PIConstants.TYPE).is(token));
						queryAccess.with(new Sort(Sort.Direction.DESC, "startDate"));
					} else {
						queryAccess.addCriteria(Criteria.where("status").is(PIConstants.INACTIVE).and(PIConstants.TYPE)
								.is(token).and("relatedParty._id").is(customerId));
						queryAccess.with(new Sort(Sort.Direction.DESC, "startDate"));

					}
					List<Product> productRequestListAccess = mongoTemplate.find(queryAccess, Product.class);
					ProductRequest productRequestAccess = null;
					for (Product product : productRequestListAccess) {
						productRequestAccess = new ProductRequest();
						BeanUtils.copyProperties(product, productRequestAccess);
						productRequestList.add(productRequestAccess);
						LOGGER.info("Products Details Are" + productRequestList);

					}
				}

				return productRequestList.stream()
						.sorted((o1, o2) -> (Integer.parseInt(o2.getStartDate()) - Integer.parseInt(o1.getStartDate())))
						.collect(Collectors.toList());
			}

			// InActive Non sim related

			if (technologySet.contains(token) && status.equalsIgnoreCase(PIConstants.INACTIVE)) {

				List<ProductRequest> productRequestList = new ArrayList<ProductRequest>();
				Query queryAccess = new Query();
				if (customerId == null) {
					queryAccess.addCriteria(
							Criteria.where("status").is(PIConstants.INACTIVE).and(PIConstants.TYPE).is("SLICE"));
					queryAccess.with(new Sort(Sort.Direction.DESC, "startDate"));
				} else {
					queryAccess.addCriteria(Criteria.where("status").is(PIConstants.INACTIVE).and("relatedParty._id")
							.is(customerId).and(PIConstants.TYPE).is("SLICE"));
					queryAccess.with(new Sort(Sort.Direction.DESC, "startDate"));

				}
				List<Product> productRequestListAccess = mongoTemplate.find(queryAccess, Product.class);

				ProductRequest productRequestAccess = null;
				for (Product product : productRequestListAccess) {
					productRequestAccess = new ProductRequest();
					BeanUtils.copyProperties(product, productRequestAccess);
					productRequestList.add(productRequestAccess);
					LOGGER.info("Products Details Are" + productRequestList);

				}
				return productRequestList.stream()
						.sorted((o1, o2) -> (Integer.parseInt(o2.getStartDate()) - Integer.parseInt(o1.getStartDate())))
						.collect(Collectors.toList());
			}

			/*
			 * If token is Direct_Slice_Access/Product_Slice_Access and customer Id is not
			 * NULL
			 */
			if (accessSet.contains(token) && customerId != null) {
				List<ProductRequest> productRequestList = new ArrayList<ProductRequest>();
				Query queryAccess = new Query();
				queryAccess.addCriteria(Criteria.where("status").is(PIConstants.ACTIVE).and("relatedParty._id")
						.is(customerId).and(PIConstants.TYPE).is(token));
				queryAccess.with(new Sort(Sort.Direction.DESC, "startDate"));
				List<Product> productRequestListAccess = mongoTemplate.find(queryAccess, Product.class);
				ProductRequest productRequestAccess = null;
				for (Product product : productRequestListAccess) {
					productRequestAccess = new ProductRequest();
					BeanUtils.copyProperties(product, productRequestAccess);
					productRequestList.add(productRequestAccess);
					LOGGER.info("Products Details Are" + productRequestList);

				}
				return productRequestList.stream()
						.sorted((o1, o2) -> (Integer.parseInt(o2.getStartDate()) - Integer.parseInt(o1.getStartDate())))
						.collect(Collectors.toList());
			}

			/*
			 * If token is Direct_Slice_Access/Product_Slice_Access and customer Id is not
			 * NULL
			 */
			if (accessSet.contains(token) && customerId == null) {
				List<ProductRequest> productRequestList = new ArrayList<ProductRequest>();
				Query queryAccess = new Query();
				queryAccess
						.addCriteria(Criteria.where("status").is(PIConstants.ACTIVE).and(PIConstants.TYPE).is(token));
				queryAccess.with(new Sort(Sort.Direction.DESC, "startDate"));

				List<Product> productRequestListAccess = mongoTemplate.find(queryAccess, Product.class);
				ProductRequest productRequestAccess = null;
				for (Product product : productRequestListAccess) {
					productRequestAccess = new ProductRequest();
					BeanUtils.copyProperties(product, productRequestAccess);
					productRequestList.add(productRequestAccess);
					LOGGER.info("Products Details Are" + productRequestList);

				}
				return productRequestList.stream()
						.sorted((o1, o2) -> (Integer.parseInt(o2.getStartDate()) - Integer.parseInt(o1.getStartDate())))
						.collect(Collectors.toList());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public HashMap<String, String> checkProdDetails(List<String> imsi, String customerId, String offerId) {
		HashMap<String, String> map = new HashMap<String, String>();

		LOGGER.info("Value Are " + customerId + offerId + imsi);
		try {
			for (int i = 0; i < imsi.size(); i++) {
				String productImsi = imsi.get(i);
				String productImsiNumber = productImsi.replace(",", "");
				LOGGER.info("Value Is " + productImsiNumber);
				Query query = new Query();
				query.addCriteria(Criteria.where("status").is(PIConstants.ACTIVE).and("relatedParty._id").is(customerId)
						.and("productOffering._id").is(offerId).and("productCharacteristic.name").is("IMSI")
						.and("productCharacteristic.value").is(productImsiNumber));
				List<Product> products = new ArrayList<>();
				products = mongoTemplate.find(query, Product.class);
				if (!products.isEmpty()) {
					map.put(productImsiNumber, "true");
					LOGGER.info(productImsiNumber + " Value Is True");
				} else {
					map.put(productImsiNumber, "false");
					LOGGER.info(productImsiNumber + " Value Is False");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;

	}

	@Override
	public HashMap<String, String> checkProdDetailssssss(List<String> imsi, String customerId, String offerId) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			if (!imsi.isEmpty()) {
				imsi.forEach(Imsione -> {
					Query query = new Query();
					query.addCriteria(Criteria.where("status").is(PIConstants.ACTIVE).and("relatedParty._id")
							.is(customerId).and("productOffering._id").is(offerId).and("productCharacteristic.name")
							.is("IMSI").and("productCharacteristic.value").is(Imsione));
					List<Product> products = new ArrayList<>();
					products = mongoTemplate.find(query, Product.class);
					if (!products.isEmpty()) {
						map.put(Imsione, "true");
						LOGGER.info(Imsione + "Value Is True");
					} else {
						map.put(Imsione, "false");
						LOGGER.info(Imsione + "Value Is False");
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;

	}

	@SuppressWarnings("unused")
	@Override
	public ResponseEntity<?> uploadCsv(List<ProductCsv> productone) {

		if (!productone.isEmpty()) {
			productone.forEach(csvone1 -> {

				if (csvone1.getoMOidentifier().contentEquals("")) {

					throw new ProductInventorycustomValidatorException("Fields Can't be null");
				}
				if (csvone1.getPriority().contentEquals("")) {

					throw new ProductInventorycustomValidatorException("Priority Can't be null");
				}
				if (csvone1.getOMOdescription().contentEquals("") || csvone1.getQCI() == null) {

					throw new ProductInventorycustomValidatorException("OMOdescription and QCI both can't be null");
				}
				if (csvone1.getQCI().contentEquals("")) {

					throw new ProductInventorycustomValidatorException("QCI  can't be null");
				}
				if (csvone1.getQI().contentEquals("")) {

					throw new ProductInventorycustomValidatorException("5QI  can't be null");
				}
				if (csvone1.getMaximumLatency() == null) {
					String mandatory = csvone1.getMaximumLatency();
					int i = Integer.parseInt(mandatory);
					throw new ProductInventorycustomValidatorException("Maximum Letancy  can't be null");
				}

				String mandatory1 = csvone1.getMaximumLatency();
				int i1 = Integer.parseInt(mandatory1);
				String mandatory2 = csvone1.getMinimumBitrateDownlink();
				int i2 = Integer.parseInt(mandatory2);
				String mandatory3 = csvone1.getMinimumBitrateUplink();
				int i3 = Integer.parseInt(mandatory3);
				// String mandatory5 = csvone1.getPrice();
				// int i6 = Integer.parseInt(mandatory5);
				String mandatory5 = csvone1.getPrice();
				float f = Float.parseFloat(mandatory5);

				LOGGER.info(csvone1 + " OMO Details");
				if (csvone1.getoMOidentifier() == null) {

					throw new ProductInventoryValidatorException("Fields Can't be null");
				}
				if (csvone1.getPriority().contentEquals("")) {

					throw new ProductInventorycustomValidatorException("Property Can't be null");
				}
				if (csvone1.getOMOdescription().contentEquals("") || csvone1.getQCI() == null) {

					throw new ProductInventorycustomValidatorException("OMOdescription and QCI both can't be null");
				}
				if (csvone1.getQCI().contentEquals("")) {

					throw new ProductInventorycustomValidatorException("QCI  can't be null");
				}
				if (csvone1.getQI().contentEquals("")) {

					throw new ProductInventorycustomValidatorException("5QI  can't be null");
				}
				if (csvone1.getMaximumLatency() == null) {
					String mandatory = csvone1.getMaximumLatency();
					int i = Integer.parseInt(mandatory);
					throw new ProductInventoryValidatorException("Maximum Letancy  can't be null");
				}
				if (csvone1.getMinimumBitrateDownlink() == null || csvone1.getMinimumBitrateUplink() == null) {
					String mandatory = csvone1.getMinimumBitrateDownlink();
					int i = Integer.parseInt(mandatory);
					String mandatory4 = csvone1.getMinimumBitrateUplink();
					int i5 = Integer.parseInt(mandatory4);
					throw new ProductInventoryValidatorException(
							"MinimumBitrateDownlink And MinimumBitrateUplink can't be null");
				}
				if (csvone1.getMinimumBitrateUplink() == null) {
					String mandatory4 = csvone1.getMinimumBitrateUplink();
					int i5 = Integer.parseInt(mandatory4);
					throw new ProductInventoryValidatorException(
							"MinimumBitrateDownlink And MinimumBitrate Uplink can't be null");
				}

				if (csvone1.getPrice() == null) {
					String mandatory7 = csvone1.getMinimumBitrateUplink();
					int i8 = Integer.parseInt(mandatory7);
					throw new ProductInventoryValidatorException("Price can't be null");
				}

				if (csvone1.getoMOidentifier() != null && csvone1.getStatus().contains("InActive")) {

					ProductCsv prodCsv1 = DAO.findById(csvone1.getoMOidentifier());

					if (prodCsv1 == null) {
						ProductCsv prodCsv2 = new ProductCsv();
						prodCsv2.setId(csvone1.getoMOidentifier());
						prodCsv2.setStatus("InActive");
						prodCsv2.setoMOidentifier(csvone1.getoMOidentifier());
						prodCsv2.setPriority(csvone1.getPriority());
						prodCsv2.setMaximumLatency(csvone1.getMaximumLatency());
						prodCsv2.setMinimumBitrateDownlink(csvone1.getMinimumBitrateDownlink());
						prodCsv2.setMinimumBitrateUplink(csvone1.getMinimumBitrateUplink());
						prodCsv2.setOMOdescription(csvone1.getOMOdescription());
						prodCsv2.setQCI(csvone1.getQCI());
						prodCsv2.setQI(csvone1.getQI());
						prodCsv2.setPrice(csvone1.getPrice());
						SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MM-dd ");
						dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
						prodCsv2.setStartDate(dateTimeInGMT.format(new Date()));
						mongoTemplate.save(prodCsv2);
					} else {
						ProductCsv prodCsv3 = new ProductCsv();
						prodCsv3.setId(prodCsv1.getoMOidentifier());
						prodCsv3.setStatus("InActive");
						prodCsv3.setoMOidentifier(prodCsv1.getoMOidentifier());
						prodCsv3.setPriority(prodCsv1.getPriority());
						prodCsv3.setMaximumLatency(prodCsv1.getMaximumLatency());
						prodCsv3.setMinimumBitrateDownlink(prodCsv1.getMinimumBitrateDownlink());
						prodCsv3.setMinimumBitrateUplink(prodCsv1.getMinimumBitrateUplink());
						prodCsv3.setModifiedDate(prodCsv1.getModifiedDate());
						prodCsv3.setStartDate(prodCsv1.getStartDate());
						prodCsv3.setOMOdescription(prodCsv1.getOMOdescription());
						prodCsv3.setQCI(prodCsv1.getQCI());
						prodCsv3.setQI(prodCsv1.getQI());
						prodCsv3.setPrice(prodCsv1.getPrice());
						SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MM-dd ");
						dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
						prodCsv3.setModifiedDate(dateTimeInGMT.format(new Date()));
						mongoTemplate.save(prodCsv3);
					}
				} else if (csvone1.getoMOidentifier() != null && csvone1.getStatus().contains("Active")) {
					ProductCsv prodCsv = new ProductCsv();
					ProductCsv prodCsv2 = DAO.findById(csvone1.getoMOidentifier());
					prodCsv.setId(csvone1.getoMOidentifier());
					prodCsv.setoMOidentifier(csvone1.getoMOidentifier());
					prodCsv.setPriority(csvone1.getPriority());
					prodCsv.setMaximumLatency(csvone1.getMaximumLatency());
					prodCsv.setMinimumBitrateDownlink(csvone1.getMinimumBitrateDownlink());
					prodCsv.setMinimumBitrateUplink(csvone1.getMinimumBitrateUplink());
					prodCsv.setOMOdescription(csvone1.getOMOdescription());
					prodCsv.setQCI(csvone1.getQCI());
					prodCsv.setPrice(csvone1.getPrice());
					if (prodCsv2 != null) {
						SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MM-dd ");
						dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
						prodCsv.setModifiedDate(dateTimeInGMT.format(new Date()));
					}
					prodCsv.setStatus(csvone1.getStatus());
					prodCsv.setQI(csvone1.getQI());
					if (prodCsv2 != null) {
						prodCsv.setStartDate(prodCsv2.getStartDate());
					} else {
						SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("yyyy-MM-dd ");
						dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
						prodCsv.setStartDate(dateTimeInGMT.format(new Date()));
					}
					mongoTemplate.save(prodCsv);
					LOGGER.info(prodCsv + " OMO Details which Inserted In DB");

				}
			});

		}
		return new ResponseEntity<>("File Uploaded Successfully", HttpStatus.OK);
	}

	@Override
	public HashMap<String, String> checkOfferDetails(String custId, List<String> sliceOfferCodes,
			String productOfferingCode) {
		HashMap<String, String> map = new HashMap<String, String>();
		LOGGER.info(custId + productOfferingCode + sliceOfferCodes + " check Offer Details");
		// sliceOfferCode should be product id within the product relationship
		try {

			for (String sliceOfferCode : sliceOfferCodes) {
				Query query = new Query();

				query.addCriteria(Criteria.where("relatedParty._id").is(custId).and("productRelationship")
						.elemMatch(Criteria.where("productRef._id").is(sliceOfferCode)).and("productOffering._id")
						.is(productOfferingCode));

				List<Product> productsList = mongoTemplate.find(query, Product.class);
				LOGGER.info(productsList + " Available Offer Details");
				if (!productsList.isEmpty()) {
					map.put(sliceOfferCode, PIConstants.TRUE);
					LOGGER.info(sliceOfferCode + " True");

				} else {
					map.put(sliceOfferCode, PIConstants.FALSE);
					LOGGER.info(sliceOfferCode + " False");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	/*
	 * @Override public List<ProductCsv> GetCsvDetails() { // return DAO.findAll();
	 * Query query = new Query(); List<ProductCsv> productcsv = new ArrayList<>();
	 * query.addCriteria(Criteria.where("Status").is("Active")); List<ProductCsv>
	 * products = DAO.findAll(); for (ProductCsv product : products) { if
	 * (product.getStatus().equalsIgnoreCase("Active")) { productcsv.add(product); }
	 * } return productcsv; }
	 */

	@Override
	public List<ProductCsv> GetCsvDetails(String token) {

		List<ProductCsv> productcsvList = new ArrayList<>();
		if (null != token) {
			if (token.equalsIgnoreCase("All")) {
				return DAO.findAll();
			}

		} else {
			List<ProductCsv> result = DAO.findAll();
			for (ProductCsv productCsv : result) {
				if (productCsv.getStatus().equalsIgnoreCase("Active")) {
					productcsvList.add(productCsv);
				}
			}
			/*
			 * Query query1 = new Query();
			 * query1.addCriteria(Criteria.where("Status").is("Active")); productcsv =
			 * mongoTemplate.find(query1, ProductCsv.class);
			 */
		}

		return productcsvList;
	}

	@Override
	public List<Product> getProductByExternalId(String externalId) {

		Query query = new Query();

		query.addCriteria(Criteria.where("productOrder._id").is(externalId));
		return mongoTemplate.find(query, Product.class);

	}

	public List<Product> getProductByInternalId(String externalId) {

		Query query = new Query();

		query.addCriteria(Criteria.where("_id").is(externalId));
		return mongoTemplate.find(query, Product.class);

	}

	@Override
	public Product deleteById(String Id) throws JsonProcessingException {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id".trim()).is(Id.trim()));

		Product product = mongoTemplate.findOne(query, Product.class);

		UUID uuid = Generators.timeBasedGenerator().generate();
		long ids = uuid.node();
		String EventId = Long.toString(ids);
		String eventDate = LocalDateTime.now().toString();

		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

		ProductOrderNotification event = new ProductOrderNotification(this, EventId, eventDate,
				PIConstants.REMOVE_NOTIFICATION5, product);

		// publishing the event here
		publisher.publishEvent(event);

		return mongoTemplate.findAndRemove(query, Product.class);
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;

	}

	@SuppressWarnings({ "unused", "unchecked", "static-access", "rawtypes" })
	@Override
	public List<Product> latestone(String token, String customerId, String status, String pageNumber, String pageSize,
			String filterKey, String filterValue) {

		Set<String> technologySet = new HashSet<>(Arrays.asList("SLICE", "OTHER ONAP SERVICE"));
		Set<String> accessSet = new HashSet<>(Arrays.asList("Product_Slice_Access", "Direct_Slice_Access"));
		Set<String> simSet = new HashSet<>(Arrays.asList("SIM_Material", "SIM Connectivity"));
		Set<String> allSet = new HashSet<>(Arrays.asList("All"));
		Set<String> imsiSet = new HashSet<>(Arrays.asList("IMSI", "IMSI1"));
		LOGGER.info("Products Details Are" + token + customerId);
		try {
			String queryToken = "";
			List<String> data = null;
			String query_CustmerId = "";
			if (customerId != null) {
				query_CustmerId = ", \"relatedParty._id\": \"" + customerId + "\"";
			}

			if (token.equalsIgnoreCase("All") && status.equalsIgnoreCase("inactive")) {
				queryToken = "[ \"SIM_Material\", \"SIM Connectivity\", \"Product_Slice_Access\", \"Direct_Slice_Access\"]";
			} else if (token.equalsIgnoreCase("All")) {
				queryToken = "[ \"SIM_Material\", \"SIM Connectivity\", \"Product_Slice_Access\", \"Direct_Slice_Access\"]";
			} else if (token.equalsIgnoreCase("SLICE")) {
				queryToken = "[ \"SLICE\", \"AML\"]";
			} else {
				queryToken = "[\"" + token + "\"]";
			}

			if (status.equalsIgnoreCase("InActive")) {
				String query = productInventoryUtility.QUERY_InActive.replace("{token}", queryToken)
						.replace("{customerId}", query_CustmerId);

				Collection<? extends Product> result = (Collection<? extends Product>) getMongoData(hostName,
						portNumber, dbName, query);
				List<Product> prodlist = new ArrayList<>();
				prodlist.addAll(result);

				/*
				 * List<BasicDBList> result = (List<BasicDBList>) getMongoData(hostName,
				 * portNumber, dbName, query);
				 * 
				 * List<Product> prodlist = new ArrayList<>(); List<String> ids = new
				 * ArrayList<String>();
				 * 
				 * for (DBObject products : result) { String id = (String) products.get("_id");
				 * ids.add(id);
				 * 
				 * } prodlist = (List<Product>) dao.findAll(ids); prodlist =
				 * prodlist.stream().sorted(Comparator.comparing(Product::getStartDate))
				 * .collect(Collectors.toList());
				 * 
				 */
				return prodlist;

			} else if (token.equalsIgnoreCase("SLICE")) {
				String query = productInventoryUtility.SLICE_QUERY.replace("{token}", queryToken)
						.replace("{customerId}", query_CustmerId);

				Collection<? extends Product> result = (Collection<? extends Product>) getMongoData(hostName,
						portNumber, dbName, query);
				List<Product> prodlist = new ArrayList<>();
				prodlist.addAll(result);

				/*
				 * List<BasicDBList> result = (List<BasicDBList>) getMongoData(hostName,
				 * portNumber, dbName, query);
				 * 
				 * List<Product> prodlist = new ArrayList<>(); List<String> ids = new
				 * ArrayList<String>();
				 * 
				 * for (DBObject products : result) { String id = (String) products.get("_id");
				 * ids.add(id);
				 * 
				 * } prodlist = (List<Product>) dao.findAll(ids); prodlist =
				 * prodlist.stream().sorted(Comparator.comparing(Product::getStartDate).reversed
				 * ()) .collect(Collectors.toList());
				 */

				LOGGER.info("list of products from DB = " + new ObjectMapper().writeValueAsString(prodlist));

				try {
					List<Product> prodlist1 = new ArrayList<>();

					Collections.reverse(prodlist);
					LOGGER.info("list of products from sorted list by collection = "
							+ new ObjectMapper().writeValueAsString(prodlist));

					/*
					 * prodlist1 =
					 * prodlist.stream().sorted(Comparator.comparing(Product::getStartDate).reversed
					 * ()) .collect(Collectors.toList());
					 * 
					 * LOGGER.info("list of products from sorted list java8 = " + new
					 * ObjectMapper().writeValueAsString(prodlist1));
					 */
				} catch (Exception e) {
					e.printStackTrace();
				}

				return prodlist;
			} else {
				String query = productInventoryUtility.IMSI_GROUP_QUERY_DATA.replace("{token}", queryToken)
						.replace("{customerId}", query_CustmerId);
				LOGGER.info("query for token All" + query);

				Collection<? extends Product> result = (Collection<? extends Product>) getMongoData(hostName,
						portNumber, dbName, query);

				List<Product> prodlist = new ArrayList<>();
				prodlist.addAll(result);
				ProductCount prod = new ProductCount();
				prod.setRelatedProductInventory(prodlist);

				// LOGGER.info("list of products from DB = " + new
				// ObjectMapper().writeValueAsString(prodlist));

				try {
					List<Product> prodlist1 = new ArrayList<>();

					Collections.reverse(prodlist);

					LOGGER.info("list of products from sorted list by collection = "
							+ new ObjectMapper().writeValueAsString(prodlist));

					/*
					 * prodlist1 = prodlist.stream().sorted((o1, o2) ->
					 * (Integer.parseInt(o2.getStartDate()) - Integer.parseInt(o1.getStartDate())))
					 * .collect(Collectors.toList());
					 * 
					 * 
					 * LOGGER.info("list of products from sorted list java8 = " + new
					 * ObjectMapper().writeValueAsString(prodlist1));
					 */
				} catch (Exception e) {
					e.printStackTrace();
				}

				/*
				 * List<BasicDBList> result = (List<BasicDBList>) getMongoData(hostName,
				 * portNumber, dbName, query);
				 * 
				 * List<Product> prodlist = new ArrayList<Product>(); List<String> ids = new
				 * ArrayList<String>();
				 * 
				 * for (DBObject products : result) { String id = (String) products.get("_id");
				 * ids.add(id);
				 * 
				 * } prodlist = (List<Product>) dao.findAll(ids); ProductCount prod = new
				 * ProductCount();
				 * 
				 * prod.setRelatedProductInventory(prodlist);
				 * 
				 * prodlist =
				 * prodlist.stream().sorted(Comparator.comparing(Product::getStartDate))
				 * .collect(Collectors.toList());
				 */

				/*
				 * z result =
				 * result.stream().sorted(Comparator.comparing(Product::getStartDate).reversed()
				 * ) .collect(Collectors.toList());
				 */

				/*
				 * ObjectMapper objectMapper = new ObjectMapper(); String modelCustomerResponse
				 * = null; try { modelCustomerResponse =
				 * objectMapper.writeValueAsString(result);
				 * 
				 * } catch (JsonProcessingException e1) { e1.getMessage(); }
				 * 
				 * try { result = objectMapper.readValue(modelCustomerResponse, new
				 * TypeReference<List<Product>>() { }); } catch (JsonParseException e) {
				 * e.getMessage(); } catch (JsonMappingException e) { e.getMessage(); } catch
				 * (IOException e) { e.getStackTrace(); }
				 */

				return prodlist;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@SuppressWarnings({ "unused", "unchecked", "rawtypes", "resource", "deprecation" })
	public List<?> getMongoData(String hostName, int portNumber, String dbName, String query) {

		DB db = new MongoClient(hostName, portNumber).getDB(dbName);

		Map<String, Object> data = (Map<String, Object>) db.eval(query);
		Map<String, Product> data1 = (Map<String, Product>) db.eval(query);
		return ((List) data.get("_batch"));

	}

	@Override
	public HashMap<String, String> checkOfferDetailsWithORNS(CheckOfferDetailsRequest checkOfferDetailsRequest)
			throws Exception {

		HashMap<String, String> map = new HashMap<String, String>();
		LOGGER.info(checkOfferDetailsRequest + " check Offer Details");
		try {
			checkOfferDetailsRequest.getRelation().forEach(firstRecord -> {
				Query query = new Query();
				query.addCriteria(Criteria.where("relatedParty._id").is(checkOfferDetailsRequest.getCustId())
						.and("productRelationship")
						.elemMatch(Criteria.where("productRef._id").is(firstRecord.getSliceOfferCodes()))
						.and("productOffering._id").is(checkOfferDetailsRequest.getProductOfferingCode())
						.and("productOrder._id").is(firstRecord.getOrderReferenceNumbers()));

				List<Product> productsList = mongoTemplate.find(query, Product.class);
				LOGGER.info(productsList + " Available Offer Details");
				if (!productsList.isEmpty()) {
					map.put(firstRecord.getSliceOfferCodes() + "_" + firstRecord.getOrderReferenceNumbers(),
							PIConstants.TRUE);
					LOGGER.info(firstRecord + " True");

				} else {
					map.put(firstRecord.getSliceOfferCodes() + "_" + firstRecord.getOrderReferenceNumbers(),
							PIConstants.FALSE);
					LOGGER.info(firstRecord + " False");
				}
			}

			);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	@Override
	public CustomerAssociatedSims retriveImsisByCustId(String cutomerId) {
		CustomerAssociatedSims customerAssociatedSims = new CustomerAssociatedSims();
		Set<Object> imsiNumbers = new HashSet();
		Query query = new Query();

		query.addCriteria(Criteria.where("status").is("Active").and("type").is(simMaterialType).and("relatedParty._id")
				.is(cutomerId));

		List<Product> products = mongoTemplate.find(query, Product.class);

		customerAssociatedSims.setCustId(cutomerId);
		for (Product product : products) {
			List<ProductCharacteristic> productCharacteristic = product.getProductCharacteristic();
			productCharacteristic.forEach(i -> {
				if (i.getName().equalsIgnoreCase("IMSI")) {
					imsiNumbers.add(i.getValue());
				}
			});
		}
		customerAssociatedSims.setSims(imsiNumbers);
		return customerAssociatedSims;
	}

	@Override
	public List<CustomerAssociatedSlices> retrivesliceDetailsByCustId(String cutomerId) {

		List<CustomerAssociatedSlices> customerSlicesList = new ArrayList<CustomerAssociatedSlices>();

		Map<String, CustomerAssociatedSlices> customerSlices = new HashMap<String, CustomerAssociatedSlices>();

		Query query = new Query();
		query.addCriteria(
				Criteria.where("status").is("Active").and("type").in(aml, slice).and("relatedParty._id").is(cutomerId));
		LOGGER.info("" + query);
		List<Product> products = mongoTemplate.find(query, Product.class);
		String nssai = "";
		String sdl = "";
		String sstl = "";
		List<String> listOfKeys = new ArrayList<String>();
		Boolean statusofSlice = true;
		Boolean statusofAml = true;
		for (Product product : products) {
			CustomerAssociatedSlices customerAssociatedSlices = new CustomerAssociatedSlices();
			customerAssociatedSlices.setType(product.getType());
			customerAssociatedSlices.setOfferName(product.getProductOfferingRef().getName());

			if (!product.getProductOfferingRef().getName().equalsIgnoreCase("Firewall")
					&& !product.getType().equalsIgnoreCase("AML")) {
				sstl = new String();
				sdl = new String();
				nssai = new String();
				for (ProductCharacteristic pr : product.getProductCharacteristic()) {

					if (pr.getName().equalsIgnoreCase("SST")) {

						if (null != pr.getValue()) {
							sstl = pr.getValue().toString();
							customerAssociatedSlices.setSst(pr.getValue());
						} else {
							sstl = "NULL";
						}

					}

					if (pr.getName().equalsIgnoreCase("sd")) {
						if (null == pr.getValue() || pr.getValue().toString().equalsIgnoreCase("")
								|| pr.getValue().toString().equalsIgnoreCase("000000")
								|| pr.getValue().toString().equalsIgnoreCase("Null")
								|| pr.getValue().toString().equalsIgnoreCase("null")) {
							customerAssociatedSlices.setSd("NULL");
							sdl = "NULL";
						} else {
							customerAssociatedSlices.setSd(pr.getValue());
							sdl = pr.getValue().toString();
						}

					}

				}

				nssai = sstl.trim() + sdl.trim();
				LOGGER.info("nssai = " + nssai);
				if (statusofSlice) {
					listOfKeys.add(nssai.trim());
					customerSlices.put(nssai, customerAssociatedSlices);
					statusofSlice = false;
				} else if (!listOfKeys.contains(nssai.trim())) {
					listOfKeys.add(nssai.trim());

					customerSlices.put(nssai, customerAssociatedSlices);
				}

			} else if (product.getType().equalsIgnoreCase("AML")) {
				LOGGER.info("In AML block");
				for (ProductCharacteristic p : product.getProductCharacteristic()) {

					String replaceSpace = p.getName();
					String values = replaceSpace.replaceAll("\\s", "");

					if (values.equalsIgnoreCase("i_AMLapplicationendpoint")
							|| values.equalsIgnoreCase("i_AML application endpoint")) {

						customerAssociatedSlices.setServiceId(p.getValue());
						if (null != p.getValue()) {
							nssai = p.getValue().toString();
						} else {
							nssai = "a";
						}

						LOGGER.info("ServiceID = " + nssai);
					}

				}
				if (statusofAml) {
					listOfKeys.add(nssai.trim());
					customerSlices.put(nssai, customerAssociatedSlices);
					statusofAml = false;
				} else if (!listOfKeys.contains(nssai.trim())) {
					listOfKeys.add(nssai.trim());
					customerSlices.put(nssai, customerAssociatedSlices);
				}

			}

		}
		LOGGER.info("Final MAP Value :" + customerSlices);
		customerSlices.forEach((k, v) -> {
			customerSlicesList.add(v);
		});

		return customerSlicesList;

	}

	@Override
	public List<Product> acticeServiceByImsi(String imsi) {

		LOGGER.info("IMSI value : " + imsi);
		List<Product> acticeImsiProducts = new ArrayList<Product>();
		Query query = new Query();
		query.addCriteria(Criteria.where("status").is(PIConstants.ACTIVE).and("type").ne("SIM_Material")
				.and("productCharacteristic.value").is(imsi));
		LOGGER.info("Query :" + query.toString());
		List<Product> prdList = new ArrayList<Product>();
		try {
			acticeImsiProducts = mongoTemplate.find(query, Product.class);

			LOGGER.info("Before filter  :" + new ObjectMapper().writeValueAsString(acticeImsiProducts));

			List<String> simList = new ArrayList<>();
			simList.add("iccid");
			simList.add("msisdn");
			simList.add("imsi");
			for(Product product:acticeImsiProducts) {
				product.getProductCharacteristic().stream().forEach(p -> {
					
					if(simList.contains(p.getName().toLowerCase())) {
						p.setIsVisible(true);
					}
					
				});
			}

			prdList = acticeImsiProducts.stream().filter(p -> !p.getType().equalsIgnoreCase("SLICE"))
					.collect(Collectors.toList());

			LOGGER.info("After filter  :" + new ObjectMapper().writeValueAsString(prdList));

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LOGGER.info("Query :" + query.toString());
		return prdList;
	}

	public Product activateProductSubscription(Product productToBeUpdated) {

		return dao.save(productToBeUpdated);

	}

	public String upDateProductRemarkByProductInstanceID(Product product, String remark) throws Exception {

		String imsi = null;
		product.getProductCharacteristic().forEach(pc -> {
			if (pc.getName().equalsIgnoreCase("remark")) {
				pc.setValue(remark);
				LOGGER.info("Remark : " + remark + "  :  " + pc.getValue());
			}

		});

		for (ProductCharacteristic pc : product.getProductCharacteristic()) {
			if (pc.getName().equalsIgnoreCase("IMSI")) {
				imsi = pc.getValue().toString();

			}
		}
		try {
			LOGGER.info("Product value after  : " + new ObjectMapper().writeValueAsString(product));
			dao.save(product);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		return imsi;
	}

	public HashMap<String, String> getRemarkByListIMSI(List<String> imsi) {
		HashMap<String, String> map = new HashMap<String, String>();

		LOGGER.info("List of IMSI Are " + imsi);
		try {
			for (int i = 0; i < imsi.size(); i++) {
				String productImsi = imsi.get(i);
				String productImsiNumber = productImsi.replace(",", "");
				LOGGER.info("Single IMSI Value Is " + productImsiNumber);
				Query query = new Query();
				query.addCriteria(Criteria.where("status").is(PIConstants.ACTIVE).and("productCharacteristic.name")
						.is("IMSI").and("productCharacteristic.value").is(productImsiNumber));
				List<Product> products = new ArrayList<>();
				products = mongoTemplate.find(query, Product.class);
				if (!products.isEmpty()) {
					for (ProductCharacteristic productCharacteristic : products.get(0).getProductCharacteristic()) {
						if (productCharacteristic.getName().equalsIgnoreCase("remark")) {
							map.put(productImsiNumber, productCharacteristic.getValue().toString());
							LOGGER.info(productImsiNumber + " Value Is" + productCharacteristic.getValue().toString());
						}
					}

				} else {
					LOGGER.info("IMSI not found " + productImsiNumber);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public String getUDMProfileIDByImsi(String imsi) {

		LOGGER.info("IMSI value : " + imsi);
		String udmProfileID = null;
		List<Product> acticeImsiProducts = new ArrayList<Product>();

		Query query = new Query();
		query.addCriteria(Criteria.where("status").is(PIConstants.ACTIVE).and("type").is("SIM Connectivity")
				.and("productCharacteristic.value").is(imsi));
		try {
			acticeImsiProducts = mongoTemplate.find(query, Product.class);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (!acticeImsiProducts.isEmpty()) {
			for (ProductCharacteristic productCharacteristic : acticeImsiProducts.get(0).getProductCharacteristic()) {
				if (productCharacteristic.getName().equalsIgnoreCase("profileID")) {

					LOGGER.info("UDM profile id value : " + productCharacteristic.getValue().toString());
					udmProfileID = productCharacteristic.getValue().toString();
				}
			}

		}

		return udmProfileID;
	}

	public Product getSimMaterialServiceByImsi(String imsi) {
		LOGGER.info("IMSI value : " + imsi);
		List<Product> acticeImsiProducts = new ArrayList<Product>();

		Query query = new Query();
		query.addCriteria(Criteria.where("status").is(PIConstants.ACTIVE).and("type").is("SIM_Material")
				.and("productCharacteristic.value").is(imsi));
		try {
			acticeImsiProducts = mongoTemplate.find(query, Product.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return acticeImsiProducts.get(0);
	}

	public List<DirectSliceData> fetchDirectSliceDataByIMSI(String imsi) {

		LOGGER.info("IMSI value : " + imsi);
		List<Product> fetchDirectSliceData = new ArrayList<Product>();
		List<DirectSliceData> directSliceDataList = new ArrayList<>();
		DirectSliceData directSliceData = null;
		Query query = new Query();
		query.addCriteria(Criteria.where("status").is(PIConstants.ACTIVE).and("type").is("Direct_Slice_Access")
				.and("productCharacteristic.value").is(imsi));
		try {
			fetchDirectSliceData = mongoTemplate.find(query, Product.class);

			for (Product product : fetchDirectSliceData) {
				directSliceData = new DirectSliceData();
				String sst = null;
				String sd = null;
				String PLMN = null;
				String dnnList = "NA";
				for (ProductCharacteristic productCharacteristic : product.getProductCharacteristic()) {

					if (productCharacteristic.getName().equalsIgnoreCase("sst")) {
						sst = productCharacteristic.getValue().toString();
						LOGGER.info("sst :" + sst);
					}
					if (productCharacteristic.getName().equalsIgnoreCase("sd")) {
						sd = productCharacteristic.getValue().toString();
						LOGGER.info("sd :" + sd);
					}
					if (productCharacteristic.getName().equalsIgnoreCase("PLMN")) {
						PLMN = productCharacteristic.getValue().toString();
						LOGGER.info("PLMN :" + PLMN);
					}
					if (productCharacteristic.getName().equalsIgnoreCase("dnnList")) {
						dnnList = productCharacteristic.getValue().toString();
						LOGGER.info("dnnList :" + dnnList);
					}

				}

				if (sst != null) {
					directSliceData.setSst(sst);
				}
				if (sd != null) {
					directSliceData.setSd(sd);
				}
				if (PLMN != null) {
					directSliceData.setPlmn(PLMN);
				}
				if (dnnList != null) {
					directSliceData.setDnnList(dnnList);
				}
				LOGGER.info("DA  :" + directSliceData);
				if (sst != null && sd != null && PLMN != null && dnnList != null) {
					directSliceDataList.add(directSliceData);
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return directSliceDataList;
	}

	@SuppressWarnings({ "null" })
	public List<ProductInventoryMain> getNewProdInventResp(String externalId)
			throws IOException, org.json.simple.parser.ParseException {
		List<ProductInventoryMain> prodmainList = new ArrayList<>();
		ProductInventoryMain prodmain = null;
		Product productold = null;
		Product_ productNew = null;
		ProductOffering_ offeringNew = null;
		ProductSpecification_ productSpecificationNew = null;
		ProductCharacteristic_ productCharacteristicNew = null;
		RelatedParty_ partyNew = null;
		ProductPrice_ productPrice = null;
		Object obj = null;
		List<Product> products = null;
		try {
			// Read JSON file for local
			// JSONParser jsonParser = new JSONParser();
			// FileReader reader = new
			// FileReader("./src/main/resources/productinventorymain.json");
			// obj = jsonParser.parse(reader);
			logger.info("Caling by external ID");
			products = getProductByExternalId(externalId);
			if (products.size() == 0) {
				logger.info("Calling with internal ID");
				products = getProductByInternalId(externalId);
			}
			if (products.size() == 0) {
				logger.info("Calling with imsi or msisdn");
				products = acticeServiceByImsi(externalId.trim());
			}

		} catch (Exception e) {
			e.getStackTrace();
		}

		// Product productModel = getModelOfferResponse(productold , obj); //for local
		logger.info("productInvResponse Respnose  :" + new ObjectMapper().writeValueAsString(products));

		for (Product prod : products) {
			prodmain = new ProductInventoryMain();
			prodmain.setId(prod.getId());
			prodmain.setHref(
					"https://playground.spacegate.telekom.de/nwstest/external/v1/5gbss/product/" + prod.getId());
			prodmain.setDescription(prod.getDescription());
			prodmain.setType(prod.getType());
			for (ProductOrder order : prod.getProductOrderRef()) {
				if (null != order.getOrderDate() && !order.getOrderDate().equalsIgnoreCase("")) {
					prodmain.setOrderDate(EpochToZulu(order.getOrderDate() + "000"));
				}

			}
			if (null != prod.getStartDate() && !prod.getStartDate().equalsIgnoreCase("")) {
				prodmain.setStartDate(EpochToZulu(prod.getStartDate() + "000"));
			}
			if (null != prod.getTerminationDate() && !prod.getTerminationDate().equalsIgnoreCase("")) {
				prodmain.setTerminationDate(EpochToZulu(prod.getTerminationDate()));
			}

			prodmain.setStatus(prod.getStatus());
			productSpecificationNew = new ProductSpecification_();
			productNew = new Product_();
			productSpecificationNew.setName(prod.getName());
			productSpecificationNew.setId(prod.getProductSpecificationRef().getId());
			productNew.setProductSpecification(productSpecificationNew);
			List<Product_> productListNew = new ArrayList<Product_>();
			List<ProductCharacteristic_> productCharacteristiListNew = new ArrayList<ProductCharacteristic_>();
			for (ProductCharacteristic productCharacteristic : prod.getProductCharacteristic()) {
				productCharacteristicNew = new ProductCharacteristic_();
				if (productCharacteristic.getIsVisible() == true && productCharacteristic.getIsVisible()) {

					productCharacteristicNew.setName(productCharacteristic.getName());
					productCharacteristicNew.setValue(productCharacteristic.getValue().toString());
					productCharacteristiListNew.add(productCharacteristicNew);
					productNew.setProductCharacteristic(productCharacteristiListNew);

				}
			}
			productListNew.add(productNew);
			// set ProductOffering_
			offeringNew = new ProductOffering_();
			offeringNew.setId(prod.getProductOfferingRef().getId());
			offeringNew.setName(prod.getProductOfferingRef().getName());
			offeringNew.setHref(prod.getProductOfferingRef().getHref());

			// set realtedParty_
			List<RelatedParty_> relatedPartyList = new ArrayList<RelatedParty_>();
			for (RelatedParty party : prod.getRelatedPartyRef()) {
				partyNew = new RelatedParty_();

				partyNew.setId(party.getId());
				partyNew.setName(party.getName());
				partyNew.setRole(party.getRole());
				partyNew.setReferredType(party.getReferredType());

			}
			relatedPartyList.add(partyNew);
			List<ProductPrice_> productPriceList = new ArrayList<>();
			for (ProductPrice prdPrice : prod.getProductPrice()) {

				productPrice = new ProductPrice_();
				Price_ price = new Price_();
				com.dtag.bm.service.product.inventory.service.type.DutyFreeAmount_ taxIncludedAmount = new com.dtag.bm.service.product.inventory.service.type.DutyFreeAmount_();
				if (prdPrice.getPriceType().equalsIgnoreCase("recuring")) {
					productPrice.setPriceType("recurring");
				} else {
					productPrice.setPriceType(prdPrice.getPriceType());
				}
				if (null != prdPrice.getRecurringChargePeriod()) {
					productPrice.setRecurringChargePeriod(prdPrice.getRecurringChargePeriod());
				}
				taxIncludedAmount.setUnit("Euro");
				taxIncludedAmount.setValue(prdPrice.getPrice().getTaxIncludedAmount().getValue());
				price.setTaxIncludedAmount(taxIncludedAmount);
				productPrice.setPrice(price);
				productPriceList.add(productPrice);
			}
			prodmain.setProductPrice(productPriceList);

			// adding to main popjo class
			productNew.setProductCharacteristic(productCharacteristiListNew);
			prodmain.setProduct(productListNew);
			prodmain.setProductOffering(offeringNew);
			prodmain.setRelatedParty(relatedPartyList);
			prodmainList.add(prodmain);

		}

		return prodmainList;
	}

	private Product getModelOfferResponse(Product prodInvMain, Object creationList) {
		ObjectMapper objectMapper = new ObjectMapper();
		String modelofferResponse = null;
		try {
			modelofferResponse = objectMapper.writeValueAsString(creationList);

		} catch (JsonProcessingException e1) {
			e1.getMessage();
		}
		try {
			prodInvMain = objectMapper.readValue(modelofferResponse, new TypeReference<Product>() {
			});

		} catch (JsonParseException e) {
			e.getMessage();
		} catch (JsonMappingException e) {
			e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			e.getStackTrace();
		}
		return prodInvMain;
	}
	
	//mohit changes for get offer data by sst sd dnn and type
	public List<Product> queryToGetMatchingOrder(String sst,String sd,String dnn) {
		Criteria criteria = new Criteria();		
		criteria.andOperator(Criteria.where("productCharacteristic.value").is(sst),Criteria.where("productCharacteristic.value").is(sd),Criteria.where("productCharacteristic.value").is(dnn)).and("type").is("SLICE").and("status").is("Active");
		Query query = new Query(criteria);
		return mongoTemplate.find(query,Product.class);
	}
	
	
	@SuppressWarnings({ "null", "unchecked" })
	public List<Product> getSstSdDnn(String sst,String sd,String dnn,String customerId,String imsi)throws Exception {
		List<Product> prdList = new ArrayList<Product>();
		List<Product> prdSecondList = new ArrayList<Product>();
		
		prdList = queryToGetMatchingOrder(sst,sd,dnn);
		logger.info("Query response."+new ObjectMapper().writeValueAsString(prdList));
		if(null != prdList && !prdList.isEmpty()){
			for (Product product : prdList) {
				for (ProductCharacteristic prdCharacteristic : product.getProductCharacteristic()) {
					if (prdCharacteristic.getName().equalsIgnoreCase("profileId")
							&& !prdCharacteristic.getValue().toString().equalsIgnoreCase("0")) {
						logger.info("ProfileID is : " + prdCharacteristic.getValue());
					}
				}
			}
			for (Product product : prdList) {
				for (ProductCharacteristic prdCharacteristic : product.getProductCharacteristic()) {
					if (prdCharacteristic.getName().equalsIgnoreCase("Slice Openness")
							&& prdCharacteristic.getValue().toString().equalsIgnoreCase("Private")) {
						logger.info("Slice Openness is private");
						prdSecondList = checkToExistOldOrderWithCustomer(sst, sd, dnn, customerId, imsi, prdSecondList, product);
					} else {
						logger.info("Slice Openness is public");
						prdSecondList = checkToExistOldOrderWithCustomer(sst, sd, dnn, customerId, imsi, prdSecondList, product);
					}
				}
			}
		}
		else {
			throw new ProductInventoryValidatorException("Data is not present for type"+"'SLICE'");
		}
		return prdList;
	}

	private List<Product> checkToExistOldOrderWithCustomer(String sst, String sd, String dnn, String customerId,
			String imsi, List<Product> prdList, Product product) {
		for (RelatedParty relatedParty : product.getRelatedPartyRef()) {
			if (relatedParty.getId().equals(customerId)) {
				logger.info("Customer ID matched.");
				prdList = queryToCheckExistingOrderWithCustomer(sst, sd, dnn, customerId, imsi);

				if (null != prdList && !prdList.isEmpty()) {
					logger.info("Data is present");
					throw new ProductInventoryValidatorException(
							"Data already present for" + " " + "'Product_Slice_Access'" + "type");

				} else {
					logger.info("Data is not present for type" + " " + "'Product_Slice_Access'");

				}

			} else {
				throw new ProductInventoryValidatorException("Customer not matching.");
			}
		}
		return prdList;
	}

	private List<Product> queryToCheckExistingOrderWithCustomer(String sst, String sd, String dnn, String customerId,
			String imsi) {
		List<Product> prdList;
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("productCharacteristic.value").is(sst),
				Criteria.where("productCharacteristic.value").is(sd),
				Criteria.where("productCharacteristic.value").is(dnn),
				Criteria.where("productCharacteristic.value").is(imsi)).and("type")
				.is("Product_Slice_Access").and("status").is("Active").and("relatedParty._id")
				.is(customerId);
		Query query = new Query(criteria);
		prdList = mongoTemplate.find(query, Product.class);
		return prdList;
	}

	

	private String EpochToZulu(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sdf.format(new java.util.Date(Long.parseLong(date)));
	}

}
