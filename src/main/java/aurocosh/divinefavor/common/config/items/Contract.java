package aurocosh.divinefavor.common.config.items;

import net.minecraftforge.common.config.Config;

public class Contract {
    @Config.Name("Regeneration")
    public int regen;
    @Config.Name("Favor minimum")
    public int minimum;
    @Config.Name("Favor maximum")
    public int maximum;

    public Contract(int regen, int minimum, int maximum) {
        this.regen = regen;
        this.minimum = minimum;
        this.maximum = maximum;
    }
}
