package com.dtag.bm.service.product.inventory.validations;

import java.util.List;

import org.springframework.web.bind.annotation.ControllerAdvice;

import com.dtag.bm.service.product.inventory.service.exceptions.ProductInventoryValidatorException;
import com.dtag.bm.service.product.inventory.service.model.ProductCsv;
@ControllerAdvice
public class CsvValidations {

	public List<ProductCsv> OmoCsvValidations(List<ProductCsv> productone){
		if (!productone.isEmpty()) {
			productone.forEach(csvone1 -> {
				if (csvone1.getoMOidentifier() == null) {
					throw new ProductInventoryValidatorException("Fields Can't be null");
				}
				if (csvone1.getPriority().contentEquals("")) {
					throw new ProductInventoryValidatorException("Property Can't be null");
				}
				if (csvone1.getOMOdescription() == null || csvone1.getQCI() == null) {
					throw new ProductInventoryValidatorException("OMOdescription and QCI both can't be null");
				}
				if (csvone1.getQI() == null) {
					throw new ProductInventoryValidatorException("5QI  can't be null");
				}
				if (csvone1.getMaximumLatency() == null) {
					throw new ProductInventoryValidatorException("Maximum Letancy  can't be null");
				}
				if (csvone1.getMinimumBitrateDownlink() == null ||csvone1.getMinimumBitrateUplink() == null) {
					throw new ProductInventoryValidatorException("MinimumBitrateDownlink And MinimumBitrateUplink can't be null");
				}
	
			});
		}
		return productone;
	}
}