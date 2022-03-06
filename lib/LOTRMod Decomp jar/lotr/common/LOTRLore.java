// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lotr.common.entity.npc.LOTRNames;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.StringUtils;
import java.util.Enumeration;
import cpw.mods.fml.common.ModContainer;
import java.io.IOException;
import java.util.Map;
import java.io.FileInputStream;
import java.io.File;
import lotr.common.util.LOTRLog;
import java.io.Reader;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.google.common.base.Charsets;
import org.apache.commons.io.input.BOMInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class LOTRLore
{
    private static final String newline = "\n";
    private static final String codeMetadata = "#";
    private static final String codeTitle = "title:";
    private static final String codeAuthor = "author:";
    private static final String codeCategory = "types:";
    private static final String codeCategorySeparator = ",";
    private static final String codeReward = "reward";
    public final String loreName;
    public final String loreTitle;
    public final String loreAuthor;
    public final String loreText;
    public final List<LoreCategory> loreCategories;
    public final boolean isRewardable;
    
    public static LOTRLore getMultiRandomLore(final List<LoreCategory> categories, final Random random, final boolean rewardsOnly) {
        final List<LOTRLore> allLore = new ArrayList<LOTRLore>();
        for (final LoreCategory c : categories) {
            for (final LOTRLore lore : c.loreList) {
                if (!allLore.contains(lore) && (!rewardsOnly || lore.isRewardable)) {
                    allLore.add(lore);
                }
            }
        }
        if (!allLore.isEmpty()) {
            return allLore.get(random.nextInt(allLore.size()));
        }
        return null;
    }
    
    public static void loadAllLore() {
        final Map<String, BufferedReader> loreReaders = new HashMap<String, BufferedReader>();
        ZipFile zip = null;
        try {
            final ModContainer mc = LOTRMod.getModContainer();
            if (mc.getSource().isFile()) {
                zip = new ZipFile(mc.getSource());
                final Enumeration entries = zip.entries();
                while (entries.hasMoreElements()) {
                    final ZipEntry entry = entries.nextElement();
                    String s = entry.getName();
                    final String path = "assets/lotr/lore/";
                    if (s.startsWith(path) && s.endsWith(".txt")) {
                        s = s.substring(path.length());
                        final int i = s.indexOf(".txt");
                        try {
                            s = s.substring(0, i);
                            final BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream)new BOMInputStream(zip.getInputStream(entry)), Charsets.UTF_8.name()));
                            loreReaders.put(s, reader);
                        }
                        catch (Exception e) {
                            LOTRLog.logger.error("Failed to load LOTR lore " + s + "from zip file");
                            e.printStackTrace();
                        }
                    }
                }
            }
            else {
                final File nameBankDir = new File(LOTRMod.class.getResource("/assets/lotr/lore").toURI());
                for (final File file : nameBankDir.listFiles()) {
                    String s2 = file.getName();
                    final int j = s2.indexOf(".txt");
                    if (j < 0) {
                        LOTRLog.logger.error("Failed to load LOTR lore " + s2 + " from MCP folder; name bank files must be in .txt format");
                    }
                    else {
                        try {
                            s2 = s2.substring(0, j);
                            final BufferedReader reader2 = new BufferedReader(new InputStreamReader((InputStream)new BOMInputStream((InputStream)new FileInputStream(file)), Charsets.UTF_8.name()));
                            loreReaders.put(s2, reader2);
                        }
                        catch (Exception e2) {
                            LOTRLog.logger.error("Failed to load LOTR lore " + s2 + " from MCP folder");
                            e2.printStackTrace();
                        }
                    }
                }
            }
        }
        catch (Exception e3) {
            LOTRLog.logger.error("Failed to load LOTR lore");
            e3.printStackTrace();
        }
        for (final Map.Entry<String, BufferedReader> entry2 : loreReaders.entrySet()) {
            final String loreName = entry2.getKey();
            final BufferedReader reader3 = entry2.getValue();
            try {
                String title = "";
                String author = "";
                final List<LoreCategory> categories = new ArrayList<LoreCategory>();
                String text = "";
                boolean reward = false;
                String line;
                while ((line = reader3.readLine()) != null) {
                    if (line.startsWith("#")) {
                        final String metadata = line.substring("#".length());
                        if (metadata.startsWith("title:")) {
                            title = metadata.substring("title:".length());
                        }
                        else if (metadata.startsWith("author:")) {
                            author = metadata.substring("author:".length());
                        }
                        else if (metadata.startsWith("types:")) {
                            String categoryString = metadata.substring("types:".length());
                            while (categoryString.length() > 0) {
                                String categoryName = null;
                                final int indexOf = categoryString.indexOf(",");
                                if (indexOf >= 0) {
                                    categoryName = categoryString.substring(0, indexOf);
                                    categoryString = categoryString.substring(indexOf + 1);
                                }
                                else {
                                    categoryName = categoryString;
                                    categoryString = "";
                                }
                                if (categoryName != null) {
                                    if (categoryName.equals("all")) {
                                        for (final LoreCategory category : LoreCategory.values()) {
                                            if (!categories.contains(category)) {
                                                categories.add(category);
                                            }
                                        }
                                    }
                                    else {
                                        final LoreCategory category2 = LoreCategory.forName(categoryName);
                                        if (category2 != null) {
                                            if (categories.contains(category2)) {
                                                continue;
                                            }
                                            categories.add(category2);
                                        }
                                        else {
                                            LOTRLog.logger.warn("LOTRLore: Loading lore " + loreName + ", no category exists for name " + categoryName);
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            if (!metadata.startsWith("reward")) {
                                continue;
                            }
                            reward = true;
                        }
                    }
                    else {
                        text += line;
                        text += "\n";
                    }
                }
                reader3.close();
                final LOTRLore lore = new LOTRLore(loreName, title, author, text, categories, reward);
                for (final LoreCategory category3 : categories) {
                    category3.addLore(lore);
                }
            }
            catch (Exception e4) {
                LOTRLog.logger.error("Failed to load LOTR lore: " + loreName);
                e4.printStackTrace();
            }
        }
        for (final LoreCategory category4 : LoreCategory.values()) {
            final int num = category4.loreList.size();
            int numReward = 0;
            for (final LOTRLore lore2 : category4.loreList) {
                if (lore2.isRewardable) {
                    ++numReward;
                }
            }
            LOTRLog.logger.info("LOTRLore: Category " + category4.categoryName + " has loaded " + num + " lore texts, of which " + numReward + " rewardable");
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
    
    public LOTRLore(final String name, final String title, final String auth, final String text, final List<LoreCategory> categories, final boolean reward) {
        this.loreName = name;
        this.loreTitle = title;
        this.loreAuthor = auth;
        this.loreText = text;
        this.loreCategories = categories;
        this.isRewardable = reward;
    }
    
    private static List<String> organisePages(final String loreText) {
        final List<String> loreTextPages = new ArrayList<String>();
        String remainingText = loreText;
        final List<String> splitTxtWords = new ArrayList<String>();
        while (remainingText.length() > 0) {
            if (remainingText.startsWith("\n")) {
                final String part = "\n";
                if (!splitTxtWords.isEmpty()) {
                    splitTxtWords.add(part);
                }
                remainingText = remainingText.substring(part.length());
            }
            else {
                String part = "";
                final int indexOf = remainingText.indexOf("\n");
                if (indexOf >= 0) {
                    part = remainingText.substring(0, indexOf);
                }
                else {
                    part = remainingText;
                }
                final String[] split;
                final String[] splitWords = split = StringUtils.split(part, " ");
                for (final String word : split) {
                    splitTxtWords.add(word);
                }
                remainingText = remainingText.substring(part.length());
            }
        }
        final int pageLengthMax = 256;
        final int maxLines = 13;
        final int avgLineLength = 17;
        int page = 0;
        while (!splitTxtWords.isEmpty()) {
            String pageText = "";
            int numLines = 0;
            String currentLine = "";
            int usedWords = 0;
            for (int i = 0; i < splitTxtWords.size(); ++i) {
                final String word2 = splitTxtWords.get(i);
                if (pageText.length() + word2.length() > 256) {
                    break;
                }
                if (word2.equals("\n")) {
                    if (currentLine.length() > 0) {
                        pageText += currentLine;
                        currentLine = "";
                        if (++numLines >= 13) {
                            break;
                        }
                    }
                    ++usedWords;
                    if (pageText.length() > 0) {
                        pageText += word2;
                        if (++numLines >= 13) {
                            break;
                        }
                    }
                }
                else {
                    currentLine += word2;
                    ++usedWords;
                    if (i < splitTxtWords.size() - 1) {
                        currentLine += " ";
                    }
                    if (currentLine.length() >= 17) {
                        pageText += currentLine;
                        currentLine = "";
                        if (++numLines >= 13) {
                            break;
                        }
                    }
                }
            }
            if (currentLine.length() > 0) {
                pageText += currentLine;
                currentLine = "";
                ++numLines;
            }
            for (int i = 0; i < usedWords; ++i) {
                splitTxtWords.remove(0);
            }
            loreTextPages.add(pageText);
            ++page;
        }
        return loreTextPages;
    }
    
    private String formatRandom(String text, final Random random) {
        int lastIndexStart = -1;
        while (true) {
            final int indexStart = text.indexOf("{", lastIndexStart + 1);
            final int indexEnd = text.indexOf("}");
            lastIndexStart = indexStart;
            if (indexStart < 0 || indexEnd <= indexStart) {
                break;
            }
            final String unformatted = text.substring(indexStart, indexEnd + 1);
            String formatted = unformatted.substring(1, unformatted.length() - 1);
            if (formatted.startsWith("num:")) {
                try {
                    final String s1 = formatted.substring("num:".length());
                    final int i1 = s1.indexOf(",");
                    final String s2 = s1.substring(0, i1);
                    final String s3 = s1.substring(i1 + ",".length());
                    final int min = Integer.parseInt(s2);
                    final int max = Integer.parseInt(s3);
                    final int number = MathHelper.getRandomIntegerInRange(random, min, max);
                    formatted = String.valueOf(number);
                }
                catch (Exception e) {
                    LOTRLog.logger.error("LOTRLore: Error formatting number " + unformatted + " in text: " + this.loreName);
                    e.printStackTrace();
                }
            }
            else if (formatted.startsWith("name:")) {
                try {
                    final String namebank;
                    final String s1 = namebank = formatted.substring("name:".length());
                    if (!LOTRNames.nameBankExists(namebank)) {
                        LOTRLog.logger.error("LOTRLore: No namebank exists for " + namebank + "!");
                    }
                    else {
                        final String name = formatted = LOTRNames.getRandomName(namebank, random);
                    }
                }
                catch (Exception e) {
                    LOTRLog.logger.error("LOTRLore: Error formatting name " + unformatted + " in text: " + this.loreName);
                    e.printStackTrace();
                }
            }
            else if (formatted.startsWith("choose:")) {
                try {
                    String remaining = formatted.substring("choose:".length());
                    final List<String> words = new ArrayList<String>();
                    while (remaining.length() > 0) {
                        final int indexOf = remaining.indexOf("/");
                        String word;
                        if (indexOf >= 0) {
                            word = remaining.substring(0, indexOf);
                            remaining = remaining.substring(indexOf + "/".length());
                        }
                        else {
                            word = remaining;
                            remaining = "";
                        }
                        words.add(word);
                    }
                    formatted = words.get(random.nextInt(words.size()));
                }
                catch (Exception e) {
                    LOTRLog.logger.error("LOTRLore: Error formatting choice " + unformatted + " in text: " + this.loreName);
                    e.printStackTrace();
                }
            }
            text = Pattern.compile(unformatted, 16).matcher(text).replaceFirst(Matcher.quoteReplacement(formatted));
        }
        return text;
    }
    
    public ItemStack createLoreBook(final Random random) {
        final ItemStack itemstack = new ItemStack(Items.written_book);
        final NBTTagCompound data = new NBTTagCompound();
        itemstack.setTagCompound(data);
        final String title = this.formatRandom(this.loreTitle, random);
        final String author = this.formatRandom(this.loreAuthor, random);
        final String text = this.formatRandom(this.loreText, random);
        final List<String> textPages = organisePages(text);
        data.setString("title", title);
        data.setString("author", author);
        final NBTTagList pages = new NBTTagList();
        for (final String pageText : textPages) {
            pages.appendTag((NBTBase)new NBTTagString(pageText));
        }
        data.setTag("pages", (NBTBase)pages);
        return itemstack;
    }
    
    public enum LoreCategory
    {
        RUINS("ruins"), 
        SHIRE("shire"), 
        BLUE_MOUNTAINS("blue_mountains"), 
        LINDON("lindon"), 
        ERIADOR("eriador"), 
        RIVENDELL("rivendell"), 
        EREGION("eregion"), 
        DUNLAND("dunland"), 
        GUNDABAD("gundabad"), 
        ANGMAR("angmar"), 
        WOODLAND_REALM("woodland_realm"), 
        DOL_GULDUR("dol_guldur"), 
        DALE("dale"), 
        DURIN("durins_folk"), 
        LOTHLORIEN("lothlorien"), 
        ROHAN("rohan"), 
        ISENGARD("isengard"), 
        GONDOR("gondor"), 
        MORDOR("mordor"), 
        DORWINION("dorwinion"), 
        RHUN("rhun"), 
        HARNEDOR("harnedor"), 
        SOUTHRON("southron"), 
        UMBAR("umbar"), 
        NOMAD("nomad"), 
        GULF("gulf"), 
        FAR_HARAD("far_harad"), 
        FAR_HARAD_JUNGLE("far_harad_jungle"), 
        HALF_TROLL("half_troll");
        
        public static final String allCode = "all";
        public final String categoryName;
        private List<LOTRLore> loreList;
        
        private LoreCategory(final String s) {
            this.loreList = new ArrayList<LOTRLore>();
            this.categoryName = s;
        }
        
        private void addLore(final LOTRLore lore) {
            this.loreList.add(lore);
        }
        
        private LOTRLore getRandomLore(final Random random) {
            return this.loreList.get(random.nextInt(this.loreList.size()));
        }
        
        public static LoreCategory forName(final String s) {
            for (final LoreCategory r : values()) {
                if (s.equalsIgnoreCase(r.categoryName)) {
                    return r;
                }
            }
            return null;
        }
    }
}
