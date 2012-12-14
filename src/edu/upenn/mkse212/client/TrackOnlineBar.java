package edu.upenn.mkse212.client;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.upenn.mkse212.Names;

public class TrackOnlineBar {
	private PennBook parent;
	private AbsolutePanel p;
	final HashMap<String,StringBuilder> map;
	
	public TrackOnlineBar(PennBook parent) {
		this.parent = parent;
		map = new HashMap<String,StringBuilder>();
	}
	
	void display(String username) {
		p = new AbsolutePanel();
		p.setWidth("200px");
		p.setHeight("500px");
		
		final VerticalPanel vPanel = new VerticalPanel();
		p.add(vPanel,10,10);
		vPanel.setWidth("180px");
		
		Label onlineU = new Label("Online Users:");
		onlineU.setHeight("30px");
		vPanel.add(onlineU);
		
		final HashMap<String,String> reverseMap = new HashMap<String,String>();
		parent.getDatabaseService().getUsers("", new AsyncCallback<List<String>>() {
			@Override
			public void onFailure(Throwable caught) {
				parent.popupBox("RPC failure", "Cannot communicate with the server");
			}
			@Override
			public void onSuccess(List<String> usernames) {
				for (final String user : usernames) {
					map.put(user,new StringBuilder());
					parent.getDatabaseService().getValue(user,Names.FIRST_NAME, new AsyncCallback<String>() {
						@Override
						public void onFailure(Throwable caught) {
							parent.popupBox("RPC failure", "Cannot communicate with the server");
						}
						@Override
						public void onSuccess(String firstname) {
							map.get(user).append(firstname + " ");
							parent.getDatabaseService().getValue(user,Names.LAST_NAME, new AsyncCallback<String>() {
								@Override
								public void onFailure(Throwable caught) {
									parent.popupBox("RPC failure", "Cannot communicate with the server");
								}
								@Override
								public void onSuccess(String lastname) {
									map.get(user).append(lastname);
									reverseMap.put(map.get(user).toString(), user);
									parent.getDatabaseService().getValue(user, Names.STATUS, new AsyncCallback<String>() {
										@Override
										public void onFailure(Throwable arg0) {
											parent.popupBox("RPC failure", "Cannot communicate with the server");
										}

										@Override
										public void onSuccess(String status) {
											
											if (status.equals("online")) {
												Button person = new Button();
												person.setText(map.get(user).toString());
												person.setWidth("170px");
												vPanel.add(person);
											}
										}
									});
								}
							});
						}
					});
				}
			}
		});
		
		/*parent.getDatabaseService().getUsers("", new AsyncCallback<List<String>>() {
			@Override
			public void onFailure(Throwable caught) {
				parent.popupBox("RPC failure", "Cannot communicate with the server");
			}
			@Override
			public void onSuccess(List<String> usernames) {
				for (final String s : usernames) {
					System.out.println("s");
					parent.getDatabaseService().getValue(s, Names.STATUS, new AsyncCallback<String>() {
						@Override
						public void onFailure(Throwable arg0) {
							parent.popupBox("RPC failure", "Cannot communicate with the server");
						}

						@Override
						public void onSuccess(String status) {
							
							if (status.equals("online")) {
								Button person = new Button();
								person.setText(s);
								person.setWidth("170px");
								vPanel.add(person);
							}
						}
					});
				}
			}
		});*/
		
		parent.getDockPanel().add(p,DockPanel.EAST);
	}
	
	void hide() {
		if (p != null) {parent.getDockPanel().remove(p);}
	}

}
