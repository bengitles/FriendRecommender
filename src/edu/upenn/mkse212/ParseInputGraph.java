package edu.upenn.mkse212;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import edu.upenn.mkse212.IKeyValueStorage;
import edu.upenn.mkse212.KeyValueStoreFactory;

public class ParseInputGraph {
	
  public static void main(String[] args) throws IOException {
    IKeyValueStorage storageSystem = KeyValueStoreFactory.getKeyValueStore(KeyValueStoreFactory.STORETYPE.BERKELEY, 
		    "socialGraph", "/home/mkse212/bdb/", "user", "authKey", false);

    File here = new File(".");

    int count = 0;

    for ( String fname : here.list()) {
      if (fname.startsWith("livejournal-links")) {
		System.out.println("Parsing " + fname + "...");
		BufferedReader br = new BufferedReader(new FileReader(fname));
	
	        // TODO: parse this file!
		String c = br.readLine();
		while(c != null) {
			String[] s = br.readLine().split("\t");
			storageSystem.put(s[0], s[1]);
			count++;
			c = br.readLine();
		}
      }
    }

    System.out.println("\nLoaded " + count + " edges");

    storageSystem.close();
  }
}
