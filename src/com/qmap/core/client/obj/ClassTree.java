package com.qmap.core.client.obj;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.qmap.core.client.ServerSide;
import com.qmap.core.client.ServerSideAsync;


public class ClassTree extends Tree {

	public ClassTree() {

		final ServerSideAsync servletObject = GWT.create(ServerSide.class);

		servletObject.getClassStructure(new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getLocalizedMessage());
			}

			@Override
			public void onSuccess(String result) {

				Document doc = XMLParser.parse(result);

				NodeList nl = doc.getChildNodes();
				nl = nl.item(0).getChildNodes();

				addItem(iterateTree(nl.item(1), "classChoice", 1));
				addItem(iterateTree(nl.item(3), "classChoice", 2));
				addItem(iterateTree(nl.item(5), "classChoice", 3));

			}
		});

	}

	// Recursive method for building tree.
	private static TreeItem iterateTree(Node n, String cl, final int shp) {

		ClassTreeItem tr = new ClassTreeItem(" " + n.getNodeName());
		//tr.addItem(new AddNewClassPanel(n.getNodeName(), shp));

		tr.name = n.getNodeName();
		tr.shape = shp;

		NodeList nl = n.getChildNodes();
		if (nl.getLength() > 1) {
			for (int x = 0; x < nl.getLength(); x++) {
				Node nn = nl.item(x);
				if (nn.getNodeType() == 1) {
					tr.addItem(iterateTree(nn, cl, shp));
				}
			}
		}
		

		return tr;
	}

}
