// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import lotr.common.entity.item.LOTREntityLionRug;
import lotr.common.entity.item.LOTREntityRugBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemLionRug extends LOTRItemRugBase
{
    public LOTRItemLionRug() {
        super(LionRugType.lionRugNames());
    }
    
    @Override
    protected LOTREntityRugBase createRug(final World world, final ItemStack itemstack) {
        final LOTREntityLionRug rug = new LOTREntityLionRug(world);
        rug.setRugType(LionRugType.forID(itemstack.getItemDamage()));
        return rug;
    }
    
    public enum LionRugType
    {
        LION(0), 
        LIONESS(1);
        
        public final int lionID;
        
        private LionRugType(final int i) {
            this.lionID = i;
        }
        
        public String textureName() {
            return this.name().toLowerCase();
        }
        
        public static LionRugType forID(final int ID) {
            for (final LionRugType t : values()) {
                if (t.lionID == ID) {
                    return t;
                }
            }
            return LionRugType.LION;
        }
        
        public static String[] lionRugNames() {
            final String[] names = new String[values().length];
            for (int i = 0; i < names.length; ++i) {
                names[i] = values()[i].textureName();
            }
            return names;
        }
    }
}
