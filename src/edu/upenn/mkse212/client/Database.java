/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package edu.upenn.mkse212.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("Database")
public interface Database extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static DatabaseAsync instance;
		public static DatabaseAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(Database.class);
			}
			return instance;
		}
	}
	
	Boolean validateLogin(String username, String password);
	
	Integer incrementLogins(String username);
	
	Boolean createAccount(String firstName, String lastName, String email, String password);
	
	Boolean putAttribute(String username, String attribute, String value);
	
	Boolean putAttributes(String username, String attribute, List<String> values);
	
	List<String> getUsers(String prefix);
	
	String getValue(String username, String attribute);
	
	List<String> getValues(String username, String attribute);
	
	Boolean isFriendsWith(String username, String other);
	
	Boolean addFriend(String username, String other);
	
	Boolean removeFriend(String username, String other);
	
	List<String> getFriendsOf(String username);
	
	String postOnWall(String username, String other, String content);
	
	String postStatus(String username, String content);
	
	String commentOnWallPost(String username, String wallPostID, String content);
	
	String getWallPost(String wallPostID);
	
	String[] getWallPostInfo(String wallPostID);

	Boolean updateFollowStatus(String user1, String user2, Boolean b);
	
	Boolean getFollowStatus(String user1, String user2);
	
	//List<String> getWallPostsIDs(String username, String other);
	
	List<String> getWallPostsIDs(String receiver);
	
	//List<String> getWallPosts(String username, String other);
	
	//List<String> getPostsOnWall(String receiver);
}
