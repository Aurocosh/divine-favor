package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

import java.util.EnumSet;

public class SpellTalismanMinersFocus extends ItemSpellTalisman {
    private static final int HASTE_DURATION = (int) (60 * 20 * 0.1);
    private static final int HASTE_LEVEL = 4;

    public SpellTalismanMinersFocus(String name, ModFavor favor, int favorCost, EnumSet<SpellOptions> options) {
        super(name, favor, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        context.player.addPotionEffect(new ModEffect(ModPotions.miners_focus,HASTE_DURATION));
        context.player.addPotionEffect(new PotionEffect(MobEffects.HASTE,HASTE_DURATION,HASTE_LEVEL));
    }
}
