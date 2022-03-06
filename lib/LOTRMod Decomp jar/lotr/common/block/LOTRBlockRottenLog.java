// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.block.Block;

public class LOTRBlockRottenLog extends LOTRBlockWoodBase
{
    public LOTRBlockRottenLog() {
        this.setWoodNames("rotten");
    }
    
    public static boolean isRottenWood(final Block block) {
        return block instanceof LOTRBlockRottenLog;
    }
}
