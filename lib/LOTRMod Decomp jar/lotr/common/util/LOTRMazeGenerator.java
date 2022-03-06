// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.util;

import java.util.List;
import net.minecraft.util.MathHelper;
import java.util.ArrayList;
import java.util.Random;

public class LOTRMazeGenerator
{
    public final int xSize;
    public final int zSize;
    private short[][] mazeFlags;
    private static final short FLAG_PATH = 1;
    private static final short FLAG_EXCLUDE = 2;
    private static final short FLAG_DEADEND = 4;
    private int startX;
    private int startZ;
    private int endX;
    private int endZ;
    private float windyness;
    private float branchingness;
    
    public LOTRMazeGenerator(final int x, final int z) {
        this.startX = -1;
        this.startZ = -1;
        this.endX = -1;
        this.endZ = -1;
        this.windyness = 0.3f;
        this.branchingness = 0.2f;
        this.xSize = x;
        this.zSize = z;
        this.setupMaze();
    }
    
    private void setupMaze() {
        this.mazeFlags = new short[this.xSize][this.zSize];
    }
    
    public void setStart(final int x, final int z) {
        this.startX = x;
        this.startZ = z;
    }
    
    public int[] getEnd() {
        return new int[] { this.endX, this.endZ };
    }
    
    public void setWindyness(final float f) {
        this.windyness = f;
    }
    
    public void clear(final int x, final int z) {
        this.setFlag(x, z, (short)1, true);
    }
    
    public void exclude(final int x, final int z) {
        this.setFlag(x, z, (short)2, true);
    }
    
    public boolean isPath(final int x, final int z) {
        return this.getFlag(x, z, (short)1);
    }
    
    public boolean isDeadEnd(final int x, final int z) {
        return this.getFlag(x, z, (short)4);
    }
    
    private void setFlag(final int x, final int z, final short flag, final boolean val) {
        if (val) {
            final short[] array = this.mazeFlags[x];
            array[z] |= flag;
        }
        else {
            final short[] array2 = this.mazeFlags[x];
            array2[z] &= (short)~flag;
        }
    }
    
    private boolean getFlag(final int x, final int z, final short flag) {
        return (this.mazeFlags[x][z] & flag) == flag;
    }
    
    public void generate(final Random random) {
        final List<MazePos> positions = new ArrayList<MazePos>();
        Dir lastDir = null;
        this.clear(this.startX, this.startZ);
        positions.add(new MazePos(this.startX, this.startZ));
        while (!positions.isEmpty()) {
            final int maxIndex = positions.size() - 1;
            final int randPosIndex = MathHelper.getRandomIntegerInRange(random, (int)(maxIndex * (1.0f - this.branchingness)), maxIndex);
            final MazePos pos = positions.get(randPosIndex);
            final List<Dir> validDirs = new ArrayList<Dir>();
            final Dir[] values = Dir.values();
            final int length = values.length;
            int j = 0;
        Label_0245_Outer:
            while (j < length) {
                final Dir dir = values[j];
                int l = 1;
                while (true) {
                    while (l <= 2) {
                        final int x = pos.xPos + dir.xDir * l;
                        final int z = pos.zPos + dir.zDir * l;
                        if (x >= 0 && x < this.xSize && z >= 0) {
                            if (z < this.zSize) {
                                if (!this.isPath(x, z)) {
                                    if (!this.getFlag(x, z, (short)2)) {
                                        ++l;
                                        continue Label_0245_Outer;
                                    }
                                }
                            }
                        }
                        ++j;
                        continue Label_0245_Outer;
                    }
                    validDirs.add(dir);
                    continue;
                }
            }
            if (!validDirs.isEmpty()) {
                Dir dir2;
                if (lastDir != null && validDirs.contains(lastDir) && random.nextFloat() >= this.windyness) {
                    dir2 = lastDir;
                }
                else {
                    dir2 = validDirs.get(random.nextInt(validDirs.size()));
                }
                int x2 = pos.xPos;
                int z2 = pos.zPos;
                if (this.getFlag(x2, z2, (short)4)) {
                    this.setFlag(x2, z2, (short)4, false);
                }
                for (int i = 0; i < 2; ++i) {
                    x2 += dir2.xDir;
                    z2 += dir2.zDir;
                    this.clear(x2, z2);
                }
                if (!this.getFlag(x2, z2, (short)4)) {
                    this.setFlag(x2, z2, (short)4, true);
                }
                positions.add(new MazePos(x2, z2));
                lastDir = dir2;
            }
            else {
                positions.remove(randPosIndex);
                lastDir = null;
            }
        }
    }
    
    public void selectOuterEndpoint(final Random random) {
        final int startXHalf = this.startX / (this.xSize / 2);
        final int startZHalf = this.startZ / (this.zSize / 2);
        int wx = 0;
        int wz = 0;
        while (true) {
            final List<MazePos> positions = new ArrayList<MazePos>();
            for (int x = 0; x < this.xSize; ++x) {
                for (int z = 0; z < this.zSize; ++z) {
                    final boolean outer = x == 0 + wx || x == this.xSize - 1 - wx || z == 0 + wz || z == this.zSize - 1 - wz;
                    if (outer && this.isPath(x, z)) {
                        final int xHalf = x / (this.xSize / 2);
                        final int zHalf = z / (this.zSize / 2);
                        if (startXHalf != xHalf && startZHalf != zHalf) {
                            positions.add(new MazePos(x, z));
                        }
                    }
                }
            }
            if (!positions.isEmpty()) {
                final MazePos pos = positions.get(random.nextInt(positions.size()));
                this.endX = pos.xPos;
                this.endZ = pos.zPos;
                return;
            }
            ++wx;
            ++wz;
            if (wx > this.xSize / 2 + 1 || wz > this.zSize / 2 + 1) {
                return;
            }
        }
    }
    
    private enum Dir
    {
        XNEG(-1, 0), 
        XPOS(1, 0), 
        ZNEG(0, -1), 
        ZPOS(0, 1);
        
        public final int xDir;
        public final int zDir;
        
        private Dir(final int x, final int z) {
            this.xDir = x;
            this.zDir = z;
        }
    }
    
    private static class MazePos
    {
        public int xPos;
        public int zPos;
        
        public MazePos(final int x, final int z) {
            this.xPos = x;
            this.zPos = z;
        }
    }
}
