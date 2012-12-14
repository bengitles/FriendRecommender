package edu.upenn.mkse212.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PennBook implements EntryPoint {
	private final DatabaseAsync databaseService = GWT.create(Database.class);
	private DockPanel dockPanel;
	private WelcomeBar welcomeBar;
	private ProfilePanel wallPanel;
	private LoginPanel loginPanel;
	private NewUserPanel userPanel;
	private SideProfilePanel sidePanel;
	private CommandBar comBar;
	private EditProfilePanel editPanel;
  
	protected DatabaseAsync getDatabaseService() {return databaseService;}
	
	protected DockPanel getDockPanel() {return dockPanel;}
  
	protected ProfilePanel getWallPanel() {return wallPanel;}
  
	protected LoginPanel getLoginPanel() {return loginPanel;}
	
	protected NewUserPanel getUserPanel() {return userPanel;}
	
	protected SideProfilePanel getSidePanel() {return sidePanel;}
	
	protected WelcomeBar getWelcomeBar() {return welcomeBar;}
	
	protected CommandBar getNavigationBar() {return comBar;}
	
	protected EditProfilePanel getEditPanel() {return editPanel;}
  
	public void onModuleLoad() {
		dockPanel = new DockPanel();
		DOM.setStyleAttribute(dockPanel.getElement(), "border", "2px solid black");
		dockPanel.getElement().getStyle().setBackgroundColor("#E6E6FA");
		RootPanel.get("rootPanelContainer").add(dockPanel);
		
		welcomeBar = new WelcomeBar(this);
		loginPanel = new LoginPanel(this);
		userPanel = new NewUserPanel(this);
		wallPanel = new ProfilePanel(this);
		sidePanel = new SideProfilePanel(this);
		comBar = new CommandBar(this);
		editPanel = new EditProfilePanel(this);
		
		welcomeBar.display();
		loginPanel.display();
		userPanel.display();
	}
	
	protected void popupBox(String title, String message) {
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText(title);
		dialogBox.setAnimationEnabled(true);
	
		Button closeButton = new Button("Close");
		VerticalPanel dialogVPanel = new VerticalPanel();
	
		dialogVPanel.add(new HTML(message));
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);
		dialogBox.center();
		
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
		});
	}

}
