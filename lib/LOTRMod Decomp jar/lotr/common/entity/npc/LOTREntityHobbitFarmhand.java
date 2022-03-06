// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.IPlantable;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.entity.ai.LOTREntityAIFarm;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.world.World;
import net.minecraft.item.Item;

public class LOTREntityHobbitFarmhand extends LOTREntityHobbit implements LOTRFarmhand
{
    public Item seedsItem;
    
    public LOTREntityHobbitFarmhand(final World world) {
        super(world);
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new LOTREntityAIAttackOnCollide(this, 1.3, false));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new LOTREntityAIFollowHiringPlayer(this));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new LOTREntityAIFarm(this, 1.0, 1.5f));
        ((EntityLiving)this).targetTasks.taskEntries.clear();
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_hoe));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        return data;
    }
    
    @Override
    public IPlantable getUnhiredSeeds() {
        if (this.seedsItem == null) {
            return (IPlantable)Items.wheat_seeds;
        }
        return (IPlantable)this.seedsItem;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        if (this.seedsItem != null) {
            nbt.setInteger("SeedsID", Item.getIdFromItem(this.seedsItem));
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        if (nbt.hasKey("SeedsID")) {
            final Item item = Item.getItemById(nbt.getInteger("SeedsID"));
            if (item != null && item instanceof IPlantable) {
                this.seedsItem = item;
            }
        }
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "hobbit/farmhand/hired";
        }
        return super.getSpeechBank(entityplayer);
    }
}
