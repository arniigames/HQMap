package com.qmap.core.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("sside")
public interface ServerSide extends RemoteService {
	String reply(String id, int type, String name, String coords, String desc,
			int area, String cls);

	String loadExistingMapObjects(String string);

	String[] getCandidateRelationships();

	String getClassStructure();

	String getValidObjectProperties(String sub,String obj);

	String augmentClassStructure(String text, String referer, String referer2);

	boolean assignObjectProperties(String[] rs);

	boolean createNewProperty(String sub, String pred, String obj);

}
