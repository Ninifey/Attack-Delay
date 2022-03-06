// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.fx;

import net.minecraft.entity.Entity;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;
import net.minecraft.client.particle.EntityNoteFX;

public class LOTREntityMusicFX extends EntityNoteFX
{
    private double noteMoveX;
    private double noteMoveY;
    private double noteMoveZ;
    
    public LOTREntityMusicFX(final World world, final double d, final double d1, final double d2, final double d3, final double d4, final double d5, final double pitch) {
        super(world, d, d1, d2, pitch, 0.0, 0.0);
        this.noteMoveX = d3;
        this.noteMoveY = d4;
        this.noteMoveZ = d5;
        ((EntityFX)this).particleMaxAge = 8 + ((Entity)this).rand.nextInt(20);
    }
    
    public void onUpdate() {
        super.onUpdate();
        final double decel = 0.98;
        this.noteMoveX *= decel;
        this.noteMoveY *= decel;
        this.noteMoveZ *= decel;
        ((Entity)this).motionX = this.noteMoveX;
        ((Entity)this).motionY = this.noteMoveY;
        ((Entity)this).motionZ = this.noteMoveZ;
    }
}
