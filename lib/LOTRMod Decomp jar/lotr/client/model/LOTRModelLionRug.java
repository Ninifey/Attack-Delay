// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.model;

import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;

public class LOTRModelLionRug extends ModelBase
{
    private LOTRModelLion lionModel;
    
    public LOTRModelLionRug() {
        this.lionModel = new LOTRModelLion();
    }
    
    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        this.setRotationAngles();
        GL11.glTranslatef(0.0f, -0.4f, 0.0f);
        GL11.glPushMatrix();
        GL11.glScalef(1.5f, 0.4f, 1.0f);
        this.lionModel.body.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.0f, 0.0f);
        this.lionModel.tail.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, -0.1f, 0.1f);
        this.lionModel.head.render(f5);
        this.lionModel.mane.render(f5);
        GL11.glPopMatrix();
        GL11.glTranslatef(0.0f, 0.15f, 0.0f);
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.4f, 0.0f, 0.0f);
        this.lionModel.leg1.render(f5);
        this.lionModel.leg3.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(0.4f, 0.0f, 0.0f);
        this.lionModel.leg2.render(f5);
        this.lionModel.leg4.render(f5);
        GL11.glPopMatrix();
    }
    
    private void setRotationAngles() {
        this.lionModel.leg1.rotateAngleX = (float)Math.toRadians(30.0);
        this.lionModel.leg1.rotateAngleZ = (float)Math.toRadians(90.0);
        this.lionModel.leg2.rotateAngleX = (float)Math.toRadians(30.0);
        this.lionModel.leg2.rotateAngleZ = (float)Math.toRadians(-90.0);
        this.lionModel.leg3.rotateAngleX = (float)Math.toRadians(-20.0);
        this.lionModel.leg3.rotateAngleZ = (float)Math.toRadians(90.0);
        this.lionModel.leg4.rotateAngleX = (float)Math.toRadians(-20.0);
        this.lionModel.leg4.rotateAngleZ = (float)Math.toRadians(-90.0);
    }
}
