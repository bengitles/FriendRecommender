package edu.upenn.mkse212.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;

import edu.upenn.mkse212.Names;

public class ListOfFriendsTab {

	PennBook parent;
	DatabaseAsync db;
	private TabPanel panel;
	
	public ListOfFriendsTab(PennBook parent) {
		this.parent = parent;
		db = parent.getDatabaseService();
	}
	
	void display(final String username) {
		// Create panel for display
		panel = new TabPanel();
		
		AbsolutePanel friendsPanel = new AbsolutePanel();
		friendsPanel.setWidth("500px");
		friendsPanel.setHeight("400px");
		panel.add(friendsPanel,"Friends");
		
		final List<Label> list = new ArrayList<Label>();
		db.getFriendsOf(username, new AsyncCallback<List<String>>() {
			@Override
			public void onFailure(Throwable arg0) {
				parent.popupBox("RPC failure", "Cannot communicate with the server");
			}

			@Override
			public void onSuccess(List<String> friends) {
				for (final String friend : friends) {
					db.getValue(friend, Names.FIRST_NAME, new AsyncCallback<String>() {
						@Override
						public void onFailure(Throwable arg0) {
							parent.popupBox("RPC failure", "Cannot communicate with the server");
						}
						@Override
						public void onSuccess(final String firstName) {
							db.getValue(friend, Names.LAST_NAME, new AsyncCallback<String>() {
								@Override
								public void onFailure(Throwable arg0) {
									parent.popupBox("RPC failure", "Cannot communicate with the server");
								}
								@Override
								public void onSuccess(String lastName) {
									list.add(new Label(firstName + " " + lastName));
								}
							});
						}
					});
				}
			}
		});
		friendsPanel.setHeight("" + (list.size()*10) + "px");
		for (Label l : list) friendsPanel.add(l);
	}
}
