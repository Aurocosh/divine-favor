package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.init.MobEffects;

import java.util.EnumSet;

public class SpellTalismanVitalize extends ItemSpellTalisman {
    public SpellTalismanVitalize(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options) {
        super(name, spirit, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        context.player.removePotionEffect(MobEffects.WITHER);
        context.player.removePotionEffect(MobEffects.POISON);
    }
}
