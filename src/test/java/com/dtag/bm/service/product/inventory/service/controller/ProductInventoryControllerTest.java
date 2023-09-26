package com.dtag.bm.service.product.inventory.service.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dtag.bm.service.product.inventory.service.Application;
import com.dtag.bm.service.product.inventory.service.service.ProductServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =Application.class)
@WebAppConfiguration

public class ProductInventoryControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
//	@Autowired
//	ProductServiceImpl productservice;

	@InjectMocks
	private ProductServiceImpl productservice;
	
	
	/*
	 * @Before public void setup() { MockitoAnnotations.initMocks(this);
	 * 
	 * mock(ProductServiceImpl.class); }
	 */

	
	@Test
	public void GetProducts() throws Exception {
	   String uri = "/product/byRole";
//	   Product product = new Product();
//	   product.setId("3");
//	   product.setName("Ginger");
	   
	 //  String inputJson = super.mapToJson(product);
	   MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	      .contentType(MediaType.APPLICATION_JSON_VALUE).content("")).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(201, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   assertEquals(content, "Product is created successfully");
	}

}
