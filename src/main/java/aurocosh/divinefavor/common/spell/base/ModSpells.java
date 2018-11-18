package aurocosh.divinefavor.common.spell.base;

import aurocosh.divinefavor.api.spell.Spell;
import aurocosh.divinefavor.common.lib.LibSpellNames;
import aurocosh.divinefavor.common.spell.SpellArrowThrow;
import aurocosh.divinefavor.common.spell.SpellBonemeal;
import aurocosh.divinefavor.common.spell.SpellIgnition;
import aurocosh.divinefavor.common.spell.SpellSnowballThrow;

public final class ModSpells {
    public static Spell arrow_throw;
    public static Spell bonemeal;
    public static Spell ignition;
    public static Spell snowball_throw;

    public static void init() {

        arrow_throw = register(new SpellArrowThrow(), LibSpellNames.ARROW_THROW);
        bonemeal = register(new SpellBonemeal(), LibSpellNames.BONEMEAL);
        ignition = register(new SpellIgnition(), LibSpellNames.IGNITION);
        snowball_throw = register(new SpellSnowballThrow(), LibSpellNames.SNOWBALL_THROW);
    }

    public static Spell register(Spell spell, String name) {
        //PsiAPI.registerSpellPieceAndTexture(name, clazz);
        //PsiAPI.addPieceToGroup(clazz, group, main);
        return spell;
    }
}