// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketTraderInfo;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.MathHelper;
import net.minecraft.inventory.Container;
import java.util.Random;
import lotr.common.inventory.LOTRContainerTrade;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRTraderNPCInfo
{
    private LOTREntityNPC theEntity;
    private LOTRTradeEntry[] buyTrades;
    private LOTRTradeEntry[] sellTrades;
    private int timeUntilAdvertisement;
    private int timeSinceTrade;
    private boolean shouldRefresh;
    private int valueSinceRefresh;
    private static final int refreshValue = 5000;
    private static final int refreshLockTime = 6000;
    
    public LOTRTraderNPCInfo(final LOTREntityNPC npc) {
        this.shouldRefresh = true;
        this.theEntity = npc;
        if (this.theEntity instanceof LOTRTradeable && !((Entity)this.theEntity).worldObj.isClient) {
            this.refreshTrades();
        }
    }
    
    public LOTRTradeEntry[] getBuyTrades() {
        return this.buyTrades;
    }
    
    public LOTRTradeEntry[] getSellTrades() {
        return this.sellTrades;
    }
    
    public void setBuyTrades(final LOTRTradeEntry[] trades) {
        this.buyTrades = trades;
    }
    
    public void setSellTrades(final LOTRTradeEntry[] trades) {
        this.sellTrades = trades;
    }
    
    public void onTrade(final EntityPlayer entityplayer, final LOTRTradeEntry trade, final LOTRTradeEntries.TradeType type, final int value) {
        ((LOTRTradeable)this.theEntity).onPlayerTrade(entityplayer, type, trade.createTradeItem());
        LOTRLevelData.getData(entityplayer).getFactionData(this.theEntity.getFaction()).addTrade();
        trade.doTransaction(value);
        this.timeSinceTrade = 0;
        this.valueSinceRefresh += value;
        this.sendClientPacket(entityplayer);
    }
    
    private void refreshTrades() {
        final LOTRTradeable theTrader = (LOTRTradeable)this.theEntity;
        final Random rand = this.theEntity.getRNG();
        this.setBuyTrades(theTrader.getBuyPool().getRandomTrades(rand));
        this.setSellTrades(theTrader.getSellPool().getRandomTrades(rand));
        this.valueSinceRefresh = 0;
        for (int i = 0; i < ((Entity)this.theEntity).worldObj.playerEntities.size(); ++i) {
            final EntityPlayer entityplayer = ((Entity)this.theEntity).worldObj.playerEntities.get(i);
            final Container container = entityplayer.openContainer;
            if (container instanceof LOTRContainerTrade && ((LOTRContainerTrade)container).theTraderNPC == this.theEntity) {
                ((LOTRContainerTrade)container).updateAllTradeSlots();
            }
        }
    }
    
    private void setAllTradesDelayed() {
        final int delay = 6000;
        for (final LOTRTradeEntry trade : this.buyTrades) {
            if (trade != null) {
                trade.setLockedForTicks(delay);
            }
        }
        for (final LOTRTradeEntry trade : this.sellTrades) {
            if (trade != null) {
                trade.setLockedForTicks(delay);
            }
        }
    }
    
    public void onUpdate() {
        if (!((Entity)this.theEntity).worldObj.isClient) {
            if (this.timeUntilAdvertisement > 0) {
                --this.timeUntilAdvertisement;
            }
            ++this.timeSinceTrade;
            boolean sendUpdate = false;
            for (final LOTRTradeEntry trade : this.buyTrades) {
                if (trade != null && trade.updateAvailable(this.theEntity)) {
                    sendUpdate = true;
                }
            }
            for (final LOTRTradeEntry trade : this.sellTrades) {
                if (trade != null && trade.updateAvailable(this.theEntity)) {
                    sendUpdate = true;
                }
            }
            if (this.shouldRefresh && this.valueSinceRefresh >= 5000) {
                this.refreshTrades();
                this.setAllTradesDelayed();
                sendUpdate = true;
            }
            if (sendUpdate) {
                for (int i = 0; i < ((Entity)this.theEntity).worldObj.playerEntities.size(); ++i) {
                    final EntityPlayer entityplayer = ((Entity)this.theEntity).worldObj.playerEntities.get(i);
                    final Container container = entityplayer.openContainer;
                    if (container instanceof LOTRContainerTrade && ((LOTRContainerTrade)container).theTraderNPC == this.theEntity) {
                        this.sendClientPacket(entityplayer);
                    }
                }
            }
            if (this.theEntity.isEntityAlive() && this.theEntity.getAttackTarget() == null && this.timeUntilAdvertisement == 0 && this.timeSinceTrade > 600) {
                final double range = 10.0;
                final List players = ((Entity)this.theEntity).worldObj.getEntitiesWithinAABB((Class)EntityPlayer.class, ((Entity)this.theEntity).boundingBox.expand(range, range, range));
                for (final Object obj : players) {
                    final EntityPlayer entityplayer2 = (EntityPlayer)obj;
                    if (entityplayer2.isEntityAlive() && !entityplayer2.capabilities.isCreativeMode && (entityplayer2.openContainer == null || entityplayer2.openContainer == entityplayer2.inventoryContainer)) {
                        final String speechBank = this.theEntity.getSpeechBank(entityplayer2);
                        if (speechBank == null || this.theEntity.getRNG().nextInt(3) != 0) {
                            continue;
                        }
                        this.theEntity.sendSpeechBank(entityplayer2, speechBank);
                    }
                }
                this.timeUntilAdvertisement = 20 * MathHelper.getRandomIntegerInRange(this.theEntity.getRNG(), 5, 20);
            }
        }
    }
    
    public void writeToNBT(final NBTTagCompound data) {
        if (this.buyTrades != null) {
            final NBTTagList buyTradesTags = new NBTTagList();
            for (final LOTRTradeEntry trade : this.buyTrades) {
                if (trade != null) {
                    final NBTTagCompound nbt = new NBTTagCompound();
                    trade.writeToNBT(nbt);
                    buyTradesTags.appendTag((NBTBase)nbt);
                }
            }
            final NBTTagCompound buyTradesData = new NBTTagCompound();
            buyTradesData.setTag("Trades", (NBTBase)buyTradesTags);
            data.setTag("LOTRBuyTrades", (NBTBase)buyTradesData);
        }
        if (this.sellTrades != null) {
            final NBTTagList sellTradesTags = new NBTTagList();
            for (final LOTRTradeEntry trade : this.sellTrades) {
                if (trade != null) {
                    final NBTTagCompound nbt = new NBTTagCompound();
                    trade.writeToNBT(nbt);
                    sellTradesTags.appendTag((NBTBase)nbt);
                }
            }
            final NBTTagCompound sellTradesData = new NBTTagCompound();
            sellTradesData.setTag("Trades", (NBTBase)sellTradesTags);
            data.setTag("LOTRSellTrades", (NBTBase)sellTradesData);
        }
        data.setBoolean("ShouldRefresh", this.shouldRefresh);
        data.setInteger("TimeSinceTrade", this.timeSinceTrade);
        data.setInteger("ValueSinceRefresh", this.valueSinceRefresh);
    }
    
    public void readFromNBT(final NBTTagCompound data) {
        if (data.hasKey("LOTRBuyTrades")) {
            final NBTTagCompound buyTradesData = data.getCompoundTag("LOTRBuyTrades");
            if (buyTradesData.hasKey("Trades")) {
                final NBTTagList buyTradesTags = buyTradesData.getTagList("Trades", 10);
                this.buyTrades = new LOTRTradeEntry[buyTradesTags.tagCount()];
                for (int i = 0; i < buyTradesTags.tagCount(); ++i) {
                    final NBTTagCompound nbt = buyTradesTags.getCompoundTagAt(i);
                    final LOTRTradeEntry trade = LOTRTradeEntry.readFromNBT(nbt);
                    this.buyTrades[i] = trade;
                }
            }
        }
        if (data.hasKey("LOTRSellTrades")) {
            final NBTTagCompound sellTradesData = data.getCompoundTag("LOTRSellTrades");
            if (sellTradesData.hasKey("Trades")) {
                final NBTTagList sellTradesTags = sellTradesData.getTagList("Trades", 10);
                this.sellTrades = new LOTRTradeEntry[sellTradesTags.tagCount()];
                for (int i = 0; i < sellTradesTags.tagCount(); ++i) {
                    final NBTTagCompound nbt = sellTradesTags.getCompoundTagAt(i);
                    final LOTRTradeEntry trade = LOTRTradeEntry.readFromNBT(nbt);
                    this.sellTrades[i] = trade;
                }
            }
        }
        if (data.hasKey("ShouldRefresh")) {
            this.shouldRefresh = data.getBoolean("ShouldRefresh");
        }
        this.timeSinceTrade = data.getInteger("TimeSinceTrade");
        this.valueSinceRefresh = data.getInteger("ValueSinceRefresh");
    }
    
    public void sendClientPacket(final EntityPlayer entityplayer) {
        final NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        final LOTRPacketTraderInfo packet = new LOTRPacketTraderInfo(nbt);
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
    }
    
    public void receiveClientPacket(final LOTRPacketTraderInfo packet) {
        final NBTTagCompound nbt = packet.traderData;
        this.readFromNBT(nbt);
    }
}
