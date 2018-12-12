package aurocosh.divinefavor.common.item.talismans;

import aurocosh.divinefavor.common.constants.items.ConstTalismanNames;
import aurocosh.divinefavor.common.favors.ModFavors;
import aurocosh.divinefavor.common.registry.MetaItemContainer;
import aurocosh.divinefavor.common.registry.interfaces.IMetaContainer;
import aurocosh.divinefavor.common.spell.common.ModSpells;

public final class ModTalismans {
    private static MetaItemContainer<Talisman> talismans = new MetaItemContainer<>();

    public static Talisman arrowThrowTalisman;
    public static Talisman bonemeal;
    public static Talisman empower_axe;
    public static Talisman fell_tree;
    public static Talisman ignition;
    public static Talisman lavawalking;
    public static Talisman small_fireball_throw;
    public static Talisman snowball_throw;
    public static Talisman stone_fever;
    public static Talisman stoneball_throw;
    public static Talisman waterwalking;
    public static Talisman wooden_punch;

    public static IMetaContainer<Talisman> getMetaContainer() {
        return talismans;
    }

    public static void preInit() {
        arrowThrowTalisman = talismans.register(new TalismanBuilder(ConstTalismanNames.ARROW_THROW, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.arrow_throw)
                .castOnUse()
                .castOnRighClick()
                .create());
        bonemeal = talismans.register(new TalismanBuilder(ConstTalismanNames.BONEMEAL, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.bonemeal)
                .castOnUse()
                .create());
        empower_axe = talismans.register(new TalismanBuilder(ConstTalismanNames.EMPOWER_AXE, ModFavors.favor_of_timber, 1)
                .setSpell(ModSpells.empower_axe)
                .castOnUse()
                .castOnRighClick()
                .create());
        fell_tree = talismans.register(new TalismanBuilder(ConstTalismanNames.FELL_TREE, ModFavors.favor_of_timber, 1)
                .setSpell(ModSpells.fell_tree)
                .castOnUse()
                .create());
        ignition = talismans.register(new TalismanBuilder(ConstTalismanNames.IGNITION, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.ignition)
                .castOnUse()
                .create());
        lavawalking = talismans.register(new TalismanBuilder(ConstTalismanNames.LAVAWALKING, ModFavors.favor_of_allfire, 2)
                .setSpell(ModSpells.lavawalking)
                .castOnUse()
                .castOnRighClick()
                .create());
        small_fireball_throw = talismans.register(new TalismanBuilder(ConstTalismanNames.SMALL_FIREBALL_THROW, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.small_fireball_throw)
                .castOnRighClick()
                .create());
        snowball_throw = talismans.register(new TalismanBuilder(ConstTalismanNames.SNOWBALL_THROW, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.snowball_throw)
                .castOnUse()
                .castOnRighClick()
                .create());
        stone_fever = talismans.register(new TalismanBuilder(ConstTalismanNames.STONE_FEVER, ModFavors.favor_of_romol, 1)
                .setSpell(ModSpells.stone_fever)
                .castOnUse()
                .castOnRighClick()
                .create());
        stoneball_throw = talismans.register(new TalismanBuilder(ConstTalismanNames.STONEBALL_THROW, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.stoneball_throw)
                .castOnUse()
                .castOnRighClick()
                .create());
        waterwalking = talismans.register(new TalismanBuilder(ConstTalismanNames.WATERWALKING, ModFavors.favor_of_allfire, 1)
                .setSpell(ModSpells.waterwalking)
                .castOnUse()
                .castOnRighClick()
                .create());
        wooden_punch = talismans.register(new TalismanBuilder(ConstTalismanNames.WOODEN_PUNCH, ModFavors.favor_of_timber, 2)
                .setSpell(ModSpells.wooden_punch)
                .castOnUse()
                .castOnRighClick()
                .create());
    }

    public static void init() {
    }
}