package aurocosh.divinefavor.common.favors;

import aurocosh.divinefavor.common.constants.LibMisc;
import com.google.gson.annotations.Expose;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class ModFavor extends IForgeRegistryEntry.Impl<ModFavor>{
    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public int getId() {
        return id;
    }

    @Expose
    private String name;
    private String tag;
    private int id;

    public ModFavor(String name, String tag, int id) {
        this.name = name;
        this.tag = tag;
        this.id = id;
        setRegistryName(LibMisc.MOD_ID,"favor." + name);
    }
}
