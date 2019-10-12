# NurioLibs
A personal Java library. You can use this for your projects.

This library is mainly focused on the development of Bukkit. But contains non-depend classes.

# Complements

#### [Message Builder](https://github.com/xXNurioXx/NLibrary/blob/master/src/main/java/me/xxnurioxx/libs/messages/MessageBuilder.java "Message Builder") is a with variable message builder.
Usage example:
```java
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
```
Output:
```text
Welcome, Nurio to the server.
Now you have 0 online friends.
```
