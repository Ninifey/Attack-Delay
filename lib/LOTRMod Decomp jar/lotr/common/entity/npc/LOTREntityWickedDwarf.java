// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import lotr.common.fac.LOTRFaction;

public class LOTREntityWickedDwarf extends LOTREntityDwarf implements LOTRTradeable.Smith
{
    public static final LOTRFaction[] tradeFactions;
    private static ItemStack[] wickedWeapons;
    
    public LOTREntityWickedDwarf(final World world) {
        super(world);
        this.addTargetTasks(true);
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.WICKED_DWARF_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.WICKED_DWARF_SELL;
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(LOTREntityWickedDwarf.wickedWeapons.length);
        super.npcItemsInv.setMeleeWeapon(LOTREntityWickedDwarf.wickedWeapons[i].copy());
        super.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.pickaxeDwarven));
        if (((Entity)this).rand.nextInt(4) == 0) {
            this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsDwarven));
            this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDwarven));
            this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDwarven));
        }
        else {
            this.setCurrentItemOrArmor(1, (ItemStack)null);
            this.setCurrentItemOrArmor(2, (ItemStack)null);
            this.setCurrentItemOrArmor(3, (ItemStack)null);
        }
        this.setCurrentItemOrArmor(4, (ItemStack)null);
        return data;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.MORDOR;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killWickedDwarf;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    public boolean canTradeWith(final EntityPlayer entityplayer) {
        boolean hasAlignment = false;
        for (final LOTRFaction f : LOTREntityWickedDwarf.tradeFactions) {
            if (LOTRLevelData.getData(entityplayer).getAlignment(f) >= 100.0f) {
                hasAlignment = true;
                break;
            }
        }
        return hasAlignment && this.isFriendly(entityplayer);
    }
    
    @Override
    public void onPlayerTrade(final EntityPlayer entityplayer, final LOTRTradeEntries.TradeType type, final ItemStack itemstack) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeWickedDwarf);
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return false;
    }
    
    @Override
    protected boolean canDwarfSpawnHere() {
        final int i = MathHelper.floor_double(((Entity)this).posX);
        final int j = MathHelper.floor_double(((Entity)this).boundingBox.minY);
        final int k = MathHelper.floor_double(((Entity)this).posZ);
        return j > 62 && ((Entity)this).worldObj.getBlock(i, j - 1, k) == ((Entity)this).worldObj.getBiomeGenForCoords(i, k).topBlock;
    }
    
    @Override
    protected boolean canDwarfSpawnAboveGround() {
        return true;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "dwarf/wicked/hostile";
        }
        if (this.canTradeWith(entityplayer)) {
            return "dwarf/wicked/friendly";
        }
        return "dwarf/wicked/neutral";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        return null;
    }
    
    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return null;
    }
    
    static {
        tradeFactions = new LOTRFaction[] { LOTRFaction.MORDOR, LOTRFaction.GUNDABAD, LOTRFaction.ANGMAR, LOTRFaction.RHUN };
        LOTREntityWickedDwarf.wickedWeapons = new ItemStack[] { new ItemStack(LOTRMod.swordDwarven), new ItemStack(LOTRMod.battleaxeDwarven), new ItemStack(LOTRMod.hammerDwarven) };
    }
}
