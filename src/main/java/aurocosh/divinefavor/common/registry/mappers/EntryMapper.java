package aurocosh.divinefavor.common.registry.mappers;

import aurocosh.divinefavor.common.lib.interfaces.IIndexedEntry;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EntryMapper<T extends IForgeRegistryEntry & IIndexedEntry> {
    private final BiMap<Integer, T> ids;
    private final BiMap<ResourceLocation, T> names;

    public EntryMapper() {
        ids = HashBiMap.create();
        names = HashBiMap.create();
    }

    public Map<Integer, T> getIdMap() {
        return Collections.unmodifiableMap(ids);
    }

    public Map<T, Integer> getIdMapinversed() {
        return Collections.unmodifiableMap(ids.inverse());
    }

    public Map<ResourceLocation, T> getNameMap() {
        return Collections.unmodifiableMap(names);
    }

    public Map<T, ResourceLocation> getNameMapinversed() {
        return Collections.unmodifiableMap(names.inverse());
    }

    public void register(List<T> values) {
        for (T value : values)
            register(value);
    }

    public void register(T value) {
        ids.put(value.getId(), value);
        names.put(value.getRegistryName(), value);
    }
}
