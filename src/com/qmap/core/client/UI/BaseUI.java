package com.qmap.core.client.UI;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.qmap.core.client.Main;
import com.qmap.core.client.ServerSide;
import com.qmap.core.client.ServerSideAsync;
import com.qmap.core.client.eventHandlers.RemoteClickHandler;
import com.qmap.core.client.process.XMLtoMapObjects;

public class BaseUI extends Composite {

	private static BaseUIUiBinder uiBinder = GWT.create(BaseUIUiBinder.class);
	@UiField
	SimplePanel buttonPanrl;
	@UiField
	CaptionPanel mapHolder;

	@UiField
	Button loginBtn;

	@UiField
	Button inputModeBtn;
	@UiField
	SimplePanel attachPanel;
	@UiField
	Label loginLabel;
	@UiField
	Button propertiesButton;

	// Login Id, functions will not work without it.
	String loginID = "";

	String nm;
	String desc;

	private boolean inputMode = false;

	public static InfoDisplay id = new InfoDisplay();
	public static PopupPanel p = new PopupPanel();

	// Show loading icon
	public static PopupPanel l;
	public static AjaxLoader aload;

	interface BaseUIUiBinder extends UiBinder<Widget, BaseUI> {
	}

	public BaseUI() {

		initWidget(uiBinder.createAndBindUi(this));

		aload = new AjaxLoader();
		l = new PopupPanel();

		// Set up display panel for user convenience.
		p.add(id);
		p.setStylePrimaryName("ToolTipInfoBox");
		p.setPopupPosition(700, 26);
		p.setTitle("Location Description");

		l.add(aload);
		l.setPopupPosition(550, 300);
		l.setGlassEnabled(true);
		l.hide();

		propertiesButton.setEnabled(false);

		inputModeBtn.addClickHandler(new RemoteClickHandler());

	}

	//PROPERTIES
	@UiHandler("propertiesButton")
	void onPropertiesButtonClick(ClickEvent event) {
		if (loginID == "") {
			Window.alert("You need to be logged in to assign properties.");
		} else if(Window.confirm("Enter Properties mode?")){
			l.show();
			if (!inputMode) {
				Main.map.clearOverlays();
				l.hide();
				this.attachPanel.clear();
				inputMode = true;
			} else {

				final ServerSideAsync servletObject = GWT
						.create(ServerSide.class);

				servletObject.loadExistingMapObjects(this.loginID,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {

								// Show the RPC error message to the user
								l.hide();
								System.out.println("rpc Message: "
										+ caught.getMessage());
							}

							@Override
							public void onSuccess(String result) {

								// Process objects from ontology using JENA/XML
								l.hide();
								XMLtoMapObjects r = new XMLtoMapObjects(result);
								r.process();
							}

						});
			}
		}
	}
	
	//LOGIN
	@UiHandler("loginBtn")
	void onLoginBtnClick(ClickEvent event) {

		PopupPanel loginPopup = new PopupPanel();

		loginPopup.setGlassEnabled(true);

		LoginForm lf = new LoginForm();

		loginPopup.add(lf);
		loginPopup.center();
		loginPopup.show();
		lf.getLoginBox().setFocus(true);

	}
	
	// Access methods
	public SimplePanel getAttachPanel() {
		return attachPanel;

	}

	public CaptionPanel getMapHolder() {
		return mapHolder;
	}

	public void disableButtons() {
		this.propertiesButton.setEnabled(false);
		this.inputModeBtn.setEnabled(false);
	}

	public void enableButtons() {
		this.propertiesButton.setEnabled(true);
		this.inputModeBtn.setEnabled(true);
	}
	
	public String getLoginID() {
		return loginID;
	}

	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}

	public Button getInputModeBtn() {
		return inputModeBtn;
	}

	public void setInputModeBtn(Button inputModeBtn) {
		this.inputModeBtn = inputModeBtn;
	}

	public Button getPropertiesButton() {
		return propertiesButton;
	}

	public void setPropertiesButton(Button propertiesButton) {
		this.propertiesButton = propertiesButton;
	}
	
	public void setInputModeStyle(boolean t){
		if(t){
			mapHolder.addStyleName("fieldset-inputmode");
		}else{
			mapHolder.removeStyleName("fieldset-inputmode");
		}
		
	}
}
