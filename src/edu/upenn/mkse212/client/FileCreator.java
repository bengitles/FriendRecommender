package edu.upenn.mkse212.client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.upenn.mkse212.Names;
import edu.upenn.mkse212.server.DatabaseImpl;


public class FileCreator {
	
	public static void main (String[] args) throws IOException {
		DatabaseAsync db = new Database.Util().getInstance();
		final Map<String, List<String>> friends = new HashMap<String, List<String>>();
		final Map<String, List<String>> interests = new HashMap<String, List<String>>();
		final Map<String, String> affiliation = new HashMap<String, String>();
		
		db.getUsers("", new AsyncCallback<List<String>>() {
			@Override
			public void onFailure(Throwable arg0) {
				System.out.println("Cannot communicate with server");
			}
			@Override
			public void onSuccess(List<String> result) {
				for (String user : result) {
					friends.put(user, new ArrayList<String>());
					interests.put(user, new ArrayList<String>());
					affiliation.put(user, "");
				}
			}
		});
		
		for (final String user : friends.keySet()) {
			db.getValues(user, Names.FRIEND, new AsyncCallback<List<String>>() {
				@Override
				public void onFailure(Throwable arg0) {
					System.out.println("Cannot communicate with server");
				}
				@Override
				public void onSuccess(List<String> result) {
					friends.get(user).addAll(result);
				}
			});
		}
		
		for (final String user : interests.keySet()) {
			db.getValues(user, Names.INTERESTS, new AsyncCallback<List<String>>() {
				@Override
				public void onFailure(Throwable arg0) {
					System.out.println("Cannot communicate with server");
				}
				@Override
				public void onSuccess(List<String> result) {
					interests.get(user).addAll(result);
				}
			});
		}
		
		for (final String user : affiliation.keySet()) {
			db.getValue(user, Names.AFFILIATION, new AsyncCallback<String>() {
				@Override
				public void onFailure(Throwable arg0) {
					System.out.println("Cannot communicate with server");
				}
				@Override
				public void onSuccess(String result) {
					interests.get(user).add(result);
				}
			});
		}
		
		BufferedWriter bw = new BufferedWriter(new FileWriter("friends.txt"));
		for (String user : friends.keySet()) {
			bw.write(user);
			bw.write('\t');
			for (String friend : friends.get(user)) {
				bw.write(friend);
				bw.write(" ");
			}
			bw.newLine();
		}
		bw.flush();
		bw.close();
		
		bw = new BufferedWriter(new FileWriter("interests.txt"));
		for (String user : interests.keySet()) {
			bw.write(user);
			bw.write('\t');
			for (String interest : interests.get(user)) {
				bw.write(interest);
				bw.write(" ");
			}
			bw.newLine();
		}
		bw.flush();
		bw.close();
		
		bw = new BufferedWriter(new FileWriter("affiliations.txt"));
		for (String user : affiliation.keySet()) {
			bw.write(user);
			bw.write('\t');
			bw.write(affiliation.get(user));
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}
}
