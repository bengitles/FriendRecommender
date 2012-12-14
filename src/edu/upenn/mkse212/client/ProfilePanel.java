package edu.upenn.mkse212.client;

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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ProfilePanel {
	private PennBook parent;
	private DatabaseAsync db;
	private TabPanel panel;
	
	public ProfilePanel(PennBook theParent) {
		this.parent = theParent;
		db = parent.getDatabaseService();
	}
	
	void display(final String username) {
		// Create map to store labels
		final Map<String, Label> attributes = new HashMap<String, Label>();
		attributes.put("firstName", new Label());
		attributes.put("lastName", new Label());
		attributes.put("email", new Label());
		attributes.put("birthday", new Label());
		attributes.put("affiliation", new Label());
		attributes.put("interests", new Label());
		attributes.put("loginsSoFar", new Label());
		
		// Grab info from database
		for (final String att : attributes.keySet()) {
			attributes.get(att).setText("loading...");
			if (!att.equals("interests")) {
				db.getValue(username, att, new AsyncCallback<String>() {
					@Override
					public void onFailure(Throwable caught) {
						parent.popupBox("RPC failure", "Cannot communicate with the server");
					}
					@Override
					public void onSuccess(String result) {
						attributes.get(att).setText(result);
					}
				});
			} else {
				db.getValues(username, "interests", new AsyncCallback<List<String>>() {
					@Override
					public void onFailure(Throwable caught) {
						parent.popupBox("RPC failure", "Cannot communicate with the server");
					}
					@Override
					public void onSuccess(List<String> results) {
						StringBuffer sb = new StringBuffer();
						
						int counter = 1;
						for (String s : results) {
							sb.append(s);
							if (counter < results.size())
								sb.append(", ");
							counter++;
						}
						attributes.get("interests").setText(sb.toString());
					}
				});
			}
		}
		
		// Create panel for display
		panel = new TabPanel();
		
		// Wall tab
		VerticalPanel wall = new VerticalPanel();
		wall.setWidth("500px");
		panel.add(wall,"Wall");
		
		// Panel to hold input form
		AbsolutePanel inputPanel = new AbsolutePanel();
		inputPanel.setWidth("480px");
		inputPanel.setHeight("110px");
		wall.add(inputPanel);
		
		// Text input area
		final TextArea inputBox = new TextArea();
		inputBox.setWidth("460px");
		inputBox.setHeight("50px");
		inputBox.setText("Write something...");
		inputPanel.add(inputBox,10,10);
		
		// Post button
		Button postButton = new Button("Post");
		DOM.setStyleAttribute(postButton.getElement(), "textAlign", "center");
		postButton.setWidth("100px");
		inputPanel.add(postButton,350,80);
		
		// Add ClickHandler to button
		postButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
				// Print inputs to check their validity
				System.out.println("Sender: " + parent.getNavigationBar().getUser());
				System.out.println("Person: " + username);
				System.out.println(inputBox.getText());
				
				parent.getDatabaseService().postOnWall(parent.getNavigationBar().getUser(), username,
						inputBox.getText(), new AsyncCallback<String>() {
							@Override
							public void onFailure(Throwable caught) {
								parent.popupBox("RPC failure", "Cannot communicate with the server");
							}
							@Override
							public void onSuccess(String result) {
								// Add post to wall
							}
				});
			}
		});
		
		// Information tab
		VerticalPanel info = new VerticalPanel();
		info.setWidth("500px");
		panel.add(info,"Information");
		
		HorizontalPanel email = new HorizontalPanel();
		email.add(new Label("Email: "));
		email.add(attributes.get("email"));
		info.add(email);
		
		HorizontalPanel bday = new HorizontalPanel();
		bday.add(new Label("Birthday: "));
		bday.add(attributes.get("birthday"));
		info.add(bday);
		
		HorizontalPanel affil = new HorizontalPanel();
		affil.add(new Label("Affiliation: "));
		affil.add(attributes.get("affiliation"));
		info.add(affil);
		
		HorizontalPanel interests = new HorizontalPanel();
		interests.add(new Label("Interests: "));
		interests.add(attributes.get("interests"));
		info.add(interests);
	
		// Update DockPanel
		panel.selectTab(0);
		parent.getDockPanel().add(panel,DockPanel.EAST);
	}
	
	void hide() {
		if (panel != null) {parent.getDockPanel().remove(panel);}
	}
}
