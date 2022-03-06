// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.item.LOTRItemHaradRobes;
import java.awt.Color;
import net.minecraft.util.MathHelper;
import lotr.common.item.LOTRItemHaradTurban;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import java.util.Random;
import net.minecraft.world.World;

public abstract class LOTREntitySouthronTrader extends LOTREntityNearHaradrim implements LOTRTradeable
{
    public LOTREntitySouthronTrader(final World world) {
        super(world);
        this.addTargetTasks(false);
    }
    
    public static ItemStack createTraderTurban(final Random random) {
        final ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
        if (random.nextInt(3) == 0) {
            LOTRItemHaradTurban.setHasOrnament(turban, true);
        }
        final float h = random.nextFloat() * 360.0f;
        final float s = MathHelper.randomFloatClamp(random, 0.6f, 0.8f);
        final float b = MathHelper.randomFloatClamp(random, 0.5f, 0.75f);
        final int turbanColor = Color.HSBtoRGB(h, s, b) & 0xFFFFFF;
        LOTRItemHaradRobes.setRobesColor(turban, turbanColor);
        return turban;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.setCurrentItemOrArmor(4, createTraderTurban(((Entity)this).rand));
        return data;
    }
    
    @Override
    public void onAttackModeChange(final AttackMode mode, final boolean mounted) {
        if (mode == AttackMode.IDLE) {
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getIdleItem());
        }
        else {
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getMeleeWeapon());
        }
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    public boolean canTradeWith(final EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 0.0f && this.isFriendly(entityplayer);
    }
    
    @Override
    public void onPlayerTrade(final EntityPlayer entityplayer, final LOTRTradeEntries.TradeType type, final ItemStack itemstack) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeBazaarTrader);
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (this.isFriendly(entityplayer)) {
            return "nearHarad/coast/bazaarTrader/friendly";
        }
        return "nearHarad/coast/bazaarTrader/hostile";
    }
}
