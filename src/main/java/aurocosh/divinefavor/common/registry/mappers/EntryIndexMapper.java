package aurocosh.divinefavor.common.registry.mappers;

import aurocosh.divinefavor.common.lib.interfaces.IIndexedEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.*;

public class EntryIndexMapper<T extends IForgeRegistryEntry & IIndexedEntry> {
    private final ArrayList<T> values;
    private final Map<ResourceLocation, T> names;

    public EntryIndexMapper() {
        values = new ArrayList<>();
        names = new HashMap<>();
    }

    public T get(int id) {
        return values.get(id);
    }

    public List<T> getValues() {
        return Collections.unmodifiableList(values);
    }

    public Map<ResourceLocation, T> getNameMap() {
        return Collections.unmodifiableMap(names);
    }

    public int register(T value) {
        int id = values.size();
        values.add(value);
        names.put(value.getRegistryName(),value);
        return id;
    }
}
