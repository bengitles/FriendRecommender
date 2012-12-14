package edu.upenn.mkse212.client;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.upenn.mkse212.Names;

public class TrackOnlineBar {
	PennBook parent;
	
	public TrackOnlineBar(PennBook parent) {
		this.parent = parent;
	}
	
	void display() {
		VerticalPanel vPanel = new VerticalPanel();
		
		HashMap<String,StringBuilder> map = parent.getNavigationBar().getMap();
		
		for (String s : map) {
			parent.getDatabaseService().getValue(s, Names.AFFILIATION, new AsyncCallback<String>() {

				@Override
				public void onFailure(Throwable arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(String arg0) {
					// TODO Auto-generated method stub
					
				}
				
			});
		}
		
		
	}
	

}
