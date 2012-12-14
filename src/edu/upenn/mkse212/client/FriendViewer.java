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
import java.util.Set;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import edu.upenn.mkse212.client.FriendVisualization;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class FriendViewer {
	private JavaScriptObject j;
	private final PennBook parent;
	private String user;
	
	public FriendViewer(PennBook parent) {
		this.parent = parent;
	}
	
	void display(String user) {
		this.user = user;
		if (RootPanel.get("content") == null) System.out.print("null");
		drawNodeAndNeighbors();
	}
	
	public void drawNodeAndNeighbors() {
		
		final FriendViewer fv = this;
		DatabaseAsync db = new Database.Util().getInstance();
		
		db.getFriendsOf(this.user, new AsyncCallback<List<String>>() {
			@Override
			public void onFailure(Throwable arg0) {
				parent.popupBox("RPC failure", "Cannot communicate with the server");
			}
			@Override
			public void onSuccess(List<String> result) {
				// Visualize the results!
				//This StringBuffer creates the JSON string
				StringBuffer sb = new StringBuffer();
				sb.append("{\"id\": \"");
				sb.append(user);
				sb.append("\", \"name\": \"");
				sb.append(user);
				sb.append("\", \"children\": [\n");
				boolean isFirstOne = true;
				for(String friend : result) {
					//Fence-post case--accounts for no comma after last child
					//in JSON string
					if (isFirstOne) {
						sb.append("\t{\"id\": \"");
						isFirstOne = false;
					}
					else { sb.append(",\n\t{\"id\": \""); }
					sb.append(friend);
					sb.append("\", \"name\": \"");
					sb.append(friend);
					sb.append("\", \"children\": []}");
				}
				sb.append("\n]}");
				//Creates the graph if it has not already been created.
				//Otherwise, add to the graph.
				if (j==null) {
					j = FriendVisualization.createGraph(sb.toString(), fv);
				}
				else {
					FriendVisualization.addToGraph(j, sb.toString());
				}
			}
		});
	}
}
