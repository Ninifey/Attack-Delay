// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import lotr.client.render.entity.LOTRHuornTextures;
import org.lwjgl.opengl.GL11;
import lotr.common.entity.npc.LOTREntityHuornBase;
import net.minecraft.entity.Entity;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.client.model.ModelRenderer;
import java.util.List;
import net.minecraft.client.model.ModelBase;

public class LOTRModelHuorn extends ModelBase
{
    private List woodBlocks;
    private List leafBlocks;
    private ModelRenderer face;
    private int baseX;
    private int baseY;
    private int baseZ;
    private Random rand;
    
    public LOTRModelHuorn() {
        this.woodBlocks = new ArrayList();
        this.leafBlocks = new ArrayList();
        this.baseX = 2;
        this.baseY = 0;
        this.baseZ = 2;
        (this.rand = new Random()).setSeed(100L);
        final int height = 6;
        final byte leafStart = 3;
        final byte leafRangeMin = 0;
        for (int j = this.baseY - leafStart + height; j <= this.baseY + height; ++j) {
            final int j2 = j - (this.baseY + height);
            for (int leafRange = leafRangeMin + 1 - j2 / 2, i = this.baseX - leafRange; i <= this.baseX + leafRange; ++i) {
                final int i2 = i - this.baseX;
                for (int k = this.baseZ - leafRange; k <= this.baseZ + leafRange; ++k) {
                    final int k2 = k - this.baseZ;
                    if (Math.abs(i2) != leafRange || Math.abs(k2) != leafRange || (this.rand.nextInt(2) != 0 && j2 != 0)) {
                        final ModelRenderer block = new ModelRenderer((ModelBase)this, 0, 0);
                        block.addBox(-8.0f, -8.0f, -8.0f, 16, 16, 16);
                        block.setRotationPoint(i * 16.0f, 16.0f - j * 16.0f, k * 16.0f);
                        this.leafBlocks.add(block);
                    }
                }
            }
        }
        for (int j = 0; j < height; ++j) {
            final ModelRenderer block2 = new ModelRenderer((ModelBase)this, 0, 0);
            block2.addBox(-8.0f, -8.0f, -8.0f, 16, 16, 16);
            block2.setRotationPoint(this.baseX * 16.0f, 16.0f - j * 16.0f, this.baseZ * 16.0f);
            this.woodBlocks.add(block2);
        }
        (this.face = new ModelRenderer((ModelBase)this, 0, 0)).addBox(-8.0f, -8.0f, -8.0f, 16, 16, 16, 0.5f);
        this.face.setRotationPoint(0.0f, 0.0f, 0.0f);
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        final LOTREntityHuornBase huorn = (LOTREntityHuornBase)entity;
        if (huorn.isHuornActive()) {
            this.face.render(f5);
        }
        GL11.glPushMatrix();
        GL11.glEnable(2884);
        GL11.glTranslatef(-(float)this.baseX, -(float)this.baseY, -(float)this.baseZ);
        for (int i = 0; i < this.woodBlocks.size(); ++i) {
            if (i == 0) {
                LOTRHuornTextures.INSTANCE.bindWoodTexture(huorn);
            }
            final ModelRenderer block = this.woodBlocks.get(i);
            block.render(f5);
        }
        for (int i = 0; i < this.leafBlocks.size(); ++i) {
            if (i == 0) {
                LOTRHuornTextures.INSTANCE.bindLeafTexture(huorn);
            }
            final ModelRenderer block = this.leafBlocks.get(i);
            block.render(f5);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(2884);
        GL11.glPopMatrix();
    }
}
