package com.qmap.core.client.obj;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class ClassTreeItem extends TreeItem {
	
	public int shape;
	public String name;
	
	

	public ClassTreeItem() {
		// TODO Auto-generated constructor stub
	}

	public ClassTreeItem(String html) {
		super(html);
		
		
		//this.addItem(new AddNewClassPanel(name, shape));
		// TODO Auto-generated constructor stub
	}

	public ClassTreeItem(SafeHtml html) {
		super(html);
		// TODO Auto-generated constructor stub
	}

	public ClassTreeItem(Widget widget) {
		super(widget);
		// TODO Auto-generated constructor stub
	}

}
