package aurocosh.divinefavor.common.favor_sources.favor_sources.base;

import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.util.helper_classes.TickCounter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public abstract class TickableFavorSource extends FavorSource {
    private final TickCounter tickCounter;

    public TickableFavorSource(ModFavor favor, int favorCount, ResourceLocation unlockAdvancements, ResourceLocation lockAdvancement, int tickRate) {
        super(favor, favorCount, unlockAdvancements, lockAdvancement);
        this.tickCounter = new TickCounter(tickRate);
    }

    public boolean IsTickNeeded(){
        return tickCounter.tick();
    }

    public abstract boolean IsFavorNeeded(EntityPlayer player);
}
