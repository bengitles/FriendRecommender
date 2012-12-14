package edu.upenn.mkse212.client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.upenn.mkse212.Names;
import edu.upenn.mkse212.server.DatabaseImpl;


public class FileCreator {
	
	public static void main (String[] args) throws IOException {
		DatabaseImpl db = new DatabaseImpl();
		final Map<String, List<String>> friends = new HashMap<String, List<String>>();
		final Map<String, List<String>> interests = new HashMap<String, List<String>>();
		final Map<String, String> affiliation = new HashMap<String, String>();
		
		for (String user : db.getUsers("")) {
			friends.put(user, new ArrayList<String>());
			interests.put(user, new ArrayList<String>());
			affiliation.put(user, "");
		}
		
		for (final String user : friends.keySet()) {
			friends.get(user).addAll(db.getValues(user, Names.FRIEND));
		}
		
		for (final String user : interests.keySet()) {
					interests.get(user).addAll(db.getValues(user, Names.INTERESTS));
		}
		
		for (final String user : affiliation.keySet()) {
			affiliation.put(user, db.getValue(user, Names.AFFILIATION));
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
