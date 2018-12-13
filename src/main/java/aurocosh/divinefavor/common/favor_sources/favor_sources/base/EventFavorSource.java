package aurocosh.divinefavor.common.favor_sources.favor_sources.base;

import aurocosh.divinefavor.common.favor_sources.conditions.base.FavorCondition;
import aurocosh.divinefavor.common.favors.ModFavor;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class EventFavorSource extends FavorSource{
    public final FavorEventType eventType;

    public EventFavorSource(ModFavor favor, int favorCount, ResourceLocation unlockAdvancements, ResourceLocation lockAdvancement, List<FavorCondition> conditions, FavorEventType eventType) {
        super(favor, favorCount, unlockAdvancements, lockAdvancement, conditions);
        this.eventType = eventType;
    }
}
