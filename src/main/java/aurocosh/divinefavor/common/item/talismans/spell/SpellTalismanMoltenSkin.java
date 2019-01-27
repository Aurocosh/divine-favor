package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.network.message.client.MessageSyncFireImmunity;
import aurocosh.divinefavor.common.potions.base.effect.ModEffectToggle;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import net.minecraft.entity.Entity;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.EnumSet;

public class SpellTalismanMoltenSkin extends ItemSpellTalisman {
    public SpellTalismanMoltenSkin(String name, ModFavor favor, int favorCost, EnumSet<SpellOptions> options) {
        super(name, favor, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        boolean newImmunityState = !context.player.isPotionActive(ModPotions.molten_skin);
        ReflectionHelper.setPrivateValue(Entity.class,context.player,newImmunityState,54);
        context.player.extinguish();
        PotionEffect potioneffect = new ModEffectToggle(ModPotions.molten_skin);
        context.player.addPotionEffect(potioneffect);
        new MessageSyncFireImmunity(newImmunityState).sendTo(context.player);
    }
}
