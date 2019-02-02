package aurocosh.divinefavor.common.item.contract;

import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.registry.ModRegistries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ModContracts {
    public static List<ItemContract> contracts = new ArrayList<>();

    public static void preInit() {
        Collection<ModFavor> favors = ModRegistries.favors.getValues();
        for (ModFavor favor : favors)
            contracts.add(new ItemContract("capacity_" + favor.getName(), "capacity/" + favor.getName(), 0, favor, 0, 0, 100));
        for (ModFavor favor : favors)
            contracts.add(new ItemContract("regen_" + favor.getName(), "regen/" + favor.getName(), 1, favor, 20, 0, 0));
    }
}