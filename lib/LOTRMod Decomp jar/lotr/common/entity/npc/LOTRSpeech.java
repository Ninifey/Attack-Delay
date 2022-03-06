// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.world.World;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketNPCSpeech;
import lotr.common.LOTRDrunkenSpeech;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;
import java.util.Collection;
import java.util.Enumeration;
import cpw.mods.fml.common.ModContainer;
import java.io.IOException;
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
import java.util.Random;
import java.util.Map;

public class LOTRSpeech
{
    private static Map<String, SpeechBank> allSpeechBanks;
    private static Random rand;
    
    public static void loadAllSpeechBanks() {
        final Map<String, BufferedReader> speechBankNamesAndReaders = new HashMap<String, BufferedReader>();
        ZipFile zip = null;
        try {
            final ModContainer mc = LOTRMod.getModContainer();
            if (mc.getSource().isFile()) {
                zip = new ZipFile(mc.getSource());
                final Enumeration entries = zip.entries();
                while (entries.hasMoreElements()) {
                    final ZipEntry entry = entries.nextElement();
                    String s = entry.getName();
                    final String path = "assets/lotr/speech/";
                    if (s.startsWith(path) && s.endsWith(".txt")) {
                        s = s.substring(path.length());
                        final int i = s.indexOf(".txt");
                        try {
                            s = s.substring(0, i);
                            final BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream)new BOMInputStream(zip.getInputStream(entry)), Charsets.UTF_8.name()));
                            speechBankNamesAndReaders.put(s, reader);
                        }
                        catch (Exception e) {
                            FMLLog.severe("Failed to load LOTR speech bank " + s + "from zip file", new Object[0]);
                            e.printStackTrace();
                        }
                    }
                }
            }
            else {
                final File speechBankDir = new File(LOTRMod.class.getResource("/assets/lotr/speech").toURI());
                final Collection<File> subfiles = (Collection<File>)FileUtils.listFiles(speechBankDir, (String[])null, true);
                for (final File subfile : subfiles) {
                    String s2 = subfile.getPath();
                    s2 = s2.substring(speechBankDir.getPath().length() + 1);
                    s2 = s2.replace(File.separator, "/");
                    final int j = s2.indexOf(".txt");
                    if (j < 0) {
                        FMLLog.severe("Failed to load LOTR speech bank " + s2 + " from MCP folder; speech bank files must be in .txt format", new Object[0]);
                    }
                    else {
                        try {
                            s2 = s2.substring(0, j);
                            final BufferedReader reader2 = new BufferedReader(new InputStreamReader((InputStream)new BOMInputStream((InputStream)new FileInputStream(subfile)), Charsets.UTF_8.name()));
                            speechBankNamesAndReaders.put(s2, reader2);
                        }
                        catch (Exception e2) {
                            FMLLog.severe("Failed to load LOTR speech bank " + s2 + " from MCP folder", new Object[0]);
                            e2.printStackTrace();
                        }
                    }
                }
            }
        }
        catch (Exception e3) {
            FMLLog.severe("Failed to load LOTR speech banks", new Object[0]);
            e3.printStackTrace();
        }
        for (final String speechBankName : speechBankNamesAndReaders.keySet()) {
            final BufferedReader reader3 = speechBankNamesAndReaders.get(speechBankName);
            try {
                final List<String> speeches = new ArrayList<String>();
                final List<String> allLines = new ArrayList<String>();
                boolean random = true;
                String line;
                while ((line = reader3.readLine()) != null) {
                    if (line.equals("!RANDOM")) {
                        random = false;
                    }
                    else {
                        speeches.add(line);
                    }
                    allLines.add(line);
                }
                reader3.close();
                if (speeches.isEmpty()) {
                    FMLLog.severe("LOTR speech bank " + speechBankName + " is empty!", new Object[0]);
                }
                else {
                    SpeechBank bank;
                    if (random) {
                        bank = new SpeechBank(speechBankName, random, speeches);
                    }
                    else {
                        bank = new SpeechBank(speechBankName, random, allLines);
                    }
                    LOTRSpeech.allSpeechBanks.put(speechBankName, bank);
                }
            }
            catch (Exception e4) {
                FMLLog.severe("Failed to load LOTR speech bank " + speechBankName, new Object[0]);
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
    
    private static SpeechBank getSpeechBank(final String name) {
        final SpeechBank bank = LOTRSpeech.allSpeechBanks.get(name);
        if (bank != null) {
            return bank;
        }
        return new SpeechBank("dummy_" + name, true, Arrays.asList("Speech bank " + name + " could not be found!"));
    }
    
    public static String getRandomSpeech(final String bankName) {
        return getSpeechBank(bankName).getRandomSpeech(LOTRSpeech.rand);
    }
    
    public static String getSpeechAtLine(final String bankName, final int i) {
        return getSpeechBank(bankName).getSpeechAtLine(i);
    }
    
    public static String getRandomSpeechForPlayer(final LOTREntityNPC entity, final String speechBankName, final EntityPlayer entityplayer) {
        return getRandomSpeechForPlayer(entity, speechBankName, entityplayer, null, null);
    }
    
    public static String getRandomSpeechForPlayer(final LOTREntityNPC entity, final String speechBankName, final EntityPlayer entityplayer, final String location, final String objective) {
        String s = getRandomSpeech(speechBankName);
        s = formatSpeech(s, entityplayer, location, objective);
        if (entity.isDrunkard()) {
            final float f = entity.getDrunkenSpeechFactor();
            s = LOTRDrunkenSpeech.getDrunkenSpeech(s, f);
        }
        return s;
    }
    
    public static String getSpeechLineForPlayer(final LOTREntityNPC entity, final String speechBankName, final int i, final EntityPlayer entityplayer) {
        return getSpeechLineForPlayer(entity, speechBankName, i, entityplayer, null, null);
    }
    
    public static String getSpeechLineForPlayer(final LOTREntityNPC entity, final String speechBankName, final int i, final EntityPlayer entityplayer, final String location, final String objective) {
        String s = getSpeechAtLine(speechBankName, i);
        s = formatSpeech(s, entityplayer, location, objective);
        if (entity.isDrunkard()) {
            final float f = entity.getDrunkenSpeechFactor();
            s = LOTRDrunkenSpeech.getDrunkenSpeech(s, f);
        }
        return s;
    }
    
    public static String formatSpeech(String speech, final EntityPlayer entityplayer, final String location, final String objective) {
        if (entityplayer != null) {
            speech = speech.replace("#", entityplayer.getCommandSenderName());
        }
        if (location != null) {
            speech = speech.replace("@", location);
        }
        if (objective != null) {
            speech = speech.replace("$", objective);
        }
        return speech;
    }
    
    public static void sendSpeech(final EntityPlayer entityplayer, final LOTREntityNPC entity, final String speech) {
        final LOTRPacketNPCSpeech packet = new LOTRPacketNPCSpeech(entity.getEntityId(), speech);
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
    }
    
    public static void sendSpeechAndChatMessage(final EntityPlayer entityplayer, final LOTREntityNPC entity, final String speechBankName) {
        final String name = entity.getCommandSenderName();
        final String speech = getRandomSpeechForPlayer(entity, speechBankName, entityplayer, null, null);
        final String message = EnumChatFormatting.YELLOW + "<" + name + ">" + EnumChatFormatting.WHITE + " " + speech;
        final IChatComponent component = (IChatComponent)new ChatComponentText(message);
        entityplayer.addChatMessage(component);
        sendSpeech(entityplayer, entity, speech);
    }
    
    public static void messageAllPlayers(final IChatComponent message) {
        if (MinecraftServer.getServer() == null) {
            return;
        }
        for (final Object player : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
            ((EntityPlayer)player).addChatMessage(message);
        }
    }
    
    public static void messageAllPlayersInWorld(final World world, final IChatComponent message) {
        for (final Object player : world.playerEntities) {
            ((EntityPlayer)player).addChatMessage(message);
        }
    }
    
    static {
        LOTRSpeech.allSpeechBanks = new HashMap<String, SpeechBank>();
        LOTRSpeech.rand = new Random();
    }
    
    private static class SpeechBank
    {
        public final String name;
        public final boolean isRandom;
        public final List<String> speeches;
        
        public SpeechBank(final String s, final boolean r, final List<String> spc) {
            this.name = s;
            this.isRandom = r;
            this.speeches = spc;
        }
        
        public String getRandomSpeech(final Random random) {
            if (!this.isRandom) {
                return "ERROR: Tried to retrieve random speech from non-random speech bank " + this.name;
            }
            String s = this.speeches.get(LOTRSpeech.rand.nextInt(this.speeches.size()));
            s = this.internalFormatSpeech(s);
            return s;
        }
        
        public String getSpeechAtLine(final int line) {
            if (this.isRandom) {
                return "ERROR: Tried to retrieve indexed speech from random speech bank " + this.name;
            }
            final int index = line - 1;
            if (index >= 0 && index < this.speeches.size()) {
                String s = this.speeches.get(index);
                s = this.internalFormatSpeech(s);
                return s;
            }
            return "ERROR: Speech line " + line + " is out of range!";
        }
        
        private String internalFormatSpeech(String s) {
            if (LOTRMod.isAprilFools() || LOTRSpeech.rand.nextInt(2000) == 0) {
                s = "Tbh, " + s.substring(0, 1).toLowerCase() + s.substring(1, s.length() - 1) + ", tbh.";
            }
            return s;
        }
    }
}
