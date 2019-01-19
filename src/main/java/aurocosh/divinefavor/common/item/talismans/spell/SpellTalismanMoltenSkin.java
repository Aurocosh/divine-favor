package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.item.talismans.base.spell.ItemSpellTalisman;
import aurocosh.divinefavor.common.network.message.client.MessageSyncFireImmunity;
import aurocosh.divinefavor.common.potions.base.effect.ModEffectToggle;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.item.talismans.base.spell.TalismanContext;
import net.minecraft.entity.Entity;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class SpellTalismanMoltenSkin extends ItemSpellTalisman {
    private static final int USES = 10;

    public SpellTalismanMoltenSkin() {
        super("molten_skin", USES, true, true);
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
