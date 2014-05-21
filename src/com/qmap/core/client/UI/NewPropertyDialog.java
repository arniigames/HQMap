package com.qmap.core.client.UI;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.qmap.core.client.ServerSide;
import com.qmap.core.client.ServerSideAsync;

public class NewPropertyDialog extends Composite {
	
	final ServerSideAsync servletObject = GWT.create(ServerSide.class);

	private static NewPropertyDialogUiBinder uiBinder = GWT
			.create(NewPropertyDialogUiBinder.class);
	@UiField Label subjectL;
	@UiField TextBox pred;
	@UiField Label objectL;
	@UiField Button cancelButton;
	@UiField Button okButton;


	interface NewPropertyDialogUiBinder extends
			UiBinder<Widget, NewPropertyDialog> {
	}

	public NewPropertyDialog(String sbjLabel, String objLabel) {
		initWidget(uiBinder.createAndBindUi(this));
		objectL.setText(objLabel);
		subjectL.setText(sbjLabel);
		
		
	}


	@UiHandler("okButton")
	void onOkButtonClick(ClickEvent event) {
		if(!pred.getValue().isEmpty()){
			if(Window.confirm(subjectL.getText()+" -> \n"+pred.getValue()+" -> \n" + objectL.getText())){
			
				servletObject.createNewProperty(subjectL.getText(), pred.getValue(), objectL.getText() , new AsyncCallback<Boolean>(){

					@Override
					public void onFailure(Throwable caught) {
						
						System.out.println(caught.getLocalizedMessage());
						
					}

					@Override
					public void onSuccess(Boolean result) {
						cancelButton.click();	
					}
					
					
				});
				
			}
		}else{
			Window.alert("A value must be entered.");
		}
		
	}
	@UiHandler("cancelButton")
	void onCancelButtonClick(ClickEvent event) {
		PopupPanel p = (PopupPanel) this.getParent();
		p.hide();
	}
}
