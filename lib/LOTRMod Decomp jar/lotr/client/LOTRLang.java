// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client;

import java.util.Iterator;
import java.util.Enumeration;
import java.util.List;
import cpw.mods.fml.common.ModContainer;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.commons.io.input.BOMInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import com.google.common.base.Charsets;
import java.io.FileOutputStream;
import cpw.mods.fml.common.FMLLog;
import org.apache.commons.io.FilenameUtils;
import java.io.File;
import java.util.zip.ZipEntry;
import java.util.ArrayList;
import java.util.zip.ZipFile;
import lotr.common.LOTRMod;

public class LOTRLang
{
    public static void runUpdateThread() {
        final Thread thread = new Thread("LOTR language update") {
            @Override
            public void run() {
                updateTranslations();
            }
        };
        thread.setDaemon(true);
        thread.start();
    }
    
    private static void updateTranslations() {
        try {
            final ModContainer container = LOTRMod.getModContainer();
            final File mod = container.getSource();
            if (!mod.isFile()) {
                return;
            }
            final ZipFile zip = new ZipFile(mod);
            ZipEntry en_US = null;
            ZipEntry en_GB = null;
            final List<ZipEntry> langFiles = new ArrayList<ZipEntry>();
            final Enumeration entries = zip.entries();
            while (entries.hasMoreElements()) {
                final ZipEntry file = entries.nextElement();
                final String filename = file.getName();
                if (filename.endsWith(".lang")) {
                    langFiles.add(file);
                }
                if (filename.endsWith("en_US.lang")) {
                    en_US = file;
                }
                if (filename.endsWith("en_GB.lang")) {
                    en_GB = file;
                }
            }
            final File newLangFolder = new File(mod.getParentFile(), "LOTR_UpdatedLangFiles");
            if (newLangFolder.exists()) {
                final File[] listFiles;
                final File[] contents = listFiles = newLangFolder.listFiles();
                for (final File file2 : listFiles) {
                    file2.delete();
                }
                newLangFolder.delete();
            }
            newLangFolder.mkdir();
            generateReadmeFile(newLangFolder);
            outputEnUS(newLangFolder, zip, en_US);
            for (final ZipEntry entry : langFiles) {
                if (!entry.equals(en_US)) {
                    if (entry.equals(en_GB)) {
                        continue;
                    }
                    final String name = FilenameUtils.getName(entry.getName());
                    FMLLog.info("Checking LOTR lang file for updates " + name, new Object[0]);
                    final File oldLang_temp = File.createTempFile(name + "_old", ".lang");
                    copyZipEntryToFile(zip, entry, oldLang_temp);
                    final File newLang = new File(newLangFolder, name);
                    newLang.createNewFile();
                    final PrintStream writer = new PrintStream(new FileOutputStream(newLang), true, Charsets.UTF_8.name());
                    final BufferedReader en_US_reader = new BufferedReader(new InputStreamReader((InputStream)new BOMInputStream(zip.getInputStream(en_US)), Charsets.UTF_8.name()));
                    String en_US_line = "";
                    while ((en_US_line = en_US_reader.readLine()) != null) {
                        final int i1 = en_US_line.indexOf("=");
                        if (i1 < 0) {
                            writer.println(en_US_line);
                        }
                        else {
                            final String en_US_key = en_US_line.substring(0, i1);
                            boolean foundKey = false;
                            final BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream)new BOMInputStream((InputStream)new FileInputStream(oldLang_temp)), Charsets.UTF_8.name()));
                            String line = "";
                            while ((line = reader.readLine()) != null) {
                                final int i2 = line.indexOf("=");
                                if (i2 >= 0) {
                                    final String key = line.substring(0, i2);
                                    if (key.equals(en_US_key)) {
                                        foundKey = true;
                                        writer.println(line);
                                        break;
                                    }
                                    continue;
                                }
                            }
                            reader.close();
                            if (foundKey) {
                                continue;
                            }
                            writer.println(en_US_key + "=");
                        }
                    }
                    writer.close();
                    en_US_reader.close();
                    oldLang_temp.delete();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void copyZipEntryToFile(final ZipFile zip, final ZipEntry entry, final File copy) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream)new BOMInputStream(zip.getInputStream(entry)), Charsets.UTF_8.name()));
        final PrintStream writer = new PrintStream(new FileOutputStream(copy), true, Charsets.UTF_8.name());
        String line = "";
        while ((line = reader.readLine()) != null) {
            writer.println(line);
        }
        reader.close();
        writer.close();
    }
    
    private static void generateReadmeFile(final File folder) throws IOException {
        final File readme = new File(folder, "readme.txt");
        readme.createNewFile();
        final PrintStream writer = new PrintStream(new FileOutputStream(readme));
        writer.println("LOTR lang file update-helper");
        writer.println();
        writer.println("This helper system is to assist people in updating the mod's lang files after a mod update.");
        writer.println("To enable the helper, go to the mod's config file - or ingame config menu - and set 'Run language update helper' to 'true'.");
        writer.println();
        writer.println("When the mod is loaded, it checks all lang files in the mod zip file against en_US.lang, and outputs a copy of them here.");
        writer.println("If a lang file is missing any keys (names of new blocks, items, etc. added in an update) then those keys are added to the copy here.");
        writer.println("Unused keys are also removed - for example, if a feature is removed from the mod, or a key is renamed.");
        writer.println("The lang files outputted here also have their contents ordered in the same order as en_US.lang, to make comparisons easier.");
        writer.println();
        writer.println("The mod's current en_US.lang is also outputted here for convenience.");
        writer.println();
        writer.println("I hope this system will be much easier than checking a lang file against en_US.lang, for every update, to find out what has been added.");
        writer.println();
        writer.println("DO NOT STORE ANYTHING in this folder! This folder, and its contents, are re-created every time the mod loads.");
        writer.println("Anything in the folder will be deleted.");
        writer.println("If you want to update one of the lang files, copy and paste it somewhere safe!");
        writer.println();
        writer.println("And finally, if you have updated a lang file (or created a new one), the best way to send it to us is through the mod's Facebook page.");
        writer.println("We credit everyone by name in the mod's credits file unless asked not to. If you do not want your name listed, then please say so.");
        writer.println();
        writer.println("Please note: Lang files must be in UTF-8 format, otherwise errors will occur.");
        writer.close();
    }
    
    private static void outputEnUS(final File folder, final ZipFile zip, final ZipEntry entry) throws IOException {
        final String name = FilenameUtils.getName(entry.getName());
        final File output = new File(folder, name);
        copyZipEntryToFile(zip, entry, output);
    }
}
