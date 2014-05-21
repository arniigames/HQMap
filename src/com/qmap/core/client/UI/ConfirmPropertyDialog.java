package com.qmap.core.client.UI;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.qmap.core.client.ServerSide;
import com.qmap.core.client.ServerSideAsync;

public class ConfirmPropertyDialog extends Composite {

	private static ConfirmPropertyDialogUiBinder uiBinder = GWT
			.create(ConfirmPropertyDialogUiBinder.class);
	@UiField
	HTMLPanel scrollText;
	@UiField
	Button cancelBtn;
	@UiField
	Button confirmButton;

	String[] rs;

	PopupPanel p;

	interface ConfirmPropertyDialogUiBinder extends
			UiBinder<Widget, ConfirmPropertyDialog> {
	}

	final ServerSideAsync servletObject = GWT.create(ServerSide.class);

	public ConfirmPropertyDialog(ArrayList<String[]> res) {
		initWidget(uiBinder.createAndBindUi(this));

		p = (PopupPanel) this.getParent();

		String html = "";

		rs = new String[res.size()];
		for (int x = 0; x < res.size(); x++) {
			String[] s = res.get(x);

			html += "<p>" + s[0] + " (" + s[3] + ") " + " - " + s[2] + " - "
					+ s[1] + " (" + s[4] + ")</p>";

			// Sub - Pred - Obj
			rs[x] = s[0] + "%%%" + s[2] + "%%%" + s[1];

		}

		scrollText.getElement().setInnerHTML(html);
	}

	@UiHandler("cancelBtn")
	void onCancelBtnClick(ClickEvent event) {
		this.scrollText.clear();
		this.getParent().removeFromParent();

	}

	@UiHandler("confirmButton")
	void onConfirmButtonClick(ClickEvent event) {

		servletObject.assignObjectProperties(rs, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("CONFIRMPROPDIAL: "
						+ caught.getLocalizedMessage());

			}

			// Ontology Updated
			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					Window.alert("Ontology successfully updated");
					cancelBtn.click();
				} else {
					Window.alert("Ontology not updated due to unkown error.");
					cancelBtn.click();
				}
			}

		});

	}
}
