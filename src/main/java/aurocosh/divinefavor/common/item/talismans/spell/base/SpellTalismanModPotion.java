package aurocosh.divinefavor.common.item.talismans.spell.base;

import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;

public class SpellTalismanModPotion extends ItemSpellTalisman {
    private final int duration;
    private final int amplifier;
    private final ModPotion potion;

    public SpellTalismanModPotion(String name, ModSpirit spirit, int favorCost, ModPotion potion, int duration) {
        super(name, spirit, favorCost, SpellOptions.ALL_CAST);
        this.duration = duration;
        this.amplifier = 0;
        this.potion = potion;
    }

    public SpellTalismanModPotion(String name, ModSpirit spirit, int favorCost, ModPotion potion, int duration, int amplifier) {
        super(name, spirit, favorCost, SpellOptions.ALL_CAST);
        this.duration = duration;
        this.amplifier = amplifier;
        this.potion = potion;
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        context.player.addPotionEffect(new ModEffect(potion, duration, amplifier));
    }
}
