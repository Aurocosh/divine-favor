package aurocosh.divinefavor.common.registry.interfaces;

import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IValueRegister<T extends IForgeRegistryEntry> {
    T register(T value);
}
