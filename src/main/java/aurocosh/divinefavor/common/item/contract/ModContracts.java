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
            contracts.add(new ItemContract("capacity_minor_" + favor.getName(), "capacity/minor/" + favor.getName(), 0, favor, 0, 0, 100));
        for (ModFavor favor : favors)
            contracts.add(new ItemContract("capacity_major_" + favor.getName(), "capacity/major/" + favor.getName(), 0, favor, 0, 0, 500));
        for (ModFavor favor : favors)
            contracts.add(new ItemContract("regen_minor_" + favor.getName(), "regen/minor/" + favor.getName(), 1, favor, 20, 0, 0));
        for (ModFavor favor : favors)
            contracts.add(new ItemContract("regen_major_" + favor.getName(), "regen/major/" + favor.getName(), 1, favor, 200, 0, 0));
    }
}