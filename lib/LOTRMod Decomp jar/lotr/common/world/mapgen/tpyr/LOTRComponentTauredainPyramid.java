// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.mapgen.tpyr;

import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.World;
import java.util.Random;
import lotr.common.world.structure2.LOTRWorldGenTauredainPyramid;
import net.minecraft.world.gen.structure.StructureComponent;

public class LOTRComponentTauredainPyramid extends StructureComponent
{
    private int posX;
    private int posY;
    private int posZ;
    private static LOTRWorldGenTauredainPyramid pyramidGen;
    private int direction;
    private static Random pyramidRand;
    private long pyramidSeed;
    
    public LOTRComponentTauredainPyramid() {
        this.posY = -1;
        this.pyramidSeed = -1L;
    }
    
    public LOTRComponentTauredainPyramid(final World world, final int l, final Random random, final int i, final int k) {
        super(l);
        this.posY = -1;
        this.pyramidSeed = -1L;
        final int r = LOTRWorldGenTauredainPyramid.RADIUS + 5;
        super.boundingBox = new StructureBoundingBox(i - r, 0, k - r, i + r, 255, k + r);
        this.posX = i;
        this.posZ = k;
        this.direction = random.nextInt(4);
    }
    
    protected void func_143012_a(final NBTTagCompound nbt) {
        nbt.setInteger("PyrX", this.posX);
        nbt.setInteger("PyrY", this.posY);
        nbt.setInteger("PyrZ", this.posZ);
        nbt.setInteger("Direction", this.direction);
        nbt.setLong("Seed", this.pyramidSeed);
    }
    
    protected void func_143011_b(final NBTTagCompound nbt) {
        this.posX = nbt.getInteger("PyrX");
        this.posY = nbt.getInteger("PyrY");
        this.posZ = nbt.getInteger("PyrZ");
        this.direction = nbt.getInteger("Direction");
        this.pyramidSeed = nbt.getLong("Seed");
    }
    
    public void buildComponent(final StructureComponent component, final List list, final Random random) {
    }
    
    public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        if (this.posY == -1) {
            this.posY = world.getTopSolidOrLiquidBlock(structureBoundingBox.getCenterX(), structureBoundingBox.getCenterZ());
        }
        if (this.pyramidSeed == -1L) {
            this.pyramidSeed = random.nextLong();
        }
        LOTRComponentTauredainPyramid.pyramidGen.setStructureBB(structureBoundingBox);
        LOTRComponentTauredainPyramid.pyramidRand.setSeed(this.pyramidSeed);
        LOTRComponentTauredainPyramid.pyramidGen.generateWithSetRotation(world, LOTRComponentTauredainPyramid.pyramidRand, this.posX, this.posY, this.posZ, this.direction);
        return true;
    }
    
    static {
        LOTRComponentTauredainPyramid.pyramidGen = new LOTRWorldGenTauredainPyramid(false);
        LOTRComponentTauredainPyramid.pyramidGen.restrictions = false;
        LOTRComponentTauredainPyramid.pyramidRand = new Random();
    }
}
