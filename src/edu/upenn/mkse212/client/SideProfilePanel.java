package edu.upenn.mkse212.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;

import edu.upenn.mkse212.Names;

public class SideProfilePanel {
	private final PennBook parent;
	private AbsolutePanel p;
	
	public SideProfilePanel(PennBook parent) {
		this.parent = parent;
	}
	
	void display(final String username) {
		// Initlize the panel
		p = new AbsolutePanel();
		p.setWidth("150px");
		p.setHeight("500px");
		
		// Profile picture, must implement AsyncCallback
		AbsolutePanel photo = new AbsolutePanel();
		photo.setWidth("120px");
		photo.setHeight("120px");
		
		p.add(photo,10,10);
		
		final Label nameLabel = new Label(username);
		DOM.setStyleAttribute(nameLabel.getElement(), "textAlign", "center");
		nameLabel.setWidth("120px");
		p.add(nameLabel,10,150);
		
		// Add Side Menu
		PushButton button1 = new PushButton("Friends");
		DOM.setStyleAttribute(button1.getElement(), "textAlign", "center");
		button1.setWidth("120px");
		p.add(button1,10,180);
		
		PushButton button2 = new PushButton("Visualize");
		DOM.setStyleAttribute(button2.getElement(), "textAlign", "center");
		button2.setWidth("120px");
		p.add(button2,10,210);
		
		
		if (parent.getNavigationBar().getUser().equals(username)) {
			PushButton button3 = new PushButton("Edit Profile");
			DOM.setStyleAttribute(button3.getElement(), "textAlign", "center");
			button3.setWidth("120px");
			p.add(button3,10,240);
		} else {
			parent.getDatabaseService().isFriendsWith(parent.getNavigationBar().getUser(), username, new AsyncCallback<Boolean>() {
				@Override
				public void onFailure(Throwable caught) {
					parent.popupBox("RPC failure", "Cannot communicate with the server");
				}
				
				@Override
				public void onSuccess(Boolean isFollowing) {
					System.out.print(isFollowing);
					if (isFollowing) {
						PushButton button3 = new PushButton("Unfollow");
						DOM.setStyleAttribute(button3.getElement(), "textAlign", "center");
						button3.setWidth("120px");
						p.add(button3,10,240);
						
						button3.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								parent.getDatabaseService().removeFriend(parent.getNavigationBar().getUser(), username, new AsyncCallback<Boolean>() {
									@Override
									public void onFailure(Throwable caught) {
										parent.popupBox("RPC failure", "Cannot communicate with the server");
									}
									
									@Override
									public void onSuccess(Boolean b) {
										parent.getSidePanel().hide();
										parent.getSidePanel().display(username);
									}
								});
							}			
						});
					} else {
						PushButton button3 = new PushButton("Follow");
						DOM.setStyleAttribute(button3.getElement(), "textAlign", "center");
						button3.setWidth("120px");
						p.add(button3,10,240);
						
						button3.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								parent.getDatabaseService().addFriend(parent.getNavigationBar().getUser(), username, new AsyncCallback<Boolean>() {
									@Override
									public void onFailure(Throwable caught) {
										parent.popupBox("RPC failure", "Cannot communicate with the server");
									}
									
									@Override
									public void onSuccess(Boolean b) {
										parent.getSidePanel().hide();
										parent.getSidePanel().display(username);
									}
								});
							}			
						});
					}
				}
			});
		}
		
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
			}
		});
		
		// Get user's last name
		parent.getDatabaseService().getValue(username, Names.LAST_NAME, new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				parent.popupBox("RPC failure", "Cannot communicate with the server");
			}
			@Override
			public void onSuccess(String result) {
				nameLabel.setText(sb.append(result).toString());
			}
		});
		
		// Add to DockPanel
		parent.getDockPanel().add(p,DockPanel.WEST);
	}
	
	void hide() {
		if (p != null) {parent.getDockPanel().remove(p);}
	}
}
