package aurocosh.divinefavor.common.item.contract;

import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.spirit.ModSpirit;

import java.util.Collection;

public final class ModContracts {
    public static void preInit() {
        Collection<ModSpirit> spirits = ModRegistries.spirits.getValues();
        for (ModSpirit spirit : spirits)
            ModRegistries.items.register(new ItemContract(spirit));
    }
}