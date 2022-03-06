// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

public abstract class LOTRWorldGenUmbarFortWall extends LOTRWorldGenSouthronFortWall
{
    public LOTRWorldGenUmbarFortWall(final boolean flag) {
        super(flag);
    }
    
    public static class Short extends LOTRWorldGenSouthronFortWall.Short
    {
        public Short(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected boolean isUmbar() {
            return true;
        }
    }
    
    public static class Long extends LOTRWorldGenSouthronFortWall.Long
    {
        public Long(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected boolean isUmbar() {
            return true;
        }
    }
}
