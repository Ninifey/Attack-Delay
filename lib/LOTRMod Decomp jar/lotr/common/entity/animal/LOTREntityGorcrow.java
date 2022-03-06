// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class LOTREntityGorcrow extends LOTREntityBird
{
    public static float GORCROW_SCALE;
    
    public LOTREntityGorcrow(final World world) {
        super(world);
        this.setSize(((Entity)this).width * LOTREntityGorcrow.GORCROW_SCALE, ((Entity)this).height * LOTREntityGorcrow.GORCROW_SCALE);
    }
    
    @Override
    public String getBirdTextureDir() {
        return "gorcrow";
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.setBirdType(BirdType.CROW);
        return data;
    }
    
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.75f;
    }
    
    static {
        LOTREntityGorcrow.GORCROW_SCALE = 1.4f;
    }
}
