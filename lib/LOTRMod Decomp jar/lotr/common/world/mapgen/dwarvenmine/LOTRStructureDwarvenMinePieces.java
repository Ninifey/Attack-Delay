// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.mapgen.dwarvenmine;

import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import java.util.Random;
import java.util.List;

public class LOTRStructureDwarvenMinePieces
{
    private static StructureComponent getRandomComponent(final List list, final Random random, final int i, final int j, final int k, final int direction, final int iteration, final boolean ruined) {
        final int l = random.nextInt(100);
        if (l >= 80) {
            final StructureBoundingBox structureboundingbox = LOTRComponentDwarvenMineCrossing.findValidPlacement(list, random, i, j, k, direction);
            if (structureboundingbox != null) {
                return new LOTRComponentDwarvenMineCrossing(iteration, random, structureboundingbox, direction, ruined);
            }
        }
        else if (l >= 70) {
            final StructureBoundingBox structureboundingbox = LOTRComponentDwarvenMineStairs.findValidPlacement(list, random, i, j, k, direction);
            if (structureboundingbox != null) {
                return new LOTRComponentDwarvenMineStairs(iteration, random, structureboundingbox, direction, ruined);
            }
        }
        else {
            final StructureBoundingBox structureboundingbox = LOTRComponentDwarvenMineCorridor.findValidPlacement(list, random, i, j, k, direction);
            if (structureboundingbox != null) {
                return new LOTRComponentDwarvenMineCorridor(iteration, random, structureboundingbox, direction, ruined);
            }
        }
        return null;
    }
    
    private static StructureComponent getNextMineComponent(final StructureComponent component, final List list, final Random random, final int i, final int j, final int k, final int direction, final int iteration, final boolean ruined) {
        if (iteration > 12) {
            return null;
        }
        if (Math.abs(i - component.getBoundingBox().minX) <= 80 && Math.abs(k - component.getBoundingBox().minZ) <= 80) {
            final StructureComponent structurecomponent1 = getRandomComponent(list, random, i, j, k, direction, iteration + 1, ruined);
            if (structurecomponent1 != null) {
                list.add(structurecomponent1);
                structurecomponent1.buildComponent(component, list, random);
            }
            return structurecomponent1;
        }
        return null;
    }
    
    public static StructureComponent getNextComponent(final StructureComponent component, final List list, final Random random, final int i, final int j, final int k, final int direction, final int iteration, final boolean ruined) {
        return getNextMineComponent(component, list, random, i, j, k, direction, iteration, ruined);
    }
}
