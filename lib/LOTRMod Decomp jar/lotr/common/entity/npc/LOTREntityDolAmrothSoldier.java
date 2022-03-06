// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

public class LOTREntityDolAmrothSoldier extends LOTREntityGondorLevyman
{
    private static ItemStack[] manAtArmsWeapons;
    
    public LOTREntityDolAmrothSoldier(final World world) {
        super(world);
        super.spawnRidingHorse = (((Entity)this).rand.nextInt(6) == 0);
    }
    
    @Override
    public LOTRNPCMount createMountToRide() {
        final LOTREntityHorse horse = (LOTREntityHorse)super.createMountToRide();
        horse.setMountArmor(new ItemStack(LOTRMod.horseArmorDolAmroth));
        return horse;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = ((Entity)this).rand.nextInt(LOTREntityDolAmrothSoldier.manAtArmsWeapons.length);
        super.npcItemsInv.setMeleeWeapon(LOTREntityDolAmrothSoldier.manAtArmsWeapons[i].copy());
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack((Item)Items.leather_boots));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDolAmrothGambeson));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDolAmrothGambeson));
        if (((Entity)this).rand.nextInt(3) == 0) {
            this.setCurrentItemOrArmor(4, (ItemStack)null);
        }
        else {
            i = ((Entity)this).rand.nextInt(3);
            if (i == 0) {
                this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDolAmroth));
            }
            else if (i == 1) {
                this.setCurrentItemOrArmor(4, new ItemStack((Item)Items.iron_helmet));
            }
            else if (i == 2) {
                this.setCurrentItemOrArmor(4, new ItemStack((Item)Items.leather_helmet));
            }
        }
        return data;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "gondor/swanKnight/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "gondor/swanKnight/hired";
        }
        return "gondor/swanKnight/friendly";
    }
    
    static {
        LOTREntityDolAmrothSoldier.manAtArmsWeapons = new ItemStack[] { new ItemStack(LOTRMod.swordDolAmroth), new ItemStack(LOTRMod.swordDolAmroth), new ItemStack(LOTRMod.swordGondor), new ItemStack(Items.iron_sword) };
    }
}
