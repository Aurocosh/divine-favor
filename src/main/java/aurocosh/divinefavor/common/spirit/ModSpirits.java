package aurocosh.divinefavor.common.spirit;

import aurocosh.divinefavor.common.registry.ModRegistries;

public final class ModSpirits {
    public static ModSpirit allfire;
    public static ModSpirit arbow;
    public static ModSpirit blizrabi;
    public static ModSpirit redwind;
    public static ModSpirit romol;
    public static ModSpirit squarefury;
    public static ModSpirit timber;

    public static void preInit() {
        allfire = ModRegistries.spirits.register(
                new SpiritBuilder("allfire")
                        .addActivityPeriod(10, 14)
                        .create()
        );
        arbow = ModRegistries.spirits.register(
                new SpiritBuilder("arbow")
                        .addActivityPeriod(10, 14)
                        .create()
        );
        blizrabi = ModRegistries.spirits.register(
                new SpiritBuilder("blizrabi")
                        .addActivityPeriod(10, 14)
                        .create()
        );
        redwind = ModRegistries.spirits.register(
                new SpiritBuilder("redwind")
                        .addActivityPeriod(6, 18)
                        .create()
        );
        romol = ModRegistries.spirits.register(
                new SpiritBuilder("romol")
                        .addActivityPeriod(6, 18)
                        .create()
        );
        squarefury = ModRegistries.spirits.register(
                new SpiritBuilder("squarefury")
                        .addActivityPeriod(15, 21)
                        .create()
        );
        timber = ModRegistries.spirits.register(
                new SpiritBuilder("timber")
                        .addActivityPeriod(6, 12)
                        .create()
        );
    }
}