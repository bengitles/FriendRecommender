package edu.upenn.mkse212.client;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.upenn.mkse212.Names;

public class TrackOnlineBar {
	private PennBook parent;
	private AbsolutePanel p;
	
	public TrackOnlineBar(PennBook parent) {
		this.parent = parent;
	}
	
	void display() {
		p = new AbsolutePanel();
		p.setWidth("200px");
		p.setHeight("500px");
		
		final VerticalPanel vPanel = new VerticalPanel();
		p.add(vPanel,10,10);
		vPanel.setWidth("180px");
		
		vPanel.add(new Label("Online Users:"));
		
		final HashMap<String,StringBuilder> map = parent.getNavigationBar().getMap();
		
		for (final String s : map.keySet()) {
			parent.getDatabaseService().getValue(s, Names.STATUS, new AsyncCallback<String>() {
				@Override
				public void onFailure(Throwable arg0) {
					parent.popupBox("RPC failure", "Cannot communicate with the server");
				}

				@Override
				public void onSuccess(String status) {
					if (status.equals("online")) {
						Button person = new Button();
						person.setText(map.get(s).toString());
						person.setWidth("180px");
						vPanel.add(person);
					}
				}
			});
		}
		
		parent.getDockPanel().add(p,DockPanel.EAST);
	}
	
	void hide() {
		if (p != null) {parent.getDockPanel().remove(p);}
	}

}
