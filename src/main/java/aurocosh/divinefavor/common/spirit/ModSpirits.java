package aurocosh.divinefavor.common.spirit;

import aurocosh.divinefavor.common.constants.ConstSpiritNames;
import aurocosh.divinefavor.common.registry.RegistryMap;
import aurocosh.divinefavor.common.registry.common.CommonRegistry;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public final class ModSpirits {
    private static RegistryMap<ModSpirit> spirits = new RegistryMap<>();

    public static ModSpirit allfire;
    public static ModSpirit timber;
    public static ModSpirit romol;

    public static void preInit() {
        allfire = spirits.register(
                new SpiritBuilder(ConstSpiritNames.ALLFIRE)
                        .addActivityPeriod(10, 14)
                        .create()
        );

        timber = spirits.register(
                new SpiritBuilder(ConstSpiritNames.TIMBER)
                        .addActivityPeriod(6, 12)
                        .create()
        );

        romol = spirits.register(
                new SpiritBuilder(ConstSpiritNames.ROMOL)
                        .addActivityPeriod(6, 18)
                        .create()
        );
    }
}