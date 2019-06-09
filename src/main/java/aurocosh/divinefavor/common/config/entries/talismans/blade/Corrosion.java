package aurocosh.divinefavor.common.config.entries.talismans.blade;

import aurocosh.divinefavor.common.config.data.IntInterval;
import net.minecraftforge.common.config.Config;

public class Corrosion {
    @Config.Name("Favor cost")
    public int favorCost = 30;
    @Config.Name("Corrosion chance")
    public float corrosionChance = 0.25f;
    @Config.Name("Corrosion power")
    public IntInterval corrosionPower = new IntInterval(1,4);
}
