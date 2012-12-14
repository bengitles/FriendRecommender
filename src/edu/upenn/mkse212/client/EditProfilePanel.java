package edu.upenn.mkse212.client;

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

import edu.upenn.mkse212.Names;

public class EditProfilePanel {
	PennBook parent;
	DatabaseAsync db;
	private TabPanel panel;
	
	public EditProfilePanel(PennBook parent) {
		this.parent = parent;
		db = parent.getDatabaseService();
	}
	
	void display(final String username) {
		// Create panel for display
		panel = new TabPanel();
		
		// Security tab
		AbsolutePanel passPanel = new AbsolutePanel();
		passPanel.setWidth("500px");
		passPanel.setHeight("200px");
		panel.add(passPanel,"Security");
		
		// Current password
		Label currPassLabel = new Label("Current Password");
		final PasswordTextBox currPassInput = new PasswordTextBox();
		currPassInput.setWidth("180px");
		passPanel.add(currPassLabel,10,15);
		passPanel.add(currPassInput,150, 10);
		
		// New password
		Label newPassLabel = new Label("New Password");
		final PasswordTextBox newPassInput = new PasswordTextBox();
		newPassInput.setWidth("180px");
		passPanel.add(newPassLabel,10,65);
		passPanel.add(newPassInput,150, 60);
		
		// Re-enter new password
		Label retryPassLabel = new Label("Confirm Password");
		final PasswordTextBox retryPassInput = new PasswordTextBox();
		retryPassInput.setWidth("180px");
		passPanel.add(retryPassLabel,10,115);
		passPanel.add(retryPassInput,150, 110);
		
		// Save button
		Button savePassword = new Button("Save Password");
		DOM.setStyleAttribute(savePassword.getElement(), "textAlign", "center");
		savePassword.setWidth("130px");
		passPanel.add(savePassword,200,160);
		
		savePassword.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				parent.getDatabaseService().validateLogin(username, currPassInput.getText(),
						new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						parent.popupBox("RPC failure", "Cannot communicate with the server");
					}
					public void onSuccess(Boolean result) {
						if (!result.booleanValue()) {
							parent.popupBox("Error", "Login incorrect");
						} else {
							if (retryPassInput.getText().equals(newPassInput.getText())) {
								parent.getDatabaseService().putAttribute(username, Names.PASSWORD, 
										retryPassInput.getText(), new AsyncCallback<Boolean>() {
									public void onFailure(Throwable caught) {
										parent.popupBox("RPC failure", "Cannot communicate with the server");
									}
									public void onSuccess(Boolean result) {
										if (!result.booleanValue()) {
											currPassInput.setText("");
											newPassInput.setText("");
											retryPassInput.setText("");
											parent.popupBox("Error", "Please try again");
										} else {
											currPassInput.setText("");
											newPassInput.setText("");
											retryPassInput.setText("");
											parent.popupBox("Success", "Your password has been updated");
										}
									}
								});
							} else {
								parent.popupBox("Error", "Please confirm new password");
							}
						}
					}
				});
			}
		});
		
		// Update DockPanel
		panel.selectTab(0);
		parent.getDockPanel().add(panel,DockPanel.CENTER);
		
		/*
		
		final Label title = new Label("Editing Profile of ");
		db.getValue(username, "username", new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				parent.popupBox("RPC failure", "Cannot communicate with the server");
			}
			@Override
			public void onSuccess(String result) {
				title.setText("Editing Profile of " + result);
			}	
		});
		
		final Map<String, TextBox> attributes = new HashMap<String, TextBox>();
		attributes.put("firstName", new TextBox());
		attributes.put("lastName", new TextBox());
		attributes.put("email", new TextBox());
		attributes.put("birthday", new TextBox());
		attributes.put("affiliation", new TextBox());
		attributes.put("interests", new TextBox());
		
		for (final String name : attributes.keySet()) {
			attributes.get(name).setText("waiting...");
			if (!name.equals("interests")) {
				db.getValue(username, name, new AsyncCallback<String>() {
					@Override
					public void onFailure(Throwable caught) {
						parent.popupBox("RPC failure", "Cannot communicate with the server");
					}
					@Override
					public void onSuccess(String result) {
						attributes.get(name).setText(result);
					}
				});
			} else {
				db.getValues(username, "interests", new AsyncCallback<List<String>>() {
					@Override
					public void onFailure(Throwable caught) {
						parent.popupBox("RPC failure", "Cannot communicate with the server");
					}
					@Override
					public void onSuccess(List<String> result) {
						StringBuffer sb = new StringBuffer();
						for (String i : result) {
							sb.append(i);
							sb.append("\n");
						}
						attributes.get("interests").setText(sb.toString());
					}
				});
			}
		}
		
		final PasswordTextBox passwordField = new PasswordTextBox();
		
		AbsolutePanel p = new AbsolutePanel();
		p.setWidth("1000px");
		p.setHeight("1000px");
		p.add(title, 200, 30);
		int left = 30;
		int right = 155;
		p.add(new Label("First Name: "), left, 80);   p.add(attributes.get("firstName"), right, 80);
		p.add(new Label("Last Name: "), left, 130);   p.add(attributes.get("lastName"), right, 130);
		p.add(new Label("Email: "), left, 180);       p.add(attributes.get("firstName"), right, 180);
		p.add(new Label("Birthday: "), left, 230);    p.add(attributes.get("birthday"), right, 230);
		p.add(new Label("Affiliation: "), left, 280); p.add(attributes.get("affiliation"), right, 280);
		p.add(new Label("Interests: "), left, 330);   p.add(attributes.get("interests"), right, 330);
		p.add(new Label("Enter each one on a new line"), left, 380);
		
		final Button updateButton = new Button("Update Profile", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				parent.getDatabaseService().validateLogin(username, passwordField.getText(),
						new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						parent.popupBox("RPC failure", "Cannot communicate with the server");
					}
					public void onSuccess(Boolean result) {
						if (!result.booleanValue()) {
							parent.popupBox("Error", "Password incorrect");
						} else {
							for (final String attributeName : attributes.keySet()) {
								db.putAttribute(username, attributeName,
									attributes.get(attributeName).getText(), new AsyncCallback<Boolean>() {
										@Override
										public void onFailure(Throwable caught) {
											parent.popupBox("RPC failure",
													"Cannot communicate with the server");
										}
										@Override
										public void onSuccess(Boolean result) {
											if (!result.booleanValue()) {
												parent.popupBox("Error",
														"Invalid input for " + attributeName);
											}
										}
									});
							}
							parent.popupBox("Success", "Profile successfully updated.");
							parent.getWallPanel().display(username);
						}
					}
				});
			}
		});
		p.add(updateButton, 900, 900);
		
		RootPanel.get("rootPanelContainer").clear();
		RootPanel.get("rootPanelContainer").add(p);
		attributes.get("firstName").setFocus(true);
		attributes.get("firstName").selectAll();
		*/
	}
	
	void hide() {
		parent.getDockPanel().remove(panel);
	}
}
