package aurocosh.divinefavor.common.item.talismans.base.spell;

import aurocosh.divinefavor.common.potions.base.effect.ModEffectToggle;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggleLimited;

public class SpellTalismanModPotionToggle extends ItemSpellTalisman {
    private final ModPotionToggle potion;

    public SpellTalismanModPotionToggle(String name, int startingSpellUses, ModPotionToggle potion) {
        super(name, startingSpellUses, true, true);
        this.potion = potion;
        if(potion instanceof ModPotionToggleLimited) {
            ModPotionToggleLimited limited = (ModPotionToggleLimited) potion;
            limited.setTalisman(this);
        }
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        context.player.addPotionEffect(new ModEffectToggle(potion));
    }

    @Override
    public boolean isConsumeCharge(TalismanContext context) {
        return !context.player.isPotionActive(potion);
    }
}
