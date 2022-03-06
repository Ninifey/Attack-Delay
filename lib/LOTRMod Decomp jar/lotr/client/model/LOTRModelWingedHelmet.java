// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

public class LOTRModelWingedHelmet extends LOTRModelGondorHelmet
{
    public LOTRModelWingedHelmet() {
        this(0.0f);
    }
    
    public LOTRModelWingedHelmet(final float f) {
        super(f);
        super.bipedHead.setTextureOffset(32, 8).addBox(-6.0f - f, -4.0f, -0.5f, 2, 2, 1, 0.0f);
        super.bipedHead.setTextureOffset(38, 8).addBox(-7.0f - f, -13.0f, -0.5f, 3, 9, 1, 0.0f);
        super.bipedHead.setTextureOffset(46, 8).addBox(-5.5f - f, -17.0f, -0.5f, 2, 4, 1, 0.0f);
        super.bipedHead.mirror = true;
        super.bipedHead.setTextureOffset(32, 8).addBox(4.0f + f, -4.0f, -0.5f, 2, 2, 1, 0.0f);
        super.bipedHead.setTextureOffset(38, 8).addBox(4.0f + f, -13.0f, -0.5f, 3, 9, 1, 0.0f);
        super.bipedHead.setTextureOffset(46, 8).addBox(3.5f + f, -17.0f, -0.5f, 2, 4, 1, 0.0f);
    }
}
