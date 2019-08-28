/**
 * 
 * N_Library by xXNurioXx [www.xxnurioxx.me]
 * Pack: [y16] Last Modify: 17/01/2016
 * Twitter: [https://twitter.com/xXNurioXx]
 * Mail: [personal@xxnurioxx.me]
 * 
 * @author xXNurioXx
 * @Target All
 * 
 */

package me.xxnurioxx.libs.y16;

import java.util.ArrayList;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Deprecated
public class MItem {

	public static ItemStack createItem(int id, short sid){
		return new ItemStack(id, 1, sid);
	}
	
	public static ItemStack createItem(int id, short sid, String name){
		ItemStack it = createItem(id, sid);
		ItemMeta im = it.getItemMeta();
		im.setDisplayName(name);
		it.setItemMeta(im);
		return it;
	}
	
	public static ItemStack createItem(int id, short sid, String name, ArrayList<String> lore){
		ItemStack it = createItem(id, sid, name);
		ItemMeta im = it.getItemMeta();
		im.setLore(lore);
		it.setItemMeta(im);
		return it;
	}
	
	public static ItemStack enchantItem(ItemStack item, Enchantment enchant, int lvl){
		try{
			item.addUnsafeEnchantment(enchant, lvl);
		}catch(Exception er){
			item.addEnchantment(enchant, lvl);
		}
		return item;
	}
	
}
