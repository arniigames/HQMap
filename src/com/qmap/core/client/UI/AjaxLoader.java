package com.qmap.core.client.UI;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AjaxLoader extends Composite {

	private static AjaxLoaderUiBinder uiBinder = GWT
			.create(AjaxLoaderUiBinder.class);

	interface AjaxLoaderUiBinder extends UiBinder<Widget, AjaxLoader> {
	}

	public AjaxLoader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
