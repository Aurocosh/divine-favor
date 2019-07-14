package aurocosh.divinefavor.common.config.entries.items;

import net.minecraftforge.common.config.Config;

public class GooVial {
    public GooVial(int capacity) {
        this.capacity = capacity;
    }

    @Config.Name("Capacity")
    public int capacity;
}
