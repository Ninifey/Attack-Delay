// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import lotr.common.entity.LOTREntities;
import net.minecraft.util.MovingObjectPosition;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.util.StatCollector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.world.World;

public class LOTREntityZebra extends LOTREntityHorse
{
    public LOTREntityZebra(final World world) {
        super(world);
    }
    
    public int getHorseType() {
        return 0;
    }
    
    public boolean func_110259_cr() {
        return false;
    }
    
    @Override
    public String getCommandSenderName() {
        if (this.hasCustomNameTag()) {
            return this.getCustomNameTag();
        }
        final String s = EntityList.getEntityString((Entity)this);
        return StatCollector.translateToLocal("entity." + s + ".name");
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityageable) {
        final LOTREntityZebra zebra = (LOTREntityZebra)super.createChild(entityageable);
        return (EntityAgeable)zebra;
    }
    
    @Override
    public boolean interact(final EntityPlayer entityplayer) {
        final ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (itemstack != null && itemstack.getItem() == Items.bucket && !entityplayer.capabilities.isCreativeMode) {
            final ItemStack itemStack = itemstack;
            --itemStack.stackSize;
            if (itemstack.stackSize <= 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.milk_bucket));
            }
            else if (!entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.milk_bucket))) {
                entityplayer.dropPlayerItemWithRandomChoice(new ItemStack(Items.milk_bucket, 1, 0), false);
            }
            return true;
        }
        return super.interact(entityplayer);
    }
    
    protected void dropFewItems(final boolean flag, final int i) {
        for (int j = ((Entity)this).rand.nextInt(2) + ((Entity)this).rand.nextInt(1 + i), k = 0; k < j; ++k) {
            this.func_145779_a(Items.leather, 1);
        }
        for (int j = ((Entity)this).rand.nextInt(2) + 1 + ((Entity)this).rand.nextInt(1 + i), l = 0; l < j; ++l) {
            if (this.isBurning()) {
                this.func_145779_a(LOTRMod.zebraCooked, 1);
            }
            else {
                this.func_145779_a(LOTRMod.zebraRaw, 1);
            }
        }
    }
    
    protected String getLivingSound() {
        return "lotr:zebra.say";
    }
    
    protected String getHurtSound() {
        return "lotr:zebra.hurt";
    }
    
    protected String getDeathSound() {
        return "lotr:zebra.death";
    }
    
    protected String getAngrySoundName() {
        return "lotr:zebra.hurt";
    }
    
    @Override
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID((Entity)this));
    }
}
