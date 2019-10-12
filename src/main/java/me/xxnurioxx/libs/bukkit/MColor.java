/**
 * 
 * N_Library by xXNurioXx [www.xxnurioxx.me]
 * Pack: [y16] Last Modify: 22/07/2017
 * Twitter: [https://twitter.com/xXNurioXx]
 * Mail: [personal@xxnurioxx.me]
 * 
 * @author xXNurioXx
 * @Target All
 *
 */

package me.xxnurioxx.libs.bukkit;

import java.util.ArrayList;

import org.bukkit.ChatColor;

public class MColor {
	
	public static String col(String msg){
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public static String uncol(String msg){
		return ChatColor.stripColor(msg);
	}
	
	public static ArrayList<String> colorizeArray(ArrayList<String> array){
		ArrayList<String> newarray = new ArrayList<String>();
		for(String line : array) {
			newarray.add(col(line));
		}
		return newarray;
	}
	
}
