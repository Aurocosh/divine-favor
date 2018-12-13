package aurocosh.divinefavor.common.spirit;

import aurocosh.divinefavor.common.constants.ConstSpiritNames;
import aurocosh.divinefavor.common.registry.ModRegistries;

public final class ModSpirits {
    public static ModSpirit allfire;
    public static ModSpirit timber;
    public static ModSpirit romol;

    public static void preInit() {
        allfire =  ModRegistries.spirits.register(
                new SpiritBuilder(ConstSpiritNames.ALLFIRE)
                        .addActivityPeriod(10, 14)
                        .create()
        );

        timber = ModRegistries.spirits.register(
                new SpiritBuilder(ConstSpiritNames.TIMBER)
                        .addActivityPeriod(6, 12)
                        .create()
        );

        romol = ModRegistries.spirits.register(
                new SpiritBuilder(ConstSpiritNames.ROMOL)
                        .addActivityPeriod(6, 18)
                        .create()
        );
    }
}