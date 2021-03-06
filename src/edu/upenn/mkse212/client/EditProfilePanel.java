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
		
		/*-------------------Query Details-------------------*/
		final Map<String, Label> attributes = new HashMap<String, Label>();
		attributes.put(Names.FIRST_NAME, new Label());
		attributes.put(Names.LAST_NAME, new Label());
		attributes.put(Names.EMAIL, new Label());
		attributes.put(Names.BIRTHDAY, new Label());
		attributes.put(Names.AFFILIATION, new Label());
		attributes.put(Names.INTERESTS, new Label());
		
		for (final String att : attributes.keySet()) {
			attributes.get(att).setText("loading...");
			if (!att.equals(Names.INTERESTS)) {
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
				db.getValues(username, Names.INTERESTS, new AsyncCallback<List<String>>() {
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
						attributes.get(Names.INTERESTS).setText(sb.toString());
					}
				});
			}
		}
		
		/*-------------------Information Tab-------------------*/
		AbsolutePanel infoPanel = new AbsolutePanel();
		infoPanel.setWidth("500px");
		infoPanel.setHeight("400px");
		panel.add(infoPanel,"Information");
		
		// E-mail address
		Label emailLabel = new Label("E-mail:");
		final TextBox emailInput = new TextBox();
		final Button emailButton = new Button("Update");
		emailInput.setWidth("180px");
		infoPanel.add(emailLabel,10,10);
		infoPanel.add(attributes.get(Names.EMAIL),80, 10);
		infoPanel.add(emailInput,20,40);
		infoPanel.add(emailButton,220,40);
		
		emailButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(!emailInput.getText().equals("")) {
					parent.getDatabaseService().putAttribute(username, Names.EMAIL, 
							emailInput.getText(), new AsyncCallback<Boolean>() {
						public void onFailure(Throwable caught) {
							parent.popupBox("RPC failure", "Cannot communicate with the server");
						}
						public void onSuccess(Boolean result) {
							attributes.get(Names.EMAIL).setText(emailInput.getText());
							emailInput.setText("");
						}
					});
					
				}
			}
		});
		
		// Affiliation
		Label affiLabel = new Label("Affiliation:");
		final TextBox affiInput = new TextBox();
		final Button affiButton = new Button("Update");
		affiInput.setWidth("180px");
		infoPanel.add(affiLabel,10,90);
		infoPanel.add(attributes.get(Names.AFFILIATION),80, 90);
		infoPanel.add(affiInput,20,120);
		infoPanel.add(affiButton,220,120);
		
		affiButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(!affiInput.getText().equals("")) {
					db.putAttribute(username, Names.AFFILIATION, 
							affiInput.getText(), new AsyncCallback<Boolean>() {
						public void onFailure(Throwable caught) {
							parent.popupBox("RPC failure", "Cannot communicate with the server");
						}
						public void onSuccess(Boolean result) {
							attributes.get(Names.AFFILIATION).setText(affiInput.getText());
							affiInput.setText("");

						}
					});
					
				}
			}
		});
		
		// Birthday
		Label bdayLabel = new Label("Birthday:");
		final TextBox bdayInput = new TextBox();
		final Button bdayButton = new Button("Update");
		bdayInput.setWidth("180px");
		infoPanel.add(bdayLabel,10,170);
		infoPanel.add(attributes.get(Names.BIRTHDAY),80, 170);
		infoPanel.add(bdayInput,20,200);
		infoPanel.add(bdayButton,220,200);
		
		bdayButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(!bdayInput.getText().equals("")) {
					db.putAttribute(username, Names.BIRTHDAY, 
							bdayInput.getText(), new AsyncCallback<Boolean>() {
						public void onFailure(Throwable caught) {
							parent.popupBox("RPC failure", "Cannot communicate with the server");
						}
						public void onSuccess(Boolean result) {
							attributes.get(Names.BIRTHDAY).setText(bdayInput.getText());
							bdayInput.setText("");
						}
					});
					
				}
			}
		});
		
		// Interests
		Label interLabel = new Label("Interests:");
		final TextBox interInput = new TextBox();
		final Button interButton = new Button("Add");
		interInput.setWidth("180px");
		infoPanel.add(interLabel,10,250);
		infoPanel.add(attributes.get(Names.INTERESTS),80, 250);
		infoPanel.add(interInput,20,280);
		infoPanel.add(interButton,220,280);
		
		interButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(!interInput.getText().equals("")) {
					ArrayList<String> arr = new ArrayList<String>();
					arr.add(interInput.getText());
					parent.getDatabaseService().putAttributes(username, Names.INTERESTS, arr, 
							new AsyncCallback<Boolean>() {
						public void onFailure(Throwable caught) {
							parent.popupBox("RPC failure", "Cannot communicate with the server");
						}
						public void onSuccess(Boolean result) {
							db.getValues(username,Names.INTERESTS, new AsyncCallback<List<String>>() {
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
									attributes.get(Names.INTERESTS).setText(sb.toString());
									interInput.setText("");
								}
							});
						}
					});
				}
			}
		});
		
		/*--------------------Security Tab--------------------*/
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
		
		/*--------------------List of Friends Tab--------------------*/
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
		
		// Update DockPanel
		panel.selectTab(0);
		parent.getDockPanel().add(panel,DockPanel.EAST);
	}
	
	void hide() {
		if (panel != null) {parent.getDockPanel().remove(panel);}
	}
}
