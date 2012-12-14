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
import com.google.gwt.user.client.ui.TextBox;

public class NewUserPanel {
	private final PennBook parent;
	private AbsolutePanel p;
	
	public NewUserPanel(PennBook theParent) {
		this.parent = theParent;
	}
	
	void display() {
		p = new AbsolutePanel();
		p.setWidth("350px");
		p.setHeight("300px");
		
		final TextBox fNameField = new TextBox();
		final TextBox lNameField = new TextBox();
		final TextBox emailField = new TextBox();
		final PasswordTextBox passwordField = new PasswordTextBox();
		final Button createButton = new Button("Create Account");
		DOM.setStyleAttribute(createButton.getElement(), "textAlign", "center");
		
		fNameField.setWidth("180px");
		lNameField.setWidth("180px");
		emailField.setWidth("180px");
		passwordField.setWidth("180px");
		createButton.setWidth("130px");
		
		p.add(new Label("First Name:"), 10, 15);
		p.add(fNameField, 110, 10);
		p.add(new Label("Last Name:"), 10, 65);
		p.add(lNameField, 110, 60);
		p.add(new Label("E-mail:"), 10, 115);
		p.add(emailField, 110, 110);
		p.add(new Label("Password:"), 10, 165);
		p.add(passwordField, 110, 160);
		p.add(createButton, 150, 210);
	
		parent.getDockPanel().add(p,DockPanel.EAST);
	
		createButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				final String fName = fNameField.getText();
				final String lName = lNameField.getText();
				final String email = emailField.getText();
				final String password = passwordField.getText();
				parent.getDatabaseService().createAccount(fName, lName, email, password,
						new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						parent.popupBox("RPC failure", "Cannot communicate with the server");
					}
					public void onSuccess(Boolean result) {
						if (!result.booleanValue()) {
							parent.popupBox("Error", "E-mail address already in use");
						} else {
							fNameField.setText("");
							lNameField.setText("");
							emailField.setText("");
							passwordField.setText("");
							parent.popupBox("Success", "Log in to get started!");
						}
					}
				});
			}
		});
	}
	
	void hide() {
		parent.getDockPanel().remove(p);
	}

}
