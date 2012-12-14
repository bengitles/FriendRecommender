package edu.upenn.mkse212.client;

import java.util.*;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DatabaseAsync {
	void validateLogin(String username, String password, AsyncCallback<Boolean> callback)
		throws IllegalArgumentException;
	
	void incrementLogins(String username, AsyncCallback<Integer> callback)
		throws IllegalArgumentException;
	
	void createAccount(String fName, String lName, String email, String password,
		AsyncCallback<Boolean> callback) throws IllegalArgumentException;
	
	void getValue(String username, String attribute, AsyncCallback<String> callback)
		throws IllegalArgumentException;
	
	void getValues(String username, String attribute, AsyncCallback<List<String>> callback)
		throws IllegalArgumentException;
	
	void putAttribute(String username, String attribute, String value,
			AsyncCallback<Boolean> callback) throws IllegalArgumentException;
	
	void putAttributes(String username, String attribute, List<String> values,
			AsyncCallback<Boolean> callback) throws IllegalArgumentException;
	
	void getUsers(String prefix, AsyncCallback<List<String>> callback)
		throws IllegalArgumentException;
	
	void isFriendsWith(String username, String other, AsyncCallback<Boolean> callback)
		throws IllegalArgumentException;
	
	void addFriend(String username, String other, AsyncCallback<Boolean> callback)
		throws IllegalArgumentException;
	
	void removeFriend(String username, String other, AsyncCallback<Boolean> callback)
		throws IllegalArgumentException;
	
	void getFriendsOf(String username, AsyncCallback<List<String>> callback)
		throws IllegalArgumentException;
	
	void postOnWall(String username, String other, String message,
			AsyncCallback<String> callback) throws IllegalArgumentException;
	
	void postStatus(String username, String content, AsyncCallback<String> callback)
		throws IllegalArgumentException;
	
	void commentOnWallPost(String username, String wallPostID, String content,
			AsyncCallback<String> callback) throws IllegalArgumentException;
	
	void getWallPost(String wallPostID, AsyncCallback<String> callback)
		throws IllegalArgumentException;
	
	void getWallPostsIDs(String receiver, AsyncCallback<List<String>> callback)
		throws IllegalArgumentException;
	
	 void getWallPostInfo(String wallPostID, AsyncCallback<String[]> callback)
	 	throws IllegalArgumentException;
	 
	 void updateFollowStatus(String user1, String user2, Boolean b, AsyncCallback<Boolean> callback)
	 	throws IllegalArgumentException;
	 
	 void getFollowStatus(String user1, String user2, AsyncCallback<Boolean> callback)
			 	throws IllegalArgumentException;
	 
	//void getWallPosts(String username, String other, AsyncCallback<List<String>> callback)
	//	throws IllegalArgumentException;
		
	//void getPostsOnWall(String receiver, AsyncCallback<List<String>> callback)
	//	throws IllegalArgumentException;
	 
	//void getWallPostsIDs(String username, String other,
	//		AsyncCallback<List<String>> callback) throws IllegalArgumentException;
}
