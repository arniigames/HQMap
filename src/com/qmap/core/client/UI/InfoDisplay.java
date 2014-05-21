package com.qmap.core.client.UI;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class InfoDisplay extends Composite {

	private static InfoDisplayUiBinder uiBinder = GWT
			.create(InfoDisplayUiBinder.class);
	@UiField HTML innerText;

	interface InfoDisplayUiBinder extends UiBinder<Widget, InfoDisplay> {
	}

	public InfoDisplay() {
		initWidget(uiBinder.createAndBindUi(this));
	
	}
	
	public void setLabelText(String s) {
		this.innerText.setHTML(s);
	}
}
