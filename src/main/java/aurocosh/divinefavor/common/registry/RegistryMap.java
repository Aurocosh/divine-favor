package aurocosh.divinefavor.common.registry;

import aurocosh.divinefavor.common.registry.common.CommonRegistry;
import aurocosh.divinefavor.common.registry.interfaces.IRegistryContainer;
import aurocosh.divinefavor.common.registry.interfaces.IValueRegister;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collection;
import java.util.HashMap;

public class RegistryMap<T extends IForgeRegistryEntry> implements IRegistryContainer, IValueRegister<T> {
    private HashMap<ResourceLocation,T> valueMap;

    public Collection<T> getValues(){
        return valueMap.values();
    }

    public RegistryMap() {
        this.valueMap = new HashMap<>();
    }

    public T register(T value) {
        valueMap.put(value.getRegistryName(), value);
        CommonRegistry.register(value);
        return value;
    }
}
