package kengxxiao.attributeseditor;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AttributesEditor extends JavaPlugin {
	private Logger logger;
	private final String version = Bukkit.getBukkitVersion().substring(2, 6);

	public void onEnable() {
		logger = getLogger();
		getCommand("attr").setExecutor(new CommandAttr(version));
		logger.info("Attributes Editor 已成功加载");
	}

	public void onDisable() {
		logger.info("Attributes Editor 已成功卸载");
	}
}
