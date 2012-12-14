package edu.upenn.mkse212.client;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;

public class WelcomeBar {
	private final PennBook parent;
	private final AbsolutePanel p;
	
	public WelcomeBar(PennBook parent) {
		this.parent = parent;
		p = new AbsolutePanel();
	}
	
	void display() {
		p.setWidth("700px");
		p.setHeight("70px");
		
		HTML html = new HTML("<font size=\"5\">Welcome to PennBook.</font>");
		p.add(html,20,20);
		
		parent.getDockPanel().add(p,DockPanel.NORTH);
	}
	
	void hide() {
		parent.getDockPanel().remove(p);
	}

}
