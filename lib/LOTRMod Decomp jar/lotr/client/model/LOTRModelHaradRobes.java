// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import lotr.common.entity.npc.LOTREntityNPC;
import org.lwjgl.opengl.GL11;
import lotr.common.item.LOTRItemHaradRobes;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class LOTRModelHaradRobes extends LOTRModelHuman
{
    protected ItemStack robeItem;
    
    public LOTRModelHaradRobes() {
        this(0.0f);
    }
    
    public LOTRModelHaradRobes(final float f) {
        super(f, true);
    }
    
    public void setRobeItem(final ItemStack itemstack) {
        this.robeItem = itemstack;
    }
    
    @Override
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        final int robeColor = LOTRItemHaradRobes.getRobesColor(this.robeItem);
        final float r = (robeColor >> 16 & 0xFF) / 255.0f;
        final float g = (robeColor >> 8 & 0xFF) / 255.0f;
        final float b = (robeColor & 0xFF) / 255.0f;
        GL11.glColor3f(r, g, b);
        super.bipedChest.showModel = (entity instanceof LOTREntityNPC && ((LOTREntityNPC)entity).shouldRenderNPCChest());
        super.bipedHead.render(f5);
        super.bipedHeadwear.render(f5);
        super.bipedBody.render(f5);
        super.bipedRightArm.render(f5);
        super.bipedLeftArm.render(f5);
        super.bipedRightLeg.render(f5);
        super.bipedLeftLeg.render(f5);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
    }
}
