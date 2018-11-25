package aurocosh.divinefavor.common.spell.base;

import aurocosh.divinefavor.common.lib.LibSpellNames;
import aurocosh.divinefavor.common.spell.*;

public final class ModSpells {
    public static Spell arrow_throw;
    public static Spell bonemeal;
    public static Spell empower_axe;
    public static Spell fell_tree;
    public static Spell ignition;
    public static Spell lavawalking;
    public static Spell snowball_throw;
    public static Spell stoneball_throw;
    public static Spell small_fireball_throw;
    public static Spell waterwalking;

    public static void init() {

        arrow_throw = register(new SpellArrowThrow(), LibSpellNames.ARROW_THROW);
        bonemeal = register(new SpellBonemeal(), LibSpellNames.BONEMEAL);
        empower_axe = register(new SpellEmpowerAxe(), LibSpellNames.EMPOWER_AXE);
        fell_tree = register(new SpellFellTree(), LibSpellNames.FELL_TREE);
        ignition = register(new SpellIgnition(), LibSpellNames.IGNITION);
        lavawalking = register(new SpellLavawalking(), LibSpellNames.LAVAWALKING);
        snowball_throw = register(new SpellSnowballThrow(), LibSpellNames.SNOWBALL_THROW);
        stoneball_throw = register(new SpellStoneballThrow(), LibSpellNames.STONEBALL_THROW);
        small_fireball_throw = register(new SpellSmallFireballThrow(), LibSpellNames.SMALL_FIREBALL_THROW);
        waterwalking = register(new SpellWaterwalking(), LibSpellNames.WATERWALKING);
    }

    public static Spell register(Spell spell, String name) {
        //PsiAPI.registerSpellPieceAndTexture(name, clazz);
        //PsiAPI.addPieceToGroup(clazz, group, main);
        return spell;
    }
}