// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.item.LOTRItemHaradTurban;
import lotr.common.item.LOTRItemHaradRobes;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

public class LOTREntityBanditHarad extends LOTREntityBandit
{
    private static ItemStack[] weapons;
    private static int[] robeColors;
    
    public LOTREntityBanditHarad(final World world) {
        super(world);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(LOTREntityBanditHarad.weapons.length);
        super.npcItemsInv.setMeleeWeapon(LOTREntityBanditHarad.weapons[i].copy());
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(4, (ItemStack)null);
        if (((Entity)this).rand.nextInt(4) == 0) {
            final ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
            final int robeColor = LOTREntityBanditHarad.robeColors[((Entity)this).rand.nextInt(LOTREntityBanditHarad.robeColors.length)];
            LOTRItemHaradRobes.setRobesColor(turban, robeColor);
            if (((Entity)this).rand.nextInt(3) == 0) {
                LOTRItemHaradTurban.setHasOrnament(turban, true);
            }
            this.setCurrentItemOrArmor(4, turban);
        }
        return data;
    }
    
    static {
        LOTREntityBanditHarad.weapons = new ItemStack[] { new ItemStack(LOTRMod.daggerBronze), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.daggerNearHarad), new ItemStack(LOTRMod.daggerNearHaradPoisoned), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.daggerHaradPoisoned) };
        LOTREntityBanditHarad.robeColors = new int[] { 3354412, 5984843, 5968655, 3619908, 9007463, 3228720 };
    }
}
