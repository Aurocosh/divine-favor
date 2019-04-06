package aurocosh.divinefavor.common.custom_data.player.data.spell.focused_fury;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

// The default implementation of the capability. Holds all the logic.
public class FocusedFuryData {
    private ResourceLocation mobTypeId;

    public FocusedFuryData() {
        this.mobTypeId = new ResourceLocation("");
    }

    public ResourceLocation getMobTypeId() {
        return mobTypeId;
    }

    public void reset(){
        mobTypeId = new ResourceLocation("");
    }

    public void setMobTypeId(ResourceLocation typeId) {
        mobTypeId = typeId;
    }

    public void setFury(Entity entity) {
        mobTypeId = EntityList.getKey(entity);
    }

    public boolean hasFury(Entity entity) {
        return EntityList.getKey(entity).equals(mobTypeId);
    }

    public boolean hasFury() {
        return !mobTypeId.getPath().isEmpty();
    }

    public String getMobName() {
        String translationName = EntityList.getTranslationName(mobTypeId);
        return translationName == null ? "divinefavor:no_fury_assigned" : translationName;
    }
}
