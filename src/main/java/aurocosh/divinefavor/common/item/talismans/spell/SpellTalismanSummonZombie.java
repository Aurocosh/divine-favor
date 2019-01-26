package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.entity.minions.MinionZombie;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellTalismanSummonMinion;

public class SpellTalismanSummonZombie extends SpellTalismanSummonMinion<MinionZombie> {
    private static final int USES = 10;

    public SpellTalismanSummonZombie() {
        super("summon_zombie", USES, true, false, MinionZombie.class);
    }
}
