package aurocosh.divinefavor.common.favors;

import aurocosh.divinefavor.common.core.ResourceNamer;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class ModFavor extends IForgeRegistryEntry.Impl<ModFavor> {
    public final String name;
    public final String tag;
    public final int id;

    public ModFavor(String name, String tag, int id) {
        this.name = name;
        this.tag = tag;
        this.id = id;

        setRegistryName(ResourceNamer.getFullName("favor", name));
    }
}
