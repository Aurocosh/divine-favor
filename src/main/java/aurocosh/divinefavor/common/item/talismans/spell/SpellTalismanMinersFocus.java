package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
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
    public SpellTalismanMinersFocus(String name, ModFavor favor, int favorCost, EnumSet<SpellOptions> options) {
        super(name, favor, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        context.player.addPotionEffect(new ModEffect(ModPotions.miners_focus, ConfigSpells.minersFocus.hasteDuration));
        context.player.addPotionEffect(new PotionEffect(MobEffects.HASTE, ConfigSpells.minersFocus.hasteDuration, ConfigSpells.minersFocus.hasteLevel));
    }
}
