package aurocosh.divinefavor.common.registry;

import aurocosh.divinefavor.common.registry.common.CommonRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collection;
import java.util.HashMap;

public class RegistryMap<T extends IForgeRegistryEntry> {
    private HashMap<ResourceLocation,T> valueMap;

    public Collection<T> getValues(){
        return valueMap.values();
    }

    public RegistryMap() {
        this.valueMap = new HashMap<>();
    }

    public <K extends T> K register(K value) {
        valueMap.put(value.getRegistryName(), value);
        CommonRegistry.register(value);
        return value;
    }
}
