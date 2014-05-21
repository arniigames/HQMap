package com.qmap.core.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ServerSideAsync {

	//Create object [individual] or class (depending on query).
	void reply(String id, int type, String name, String coords, String desc, int area,
			String cls, AsyncCallback<String> callback);
	
	//For visualisation of objects on googlemaps.
	void loadExistingMapObjects(String string, AsyncCallback<String> callback);
	
	//To manually process ontology looking for candidtate object properties and classes.
	void getCandidateRelationships(AsyncCallback<String[]> asyncCallback);
	
	
	//Get class structure of Location classes/subclasses only (for tree).
	void getClassStructure(AsyncCallback<String> asyncCallback);

	void getValidObjectProperties(String sub, String obj,
			AsyncCallback<String> asyncCallback);

	void augmentClassStructure(String text, String referer, String referer2, AsyncCallback<String> asyncCallback);

	void assignObjectProperties(String[] rs,
			AsyncCallback<Boolean> asyncCallback);

	void createNewProperty(String sub, String pred, String obj,
			AsyncCallback<Boolean> asyncCallback);
}
