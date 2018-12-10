package aurocosh.divinefavor.common.spirit;

import aurocosh.divinefavor.common.constants.ConstSpiritNames;
import aurocosh.divinefavor.common.registry.CommonRegistry;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public final class ModSpirits {
    public static ModSpirit allfire;
    public static ModSpirit timber;
    public static ModSpirit romol;
    private static Map<ResourceLocation, ModSpirit> spirits = new HashMap<>();

    public static void preInit() {
        allfire = register(
                new SpiritBuilder(ConstSpiritNames.ALLFIRE)
                        .addActivityPeriod(10, 14)
                        .create()
        );

        timber = register(
                new SpiritBuilder(ConstSpiritNames.TIMBER)
                        .addActivityPeriod(6, 12)
                        .create()
        );

        romol = register(
                new SpiritBuilder(ConstSpiritNames.ROMOL)
                        .addActivityPeriod(6, 18)
                        .create()
        );
    }

    public static ModSpirit register(ModSpirit spirit) {
        spirits.put(spirit.getRegistryName(), spirit);
        CommonRegistry.register(spirit);
        return spirit;
    }
}