package edu.upenn.mkse212.client;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SuggestBox;

import edu.upenn.mkse212.Names;

public class CommandBar {
	private final PennBook parent;
	private AbsolutePanel p;
	private String username;
	private final HashMap<String,StringBuilder> oracleMap;
	
	public CommandBar(PennBook parent) {
		this.parent = parent;
		oracleMap = new HashMap<String,StringBuilder>();
	}
	
	void display(final String username) {
		// Initialize the panel
		this.username = username;
		p = new AbsolutePanel();
		
		// Set size of CommandBar
		p.setWidth("900px");
		p.setHeight("70px");
		
		// Add logo
		Image crest = new Image("/Users/drewtrager/Documents/workspace/PennBook/crest.png");
		p.add(crest,10,15);
		HTML html = new HTML("<font size=\"4\">PennBook</font>");
		p.add(html,50,15);
		
		crest.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Go to newsfeed
			}
		});
		
		// Add search bar
		final HashMap<String,String> reverseMap = new HashMap<String,String>();
		final MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
		
		final SuggestBox sbox = new SuggestBox(oracle);
		sbox.setWidth("250px");
		p.add(sbox,150,10);
				
		// Import friend list for recommendations
		parent.getDatabaseService().getUsers("", new AsyncCallback<List<String>>() {
				@Override
				public void onFailure(Throwable caught) {
					parent.popupBox("RPC failure", "Cannot communicate with the server");
				}
				@Override
				public void onSuccess(List<String> usernames) {
					for (final String user : usernames) {
						oracleMap.put(user,new StringBuilder());
						parent.getDatabaseService().getValue(user,Names.FIRST_NAME, new AsyncCallback<String>() {
							@Override
							public void onFailure(Throwable caught) {
								parent.popupBox("RPC failure", "Cannot communicate with the server");
							}
							@Override
							public void onSuccess(String firstname) {
								oracleMap.get(user).append(firstname + " ");
								parent.getDatabaseService().getValue(user,Names.LAST_NAME, new AsyncCallback<String>() {
									@Override
									public void onFailure(Throwable caught) {
										parent.popupBox("RPC failure", "Cannot communicate with the server");
									}
									@Override
									public void onSuccess(String lastname) {
										oracleMap.get(user).append(lastname);
										reverseMap.put(oracleMap.get(user).toString(), user);
										oracle.add(oracleMap.get(user).toString());
									}
								});
							}
						});
					}
				}
		});
		
		// Go to other user profile
		sbox.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent arg0) {
				if (arg0.getNativeKeyCode() == 13 && (reverseMap.containsKey(sbox.getText()))) {
					String other = reverseMap.get(sbox.getText());
					if (!other.equals(null)) {
						parent.getSidePanel().hide();
						parent.getWallPanel().hide();
						parent.getEditPanel().hide();
						parent.getSidePanel().display(other);
						parent.getWallPanel().display(other);
						sbox.setText("");
					}
				}	
			}	
		});
		
		// Add menu
		final PopupPanel popup = new PopupPanel(true);
		MenuBar options = new MenuBar(true);
		popup.add(options);
		
		options.addItem("View Profile", new Command() {
			public void execute() {
				popup.hide();
				parent.getSidePanel().hide();
				parent.getWallPanel().hide();
				parent.getEditPanel().hide();
				parent.getSidePanel().display(username);
				parent.getWallPanel().display(username);
			}
		});
		
		options.addItem("Newfeed", new Command() {
			public void execute() {
				// Navigate to edit profile page
			}
		});
		
		options.addItem("Edit Profile", new Command() {
			public void execute() {
				popup.hide();
				parent.getSidePanel().hide();
				parent.getEditPanel().hide();
				parent.getWallPanel().hide();
				parent.getSidePanel().display(username);
				parent.getEditPanel().display(username);
			}
		});
		
		final Button menu = new Button(username);
		DOM.setStyleAttribute(menu.getElement(), "textAlign", "center");
		menu.setWidth("150px");
		menu.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				popup.setPopupPosition(menu.getAbsoluteLeft() + 10, menu.getAbsoluteTop() + 25);
				popup.show();
			}
		});
		
		p.add(menu,470,15);
		
		// Add logout button
		Button logout = new Button("Logout");
		DOM.setStyleAttribute(logout.getElement(), "textAlign", "center");
		logout.setWidth("60px");
		
		// Add ClickHandler to button
		logout.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				parent.getNavigationBar().hide();
				parent.getWelcomeBar().display();
				parent.getLoginPanel().display();
				parent.getUserPanel().display();
			}
		});
		
		p.add(logout,630,15);
		
		// Get user's first name
		final StringBuilder sb = new StringBuilder();
		parent.getDatabaseService().getValue(username, Names.FIRST_NAME, new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				parent.popupBox("RPC failure", "Cannot communicate with the server");
			}
			@Override
			public void onSuccess(String result) {
				sb.append(result + " ");
				// Get user's last name
				parent.getDatabaseService().getValue(username, Names.LAST_NAME, new AsyncCallback<String>() {
					@Override
					public void onFailure(Throwable caught) {
						parent.popupBox("RPC failure", "Cannot communicate with the server");
					}
					@Override
					public void onSuccess(String result) {
						menu.setText(sb.append(result).toString());
					}
				});
			}
		});
		
		// Add panel to DockPanel
		parent.getDockPanel().add(p,DockPanel.NORTH);
	}
	
	void hide() {parent.getDockPanel().remove(p);}
	
	String getUser() {return username;}
	
	HashMap<String,StringBuilder> getMap() {return oracleMap;}

}
