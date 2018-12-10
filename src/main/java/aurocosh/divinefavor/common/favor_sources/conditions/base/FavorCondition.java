package aurocosh.divinefavor.common.favor_sources.conditions.base;

import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.CheckForNull;

public abstract class FavorCondition {
    public abstract boolean isMet(EntityPlayer player, @CheckForNull Object context);
}
