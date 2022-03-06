// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.entity.Entity;
import java.util.List;
import net.minecraft.entity.EntityLiving;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.World;

public class LOTREntitySeagull extends LOTREntityBird
{
    public static float SEAGULL_SCALE;
    
    public LOTREntitySeagull(final World world) {
        super(world);
        this.setSize(((Entity)this).width * LOTREntitySeagull.SEAGULL_SCALE, ((Entity)this).height * LOTREntitySeagull.SEAGULL_SCALE);
    }
    
    @Override
    public String getBirdTextureDir() {
        return "seagull";
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.setBirdType(BirdType.COMMON);
        return data;
    }
    
    @Override
    protected boolean canStealItems() {
        return true;
    }
    
    @Override
    protected boolean isStealable(final ItemStack itemstack) {
        final Item item = itemstack.getItem();
        return item == Items.fish || item == Items.cooked_fished || super.isStealable(itemstack);
    }
    
    @Override
    protected boolean canBirdSpawnHere() {
        if (LOTRAmbientSpawnChecks.canSpawn(this, 8, 4, 40, 4, Material.leaves, Material.sand)) {
            final double range = 16.0;
            final List nearbyGulls = ((Entity)this).worldObj.getEntitiesWithinAABB((Class)LOTREntitySeagull.class, ((Entity)this).boundingBox.expand(range, range, range));
            return nearbyGulls.size() < 2;
        }
        return false;
    }
    
    @Override
    protected String getLivingSound() {
        return "lotr:bird.seagull.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "lotr:bird.seagull.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "lotr:bird.seagull.hurt";
    }
    
    static {
        LOTREntitySeagull.SEAGULL_SCALE = 1.4f;
    }
}
