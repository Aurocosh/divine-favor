package aurocosh.divinefavor.common.favor_sources.favor_sources.base;

import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.player_data.favor.IFavorHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import static aurocosh.divinefavor.common.player_data.favor.FavorDataHandler.CAPABILITY_FAVOR;

public abstract class RegenFavorSource extends TickableFavorSource {
    private final int upperRegenLimit;

    public RegenFavorSource(ModFavor favor, int favorCount, ResourceLocation unlockAdvancements, ResourceLocation lockAdvancement, int tickRate, int upperRegenLimit) {
        super(favor, favorCount, unlockAdvancements, lockAdvancement, tickRate);
        this.upperRegenLimit = upperRegenLimit;
    }

    @Override
    public boolean IsFavorNeeded(EntityPlayer player) {
        IFavorHandler favorHandler = player.getCapability(CAPABILITY_FAVOR, null);
        if(favorHandler == null)
            return false;
        int favorValue = favorHandler.getFavor(favor.id);
        return favorValue + favorCount <= upperRegenLimit;
    }
}
