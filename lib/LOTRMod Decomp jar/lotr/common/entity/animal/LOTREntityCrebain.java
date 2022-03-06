// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class LOTREntityCrebain extends LOTREntityBird
{
    public static float CREBAIN_SCALE;
    
    public LOTREntityCrebain(final World world) {
        super(world);
        this.setSize(((Entity)this).width * LOTREntityCrebain.CREBAIN_SCALE, ((Entity)this).height * LOTREntityCrebain.CREBAIN_SCALE);
    }
    
    @Override
    public String getBirdTextureDir() {
        return "crebain";
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.setBirdType(BirdType.CROW);
        return data;
    }
    
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.85f;
    }
    
    static {
        LOTREntityCrebain.CREBAIN_SCALE = 1.8f;
    }
}
