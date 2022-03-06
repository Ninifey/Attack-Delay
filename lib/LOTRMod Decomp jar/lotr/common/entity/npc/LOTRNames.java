// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.LOTRDate;
import java.util.Arrays;
import java.util.Random;
import java.util.List;
import java.util.Iterator;
import java.util.Enumeration;
import cpw.mods.fml.common.ModContainer;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.File;
import cpw.mods.fml.common.FMLLog;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.google.common.base.Charsets;
import org.apache.commons.io.input.BOMInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import lotr.common.LOTRMod;
import java.util.HashMap;
import java.util.Map;

public class LOTRNames
{
    private static Map<String, String[]> allNameBanks;
    
    public static void loadAllNameBanks() {
        final Map nameBankNamesAndReaders = new HashMap();
        ZipFile zip = null;
        try {
            final ModContainer mc = LOTRMod.getModContainer();
            if (mc.getSource().isFile()) {
                zip = new ZipFile(mc.getSource());
                final Enumeration entries = zip.entries();
                while (entries.hasMoreElements()) {
                    final ZipEntry entry = entries.nextElement();
                    String s = entry.getName();
                    final String path = "assets/lotr/names/";
                    if (s.startsWith(path) && s.endsWith(".txt")) {
                        s = s.substring(path.length());
                        final int i = s.indexOf(".txt");
                        try {
                            s = s.substring(0, i);
                            final BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream)new BOMInputStream(zip.getInputStream(entry)), Charsets.UTF_8.name()));
                            nameBankNamesAndReaders.put(s, reader);
                        }
                        catch (Exception e) {
                            FMLLog.severe("Failed to load LOTR name bank " + s + "from zip file", new Object[0]);
                            e.printStackTrace();
                        }
                    }
                }
            }
            else {
                final File nameBankDir = new File(LOTRMod.class.getResource("/assets/lotr/names").toURI());
                for (final File file : nameBankDir.listFiles()) {
                    String s2 = file.getName();
                    final int j = s2.indexOf(".txt");
                    if (j < 0) {
                        FMLLog.severe("Failed to load LOTR name bank " + s2 + " from MCP folder; name bank files must be in .txt format", new Object[0]);
                    }
                    else {
                        try {
                            s2 = s2.substring(0, j);
                            final BufferedReader reader2 = new BufferedReader(new InputStreamReader((InputStream)new BOMInputStream((InputStream)new FileInputStream(file)), Charsets.UTF_8.name()));
                            nameBankNamesAndReaders.put(s2, reader2);
                        }
                        catch (Exception e2) {
                            FMLLog.severe("Failed to load LOTR name bank " + s2 + " from MCP folder", new Object[0]);
                            e2.printStackTrace();
                        }
                    }
                }
            }
        }
        catch (Exception e3) {
            FMLLog.severe("Failed to load LOTR name banks", new Object[0]);
            e3.printStackTrace();
        }
        for (final String nameBankName : nameBankNamesAndReaders.keySet()) {
            final BufferedReader reader3 = nameBankNamesAndReaders.get(nameBankName);
            try {
                final List<String> nameList = new ArrayList<String>();
                String line;
                while ((line = reader3.readLine()) != null) {
                    nameList.add(line);
                }
                reader3.close();
                if (nameList.isEmpty()) {
                    FMLLog.severe("LOTR name bank " + nameBankName + " is empty!", new Object[0]);
                }
                else {
                    final String[] nameBank = nameList.toArray(new String[0]);
                    LOTRNames.allNameBanks.put(nameBankName, nameBank);
                }
            }
            catch (Exception e4) {
                FMLLog.severe("Failed to load LOTR name bank " + nameBankName, new Object[0]);
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
    
    private static String[] getNameBank(final String nameBankName) {
        return LOTRNames.allNameBanks.get(nameBankName);
    }
    
    public static boolean nameBankExists(final String nameBankName) {
        return getNameBank(nameBankName) != null;
    }
    
    public static String getRandomName(final String nameBankName, final Random rand) {
        if (LOTRNames.allNameBanks.containsKey(nameBankName)) {
            final String[] nameBank = getNameBank(nameBankName);
            return nameBank[rand.nextInt(nameBank.length)];
        }
        return "Unnamed";
    }
    
    public static String getHobbitName(final Random rand, final boolean male) {
        final String name = getRandomName(male ? "hobbit_male" : "hobbit_female", rand);
        final String surname = getRandomName("hobbit_surname", rand);
        return name + " " + surname;
    }
    
    public static String[] getHobbitCoupleAndHomeNames(final Random rand) {
        final String[] names = new String[4];
        final String surname = getRandomName("hobbit_surname", rand);
        final String maleName = getRandomName("hobbit_male", rand);
        final String femaleName = getRandomName("hobbit_female", rand);
        final String homeName = getRandomName("hobbit_home", rand);
        names[0] = maleName + " " + surname;
        names[1] = femaleName + " " + surname;
        names[2] = surname;
        names[3] = homeName;
        return names;
    }
    
    public static String[] getHobbitSign(final Random rand) {
        final String[] sign = new String[4];
        Arrays.fill(sign, "");
        final String text = getRandomName("hobbit_sign", rand);
        final String[] split = text.split("#");
        sign[1] = split[0];
        sign[2] = ((split.length < 2) ? "" : split[1]);
        if (rand.nextInt(1000) == 0) {
            sign[1] = "Vote";
            sign[2] = "UKIP";
        }
        return sign;
    }
    
    public static String getHobbitChildNameForParent(final Random rand, final boolean male, final LOTREntityHobbit parent) {
        final String name = getRandomName(male ? "hobbit_male" : "hobbit_female", rand);
        final String surname = parent.getNPCName().substring(parent.getNPCName().indexOf(" ") + 1);
        return name + " " + surname;
    }
    
    public static void changeHobbitSurnameForMarriage(final LOTREntityHobbit maleHobbit, final LOTREntityHobbit femaleHobbit) {
        final String surname = maleHobbit.getNPCName().substring(maleHobbit.getNPCName().indexOf(" ") + 1);
        final String femaleFirstName = femaleHobbit.getNPCName().substring(0, femaleHobbit.getNPCName().indexOf(" "));
        femaleHobbit.familyInfo.setName(femaleFirstName + " " + surname);
    }
    
    public static String[] getHobbitTavernName(final Random rand) {
        final String prefix = getRandomName("hobbitTavern_prefix", rand);
        final String suffix = getRandomName("hobbitTavern_suffix", rand);
        return new String[] { prefix, suffix };
    }
    
    public static String[] getHobbitTavernQuote(final Random rand) {
        final String[] sign = new String[4];
        Arrays.fill(sign, "");
        final String text = getRandomName("hobbitTavern_quote", rand);
        final String[] split = text.split("#");
        for (int l = 0; l < sign.length && l < split.length; ++l) {
            sign[l] = split[l];
        }
        return sign;
    }
    
    public static String getSindarinName(final Random rand, final boolean male) {
        final String name = getRandomName(male ? "sindarin_male" : "sindarin_female", rand);
        return name;
    }
    
    public static String getQuenyaName(final Random rand, final boolean male) {
        String name = getRandomName(male ? "quenya_male" : "quenya_female", rand);
        if (rand.nextInt(5) == 0) {
            name = name + " " + getRandomName("quenya_title", rand);
        }
        return name;
    }
    
    public static String getSindarinOrQuenyaName(final Random rand, final boolean male) {
        if (male) {
            final String[] sNames = getNameBank("sindarin_male");
            final String[] qNames = getNameBank("quenya_male");
            final int i = sNames.length + qNames.length;
            if (rand.nextInt(i) < sNames.length) {
                return getSindarinName(rand, male);
            }
            return getQuenyaName(rand, male);
        }
        else {
            final String[] sNames = getNameBank("sindarin_female");
            final String[] qNames = getNameBank("quenya_female");
            final int i = sNames.length + qNames.length;
            if (rand.nextInt(i) < sNames.length) {
                return getSindarinName(rand, male);
            }
            return getQuenyaName(rand, male);
        }
    }
    
    public static String getRhudaurName(final Random rand, final boolean male) {
        final String name = getRandomName(male ? "rhudaur_male" : "rhudaur_female", rand);
        return name;
    }
    
    public static String getRohirricName(final Random rand, final boolean male) {
        final String name = getRandomName(male ? "rohan_male" : "rohan_female", rand);
        return name;
    }
    
    public static String[] getRohanMeadHallName(final Random rand) {
        final String prefix = getRandomName("rohanMeadHall_prefix", rand);
        final String suffix = getRandomName("rohanMeadHall_suffix", rand);
        return new String[] { prefix, suffix };
    }
    
    public static String[] getRohanVillageName(final Random rand) {
        final String welcome = "Welcome to";
        final String prefix = getRandomName("rohanVillage_prefix", rand);
        String suffix = getRandomName("rohanVillage_suffix", rand);
        if (prefix.endsWith(suffix.substring(0, 1))) {
            suffix = suffix.substring(1);
        }
        final String name = prefix + suffix;
        final String date = getRandomVillageDate(rand, 50, 500, 100);
        final String est = "est. " + date;
        return new String[] { welcome, name, "", est };
    }
    
    public static String getDunlendingName(final Random rand, final boolean male) {
        final String name = getRandomName(male ? "dunlending_male" : "dunlending_female", rand);
        return name;
    }
    
    public static String[] getDunlendingTavernName(final Random rand) {
        final String prefix = getRandomName("dunlendingTavern_prefix", rand);
        final String suffix = getRandomName("dunlendingTavern_suffix", rand);
        return new String[] { prefix, suffix };
    }
    
    public static String getEntName(final Random rand) {
        final String prefix = getRandomName("ent_prefix", rand);
        final String suffix = getRandomName("ent_suffix", rand);
        return prefix + suffix;
    }
    
    public static String getGondorName(final Random rand, final boolean male) {
        final String name = getRandomName(male ? "gondor_male" : "gondor_female", rand);
        return name;
    }
    
    public static String[] getGondorTavernName(final Random rand) {
        final String prefix = getRandomName("gondorTavern_prefix", rand);
        final String suffix = getRandomName("gondorTavern_suffix", rand);
        return new String[] { prefix, suffix };
    }
    
    public static String[] getGondorVillageName(final Random rand) {
        final String welcome = "Welcome to";
        final String prefix = getRandomName("gondorVillage_prefix", rand);
        String suffix = getRandomName("gondorVillage_suffix", rand);
        if (prefix.endsWith(suffix.substring(0, 1))) {
            suffix = suffix.substring(1);
        }
        final String name = prefix + suffix;
        final String date = getRandomVillageDate(rand, 50, 5000, 1500);
        final String est = "est. " + date;
        return new String[] { welcome, name, "", est };
    }
    
    public static String getDorwinionName(final Random rand, final boolean male) {
        final String name = getRandomName(male ? "dorwinion_male" : "dorwinion_female", rand);
        return name;
    }
    
    public static String getDalishName(final Random rand, final boolean male) {
        final String name = getRandomName(male ? "dale_male" : "dale_female", rand);
        return name;
    }
    
    public static String[] getDaleBakeryName(final Random rand, final String name) {
        final String title = getRandomName("dale_bakery", rand);
        return new String[] { name + "'s", title };
    }
    
    public static String getDwarfName(final Random rand, final boolean male) {
        final String name = getRandomName(male ? "dwarf_male" : "dwarf_female", rand);
        final String parentName = getRandomName("dwarf_male", rand);
        return name + (male ? " son of " : " daughter of ") + parentName;
    }
    
    public static String getDwarfChildNameForParent(final Random rand, final boolean male, final LOTREntityDwarf parent) {
        final String name = getRandomName(male ? "dwarf_male" : "dwarf_female", rand);
        String parentName = parent.getNPCName();
        parentName = parentName.substring(0, parentName.indexOf(" "));
        return name + (male ? " son of " : " daughter of ") + parentName;
    }
    
    public static String getRhunicName(final Random rand, final boolean male) {
        final String name = getRandomName(male ? "rhun_male" : "rhun_female", rand);
        return name;
    }
    
    public static String[] getRhunTavernName(final Random rand) {
        final String prefix = getRandomName("rhunTavern_prefix", rand);
        final String suffix = getRandomName("rhunTavern_suffix", rand);
        return new String[] { prefix, suffix };
    }
    
    public static String[] getRhunVillageName(final Random rand) {
        final String welcome = "Welcome to";
        final String prefix = getRandomName("rhunVillage_prefix", rand);
        String suffix = getRandomName("rhunVillage_suffix", rand);
        if (prefix.endsWith(suffix.substring(0, 1))) {
            suffix = suffix.substring(1);
        }
        final String name = prefix + suffix;
        final String date = getRandomVillageDate(rand, 50, 2000, 300);
        final String est = "est. " + date;
        return new String[] { welcome, name, "", est };
    }
    
    public static String getOrcName(final Random rand) {
        final String prefix = getRandomName("orc_prefix", rand);
        final String suffix = getRandomName("orc_suffix", rand);
        return prefix + suffix;
    }
    
    public static String getTrollName(final Random rand) {
        final String name = getRandomName("troll", rand);
        return name;
    }
    
    public static String getUmbarName(final Random rand, final boolean male) {
        final String name = getRandomName(male ? "umbar_male" : "umbar_female", rand);
        return name;
    }
    
    public static String getHarnedorName(final Random rand, final boolean male) {
        final String name = getRandomName(male ? "nearHaradrim_male" : "nearHaradrim_female", rand);
        return name;
    }
    
    public static String getSouthronCoastName(final Random rand, final boolean male) {
        if (rand.nextInt(3) == 0) {
            return getUmbarName(rand, male);
        }
        return getHarnedorName(rand, male);
    }
    
    public static String getNomadName(final Random rand, final boolean male) {
        final String name = getRandomName(male ? "nomad_male" : "nomad_female", rand);
        return name;
    }
    
    public static String getGulfHaradName(final Random rand, final boolean male) {
        final String name = getRandomName(male ? "gulf_male" : "gulf_female", rand);
        return name;
    }
    
    public static String[] getHaradTavernName(final Random rand) {
        final String prefix = getRandomName("haradTavern_prefix", rand);
        final String suffix = getRandomName("haradTavern_suffix", rand);
        return new String[] { prefix, suffix };
    }
    
    public static String[] getHaradVillageName(final Random rand) {
        final String welcome = "Welcome to";
        final String prefix = getRandomName("haradVillage_prefix", rand);
        String suffix = getRandomName("haradVillage_suffix", rand);
        if (prefix.endsWith(suffix.substring(0, 1))) {
            suffix = suffix.substring(1);
        }
        final String name = prefix + suffix;
        final String date = getRandomVillageDate(rand, 50, 4000, 1000);
        final String est = "est. " + date;
        return new String[] { welcome, name, "", est };
    }
    
    public static String getMoredainName(final Random rand, final boolean male) {
        final String name = getRandomName(male ? "moredain_male" : "moredain_female", rand);
        return name;
    }
    
    public static String getTauredainName(final Random rand, final boolean male) {
        final String name = getRandomName(male ? "tauredain_male" : "tauredain_female", rand);
        return name;
    }
    
    private static String getRandomVillageDate(final Random rand, final int min, final int max, final int std) {
        double d = rand.nextGaussian();
        d = Math.abs(d);
        int ago = min + (int)Math.round(d * std);
        ago = Math.min(ago, max);
        int date = LOTRDate.THIRD_AGE_CURRENT - ago;
        if (date >= 1) {
            return "T.A. " + date;
        }
        date += LOTRDate.SECOND_AGE_LENGTH;
        return "S.A. " + date;
    }
    
    static {
        LOTRNames.allNameBanks = new HashMap<String, String[]>();
    }
}
