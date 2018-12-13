package aurocosh.divinefavor.common.favor_sources.conditions;

import aurocosh.divinefavor.common.favor_sources.conditions.base.FavorCondition;
import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.player_data.favor.IFavorHandler;
import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.CheckForNull;

import static aurocosh.divinefavor.common.player_data.favor.FavorDataHandler.CAPABILITY_FAVOR;

public class MaxFavorCondition extends FavorCondition {
    private final ModFavor favor;
    private final int maximumValue;

    public MaxFavorCondition(ModFavor favor, int maximumValue) {
        this.favor = favor;
        this.maximumValue = maximumValue;
    }

    @Override
    public boolean isMet(EntityPlayer player, @CheckForNull Object context) {
        IFavorHandler favorHandler = player.getCapability(CAPABILITY_FAVOR, null);
        assert favorHandler != null;
        return favorHandler.getFavor(favor.id) < maximumValue;
    }
}
