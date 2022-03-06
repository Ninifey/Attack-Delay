// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common;

import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.EnumChatFormatting;
import lotr.common.fac.LOTRAlignmentValues;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import java.util.Iterator;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;
import lotr.common.fac.LOTRFactionRank;
import lotr.common.fac.LOTRFaction;
import java.util.UUID;
import java.util.List;

public class LOTRTitle implements Comparable<LOTRTitle>
{
    public static List<LOTRTitle> allTitles;
    public static LOTRTitle adventurer;
    public static LOTRTitle rogue;
    public static LOTRTitle bartender;
    public static LOTRTitle gaffer;
    public static LOTRTitle scholar;
    public static LOTRTitle minstrel;
    public static LOTRTitle bard;
    public static LOTRTitle artisan;
    public static LOTRTitle warrior;
    public static LOTRTitle scourge;
    public static LOTRTitle wanderer;
    public static LOTRTitle peacekeeper;
    public static LOTRTitle warmongerer;
    public static LOTRTitle hunter;
    public static LOTRTitle raider;
    public static LOTRTitle explorer;
    public static LOTRTitle mercenary;
    public static LOTRTitle shipwright;
    public static LOTRTitle marksman;
    public static LOTRTitle merchant;
    public static LOTRTitle farmer;
    public static LOTRTitle burglar;
    public static LOTRTitle ruffian;
    public static LOTRTitle architect;
    public static LOTRTitle miner;
    public static LOTRTitle swordsman;
    public static LOTRTitle scout;
    public static LOTRTitle spearman;
    public static LOTRTitle rider;
    public static LOTRTitle watcher;
    public static LOTRTitle creator;
    public static LOTRTitle creator2;
    public static LOTRTitle moderator;
    public static LOTRTitle gruk;
    public static LOTRTitle boyd;
    public static LOTRTitle translator;
    public static LOTRTitle patron;
    public static LOTRTitle loremaster;
    public static LOTRTitle builder;
    public static LOTRTitle ANY_10000;
    public static LOTRTitle MULTI_freePeoples;
    public static LOTRTitle MULTI_elf;
    public static LOTRTitle MULTI_orc;
    public static LOTRTitle MULTI_snaga;
    public static LOTRTitle MULTI_dwarf;
    public static LOTRTitle MULTI_dunedain;
    public static LOTRTitle MULTI_moria;
    public static LOTRTitle MULTI_rhun;
    public static LOTRTitle MULTI_easterling;
    public static LOTRTitle MULTI_harad;
    public static LOTRTitle MULTI_haradrim;
    public static LOTRTitle MULTI_southron;
    public static LOTRTitle MULTI_farHarad;
    public static LOTRTitle MULTI_farHaradrim;
    public static LOTRTitle MULTI_silvanElf;
    public static LOTRTitle MULTI_sindar;
    public static LOTRTitle MULTI_greyElf;
    public static LOTRTitle MULTI_noldor;
    public static LOTRTitle MULTI_avari;
    public static LOTRTitle MULTI_sunlands;
    public static LOTRTitle MULTI_swerting;
    public static LOTRTitle MULTI_sutherland;
    public static LOTRTitle HOBBIT_hobbit;
    public static LOTRTitle HOBBIT_halfling;
    public static LOTRTitle HOBBIT_shire;
    public static LOTRTitle HOBBIT_hobbiton;
    public static LOTRTitle HOBBIT_buckland;
    public static LOTRTitle HOBBIT_tookland;
    public static LOTRTitle HOBBIT_bywater;
    public static LOTRTitle HOBBIT_longbottom;
    public static LOTRTitle HOBBIT_michelDelving;
    public static LOTRTitle HOBBIT_northfarthing;
    public static LOTRTitle HOBBIT_westfarthing;
    public static LOTRTitle HOBBIT_southfarthing;
    public static LOTRTitle HOBBIT_eastfarthing;
    public static LOTRTitle HOBBIT_mathomKeeper;
    public static LOTRTitle RANGER_ranger;
    public static LOTRTitle RANGER_northDunedain;
    public static LOTRTitle RANGER_arnor;
    public static LOTRTitle RANGER_annuminas;
    public static LOTRTitle RANGER_fornost;
    public static LOTRTitle BLUE_MOUNTAINS_blueDwarf;
    public static LOTRTitle BLUE_MOUNTAINS_blueMountains;
    public static LOTRTitle BLUE_MOUNTAINS_firebeard;
    public static LOTRTitle BLUE_MOUNTAINS_broadbeam;
    public static LOTRTitle BLUE_MOUNTAINS_belegost;
    public static LOTRTitle BLUE_MOUNTAINS_nogrod;
    public static LOTRTitle HIGH_ELF_highElf;
    public static LOTRTitle HIGH_ELF_lindon;
    public static LOTRTitle HIGH_ELF_mithlond;
    public static LOTRTitle HIGH_ELF_rivendell;
    public static LOTRTitle HIGH_ELF_eregion;
    public static LOTRTitle HIGH_ELF_forlindon;
    public static LOTRTitle HIGH_ELF_harlindon;
    public static LOTRTitle HIGH_ELF_imladris;
    public static LOTRTitle GUNDABAD_gundabad;
    public static LOTRTitle GUNDABAD_gundabadOrc;
    public static LOTRTitle GUNDABAD_moriaOrc;
    public static LOTRTitle GUNDABAD_goblin;
    public static LOTRTitle GUNDABAD_goblinTown;
    public static LOTRTitle GUNDABAD_mountGundabad;
    public static LOTRTitle GUNDABAD_mountGram;
    public static LOTRTitle GUNDABAD_gundabadUruk;
    public static LOTRTitle ANGMAR_angmar;
    public static LOTRTitle ANGMAR_angmarOrc;
    public static LOTRTitle ANGMAR_troll;
    public static LOTRTitle ANGMAR_carnDum;
    public static LOTRTitle ANGMAR_hillman;
    public static LOTRTitle ANGMAR_rhudaur;
    public static LOTRTitle ANGMAR_ettenmoors;
    public static LOTRTitle ANGMAR_coldfells;
    public static LOTRTitle WOOD_ELF_woodElf;
    public static LOTRTitle WOOD_ELF_woodlandRealm;
    public static LOTRTitle DOL_GULDUR_dolGuldur;
    public static LOTRTitle DOL_GULDUR_dolGuldurOrc;
    public static LOTRTitle DOL_GULDUR_necromancer;
    public static LOTRTitle DOL_GULDUR_sorcerer;
    public static LOTRTitle DOL_GULDUR_spiderRider;
    public static LOTRTitle DALE_dale;
    public static LOTRTitle DALE_northman;
    public static LOTRTitle DALE_barding;
    public static LOTRTitle DALE_esgaroth;
    public static LOTRTitle DWARF_durin;
    public static LOTRTitle DWARF_greyDwarf;
    public static LOTRTitle DWARF_ironHills;
    public static LOTRTitle DWARF_erebor;
    public static LOTRTitle DWARF_khazadDum;
    public static LOTRTitle GALADHRIM_galadhrim;
    public static LOTRTitle GALADHRIM_lothlorien;
    public static LOTRTitle GALADHRIM_carasGaladhon;
    public static LOTRTitle DUNLAND_dunland;
    public static LOTRTitle DUNLAND_dunlending;
    public static LOTRTitle DUNLAND_wildman;
    public static LOTRTitle DUNLAND_barbarian;
    public static LOTRTitle DUNLAND_adorn;
    public static LOTRTitle DUNLAND_berserker;
    public static LOTRTitle URUK_uruk;
    public static LOTRTitle URUK_urukHai;
    public static LOTRTitle URUK_isengard;
    public static LOTRTitle URUK_whiteHand;
    public static LOTRTitle FANGORN_fangorn;
    public static LOTRTitle FANGORN_ent;
    public static LOTRTitle ROHAN_rohan;
    public static LOTRTitle ROHAN_rohirrim;
    public static LOTRTitle ROHAN_eorlingas;
    public static LOTRTitle ROHAN_strawhead;
    public static LOTRTitle ROHAN_edoras;
    public static LOTRTitle ROHAN_helmsDeep;
    public static LOTRTitle ROHAN_grimslade;
    public static LOTRTitle ROHAN_aldburg;
    public static LOTRTitle ROHAN_westfold;
    public static LOTRTitle ROHAN_eastfold;
    public static LOTRTitle ROHAN_westemnet;
    public static LOTRTitle ROHAN_eastemnet;
    public static LOTRTitle ROHAN_wold;
    public static LOTRTitle ROHAN_shieldmaiden;
    public static LOTRTitle ROHAN_horselord;
    public static LOTRTitle GONDOR_gondor;
    public static LOTRTitle GONDOR_gondorian;
    public static LOTRTitle GONDOR_southDunedain;
    public static LOTRTitle GONDOR_dolAmroth;
    public static LOTRTitle GONDOR_swanKnight;
    public static LOTRTitle GONDOR_ithilien;
    public static LOTRTitle GONDOR_ithilienRanger;
    public static LOTRTitle GONDOR_minasTirith;
    public static LOTRTitle GONDOR_towerGuard;
    public static LOTRTitle GONDOR_osgiliath;
    public static LOTRTitle GONDOR_lebennin;
    public static LOTRTitle GONDOR_anorien;
    public static LOTRTitle GONDOR_lossarnach;
    public static LOTRTitle GONDOR_imlothMelui;
    public static LOTRTitle GONDOR_pelargir;
    public static LOTRTitle GONDOR_blackrootVale;
    public static LOTRTitle GONDOR_mornan;
    public static LOTRTitle GONDOR_pinnathGelin;
    public static LOTRTitle GONDOR_lamedon;
    public static LOTRTitle GONDOR_anfalas;
    public static LOTRTitle GONDOR_belfalas;
    public static LOTRTitle GONDOR_linhir;
    public static LOTRTitle GONDOR_edhellond;
    public static LOTRTitle GONDOR_tarnost;
    public static LOTRTitle GONDOR_calembel;
    public static LOTRTitle GONDOR_ethring;
    public static LOTRTitle GONDOR_erech;
    public static LOTRTitle GONDOR_ethirAnduin;
    public static LOTRTitle MORDOR_mordor;
    public static LOTRTitle MORDOR_mordorOrc;
    public static LOTRTitle MORDOR_blackUruk;
    public static LOTRTitle MORDOR_nurn;
    public static LOTRTitle MORDOR_baradDur;
    public static LOTRTitle MORDOR_morannon;
    public static LOTRTitle MORDOR_minasMorgul;
    public static LOTRTitle MORDOR_cirithUngol;
    public static LOTRTitle MORDOR_blackNumenorean;
    public static LOTRTitle MORDOR_nanUngol;
    public static LOTRTitle DORWINION_dorwinion;
    public static LOTRTitle DORWINION_vintner;
    public static LOTRTitle DORWINION_dorwinrim;
    public static LOTRTitle DORWINION_dorwinionElf;
    public static LOTRTitle DORWINION_bladorthin;
    public static LOTRTitle DORWINION_wineTaster;
    public static LOTRTitle RHUN_rhudel;
    public static LOTRTitle RHUN_rhunaer;
    public static LOTRTitle RHUN_rhunaerim;
    public static LOTRTitle NEAR_HARAD_nearHarad;
    public static LOTRTitle NEAR_HARAD_nearHaradrim;
    public static LOTRTitle NEAR_HARAD_umbar;
    public static LOTRTitle NEAR_HARAD_corsair;
    public static LOTRTitle NEAR_HARAD_harnedor;
    public static LOTRTitle NEAR_HARAD_ninzayan;
    public static LOTRTitle NEAR_HARAD_belkadar;
    public static LOTRTitle NEAR_HARAD_southronCoasts;
    public static LOTRTitle NEAR_HARAD_azrazain;
    public static LOTRTitle NEAR_HARAD_ain;
    public static LOTRTitle NEAR_HARAD_aj;
    public static LOTRTitle NEAR_HARAD_nomad;
    public static LOTRTitle NEAR_HARAD_gulf;
    public static LOTRTitle NEAR_HARAD_khopazul;
    public static LOTRTitle NEAR_HARAD_khopakadar;
    public static LOTRTitle NEAR_HARAD_yaphu;
    public static LOTRTitle NEAR_HARAD_serpent;
    public static LOTRTitle NEAR_HARAD_gulfing;
    public static LOTRTitle NEAR_HARAD_coastling;
    public static LOTRTitle MOREDAIN_moredain;
    public static LOTRTitle MOREDAIN_lion;
    public static LOTRTitle MOREDAIN_lioness;
    public static LOTRTitle TAUREDAIN_tauredain;
    public static LOTRTitle HALF_TROLL_halfTroll;
    public static LOTRTitle HALF_TROLL_pertorogwaith;
    private static int nextTitleID;
    public final int titleID;
    private String name;
    private boolean isHidden;
    private TitleType titleType;
    private UUID[] uuids;
    private List<LOTRFaction> alignmentFactions;
    private float alignmentRequired;
    private boolean anyAlignment;
    private LOTRAchievement titleAchievement;
    private boolean useAchievementName;
    private LOTRFactionRank titleRank;
    private boolean isFeminineRank;
    
    public LOTRTitle(final String s) {
        this.isHidden = false;
        this.titleType = TitleType.STARTER;
        this.alignmentFactions = new ArrayList<LOTRFaction>();
        this.anyAlignment = false;
        this.useAchievementName = false;
        this.titleID = LOTRTitle.nextTitleID;
        ++LOTRTitle.nextTitleID;
        this.name = s;
        LOTRTitle.allTitles.add(this);
    }
    
    public LOTRTitle(final String s, final LOTRAchievement ach) {
        this((s == null) ? ach.getCodeName() : s);
        this.titleType = TitleType.ACHIEVEMENT;
        this.titleAchievement = ach;
        if (s == null) {
            this.useAchievementName = true;
        }
    }
    
    public LOTRTitle(final LOTRFactionRank rank, final boolean fem) {
        this(fem ? rank.getCodeNameFem() : rank.getCodeName());
        this.titleType = TitleType.RANK;
        this.titleRank = rank;
        this.isFeminineRank = fem;
    }
    
    public LOTRTitle setPlayerExclusive(final UUID... players) {
        this.titleType = TitleType.PLAYER_EXCLUSIVE;
        this.uuids = players;
        this.isHidden = true;
        return this;
    }
    
    public LOTRTitle setPlayerExclusive(final String... players) {
        final UUID[] us = new UUID[players.length];
        for (int i = 0; i < players.length; ++i) {
            us[i] = UUID.fromString(players[i]);
        }
        return this.setPlayerExclusive(us);
    }
    
    public LOTRTitle setPlayerExclusive(final UUID[]... playersArrays) {
        final List<UUID> allPlayers = new ArrayList<UUID>();
        for (final UUID[] players : playersArrays) {
            allPlayers.addAll(Arrays.asList(players));
        }
        return this.setPlayerExclusive((UUID[])allPlayers.toArray(new UUID[0]));
    }
    
    public LOTRTitle setShieldExclusive(final LOTRShields... shields) {
        final List<UUID> allPlayers = new ArrayList<UUID>();
        for (final LOTRShields shield : shields) {
            allPlayers.addAll(Arrays.asList(shield.exclusiveUUIDs));
        }
        return this.setPlayerExclusive((UUID[])allPlayers.toArray(new UUID[0]));
    }
    
    public LOTRTitle setAlignment(final LOTRFaction faction) {
        return this.setAlignment(faction, faction.getPledgeAlignment());
    }
    
    public LOTRTitle setAlignment(final LOTRFaction faction, final float alignment) {
        return this.setMultiAlignment(alignment, faction);
    }
    
    public LOTRTitle setMultiAlignment(final float alignment, final LOTRFaction... factions) {
        return this.setMultiAlignment(alignment, Arrays.asList(factions));
    }
    
    public LOTRTitle setMultiAlignment(final float alignment, final List<LOTRFaction> factions) {
        this.titleType = TitleType.ALIGNMENT;
        this.alignmentFactions.addAll(factions);
        this.alignmentRequired = alignment;
        return this;
    }
    
    public LOTRTitle setAnyAlignment(final float alignment) {
        this.setMultiAlignment(alignment, LOTRFaction.getPlayableAlignmentFactions());
        this.anyAlignment = true;
        return this;
    }
    
    public String getTitleName() {
        return this.name;
    }
    
    public static LOTRTitle forName(final String name) {
        for (final LOTRTitle title : LOTRTitle.allTitles) {
            if (title.getTitleName().equals(name)) {
                return title;
            }
        }
        return null;
    }
    
    public static LOTRTitle forID(final int ID) {
        for (final LOTRTitle title : LOTRTitle.allTitles) {
            if (title.titleID == ID) {
                return title;
            }
        }
        return null;
    }
    
    public String getUntranslatedName() {
        if (this.useAchievementName && this.titleAchievement != null) {
            return this.titleAchievement.getUntranslatedTitle();
        }
        if (this.titleType != TitleType.RANK) {
            return "lotr.title." + this.name;
        }
        if (this.isFeminineRank) {
            return this.titleRank.getCodeFullNameFem();
        }
        return this.titleRank.getCodeFullName();
    }
    
    public String getDisplayName() {
        if (this.titleType != TitleType.RANK) {
            return StatCollector.translateToLocal(this.getUntranslatedName());
        }
        if (this.isFeminineRank) {
            return this.titleRank.getDisplayFullNameFem();
        }
        return this.titleRank.getDisplayFullName();
    }
    
    public String getDescription(final EntityPlayer entityplayer) {
        switch (this.titleType) {
            case STARTER: {
                return StatCollector.translateToLocal("lotr.titles.unlock.starter");
            }
            case PLAYER_EXCLUSIVE: {
                return StatCollector.translateToLocal("lotr.titles.unlock.exclusive");
            }
            case ALIGNMENT: {
                final String alignLevel = LOTRAlignmentValues.formatAlignForDisplay(this.alignmentRequired);
                if (this.anyAlignment) {
                    return StatCollector.translateToLocalFormatted("lotr.titles.unlock.alignment.any", new Object[] { alignLevel });
                }
                String s = "";
                if (this.alignmentFactions.size() > 1) {
                    for (int i = 0; i < this.alignmentFactions.size(); ++i) {
                        final LOTRFaction f = this.alignmentFactions.get(i);
                        if (i > 0) {
                            s += " / ";
                        }
                        s += f.factionName();
                    }
                }
                else {
                    final LOTRFaction f2 = this.alignmentFactions.get(0);
                    s = f2.factionName();
                }
                return StatCollector.translateToLocalFormatted("lotr.titles.unlock.alignment", new Object[] { s, alignLevel });
            }
            case ACHIEVEMENT: {
                return this.titleAchievement.getDescription(entityplayer);
            }
            case RANK: {
                final String alignS = LOTRAlignmentValues.formatAlignForDisplay(this.titleRank.alignment);
                if (this.titleRank.isAbovePledgeRank()) {
                    return StatCollector.translateToLocalFormatted("lotr.titles.unlock.alignment.pledge", new Object[] { this.titleRank.fac.factionName(), alignS });
                }
                return StatCollector.translateToLocalFormatted("lotr.titles.unlock.alignment", new Object[] { this.titleRank.fac.factionName(), alignS });
            }
            default: {
                return "If you can read this, something has gone hideously wrong";
            }
        }
    }
    
    public boolean isFeminineRank() {
        return this.titleType == TitleType.RANK && this.isFeminineRank;
    }
    
    @Override
    public int compareTo(final LOTRTitle other) {
        return this.getDisplayName().compareTo(other.getDisplayName());
    }
    
    public boolean canPlayerUse(final EntityPlayer entityplayer) {
        switch (this.titleType) {
            case STARTER: {
                return true;
            }
            case PLAYER_EXCLUSIVE: {
                for (final UUID player : this.uuids) {
                    if (entityplayer.getUniqueID().equals(player)) {
                        return true;
                    }
                }
                return false;
            }
            case ALIGNMENT: {
                for (final LOTRFaction f : this.alignmentFactions) {
                    if (LOTRLevelData.getData(entityplayer).getAlignment(f) >= this.alignmentRequired) {
                        return true;
                    }
                }
                return false;
            }
            case ACHIEVEMENT: {
                return LOTRLevelData.getData(entityplayer).hasAchievement(this.titleAchievement);
            }
            case RANK: {
                final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
                final LOTRFaction fac = this.titleRank.fac;
                final float align = pd.getAlignment(fac);
                return align >= this.titleRank.alignment && (!this.titleRank.isAbovePledgeRank() || pd.isPledgedTo(fac));
            }
            default: {
                return true;
            }
        }
    }
    
    public boolean canDisplay(final EntityPlayer entityplayer) {
        return !this.isHidden || this.canPlayerUse(entityplayer);
    }
    
    public static void createTitles() {
        LOTRTitle.adventurer = new LOTRTitle("adventurer");
        LOTRTitle.rogue = new LOTRTitle("rogue");
        LOTRTitle.bartender = new LOTRTitle("bartender");
        LOTRTitle.gaffer = new LOTRTitle("gaffer");
        LOTRTitle.scholar = new LOTRTitle("scholar");
        LOTRTitle.minstrel = new LOTRTitle("minstrel");
        LOTRTitle.bard = new LOTRTitle("bard");
        LOTRTitle.artisan = new LOTRTitle("artisan");
        LOTRTitle.warrior = new LOTRTitle("warrior");
        LOTRTitle.scourge = new LOTRTitle("scourge");
        LOTRTitle.wanderer = new LOTRTitle("wanderer");
        LOTRTitle.peacekeeper = new LOTRTitle("peacekeeper");
        LOTRTitle.warmongerer = new LOTRTitle("warmongerer");
        LOTRTitle.hunter = new LOTRTitle("hunter");
        LOTRTitle.raider = new LOTRTitle("raider");
        LOTRTitle.explorer = new LOTRTitle("explorer");
        LOTRTitle.mercenary = new LOTRTitle("mercenary");
        LOTRTitle.shipwright = new LOTRTitle("shipwright");
        LOTRTitle.marksman = new LOTRTitle("marksman");
        LOTRTitle.merchant = new LOTRTitle("merchant");
        LOTRTitle.farmer = new LOTRTitle("farmer");
        LOTRTitle.burglar = new LOTRTitle("burglar");
        LOTRTitle.ruffian = new LOTRTitle("ruffian");
        LOTRTitle.architect = new LOTRTitle("architect");
        LOTRTitle.miner = new LOTRTitle("miner");
        LOTRTitle.swordsman = new LOTRTitle("swordsman");
        LOTRTitle.scout = new LOTRTitle("scout");
        LOTRTitle.spearman = new LOTRTitle("spearman");
        LOTRTitle.rider = new LOTRTitle("rider");
        LOTRTitle.watcher = new LOTRTitle("watcher");
        LOTRTitle.creator = new LOTRTitle("creator").setPlayerExclusive(new UUID[0]);
        LOTRTitle.creator2 = new LOTRTitle("creator2").setPlayerExclusive("7bc56da6-f133-4e47-8d0f-a2776762bca6");
        LOTRTitle.moderator = new LOTRTitle("moderator").setShieldExclusive(LOTRShields.MOD);
        LOTRTitle.gruk = new LOTRTitle("gruk").setShieldExclusive(LOTRShields.GRUK);
        LOTRTitle.boyd = new LOTRTitle("boyd").setShieldExclusive(LOTRShields.BOYD);
        LOTRTitle.translator = new LOTRTitle("translator").setPlayerExclusive(LOTRTranslatorList.playerUUIDs);
        LOTRTitle.patron = new LOTRTitle("patron").setPlayerExclusive(LOTRPatron.getTitlePlayers());
        LOTRTitle.loremaster = new LOTRTitle("loremaster").setShieldExclusive(LOTRShields.LOREMASTER_2013, LOTRShields.LOREMASTER_2014, LOTRShields.LOREMASTER_2015, LOTRShields.LOREMASTER_2016);
        LOTRTitle.builder = new LOTRTitle("builder").setShieldExclusive(LOTRShields.ELVEN_CONTEST, LOTRShields.EVIL_CONTEST, LOTRShields.SHIRE_CONTEST, LOTRShields.GONDOR_CONTEST, LOTRShields.HARAD_CONTEST, LOTRShields.RHUN_CONTEST);
        LOTRTitle.ANY_10000 = new LOTRTitle("ANY_10000").setAnyAlignment(10000.0f);
        LOTRTitle.MULTI_freePeoples = new LOTRTitle("MULTI_freePeoples").setMultiAlignment(100.0f, LOTRFaction.getAllOfType(LOTRFaction.FactionType.TYPE_FREE));
        LOTRTitle.MULTI_elf = new LOTRTitle("MULTI_elf").setMultiAlignment(100.0f, LOTRFaction.getAllOfType(LOTRFaction.FactionType.TYPE_ELF));
        LOTRTitle.MULTI_orc = new LOTRTitle("MULTI_orc").setMultiAlignment(100.0f, LOTRFaction.getAllOfType(LOTRFaction.FactionType.TYPE_ORC));
        LOTRTitle.MULTI_snaga = new LOTRTitle("MULTI_snaga").setMultiAlignment(100.0f, LOTRFaction.getAllOfType(LOTRFaction.FactionType.TYPE_ORC));
        LOTRTitle.MULTI_dwarf = new LOTRTitle("MULTI_dwarf").setMultiAlignment(100.0f, LOTRFaction.getAllOfType(LOTRFaction.FactionType.TYPE_DWARF));
        LOTRTitle.MULTI_dunedain = new LOTRTitle("MULTI_dunedain").setMultiAlignment(100.0f, LOTRFaction.RANGER_NORTH, LOTRFaction.GONDOR);
        LOTRTitle.MULTI_moria = new LOTRTitle("MULTI_moria").setMultiAlignment(100.0f, LOTRFaction.GUNDABAD, LOTRFaction.DWARF);
        LOTRTitle.MULTI_rhun = new LOTRTitle("MULTI_rhun").setMultiAlignment(100.0f, LOTRFaction.getAllRhun());
        LOTRTitle.MULTI_easterling = new LOTRTitle("MULTI_easterling").setMultiAlignment(100.0f, LOTRFaction.getAllRhun());
        LOTRTitle.MULTI_harad = new LOTRTitle("MULTI_harad").setMultiAlignment(100.0f, LOTRFaction.getAllHarad());
        LOTRTitle.MULTI_haradrim = new LOTRTitle("MULTI_haradrim").setMultiAlignment(100.0f, LOTRFaction.getAllHarad());
        LOTRTitle.MULTI_southron = new LOTRTitle("MULTI_southron").setMultiAlignment(100.0f, LOTRFaction.getAllHarad());
        LOTRTitle.MULTI_farHarad = new LOTRTitle("MULTI_farHarad").setMultiAlignment(100.0f, LOTRFaction.MOREDAIN, LOTRFaction.TAUREDAIN, LOTRFaction.HALF_TROLL);
        LOTRTitle.MULTI_farHaradrim = new LOTRTitle("MULTI_farHaradrim").setMultiAlignment(100.0f, LOTRFaction.MOREDAIN, LOTRFaction.TAUREDAIN, LOTRFaction.HALF_TROLL);
        LOTRTitle.MULTI_silvanElf = new LOTRTitle("MULTI_silvanElf").setMultiAlignment(100.0f, LOTRFaction.WOOD_ELF, LOTRFaction.GALADHRIM);
        LOTRTitle.MULTI_sindar = new LOTRTitle("MULTI_sindar").setMultiAlignment(100.0f, LOTRFaction.HIGH_ELF, LOTRFaction.WOOD_ELF, LOTRFaction.GALADHRIM);
        LOTRTitle.MULTI_greyElf = new LOTRTitle("MULTI_greyElf").setMultiAlignment(100.0f, LOTRFaction.HIGH_ELF, LOTRFaction.WOOD_ELF, LOTRFaction.GALADHRIM);
        LOTRTitle.MULTI_noldor = new LOTRTitle("MULTI_noldor").setMultiAlignment(100.0f, LOTRFaction.HIGH_ELF);
        LOTRTitle.MULTI_avari = new LOTRTitle("MULTI_avari").setMultiAlignment(100.0f, LOTRFaction.DORWINION);
        LOTRTitle.MULTI_sunlands = new LOTRTitle("MULTI_sunlands").setMultiAlignment(100.0f, LOTRFaction.getAllHarad());
        LOTRTitle.MULTI_swerting = new LOTRTitle("MULTI_swerting").setMultiAlignment(100.0f, LOTRFaction.getAllHarad());
        LOTRTitle.MULTI_sutherland = new LOTRTitle("MULTI_sutherland").setMultiAlignment(100.0f, LOTRFaction.getAllHarad());
        LOTRTitle.HOBBIT_hobbit = new LOTRTitle("HOBBIT_hobbit").setAlignment(LOTRFaction.HOBBIT);
        LOTRTitle.HOBBIT_halfling = new LOTRTitle("HOBBIT_halfling").setAlignment(LOTRFaction.HOBBIT);
        LOTRTitle.HOBBIT_shire = new LOTRTitle("HOBBIT_shire").setAlignment(LOTRFaction.HOBBIT);
        LOTRTitle.HOBBIT_hobbiton = new LOTRTitle("HOBBIT_hobbiton").setAlignment(LOTRFaction.HOBBIT);
        LOTRTitle.HOBBIT_buckland = new LOTRTitle("HOBBIT_buckland").setAlignment(LOTRFaction.HOBBIT);
        LOTRTitle.HOBBIT_tookland = new LOTRTitle("HOBBIT_tookland").setAlignment(LOTRFaction.HOBBIT);
        LOTRTitle.HOBBIT_bywater = new LOTRTitle("HOBBIT_bywater").setAlignment(LOTRFaction.HOBBIT);
        LOTRTitle.HOBBIT_longbottom = new LOTRTitle("HOBBIT_longbottom").setAlignment(LOTRFaction.HOBBIT);
        LOTRTitle.HOBBIT_michelDelving = new LOTRTitle("HOBBIT_michelDelving").setAlignment(LOTRFaction.HOBBIT);
        LOTRTitle.HOBBIT_northfarthing = new LOTRTitle("HOBBIT_northfarthing").setAlignment(LOTRFaction.HOBBIT);
        LOTRTitle.HOBBIT_westfarthing = new LOTRTitle("HOBBIT_westfarthing").setAlignment(LOTRFaction.HOBBIT);
        LOTRTitle.HOBBIT_southfarthing = new LOTRTitle("HOBBIT_southfarthing").setAlignment(LOTRFaction.HOBBIT);
        LOTRTitle.HOBBIT_eastfarthing = new LOTRTitle("HOBBIT_eastfarthing").setAlignment(LOTRFaction.HOBBIT);
        LOTRTitle.HOBBIT_mathomKeeper = new LOTRTitle("HOBBIT_mathomKeeper").setAlignment(LOTRFaction.HOBBIT);
        LOTRTitle.RANGER_ranger = new LOTRTitle("RANGER_ranger").setAlignment(LOTRFaction.RANGER_NORTH);
        LOTRTitle.RANGER_northDunedain = new LOTRTitle("RANGER_northDunedain").setAlignment(LOTRFaction.RANGER_NORTH);
        LOTRTitle.RANGER_arnor = new LOTRTitle("RANGER_arnor").setAlignment(LOTRFaction.RANGER_NORTH);
        LOTRTitle.RANGER_annuminas = new LOTRTitle("RANGER_annuminas").setAlignment(LOTRFaction.RANGER_NORTH);
        LOTRTitle.RANGER_fornost = new LOTRTitle("RANGER_fornost").setAlignment(LOTRFaction.RANGER_NORTH);
        LOTRTitle.BLUE_MOUNTAINS_blueDwarf = new LOTRTitle("BLUE_MOUNTAINS_blueDwarf").setAlignment(LOTRFaction.BLUE_MOUNTAINS);
        LOTRTitle.BLUE_MOUNTAINS_blueMountains = new LOTRTitle("BLUE_MOUNTAINS_blueMountains").setAlignment(LOTRFaction.BLUE_MOUNTAINS);
        LOTRTitle.BLUE_MOUNTAINS_firebeard = new LOTRTitle("BLUE_MOUNTAINS_firebeard").setAlignment(LOTRFaction.BLUE_MOUNTAINS);
        LOTRTitle.BLUE_MOUNTAINS_broadbeam = new LOTRTitle("BLUE_MOUNTAINS_broadbeam").setAlignment(LOTRFaction.BLUE_MOUNTAINS);
        LOTRTitle.BLUE_MOUNTAINS_belegost = new LOTRTitle("BLUE_MOUNTAINS_belegost").setAlignment(LOTRFaction.BLUE_MOUNTAINS);
        LOTRTitle.BLUE_MOUNTAINS_nogrod = new LOTRTitle("BLUE_MOUNTAINS_nogrod").setAlignment(LOTRFaction.BLUE_MOUNTAINS);
        LOTRTitle.HIGH_ELF_highElf = new LOTRTitle("HIGH_ELF_highElf").setAlignment(LOTRFaction.HIGH_ELF);
        LOTRTitle.HIGH_ELF_lindon = new LOTRTitle("HIGH_ELF_lindon").setAlignment(LOTRFaction.HIGH_ELF);
        LOTRTitle.HIGH_ELF_mithlond = new LOTRTitle("HIGH_ELF_mithlond").setAlignment(LOTRFaction.HIGH_ELF);
        LOTRTitle.HIGH_ELF_rivendell = new LOTRTitle("HIGH_ELF_rivendell").setAlignment(LOTRFaction.HIGH_ELF);
        LOTRTitle.HIGH_ELF_eregion = new LOTRTitle("HIGH_ELF_eregion").setAlignment(LOTRFaction.HIGH_ELF);
        LOTRTitle.HIGH_ELF_forlindon = new LOTRTitle("HIGH_ELF_forlindon").setAlignment(LOTRFaction.HIGH_ELF);
        LOTRTitle.HIGH_ELF_harlindon = new LOTRTitle("HIGH_ELF_harlindon").setAlignment(LOTRFaction.HIGH_ELF);
        LOTRTitle.HIGH_ELF_imladris = new LOTRTitle("HIGH_ELF_imladris").setAlignment(LOTRFaction.HIGH_ELF);
        LOTRTitle.GUNDABAD_gundabad = new LOTRTitle("GUNDABAD_gundabad").setAlignment(LOTRFaction.GUNDABAD);
        LOTRTitle.GUNDABAD_gundabadOrc = new LOTRTitle("GUNDABAD_gundabadOrc").setAlignment(LOTRFaction.GUNDABAD);
        LOTRTitle.GUNDABAD_moriaOrc = new LOTRTitle("GUNDABAD_moriaOrc").setAlignment(LOTRFaction.GUNDABAD);
        LOTRTitle.GUNDABAD_goblin = new LOTRTitle("GUNDABAD_goblin").setAlignment(LOTRFaction.GUNDABAD);
        LOTRTitle.GUNDABAD_goblinTown = new LOTRTitle("GUNDABAD_goblinTown").setAlignment(LOTRFaction.GUNDABAD);
        LOTRTitle.GUNDABAD_mountGundabad = new LOTRTitle("GUNDABAD_mountGundabad").setAlignment(LOTRFaction.GUNDABAD);
        LOTRTitle.GUNDABAD_mountGram = new LOTRTitle("GUNDABAD_mountGram").setAlignment(LOTRFaction.GUNDABAD);
        LOTRTitle.GUNDABAD_gundabadUruk = new LOTRTitle("GUNDABAD_gundabadUruk").setAlignment(LOTRFaction.GUNDABAD);
        LOTRTitle.ANGMAR_angmar = new LOTRTitle("ANGMAR_angmar").setAlignment(LOTRFaction.ANGMAR);
        LOTRTitle.ANGMAR_angmarOrc = new LOTRTitle("ANGMAR_angmarOrc").setAlignment(LOTRFaction.ANGMAR);
        LOTRTitle.ANGMAR_troll = new LOTRTitle("ANGMAR_troll").setAlignment(LOTRFaction.ANGMAR);
        LOTRTitle.ANGMAR_carnDum = new LOTRTitle("ANGMAR_carnDum").setAlignment(LOTRFaction.ANGMAR);
        LOTRTitle.ANGMAR_hillman = new LOTRTitle("ANGMAR_hillman").setAlignment(LOTRFaction.ANGMAR);
        LOTRTitle.ANGMAR_rhudaur = new LOTRTitle("ANGMAR_rhudaur").setAlignment(LOTRFaction.ANGMAR);
        LOTRTitle.ANGMAR_ettenmoors = new LOTRTitle("ANGMAR_ettenmoors").setAlignment(LOTRFaction.ANGMAR);
        LOTRTitle.ANGMAR_coldfells = new LOTRTitle("ANGMAR_coldfells").setAlignment(LOTRFaction.ANGMAR);
        LOTRTitle.WOOD_ELF_woodElf = new LOTRTitle("WOOD_ELF_woodElf").setAlignment(LOTRFaction.WOOD_ELF);
        LOTRTitle.WOOD_ELF_woodlandRealm = new LOTRTitle("WOOD_ELF_woodlandRealm").setAlignment(LOTRFaction.WOOD_ELF);
        LOTRTitle.DOL_GULDUR_dolGuldur = new LOTRTitle("DOL_GULDUR_dolGuldur").setAlignment(LOTRFaction.DOL_GULDUR);
        LOTRTitle.DOL_GULDUR_dolGuldurOrc = new LOTRTitle("DOL_GULDUR_dolGuldurOrc").setAlignment(LOTRFaction.DOL_GULDUR);
        LOTRTitle.DOL_GULDUR_necromancer = new LOTRTitle("DOL_GULDUR_necromancer").setAlignment(LOTRFaction.DOL_GULDUR);
        LOTRTitle.DOL_GULDUR_sorcerer = new LOTRTitle("DOL_GULDUR_sorcerer").setAlignment(LOTRFaction.DOL_GULDUR);
        LOTRTitle.DOL_GULDUR_spiderRider = new LOTRTitle("DOL_GULDUR_spiderRider").setAlignment(LOTRFaction.DOL_GULDUR);
        LOTRTitle.DALE_dale = new LOTRTitle("DALE_dale").setAlignment(LOTRFaction.DALE);
        LOTRTitle.DALE_northman = new LOTRTitle("DALE_northman").setAlignment(LOTRFaction.DALE);
        LOTRTitle.DALE_barding = new LOTRTitle("DALE_barding").setAlignment(LOTRFaction.DALE);
        LOTRTitle.DALE_esgaroth = new LOTRTitle("DALE_esgaroth").setAlignment(LOTRFaction.DALE);
        LOTRTitle.DWARF_durin = new LOTRTitle("DWARF_durin").setAlignment(LOTRFaction.DWARF);
        LOTRTitle.DWARF_greyDwarf = new LOTRTitle("DWARF_greyDwarf").setAlignment(LOTRFaction.DWARF);
        LOTRTitle.DWARF_ironHills = new LOTRTitle("DWARF_ironHills").setAlignment(LOTRFaction.DWARF);
        LOTRTitle.DWARF_erebor = new LOTRTitle("DWARF_erebor").setAlignment(LOTRFaction.DWARF);
        LOTRTitle.DWARF_khazadDum = new LOTRTitle("DWARF_khazadDum").setAlignment(LOTRFaction.DWARF);
        LOTRTitle.GALADHRIM_galadhrim = new LOTRTitle("GALADHRIM_galadhrim").setAlignment(LOTRFaction.GALADHRIM);
        LOTRTitle.GALADHRIM_lothlorien = new LOTRTitle("GALADHRIM_lothlorien").setAlignment(LOTRFaction.GALADHRIM);
        LOTRTitle.GALADHRIM_carasGaladhon = new LOTRTitle("GALADHRIM_carasGaladhon").setAlignment(LOTRFaction.GALADHRIM);
        LOTRTitle.DUNLAND_dunland = new LOTRTitle("DUNLAND_dunland").setAlignment(LOTRFaction.DUNLAND);
        LOTRTitle.DUNLAND_dunlending = new LOTRTitle("DUNLAND_dunlending").setAlignment(LOTRFaction.DUNLAND);
        LOTRTitle.DUNLAND_wildman = new LOTRTitle("DUNLAND_wildman").setAlignment(LOTRFaction.DUNLAND);
        LOTRTitle.DUNLAND_barbarian = new LOTRTitle("DUNLAND_barbarian").setAlignment(LOTRFaction.DUNLAND);
        LOTRTitle.DUNLAND_adorn = new LOTRTitle("DUNLAND_adorn").setAlignment(LOTRFaction.DUNLAND);
        LOTRTitle.DUNLAND_berserker = new LOTRTitle("DUNLAND_berserker").setAlignment(LOTRFaction.DUNLAND);
        LOTRTitle.URUK_uruk = new LOTRTitle("URUK_uruk").setAlignment(LOTRFaction.URUK_HAI);
        LOTRTitle.URUK_urukHai = new LOTRTitle("URUK_urukHai").setAlignment(LOTRFaction.URUK_HAI);
        LOTRTitle.URUK_isengard = new LOTRTitle("URUK_isengard").setAlignment(LOTRFaction.URUK_HAI);
        LOTRTitle.URUK_whiteHand = new LOTRTitle("URUK_whiteHand").setAlignment(LOTRFaction.URUK_HAI);
        LOTRTitle.FANGORN_fangorn = new LOTRTitle("FANGORN_fangorn").setAlignment(LOTRFaction.FANGORN);
        LOTRTitle.FANGORN_ent = new LOTRTitle("FANGORN_ent").setAlignment(LOTRFaction.FANGORN);
        LOTRTitle.ROHAN_rohan = new LOTRTitle("ROHAN_rohan").setAlignment(LOTRFaction.ROHAN);
        LOTRTitle.ROHAN_rohirrim = new LOTRTitle("ROHAN_rohirrim").setAlignment(LOTRFaction.ROHAN);
        LOTRTitle.ROHAN_eorlingas = new LOTRTitle("ROHAN_eorlingas").setAlignment(LOTRFaction.ROHAN);
        LOTRTitle.ROHAN_strawhead = new LOTRTitle("ROHAN_strawhead").setAlignment(LOTRFaction.ROHAN);
        LOTRTitle.ROHAN_edoras = new LOTRTitle("ROHAN_edoras").setAlignment(LOTRFaction.ROHAN);
        LOTRTitle.ROHAN_helmsDeep = new LOTRTitle("ROHAN_helmsDeep").setAlignment(LOTRFaction.ROHAN);
        LOTRTitle.ROHAN_grimslade = new LOTRTitle("ROHAN_grimslade").setAlignment(LOTRFaction.ROHAN);
        LOTRTitle.ROHAN_aldburg = new LOTRTitle("ROHAN_aldburg").setAlignment(LOTRFaction.ROHAN);
        LOTRTitle.ROHAN_westfold = new LOTRTitle("ROHAN_westfold").setAlignment(LOTRFaction.ROHAN);
        LOTRTitle.ROHAN_eastfold = new LOTRTitle("ROHAN_eastfold").setAlignment(LOTRFaction.ROHAN);
        LOTRTitle.ROHAN_westemnet = new LOTRTitle("ROHAN_westemnet").setAlignment(LOTRFaction.ROHAN);
        LOTRTitle.ROHAN_eastemnet = new LOTRTitle("ROHAN_eastemnet").setAlignment(LOTRFaction.ROHAN);
        LOTRTitle.ROHAN_wold = new LOTRTitle("ROHAN_wold").setAlignment(LOTRFaction.ROHAN);
        LOTRTitle.ROHAN_shieldmaiden = new LOTRTitle("ROHAN_shieldmaiden").setAlignment(LOTRFaction.ROHAN);
        LOTRTitle.ROHAN_horselord = new LOTRTitle("ROHAN_horselord").setAlignment(LOTRFaction.ROHAN);
        LOTRTitle.GONDOR_gondor = new LOTRTitle("GONDOR_gondor").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_gondorian = new LOTRTitle("GONDOR_gondorian").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_southDunedain = new LOTRTitle("GONDOR_southDunedain").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_dolAmroth = new LOTRTitle("GONDOR_dolAmroth").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_swanKnight = new LOTRTitle("GONDOR_swanKnight").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_ithilien = new LOTRTitle("GONDOR_ithilien").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_ithilienRanger = new LOTRTitle("GONDOR_ithilienRanger").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_minasTirith = new LOTRTitle("GONDOR_minasTirith").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_towerGuard = new LOTRTitle("GONDOR_towerGuard").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_osgiliath = new LOTRTitle("GONDOR_osgiliath").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_lebennin = new LOTRTitle("GONDOR_lebennin").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_anorien = new LOTRTitle("GONDOR_anorien").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_lossarnach = new LOTRTitle("GONDOR_lossarnach").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_imlothMelui = new LOTRTitle("GONDOR_imlothMelui").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_pelargir = new LOTRTitle("GONDOR_pelargir").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_blackrootVale = new LOTRTitle("GONDOR_blackrootVale").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_mornan = new LOTRTitle("GONDOR_mornan").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_pinnathGelin = new LOTRTitle("GONDOR_pinnathGelin").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_lamedon = new LOTRTitle("GONDOR_lamedon").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_anfalas = new LOTRTitle("GONDOR_anfalas").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_belfalas = new LOTRTitle("GONDOR_belfalas").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_linhir = new LOTRTitle("GONDOR_linhir").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_edhellond = new LOTRTitle("GONDOR_edhellond").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_tarnost = new LOTRTitle("GONDOR_tarnost").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_calembel = new LOTRTitle("GONDOR_calembel").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_ethring = new LOTRTitle("GONDOR_ethring").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_erech = new LOTRTitle("GONDOR_erech").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.GONDOR_ethirAnduin = new LOTRTitle("GONDOR_ethirAnduin").setAlignment(LOTRFaction.GONDOR);
        LOTRTitle.MORDOR_mordor = new LOTRTitle("MORDOR_mordor").setAlignment(LOTRFaction.MORDOR);
        LOTRTitle.MORDOR_mordorOrc = new LOTRTitle("MORDOR_mordorOrc").setAlignment(LOTRFaction.MORDOR);
        LOTRTitle.MORDOR_blackUruk = new LOTRTitle("MORDOR_blackUruk").setAlignment(LOTRFaction.MORDOR);
        LOTRTitle.MORDOR_nurn = new LOTRTitle("MORDOR_nurn").setAlignment(LOTRFaction.MORDOR);
        LOTRTitle.MORDOR_baradDur = new LOTRTitle("MORDOR_baradDur").setAlignment(LOTRFaction.MORDOR);
        LOTRTitle.MORDOR_morannon = new LOTRTitle("MORDOR_morannon").setAlignment(LOTRFaction.MORDOR);
        LOTRTitle.MORDOR_minasMorgul = new LOTRTitle("MORDOR_minasMorgul").setAlignment(LOTRFaction.MORDOR);
        LOTRTitle.MORDOR_cirithUngol = new LOTRTitle("MORDOR_cirithUngol").setAlignment(LOTRFaction.MORDOR);
        LOTRTitle.MORDOR_blackNumenorean = new LOTRTitle("MORDOR_blackNumenorean").setAlignment(LOTRFaction.MORDOR);
        LOTRTitle.MORDOR_nanUngol = new LOTRTitle("MORDOR_nanUngol").setAlignment(LOTRFaction.MORDOR);
        LOTRTitle.DORWINION_dorwinion = new LOTRTitle("DORWINION_dorwinion").setAlignment(LOTRFaction.DORWINION);
        LOTRTitle.DORWINION_vintner = new LOTRTitle("DORWINION_vintner").setAlignment(LOTRFaction.DORWINION);
        LOTRTitle.DORWINION_dorwinrim = new LOTRTitle("DORWINION_dorwinrim").setAlignment(LOTRFaction.DORWINION);
        LOTRTitle.DORWINION_dorwinionElf = new LOTRTitle("DORWINION_dorwinionElf").setAlignment(LOTRFaction.DORWINION);
        LOTRTitle.DORWINION_bladorthin = new LOTRTitle("DORWINION_bladorthin").setAlignment(LOTRFaction.DORWINION);
        LOTRTitle.DORWINION_wineTaster = new LOTRTitle("DORWINION_wineTaster").setAlignment(LOTRFaction.DORWINION);
        LOTRTitle.RHUN_rhudel = new LOTRTitle("RHUN_rhudel").setAlignment(LOTRFaction.RHUN);
        LOTRTitle.RHUN_rhunaer = new LOTRTitle("RHUN_rhunaer").setAlignment(LOTRFaction.RHUN);
        LOTRTitle.RHUN_rhunaerim = new LOTRTitle("RHUN_rhunaerim").setAlignment(LOTRFaction.RHUN);
        LOTRTitle.NEAR_HARAD_nearHarad = new LOTRTitle("NEAR_HARAD_nearHarad").setAlignment(LOTRFaction.NEAR_HARAD);
        LOTRTitle.NEAR_HARAD_nearHaradrim = new LOTRTitle("NEAR_HARAD_nearHaradrim").setAlignment(LOTRFaction.NEAR_HARAD);
        LOTRTitle.NEAR_HARAD_umbar = new LOTRTitle("NEAR_HARAD_umbar").setAlignment(LOTRFaction.NEAR_HARAD);
        LOTRTitle.NEAR_HARAD_corsair = new LOTRTitle("NEAR_HARAD_corsair").setAlignment(LOTRFaction.NEAR_HARAD);
        LOTRTitle.NEAR_HARAD_harnedor = new LOTRTitle("NEAR_HARAD_harnedor").setAlignment(LOTRFaction.NEAR_HARAD);
        LOTRTitle.NEAR_HARAD_ninzayan = new LOTRTitle("NEAR_HARAD_ninzayan").setAlignment(LOTRFaction.NEAR_HARAD);
        LOTRTitle.NEAR_HARAD_belkadar = new LOTRTitle("NEAR_HARAD_belkadar").setAlignment(LOTRFaction.NEAR_HARAD);
        LOTRTitle.NEAR_HARAD_southronCoasts = new LOTRTitle("NEAR_HARAD_southronCoasts").setAlignment(LOTRFaction.NEAR_HARAD);
        LOTRTitle.NEAR_HARAD_azrazain = new LOTRTitle("NEAR_HARAD_azrazain").setAlignment(LOTRFaction.NEAR_HARAD);
        LOTRTitle.NEAR_HARAD_ain = new LOTRTitle("NEAR_HARAD_ain").setAlignment(LOTRFaction.NEAR_HARAD);
        LOTRTitle.NEAR_HARAD_aj = new LOTRTitle("NEAR_HARAD_aj").setAlignment(LOTRFaction.NEAR_HARAD);
        LOTRTitle.NEAR_HARAD_nomad = new LOTRTitle("NEAR_HARAD_nomad").setAlignment(LOTRFaction.NEAR_HARAD);
        LOTRTitle.NEAR_HARAD_gulf = new LOTRTitle("NEAR_HARAD_gulf").setAlignment(LOTRFaction.NEAR_HARAD);
        LOTRTitle.NEAR_HARAD_khopazul = new LOTRTitle("NEAR_HARAD_khopazul").setAlignment(LOTRFaction.NEAR_HARAD);
        LOTRTitle.NEAR_HARAD_khopakadar = new LOTRTitle("NEAR_HARAD_khopakadar").setAlignment(LOTRFaction.NEAR_HARAD);
        LOTRTitle.NEAR_HARAD_yaphu = new LOTRTitle("NEAR_HARAD_yaphu").setAlignment(LOTRFaction.NEAR_HARAD);
        LOTRTitle.NEAR_HARAD_serpent = new LOTRTitle("NEAR_HARAD_serpent").setAlignment(LOTRFaction.NEAR_HARAD);
        LOTRTitle.NEAR_HARAD_gulfing = new LOTRTitle("NEAR_HARAD_gulfing").setAlignment(LOTRFaction.NEAR_HARAD);
        LOTRTitle.NEAR_HARAD_coastling = new LOTRTitle("NEAR_HARAD_coastling").setAlignment(LOTRFaction.NEAR_HARAD);
        LOTRTitle.MOREDAIN_moredain = new LOTRTitle("MOREDAIN_moredain").setAlignment(LOTRFaction.MOREDAIN);
        LOTRTitle.MOREDAIN_lion = new LOTRTitle("MOREDAIN_lion").setAlignment(LOTRFaction.MOREDAIN);
        LOTRTitle.MOREDAIN_lioness = new LOTRTitle("MOREDAIN_lioness").setAlignment(LOTRFaction.MOREDAIN);
        LOTRTitle.TAUREDAIN_tauredain = new LOTRTitle("TAUREDAIN_tauredain").setAlignment(LOTRFaction.TAUREDAIN);
        LOTRTitle.HALF_TROLL_halfTroll = new LOTRTitle("HALF_TROLL_halfTroll").setAlignment(LOTRFaction.HALF_TROLL);
        LOTRTitle.HALF_TROLL_pertorogwaith = new LOTRTitle("HALF_TROLL_pertorogwaith").setAlignment(LOTRFaction.HALF_TROLL);
    }
    
    static {
        LOTRTitle.allTitles = new ArrayList<LOTRTitle>();
        LOTRTitle.nextTitleID = 0;
    }
    
    public enum TitleType
    {
        STARTER, 
        PLAYER_EXCLUSIVE, 
        ALIGNMENT, 
        ACHIEVEMENT, 
        RANK;
    }
    
    public static class PlayerTitle
    {
        private final LOTRTitle theTitle;
        private final EnumChatFormatting theColor;
        
        public PlayerTitle(final LOTRTitle title) {
            this(title, null);
        }
        
        public PlayerTitle(final LOTRTitle title, EnumChatFormatting color) {
            this.theTitle = title;
            if (color == null || !color.Checks()) {
                color = EnumChatFormatting.WHITE;
            }
            this.theColor = color;
        }
        
        public IChatComponent getFullTitleComponent() {
            final IChatComponent component = new ChatComponentText("[").appendSibling((IChatComponent)new ChatComponentTranslation(this.theTitle.getUntranslatedName(), new Object[0])).appendText("]").appendText(" ");
            component.getChatStyle().setColor(this.theColor);
            return component;
        }
        
        public String getFormattedTitle() {
            return this.getFullTitleComponent().getFormattedText();
        }
        
        public LOTRTitle getTitle() {
            return this.theTitle;
        }
        
        public EnumChatFormatting getColor() {
            return this.theColor;
        }
        
        public static EnumChatFormatting colorForID(final int ID) {
            for (final EnumChatFormatting color : EnumChatFormatting.values()) {
                if (color.getFormattingCode() == ID) {
                    return color;
                }
            }
            return null;
        }
    }
}
