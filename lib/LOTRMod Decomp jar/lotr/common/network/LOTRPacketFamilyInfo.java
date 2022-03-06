// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketFamilyInfo implements IMessage
{
    private int entityID;
    public int age;
    public boolean isMale;
    public String name;
    public boolean isDrunk;
    
    public LOTRPacketFamilyInfo() {
    }
    
    public LOTRPacketFamilyInfo(final int id, final int a, final boolean m, final String s, final boolean drunk) {
        this.entityID = id;
        this.age = a;
        this.isMale = m;
        this.name = s;
        this.isDrunk = drunk;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.entityID);
        data.writeInt(this.age);
        data.writeBoolean(this.isMale);
        if (this.name == null) {
            data.writeShort(-1);
        }
        else {
            final byte[] nameBytes = this.name.getBytes(Charsets.UTF_8);
            data.writeShort(nameBytes.length);
            data.writeBytes(nameBytes);
        }
        data.writeBoolean(this.isDrunk);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.entityID = data.readInt();
        this.age = data.readInt();
        this.isMale = data.readBoolean();
        final int nameLength = data.readShort();
        if (nameLength > -1) {
            this.name = data.readBytes(nameLength).toString(Charsets.UTF_8);
        }
        this.isDrunk = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketFamilyInfo, IMessage>
    {
        public IMessage onMessage(final LOTRPacketFamilyInfo packet, final MessageContext context) {
            final World world = LOTRMod.proxy.getClientWorld();
            final Entity entity = world.getEntityByID(packet.entityID);
            if (entity instanceof LOTREntityNPC) {
                ((LOTREntityNPC)entity).familyInfo.receiveData(packet);
            }
            return null;
        }
    }
}
