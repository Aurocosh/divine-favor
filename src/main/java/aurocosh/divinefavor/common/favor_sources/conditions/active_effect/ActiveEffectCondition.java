package aurocosh.divinefavor.common.favor_sources.conditions.active_effect;

import aurocosh.divinefavor.common.favor_sources.conditions.base.FavorCondition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import javax.annotation.CheckForNull;

public class ActiveEffectCondition extends FavorCondition {
    private final Potion potion;
    private final DenyCondition denyCondition;

    public ActiveEffectCondition(Potion potion, DenyCondition denyCondition) {
        this.potion = potion;
        this.denyCondition = denyCondition;
    }

    @Override
    public boolean isMet(EntityPlayer player, @CheckForNull Object context) {
        PotionEffect effect = player.getActivePotionEffect(potion);
        if(denyCondition == DenyCondition.DENY_IF_ACTIVE)
            return effect == null;
        return effect != null;
    }
}
