/**
 * 
 * N_Library by xXNurioXx [www.xxnurioxx.me]
 * Pack: [y18m10] Last Modify: 4/10/2018
 * Twitter: [https://twitter.com/xXNurioXx]
 * Mail: [personal@xxnurioxx.me]
 * 
 * @author xXNurioXx
 * @Target All
 * 
 */
package me.xxnurioxx.libs.bukkit;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class MItem {
	
	public static ItemStack createItem(Material material){
		return new ItemStack(material);
	}
	
	public static ItemStack createItem(Material material, int amount){
		ItemStack item = createItem(material);
		item.setAmount(amount);
		return item;
	}
	
	public static ItemStack createItem(Material material, String name){
		ItemStack it = createItem(material);
		ItemMeta im = it.getItemMeta();
		im.setDisplayName(name);
		it.setItemMeta(im);
		return it;
	}
	
	public static ItemStack createItem(Material material, int amount, String name){
		ItemStack it = createItem(material, amount);
		ItemMeta im = it.getItemMeta();
		im.setDisplayName(name);
		it.setItemMeta(im);
		return it;
	}
	
	public static ItemStack createItem(Material material, String name, ArrayList<String> lore){
		ItemStack it = createItem(material, name);
		ItemMeta im = it.getItemMeta();
		im.setLore(lore);
		it.setItemMeta(im);
		return it;
	}
	
	public static ItemStack createItem(Material material, int amount, String name, ArrayList<String> lore){
		ItemStack it = createItem(material, amount, name);
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
