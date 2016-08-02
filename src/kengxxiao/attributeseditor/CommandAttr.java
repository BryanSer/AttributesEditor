package kengxxiao.attributeseditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONObject;

import kengxxiao.attributeseditor.api.AttributesEditorAPI;

public class CommandAttr implements CommandExecutor {
	public static int version;
	public static List<Integer> allowedType, allowedSlot = Arrays.asList(1, 2, 3, 4, 5, 6);
	public static List<Integer> allowedEnchantment, allowedFlags = Arrays.asList(1, 2, 3, 4, 5, 6);
	public static List<Integer> allowedPotionEffects, allowedFireworkType = Arrays.asList(0, 1, 2, 3, 4);
	public static List<Integer> allowedGeneration = Arrays.asList(0,1,2,3);

	public CommandAttr(String ver) {
		if (!ver.substring(2, 4).contains("-") && !ver.substring(2, 4).contains("\\.")) {
			version = 10;
			allowedType = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
			allowedEnchantment = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 16, 17, 18, 19, 20, 21, 32, 33, 34, 35, 48, 49,
					50, 51, 61, 62, 8, 9, 70);
			allowedPotionEffects = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
					21, 22, 23, 24, 25, 26, 27);
		} else {
			version = Integer.valueOf(ver.substring(2, 3));
			if (version < 7)
				allowedEnchantment = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 16, 17, 18, 19, 20, 21, 32, 33, 34, 35, 48,
						49, 50, 51);
			if (version == 7)
				allowedEnchantment = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 16, 17, 18, 19, 20, 21, 32, 33, 34, 35, 48,
						49, 50, 51, 61, 62);
			if (version == 8)
				allowedEnchantment = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 16, 17, 18, 19, 20, 21, 32, 33, 34, 35, 48,
						49, 50, 51, 61, 62, 8);
			if (version < 9)
				allowedPotionEffects = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
						20, 21, 22, 23);
			if (version == 9) {
				String cache = ver.substring(4, 5);
				allowedPotionEffects = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
						20, 21, 22, 23, 24, 25, 26, 27);
				allowedEnchantment = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 16, 17, 18, 19, 20, 21, 32, 33, 34, 35, 48,
						49, 50, 51, 61, 62, 8, 9, 70);
				if (cache.contains("R"))
					allowedType = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
				else
					allowedType = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
			} else if (version <= 8)
				allowedType = Arrays.asList(1, 2, 3, 4, 5);
		}
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (arg0 instanceof Player) {
			Player player = (Player) arg0;
			if (!player.hasPermission("ae.use")) {
				player.sendMessage(ChatColor.RED + "您没有使用这个命令的权限");
				return true;
			}
			ItemStack is = AttributesEditorAPI.getAvailableItemInHand(player);
			if (is == null) {
				player.sendMessage(ChatColor.RED + "手中的物品无效，无法编辑。");
				return true;
			}
			if (arg3.length == 1) {
				if (!arg3[0].equalsIgnoreCase("remove"))
					return false;
				try {
					is = AttributesEditorAPI.removeAttribute(is);
					if (is != null) {
						AttributesEditorAPI.setItemInHand(player, is);
						player.sendMessage(ChatColor.GREEN + "蓝字属性移除成功。");
						return true;
					} else {
						player.sendMessage(ChatColor.RED + "物品没有蓝字属性，无需移除。");
						return true;
					}
				} catch (Exception e) {
					AttributesEditorAPI.printStackTrace(player, e);
					return true;
				}
			}
			if (arg3.length == 2) {
				if (!arg3[0].equalsIgnoreCase("display") && !arg3[0].equalsIgnoreCase("hide")
						&& !arg3[0].equalsIgnoreCase("potion") && !arg3[0].equalsIgnoreCase("firework")
						&& !arg3[0].equalsIgnoreCase("enchbook") && !arg3[0].equalsIgnoreCase("book")) {
					return false;
				}
				if (arg3[0].equalsIgnoreCase("enchbook")) {
					if (!arg3[1].equalsIgnoreCase("remove"))
						return false;
					try {
						is = AttributesEditorAPI.removeStoredEnchantment(is);
						if (is != null) {
							AttributesEditorAPI.setItemInHand(player, is);
							player.sendMessage(ChatColor.GREEN + "附魔书属性移除成功。");
							return true;
						} else {
							player.sendMessage(ChatColor.RED + "物品没有附魔书属性，无需移除。");
							return true;
						}
					} catch (Exception e) {
						AttributesEditorAPI.printStackTrace(player, e);
						return true;
					}
				}
				if (arg3[0].equalsIgnoreCase("firework")) {
					if (!arg3[1].equalsIgnoreCase("remove"))
						return false;
					try {
						is = AttributesEditorAPI.removeFireworks(is);
						if (is != null) {
							AttributesEditorAPI.setItemInHand(player, is);
							player.sendMessage(ChatColor.GREEN + "烟花火箭属性移除成功。");
							return true;
						} else {
							player.sendMessage(ChatColor.RED + "物品没有烟花火箭属性，无需移除。");
							return true;
						}
					} catch (Exception e) {
						AttributesEditorAPI.printStackTrace(player, e);
						return true;
					}
				}
				if (arg3[0].equalsIgnoreCase("potion")) {
					if (!arg3[1].equalsIgnoreCase("remove"))
						return false;
					try {
						is = AttributesEditorAPI.removePotionEffects(is);
						if (is != null) {
							AttributesEditorAPI.setItemInHand(player, is);
							player.sendMessage(ChatColor.GREEN + "药水效果移除成功。");
							return true;
						} else {
							player.sendMessage(ChatColor.RED + "物品没有药水效果，无需移除。");
							return true;
						}
					} catch (Exception e) {
						AttributesEditorAPI.printStackTrace(player, e);
						return true;
					}
				}
				if (arg3[0].equalsIgnoreCase("hide")) {
					int hideType = -1;
					try {
						hideType = Integer.valueOf(arg3[1]);
					} catch (Exception e) {
						sendInvavidMessage(player);
						return true;
					}
					if (version >= 8) {
						if (!AttributesEditorAPI.isAllowedHideType(hideType)) {
							player.sendMessage(ChatColor.RED + "这个属性类型无效。");
							return true;
						}
						ItemFlag sHide = AttributesEditorAPI.decodeHideType(hideType);
						ItemMeta im = is.getItemMeta();
						if (im.hasItemFlag(sHide)) {
							im.removeItemFlags(sHide);
							is.setItemMeta(im);
							AttributesEditorAPI.setItemInHand(player, is);
							player.sendMessage(ChatColor.GREEN + "成功显示了指定属性。");
							return true;
						} else {
							im.addItemFlags(sHide);
							is.setItemMeta(im);
							AttributesEditorAPI.setItemInHand(player, is);
							player.sendMessage(ChatColor.GREEN + "成功隐藏了指定属性。");
							return true;
						}
					} else {
						player.sendMessage(ChatColor.RED + "这个操作要求游戏版本高于1.8，您的版本不足以达到这个要求。");
						return true;
					}
				}
				if (arg3[0].equalsIgnoreCase("display")) {
					String newDisplay = ChatColor.translateAlternateColorCodes('&', arg3[1]);
					ItemMeta im = is.getItemMeta();
					im.setDisplayName(newDisplay);
					is.setItemMeta(im);
					AttributesEditorAPI.setItemInHand(player, is);
					player.sendMessage(ChatColor.GREEN + "成功修改物品名字为" + ChatColor.RESET + newDisplay);
					return true;
				}
				if(arg3[0].equalsIgnoreCase("book"))
				{
					try{
					BookMeta bm = (BookMeta)is.getItemMeta();
					bm.addPage(ChatColor.translateAlternateColorCodes('&', arg3[1]));
					is.setItemMeta(bm);
					AttributesEditorAPI.setItemInHand(player, is);
					player.sendMessage(ChatColor.GREEN+"成功添加了一页书。");
					return true;
					}
					catch(ClassCastException e)
					{
						player.sendMessage(ChatColor.RED + "您手中的物品不是书类物品。");
						return true;
					}
				}
			}
			if (arg3.length == 3) {
				if (!arg3[0].equalsIgnoreCase("lore") && !arg3[0].equalsIgnoreCase("enchant")
						&& !arg3[0].equalsIgnoreCase("firework") && !arg3[0].equalsIgnoreCase("enchbook")
						&& !arg3[0].equalsIgnoreCase("book")) {
					return false;
				}
				if (arg3[0].equalsIgnoreCase("enchbook")) {
					short enchid, level;
					try {
						int ench = Integer.valueOf(arg3[1]);
						if (!AttributesEditorAPI.isAllowedEnchantment(ench)) {
							player.sendMessage(ChatColor.RED + "在您的版本不支持这个附魔类型。");
							return true;
						}
						enchid = Short.valueOf(arg3[1]);
						level = Short.valueOf(arg3[2]);
						if (level < 0) {
							player.sendMessage(ChatColor.RED + "您想附魔负数等级？这是不安全的操作。");
							return true;
						}
					} catch (Exception e) {
						sendInvavidMessage(player);
						return true;
					}
					try {
						is = AttributesEditorAPI.addStoredEnchantment(is, enchid, level);
						AttributesEditorAPI.setItemInHand(player, is);
						player.sendMessage(ChatColor.GREEN + "附魔书属性添加成功。");
						return true;
					} catch (Exception e) {
						AttributesEditorAPI.printStackTrace(player, e);
						return true;
					}
				}
				if (arg3[0].equalsIgnoreCase("firework")) {
					if (!arg3[1].equalsIgnoreCase("flight"))
						return false;
					byte flight;
					try {
						flight = Byte.valueOf(arg3[2]);
					} catch (Exception e) {
						sendInvavidMessage(player);
						return true;
					}
					try {
						is = AttributesEditorAPI.changeFireworksFlightTime(is, flight);
						AttributesEditorAPI.setItemInHand(player, is);
						player.sendMessage(ChatColor.GREEN + "烟花飞行时间修改成功。");
						return true;
					} catch (Exception e) {
						AttributesEditorAPI.printStackTrace(player, e);
						return true;
					}
				}
				if (arg3[0].equalsIgnoreCase("enchant")) {
					int enchantType, level;
					try {
						enchantType = Integer.valueOf(arg3[1]);
						level = Integer.valueOf(arg3[2]);
						if (!AttributesEditorAPI.isAllowedEnchantment(enchantType)) {
							player.sendMessage(ChatColor.RED + "在您的版本不支持这个附魔类型。");
							return true;
						}
						if (level < 0) {
							player.sendMessage(ChatColor.RED + "您想附魔负数等级？这是不安全的操作。");
							return true;
						}
					} catch (Exception e) {
						sendInvavidMessage(player);
						return true;
					}
					if (level > 0)
						is.addUnsafeEnchantment(Enchantment.getById(enchantType), level);
					else if (level == 0) {
						if (is.containsEnchantment(Enchantment.getById(enchantType))) {
							is.removeEnchantment(Enchantment.getById(enchantType));
							AttributesEditorAPI.setItemInHand(player, is);
							player.sendMessage(ChatColor.GREEN + "成功移除了附魔。");
							return true;
						} else {
							player.sendMessage(ChatColor.RED + "您手中的道具不存在这个附魔。");
							return true;
						}
					}
					AttributesEditorAPI.setItemInHand(player, is);
					player.sendMessage(ChatColor.GREEN + "修改附魔成功。");
					return true;
				}
				if (arg3[0].equalsIgnoreCase("lore")) {
					int location = 0;
					try {
						location = Integer.valueOf(arg3[1]) - 1;
						if (location < 0) {
							player.sendMessage(ChatColor.RED + "Lore位置越界，无法修改。");
							return true;
						}
					} catch (Exception e) {
						sendInvavidMessage(player);
						return true;
					}
					ItemMeta im = is.getItemMeta();
					List<String> nowLore = im.hasLore() ? im.getLore() : new ArrayList<String>();
					int loreLength = nowLore.size();
					if (location > loreLength) {
						player.sendMessage(ChatColor.RED + "Lore位置越界，无法修改。");
						return true;
					}
					if (location == loreLength) {
						if (!arg3[2].equalsIgnoreCase("remove"))
							nowLore.add(ChatColor.translateAlternateColorCodes('&', arg3[2]));
						else {
							player.sendMessage(ChatColor.RED + "Lore位置越界，无法修改。");
							return true;
						}
					} else {
						if (!arg3[2].equalsIgnoreCase("remove"))
							nowLore.set(location, ChatColor.translateAlternateColorCodes('&', arg3[2]));
						else
							nowLore.remove(location);
					}
					im.setLore(nowLore);
					is.setItemMeta(im);
					AttributesEditorAPI.setItemInHand(player, is);
					player.sendMessage(ChatColor.GREEN + "修改Lore成功。");
					return true;
				}
				if(arg3[0].equalsIgnoreCase("book"))
				{
					int page = 0;
					try
					{
						BookMeta im = (BookMeta)is.getItemMeta();
						if(arg3[1].equalsIgnoreCase("author"))
						{
							im.setAuthor(ChatColor.translateAlternateColorCodes('&',arg3[2]));
							is.setItemMeta(im);
							AttributesEditorAPI.setItemInHand(player, is);
							player.sendMessage(ChatColor.GREEN + "修改作者成功。");
							return true;
						}
						if(arg3[1].equalsIgnoreCase("title"))
						{
							im.setTitle(ChatColor.translateAlternateColorCodes('&', arg3[2]));
							is.setItemMeta(im);
							AttributesEditorAPI.setItemInHand(player, is);
							player.sendMessage(ChatColor.GREEN + "修改标题成功。");
							return true;
						}
						if(arg3[1].equalsIgnoreCase("generation"))
						{
							int type = Integer.valueOf(arg3[2]);
							if(AttributesEditorAPI.isAllowedGeneration(type))
							{
							im.setGeneration(AttributesEditorAPI.decodeGeneration(type));
							is.setItemMeta(im);
							AttributesEditorAPI.setItemInHand(player, is);
							player.sendMessage(ChatColor.GREEN + "修改成书版本成功。");
							return true;
							}
							else
							{
								player.sendMessage(ChatColor.RED + "您的版本不存在这个成书版本数值。");
								return true;
							}
						}
						if(arg3[1].equalsIgnoreCase("remove"))
						{
							if(!arg3[2].equalsIgnoreCase("all"))
							{
								page = Integer.valueOf(arg3[2]);
								if(page > im.getPageCount() || page <= 0)
								{
									player.sendMessage(ChatColor.RED + "不存在的页数或不能移除。");
									return true;
								}
								List<String> pages = im.getPages();
								List<String> cache = new ArrayList<String>();
								for (Object c:pages)
								{
									String s = (String)c;
									cache.add(s);
								}
								cache.remove(page - 1);
								im.setPages(cache);
								is.setItemMeta(im);
								AttributesEditorAPI.setItemInHand(player, is);
								player.sendMessage(ChatColor.GREEN + "移除指定页数成功。");
							}
							else
							{
								im.setPages(Arrays.asList(""));
								is.setItemMeta(im);
								AttributesEditorAPI.setItemInHand(player, is);
								player.sendMessage(ChatColor.GREEN + "移除全部页数成功。");
							}
							return true;
						}
						else{
						page = Integer.valueOf(arg3[1]);
						if(page > im.getPageCount() || page <= 0)
						{
							player.sendMessage(ChatColor.RED + "不存在的页数。");
							return true;
						}
						im.setPage(page, ChatColor.translateAlternateColorCodes('&', arg3[2]));
						is.setItemMeta(im);
						AttributesEditorAPI.setItemInHand(player, is);
						player.sendMessage(ChatColor.GREEN + "修改指定页数成功。");
						return true;
						}
					}
					catch(ClassCastException e)
					{
						player.sendMessage(ChatColor.RED + "您手中的物品不是书类物品或参数无效。");
						return true;
					}
				}
			}
			if (arg3.length == 4) {
				if (!arg3[0].equalsIgnoreCase("add"))
					return false;
				int type, slot;
				double amount;
				try {
					type = Integer.valueOf(arg3[1]);
					slot = Integer.valueOf(arg3[2]);
					amount = Double.valueOf(arg3[3]).doubleValue();
					if (!AttributesEditorAPI.isAllowedAttrType(type) || !AttributesEditorAPI.isAllowedSlot(slot)) {
						player.sendMessage(ChatColor.RED + "在您的版本不支持这个蓝字属性类型或部位无效。");
						return true;
					}
				} catch (Exception e) {
					sendInvavidMessage(player);
					return true;
				}
				List<String> cache = AttributesEditorAPI.decodeTypeAndSlot(type, slot);
				try {
					is = AttributesEditorAPI.addAttribute(is, cache.get(0), cache.get(1), amount);
					AttributesEditorAPI.setItemInHand(player, is);
					player.sendMessage(ChatColor.GREEN + "蓝字属性添加成功。");
					return true;
				} catch (Exception e) {
					AttributesEditorAPI.printStackTrace(player, e);
					return true;
				}
			}
			if (arg3.length == 5) {
				if (!arg3[0].equalsIgnoreCase("potion"))
					return false;
				byte id, amplifier, showParticles;
				int duration;
				try {
					id = Byte.valueOf(arg3[1]);
					if (!AttributesEditorAPI.isAllowedPotionEffect(id)) {
						player.sendMessage(ChatColor.RED + "您的版本不支持这个药水效果。");
						return true;
					}
					amplifier = (byte) (Byte.valueOf(arg3[2]) - 1);
					duration = Integer.valueOf(arg3[3]) * 20;
					int c = Integer.valueOf(arg3[4]);
					if (c != 0 && c != 1)
						throw new Exception();
					showParticles = Byte.valueOf(arg3[4]);
				} catch (Exception e) {
					sendInvavidMessage(player);
					return true;
				}
				try {
					is = AttributesEditorAPI.addPotionEffect(is, id, amplifier, showParticles, duration);
					AttributesEditorAPI.setItemInHand(player, is);
					player.sendMessage(ChatColor.GREEN + "药水效果添加成功。");
					return true;
				} catch (Exception e) {
					AttributesEditorAPI.printStackTrace(player, e);
					return true;
				}
			}
			if (arg3.length == 10) {
				if (!arg3[0].equalsIgnoreCase("fwstar") && !arg3[0].equalsIgnoreCase("firework"))
					return false;
				if (arg3[0].equalsIgnoreCase("firework")) {
					byte flicker, trail, type;
					int colors, fadeColors;
					try {
						int c1 = Integer.valueOf(arg3[1]);
						int c2 = Integer.valueOf(arg3[2]);
						int c3 = Integer.valueOf(arg3[3]);
						if (c1 != 0 && c1 != 1)
							throw new Exception();
						if (c2 != 0 && c2 != 1)
							throw new Exception();
						if (!AttributesEditorAPI.isAllowedFireworkType(c3)) {
							player.sendMessage(ChatColor.RED + "您的版本不支持这个烟花爆炸形态。");
							return true;
						}
						flicker = Byte.valueOf(arg3[1]);
						trail = Byte.valueOf(arg3[2]);
						type = Byte.valueOf(arg3[3]);
						colors = AttributesEditorAPI.decodeColors(Integer.valueOf(arg3[4]), Integer.valueOf(arg3[5]),
								Integer.valueOf(arg3[6]));
						fadeColors = AttributesEditorAPI.decodeColors(Integer.valueOf(arg3[7]),
								Integer.valueOf(arg3[8]), Integer.valueOf(arg3[9]));
						if (colors >= 16777216 || fadeColors >= 16777216) {
							player.sendMessage(ChatColor.RED + "RGB颜色溢出。[三色值各应不大于255，且应不小于0]");
							return true;
						}
					} catch (Exception e) {
						sendInvavidMessage(player);
						return true;
					}

					try {
						is = AttributesEditorAPI.addFireworks(is, flicker, trail, type, colors, fadeColors);
						AttributesEditorAPI.setItemInHand(player, is);
						player.sendMessage(ChatColor.GREEN + "烟花属性添加成功。");
						return true;
					} catch (Exception e) {
						AttributesEditorAPI.printStackTrace(player, e);
						return true;
					}
				}
				byte flicker, trail, type;
				int colors, fadeColors;
				try {
					int c1 = Integer.valueOf(arg3[1]);
					int c2 = Integer.valueOf(arg3[2]);
					int c3 = Integer.valueOf(arg3[3]);
					if (c1 != 0 && c1 != 1)
						throw new Exception();
					if (c2 != 0 && c2 != 1)
						throw new Exception();
					if (!AttributesEditorAPI.isAllowedFireworkType(c3)) {
						player.sendMessage(ChatColor.RED + "您的版本不支持这个烟花爆炸形态。");
						return true;
					}
					flicker = Byte.valueOf(arg3[1]);
					trail = Byte.valueOf(arg3[2]);
					type = Byte.valueOf(arg3[3]);
					colors = AttributesEditorAPI.decodeColors(Integer.valueOf(arg3[4]), Integer.valueOf(arg3[5]),
							Integer.valueOf(arg3[6]));
					fadeColors = AttributesEditorAPI.decodeColors(Integer.valueOf(arg3[7]), Integer.valueOf(arg3[8]),
							Integer.valueOf(arg3[9]));
					if (colors >= 16777216 || fadeColors >= 16777216) {
						player.sendMessage(ChatColor.RED + "RGB颜色溢出。[三色值各应不大于255，且应不小于0]");
						return true;
					}
				} catch (Exception e) {
					sendInvavidMessage(player);
					return true;
				}
				try {
					is = AttributesEditorAPI.changeFireworkCharge(is, flicker, trail, type, colors, fadeColors);
					AttributesEditorAPI.setItemInHand(player, is);
					player.sendMessage(ChatColor.GREEN + "烟火之星修改成功。");
					return true;
				} catch (Exception e) {
					AttributesEditorAPI.printStackTrace(player, e);
					return true;
				}
			}

		} else {
			arg0.sendMessage("必须是玩家才能执行这个指令");
			return true;
		}
		return false;
	}

	private void sendInvavidMessage(Player player) {
		player.sendMessage(ChatColor.RED + "您输入的参数无效。");
	}

}
