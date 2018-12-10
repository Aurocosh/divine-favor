package aurocosh.divinefavor.common.favor_sources.favor_sources.base;

import aurocosh.divinefavor.common.favor_sources.conditions.base.FavorCondition;
import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.lib.TickCounter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class TickableFavorSource extends FavorSource {
    private final TickCounter tickCounter;

    public TickableFavorSource(ModFavor favor, int favorCount, ResourceLocation unlockAdvancements, ResourceLocation lockAdvancement, List<FavorCondition> conditions, int tickRate) {
        super(favor, favorCount, unlockAdvancements, lockAdvancement, conditions);
        this.tickCounter = new TickCounter(tickRate);
    }

    public boolean IsTickNeeded(){
        return tickCounter.tick();
    }
}
