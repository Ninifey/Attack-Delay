// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

public class LOTREntityDaleLevyman extends LOTREntityDaleMan
{
    private static ItemStack[] militiaWeapons;
    private static int[] leatherDyes;
    
    public LOTREntityDaleLevyman(final World world) {
        super(world);
        this.addTargetTasks(true);
    }
    
    @Override
    protected EntityAIBase createDaleAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.4, true);
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = ((Entity)this).rand.nextInt(LOTREntityDaleLevyman.militiaWeapons.length);
        super.npcItemsInv.setMeleeWeapon(LOTREntityDaleLevyman.militiaWeapons[i].copy());
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, this.dyeLeather(new ItemStack((Item)Items.leather_boots)));
        this.setCurrentItemOrArmor(2, this.dyeLeather(new ItemStack((Item)Items.leather_leggings)));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDaleGambeson));
        if (((Entity)this).rand.nextInt(3) != 0) {
            this.setCurrentItemOrArmor(4, (ItemStack)null);
        }
        else {
            i = ((Entity)this).rand.nextInt(3);
            if (i == 0) {
                this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDale));
            }
            else if (i == 1) {
                this.setCurrentItemOrArmor(4, new ItemStack((Item)Items.iron_helmet));
            }
            else if (i == 2) {
                this.setCurrentItemOrArmor(4, this.dyeLeather(new ItemStack((Item)Items.leather_helmet)));
            }
        }
        return data;
    }
    
    private ItemStack dyeLeather(final ItemStack itemstack) {
        final int i = ((Entity)this).rand.nextInt(LOTREntityDaleLevyman.leatherDyes.length);
        final int color = LOTREntityDaleLevyman.leatherDyes[i];
        ((ItemArmor)itemstack.getItem()).func_82813_b(itemstack, color);
        return itemstack;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "dale/soldier/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "dale/soldier/hired";
        }
        return "dale/soldier/friendly";
    }
    
    static {
        LOTREntityDaleLevyman.militiaWeapons = new ItemStack[] { new ItemStack(LOTRMod.swordDale), new ItemStack(LOTRMod.battleaxeDale), new ItemStack(LOTRMod.pikeDale), new ItemStack(Items.iron_sword), new ItemStack(Items.iron_axe), new ItemStack(LOTRMod.battleaxeIron), new ItemStack(LOTRMod.pikeIron), new ItemStack(LOTRMod.swordBronze), new ItemStack(LOTRMod.axeBronze), new ItemStack(LOTRMod.battleaxeBronze) };
        LOTREntityDaleLevyman.leatherDyes = new int[] { 7034184, 5650986, 7039851, 5331051, 2305612, 2698291, 1973790 };
    }
}
