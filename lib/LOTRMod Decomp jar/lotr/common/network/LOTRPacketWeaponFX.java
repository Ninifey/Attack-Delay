// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketWeaponFX implements IMessage
{
    private Type type;
    private int entityID;
    
    public LOTRPacketWeaponFX() {
    }
    
    public LOTRPacketWeaponFX(final Type t, final Entity entity) {
        this.type = t;
        this.entityID = entity.getEntityId();
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeByte(this.type.ordinal());
        data.writeInt(this.entityID);
    }
    
    public void fromBytes(final ByteBuf data) {
        final int typeID = data.readByte();
        this.type = Type.values()[typeID];
        this.entityID = data.readInt();
    }
    
    public enum Type
    {
        MACE_SAURON, 
        STAFF_GANDALF_WHITE, 
        FIREBALL_GANDALF_WHITE, 
        INFERNAL, 
        CHILLING;
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketWeaponFX, IMessage>
    {
        public IMessage onMessage(final LOTRPacketWeaponFX packet, final MessageContext context) {
            final World world = LOTRMod.proxy.getClientWorld();
            final Entity entity = world.getEntityByID(packet.entityID);
            if (entity != null) {
                final double x = entity.posX;
                final double y = entity.boundingBox.minY;
                final double z = entity.posZ;
                final Random rand = world.rand;
                if (packet.type == Type.MACE_SAURON) {
                    for (int i = 0; i < 360; i += 2) {
                        final float angle = (float)Math.toRadians(i);
                        final double dist = 1.5;
                        final double d = dist * MathHelper.sin(angle);
                        final double d2 = dist * MathHelper.cos(angle);
                        world.spawnParticle("smoke", x + d, y + 0.1, z + d2, d * 0.2, 0.0, d2 * 0.2);
                    }
                }
                else if (packet.type == Type.STAFF_GANDALF_WHITE) {
                    for (int i = 0; i < 360; i += 2) {
                        final float angle = (float)Math.toRadians(i);
                        final double dist = 1.5;
                        final double d = dist * MathHelper.sin(angle);
                        final double d2 = dist * MathHelper.cos(angle);
                        LOTRMod.proxy.spawnParticle("blueFlame", x + d, y + 0.1, z + d2, d * 0.2, 0.0, d2 * 0.2);
                    }
                }
                else if (packet.type == Type.FIREBALL_GANDALF_WHITE) {
                    LOTRMod.proxy.spawnParticle("gandalfFireball", x, y, z, 0.0, 0.0, 0.0);
                }
                else if (packet.type == Type.INFERNAL) {
                    for (int i = 0; i < 20; ++i) {
                        final double d3 = x;
                        final double d4 = y + entity.height * 0.7f;
                        final double d5 = z;
                        final float angleXZ = rand.nextFloat() * 3.1415927f * 2.0f;
                        final float angleY = rand.nextFloat() * 3.1415927f * 2.0f;
                        final float speed = MathHelper.randomFloatClamp(rand, 0.1f, 0.15f);
                        double d6 = MathHelper.cos(angleXZ) * MathHelper.cos(angleY) * speed;
                        double d7 = MathHelper.sin(angleY) * speed;
                        double d8 = MathHelper.sin(angleXZ) * MathHelper.cos(angleY) * speed;
                        d7 += 0.15000000596046448;
                        d6 += entity.posX - entity.lastTickPosX;
                        d7 += entity.posY - entity.lastTickPosY;
                        d8 += entity.posZ - entity.lastTickPosZ;
                        world.spawnParticle("flame", d3, d4, d5, d6, d7, d8);
                    }
                }
                else if (packet.type == Type.CHILLING) {
                    for (int i = 0; i < 40; ++i) {
                        final double d3 = x;
                        final double d4 = y + entity.height * 0.7f;
                        final double d5 = z;
                        final float angleXZ = rand.nextFloat() * 3.1415927f * 2.0f;
                        final float angleY = rand.nextFloat() * 3.1415927f * 2.0f;
                        final float speed = MathHelper.randomFloatClamp(rand, 0.1f, 0.2f);
                        double d6 = MathHelper.cos(angleXZ) * MathHelper.cos(angleY) * speed;
                        double d7 = MathHelper.sin(angleY) * speed;
                        double d8 = MathHelper.sin(angleXZ) * MathHelper.cos(angleY) * speed;
                        d6 += entity.posX - entity.lastTickPosX;
                        d7 += entity.posY - entity.lastTickPosY;
                        d8 += entity.posZ - entity.lastTickPosZ;
                        LOTRMod.proxy.spawnParticle("chill", d3, d4, d5, d6, d7, d8);
                    }
                }
            }
            return null;
        }
    }
}
