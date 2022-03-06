// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.command;

import java.util.List;
import net.minecraft.world.World;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.init.Blocks;
import lotr.common.world.structure2.scan.LOTRStructureScan;
import net.minecraft.util.MathHelper;
import net.minecraft.command.WrongUsageException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import java.util.HashMap;
import org.apache.commons.lang3.tuple.Pair;
import net.minecraft.block.Block;
import java.util.Map;
import net.minecraft.command.CommandBase;

public class LOTRCommandStrScan extends CommandBase
{
    private boolean scanning;
    private Map<Block, String> blockAliases;
    private Map<Pair<Block, Integer>, String> blockMetaAliases;
    private int originX;
    private int originY;
    private int originZ;
    private int minX;
    private int minY;
    private int minZ;
    private int maxX;
    private int maxY;
    private int maxZ;
    
    public LOTRCommandStrScan() {
        this.scanning = false;
        this.blockAliases = new HashMap<Block, String>();
        this.blockMetaAliases = new HashMap<Pair<Block, Integer>, String>();
    }
    
    public String getCommandName() {
        return "strscan";
    }
    
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "development command";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) {
        if (args.length >= 1) {
            final String option = args[0];
            if (option.equals("begin")) {
                if (!this.scanning) {
                    this.scanning = true;
                    this.blockAliases.clear();
                    this.blockMetaAliases.clear();
                    func_152373_a(sender, (ICommand)this, "Begun scanning", new Object[0]);
                    return;
                }
                throw new WrongUsageException("Already begun scanning", new Object[0]);
            }
            else if (option.equals("assoc") && args.length >= 3 && this.scanning) {
                final String blockID = args[1];
                final Block block = Block.getBlockFromName(blockID);
                if (block == null) {
                    throw new WrongUsageException("Block %s does not exist", new Object[] { blockID });
                }
                final String alias = args[2];
                if (!this.blockAliases.containsValue(alias)) {
                    this.blockAliases.put(block, alias);
                    func_152373_a(sender, (ICommand)this, "Associated block %s to alias %s", new Object[] { blockID, alias });
                    return;
                }
                throw new WrongUsageException("Alias %s already used", new Object[] { alias });
            }
            else if (option.equals("assoc_meta") && args.length >= 4 && this.scanning) {
                final String blockID = args[1];
                final Block block = Block.getBlockFromName(blockID);
                if (block == null) {
                    throw new WrongUsageException("Block %s does not exist", new Object[] { blockID });
                }
                final int meta = parseInt(sender, args[2]);
                if (meta < 0 || meta > 15) {
                    throw new WrongUsageException("Invalid metadata value %s", new Object[] { meta });
                }
                final String alias2 = args[3];
                if (!this.blockMetaAliases.containsValue(alias2)) {
                    this.blockMetaAliases.put((Pair<Block, Integer>)Pair.of((Object)block, (Object)meta), alias2);
                    func_152373_a(sender, (ICommand)this, "Associated block %s and metadata %s to alias %s", new Object[] { blockID, meta, alias2 });
                    return;
                }
                throw new WrongUsageException("Alias %s already used", new Object[] { alias2 });
            }
            else {
                if (option.equals("origin") && args.length >= 4 && this.scanning) {
                    final ChunkCoordinates coords = sender.getCommandSenderPosition();
                    int i = coords.posX;
                    int j = coords.posY;
                    int k = coords.posZ;
                    i = MathHelper.floor_double(func_110666_a(sender, (double)i, args[1]));
                    j = MathHelper.floor_double(func_110666_a(sender, (double)j, args[2]));
                    k = MathHelper.floor_double(func_110666_a(sender, (double)k, args[3]));
                    final int minX = i;
                    this.originX = minX;
                    this.maxX = minX;
                    this.minX = minX;
                    final int minY = j;
                    this.originY = minY;
                    this.maxY = minY;
                    this.minY = minY;
                    final int minZ = k;
                    this.originZ = minZ;
                    this.maxZ = minZ;
                    this.minZ = minZ;
                    func_152373_a(sender, (ICommand)this, "Set scan origin to %s %s %s", new Object[] { this.originX, this.originY, this.originZ });
                    return;
                }
                if (option.equals("expand") && args.length >= 4 && this.scanning) {
                    final ChunkCoordinates coords = sender.getCommandSenderPosition();
                    int i = coords.posX;
                    int j = coords.posY;
                    int k = coords.posZ;
                    i = MathHelper.floor_double(func_110666_a(sender, (double)i, args[1]));
                    j = MathHelper.floor_double(func_110666_a(sender, (double)j, args[2]));
                    k = MathHelper.floor_double(func_110666_a(sender, (double)k, args[3]));
                    this.minX = Math.min(i, this.minX);
                    this.minY = Math.min(j, this.minY);
                    this.minZ = Math.min(k, this.minZ);
                    this.maxX = Math.max(i, this.maxX);
                    this.maxY = Math.max(j, this.maxY);
                    this.maxZ = Math.max(k, this.maxZ);
                    func_152373_a(sender, (ICommand)this, "Expanded scan region to include %s %s %s", new Object[] { i, j, k });
                    return;
                }
                if (option.equals("scan") && args.length >= 2 && this.scanning) {
                    final String scanName = args[1];
                    final LOTRStructureScan scan = new LOTRStructureScan(scanName);
                    final World world = sender.getEntityWorld();
                    for (int l = this.minY; l <= this.maxY; ++l) {
                        for (int m = this.minZ; m <= this.maxZ; ++m) {
                            for (int i2 = this.minX; i2 <= this.maxX; ++i2) {
                                final int i3 = i2 - this.originX;
                                final int j2 = l - this.originY;
                                final int k2 = m - this.originZ;
                                final Block block2 = world.getBlock(i2, l, m);
                                final int meta2 = world.getBlockMetadata(i2, l, m);
                                boolean fillBelow = false;
                                if (block2 != Blocks.air) {
                                    if (block2 != Blocks.bedrock) {
                                        if (world.getBlock(i2, l - 1, m) == Blocks.bedrock) {
                                            fillBelow = true;
                                        }
                                        LOTRStructureScan.ScanStepBase step = null;
                                        if (this.blockMetaAliases.containsKey(Pair.of((Object)block2, (Object)meta2))) {
                                            final String alias3 = this.blockMetaAliases.get(Pair.of((Object)block2, (Object)meta2));
                                            step = new LOTRStructureScan.ScanStepBlockMetaAlias(i3, j2, k2, alias3);
                                            scan.includeBlockMetaAlias(alias3);
                                        }
                                        else if (this.blockAliases.containsKey(block2)) {
                                            final String alias3 = this.blockAliases.get(block2);
                                            step = new LOTRStructureScan.ScanStepBlockAlias(i3, j2, k2, alias3, meta2);
                                            scan.includeBlockAlias(alias3);
                                        }
                                        else {
                                            step = new LOTRStructureScan.ScanStep(i3, j2, k2, block2, meta2);
                                        }
                                        if (step != null) {
                                            step.fillDown = fillBelow;
                                            scan.addScanStep(step);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (LOTRStructureScan.writeScanToFile(scan)) {
                        this.scanning = false;
                        func_152373_a(sender, (ICommand)this, "Scanned structure as %s", new Object[] { scanName });
                        return;
                    }
                    throw new WrongUsageException("Error scanning structure as %s", new Object[] { scanName });
                }
            }
        }
        throw new WrongUsageException(this.getCommandUsage(sender), new Object[0]);
    }
    
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args) {
        return null;
    }
    
    public boolean isUsernameIndex(final String[] args, final int i) {
        return false;
    }
}
