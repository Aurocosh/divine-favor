package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.network.message.client.MessageSyncFireImmunity;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.effect.ModEffectToggle;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class SpellMoltenSkin extends ModSpell {
    public SpellMoltenSkin() {
        super("molten_skin");
    }

    @Override
    protected void performActionServer(SpellContext context) {
        boolean newImmunityState = !context.player.isPotionActive(ModPotions.molten_skin);
        ReflectionHelper.setPrivateValue(Entity.class,context.player,newImmunityState,54);
        context.player.extinguish();
        PotionEffect potioneffect = new ModEffectToggle(ModPotions.molten_skin);
        context.player.addPotionEffect(potioneffect);
        new MessageSyncFireImmunity(newImmunityState).sendTo(context.player);
    }
}
