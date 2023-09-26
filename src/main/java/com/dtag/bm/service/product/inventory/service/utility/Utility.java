package com.dtag.bm.service.product.inventory.service.utility;

import org.springframework.stereotype.Component;

@Component
public class Utility {

	public static final String SIM_QUERY = "db.ProductInventoryDetails.aggregate([\r\n"
			+ "                    {$match: {\"type\": {$in: [\"SIM_Material\", \"SIM Connectivity\"]}, \"status\": \"Active\"}},\r\n"
			+ "                    {$sort: {\"startDate\": -1}}, \r\n" + "                    {$skip: {skip}}, \r\n"
			+ "                    {$limit: {limit}},\r\n" + "                    {$addFields: {\r\n"
			+ "                        \"imsiObj\": {$arrayElemAt: [{$filter: {input: \"$productCharacteristic\", \"as\": \"char\", cond: { $in: [{$toLower: \"$$char.name\"}, [\"imsi\", \"i_imsi\"]]}}}, 0]},\r\n"
			+ "                        \"childType\": {$cond: {if: {$in: [\"$type\", [\"SLICE\"]]}, then: [\"SLICE\"], else: {$cond: {if: {$in: [\"$type\", [\"SIM_Material\"]]}, then: [\"Direct_Slice_Access\"], else: {$cond: {if: {$in: [\"$type\", [\"SIM Connectivity\"]]}, then: [\"SIM Connectivity\"], else: [\"\"]}} }}}}\r\n"
			+ "                        }},\r\n" + "                    {$lookup: {\r\n"
			+ "                        \"from\": \"ProductInventoryDetails\",\r\n"
			+ "                        \"let\": { \"type\": \"$childType\", \"status\": \"Active\", \"relation\": \"$imsiObj.value\", \"customer\": \"$relatedParty\" },\r\n"
			+ "                        \"pipeline\": [\r\n"
			+ "                            {$addFields: {\"imsiObj\": {$arrayElemAt: [{$filter: {input: \"$productCharacteristic\", \"as\": \"char\", cond: { $in: [{$toLower: \"$$char.name\"}, [\"imsi\", \"i_imsi\"]]}}}, 0]}}},\r\n"
			+ "                            {$match: {$expr: {$and: [ {$in: [\"$type\", \"$$type\"]}, {$eq: [\"$status\", \"$$status\"]}, {$eq: [\"$imsiObj.value\", \"$$relation\"]}, {$eq: [\"$relatedParty\", \"$$customer\"]}]}}},\r\n"
			+ "                            {$sort: {\"startDate\": -1}},\r\n" + "                        ],\r\n"
			+ "                        \"as\": \"relatedProductInventory\"\r\n" + "                        }}\r\n"
			+ "                    ])";

	/*public static final String SLICE_QUERY = "db.ProductInventoryDetails.aggregate([\r\n"
			+ "{$match: {\"type\": {$in: {token}}, \"status\": \"Active\", \"productRelationship\": null  {customerId}}},\r\n"
			+ "{$sort: {\"productOrder.startDate\": -1}},\r\n"
			+ "{$addFields: {\r\n"
			+ "\"childType\": {$cond: {if: {$in: [\"$type\", [\"SLICE\"]]}, then: [\"SLICE\"], else: [\"SLICE\"]}},\r\n"
			+ "\"relation\": [{ \"type\" : \"reliesOn\", \"productRef\" : { \"_id\" : \"$productOffering._id\", \"href\" : \"\" }}]}},\r\n"
			+ "{$lookup: {\r\n" + "\"from\": \"ProductInventoryDetails\",\r\n"
			+ " \"let\": { \"type\": \"$childType\", \"status\": \"Active\", \"relation\": \"$relation\", \"customer\": \"$relatedParty\" },\r\n"
			+ "\"pipeline\": [\r\n"
			+ "  {$match: {$expr: {$and: [ {$in: [\"$type\", \"$$type\"]}, {$eq: [\"$status\", \"$$status\"]}, {$eq: [\"$productRelationship\", \"$$relation\"]}, {$eq: [\"$relatedParty\", \"$$customer\"]}]}}},\r\n"
			+ "    {$sort: {\"productOrder.startDate\": -1}},\r\n" + "],\r\n" + " \"as\": \"relatedProductInventory\"\r\n" + "  }}\r\n"
			+ "])";
*/
	
	
	public static final String SLICE_QUERY = "db.ProductInventoryDetails.aggregate([" +
			"			{$match: {\"type\": {$in: {token}}, \"status\": \"Active\", \"productRelationship\": null  {customerId}}}, \r\n" +
			"           {$addFields: {\"_startDate\": { $convert: { input: \"$startDate\", to: \"int\" }}}},"+
			"			{$sort: {\"_startDate\": -1}}," +
			"			{$addFields: {" +
			"			\"childType\": {$cond: {if: {$in: [\"$type\", [\"SLICE\"]]}, then: [\"SLICE\"], else: [\"SLICE\"]}}," +
			"			\"relation\": [{ \"type\" : \"reliesOn\", \"productRef\" : { \"_id\" : \"$productOffering._id\" }}," +
			"			{ \"type\" : \"orderRelation\", \"productRef\" : { \"_id\" : {$arrayElemAt: [\"$productOrder._id\", 0]} }}]}}," +
			"		{$lookup: {\"from\": \"ProductInventoryDetails\"," +
			"			 \"let\": { \"type\": \"$childType\", \"status\": \"Active\", \"relation\": \"$relation\", \"customer\": \"$relatedParty\" }," +
			"			\"pipeline\": [" +
			"			  {$match: {$expr: {$and: [ {$in: [\"$type\", \"$$type\"]}, {$eq: [\"$status\", \"$$status\"]}, {$eq: [\"$productRelationship\", \"$$relation\"]}, {$eq: [\"$relatedParty\", \"$$customer\"]}]}}}," +
			"		{$sort: {\"productOrder.startDate\": -1}},], \"as\": \"relatedProductInventory\" }}" +
			"			])";
	public static final String SIM_QUERY_IMSI = "db.ProductInventoryDetails.aggregate([\r\n"
			+ "                    {$match: {\"type\": {$in: [\"SIM_Material\", \"SIM Connectivity\"]}, \"status\": \"Active\"}},\r\n"
			+ "                    {$skip: 0}, \r\n"
			+ "                    {$limit: 50},\r\n" + "                    {$addFields: {\r\n"
			+ "                        \"imsiObj\": {$arrayElemAt: [{$filter: {input: \"$productCharacteristic\", \"as\": \"char\", cond: { $in: [{$toLower: \"$$char.name\"}, [\"imsi\", \"i_imsi\"]]}}}, 0]},\r\n"
			+ "                        \"childType\": {$cond: {if: {$in: [\"$type\", [\"SLICE\"]]}, then: [\"SLICE\"], else: {$cond: {if: {$in: [\"$type\", [\"SIM_Material\"]]}, then: [\"Direct_Slice_Access\"], else: {$cond: {if: {$in: [\"$type\", [\"SIM Connectivity\"]]}, then: [\"SIM Connectivity\"], else: [\"\"]}} }}}}\r\n"
			+ "                        }},\r\n" + "                    {$lookup: {\r\n"
			+ "                        \"from\": \"ProductInventoryDetails\",\r\n"
			+ "                        \"let\": { \"type\": \"$childType\", \"status\": \"Active\", \"relation\": \"$imsiObj.value\", \"customer\": \"$relatedParty\" },\r\n"
			+ "                        \"pipeline\": [\r\n"
			+ "                            {$addFields: {\"imsiObj\": {$arrayElemAt: [{$filter: {input: \"$productCharacteristic\", \"as\": \"char\", cond: { $in: [{$toLower: \"$$char.name\"}, [\"imsi\", \"i_imsi\"]]}}}, 0]}}},\r\n"
			+ "                            {$match: {$expr: {$and: [ {$in: [\"$type\", \"$$type\"]}, {$eq: [\"$status\", \"$$status\"]}, {$eq: [\"$imsiObj.value\", \"$$relation\"]}, {$eq: [\"$relatedParty\", \"$$customer\"]}]}}},\r\n"
			+ "                            {\"$group\" : {_id:\"$source\", count:{$sum:1}}}, \r\n" + "                        ],\r\n"
			+ "                        \"as\": \"relatedProductInventory\"\r\n" + "                        }}\r\n"
			+ "                    ])";
	


public static final String ALL_QUERY_DATA = "db.ProductInventoryDetails.aggregate([\r\n"
+"                 {$match: {\"type\": {$in: [\"SIM_Material\", \"SIM Connectivity\",\"Product_Slice_Access\"]}, \"status\": \"Active\"}},\r\n"
+"   {$sort: {\"startDate\": -1}}, \r\n" + " {$skip: 0}, \r\n"
+ "                    {$limit: 200},\r\n" + "                    {$addFields: {\r\n"
+"    \"imsiObj\": {$arrayElemAt: [{$filter: {input: \"$productCharacteristic\", \"as\": \"char\", cond: { $in: [{$toLower: \"$$char.name\"}, [\"imsi\", \"i_imsi\"]]}}}, 0]},\r\n" 
+"       \"childType\": {$cond: {if: {$in: [\"$type\", [\"SIM_Material\"]]}, then: [\"Direct_Slice_Access\"], else: {$cond: {if: {$in: [\"$type\", [\"SIM Connectivity\"]]}, then: [\"SIM Connectivity\"], else: [\"\"]}}}},\r\n"
+" \"relation\": {$cond: {if: {$in: [\"$type\", [\"SIM_Material\"]]}, then: \"$imsiObj.value\", else: [{ \"type\" : \"reliesOn\", \"productRef\" : { \"_id\" : \"$productOffering._id\", \"href\" : \"\" } }]}},\r\n"
+"  }},\r\n"  + " {$lookup: {\r\n" 
+"     \"from\": \"ProductInventoryDetails\",\r\n"
+"   \"let\": { \"type\": \"$childType\", \"status\": \"Active\", \"relation\": \"$relation\", \"customer\": \"$relatedParty\" },\r\n"
+"  \"pipeline\": [\r\n"
+"    {$addFields: {\"imsiObj\": {$arrayElemAt: [{$filter: {input: \"$productCharacteristic\", \"as\": \"char\", cond: { $in: [{$toLower: \"$$char.name\"}, [\"imsi\", \"i_imsi\"]]}}}, 0]}}},\r\n"
+" {$match: {$expr: {$and: [ {$in: [\"$type\", \"$$type\"]}, {$eq: [\"$status\", \"$$status\"]}, {$or: [{$eq: [\"$productRelationship\", \"$$relation\"]}, {$eq: [\"$imsiObj.value\", \"$$relation\"]}]}, {$eq: [\"$relatedParty\", \"$$customer\"]}]}}},\r\n"
+"      {$sort: {\"startDate\": -1}},\r\n" + "                        ],\r\n"
+ "    \"as\": \"relatedProductInventory\"\r\n" + "                        }}\r\n"
+"])";

public static final String Child_PARENT_QUERY_DATA = "db.ProductInventoryDetails.aggregate([" +
		"{$match: {\"type\": {$in: [\"SIM_Material\", \"SIM Connectivity\",\"Product_Slice_Access\", \"Direct_Slice_Access\"] }, \"status\": \"Active\", \"productCharacteristic.value\": \"IMSI83\"}}," +
		"{$group: {_id: null, }}" +
		"])";


public static final String IMSI_GROUP_QUERY_DATA = "db.ProductInventoryDetails.aggregate([" +
              "{$match: {\"type\": {$in: {token}}, \"status\": \"Active\", \"productRelationship\": null {customerId}}}," +
              "{$sort: {\"startDate\": -1}}," +
			  "{$addFields: {" +
			  "    \"imsiObj\": {$arrayElemAt: [{$filter: {input: \"$productCharacteristic\", \"as\": \"char\", cond: { $in: [{$toLower: \"$$char.name\"}, [\"imsi\", \"i_imsi\"]]}}}, 0]} " +
			  "    }}," +
              "{$addFields: {" +
              "    \"childType\": {$cond: {if: {$in: [\"$type\", [\"SIM_Material\"]]}, then: [\"Direct_Slice_Access\"], else: {$cond: {if: {$in: [\"$type\", [\"SIM Connectivity\"]]}, then: [\"SIM Connectivity\"], else: [\"\"]}}}}," +
              "    \"relation\": {$cond: {if: {$in: [\"$type\", [\"SIM_Material\"]]}, then: \"$imsiObj.value\", else: [{ \"type\" : \"reliesOn\", \"productRef\" : { \"_id\" : \"$productOffering._id\", \"href\" : \"\" } }]}}" +
              "    }}," +
              "{$lookup: {" +
              "    \"from\": \"ProductInventoryDetails\"," +
              "    \"let\": { \"type\": \"$childType\", \"status\": \"Active\", \"relation\": \"$relation\", \"customer\": \"$relatedParty\" }," +
              "    \"pipeline\": [" +
              "    {$addFields: {\"imsiObj\": {$arrayElemAt: [{$filter: {input: \"$productCharacteristic\", \"as\": \"char\", cond: { $in: [{$toLower: \"$$char.name\"}, [\"imsi\", \"i_imsi\"]]}}}, 0]}}}," +
              "    {$match: {$expr: {$and: [ {$in: [\"$type\", \"$$type\"]}, {$eq: [\"$status\", \"$$status\"]}, {$eq: [\"$imsiObj.value\", \"$$relation\"]}, {$eq: [\"$relatedParty\", \"$$customer\"]}]}}}," +
              "    {$sort: {\"startDate\": -1}}]," +
              "    \"as\": \"relatedProductInventory\" }}," +
              "{$addFields: {" +
              "    \"data\": {" +
              "        \"_id\": \"$_id\"," +
              "        \"type\": \"$type\"," +
              "        \"name\": \"$name\"," +
              "        \"startDate\": \"$startDate\"," +
              "        \"status\": \"$status\"," +
              "        \"productSpecification\": \"$productSpecification\"," +
              "        \"productOffering\": \"$productOffering\"," +
              "        \"productOrder\": \"$productOrder\"," +
			  "        \"productPrice\": \"$productPrice\"," +
              "        \"relatedParty\": \"$relatedParty\"," +
              "        \"productCharacteristic\": \"$productCharacteristic\"," +
              "        \"relatedProductInventory\": \"$relatedProductInventory\"," +
              "        \"imsi\": \"$imsiObj.value\"" +
              "        }" +
              "    }}," +
              "{$group: {_id: \"$imsiObj.value\", \"maxDate\": {$max: \"$startDate\"}, \"data\": {$push: \"$data\"}}}," +
              "{$unwind: \"$data\"}," +
              "{$sort: {\"maxDate\": -1, \"data.startDate\": -1}}," +
              "{$project: {" +
              "        \"_id\": \"$data._id\"," +
              "        \"type\": \"$data.type\"," +
              "        \"name\": \"$data.name\"," +
              "        \"startDate\": \"$data.startDate\"," +
              "        \"status\": \"$data.status\"," +
              "        \"productSpecification\": \"$data.productSpecification\"," +
              "        \"productOffering\": \"$data.productOffering\"," +
              "        \"productOrder\": \"$data.productOrder\"," +
			    "        \"productPrice\": \"$data.productPrice\"," +
              "        \"relatedParty\": \"$data.relatedParty\"," +
              "        \"productCharacteristic\": \"$data.productCharacteristic\"," +
              "        \"imsi\": \"$data.imsi\"" +
              "    }}" +
              "]);";



public static final String Child_PARENT_QUERY_DATA1 =  "db.ProductInventoryDetails.aggregate([" +
"{$match: {\"type\": {$in: [\"SIM_Material\", \"SIM Connectivity\",\"Product_Slice_Access\"]}, \"status\": \"Active\", \"productRelationship\": null, \"_id\": {$in: [ " +
"        \"81d0fdb6-fc03-404b-9e6a-ea1c18f91f17\", " +
"        \"113013a8-631c-40d7-aec4-41242f1bab38\", " +
"        \"ff23d667-7b1c-4754-aa6a-6265afcbd511\", " +
"        \"67a82972-fca1-4e55-9eba-6256cf1ca1fa\", " +
"        \"65c2866c-5222-457c-a268-4bb30cff6915\", " +
"        \"16438063-e06f-4bd7-b240-e043f101e174\", " +
"        \"05f05f42-dc7a-4091-a372-ba0f716b5225\"" +
"    ]}}}," +
"{$sort: {\"startDate\": -1}}," +
"{$skip: 0}," +
"{$limit: 50},                    " +
"{$addFields: {" +
"    \"imsiObj\": {$arrayElemAt: [{$filter: {input: \"$productCharacteristic\", \"as\": \"char\", cond: { $in: [{$toLower: \"$$char.name\"}, [\"imsi\", \"i_imsi\"]]}}}, 0]}, " +
"    \"childType\": {$cond: {if: {$in: [\"$type\", [\"SIM_Material\"]]}, then: [\"Direct_Slice_Access\"], else: {$cond: {if: {$in: [\"$type\", [\"SIM Connectivity\"]]}, then: [\"SIM Connectivity\"], else: [\"\"]}}}}," +
"    \"relation\": {$cond: {if: {$in: [\"$type\", [\"SIM_Material\"]]}, then: \"$imsiObj.value\", else: [{ \"type\" : \"reliesOn\", \"productRef\" : { \"_id\" : \"$productOffering._id\", \"href\" : \"\" } }]}}" +
"    }}," +
"{$lookup: {" +
"    \"from\": \"ProductInventoryDetails\"," +
"    \"let\": { \"type\": \"$childType\", \"status\": \"Active\", \"relation\": \"$relation\", \"customer\": \"$relatedParty\", \"_id\": [ " +
"        \"81d0fdb6-fc03-404b-9e6a-ea1c18f91f17\", " +
"        \"113013a8-631c-40d7-aec4-41242f1bab38\", " +
"        \"ff23d667-7b1c-4754-aa6a-6265afcbd511\", " +
"        \"67a82972-fca1-4e55-9eba-6256cf1ca1fa\", " +
"        \"65c2866c-5222-457c-a268-4bb30cff6915\", " +
"        \"16438063-e06f-4bd7-b240-e043f101e174\", " +
"        \"05f05f42-dc7a-4091-a372-ba0f716b5225\"" +
"    ] }," +
"    \"pipeline\": [" +
"    {$addFields: {\"imsiObj\": {$arrayElemAt: [{$filter: {input: \"$productCharacteristic\", \"as\": \"char\", cond: { $in: [{$toLower: \"$$char.name\"}, [\"imsi\", \"i_imsi\"]]}}}, 0]}}}," +
"    {$match: {$expr: {$and: [ {$in: [\"$type\", \"$$type\"]}, {$eq: [\"$status\", \"$$status\"]}, {$or: [{$eq: [\"$productRelationship\", \"$$relation\"]}, {$eq: [\"$imsiObj.value\", \"$$relation\"]}]}, {$eq: [\"$relatedParty\", \"$$customer\"]}, {$in: [\"$_id\", \"$$_id\"]}]}}}," +
"    {$sort: {\"startDate\": -1}}]," +
"    \"as\": \"relatedProductInventory\" }}," +
"{$addFields: {" +
"    \"data\": {" +
"        \"_id\": \"$_id\"," +
"        \"type\": \"$type\"," +
"        \"name\": \"$name\"," +
"        \"startDate\": \"$startDate\"," +
"        \"status\": \"$status\"," +
"        \"productSpecification\": \"$productSpecification\"," +
"        \"productOffering\": \"$productOffering\"," +
"        \"productOrder\": \"$productOrder\"," +
"        \"relatedParty\": \"$relatedParty\"," +
"        \"productCharacteristic\": \"$productCharacteristic\"," +
"        \"relatedProductInventory\": \"$relatedProductInventory\"," +
"        \"imsi\": \"$imsiObj.value\"" +
"        }" +
"    }}," +
"{$group: {_id: \"$imsiObj.value\", \"maxDate\": {$max: \"$startDate\"}, \"data\": {$push: \"$data\"}}}," +
"{$sort: {\"maxDate\": -1}}," +
"{$unwind: \"$data\"}," +
"{$project: {" +
"        \"_id\": \"$data._id\"," +
"        \"type\": \"$data.type\"," +
"        \"name\": \"$data.name\"," +
"        \"startDate\": \"$data.startDate\"," +
"        \"status\": \"$data.status\"," +
"        \"productSpecification\": \"$data.productSpecification\"," +
"        \"productOffering\": \"$data.productOffering\"," +
"        \"productOrder\": \"$data.productOrder\"," +
"        \"relatedParty\": \"$data.relatedParty\"," +
"        \"productCharacteristic\": \"$data.productCharacteristic\"," +
"        \"relatedProductInventory\": \"$data.relatedProductInventory\"," +
"        \"imsi\": \"$data.imsi\"" +
"    }}" +
"]);";


public static final String SIM_Count = "db.ProductInventoryDetails.count({\"type\": {$in: {token}}, \"status\": {status}, \"productRelationship\": null})";

public static final String QUERY_InActive = "db.ProductInventoryDetails.aggregate([{$match: {\"type\": {$in: {token}}, \"status\": \"InActive\" {customerId}}},{$sort: {\"startDate\": -1}}]);";

}


