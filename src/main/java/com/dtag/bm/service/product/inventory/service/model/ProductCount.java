package com.dtag.bm.service.product.inventory.service.model;

import java.util.List;

public class ProductCount {

	private List<Product> relatedProductInventory;
	private int sizeCount;
	public List<Product> getRelatedProductInventory() {
		return relatedProductInventory;
	}
	public void setRelatedProductInventory(List<Product> relatedProductInventory) {
		this.relatedProductInventory = relatedProductInventory;
	}
	public int getSizeCount() {
		return sizeCount;
	}
	public void setSizeCount(int sizeCount) {
		this.sizeCount = sizeCount;
	}
	@Override
	public String toString() {
		return "ProductCount [relatedProductInventory=" + relatedProductInventory + ", sizeCount=" + sizeCount + "]";
	}
	
	
	

}