/**
 * 
 * N_Library by xXNurioXx [www.xxnurioxx.me]
 * Pack: [y18m10] Last Modify: 4/10/2018
 * Twitter: [https://twitter.com/xXNurioXx]
 * Mail: [personal@xxnurioxx.me]
 * 
 * @author xXNurioXx
 * @Target Bukkit
 */

package me.xxnurioxx.libs.y18m10.bukkit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class NLConfig {

	private Plugin pmain;
	private String fname;
	private File folder;
	private FileConfiguration config;
	private File configFile;

	/**
	 * YAML Configuration manager for Bukkit
	 * Auto load configuration when fired.
	 * @param filename
	 * @param instance
	 */
	public NLConfig(String filename, Plugin instance) {
		if (!filename.endsWith(".yml"))  filename += ".yml";
		fname = filename;
		pmain = instance;
		folder = pmain.getDataFolder();
		config = null;
		configFile = null;
		reload();
	}

	/**
	 * Get configuration manager.
	 * Auto reload when not null.
	 * @return
	 */
	public FileConfiguration getConfig() {
		if (config == null) reload();
		return config;
	}

	/**
	 * Reload config when fired.
	 */
	public void reload() {
		if (!folder.exists()) {
			try {
				if (!folder.mkdir()) System.err.println("Error creating plugin folder!");
			} catch (Exception e) {
				System.err.println("Error creating plugin folder!");
				System.err.println(e.getMessage());
			}
		}
		
		configFile = new File(folder, fname);
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				System.err.println("Error creating plugin config file!");
				System.err.println(e.getMessage());
			}
		}
		config = YamlConfiguration.loadConfiguration(configFile);
	}

	/**
	 * Copy defaults from resources if not exists.
	 */
	public void saveDefaultConfig() {
		if (configFile == null) configFile = new File(pmain.getDataFolder(), fname);
		if (!configFile.exists()) pmain.saveResource(fname, false);
	}

	/**
	 * Save configuration to file.
	 */
	public void save() {
		if (config == null || configFile == null) return;
		try {
			getConfig().save(configFile);
		} catch (IOException ex) {
			System.err.println("Could not save config to " + configFile.getName());
			System.err.println(ex.getMessage());
		}
	}

	/**
	 * Alias to getConfig().set()
	 * @param path
	 * @param o
	 */
	public void set(String path, Object o) {
		getConfig().set(path, o);
	}

	/**
	 * Save Location to YAML
	 * Include: World, x, y, z, yaw and pitch
	 * @param path
	 * @param l
	 */
	public void setLocation(String path, Location l) {
		getConfig().set(path + ".w", l.getWorld().getName());
		getConfig().set(path + ".x", l.getX());
		getConfig().set(path + ".y", l.getY());
		getConfig().set(path + ".z", l.getZ());
		getConfig().set(path + ".yaw", l.getYaw());
		getConfig().set(path + ".pitch", l.getPitch());
		save();
	}

	/**
	 * Obtain location from config.
	 * Include: World, x, y, z, yaw and pitch
	 * @param path
	 * @return
	 */
	public Location getLocation(String path) {
		World world = Bukkit.getWorld(getConfig().getString(path + ".w"));
		double x = getConfig().getDouble(path + ".x");
		double y = getConfig().getDouble(path + ".y");
		double z = getConfig().getDouble(path + ".z");
		float yaw = Float.parseFloat("" + getConfig().getDouble(path + ".yaw"));
		float pitch = Float.parseFloat("" + getConfig().getDouble(path + ".pitch"));
		return new Location(world, x, y, z, yaw, pitch);
	}

	/**
	 * Save ItemStack to YAML
	 * @param patch
	 * @param item
	 */
	public void setItem(String patch, ItemStack item) {
		if (item == null) {
			getConfig().set(patch + ".id", "AIR");
			save();
			return;
		}
		
		/* ItemStack area */
		
		String id = item.getType().name();
		int amount = item.getAmount();

		getConfig().set(patch + ".id", id);
		getConfig().set(patch + ".amount", amount);
		
		/* Simple meta area */

		boolean hasMeta = item.hasItemMeta();
		String name = hasMeta ? item.getItemMeta().getDisplayName() : "";
		List<String> lore = hasMeta ? item.getItemMeta().getLore() : new ArrayList<String>();
		int damage = ((Damageable) item.getItemMeta()).getDamage();

		getConfig().set(patch + ".hasMeta", hasMeta);
		getConfig().set(patch + ".name", name);
		getConfig().set(patch + ".lore", lore);
		getConfig().set(patch + ".damage", damage);


		/* Enchantment area  */
		
		boolean hasEnchants = !(item.getEnchantments().size() == 0);
		List<String> enchants = new ArrayList<String>();
		if (hasEnchants) {
			for (Enchantment enchname : item.getEnchantments().keySet()) {
				enchants.add(enchname.getKey() + "," + item.getEnchantmentLevel(enchname));
			}
		}
		
		List<String> storedenchants = new ArrayList<String>();
		EnchantmentStorageMeta meta = (item.getType() == Material.ENCHANTED_BOOK && hasMeta) ? (EnchantmentStorageMeta) item.getItemMeta() : null;
		boolean hasStoredEnchants = meta != null ? meta.hasStoredEnchants() : false;		
		if(hasStoredEnchants){
			for(Enchantment enchname : meta.getStoredEnchants().keySet()) {
				storedenchants.add(enchname.getKey()+","+meta.getStoredEnchantLevel(enchname));
			}
		}

		getConfig().set(patch+".hasEnchants", hasEnchants);
		getConfig().set(patch+".hasStoredEnchants", hasStoredEnchants);
		
		if(hasEnchants) getConfig().set(patch+".enchants", enchants);
		if(hasStoredEnchants) getConfig().set(patch+".storedenchants", storedenchants);
		
		/* Potion area  */
		
		boolean isPotion = (item.getType() == Material.POTION || item.getType() == Material.SPLASH_POTION ||  item.getType() == Material.LINGERING_POTION || item.getType() == Material.TIPPED_ARROW) ;
		PotionMeta pmeta = null;
		if(isPotion) {
			pmeta = (PotionMeta)item.getItemMeta();
			PotionData pa = pmeta.getBasePotionData();
			getConfig().set(patch+".isPotion", isPotion);
			getConfig().set(patch+".potion_type", pa.getType().name());
			getConfig().set(patch+".potion_upgraded", pa.isUpgraded());
			getConfig().set(patch+".potion_extended", pa.isExtended());
		}

		save();
	}
	
	/**
	 * Get ItemStack from YAML
	 * @param patch
	 * @return
	 */
	public ItemStack getItem(String patch) {
		if(!getConfig().isSet(patch+".id")) return new ItemStack(Material.AIR);
		
		/* ItemStack area */
		
		String id = getConfig().getString(patch + ".id");
		if (id == null || id.isEmpty()) return new ItemStack(Material.AIR);
		int amount = getConfig().getInt(patch + ".amount");

		ItemStack item = new ItemStack(Material.getMaterial(id));
		item.setAmount(amount);

		/* Simple meta area */

		boolean hasMeta = getConfig().getBoolean(patch + ".hasMeta");
		String name = hasMeta ? getConfig().getString(patch + ".name") : "";
		ArrayList<String> lore = hasMeta ? (ArrayList<String>) getConfig().getStringList(patch + ".lore") : new ArrayList<String>();
		int damage = getConfig().getInt(patch + ".damage");
		
		if(hasMeta) {	
			if(damage != 0) {
				Damageable ditem = (Damageable) item.getItemMeta();
				ditem.setDamage(damage);
				item.setItemMeta((ItemMeta) ditem);
			}
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(name);
			if(lore.size() > 0)im.setLore(lore);
			item.setItemMeta(im);
		}
		
		/* Enchantment area  */
		
		boolean hasEnchants = getConfig().getBoolean(patch + ".hasEnchants");
		HashMap<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
		if (hasEnchants) {
			for (String map : getConfig().getStringList(patch + ".enchants")) {
				String[] data = map.split(",");
				enchants.put(Enchantment.getByKey(NamespacedKey.minecraft(data[0])), Integer.parseInt(data[1]));
			}
		}
		
		boolean hasStoredEnchants = getConfig().getBoolean(patch+".hasStoredEnchants");
		HashMap<Enchantment, Integer> storedenchants = new HashMap<Enchantment, Integer>();
		if(hasStoredEnchants){
			for(String map : getConfig().getStringList(patch+".storedenchants")){
				String[] data = map.split(",");
				storedenchants.put(Enchantment.getByKey(NamespacedKey.minecraft(data[0])), Integer.parseInt(data[1]));
			}
		}
		if(item.getType() == Material.ENCHANTED_BOOK && hasStoredEnchants){
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
			for(Entry<Enchantment, Integer> ench : storedenchants.entrySet()) {
				meta.addStoredEnchant(ench.getKey(), ench.getValue(), true);
			}
			item.setItemMeta(meta);
		}
		
		if (hasEnchants) item.addUnsafeEnchantments(enchants);

		/* Potion area  */

		boolean isPotion = getConfig().getBoolean(patch+".isPotion");
		if(isPotion && (item.getType() == Material.POTION || item.getType() == Material.SPLASH_POTION ||  item.getType() == Material.LINGERING_POTION  || item.getType() == Material.TIPPED_ARROW)){
			
			String namepotion = getConfig().getString(patch+".potion_type");
			boolean extended = getConfig().getBoolean(patch+".potion_extended");
			boolean upgraded = getConfig().getBoolean(patch+".potion_upgraded");
			
			PotionMeta pmeta = (PotionMeta)item.getItemMeta();
			pmeta.setBasePotionData(new PotionData(PotionType.valueOf(namepotion), extended, upgraded));
			item.setItemMeta(pmeta);
		}
		
		return item;
	}

	/**
	 * Save full inventroy to YAML
	 * @param patch
	 * @param inv
	 */
	public void setInventory(String patch, Inventory inv) {
		for(int current=0;(inv.getSize()-1) >= current;current++) {
			ItemStack item;
			try { item = inv.getItem(current); } catch (Exception er) { item = null; }
			setItem(patch + ".slot" + current, item);
		}
	}

	/**
	 * Get full inventory from YAML
	 * @param patch
	 * @return
	 */
	public Inventory getInventory(String patch) {
		int slots = getConfig().getConfigurationSection(patch).getKeys(false).size()-1;
		Inventory inv = Bukkit.createInventory(null, slots+1);
		for(int current=0;(inv.getSize()-1) >= current;current++) {
			inv.setItem(current, getItem(patch + ".slot" + current));
		}
		return inv;
	}

}