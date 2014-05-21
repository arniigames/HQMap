package com.qmap.core.client.UI;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class PropertySuggestion extends Composite {

	private static PropertySuggestionUiBinder uiBinder = GWT
			.create(PropertySuggestionUiBinder.class);
	
	@UiField ToggleButton toggleButton;

	private String subject;
	private String property;
	private String object;
	private String iSub;
	private String iObj;
	
	private boolean on = false;
	
	
	interface PropertySuggestionUiBinder extends
			UiBinder<Widget, PropertySuggestion> {
	}

	public PropertySuggestion(String s, String p, String o, String xSub, String xObj) {
		initWidget(uiBinder.createAndBindUi(this));

		//Set OntClass
		subject = s;
		property = p;
		object = o; 
		
		//SetIndividuals
		iSub = xSub;
		iObj = xObj;
		
		
		toggleButton.setHTML("<p>"+iSub+" ("+subject+")<br/><span style='font-weight:bold;'>"+
				property+"</span><br/>" +
				iObj+" ("+object+")</p>");

	}



//Getters and setters
	public String getSubject() {
		return subject;
	}



	public void setSubject(String subject) {
		this.subject = subject;
	}



	public String getProperty() {
		return property;
	}



	public void setProperty(String property) {
		this.property = property;
	}



	public String getObject() {
		return object;
	}



	public void setObject(String object) {
		this.object = object;
	}



	public String getSubjectIndividual() {
		return iSub;
	}



	public void setSubjectIndividual(String iSub) {
		this.iSub = iSub;
	}



	public String getObjectIndividual() {
		return iObj;
	}



	public void setObjectIndividual(String iObj) {
		this.iObj = iObj;
	}


	public boolean isOn() {
		return on;
	}



	public void setOn(boolean on) {
		this.on = on;
	}



	@UiHandler("toggleButton")
	void onToggleButtonClick(ClickEvent event) {
		if(!on){
			on = true;
		}else{
			on = false;
		}
	}
}
