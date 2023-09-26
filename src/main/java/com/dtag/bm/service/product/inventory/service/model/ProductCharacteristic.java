package com.dtag.bm.service.product.inventory.service.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "_id", "name", "value", "@type", "@schemaLocation", "isVisible", "unitOfMeasurement",
		"displayAttributeName" })

public class ProductCharacteristic implements Serializable {

	/**
	* 
	 */
	private static final long serialVersionUID = -7841255911690736956L;

	@JsonProperty("name")
	private String name;

	@JsonProperty("value")
	private Object value="";

	@JsonProperty("@type")                                //  changing from type to @type
	private String type;

	@JsonProperty("@schemaLocation")
	private String schemaLocation;

	@JsonProperty("isVisible")
	private boolean isVisible;

	@JsonProperty("unitOfMeasurement")
	private String unitOfMeasurement;

	@JsonProperty("displayAttributeName")
	private String displayAttributeName;

	@JsonProperty("_id")
	private String _id;

	public boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSchemaLocation() {
		return schemaLocation;
	}

	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}

	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public String getDisplayAttributeName() {
		return displayAttributeName;
	}

	public void setDisplayAttributeName(String displayAttributeName) {
		this.displayAttributeName = displayAttributeName;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	@Override
	public String toString() {
		return "ProductCharacteristic [name=" + name + ", value=" + value + ", type=" + type + ", schemaLocation="
				+ schemaLocation + ", isVisible=" + isVisible + ", unitOfMeasurement=" + unitOfMeasurement
				+ ", displayAttributeName=" + displayAttributeName + ", _id=" + _id + "]";
	}

}
