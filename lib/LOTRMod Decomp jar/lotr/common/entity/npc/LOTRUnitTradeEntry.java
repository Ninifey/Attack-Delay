// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.item.ItemStack;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import lotr.common.entity.LOTREntities;
import lotr.common.item.LOTRItemCoin;
import lotr.common.LOTRPlayerData;
import lotr.common.fac.LOTRFaction;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.item.Item;

public class LOTRUnitTradeEntry
{
    public Class entityClass;
    public Class mountClass;
    private Item mountArmor;
    private float mountArmorChance;
    private String name;
    private int initialCost;
    public float alignmentRequired;
    private PledgeType pledgeType;
    public LOTRHiredNPCInfo.Task task;
    private String extraInfo;
    
    public LOTRUnitTradeEntry(final Class c, final int cost, final float alignment) {
        this.pledgeType = PledgeType.NONE;
        this.task = LOTRHiredNPCInfo.Task.WARRIOR;
        this.extraInfo = null;
        this.entityClass = c;
        this.initialCost = cost;
        this.alignmentRequired = alignment;
        if (LOTRBannerBearer.class.isAssignableFrom(this.entityClass)) {
            this.setExtraInfo("Banner");
        }
    }
    
    public LOTRUnitTradeEntry(final Class c, final Class c1, final String s, final int cost, final float alignment) {
        this(c, cost, alignment);
        this.mountClass = c1;
        this.name = s;
    }
    
    public LOTRUnitTradeEntry setTask(final LOTRHiredNPCInfo.Task t) {
        this.task = t;
        return this;
    }
    
    public LOTRUnitTradeEntry setMountArmor(final Item item) {
        return this.setMountArmor(item, 1.0f);
    }
    
    public LOTRUnitTradeEntry setMountArmor(final Item item, final float chance) {
        this.mountArmor = item;
        this.mountArmorChance = chance;
        return this;
    }
    
    public LOTRUnitTradeEntry setPledgeExclusive() {
        return this.setPledgeType(PledgeType.FACTION);
    }
    
    public LOTRUnitTradeEntry setPledgeType(final PledgeType t) {
        this.pledgeType = t;
        return this;
    }
    
    public PledgeType getPledgeType() {
        return this.pledgeType;
    }
    
    public LOTRUnitTradeEntry setExtraInfo(final String s) {
        this.extraInfo = s;
        return this;
    }
    
    public boolean hasExtraInfo() {
        return this.extraInfo != null;
    }
    
    public String getFormattedExtraInfo() {
        return StatCollector.translateToLocal("lotr.unitinfo." + this.extraInfo);
    }
    
    public int getCost(final EntityPlayer entityplayer, final LOTRHireableBase trader) {
        float cost = (float)this.initialCost;
        final float maxDiscount = 0.5f;
        final float notPledgedExpense = 2.0f;
        final LOTRFaction fac = trader.getFaction();
        final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
        final float alignment = pd.getAlignment(fac);
        final boolean pledged = pd.isPledgedTo(fac);
        final float alignSurplus = Math.max(alignment - this.alignmentRequired, 0.0f);
        if (pledged) {
            float f = alignSurplus / 1500.0f;
            f = MathHelper.clamp_float(f, 0.0f, 1.0f);
            f *= 0.5f;
            cost *= 1.0f - f;
        }
        else {
            cost *= 2.0f;
            float f = alignSurplus / 2000.0f;
            f = MathHelper.clamp_float(f, 0.0f, 1.0f);
            f *= 0.5f;
            cost *= 1.0f - f;
        }
        int costI = Math.round(cost);
        costI = Math.max(costI, 1);
        return costI;
    }
    
    public boolean hasRequiredCostAndAlignment(final EntityPlayer entityplayer, final LOTRHireableBase trader) {
        final int coins = LOTRItemCoin.getInventoryValue(entityplayer);
        if (coins < this.getCost(entityplayer, trader)) {
            return false;
        }
        final LOTRFaction fac = trader.getFaction();
        if (!this.pledgeType.canAcceptPlayer(entityplayer, fac)) {
            return false;
        }
        final float alignment = LOTRLevelData.getData(entityplayer).getAlignment(fac);
        return alignment >= this.alignmentRequired;
    }
    
    public String getUnitTradeName() {
        if (this.mountClass == null) {
            final String entityName = LOTREntities.getStringFromClass(this.entityClass);
            return StatCollector.translateToLocal("entity." + entityName + ".name");
        }
        return StatCollector.translateToLocal("lotr.unit." + this.name);
    }
    
    public void hireUnit(final EntityPlayer entityplayer, final LOTRHireableBase trader, final String squadron) {
        if (this.hasRequiredCostAndAlignment(entityplayer, trader)) {
            trader.onUnitTrade(entityplayer);
            final int cost = this.getCost(entityplayer, trader);
            LOTRItemCoin.takeCoins(cost, entityplayer);
            ((LOTREntityNPC)trader).playTradeSound();
            final World world = ((Entity)entityplayer).worldObj;
            final LOTREntityNPC hiredNPC = this.getOrCreateHiredNPC(world);
            if (hiredNPC != null) {
                EntityLiving mount = null;
                if (this.mountClass != null) {
                    mount = this.createHiredMount(world);
                }
                final boolean unitExists = world.loadedEntityList.contains(hiredNPC);
                hiredNPC.hiredNPCInfo.hireUnit(entityplayer, !unitExists, trader.getFaction(), this, squadron, (Entity)mount);
                if (!unitExists) {
                    world.spawnEntityInWorld((Entity)hiredNPC);
                    if (mount != null) {
                        world.spawnEntityInWorld((Entity)mount);
                    }
                }
            }
        }
    }
    
    public LOTREntityNPC getOrCreateHiredNPC(final World world) {
        final LOTREntityNPC entity = (LOTREntityNPC)EntityList.createEntityByName(LOTREntities.getStringFromClass(this.entityClass), world);
        entity.initCreatureForHire(null);
        entity.refreshCurrentAttackMode();
        return entity;
    }
    
    public EntityLiving createHiredMount(final World world) {
        if (this.mountClass == null) {
            return null;
        }
        final EntityLiving entity = (EntityLiving)EntityList.createEntityByName(LOTREntities.getStringFromClass(this.mountClass), world);
        if (entity instanceof LOTREntityNPC) {
            ((LOTREntityNPC)entity).initCreatureForHire(null);
            ((LOTREntityNPC)entity).refreshCurrentAttackMode();
        }
        else {
            entity.onSpawnWithEgg((IEntityLivingData)null);
        }
        if (this.mountArmor != null && world.rand.nextFloat() < this.mountArmorChance) {
            if (entity instanceof LOTREntityHorse) {
                ((LOTREntityHorse)entity).setMountArmor(new ItemStack(this.mountArmor));
            }
            else if (entity instanceof LOTREntityWarg) {
                ((LOTREntityWarg)entity).setWargArmor(new ItemStack(this.mountArmor));
            }
        }
        return entity;
    }
    
    public enum PledgeType
    {
        NONE(0), 
        FACTION(1), 
        ANY_ELF(2), 
        ANY_DWARF(3);
        
        public final int typeID;
        
        private PledgeType(final int i) {
            this.typeID = i;
        }
        
        public boolean canAcceptPlayer(final EntityPlayer entityplayer, final LOTRFaction fac) {
            final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            final LOTRFaction pledged = pd.getPledgeFaction();
            if (this == PledgeType.NONE) {
                return true;
            }
            if (this == PledgeType.FACTION) {
                return pd.isPledgedTo(fac);
            }
            if (this == PledgeType.ANY_ELF) {
                return pledged != null && pledged.isOfType(LOTRFaction.FactionType.TYPE_ELF) && !pledged.isOfType(LOTRFaction.FactionType.TYPE_MAN);
            }
            return this == PledgeType.ANY_DWARF && pledged != null && pledged.isOfType(LOTRFaction.FactionType.TYPE_DWARF);
        }
        
        public String getCommandReqText(final LOTRFaction fac) {
            if (this == PledgeType.NONE) {
                return null;
            }
            return StatCollector.translateToLocalFormatted("lotr.hiredNPC.commandReq.pledge." + this.name(), new Object[] { fac.factionName() });
        }
        
        public static PledgeType forID(final int i) {
            for (final PledgeType t : values()) {
                if (t.typeID == i) {
                    return t;
                }
            }
            return PledgeType.NONE;
        }
    }
}
