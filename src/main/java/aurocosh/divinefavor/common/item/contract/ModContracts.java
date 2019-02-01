package aurocosh.divinefavor.common.item.contract;

import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ModContracts {
    public static List<ItemContract> contracts = new ArrayList<>();

    public static void preInit() {
        Collection<ModSpirit> spirits = ModRegistries.spirits.getValues();
        for (ModSpirit spirit : spirits)
            contracts.add(new ItemContract(spirit));
    }
}