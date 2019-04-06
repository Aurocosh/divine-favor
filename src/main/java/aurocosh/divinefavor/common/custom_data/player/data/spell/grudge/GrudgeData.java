package aurocosh.divinefavor.common.custom_data.player.data.spell.grudge;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

// The default implementation of the capability. Holds all the logic.
public class GrudgeData {
    private ResourceLocation mobTypeId;

    public GrudgeData() {
        mobTypeId = new ResourceLocation("");
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

    public void setGrudge(Entity entity) {
        mobTypeId = EntityList.getKey(entity);
    }

    public boolean hasGrudge(Entity entity) {
        return EntityList.getKey(entity).equals(mobTypeId);
    }

    public String getMobName() {
        String translationName = EntityList.getTranslationName(mobTypeId);
        return translationName == null ? "divinefavor:no_grudge_assigned" : translationName;
    }
}
