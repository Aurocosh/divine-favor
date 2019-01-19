package aurocosh.divinefavor.common.item.talismans.base.spell;

import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;

public class SpellTalismanModPotion extends ItemSpellTalisman {
    private final int duration;
    private final int amplifier;
    private final ModPotion potion;

    public SpellTalismanModPotion(String name, int startingSpellUses, ModPotion potion, int duration) {
        super(name, startingSpellUses, true, true);
        this.duration = duration;
        this.amplifier = 0;
        this.potion = potion;
    }

    public SpellTalismanModPotion(String name, int startingSpellUses, ModPotion potion, int duration, int amplifier) {
        super(name, startingSpellUses, true, true);
        this.duration = duration;
        this.amplifier = amplifier;
        this.potion = potion;
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        context.player.addPotionEffect(new ModEffect(potion, duration, amplifier));
    }
}
