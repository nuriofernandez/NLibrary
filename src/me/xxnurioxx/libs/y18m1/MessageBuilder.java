/**
 * 
 * N_Library by xXNurioXx [www.xxnurioxx.me]
 * Pack: [y18m1] Last Modify: 1/1/2018
 * Twitter: [https://twitter.com/xXNurioXx]
 * Mail: [personal@xxnurioxx.me]
 * 
 * @author xXNurioXx
 * @Target Bukkit
 */

package me.xxnurioxx.libs.y18m1;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class MessageBuilder {

	/* Start of global zone */
	
	public static ConcurrentHashMap<String, ArrayList<String>> messages = new ConcurrentHashMap<String, ArrayList<String>>();

	/**
	 * Set default messages.
	 * @param message_id Message id
	 * @param value ArrayList with default message of the message-id
	 */
	public static void setDefaultMessage(String message_id, ArrayList<String> value) {
		messages.put(message_id, value);
	}

	/* End of global zone */
	
	/* Start of private zone */
	
	private ConcurrentHashMap<String, String> variables = new ConcurrentHashMap<String, String>();
	private String message_id = null;
	
	/**
	 * Create MessageBuilder
	 * @param message_id Message id
	 */
	public MessageBuilder(String message_id) {
		this.message_id = message_id;
	}

	/**
	 * Get message-id
	 * @return Message id
	 */
	public String getMessageId() {
		return message_id;
	}

	/**
	 * Register message variable and his value
	 * @param variable_id Variable name
	 * @param value Value for the variable
	 */
	public void registerVariable(String variable_id, String value) {
		variables.put(variable_id, value);
	}

	/**
	 * Get variable value
	 * @param variable_id Variable name
	 * @return Variable value
	 */
	public String getVariable(String variable_id) {
		return variables.containsKey(variable_id) ? variables.get(variable_id) : "";
	}

	/**
	 * Get result message with all variables parsed
	 * @return Message ArrayList
	 */
	public ArrayList<String> getMessageResult() {
		ArrayList<String> message = new ArrayList<>();
		
		/* Stop when message id isn't exist's */
		if(messages.containsKey(message_id)) {
			System.out.println("Error obtaining message '"+message_id+"'");
			return message;
		}
		
		/* Apply variables */
		for(String line : messages.get(message_id)) {
			for (Entry<String, String> ent : variables.entrySet()) {
				line = line.replaceAll("%" + ent.getKey() + "%", ent.getValue());
			}
			message.add(line);
		}
		
		return message;
	}
	
	/* End of private zone */
	
}
