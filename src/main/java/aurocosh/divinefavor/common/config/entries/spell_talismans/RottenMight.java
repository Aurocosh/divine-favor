package aurocosh.divinefavor.common.config.entries.spell_talismans;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class RottenMight {
    @Config.Name("Favor cost")
    public int favorCost = 20;
    @Config.Name("Speed modifier")
    public double speedModifier = 0.5f;
    @Config.Name("Damage modifier")
    public double damageModifier = 0.4f;
    @Config.Name("Infection chance")
    public float infectionChance = 0.4f;
    @Config.Name("Burn time")
    public int burnTime = 2;
    @Config.Name("Tolerable light level")
    public int tolerableLightLevel = 8;
    @Config.Name("Blindness duration")
    public int blindnessDuration = UtilTick.INSTANCE.secondsToTicks(2.5f);
    @Config.Name("Extra fire damage")
    public float fireDamage = 4;
    @Config.Name("Extra smite damage")
    @Config.Comment("Extra damage per level of smite enchantment")
    public float smiteDamage = 4;
}