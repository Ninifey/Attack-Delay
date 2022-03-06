// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import net.minecraft.init.Items;
import net.minecraft.entity.EntityAgeable;
import java.util.UUID;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;
import lotr.common.entity.LOTRRandomSkinEntity;

public class LOTREntityWhiteOryx extends LOTREntityGemsbok implements LOTRRandomSkinEntity
{
    public static final float ORYX_SCALE = 0.9f;
    
    public LOTREntityWhiteOryx(final World world) {
        super(world);
        this.setSize(((Entity)this).width * 0.9f, ((Entity)this).height * 0.9f);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0);
    }
    
    @Override
    public void setUniqueID(final UUID uuid) {
        ((Entity)this).entityUniqueID = uuid;
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entity) {
        return (EntityAgeable)new LOTREntityWhiteOryx(((Entity)this).worldObj);
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        for (int hide = ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(1 + i), l = 0; l < hide; ++l) {
            this.func_145779_a(Items.leather, 1);
        }
        for (int meat = ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(1 + i), j = 0; j < meat; ++j) {
            if (this.isBurning()) {
                this.func_145779_a(LOTRMod.deerCooked, 1);
            }
            else {
                this.func_145779_a(LOTRMod.deerRaw, 1);
            }
        }
    }
    
    @Override
    protected float getGemsbokSoundPitch() {
        return 0.9f;
    }
}
