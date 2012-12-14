package edu.upenn.mkse212.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;

public class SideProfilePanel {
	private final PennBook parent;
	private AbsolutePanel p;
	
	public SideProfilePanel(PennBook parent) {
		this.parent = parent;
	}
	
	void display(String username) {
		// Initlize the panel
		p = new AbsolutePanel();
		p.setWidth("150px");
		p.setHeight("500px");
		
		// Profile picture, must implement AsyncCallback
		AbsolutePanel photo = new AbsolutePanel();
		photo.setWidth("120px");
		photo.setHeight("120px");
		
		p.add(photo,10,10);
		
		p.add(new Label(username),10,150);
		
		// Add Side Menu
		PushButton button1 = new PushButton("Edit Profile");
		DOM.setStyleAttribute(button1.getElement(), "textAlign", "center");
		button1.setWidth("120px");
		p.add(button1,10,180);
		
		PushButton button2 = new PushButton("Friends");
		DOM.setStyleAttribute(button2.getElement(), "textAlign", "center");
		button2.setWidth("120px");
		p.add(button2,10,210);
		
		// Add to DockPanel
		parent.getDockPanel().add(p,DockPanel.WEST);
	}
	
	void hide() {
		parent.getDockPanel().remove(p);
	}
	

}
