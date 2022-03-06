// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import java.lang.reflect.Field;
import cpw.mods.fml.common.FMLLog;
import lotr.common.LOTRReflection;
import net.minecraft.entity.Entity;
import net.minecraft.command.IEntitySelector;
import lotr.common.entity.npc.LOTREntityHuornBase;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAvoidEntity;

public class LOTREntityAIAvoidHuorn extends EntityAIAvoidEntity
{
    public LOTREntityAIAvoidHuorn(final EntityCreature entity, final float range, final double near, final double far) {
        super(entity, (Class)LOTREntityHuornBase.class, range, near, far);
        try {
            final IEntitySelector replaceSelect = (IEntitySelector)new IEntitySelector() {
                public boolean isEntityApplicable(final Entity target) {
                    if (target.isEntityAlive() && entity.getEntitySenses().canSee(target)) {
                        final LOTREntityHuornBase huorn = (LOTREntityHuornBase)target;
                        return huorn.isHuornActive();
                    }
                    return false;
                }
            };
            final Field[] fields;
            final Field[] fs = fields = EntityAIAvoidEntity.class.getFields();
            for (final Field f : fields) {
                final Object inst = f.get(this);
                if (inst == super.field_98218_a) {
                    LOTRReflection.unlockFinalField(f);
                    f.set(this, replaceSelect);
                    break;
                }
            }
        }
        catch (Exception e) {
            FMLLog.warning("LOTR: Error constructing Avoid Huorn AI", new Object[0]);
            e.printStackTrace();
        }
    }
}
