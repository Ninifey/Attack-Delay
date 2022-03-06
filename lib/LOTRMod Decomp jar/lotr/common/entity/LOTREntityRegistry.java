// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity;

import lotr.common.fac.LOTRAlignmentValues;
import java.util.HashMap;
import cpw.mods.fml.common.FMLLog;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import org.apache.commons.io.input.BOMInputStream;
import java.io.FileInputStream;
import lotr.common.entity.npc.LOTREntityMordorOrc;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.File;
import lotr.common.fac.LOTRFaction;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import java.util.Map;

public class LOTREntityRegistry
{
    public static Map registeredNPCs;
    
    public static void loadRegisteredNPCs(final FMLPreInitializationEvent event) {
        final StringBuilder stringbuilder = new StringBuilder();
        for (final LOTRFaction faction : LOTRFaction.values()) {
            if (faction.allowEntityRegistry) {
                if (faction.ordinal() > 0) {
                    stringbuilder.append(", ");
                }
                stringbuilder.append(faction.codeName());
            }
        }
        final String allFactions = stringbuilder.toString();
        try {
            final File file = event.getModConfigurationDirectory();
            final File config = new File(file, "LOTR_EntityRegistry.txt");
            if (!config.exists()) {
                if (config.createNewFile()) {
                    final PrintStream writer = new PrintStream(new FileOutputStream(config));
                    writer.println("#Lines starting with '#' will be ignored");
                    writer.println("#");
                    writer.println("#Use this file to register entities with the LOTR alignment system.");
                    writer.println("#");
                    writer.println("#An example format for registering an entity is as follows: (do not use spaces)");
                    writer.println("#name=" + LOTREntities.getStringFromClass(LOTREntityMordorOrc.class) + ",faction=" + LOTRFaction.MORDOR.codeName() + ",targetEnemies=true,bonus=1");
                    writer.println("#");
                    writer.println("#'name' is the entity name, prefixed with the associated mod ID.");
                    writer.println("#The mod ID can be found in the Mod List on the main menu - for example, \"lotr\" for the LOTR mod.");
                    writer.println("#The entity name is not necessarily the in-game name. It is the name used to register the entity in the code.");
                    writer.println("#You may be able to discover the entity name in the mod's language file if there is one - otherwise, contact the mod author.");
                    writer.println("#The mod ID and entity name must be separated by a '.' character.");
                    writer.println("#Vanilla entities have no mod ID and therefore no prefix.");
                    writer.println("#");
                    writer.println("#'faction' can be " + allFactions);
                    writer.println("#");
                    writer.println("#'targetEnemies' can be true or false.");
                    writer.println("#If true, the entity will be equipped with AI modules to target its enemies.");
                    writer.println("#Actual combat behaviour may or may not be present, depending on whether the entity is designed with combat AI modules.");
                    writer.println("#");
                    writer.println("#'bonus' is the alignment bonus awarded to a player who kills the entity.");
                    writer.println("#It can be positive, negative, or zero, in which case no bonus will be awarded.");
                    writer.println("#");
                    writer.close();
                }
            }
            else {
                final BufferedReader bufferedreader = new BufferedReader(new InputStreamReader((InputStream)new BOMInputStream((InputStream)new FileInputStream(config))));
                String s = "";
                while ((s = bufferedreader.readLine()) != null) {
                    final String line = s;
                    if (s.startsWith("#")) {
                        continue;
                    }
                    LOTRFaction faction2 = null;
                    if (!s.startsWith("name=")) {
                        continue;
                    }
                    s = s.substring("name=".length());
                    if (s.toLowerCase().startsWith("lotr".toLowerCase())) {
                        continue;
                    }
                    final int i = s.indexOf(",faction=");
                    if (i < 0) {
                        continue;
                    }
                    final int j = s.indexOf(",targetEnemies=");
                    if (j < 0) {
                        continue;
                    }
                    final int k = s.indexOf(",bonus=");
                    if (k < 0) {
                        continue;
                    }
                    final String name = s.substring(0, i);
                    if (name.length() == 0) {
                        continue;
                    }
                    final String factionString = s.substring(i + ",faction=".length(), j);
                    for (final LOTRFaction f : LOTRFaction.values()) {
                        if (f.codeName().equals(factionString)) {
                            faction2 = f;
                            break;
                        }
                    }
                    if (faction2 == null) {
                        continue;
                    }
                    final String targetEnemiesString = s.substring(j + ",targetEnemies=".length(), k);
                    boolean targetEnemies;
                    if (targetEnemiesString.equals("true")) {
                        targetEnemies = true;
                    }
                    else {
                        if (!targetEnemiesString.equals("false")) {
                            continue;
                        }
                        targetEnemies = false;
                    }
                    final String bonusString = s.substring(k + ",bonus=".length());
                    final int bonus = Integer.parseInt(bonusString);
                    LOTREntityRegistry.registeredNPCs.put(name, new RegistryInfo(name, faction2, targetEnemies, bonus));
                    FMLLog.info("Successfully registered entity " + name + " with the LOTR alignment system as " + line, new Object[0]);
                }
                bufferedreader.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static {
        LOTREntityRegistry.registeredNPCs = new HashMap();
    }
    
    public static class RegistryInfo
    {
        public LOTRFaction alignmentFaction;
        public boolean shouldTargetEnemies;
        public LOTRAlignmentValues.AlignmentBonus alignmentBonus;
        
        public RegistryInfo(final String entityName, final LOTRFaction side, final boolean flag, final int bonus) {
            this.alignmentFaction = side;
            this.shouldTargetEnemies = flag;
            this.alignmentBonus = new LOTRAlignmentValues.AlignmentBonus((float)bonus, "entity." + entityName + ".name");
            this.alignmentBonus.needsTranslation = true;
        }
    }
}
