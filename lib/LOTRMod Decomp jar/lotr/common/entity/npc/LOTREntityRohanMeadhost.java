// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import lotr.common.LOTRFoods;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityRohanMeadhost extends LOTREntityRohanMan implements LOTRTradeable.Bartender
{
    public LOTREntityRohanMeadhost(final World world) {
        super(world);
        this.addTargetTasks(false);
        super.npcLocationName = "entity.lotr.RohanMeadhost.locationName";
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.ROHAN_MEADHOST_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.ROHAN_MEADHOST_SELL;
    }
    
    public EntityAIBase createRohanAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.3, false);
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(((Entity)this).rand.nextBoolean());
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.mugMead));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        return data;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int j = ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(i + 1), k = 0; k < j; ++k) {
            final int l = ((Entity)this).rand.nextInt(11);
            switch (l) {
                case 0:
                case 1:
                case 2: {
                    final Item food = LOTRFoods.ROHAN.getRandomFood(((Entity)this).rand).getItem();
                    this.entityDropItem(new ItemStack(food), 0.0f);
                    break;
                }
                case 3: {
                    this.entityDropItem(new ItemStack(Items.gold_nugget, 2 + ((Entity)this).rand.nextInt(3)), 0.0f);
                    break;
                }
                case 4: {
                    this.entityDropItem(new ItemStack(Items.wheat, 1 + ((Entity)this).rand.nextInt(4)), 0.0f);
                    break;
                }
                case 5: {
                    this.entityDropItem(new ItemStack(Items.sugar, 1 + ((Entity)this).rand.nextInt(3)), 0.0f);
                    break;
                }
                case 6: {
                    this.entityDropItem(new ItemStack(Items.paper, 1 + ((Entity)this).rand.nextInt(2)), 0.0f);
                    break;
                }
                case 7:
                case 8: {
                    this.entityDropItem(new ItemStack(LOTRMod.mug), 0.0f);
                    break;
                }
                case 9:
                case 10: {
                    final Item drink = LOTRMod.mugMead;
                    this.entityDropItem(new ItemStack(drink, 1, 1 + ((Entity)this).rand.nextInt(3)), 0.0f);
                    break;
                }
            }
        }
    }
    
    @Override
    public boolean canTradeWith(final EntityPlayer entityplayer) {
        return this.isFriendly(entityplayer);
    }
    
    @Override
    public void onPlayerTrade(final EntityPlayer entityplayer, final LOTRTradeEntries.TradeType type, final ItemStack itemstack) {
        if (type == LOTRTradeEntries.TradeType.BUY && itemstack.getItem() == LOTRMod.mugMead) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.buyRohanMead);
        }
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (this.isFriendly(entityplayer)) {
            return "rohan/meadhost/friendly";
        }
        return "rohan/meadhost/hostile";
    }
}
