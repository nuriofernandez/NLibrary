package examples;

import java.util.ArrayList;

import me.xxnurioxx.libs.y18m1.MessageBuilder;

public class MessageBuilderExample {

	public static void main(String[] args) {
		
		// Register a new message.
		ArrayList<String> message = new ArrayList<String>();
		message.add("Welcome, %username% to the server.");
		message.add("Now you have %friend_count% online friends.");
		MessageBuilder.setDefaultMessage("welcome_message", message);
		
		// Build and print a message
		MessageBuilder mb = new MessageBuilder("welcome_message");
		mb.registerVariable("username", "Nurio");
		mb.registerVariable("friend_count", "0");
		for(String line : mb.getMessageResult()) {
			System.out.println(line);
		}
		
	}
}
