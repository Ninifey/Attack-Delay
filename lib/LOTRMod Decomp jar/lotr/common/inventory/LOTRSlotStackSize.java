// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

public class LOTRSlotStackSize implements Comparable
{
    public int slot;
    public int stackSize;
    
    public LOTRSlotStackSize(final int i, final int j) {
        this.slot = i;
        this.stackSize = j;
    }
    
    @Override
    public int compareTo(final Object obj) {
        if (obj instanceof LOTRSlotStackSize) {
            final LOTRSlotStackSize obj2 = (LOTRSlotStackSize)obj;
            if (obj2.stackSize < this.stackSize) {
                return 1;
            }
            if (obj2.stackSize > this.stackSize) {
                return -1;
            }
            if (obj2.stackSize == this.stackSize) {
                if (obj2.slot < this.slot) {
                    return 1;
                }
                if (obj2.slot > this.slot) {
                    return -1;
                }
            }
        }
        return 0;
    }
}
