// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import com.google.common.math.IntMath;
import net.minecraft.util.StatCollector;
import java.util.HashMap;
import java.util.Map;
import java.awt.Color;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketDate;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.FMLLog;
import lotr.common.world.LOTRWorldInfo;
import net.minecraft.world.World;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class LOTRDate
{
    private static int ticksInDay;
    private static long prevWorldTime;
    public static int SECOND_AGE_LENGTH;
    public static int THIRD_AGE_LENGTH;
    public static int SR_TO_TA;
    public static int THIRD_AGE_CURRENT;
    
    public static void saveDates(final NBTTagCompound levelData) {
        final NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("ShireDate", ShireReckoning.currentDay);
        levelData.setTag("Dates", (NBTBase)nbt);
    }
    
    public static void loadDates(final NBTTagCompound levelData) {
        if (levelData.hasKey("Dates")) {
            final NBTTagCompound nbt = levelData.getCompoundTag("Dates");
            ShireReckoning.currentDay = nbt.getInteger("ShireDate");
        }
        else {
            ShireReckoning.currentDay = 0;
        }
    }
    
    public static void resetWorldTimeInMenu() {
        LOTRDate.prevWorldTime = -1L;
    }
    
    public static void update(final World world) {
        if (!(world.getWorldInfo() instanceof LOTRWorldInfo)) {
            return;
        }
        final long worldTime = world.getWorldTime();
        if (LOTRDate.prevWorldTime == -1L) {
            LOTRDate.prevWorldTime = worldTime;
        }
        final long prevDay = LOTRDate.prevWorldTime / LOTRDate.ticksInDay;
        final long day = worldTime / LOTRDate.ticksInDay;
        if (day != prevDay) {
            setDate(ShireReckoning.currentDay + 1);
        }
        LOTRDate.prevWorldTime = worldTime;
    }
    
    public static void setDate(final int date) {
        ShireReckoning.currentDay = date;
        LOTRLevelData.markDirty();
        FMLLog.info("Updating LOTR day: " + ShireReckoning.getShireDate().getDateName(false), new Object[0]);
        for (final Object obj : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
            final EntityPlayerMP entityplayer = (EntityPlayerMP)obj;
            sendUpdatePacket(entityplayer, true);
        }
    }
    
    public static void sendUpdatePacket(final EntityPlayerMP entityplayer, final boolean update) {
        final NBTTagCompound nbt = new NBTTagCompound();
        saveDates(nbt);
        final LOTRPacketDate packet = new LOTRPacketDate(nbt, update);
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayer);
    }
    
    static {
        LOTRDate.ticksInDay = LOTRTime.DAY_LENGTH;
        LOTRDate.prevWorldTime = -1L;
        LOTRDate.SECOND_AGE_LENGTH = 3441;
        LOTRDate.THIRD_AGE_LENGTH = 3021;
        LOTRDate.SR_TO_TA = 1600;
        LOTRDate.THIRD_AGE_CURRENT = ShireReckoning.startDate.year + LOTRDate.SR_TO_TA;
    }
    
    public enum Season
    {
        SPRING("spring", 0, new float[] { 1.0f, 1.0f, 1.0f }), 
        SUMMER("summer", 1, new float[] { 1.15f, 1.15f, 0.9f }), 
        AUTUMN("autumn", 2, new float[] { 1.2f, 1.0f, 0.7f }), 
        WINTER("winter", 3, new float[] { 1.0f, 0.8f, 0.8f });
        
        public static Season[] allSeasons;
        private final String name;
        public final int seasonID;
        private final float[] grassRGB;
        
        private Season(final String s, final int i, final float[] f) {
            this.name = s;
            this.seasonID = i;
            this.grassRGB = f;
        }
        
        public String codeName() {
            return this.name;
        }
        
        public int transformColor(final int color) {
            final float[] rgb = new Color(color).getRGBColorComponents(null);
            float r = rgb[0];
            float g = rgb[1];
            float b = rgb[2];
            r = Math.min(r * this.grassRGB[0], 1.0f);
            g = Math.min(g * this.grassRGB[1], 1.0f);
            b = Math.min(b * this.grassRGB[2], 1.0f);
            return new Color(r, g, b).getRGB();
        }
        
        static {
            Season.allSeasons = new Season[] { Season.SPRING, Season.SUMMER, Season.AUTUMN, Season.WINTER };
        }
    }
    
    public static class ShireReckoning
    {
        public static Date startDate;
        public static int currentDay;
        private static Map<Integer, Date> cachedDates;
        
        public static boolean isLeapYear(final int year) {
            return year % 4 == 0 && year % 100 != 0;
        }
        
        public static Date getShireDate() {
            return getShireDate(ShireReckoning.currentDay);
        }
        
        public static Date getShireDate(final int day) {
            Date date = ShireReckoning.cachedDates.get(day);
            if (date == null) {
                date = ShireReckoning.startDate.copy();
                if (day < 0) {
                    for (int i = 0; i < -day; ++i) {
                        date = date.decrement();
                    }
                }
                else {
                    for (int i = 0; i < day; ++i) {
                        date = date.increment();
                    }
                }
                ShireReckoning.cachedDates.put(day, date);
            }
            return date;
        }
        
        public static Season getSeason() {
            return getShireDate().month.season;
        }
        
        static {
            ShireReckoning.startDate = new Date(1401, Month.HALIMATH, 22);
            ShireReckoning.currentDay = 0;
            ShireReckoning.cachedDates = new HashMap<Integer, Date>();
        }
        
        public enum Day
        {
            STERDAY("sterday"), 
            SUNDAY("sunday"), 
            MONDAY("monday"), 
            TREWSDAY("trewsday"), 
            HEVENSDAY("hevensday"), 
            MERSDAY("mersday"), 
            HIGHDAY("highday");
            
            private String name;
            
            private Day(final String s) {
                this.name = s;
            }
            
            public String getDayName() {
                return StatCollector.translateToLocal("lotr.date.shire.day." + this.name);
            }
        }
        
        public enum Month
        {
            YULE_2("yule2", 1, Season.WINTER), 
            AFTERYULE("afteryule", 30, Season.WINTER), 
            SOLMATH("solmath", 30, Season.WINTER), 
            RETHE("rethe", 30, Season.WINTER), 
            ASTRON("astron", 30, Season.SPRING), 
            THRIMIDGE("thrimidge", 30, Season.SPRING), 
            FORELITHE("forelithe", 30, Season.SPRING), 
            LITHE_1("lithe1", 1, Season.SPRING), 
            MIDYEARSDAY("midyearsday", 1, Season.SUMMER, false, false), 
            OVERLITHE("overlithe", 1, Season.SUMMER, false, true), 
            LITHE_2("lithe2", 1, Season.SUMMER), 
            AFTERLITHE("afterlithe", 30, Season.SUMMER), 
            WEDMATH("wedmath", 30, Season.SUMMER), 
            HALIMATH("halimath", 30, Season.SUMMER), 
            WINTERFILTH("winterfilth", 30, Season.AUTUMN), 
            BLOTMATH("blotmath", 30, Season.AUTUMN), 
            FOREYULE("foreyule", 30, Season.AUTUMN), 
            YULE_1("yule1", 1, Season.AUTUMN);
            
            private String name;
            public int days;
            public boolean hasWeekdayName;
            public boolean isLeapYear;
            public Season season;
            
            private Month(final String s, final int i, final Season se) {
                this(s, i, se, true, false);
            }
            
            private Month(final String s, final int i, final Season se, final boolean flag, final boolean flag1) {
                this.name = s;
                this.days = i;
                this.hasWeekdayName = flag;
                this.isLeapYear = flag1;
                this.season = se;
            }
            
            public String getMonthName() {
                return StatCollector.translateToLocal("lotr.date.shire.month." + this.name);
            }
            
            public boolean isSingleDay() {
                return this.days == 1;
            }
        }
        
        public static class Date
        {
            public final int year;
            public final Month month;
            public final int monthDate;
            private Day day;
            
            public Date(final int y, final Month m, final int d) {
                this.year = y;
                this.month = m;
                this.monthDate = d;
            }
            
            public String getDateName(final boolean longName) {
                final String[] dayYear = this.getDayAndYearNames(longName);
                return dayYear[0] + ", " + dayYear[1];
            }
            
            public String[] getDayAndYearNames(final boolean longName) {
                StringBuilder builder = new StringBuilder();
                if (this.month.hasWeekdayName) {
                    builder.append(this.getDay().getDayName());
                }
                builder.append(" ");
                if (!this.month.isSingleDay()) {
                    builder.append(this.monthDate);
                }
                builder.append(" ");
                builder.append(this.month.getMonthName());
                final String dateName = builder.toString();
                builder = new StringBuilder();
                if (longName) {
                    builder.append(StatCollector.translateToLocal("lotr.date.shire.long"));
                }
                else {
                    builder.append(StatCollector.translateToLocal("lotr.date.shire"));
                }
                builder.append(" ");
                builder.append(this.year);
                final String yearName = builder.toString();
                return new String[] { dateName, yearName };
            }
            
            public Day getDay() {
                if (!this.month.hasWeekdayName) {
                    return null;
                }
                if (this.day == null) {
                    int yearDay = 0;
                    for (int monthID = this.month.ordinal(), i = 0; i < monthID; ++i) {
                        final Month m = Month.values()[i];
                        if (m.hasWeekdayName) {
                            yearDay += m.days;
                        }
                    }
                    yearDay += this.monthDate;
                    final int dayID = IntMath.mod(yearDay - 1, Day.values().length);
                    this.day = Day.values()[dayID];
                }
                return this.day;
            }
            
            public Date copy() {
                return new Date(this.year, this.month, this.monthDate);
            }
            
            public Date increment() {
                int newYear = this.year;
                Month newMonth = this.month;
                int newDate = this.monthDate;
                if (++newDate > newMonth.days) {
                    newDate = 1;
                    int monthID = newMonth.ordinal();
                    if (++monthID >= Month.values().length) {
                        monthID = 0;
                        ++newYear;
                    }
                    newMonth = Month.values()[monthID];
                    if (newMonth.isLeapYear && !ShireReckoning.isLeapYear(newYear)) {
                        ++monthID;
                        newMonth = Month.values()[monthID];
                    }
                }
                return new Date(newYear, newMonth, newDate);
            }
            
            public Date decrement() {
                int newYear = this.year;
                Month newMonth = this.month;
                int newDate = this.monthDate;
                if (--newDate < 0) {
                    int monthID = newMonth.ordinal();
                    if (--monthID < 0) {
                        monthID = Month.values().length - 1;
                        --newYear;
                    }
                    newMonth = Month.values()[monthID];
                    if (newMonth.isLeapYear && !ShireReckoning.isLeapYear(newYear)) {
                        --monthID;
                        newMonth = Month.values()[monthID];
                    }
                    newDate = newMonth.days;
                }
                return new Date(newYear, newMonth, newDate);
            }
        }
    }
}
