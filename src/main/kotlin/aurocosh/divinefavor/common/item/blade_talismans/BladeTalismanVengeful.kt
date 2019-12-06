package aurocosh.divinefavor.common.item.blade_talismans

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.blade_talismans.base.ItemBladeTalisman
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.DamageSource
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class BladeTalismanVengeful(name: String, spirit: ModSpirit, favorCost: Int) : ItemBladeTalisman(name, spirit, favorCost) {
    override fun performActionServer(context: CastContext) {
        val damage = context.player.divinePlayerData.vengefulBladeData.lastDamage
        context.target?.attackEntityFrom(DamageSource.causePlayerDamage(context.player), damage)
    }

    companion object {
        @SubscribeEvent
        fun onEntityDamaged(event: LivingDamageEvent) {
            val player = event.entity as? EntityPlayer ?: return
            player.divinePlayerData.vengefulBladeData.lastDamage = event.amount
        }
    }
}
