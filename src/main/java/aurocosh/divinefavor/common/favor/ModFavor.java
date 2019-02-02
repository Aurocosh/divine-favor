package aurocosh.divinefavor.common.favor;

import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.lib.interfaces.IIndexedEntry;
import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class ModFavor extends IForgeRegistryEntry.Impl<ModFavor> implements IIndexedEntry {
    private final int id;
    private final String name;
    private final ResourceLocation icon;
    // Talisman functions

    public ModFavor(String name) {
        this.name = name;
        this.icon = new ResourceLocation(ConstResources.PREFIX_FAVOR_ICONS + name + ".png");

        setRegistryName(ResourceNamer.getFullName("favor", name));
        id = ModMappers.favors.register(this);
        ModRegistries.favors.register(this);
    }

    public String getName() {
        return name;
    }

    public ResourceLocation getIcon() {
        return icon;
    }

    @Override
    public int getId() {
        return id;
    }
}