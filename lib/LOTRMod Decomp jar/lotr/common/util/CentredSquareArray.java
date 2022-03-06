// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.util;

import java.util.Arrays;

public class CentredSquareArray<T>
{
    private int radius;
    private int width;
    private Object[] array;
    
    public CentredSquareArray(final int r) {
        this.radius = r;
        this.width = this.radius * 2 + 1;
        this.array = new Object[this.width * this.width];
    }
    
    private int getIndex(final int x, final int y) {
        return (y + this.radius) * this.width + (x + this.radius);
    }
    
    public T get(final int x, final int y) {
        final int index = this.getIndex(x, y);
        return (T)this.array[index];
    }
    
    public void set(final int x, final int y, final T val) {
        final int index = this.getIndex(x, y);
        this.array[index] = val;
    }
    
    public void fill(final T val) {
        Arrays.fill(this.array, val);
    }
}
