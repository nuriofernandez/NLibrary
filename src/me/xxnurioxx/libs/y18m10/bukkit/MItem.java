/**
 * 
 * N_Library by xXNurioXx [www.xxnurioxx.me]
 * Pack: [y18m10] Last Modify: 1/10/2018
 * Twitter: [https://twitter.com/xXNurioXx]
 * Mail: [personal@xxnurioxx.me]
 * 
 * @author xXNurioXx
 * @Target All
 * 
 */
package me.xxnurioxx.libs.y18m10.bukkit;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;


public class MItem {
	
	@Deprecated
	public static ItemStack createItem(int id, short sid){
		return new ItemStack(convertMaterial(sid, (byte) sid),1);
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
	
	/* 
	 * Minecraft 1.13 changed the id system of items. This function translate old id's to the new system. 
	 * This is just a compatibility function, it is not the use in new plugin.
	 */
	public static ConcurrentHashMap<Integer, ConcurrentHashMap<Byte, Material>> material_cache = new ConcurrentHashMap<Integer, ConcurrentHashMap<Byte, Material>>();
	@SuppressWarnings("deprecation")
	public static Material convertMaterial(int id, byte sid) {
		if(material_cache.containsKey(id) && material_cache.get(id).containsKey(sid)) return material_cache.get(id).get(sid);
	    for(Material i : EnumSet.allOf(Material.class)) {
	    	if(i.getId() != id) continue;
	    	Material mat = Bukkit.getUnsafe().fromLegacy(new MaterialData(i, sid));
	    	if(!material_cache.containsKey(id))material_cache.put(id, new ConcurrentHashMap<Byte, Material>());
	    	material_cache.get(id).put(sid, mat);
	    }
	    return material_cache.get(id).get(sid);
	}
}
