package aurocosh.divinefavor.common.item.talismans;

import aurocosh.divinefavor.common.favors.ModFavors;
import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.spell.common.ModSpells;

public final class ModTalismans {
    public static ItemTalisman arrowThrowTalisman;
    public static ItemTalisman bonemeal;
    public static ItemTalisman combustion;
    public static ItemTalisman crushing_palm;
    public static ItemTalisman empower_axe;
    public static ItemTalisman empower_pickaxe;
    public static ItemTalisman fell_tree;
    public static ItemTalisman ignition;
    public static ItemTalisman lavawalking;
    public static ItemTalisman searing_pulse;
    public static ItemTalisman small_fireball_throw;
    public static ItemTalisman snowball_throw;
    public static ItemTalisman stone_fever;
    public static ItemTalisman stoneball_throw;
    public static ItemTalisman waterwalking;
    public static ItemTalisman wooden_punch;

    public static void preInit() {
        arrowThrowTalisman = ModRegistries.items.register(new TalismanBuilder("arrow_throw", ModFavors.favor_of_allfire, 1)
                .castOnUse()
                .castOnRighClick()
                .create());
        bonemeal = ModRegistries.items.register(new TalismanBuilder("bonemeal", ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.bonemeal)
                .castOnUse()
                .create());
        combustion = ModRegistries.items.register(new TalismanBuilder("combustion", ModFavors.favor_of_allfire, 3)
                .setSpell(ModSpells.combustion)
                .castOnUse()
                .create());
        empower_axe = ModRegistries.items.register(new TalismanBuilder("empower_axe", ModFavors.favor_of_timber, 1)
                .setSpell(ModSpells.empower_axe)
                .castOnUse()
                .castOnRighClick()
                .create());
        empower_pickaxe = ModRegistries.items.register(new TalismanBuilder("empower_pickaxe", ModFavors.favor_of_romol, 1)
                .setSpell(ModSpells.empower_pickaxe)
                .castOnUse()
                .castOnRighClick()
                .create());
        fell_tree = ModRegistries.items.register(new TalismanBuilder("fell_tree", ModFavors.favor_of_timber, 1)
                .setSpell(ModSpells.fell_tree)
                .castOnUse()
                .create());
        ignition = ModRegistries.items.register(new TalismanBuilder("ignition", ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.ignition)
                .castOnUse()
                .create());
        lavawalking = ModRegistries.items.register(new TalismanBuilder("lavawalking", ModFavors.favor_of_allfire, 2)
                .setSpell(ModSpells.lavawalking)
                .castOnUse()
                .castOnRighClick()
                .create());
        searing_pulse = ModRegistries.items.register(new TalismanBuilder("searing_pulse", ModFavors.favor_of_allfire, 3)
                .setSpell(ModSpells.searing_pulse)
                .castOnUse()
                .create());
        small_fireball_throw = ModRegistries.items.register(new TalismanBuilder("small_fireball_throw", ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.small_fireball_throw)
                .castOnRighClick()
                .create());
        snowball_throw = ModRegistries.items.register(new TalismanBuilder("snowball_throw", ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.snowball_throw)
                .castOnUse()
                .castOnRighClick()
                .create());
        stone_fever = ModRegistries.items.register(new TalismanBuilder("stone_fever", ModFavors.favor_of_romol, 1)
                .setSpell(ModSpells.stone_fever)
                .castOnUse()
                .castOnRighClick()
                .create());
        stoneball_throw = ModRegistries.items.register(new TalismanBuilder("stoneball_throw", ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.stoneball_throw)
                .castOnUse()
                .castOnRighClick()
                .create());
        waterwalking = ModRegistries.items.register(new TalismanBuilder("waterwalking", ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.waterwalking)
                .castOnUse()
                .castOnRighClick()
                .create());
        wooden_punch = ModRegistries.items.register(new TalismanBuilder("wooden_punch", ModFavors.favor_of_timber, 2)
                .setSpell(ModSpells.wooden_punch)
                .castOnUse()
                .castOnRighClick()
                .create());
        crushing_palm = ModRegistries.items.register(new TalismanBuilder("crushing_palm", ModFavors.favor_of_romol, 2)
                .setSpell(ModSpells.crushing_palm)
                .castOnUse()
                .castOnRighClick()
                .create());
    }

    public static void init() {
    }
}