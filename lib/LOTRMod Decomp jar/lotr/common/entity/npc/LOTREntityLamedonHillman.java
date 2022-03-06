// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.item.LOTRItemLeatherHat;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

public class LOTREntityLamedonHillman extends LOTREntityGondorLevyman
{
    private static ItemStack[] hillmanWeapons;
    private static int[] dyedHatColors;
    private static int[] featherColors;
    
    public LOTREntityLamedonHillman(final World world) {
        super(world);
    }
    
    @Override
    protected EntityAIBase createGondorAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.6, true);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(LOTREntityLamedonHillman.hillmanWeapons.length);
        super.npcItemsInv.setMeleeWeapon(LOTREntityLamedonHillman.hillmanWeapons[i].copy());
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack((Item)Items.leather_boots));
        this.setCurrentItemOrArmor(2, (ItemStack)null);
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyLamedonJacket));
        if (((Entity)this).rand.nextInt(3) == 0) {
            final ItemStack hat = new ItemStack(LOTRMod.leatherHat);
            if (((Entity)this).rand.nextBoolean()) {
                LOTRItemLeatherHat.setHatColor(hat, LOTREntityLamedonHillman.dyedHatColors[((Entity)this).rand.nextInt(LOTREntityLamedonHillman.dyedHatColors.length)]);
            }
            if (((Entity)this).rand.nextBoolean()) {
                LOTRItemLeatherHat.setFeatherColor(hat, LOTREntityLamedonHillman.featherColors[((Entity)this).rand.nextInt(LOTREntityLamedonHillman.featherColors.length)]);
            }
            this.setCurrentItemOrArmor(4, hat);
        }
        else {
            this.setCurrentItemOrArmor(4, (ItemStack)null);
        }
        return data;
    }
    
    static {
        LOTREntityLamedonHillman.hillmanWeapons = new ItemStack[] { new ItemStack(Items.iron_axe), new ItemStack(LOTRMod.battleaxeIron), new ItemStack(LOTRMod.pikeIron), new ItemStack(LOTRMod.axeBronze), new ItemStack(LOTRMod.battleaxeBronze) };
        LOTREntityLamedonHillman.dyedHatColors = new int[] { 6316128, 2437173, 0 };
        LOTREntityLamedonHillman.featherColors = new int[] { 16777215, 10526880, 5658198, 2179924, 798013 };
    }
}
