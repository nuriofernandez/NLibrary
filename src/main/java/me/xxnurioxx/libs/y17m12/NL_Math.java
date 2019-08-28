/**
 * 
 * N_Library by xXNurioXx [www.xxnurioxx.me]
 * Pack: [y17m12] Last Modify: 29/12/2017
 * Twitter: [https://twitter.com/xXNurioXx]
 * Mail: [personal@xxnurioxx.me]
 * 
 * @author xXNurioXx
 * @Target All
 *
 */

package me.xxnurioxx.libs.y17m12;

import java.util.Random;

public class NL_Math {

	/**
	 * Random class
	 */
	public static Random rand = new Random();

	/**
	 * Round double value to X decimal places.
	 * @param value
	 * @param places
	 * @return
	 */
	public static double round(double value, int places) {
		if (places < 0) return (int) value;
		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	/**
	 * Generate random integer inside a range
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomInt(int min, int max) {
		return rand.nextInt(max - min) + min;
	}

	/**
	 * Super fast checking of integer content in a string.
	 * @param string
	 * @return
	 */
	public static boolean isInteger(String string) {
		if (string == null) return false;
		int length = string.length();
		if (length == 0) return false;
		int i = 0;
		if (string.charAt(0) == '-') {
			if (length == 1) return false;
			i = 1;
		}
		for (; i < length; i++) {
			char c = string.charAt(i);
			if (c <= '/' || c >= ':') return false;
		}
		return true;
	}
}
