package aurocosh.divinefavor.common.config.entries.talismans.spell;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class MoltenSkin {
    @Config.Name("Favor cost")
    public int favorCost = 20;
    @Config.Name("Speed modifier")
    public float speedModifier = 0.14f;
    @Config.Name("Max time outside lava")
    public int maxTimeOutsideLava = UtilTick.INSTANCE.secondsToTicks(20f);
    @Config.Name("Damage delay")
    public int damageDelay = UtilTick.INSTANCE.secondsToTicks(2.5f);
    @Config.Name("Freezing damage")
    public float freezingDamage = 4;
}
