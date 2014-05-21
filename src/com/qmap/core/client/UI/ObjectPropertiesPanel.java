package com.qmap.core.client.UI;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class ObjectPropertiesPanel extends Composite {

	private static ObjectPropertiesPanelUiBinder uiBinder = GWT
			.create(ObjectPropertiesPanelUiBinder.class);
	@UiField
	Label subjectLabel;

	@UiField
	ScrollPanel propertiesPanelScroller;
	@UiField
	VerticalPanel propertiesVPanel;

	@UiField
	Label objectLabel;
	@UiField
	Button createNewProperty;
	@UiField
	Button createNewDataProperty;
	@UiField
	Button confirmBtn;


	private int status = 0;

	interface ObjectPropertiesPanelUiBinder extends
			UiBinder<Widget, ObjectPropertiesPanel> {
	}

	public ObjectPropertiesPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int s) {
		status = s;
	}

	public String getSubjectLabel() {
		return subjectLabel.getText();
	}

	public void setSubjectLabel(String subjectLabel) {
		this.subjectLabel.setText(subjectLabel);
	}

	public String getObjectLabel() {
		return objectLabel.getText();
	}

	public void setObjectLabel(String objectLabel) {
		this.objectLabel.setText(objectLabel);
	}

	public VerticalPanel getPropertiesVPanel() {
		return propertiesVPanel;
	}

	public void setPropertiesVPanel(VerticalPanel propertiesVPanel) {
		this.propertiesVPanel = propertiesVPanel;
	}

	// Process valid relationships.
	@UiHandler("confirmBtn")
	void onConfirmBtnClick(ClickEvent event) {
		ArrayList<String[]> res = new ArrayList<String[]>();
		if (propertiesVPanel.getWidgetCount() > 0) {

			int cnt = propertiesVPanel.getWidgetCount();

			for (int x = 0; x < cnt; x++) {
				PropertySuggestion p = (PropertySuggestion) propertiesVPanel
						.getWidget(x);

				if (p.isOn()) {

					String[] st = new String[5];
					st[0] = p.getSubjectIndividual();
					st[1] = p.getObjectIndividual();
					st[2] = p.getProperty();
					st[3] = p.getSubject();
					st[4] = p.getObject();
					res.add(st);
				}

			}

			PopupPanel p = new PopupPanel();
			p.setGlassEnabled(true);
			ConfirmPropertyDialog pp = new ConfirmPropertyDialog(res);
			p.add(pp);
			p.center();
			p.show();

		}

	}
	@UiHandler("createNewProperty")
	void onCreateNewPropertyClick(ClickEvent event) {
		PopupPanel pop = new PopupPanel();
		pop.setTitle("Create New Object Property");
		
		NewPropertyDialog npd = new NewPropertyDialog(subjectLabel.getText(), objectLabel.getText());
		
		pop.add(npd);
		pop.setGlassEnabled(true);
		pop.center();
		
		pop.show();
		
		
		
	}

	public Button getCreateNewProperty() {
		return createNewProperty;
	}

	public void setCreateNewProperty(Button createNewProperty) {
		this.createNewProperty = createNewProperty;
	}

	public Button getCreateNewDataProperty() {
		return createNewDataProperty;
	}

	public void setCreateNewDataProperty(Button createNewDataProperty) {
		this.createNewDataProperty = createNewDataProperty;
	}
	

	public Button getConfirmBtn() {
		return confirmBtn;
	}

	public void setConfirmBtn(Button confirmBtn) {
		this.confirmBtn = confirmBtn;
	}
}
