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

package me.xxnurioxx.libs.y17m12.bukkit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

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
		if (!filename.endsWith(".yml")) {
			filename += ".yml";
		}
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
		if (config == null)  reload();
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
		Location loc = new Location(world, x, y, z, yaw, pitch);
		return loc;
	}

	/**
	 * Save ItemStack to YAML
	 * Not working with 1.12 new metadata.
	 * @param patch
	 * @param item
	 */
	@SuppressWarnings("deprecation")
	public void setItem(String patch, ItemStack item) {
		if (item == null) {
			getConfig().set(patch + ".id", 0);
			save();
			return;
		}
		boolean hasMeta = item.hasItemMeta();
		String name = "";
		int id = item.getTypeId();
		List<String> lore = new ArrayList<String>();
		if (hasMeta) {
			name = item.getItemMeta().getDisplayName();
			lore = item.getItemMeta().getLore();
		}
		boolean hasEnchants = !(item.getEnchantments().size() == 0);
		List<String> enchants = new ArrayList<String>();
		if (hasEnchants) {
			for (Enchantment enchname : item.getEnchantments().keySet()) {
				enchants.add(enchname.getName() + "," + item.getEnchantmentLevel(enchname));
			}
		}

		int amount = item.getAmount();
		short sid = item.getDurability();

		getConfig().set(patch + ".id", id);
		getConfig().set(patch + ".hasmeta", hasMeta);
		getConfig().set(patch + ".name", name);
		getConfig().set(patch + ".lore", lore);
		getConfig().set(patch + ".hasEnchants", hasEnchants);
		if (hasEnchants) getConfig().set(patch + ".enchants", enchants);
		getConfig().set(patch + ".amount", amount);
		getConfig().set(patch + ".sid", (int) sid);
		save();
	}
	
	/**
	 * Get ItemStack from YAML
	 * Not working with 1.12 new metadata.
	 * @param patch
	 * @return
	 */
	public ItemStack getItem(String patch) {
		int id = getConfig().getInt(patch + ".id");
		if (id == 0) return new ItemStack(Material.AIR);
		boolean hasMeta = getConfig().getBoolean(patch + ".hasmeta");
		String name = "";
		ArrayList<String> lore = new ArrayList<String>();
		if (hasMeta) {
			name = getConfig().getString(patch + ".name");
			lore = (ArrayList<String>) getConfig().getStringList(patch + ".lore");
		}
		boolean hasEnchants = getConfig().getBoolean(patch + ".hasEnchants");
		HashMap<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
		if (hasEnchants) {
			for (String map : getConfig().getStringList(patch + ".enchants")) {
				String[] data = map.split(",");
				enchants.put(Enchantment.getByName(data[0]), Integer.parseInt(data[1]));
			}
		}
		int amount = getConfig().getInt(patch + ".amount");
		short sid = (short) getConfig().getInt(patch + ".sid");
		ItemStack item = MItem.createItem(id, sid);
		if (hasMeta) item = MItem.createItem(id, sid, name, lore);
		if (hasEnchants) item.addUnsafeEnchantments(enchants);
		item.setAmount(amount);
		return item;
	}

	/**
	 * Save full inventroy to YAML
	 * Not working with 1.12 new metadata.
	 * @param patch
	 * @param inv
	 */
	public void setInventory(String patch, Inventory inv) {
		int slots = inv.getSize();
		slots = slots - 1;
		int current = 0;
		while (slots >= current) {
			ItemStack item;
			try {
				item = inv.getItem(current);
			} catch (Exception er) {
				item = null;
			}
			setItem(patch + ".slot" + current, item);
			current++;
		}
	}

	/**
	 * Get full inventory from YAML
	 * Not working with 1.12 new metadata.
	 * @param patch
	 * @return
	 */
	public Inventory getInventory(String patch) {
		int slots = getConfig().getConfigurationSection(patch).getKeys(false).size();
		Inventory inv = Bukkit.createInventory(null, slots);
		slots = slots - 1;
		int current = 0;
		while (slots >= current) {
			inv.setItem(current, getItem(patch + ".slot" + current));
			current++;
		}
		return inv;
	}

}