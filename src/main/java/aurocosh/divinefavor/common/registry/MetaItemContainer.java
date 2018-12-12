package aurocosh.divinefavor.common.registry;

import aurocosh.divinefavor.common.registry.interfaces.IMetaContainer;
import aurocosh.divinefavor.common.registry.interfaces.IValueRegister;
import aurocosh.divinefavor.common.registry.interfaces.IVariant;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.*;

public class MetaItemContainer<T extends IVariant & IForgeRegistryEntry> extends RegistryMap<T> implements IMetaContainer<T>, IValueRegister<T> {
    private final List<T> variants = new ArrayList<>();

    public int getMeta(T variant){
        return variants.indexOf(variant);
    }

    public T getByMeta(int meta){
        if(meta >= variants.size())
            return null;
        return variants.get(meta);
    }

    public String[] getNames() {
        String[] names = new String[variants.size()];
        for (int i = 0; i < variants.size(); i++)
            names[i] = variants.get(i).getName();
        return names;
    }

    @Override
    public T register(T value) {
        variants.add(value);
        return super.register(value);
    }
}