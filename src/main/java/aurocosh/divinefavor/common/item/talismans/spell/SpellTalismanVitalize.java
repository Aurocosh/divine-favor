package aurocosh.divinefavor.common.item.talismans.spell;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import net.minecraft.init.MobEffects;

import java.util.EnumSet;

public class SpellTalismanVitalize extends ItemSpellTalisman {
    public SpellTalismanVitalize(String name, ModFavor favor, int favorCost, EnumSet<SpellOptions> options) {
        super(name, favor, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        context.player.removePotionEffect(MobEffects.WITHER);
        context.player.removePotionEffect(MobEffects.POISON);
    }
}
