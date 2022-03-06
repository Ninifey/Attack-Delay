// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.EntityCreature;

public class LOTREntityUtils
{
    public static EntityAITasks.EntityAITaskEntry removeAITask(final EntityCreature entity, final Class taskClass) {
        for (int i = 0; i < ((EntityLiving)entity).tasks.taskEntries.size(); ++i) {
            final EntityAITasks.EntityAITaskEntry taskEntry = ((EntityLiving)entity).tasks.taskEntries.get(i);
            if (taskClass.isAssignableFrom(taskEntry.action.getClass())) {
                ((EntityLiving)entity).tasks.removeTask(taskEntry.action);
                return taskEntry;
            }
        }
        for (int i = 0; i < ((EntityLiving)entity).targetTasks.taskEntries.size(); ++i) {
            final EntityAITasks.EntityAITaskEntry taskEntry = ((EntityLiving)entity).targetTasks.taskEntries.get(i);
            if (taskClass.isAssignableFrom(taskEntry.action.getClass())) {
                ((EntityLiving)entity).targetTasks.removeTask(taskEntry.action);
                return taskEntry;
            }
        }
        return null;
    }
}
