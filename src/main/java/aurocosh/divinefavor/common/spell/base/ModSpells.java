package aurocosh.divinefavor.common.spell.base;

import aurocosh.divinefavor.api.spell.Spell;
import aurocosh.divinefavor.common.lib.LibSpellNames;
import aurocosh.divinefavor.common.spell.SpellIgnition;

public final class ModSpells {
    public static Spell ignition;

    public static void init() {
        ignition = register(new SpellIgnition(), LibSpellNames.IGNITION);
    }

    public static Spell register(Spell spell, String name) {
        //PsiAPI.registerSpellPieceAndTexture(name, clazz);
        //PsiAPI.addPieceToGroup(clazz, group, main);
        return spell;
    }
}