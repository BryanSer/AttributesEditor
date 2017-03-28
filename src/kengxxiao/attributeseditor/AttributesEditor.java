package kengxxiao.attributeseditor;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import kengxxiao.attributeseditor.api.AttributesEditorAPI;
import kengxxiao.attributeseditor.api.AttributesEditorAPI_cauldron;
import kengxxiao.attributeseditor.api.IAttributesEditorAPI;

public class AttributesEditor extends JavaPlugin {
	private Logger logger;
	private final String version = Bukkit.getBukkitVersion().substring(2, 6);
	private IAttributesEditorAPI api;

	public void onEnable() {
		logger = getLogger();
		if ( Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].equalsIgnoreCase("v1_7_R4") ) {
			api = new AttributesEditorAPI_cauldron();
		} else {
			api = new AttributesEditorAPI();
		}
		getCommand("attr").setExecutor(new CommandAttr(this));
		logger.info("Attributes Editor 已成功加载");
	}

	public void onDisable() {
		logger.info("Attributes Editor 已成功卸载");
	}
	
	public IAttributesEditorAPI getApi(){
		return api;
	}

	public String getVersion() {
		return version;
	}
}
