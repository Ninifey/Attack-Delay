// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import java.util.HashMap;
import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBiped;
import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityHaradSlave;
import java.util.Map;

public class LOTRRenderHaradSlave extends LOTRRenderBiped
{
    private static Map<LOTREntityHaradSlave.SlaveType, LOTRRandomSkins> slaveSkinsMale;
    private static Map<LOTREntityHaradSlave.SlaveType, LOTRRandomSkins> slaveSkinsFemale;
    
    public LOTRRenderHaradSlave() {
        super(new LOTRModelHuman(), 0.5f);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityHaradSlave slave = (LOTREntityHaradSlave)entity;
        final boolean isMale = slave.familyInfo.isMale();
        final LOTREntityHaradSlave.SlaveType type = slave.getSlaveType();
        final String skinDir = "lotr:mob/nearHarad/slave/" + type.skinDir + "_" + (isMale ? "male" : "female");
        final LOTRRandomSkins skins = LOTRRandomSkins.loadSkinsList(skinDir);
        return skins.getRandomSkin(slave);
    }
    
    static {
        LOTRRenderHaradSlave.slaveSkinsMale = new HashMap<LOTREntityHaradSlave.SlaveType, LOTRRandomSkins>();
        LOTRRenderHaradSlave.slaveSkinsFemale = new HashMap<LOTREntityHaradSlave.SlaveType, LOTRRandomSkins>();
    }
}
