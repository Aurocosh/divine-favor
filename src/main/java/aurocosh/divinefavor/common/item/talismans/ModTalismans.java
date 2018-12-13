package aurocosh.divinefavor.common.item.talismans;

import aurocosh.divinefavor.common.constants.items.ConstTalismanNames;
import aurocosh.divinefavor.common.favors.ModFavors;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.spell.common.ModSpells;

public final class ModTalismans {
    public static ModItem arrowThrowTalisman;
    public static ModItem bonemeal;
    public static ModItem empower_axe;
    public static ModItem empower_pickaxe;
    public static ModItem fell_tree;
    public static ModItem ignition;
    public static ModItem lavawalking;
    public static ModItem small_fireball_throw;
    public static ModItem snowball_throw;
    public static ModItem stone_fever;
    public static ModItem stoneball_throw;
    public static ModItem waterwalking;
    public static ModItem wooden_punch;
    public static ModItem crushing_palm;

    public static void preInit() {
        arrowThrowTalisman = ModRegistries.items.register(new TalismanBuilder(ConstTalismanNames.ARROW_THROW, ModFavors.favor_of_allfire, 1)
                .castOnUse()
                .castOnRighClick()
                .create());
        bonemeal = ModRegistries.items.register(new TalismanBuilder(ConstTalismanNames.BONEMEAL, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.bonemeal)
                .castOnUse()
                .create());
        empower_axe = ModRegistries.items.register(new TalismanBuilder(ConstTalismanNames.EMPOWER_AXE, ModFavors.favor_of_timber, 1)
                .setSpell(ModSpells.empower_axe)
                .castOnUse()
                .castOnRighClick()
                .create());
        empower_pickaxe = ModRegistries.items.register(new TalismanBuilder(ConstTalismanNames.EMPOWER_PICKAXE, ModFavors.favor_of_romol, 1)
                .setSpell(ModSpells.empower_pickaxe)
                .castOnUse()
                .castOnRighClick()
                .create());
        fell_tree = ModRegistries.items.register(new TalismanBuilder(ConstTalismanNames.FELL_TREE, ModFavors.favor_of_timber, 1)
                .setSpell(ModSpells.fell_tree)
                .castOnUse()
                .create());
        ignition = ModRegistries.items.register(new TalismanBuilder(ConstTalismanNames.IGNITION, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.ignition)
                .castOnUse()
                .create());
        lavawalking = ModRegistries.items.register(new TalismanBuilder(ConstTalismanNames.LAVAWALKING, ModFavors.favor_of_allfire, 2)
                .setSpell(ModSpells.lavawalking)
                .castOnUse()
                .castOnRighClick()
                .create());
        small_fireball_throw = ModRegistries.items.register(new TalismanBuilder(ConstTalismanNames.SMALL_FIREBALL_THROW, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.small_fireball_throw)
                .castOnRighClick()
                .create());
        snowball_throw = ModRegistries.items.register(new TalismanBuilder(ConstTalismanNames.SNOWBALL_THROW, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.snowball_throw)
                .castOnUse()
                .castOnRighClick()
                .create());
        stone_fever = ModRegistries.items.register(new TalismanBuilder(ConstTalismanNames.STONE_FEVER, ModFavors.favor_of_romol, 1)
                .setSpell(ModSpells.stone_fever)
                .castOnUse()
                .castOnRighClick()
                .create());
        stoneball_throw = ModRegistries.items.register(new TalismanBuilder(ConstTalismanNames.STONEBALL_THROW, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.stoneball_throw)
                .castOnUse()
                .castOnRighClick()
                .create());
        waterwalking = ModRegistries.items.register(new TalismanBuilder(ConstTalismanNames.WATERWALKING, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.waterwalking)
                .castOnUse()
                .castOnRighClick()
                .create());
        wooden_punch = ModRegistries.items.register(new TalismanBuilder(ConstTalismanNames.WOODEN_PUNCH, ModFavors.favor_of_timber, 2)
                .setSpell(ModSpells.wooden_punch)
                .castOnUse()
                .castOnRighClick()
                .create());
        crushing_palm = ModRegistries.items.register(new TalismanBuilder(ConstTalismanNames.CRUSHING_PALM, ModFavors.favor_of_romol, 2)
                .setSpell(ModSpells.crushing_palm)
                .castOnUse()
                .castOnRighClick()
                .create());
    }

    public static void init() {
    }
}