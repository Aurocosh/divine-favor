package aurocosh.divinefavor.common.favor_sources.builders;

import aurocosh.divinefavor.common.favor_sources.favor_sources.base.EventFavorSource;
import aurocosh.divinefavor.common.favor_sources.favor_sources.base.FavorEventType;
import aurocosh.divinefavor.common.favor_sources.favor_sources.base.FavorSource;
import aurocosh.divinefavor.common.favors.ModFavor;

public class EventFavorSourceBuilder extends FavorSourceBuilder<EventFavorSourceBuilder> {
    private FavorEventType eventType;

    public EventFavorSourceBuilder(ModFavor favor, int favorCount, FavorEventType eventType) {
        super(favor, favorCount);
        this.eventType = eventType;
    }

    @Override
    public FavorSource create() {
        return new EventFavorSource(favor, favorCount, unlockAdvancements, lockAdvancement, conditions, eventType);
    }
}
