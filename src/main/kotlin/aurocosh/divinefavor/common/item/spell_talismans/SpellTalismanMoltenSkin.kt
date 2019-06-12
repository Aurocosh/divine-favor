package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.base.TalismanContext
import aurocosh.divinefavor.common.network.message.client.syncing.MessageSyncFireImmunity
import aurocosh.divinefavor.common.potions.base.effect.ModEffectToggle
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.fml.relauncher.ReflectionHelper
import java.util.*

class SpellTalismanMoltenSkin(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: TalismanContext) {
        val player = context.player
        val newImmunityState = !player.isPotionActive(ModPotions.molten_skin)
        ReflectionHelper.setPrivateValue<EntityPlayer, Boolean>(Entity::class.java, player, newImmunityState, 54)
        player.extinguish()
        player.addPotionEffect(ModEffectToggle(ModPotions.molten_skin))
        MessageSyncFireImmunity(newImmunityState).sendTo(player)
    }
}
