package aurocosh.divinefavor.common.favor;

import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.custom_data.player.data.talisman_uses.FavorValue;
import aurocosh.divinefavor.common.lib.interfaces.IIndexedEntry;
import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class ModFavor extends IForgeRegistryEntry.Impl<ModFavor> implements IIndexedEntry {
    private final int id;
    private final String name;
    private int startingValue;
    private int startingLimit;
    private int maxLimit;

    // Talisman functions
    public ModFavor(String name, int startingValue, int startingLimit, int maxLimit) {
        this.name = name;
        this.startingValue = startingValue;
        this.startingLimit = startingLimit;
        this.maxLimit = maxLimit;

        setRegistryName(ResourceNamer.getFullName("favor", name));
        id = ModMappers.favors.register(this);
        ModRegistries.favors.register(this);
    }

    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }

    public FavorValue getDefaultValues() {
        return new FavorValue(startingValue, 0, startingLimit);
    }
}