// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2.scan;

import lotr.common.util.LOTRLog;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.FileOutputStream;
import net.minecraftforge.common.DimensionManager;
import java.util.Set;
import java.util.Iterator;
import java.util.Collection;
import java.util.Enumeration;
import cpw.mods.fml.common.ModContainer;
import java.io.IOException;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import java.util.HashSet;
import java.util.ArrayList;
import java.io.FileInputStream;
import org.apache.commons.io.FileUtils;
import java.io.File;
import cpw.mods.fml.common.FMLLog;
import java.io.Reader;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.google.common.base.Charsets;
import org.apache.commons.io.input.BOMInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import lotr.common.LOTRMod;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LOTRStructureScan
{
    private static final String strscanFormat = ".strscan";
    private static Map<String, LOTRStructureScan> allLoadedScans;
    public final String scanName;
    public final List<ScanStepBase> scanSteps;
    public final List<String> blockAliases;
    public final List<String> blockMetaAliases;
    
    public static void loadAllScans() {
        LOTRStructureScan.allLoadedScans.clear();
        final Map<String, BufferedReader> scanNamesAndReaders = new HashMap<String, BufferedReader>();
        ZipFile zip = null;
        try {
            final ModContainer mc = LOTRMod.getModContainer();
            if (mc.getSource().isFile()) {
                zip = new ZipFile(mc.getSource());
                final Enumeration entries = zip.entries();
                while (entries.hasMoreElements()) {
                    final ZipEntry entry = entries.nextElement();
                    String s = entry.getName();
                    final String path = "assets/lotr/strscan/";
                    if (s.startsWith(path) && s.endsWith(".strscan")) {
                        s = s.substring(path.length());
                        final int i = s.indexOf(".strscan");
                        try {
                            s = s.substring(0, i);
                            final BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream)new BOMInputStream(zip.getInputStream(entry)), Charsets.UTF_8.name()));
                            scanNamesAndReaders.put(s, reader);
                        }
                        catch (Exception e) {
                            FMLLog.severe("Failed to load LOTR structure scan " + s + "from zip file", new Object[0]);
                            e.printStackTrace();
                        }
                    }
                }
            }
            else {
                final File scanDir = new File(LOTRMod.class.getResource("/assets/lotr/strscan").toURI());
                final Collection<File> subfiles = (Collection<File>)FileUtils.listFiles(scanDir, (String[])null, true);
                for (final File subfile : subfiles) {
                    String s2 = subfile.getPath();
                    s2 = s2.substring(scanDir.getPath().length() + 1);
                    s2 = s2.replace(File.separator, "/");
                    final int j = s2.indexOf(".strscan");
                    if (j < 0) {
                        FMLLog.severe("Failed to load LOTR structure scan " + s2 + " from MCP folder - not in " + ".strscan" + " format", new Object[0]);
                    }
                    else {
                        try {
                            s2 = s2.substring(0, j);
                            final BufferedReader reader2 = new BufferedReader(new InputStreamReader((InputStream)new BOMInputStream((InputStream)new FileInputStream(subfile)), Charsets.UTF_8.name()));
                            scanNamesAndReaders.put(s2, reader2);
                        }
                        catch (Exception e2) {
                            FMLLog.severe("Failed to load LOTR structure scan " + s2 + " from MCP folder", new Object[0]);
                            e2.printStackTrace();
                        }
                    }
                }
            }
        }
        catch (Exception e3) {
            FMLLog.severe("Failed to load LOTR structure scans", new Object[0]);
            e3.printStackTrace();
        }
        for (final String strName : scanNamesAndReaders.keySet()) {
            final BufferedReader reader3 = scanNamesAndReaders.get(strName);
            int curLine = 0;
            try {
                final List<String> lines = new ArrayList<String>();
                String nextLine;
                while ((nextLine = reader3.readLine()) != null) {
                    lines.add(nextLine);
                }
                reader3.close();
                if (lines.isEmpty()) {
                    FMLLog.severe("LOTR structure scans " + strName + " is empty!", new Object[0]);
                }
                else {
                    final LOTRStructureScan scan = new LOTRStructureScan(strName);
                    final Set<String> blockAliases = new HashSet<String>();
                    final Set<String> blockMetaAliases = new HashSet<String>();
                    for (final String line : lines) {
                        ++curLine;
                        if (line.length() == 0) {
                            continue;
                        }
                        if (line.startsWith("#")) {
                            final String s3 = line.substring(1, line.length() - 1);
                            blockAliases.add(s3);
                        }
                        else if (line.startsWith("~")) {
                            final String s3 = line.substring(1, line.length() - 1);
                            blockMetaAliases.add(s3);
                        }
                        else {
                            int k = 0;
                            int l = line.indexOf(".");
                            String s4 = line.substring(k, l);
                            final int x = Integer.parseInt(s4);
                            ScanStepBase step = null;
                            boolean fillDown = false;
                            boolean findLowest = false;
                            k = l + 1;
                            l = line.indexOf(".", k);
                            s4 = line.substring(k, l);
                            if (s4.endsWith("v")) {
                                fillDown = true;
                                s4 = s4.substring(0, s4.length() - 1);
                            }
                            else if (s4.endsWith("_")) {
                                findLowest = true;
                                s4 = s4.substring(0, s4.length() - 1);
                            }
                            final int y = Integer.parseInt(s4);
                            k = l + 1;
                            l = line.indexOf(".", k);
                            s4 = line.substring(k, l);
                            final int z = Integer.parseInt(s4);
                            k = l + 1;
                            final char c = line.charAt(k);
                            if (c == '\"') {
                                l = line.indexOf("\"", k + 1);
                                s4 = line.substring(k, l + 1);
                                final String blockID;
                                s4 = (blockID = s4.substring(1, s4.length() - 1));
                                Block block = Block.getBlockFromName(blockID);
                                if (block == null) {
                                    FMLLog.severe("LOTRStrScan: Block " + blockID + " does not exist!", new Object[0]);
                                    block = Blocks.stone;
                                }
                                k = l + 2;
                                l = line.length();
                                s4 = line.substring(k, l);
                                final int meta = Integer.parseInt(s4);
                                step = new ScanStep(x, y, z, block, meta);
                            }
                            else if (c == '#') {
                                l = line.indexOf("#", k + 1);
                                s4 = line.substring(k, l + 1);
                                final String alias;
                                s4 = (alias = s4.substring(1, s4.length() - 1));
                                k = l + 2;
                                l = line.length();
                                s4 = line.substring(k, l);
                                final int meta2 = Integer.parseInt(s4);
                                step = new ScanStepBlockAlias(x, y, z, alias, meta2);
                            }
                            else if (c == '~') {
                                l = line.indexOf("~", k + 1);
                                s4 = line.substring(k, l + 1);
                                final String alias;
                                s4 = (alias = s4.substring(1, s4.length() - 1));
                                step = new ScanStepBlockMetaAlias(x, y, z, alias);
                            }
                            else if (c == '/') {
                                l = line.indexOf("/", k + 1);
                                s4 = line.substring(k, l + 1);
                                final String code;
                                s4 = (code = s4.substring(1, s4.length() - 1));
                                if (code.equals("SKULL")) {
                                    step = new ScanStepSkull(x, y, z);
                                }
                            }
                            if (step == null) {
                                throw new IllegalArgumentException("Invalid scan instruction on line " + curLine);
                            }
                            step.fillDown = fillDown;
                            step.findLowest = findLowest;
                            scan.addScanStep(step);
                        }
                    }
                    LOTRStructureScan.allLoadedScans.put(scan.scanName, scan);
                }
            }
            catch (Exception e4) {
                FMLLog.severe("Failed to load LOTR structure scan " + strName + ": error on line " + curLine, new Object[0]);
                e4.printStackTrace();
            }
        }
        if (zip != null) {
            try {
                zip.close();
            }
            catch (IOException e5) {
                e5.printStackTrace();
            }
        }
    }
    
    public static boolean writeScanToFile(final LOTRStructureScan scan) {
        final File dir = new File(DimensionManager.getCurrentSaveRootDirectory(), "lotr_str_scans");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final File scanFile = new File(dir, scan.scanName + ".strscan");
        try {
            if (!scanFile.exists()) {
                scanFile.createNewFile();
            }
            final PrintStream writer = new PrintStream(new FileOutputStream(scanFile));
            if (!scan.blockAliases.isEmpty() || !scan.blockMetaAliases.isEmpty()) {
                for (final String alias : scan.blockAliases) {
                    writer.println("#" + alias + "#");
                }
                for (final String alias : scan.blockMetaAliases) {
                    writer.println("~" + alias + "~");
                }
                writer.println();
            }
            for (final ScanStepBase e : scan.scanSteps) {
                writer.print(e.x);
                writer.print(".");
                writer.print(e.y);
                if (e.fillDown) {
                    writer.print("v");
                }
                writer.print(".");
                writer.print(e.z);
                writer.print(".");
                if (e instanceof ScanStep) {
                    final ScanStep step = (ScanStep)e;
                    writer.print("\"");
                    String blockName = Block.blockRegistry.getNameForObject((Object)step.block);
                    if (blockName.startsWith("minecraft:")) {
                        blockName = blockName.substring("minecraft:".length());
                    }
                    writer.print(blockName);
                    writer.print("\"");
                    writer.print(".");
                    writer.print(step.meta);
                    writer.println();
                }
                else if (e instanceof ScanStepBlockAlias) {
                    final ScanStepBlockAlias step2 = (ScanStepBlockAlias)e;
                    writer.print("#");
                    writer.print(step2.alias);
                    writer.print("#");
                    writer.print(".");
                    writer.print(step2.meta);
                    writer.println();
                }
                else {
                    if (!(e instanceof ScanStepBlockMetaAlias)) {
                        continue;
                    }
                    final ScanStepBlockMetaAlias step3 = (ScanStepBlockMetaAlias)e;
                    writer.print("~");
                    writer.print(step3.alias);
                    writer.print("~");
                    writer.println();
                }
            }
            writer.close();
            return true;
        }
        catch (Exception e2) {
            LOTRLog.logger.error("Error saving strscan file " + scan.scanName);
            e2.printStackTrace();
            return false;
        }
    }
    
    public static LOTRStructureScan getScanByName(final String name) {
        return LOTRStructureScan.allLoadedScans.get(name);
    }
    
    public LOTRStructureScan(final String name) {
        this.scanSteps = new ArrayList<ScanStepBase>();
        this.blockAliases = new ArrayList<String>();
        this.blockMetaAliases = new ArrayList<String>();
        this.scanName = name;
    }
    
    public void addScanStep(final ScanStepBase e) {
        this.scanSteps.add(e);
    }
    
    public void includeBlockAlias(final String alias) {
        for (final String s : this.blockAliases) {
            if (s.equals(alias)) {
                return;
            }
        }
        this.blockAliases.add(alias);
    }
    
    public void includeBlockMetaAlias(final String alias) {
        for (final String s : this.blockMetaAliases) {
            if (s.equals(alias)) {
                return;
            }
        }
        this.blockMetaAliases.add(alias);
    }
    
    static {
        LOTRStructureScan.allLoadedScans = new HashMap<String, LOTRStructureScan>();
    }
    
    public abstract static class ScanStepBase
    {
        public final int x;
        public final int y;
        public final int z;
        public boolean fillDown;
        public boolean findLowest;
        
        public ScanStepBase(final int _x, final int _y, final int _z) {
            this.fillDown = false;
            this.findLowest = false;
            this.x = _x;
            this.y = _y;
            this.z = _z;
        }
        
        public abstract boolean hasAlias();
        
        public abstract String getAlias();
        
        public abstract Block getBlock(final Block p0);
        
        public abstract int getMeta(final int p0);
    }
    
    public static class ScanStep extends ScanStepBase
    {
        public final Block block;
        public final int meta;
        
        public ScanStep(final int _x, final int _y, final int _z, final Block _block, final int _meta) {
            super(_x, _y, _z);
            this.block = _block;
            this.meta = _meta;
        }
        
        @Override
        public boolean hasAlias() {
            return false;
        }
        
        @Override
        public String getAlias() {
            return null;
        }
        
        @Override
        public Block getBlock(final Block aliasBlock) {
            return this.block;
        }
        
        @Override
        public int getMeta(final int aliasMeta) {
            return this.meta;
        }
    }
    
    public static class ScanStepBlockAlias extends ScanStepBase
    {
        public final String alias;
        public final int meta;
        
        public ScanStepBlockAlias(final int _x, final int _y, final int _z, final String _alias, final int _meta) {
            super(_x, _y, _z);
            this.alias = _alias;
            this.meta = _meta;
        }
        
        @Override
        public boolean hasAlias() {
            return true;
        }
        
        @Override
        public String getAlias() {
            return this.alias;
        }
        
        @Override
        public Block getBlock(final Block aliasBlock) {
            return aliasBlock;
        }
        
        @Override
        public int getMeta(final int aliasMeta) {
            return this.meta;
        }
    }
    
    public static class ScanStepBlockMetaAlias extends ScanStepBase
    {
        public final String alias;
        
        public ScanStepBlockMetaAlias(final int _x, final int _y, final int _z, final String _alias) {
            super(_x, _y, _z);
            this.alias = _alias;
        }
        
        @Override
        public boolean hasAlias() {
            return true;
        }
        
        @Override
        public String getAlias() {
            return this.alias;
        }
        
        @Override
        public Block getBlock(final Block aliasBlock) {
            return aliasBlock;
        }
        
        @Override
        public int getMeta(final int aliasMeta) {
            return aliasMeta;
        }
    }
    
    public static class ScanStepSkull extends ScanStepBase
    {
        public ScanStepSkull(final int _x, final int _y, final int _z) {
            super(_x, _y, _z);
        }
        
        @Override
        public boolean hasAlias() {
            return false;
        }
        
        @Override
        public String getAlias() {
            return null;
        }
        
        @Override
        public Block getBlock(final Block aliasBlock) {
            return Blocks.skull;
        }
        
        @Override
        public int getMeta(final int aliasMeta) {
            return 1;
        }
    }
}
