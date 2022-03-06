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

public class LOTREntityGondorLevyman extends LOTREntityGondorMan
{
    private static ItemStack[] militiaWeapons;
    private static int[] leatherDyes;
    
    public LOTREntityGondorLevyman(final World world) {
        super(world);
        this.addTargetTasks(true);
    }
    
    @Override
    protected EntityAIBase createGondorAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.4, true);
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = ((Entity)this).rand.nextInt(LOTREntityGondorLevyman.militiaWeapons.length);
        super.npcItemsInv.setMeleeWeapon(LOTREntityGondorLevyman.militiaWeapons[i].copy());
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, this.dyeLeather(new ItemStack((Item)Items.leather_boots)));
        this.setCurrentItemOrArmor(2, this.dyeLeather(new ItemStack((Item)Items.leather_leggings)));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyGondorGambeson));
        if (((Entity)this).rand.nextInt(3) != 0) {
            this.setCurrentItemOrArmor(4, (ItemStack)null);
        }
        else {
            i = ((Entity)this).rand.nextInt(3);
            if (i == 0) {
                this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetGondor));
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
        final int i = ((Entity)this).rand.nextInt(LOTREntityGondorLevyman.leatherDyes.length);
        final int color = LOTREntityGondorLevyman.leatherDyes[i];
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
            return "gondor/soldier/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "gondor/soldier/hired";
        }
        return "gondor/soldier/friendly";
    }
    
    static {
        LOTREntityGondorLevyman.militiaWeapons = new ItemStack[] { new ItemStack(LOTRMod.swordGondor), new ItemStack(LOTRMod.hammerGondor), new ItemStack(LOTRMod.pikeGondor), new ItemStack(Items.iron_sword), new ItemStack(Items.iron_axe), new ItemStack(LOTRMod.battleaxeIron), new ItemStack(LOTRMod.pikeIron), new ItemStack(LOTRMod.swordBronze), new ItemStack(LOTRMod.axeBronze), new ItemStack(LOTRMod.battleaxeBronze) };
        LOTREntityGondorLevyman.leatherDyes = new int[] { 10855845, 8026746, 5526612, 3684408, 8350297, 10388590, 4799795, 5330539, 4211801, 2632504 };
    }
}
