package com.qmap.core.client.UI;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.qmap.core.client.Main;

public class LoginForm extends Composite {

	private static LoginFormUiBinder uiBinder = GWT
			.create(LoginFormUiBinder.class);
	@UiField
	Button cancleBtn;
	@UiField
	Button okBtn;
	@UiField
	TextBox loginBox;
	
	
	
	String[] ids = new String[16];

	interface LoginFormUiBinder extends UiBinder<Widget, LoginForm> {
	}

	public LoginForm() {
		initWidget(uiBinder.createAndBindUi(this));
		
		
		
		//Valid IDs
		ids[0] = "57260";
		ids[1] = "55367";
		ids[2] = "34870";
		ids[3] = "75810";
		ids[4] = "42025";
		ids[5] = "38890";
		ids[6] = "96190";
		ids[7] = "73257";
		ids[8] = "25467";
		ids[9] = "36488";
		//SPARE
		ids[10] = "26132";
		ids[11] = "98781";
		ids[12] = "16172";
		ids[13] = "43971";
		ids[14] = "66572";
		//Admin
		ids[15] = "admin1612";
		
		
		
	}

	@UiHandler("cancleBtn")
	void onCancleBtnClick(ClickEvent event) {
		PopupPanel p = (PopupPanel) this.getParent();
		p.hide();
	}
	@UiHandler("okBtn")
	void onOkBtnClick(ClickEvent event) {
		login();
	}
	
	@UiHandler("loginBox")
	void onLoginBoxKeyDown(KeyDownEvent event) {
		if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
			System.out.println("kk");
			login();
		}
	}

	
	private void login(){
		boolean check = false;
		for(int x = 0; x < ids.length; x++){
			if(ids[x].equals(loginBox.getValue())){
				check = true;	
			}
		}
		
		if(check){
			Main.bui.setLoginID(loginBox.getValue());
			Main.bui.loginLabel.setText("Logged in as " + loginBox.getValue());
			Main.bui.propertiesButton.setEnabled(true);
			Main.bui.inputModeBtn.setEnabled(true);
			
			
			cancleBtn.click();
		}else{
			Window.alert("Invalid Login");
		}
	}

	public TextBox getLoginBox() {
		return loginBox;
	}

	public void setLoginBox(TextBox loginBox) {
		this.loginBox = loginBox;
	}

}
