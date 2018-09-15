/**
 * 
 * N_Library by xXNurioXx [www.xxnurioxx.me]
 * Pack: [y17m12] Last Modify: 29/12/2017
 * Twitter: [https://twitter.com/xXNurioXx]
 * Mail: [personal@xxnurioxx.me]
 * 
 * @author xXNurioXx
 * @Target Bukkit
 */

package me.xxnurioxx.libs.y18m1.bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.xxnurioxx.libs.y16.MColor;
import me.xxnurioxx.libs.y18m1.MessageBuilder;

import java.util.HashMap;

public class CommandHandler implements CommandExecutor {

	/* Start of constructor's area */

	private String unknown_command_message_id;
	private String console_command_not_allowed;

	/**
	 * Create Command Handler
	 * @param unknown_command_message_id message_id of the MessageBuilder to send when unknown command.
	 * @param console_command_not_allowed message_id of the MessageBuilder to send when try to use console on disallowed command.
	 */
	public CommandHandler(String unknown_command_message_id, String console_command_not_allowed) {
		this.unknown_command_message_id = unknown_command_message_id;
		this.console_command_not_allowed = console_command_not_allowed;
	}

	/* End of constructor's area */

	/* Start of executor's area */

	private HashMap<String, CommandInterface> commands = new HashMap<String, CommandInterface>();

	/**
	 * Register command
	 * 
	 * @param name Sub-command name
	 * @param cmd CommandInterface to be executed.
	 */
	public void registerCommand(String name, CommandInterface cmd) {
		commands.put(name, cmd);
	}

	/**
	 * Set default command executor
	 * 
	 * @param cmd CommandInterface to be executed.
	 */
	public void setDefaultCommand(CommandInterface cmd) {
		commands.put(" _default", cmd);
	}

	/**
	 * Check if command is registered
	 * 
	 * @param name Sub-command name
	 * @return
	 */
	public boolean existsCommand(String name) {
		return commands.containsKey(name);
	}

	/**
	 * Get executor from command name
	 * 
	 * @param name Sub-command name
	 * @return CommandInterface to be runned.
	 */
	public CommandInterface getExecutor(String name) {
		return commands.get(name);
	}

	/* End of executor's area */

	/* Start of onCommand area */

	/**
	 *  Bukkit onCommand
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		/* Register executor */
		CommandInterface executor = null;

		if (args.length == 0) {
			executor = getExecutor(" _default");
		}else {
			if(!existsCommand(args[0])) {
				MessageBuilder msgbuilder = new MessageBuilder(unknown_command_message_id);
				msgbuilder.registerVariable("argument", args[0]);
				msgbuilder.getMessageResult().forEach(line -> sender.sendMessage(MColor.col(line)));
				return true;
			}
			executor = getExecutor(args[0]);
		}

		/* Check if command allow console sender */
		if(!executor.allowConsole() && !(sender instanceof Player)) {
			MessageBuilder msgbuilder = new MessageBuilder(console_command_not_allowed);
			msgbuilder.registerVariable("argument", args[0]);
			msgbuilder.getMessageResult().forEach(line -> sender.sendMessage(MColor.col(line)));
			return true;
		}
		
		/* Run command */
		return executor.onCommand(sender, cmd, commandLabel, args);
		
	}

	/* End of onCommand area */

}