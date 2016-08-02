package kengxxiao.attributeseditor.api;

public enum AttrSlot {
	MAINHAND("mainhand",1),
	OFFHAND("offhand",2),
	HEAD("head",3),
	CHEST("chest",4),
	LEGS("legs",5),
	FEET("feet",6);
	
	private String slotString;
	private int slot;
	AttrSlot(String slotString,int slot)
	{
		this.slotString = slotString;
		this.slot = slot;
	}
	
	public String getSlotString()
	{
		return slotString;
	}
	
	public int getSlot()
	{
		return slot;
	}
	
}
