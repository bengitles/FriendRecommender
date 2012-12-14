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

public class LoginPanel {
	private final PennBook parent;
	private AbsolutePanel p;
	
	public LoginPanel(PennBook theParent) {
		this.parent = theParent;
	}
	
	void display() {
		p = new AbsolutePanel();
		p.setWidth("350px");
		p.setHeight("300px");
		
		final TextBox usernameField = new TextBox();
		final PasswordTextBox passwordField = new PasswordTextBox();
		final Button loginButton = new Button("Login");
		DOM.setStyleAttribute(loginButton.getElement(), "textAlign", "center");
		
		usernameField.setWidth("180px");
		passwordField.setWidth("180px");
		loginButton.setWidth("130px");
	
		p.add(new Label("Username:"), 10, 15);
		p.add(usernameField, 110, 10);
		p.add(new Label("Password:"), 10, 65);
		p.add(passwordField, 110, 60);
		p.add(loginButton, 150, 110);
	
		parent.getDockPanel().add(p,DockPanel.WEST);
		usernameField.setFocus(true);
		usernameField.selectAll();
	
		loginButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				final String username = usernameField.getText();
				parent.getDatabaseService().validateLogin(username, passwordField.getText(),
						new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						parent.popupBox("RPC failure", "Cannot communicate with the server");
					}
					public void onSuccess(Boolean result) {
						if (!result.booleanValue()) {
							parent.popupBox("Error", "Login incorrect");
						} else {
							parent.getLoginPanel().hide();
							parent.getUserPanel().hide();
							parent.getWelcomeBar().hide();
							parent.getNavigationBar().display(username);
							parent.getSidePanel().display(username);
							parent.getWallPanel().display(username);
						}
					}
				});
			}
		});
		
		// Bypass login for debugging
		final Button debug = new Button("Debug with Arnold Palmer");
		p.add(debug,20,250);
		
		debug.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String username = "arnold.palmer@pga.com";
				parent.getLoginPanel().hide();
				parent.getUserPanel().hide();
				parent.getWelcomeBar().hide();
				parent.getNavigationBar().display(username);
				parent.getSidePanel().display(username);
				parent.getWallPanel().display(username);
			}
		});
	}

	void hide() {
		parent.getDockPanel().remove(p);
	}
	
}
