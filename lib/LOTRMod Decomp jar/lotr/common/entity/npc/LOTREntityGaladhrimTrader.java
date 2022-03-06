// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.EntityLiving;
import lotr.common.LOTRAchievement;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRLevelData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import lotr.common.LOTRCapes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.world.World;

public class LOTREntityGaladhrimTrader extends LOTREntityGaladhrimElf implements LOTRTravellingTrader
{
    public LOTREntityGaladhrimTrader(final World world) {
        super(world);
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new LOTREntityAIAttackOnCollide(this, 1.6, false));
        this.addTargetTasks(false);
        super.npcCape = LOTRCapes.GALADHRIM_TRADER;
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.GALADHRIM_TRADER_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.GALADHRIM_TRADER_SELL;
    }
    
    @Override
    public LOTREntityNPC createTravellingEscort() {
        return new LOTREntityGaladhrimElf(((Entity)this).worldObj);
    }
    
    @Override
    public String getDepartureSpeech() {
        return "galadhrim/trader/departure";
    }
    
    public int getTotalArmorValue() {
        return 10;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!((Entity)this).worldObj.isClient && this.isEntityAlive() && super.travellingTraderInfo.timeUntilDespawn == 0) {
            ((Entity)this).worldObj.setEntityState((Entity)this, (byte)15);
        }
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityplayer) {
        return 5 + ((Entity)this).rand.nextInt(3);
    }
    
    @Override
    public void onDeath(final DamageSource damagesource) {
        super.onDeath(damagesource);
        if (!((Entity)this).worldObj.isClient) {
            ((Entity)this).worldObj.setEntityState((Entity)this, (byte)15);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(final byte b) {
        if (b == 15) {
            for (int i = 0; i < 16; ++i) {
                final double d = ((Entity)this).posX + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)this).width;
                final double d2 = ((Entity)this).posY + ((Entity)this).rand.nextDouble() * ((Entity)this).height;
                final double d3 = ((Entity)this).posZ + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)this).width;
                final double d4 = -0.2 + ((Entity)this).rand.nextFloat() * 0.4f;
                final double d5 = -0.2 + ((Entity)this).rand.nextFloat() * 0.4f;
                final double d6 = -0.2 + ((Entity)this).rand.nextFloat() * 0.4f;
                LOTRMod.proxy.spawnParticle("leafGold_" + (30 + ((Entity)this).rand.nextInt(30)), d, d2, d3, d4, d5, d6);
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    @Override
    public boolean canTradeWith(final EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 75.0f && this.isFriendly(entityplayer);
    }
    
    @Override
    public void onPlayerTrade(final EntityPlayer entityplayer, final LOTRTradeEntries.TradeType type, final ItemStack itemstack) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeElvenTrader);
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return false;
    }
    
    @Override
    public boolean shouldRenderNPCHair() {
        return false;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "galadhrim/trader/hostile";
        }
        if (this.canTradeWith(entityplayer)) {
            return "galadhrim/trader/friendly";
        }
        return "galadhrim/trader/neutral";
    }
}
