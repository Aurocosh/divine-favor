package aurocosh.divinefavor.common.registry.interfaces;

import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collection;

public interface IRegistryContainer<T extends IForgeRegistryEntry>  {
    Collection<T> getValues();
}
