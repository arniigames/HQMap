package com.qmap.core.client.UI;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.qmap.core.client.Main;
import com.qmap.core.client.ServerSide;
import com.qmap.core.client.ServerSideAsync;
import com.qmap.core.client.obj.ClassTree;
import com.google.gwt.event.dom.client.KeyDownEvent;

public class NewClassDialog extends Composite {

	private static NewClassDialogUiBinder uiBinder = GWT
			.create(NewClassDialogUiBinder.class);
	@UiField
	Label DBLabel;
	@UiField
	TextBox classNameInput;
	@UiField
	Button cancelButton;
	@UiField
	Button confirmButton;

	private String referer;

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer, boolean prnt) {
		this.referer = referer;
	}

	interface NewClassDialogUiBinder extends UiBinder<Widget, NewClassDialog> {
	}

	public NewClassDialog(String r) {
		initWidget(uiBinder.createAndBindUi(this));
		this.referer = r;
	}

	@UiHandler("classNameInput")
	void onClassNameInputBlur(BlurEvent event) {
		// ADDITIONAL VALIDATION NEEDED
		classNameInput.setText(classNameInput.getText().replace(' ', '_'));

	}

	@UiHandler("cancelButton")
	void onCancelButtonClick(ClickEvent event) {
		PopupPanel p = (PopupPanel) this.getParent();
		p.hide();
	}

	@UiHandler("confirmButton")
	void onConfirmButtonClick(ClickEvent event) {
		
		processClass();

//		if (!classNameInput.getValue().isEmpty()) {
//
//			final ServerSideAsync servletObject = GWT.create(ServerSide.class);
//
//			servletObject.augmentClassStructure(Main.bui.getLoginID(),
//					classNameInput.getText(), referer,
//					new AsyncCallback<String>() {
//
//						@Override
//						public void onFailure(Throwable caught) {
//							// TODO Auto-generated method stub
//							System.out.println(caught.getMessage());
//							Window.alert("S2 " + caught.getLocalizedMessage());
//						}
//
//						@Override
//						public void onSuccess(String result) {
//							// Reports successful reult, replaces tree panel
//							// with new structure.
//							Window.alert("New Class Created: \n" + result);
//							Main.dd.getPopup().hide();
//
//							// Clears Widget
//							Main.dd.getPopup().clear();
//							Main.dd.treePanel.clear();
//							Main.dd.setcTree(new ClassTree());
//
//							Main.dd.treePanel.add(Main.dd.buildClassTree());
//
//						}
//					});
//		}else{
//			Window.alert("You must enter a value to create a valid class.");
//		}

	}
	@UiHandler("classNameInput")
	void onClassNameInputKeyDown(KeyDownEvent event) {
		if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
			processClass();
		}
	}
	
	private void processClass(){
		if (!classNameInput.getValue().isEmpty()) {

			final ServerSideAsync servletObject = GWT.create(ServerSide.class);

			servletObject.augmentClassStructure(Main.bui.getLoginID(),
					classNameInput.getText(), referer,
					new AsyncCallback<String>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							System.out.println(caught.getMessage());
							Window.alert("S2 " + caught.getLocalizedMessage());
						}

						@Override
						public void onSuccess(String result) {
							// Reports successful reult, replaces tree panel
							// with new structure.
							Window.alert("New Class Created: \n" + result);
							Main.dd.getPopup().hide();

							// Clears Widget
							Main.dd.getPopup().clear();
							Main.dd.treePanel.clear();
							Main.dd.setcTree(new ClassTree());

							Main.dd.treePanel.add(Main.dd.buildClassTree());

						}
					});
		}else{
			Window.alert("You must enter a value to create a valid class.");
		}
	}
}
