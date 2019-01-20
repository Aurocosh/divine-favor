package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.item.talismans.base.spell.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.base.spell.TalismanContext;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class SpellTalismanMinersFocus extends ItemSpellTalisman {
    private static final int HASTE_DURATION = (int) (60 * 20 * 0.1);
    private static final int HASTE_LEVEL = 4;
    private static final int USES = 10;

    public SpellTalismanMinersFocus() {
        super("miners_focus", USES, true, true);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        context.player.addPotionEffect(new ModEffect(ModPotions.miners_focus,HASTE_DURATION));
        context.player.addPotionEffect(new PotionEffect(MobEffects.HASTE,HASTE_DURATION,HASTE_LEVEL));
    }
}
