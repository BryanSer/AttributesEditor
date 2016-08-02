package kengxxiao.attributeseditor.api;

public enum AttrType {
	MAX_HEALTH("generic.maxHealth",1),
	FOLLOW_RANGE("generic.followRange",2),
	KNOCKBACK_RESISTANCE("generic.knockbackResistance",3),
	MOVEMENT_SPEED("generic.movementSpeed",4),
	ATTACK_DAMAGE("generic.attackDamage",5),
	ARMOR("generic.armor",6),
	ATTACK_SPEED("generic.attackSpeed",7),
	LUCK("generic.luck",8),
	ARMOR_TOUGHNESS("generic.armorToughness",9);
	
	private String typeString;
	private int type;
	AttrType(String typeString,int type)
	{
		this.typeString = typeString;
		this.type = type;
	}
	
	public String getTypeString()
	{
		return typeString;
	}
	
	public int getType()
	{
		return type;
	}
}
