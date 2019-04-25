package aurocosh.divinefavor.common.item.talismans.spell.base;

import aurocosh.divinefavor.common.potions.base.effect.ModEffectToggle;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggle;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionToggleLimited;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.entity.player.EntityPlayer;

public class SpellTalismanModPotionToggle extends ItemSpellTalisman {
    private final ModPotionToggle potion;

    public SpellTalismanModPotionToggle(String name, ModSpirit spirit, int favorCost, ModPotionToggle potion) {
        super(name, spirit, favorCost, SpellOptions.ALL_CAST);
        this.potion = potion;
        if(potion instanceof ModPotionToggleLimited) {
            ModPotionToggleLimited limited = (ModPotionToggleLimited) potion;
            limited.setTalisman(this);
        }
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        EntityPlayer player = context.player;
        if(player.isPotionActive(potion))
            player.removePotionEffect(potion);
        else
            player.addPotionEffect(new ModEffectToggle(potion));
    }

    @Override
    public boolean isConsumeCharge(TalismanContext context) {
        return !context.player.isPotionActive(potion);
    }
}
