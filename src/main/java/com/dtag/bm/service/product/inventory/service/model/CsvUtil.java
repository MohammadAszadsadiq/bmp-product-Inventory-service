package com.dtag.bm.service.product.inventory.service.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class CsvUtil {
	
	private static final CsvMapper mapper = new CsvMapper();
	@Autowired
	MongoTemplate mongoTemplate;
	
	public static List<ProductCsv> readcsv1(Class<ProductCsv> clazz, InputStream stream) throws IOException {
		CsvSchema schema = mapper.schemaFor(clazz).withHeader().withColumnReordering(true);
 		ObjectReader reader = mapper.readerFor(clazz).with(schema);
		return reader.<ProductCsv> readValues(stream).readAll();
	}

	
	
	
	public static List<ProductCsv> convertCsv(List<ProductCsv> productone) {
		if (!productone.isEmpty()) {
			productone.forEach(csvone1 -> {
//				ProductCsv prodCsv = new ProductCsv();
//				prodCsv.setMaximumLatency(csvone1.getMaximumLatency());
//				prodCsv.setMinimumBitrateDownlink(csvone1.getMinimumBitrateDownlink());
//				prodCsv.setMinimumBitrateUplink(csvone1.getMinimumBitrateUplink());
//				//prodCsv.setModifiedDate(csvone1.getModifiedDate());
//				prodCsv.setOMOdescription(csvone1.getOMOdescription());
//				prodCsv.setQCI(csvone1.getQCI());
//				//prodCsv.setStartDate(csvone1.getStartDate());
//				prodCsv.setStatus(csvone1.getStatus());
//				prodCsv.setQI(csvone1.getQI());
//				
			});
		}

		return productone;

	}
}
