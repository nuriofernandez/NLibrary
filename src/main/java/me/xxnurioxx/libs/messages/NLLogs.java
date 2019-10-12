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

package me.xxnurioxx.libs.messages;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.bukkit.plugin.Plugin;

public class NLLogs {

	private static String log_prefix = "";

	/**
	 * Select Log prefix
	 * @param prefix
	 */
	public static void setPrefix(String prefix) {
		log_prefix = "[" + prefix + "] ";
	}

	/**
	 * Print console message
	 * @param line
	 */
	public static void info(String line) {
		System.out.println(log_prefix + line);
	}

	/**
	 * Save crash throwable to file inside.
	 * Save path: plugins/plugin-name/Crashes
	 * @param plugin
	 * @param note
	 * @param trow
	 */
	public static void reportCrash(Plugin plugin, String note, Throwable trow) {
		info("[Crash] "+note+" "+trow.getMessage());
		try {
			
			String classname = trow.getStackTrace()[0].getClassName();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
			String formatted = format.format(cal.getTime());
			String filename = "plugin-crash-" + classname + "-" + formatted;
			File file = new File(plugin.getDataFolder() + File.separator + "Crashes" + File.separator + filename + ".log");
			if (!file.exists()) file.createNewFile();

			String lines = "===============================================\n";
			lines += note + "\n";
			lines += "===============================================\n";
			lines += trow.getMessage() + "\n";
			lines += "===============================================\n";
			lines += ExceptionUtils.getStackTrace(trow) + "\n";
			lines += "===============================================\n\n\n\n\n\n\n";

			Writer output = new BufferedWriter(new FileWriter(file.getAbsolutePath())); // clears file every time
			output.append(lines);
			output.close();
			
		} catch (Exception e) {
			info("Error saving crash to file.");
			e.printStackTrace();
		}

	}
	
}
