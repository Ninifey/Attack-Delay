// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

public abstract class LOTRWorldGenUmbarTownWall extends LOTRWorldGenSouthronTownWall
{
    public LOTRWorldGenUmbarTownWall(final boolean flag) {
        super(flag);
    }
    
    public static class Short extends LOTRWorldGenSouthronTownWall.Short
    {
        public Short(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected boolean isUmbar() {
            return true;
        }
    }
    
    public static class Long extends LOTRWorldGenSouthronTownWall.Long
    {
        public Long(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected boolean isUmbar() {
            return true;
        }
    }
    
    public static class SideMid extends LOTRWorldGenSouthronTownWall.SideMid
    {
        public SideMid(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected boolean isUmbar() {
            return true;
        }
    }
    
    public static class Extra extends LOTRWorldGenSouthronTownWall.Extra
    {
        public Extra(final boolean flag) {
            super(flag);
        }
        
        @Override
        protected boolean isUmbar() {
            return true;
        }
    }
}
