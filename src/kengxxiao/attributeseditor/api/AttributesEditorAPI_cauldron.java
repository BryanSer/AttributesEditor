package kengxxiao.attributeseditor.api;

import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.NBTTagList;

public class AttributesEditorAPI_cauldron extends AttributesEditorAPI{
	
	@Override
	public ItemStack changeFireworksFlightTime(ItemStack itemstack,byte flightTime) throws Exception
	{
		net.minecraft.server.v1_7_R4.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemstack);
		NBTTagCompound tag = nmsItemStack.hasTag()? nmsItemStack.getTag(): new NBTTagCompound();
		NBTTagCompound fireworks = (NBTTagCompound) (tag.hasKey("Fireworks")? tag.get("Fireworks"): new NBTTagCompound());
		fireworks.setByte("Flight",flightTime);
		tag.set("Fireworks", fireworks);
		nmsItemStack.setTag(tag);
		return CraftItemStack.asBukkitCopy(nmsItemStack);
	}
	
	@Override
	public ItemStack changeFireworkCharge(ItemStack itemstack,byte flicker,byte trail,byte type,int colors,int fadeColors) throws Exception {
		net.minecraft.server.v1_7_R4.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemstack);
		NBTTagCompound tag = nmsItemStack.hasTag()? nmsItemStack.getTag(): new NBTTagCompound();
		NBTTagCompound expCompound = (NBTTagCompound) (tag.hasKey("Explosion")? tag.get("Explosion"): new NBTTagCompound());
		expCompound.setByte("Flicker", flicker);
		expCompound.setByte("Trail", trail);
		expCompound.setByte("Type", type);
		expCompound.setIntArray("Colors", new int[] { colors });
		expCompound.setIntArray("FadeColors", new int[] { fadeColors });
		tag.set("Explosion", expCompound);
		nmsItemStack.setTag(tag);
		return CraftItemStack.asBukkitCopy(nmsItemStack);		
	}
	
	@Override
	public ItemStack addAttribute(ItemStack itemstack,String type,String slot,double amount) throws Exception {
		net.minecraft.server.v1_7_R4.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemstack);
		NBTTagCompound tag = nmsItemStack.hasTag()? nmsItemStack.getTag(): new NBTTagCompound();
		NBTTagList taglist = (NBTTagList) (tag.hasKey("AttributeModifiers")? tag.get("AttributeModifiers"): new NBTTagList());
		NBTTagCompound newAttr = new NBTTagCompound();
		newAttr.setString("AttributeName",type);
		newAttr.setString("Name",type);
		newAttr.setString("Slot",slot);
		newAttr.setDouble("Amount",amount);
		newAttr.setInt("Operation",0);
		newAttr.setInt("UUIDLeast",894654);
		newAttr.setInt("UUIDMost",2872);
		taglist.add(newAttr);
		tag.set("AttributeModifiers",taglist);
		nmsItemStack.setTag(tag);
		return CraftItemStack.asBukkitCopy(nmsItemStack);		
	}
	
	@Override
	public ItemStack addStoredEnchantment(ItemStack itemstack,short enchType,short level) throws Exception {
		net.minecraft.server.v1_7_R4.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemstack);
		NBTTagCompound tag = nmsItemStack.hasTag()? nmsItemStack.getTag(): new NBTTagCompound();
		NBTTagList taglist = (NBTTagList) (tag.hasKey("StoredEnchantments")? tag.get("StoredEnchantments"): new NBTTagList());
		NBTTagCompound newStoredEnchantment = new NBTTagCompound();
		newStoredEnchantment.setShort("id",enchType);
		newStoredEnchantment.setShort("lvl", level);
		taglist.add(newStoredEnchantment);
		tag.set("StoredEnchantments", taglist);
		nmsItemStack.setTag(tag);
		return CraftItemStack.asBukkitCopy(nmsItemStack);
	}
	
	@Override
	public ItemStack addPotionEffect(ItemStack itemstack,byte potionType,byte amplifier,byte showParticles,int duration) throws Exception {
		net.minecraft.server.v1_7_R4.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemstack);
		NBTTagCompound tag = nmsItemStack.hasTag()? nmsItemStack.getTag(): new NBTTagCompound();
		NBTTagList taglist = (NBTTagList) (tag.hasKey("CustomPotionEffects")? tag.get("CustomPotionEffects"): new NBTTagList());
		NBTTagCompound newPotionEffect = new NBTTagCompound();
		newPotionEffect.setByte("Id", potionType);
		newPotionEffect.setByte("Amplifier", amplifier);
		newPotionEffect.setByte("ShowParticles", showParticles);
		newPotionEffect.setInt("Duration", duration);
		taglist.add(newPotionEffect);
		tag.set("CustomPotionEffects", taglist);
		nmsItemStack.setTag(tag);
		return CraftItemStack.asBukkitCopy(nmsItemStack);
	}
	
	@Override
	public ItemStack addFireworks(ItemStack itemstack,byte flicker,byte trail,byte type,int colors,int fadeColors) throws Exception {
		net.minecraft.server.v1_7_R4.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemstack);
		NBTTagCompound tag = nmsItemStack.hasTag()? nmsItemStack.getTag(): new NBTTagCompound();
		NBTTagCompound fireworks = (NBTTagCompound) (tag.hasKey("Fireworks")? tag.get("Fireworks"): new NBTTagCompound());
		NBTTagList explosionsList = (NBTTagList) (fireworks.hasKey("Explotions")? fireworks.get("Explotions"): new NBTTagList());
		NBTTagCompound expCompound = new NBTTagCompound();
		expCompound.setByte("Flicker", flicker);
		expCompound.setByte("Trail", trail);
		expCompound.setByte("Type", type);
		expCompound.setIntArray("Colors", new int[] { colors });
		expCompound.setIntArray("FadeColors", new int[] { fadeColors });
		explosionsList.add(expCompound);
		fireworks.set("Explosions", explosionsList);
		tag.set("Fireworks", fireworks);
		nmsItemStack.setTag(tag);
		return CraftItemStack.asBukkitCopy(nmsItemStack);
	}
	
	@Override
	public ItemStack removeAttribute(ItemStack itemstack) throws Exception {
		net.minecraft.server.v1_7_R4.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemstack);
		boolean hastag = nmsItemStack.hasTag();
		if (!hastag) 
			return null;
		NBTTagCompound tag = nmsItemStack.getTag();
		boolean taglist = tag.hasKey("AttributeModifiers");
		if (!taglist) 
			return null;
		tag.remove("AttributeModifiers");
		nmsItemStack.setTag(tag);
		return CraftItemStack.asBukkitCopy(nmsItemStack);
	}
	
	@Override
	public ItemStack removeStoredEnchantment(ItemStack itemstack) throws Exception {
		net.minecraft.server.v1_7_R4.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemstack);
		boolean hastag = nmsItemStack.hasTag();
		if (!hastag) 
			return null;
		NBTTagCompound tag = nmsItemStack.getTag();
		boolean taglist = tag.hasKey("StoredEnchantments");
		if (!taglist) 
			return null;
		tag.remove("StoredEnchantments");
		nmsItemStack.setTag(tag);
		return CraftItemStack.asBukkitCopy(nmsItemStack);
	}
	
	@Override
	public ItemStack removeFireworks(ItemStack itemstack) throws Exception {
		net.minecraft.server.v1_7_R4.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemstack);
		boolean hastag = nmsItemStack.hasTag();
		if (!hastag) 
			return null;
		NBTTagCompound tag = nmsItemStack.getTag();
		boolean firework = tag.hasKey("Fireworks");
		if (!firework) 
			return null;
		NBTTagCompound fireworks = (NBTTagCompound) tag.get("Fireworks");
		boolean explosions = fireworks.hasKey("Explosions");
		if (!explosions) 
			return null;
		fireworks.remove("Explosions");
		tag.set("Fireworks",fireworks);
		nmsItemStack.setTag(tag);
		return CraftItemStack.asBukkitCopy(nmsItemStack);
	}
	
	@Override
	public ItemStack removePotionEffects(ItemStack itemstack) throws Exception {
		net.minecraft.server.v1_7_R4.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemstack);
		boolean hastag = nmsItemStack.hasTag();
		if (!hastag) 
			return null;
		NBTTagCompound tag = nmsItemStack.getTag();
		boolean taglist = tag.hasKey("CustomPotionEffects");
		if (!taglist) 
			return null;
		tag.remove("CustomPotionEffects");
		nmsItemStack.setTag(tag);
		return CraftItemStack.asBukkitCopy(nmsItemStack);
	}

}
