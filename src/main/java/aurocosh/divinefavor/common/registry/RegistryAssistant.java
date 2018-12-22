package aurocosh.divinefavor.common.registry;

import aurocosh.divinefavor.common.registry.common.CommonRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class RegistryAssistant<T extends IForgeRegistryEntry> {
    private HashMap<ResourceLocation,T> valueMap;

    public Collection<T> getValues(){
        return valueMap.values();
    }

    public <K extends T> List<K> getValues(Class clazz){
        List<K> values = new ArrayList<>();
        for (T value : valueMap.values())
            if (clazz.isInstance(value))
                values.add((K) value);
        return values;
    }

    public RegistryAssistant() {
        this.valueMap = new HashMap<>();
    }

    public <K extends T> K register(K value) {
        valueMap.put(value.getRegistryName(), value);
        CommonRegistry.register(value);
        return value;
    }
}
