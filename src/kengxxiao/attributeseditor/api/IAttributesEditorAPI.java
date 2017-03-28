package kengxxiao.attributeseditor.api;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta.Generation;

public interface IAttributesEditorAPI {

	List<String> decodeTypeAndSlot(int type, int slot);

	ItemFlag decodeHideType(int hideType);

	Generation decodeGeneration(int generation);

	int decodeColors(int r, int g, int b);

	int getMajorVersion();

	ItemStack getAvailableItemInHand(Player player);

	void setItemInHand(Player player, ItemStack itemstack);

	void printStackTrace(Player p, Exception e);

	ItemStack changeFireworksFlightTime(ItemStack itemstack, byte flightTime) throws Exception;

	ItemStack changeFireworkCharge(ItemStack itemstack, byte flicker, byte trail, byte type, int colors, int fadeColors)
			throws Exception;

	ItemStack addAttribute(ItemStack itemstack, String type, String slot, double amount) throws Exception;

	ItemStack addStoredEnchantment(ItemStack itemstack, short enchType, short level) throws Exception;

	ItemStack addPotionEffect(ItemStack itemstack, byte potionType, byte amplifier, byte showParticles, int duration)
			throws Exception;

	ItemStack addFireworks(ItemStack itemstack, byte flicker, byte trail, byte type, int colors, int fadeColors)
			throws Exception;
	
	ItemStack removeAttribute(ItemStack itemstack) throws Exception;

	ItemStack removeStoredEnchantment(ItemStack itemstack) throws Exception;

	ItemStack removeFireworks(ItemStack itemstack) throws Exception;

	ItemStack removePotionEffects(ItemStack itemstack) throws Exception;

	boolean isAllowedEnchantment(int enchid);

	boolean isAllowedFireworkType(int fireworkType);

	boolean isAllowedHideType(int hideType);

	boolean isAllowedPotionEffect(int potionid);

	boolean isAllowedSlot(int slot);

	boolean isAllowedAttrType(int attrType);

	boolean isAllowedGeneration(int generation);

}