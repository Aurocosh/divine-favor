package aurocosh.divinefavor.common.item.talismans.spell.base;

import aurocosh.divinefavor.common.potions.base.effect.ModEffectCharge;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionCharge;

public class SpellTalismanModPotionCharge extends ItemSpellTalisman {
    private final ModPotionCharge potion;
    private int charges;

    public SpellTalismanModPotionCharge(String name, int startingSpellUses, ModPotionCharge potion, int charges) {
        super(name, startingSpellUses, true, true);
        this.potion = potion;
        this.charges = charges;
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        context.player.addPotionEffect(new ModEffectCharge(potion, charges));
    }

    @Override
    public boolean isConsumeCharge(TalismanContext context) {
        return !context.player.isPotionActive(potion);
    }
}
