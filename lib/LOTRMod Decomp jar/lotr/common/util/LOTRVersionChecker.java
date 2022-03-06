// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ChatComponentText;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class LOTRVersionChecker
{
    private static String versionURL;
    private static boolean checkedUpdate;
    
    public static void checkForUpdates() {
        if (!LOTRVersionChecker.checkedUpdate) {
            final Thread checkThread = new Thread("LOTR Update Checker") {
                @Override
                public void run() {
                    try {
                        final URL url = new URL(LOTRVersionChecker.versionURL);
                        final BufferedReader updateReader = new BufferedReader(new InputStreamReader(url.openStream()));
                        String updateVersion = "";
                        String line;
                        while ((line = updateReader.readLine()) != null) {
                            updateVersion = updateVersion.concat(line);
                        }
                        updateReader.close();
                        updateVersion = updateVersion.trim();
                        final String currentVersion = "Update v35.4 for Minecraft 1.7.10";
                        if (!updateVersion.equals(currentVersion)) {
                            final IChatComponent component = (IChatComponent)new ChatComponentText("The Lord of the Rings Mod:");
                            component.getChatStyle().setColor(EnumChatFormatting.YELLOW);
                            final EntityPlayer entityplayer = (EntityPlayer)Minecraft.getMinecraft().thePlayer;
                            if (entityplayer != null) {
                                entityplayer.addChatMessage((IChatComponent)new ChatComponentTranslation("chat.lotr.update", new Object[] { component, updateVersion }));
                            }
                        }
                    }
                    catch (Exception e) {
                        LOTRLog.logger.warn("LOTR: Version check failed");
                        e.printStackTrace();
                    }
                }
            };
            checkThread.setDaemon(LOTRVersionChecker.checkedUpdate = true);
            checkThread.start();
        }
    }
    
    static {
        LOTRVersionChecker.versionURL = "https://dl.dropboxusercontent.com/s/sidxw1dicl2nsev/version.txt";
        LOTRVersionChecker.checkedUpdate = false;
    }
}
