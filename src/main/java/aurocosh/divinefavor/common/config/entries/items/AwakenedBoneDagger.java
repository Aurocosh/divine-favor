package aurocosh.divinefavor.common.config.entries.items;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class AwakenedBoneDagger {
    @Config.Name("Awakened dagger soul stealing speed")
    public float soulSteelingSpeed = 0.5f;
    @Config.Name("Awakened dagger soul theft duration")
    public int soulTheftDuration = UtilTick.minutesToTicks(5);
}
