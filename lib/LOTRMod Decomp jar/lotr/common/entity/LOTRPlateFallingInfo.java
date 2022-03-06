// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity;

import net.minecraft.world.World;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.IExtendedEntityProperties;

public class LOTRPlateFallingInfo implements IExtendedEntityProperties
{
    private static final String propID = "lotr_plateFall";
    private Entity theEntity;
    private static final int tickDelayFactor = 1;
    private static final int stackSize = 64;
    private static final int numFallers = 65;
    private int updateTick;
    private float[] posXTicksAgo;
    private boolean[] isFalling;
    private float[] fallerPos;
    private float[] prevFallerPos;
    private float[] fallerSpeed;
    
    public LOTRPlateFallingInfo(final Entity entity) {
        this.posXTicksAgo = new float[65];
        this.isFalling = new boolean[65];
        this.fallerPos = new float[65];
        this.prevFallerPos = new float[65];
        this.fallerSpeed = new float[65];
        this.theEntity = entity;
    }
    
    public void update() {
        final float curPos = (float)this.theEntity.posY;
        if (!this.theEntity.onGround && this.theEntity.motionY > 0.0) {
            for (int l = 0; l < this.posXTicksAgo.length; ++l) {
                this.posXTicksAgo[l] = Math.max(this.posXTicksAgo[l], curPos);
            }
        }
        if (this.updateTick % 1 == 0) {
            for (int l = this.posXTicksAgo.length - 1; l > 0; --l) {
                this.posXTicksAgo[l] = this.posXTicksAgo[l - 1];
            }
            this.posXTicksAgo[0] = curPos;
        }
        ++this.updateTick;
        for (int l = 0; l < this.fallerPos.length; ++l) {
            this.prevFallerPos[l] = this.fallerPos[l];
            float pos = this.fallerPos[l];
            float speed = this.fallerSpeed[l];
            boolean fall = this.isFalling[l];
            if (!fall && pos > this.posXTicksAgo[l]) {
                fall = true;
            }
            if (this.isFalling[l] = fall) {
                speed += (float)0.08;
                pos -= speed;
                speed *= (float)0.98;
            }
            else {
                speed = 0.0f;
            }
            if (pos < curPos) {
                pos = curPos;
                speed = 0.0f;
                this.isFalling[l] = false;
            }
            this.fallerPos[l] = pos;
            this.fallerSpeed[l] = speed;
        }
    }
    
    public float getPlateOffsetY(final float f) {
        return this.getOffsetY(0, f);
    }
    
    public float getFoodOffsetY(final int food, final float f) {
        return this.getOffsetY(food - 1, f);
    }
    
    private float getOffsetY(int index, final float f) {
        index = MathHelper.clamp_int(index, 0, this.fallerPos.length - 1);
        final float pos = this.prevFallerPos[index] + (this.fallerPos[index] - this.prevFallerPos[index]) * f;
        float offset = pos - (float)(this.theEntity.prevPosY + (this.theEntity.posY - this.theEntity.prevPosY) * f);
        offset = Math.max(offset, 0.0f);
        return offset;
    }
    
    public static LOTRPlateFallingInfo getOrCreateFor(final Entity entity, final boolean create) {
        LOTRPlateFallingInfo props = (LOTRPlateFallingInfo)entity.getExtendedProperties("lotr_plateFall");
        if (props == null && create) {
            props = new LOTRPlateFallingInfo(entity);
            entity.registerExtendedProperties("lotr_plateFall", (IExtendedEntityProperties)props);
        }
        return props;
    }
    
    public void saveNBTData(final NBTTagCompound nbt) {
    }
    
    public void loadNBTData(final NBTTagCompound nbt) {
    }
    
    public void init(final Entity entity, final World world) {
    }
}
