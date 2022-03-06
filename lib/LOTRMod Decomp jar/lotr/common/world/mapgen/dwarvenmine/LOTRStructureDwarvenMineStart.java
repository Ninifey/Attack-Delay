// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.mapgen.dwarvenmine;

import java.util.List;
import net.minecraft.world.gen.structure.StructureComponent;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;

public class LOTRStructureDwarvenMineStart extends StructureStart
{
    public LOTRStructureDwarvenMineStart() {
    }
    
    public LOTRStructureDwarvenMineStart(final World world, final Random random, final int i, final int j, final boolean r) {
        final LOTRComponentDwarvenMineEntrance startComponent = new LOTRComponentDwarvenMineEntrance(world, 0, random, (i << 4) + 8, (j << 4) + 8, r);
        super.components.add(startComponent);
        startComponent.buildComponent(startComponent, super.components, random);
        this.updateBoundingBox();
    }
}
