// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.mapgen.tpyr;

import java.util.List;
import net.minecraft.world.gen.structure.StructureComponent;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;

public class LOTRStructureTPyrStart extends StructureStart
{
    public LOTRStructureTPyrStart() {
    }
    
    public LOTRStructureTPyrStart(final World world, final Random random, final int i, final int j) {
        final LOTRComponentTauredainPyramid startComponent = new LOTRComponentTauredainPyramid(world, 0, random, (i << 4) + 8, (j << 4) + 8);
        super.components.add(startComponent);
        startComponent.buildComponent(startComponent, super.components, random);
        this.updateBoundingBox();
    }
}
