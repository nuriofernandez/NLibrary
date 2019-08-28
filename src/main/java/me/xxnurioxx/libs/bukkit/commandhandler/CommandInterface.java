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

package me.xxnurioxx.libs.bukkit.commandhandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface CommandInterface {

	/**
	 * Bukkit onCommand
	 * @param sender
	 * @param cmd
	 * @param commandLabel
	 * @param args
	 * @return 
	 */
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args);
    
    /**
     * If sub command allows Console command sender.
     * @return true when allows.
     */
    public boolean allowConsole();

}
