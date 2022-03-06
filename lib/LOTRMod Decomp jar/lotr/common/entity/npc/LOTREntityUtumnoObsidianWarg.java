// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.world.World;

public class LOTREntityUtumnoObsidianWarg extends LOTREntityUtumnoWarg
{
    public LOTREntityUtumnoObsidianWarg(final World world) {
        super(world);
    }
    
    public void entityInit() {
        super.entityInit();
        this.setWargType(WargType.OBSIDIAN);
    }
}
