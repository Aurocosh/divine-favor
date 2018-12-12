package aurocosh.divinefavor.common.registry.interfaces;

import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IMetaContainer<T extends IVariant & IForgeRegistryEntry> {
    int getMeta(T variant);
    T getByMeta(int meta);

    String[] getNames();
}