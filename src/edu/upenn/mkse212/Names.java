package edu.upenn.mkse212;

public class Names {
	public static final String USERS = "users";
	//The following are Item name fields for the Domain "users"
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String PASSWORD = "password";
	public static final String LOGINS_SO_FAR = "loginsSoFar";
	public static final String BIRTHDAY = "birthday";
	public static final String USERNAME = "username";
	public static final String INTERESTS = "interests";
	public static final String EMAIL = "email";
	public static final String AFFILIATION = "affiliation";
	
	
	public static final String WALL = "wallposts";
	//The following are Item name fields for the Domain "wallposts"
	public static final String SENDER = "sender";
	public static final String RECEIVER = "receiver";
	public static final String POST = "wallPostContent";
	public static final String COMMENT = "comment";
	public static final String NUM_COMMENTS = "numComments";
	public static final String POST_ID_NUM = "postIdNumber";
	
	public static final String FRIENDS = "friends"; //This is used to query the friends database
	
	public static final String FOLLOW_STATUS = "followStatus";
	//The following are Item name fields for the Domain "wallposts"
	public static final String RELATION = "relation";
	
	//The following are used to encode and create IDs.
	public static final String SEPARATOR = "qxz";
	public static final String TO = SEPARATOR + "to" + SEPARATOR;
	public static final String ID = SEPARATOR + "id" + SEPARATOR;
	
	public static final String EC2_NAME = "ec2-107-22-89-85.compute-1.amazonaws.com";
	
	public static final String OUTGOING_EDGES = "outgoingEdges";
}
