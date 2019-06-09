package aurocosh.divinefavor.common.item.talismans.blade

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.talismans.blade.base.ItemBladeTalisman
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.DamageSource
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
class BladeTalismanVengeful(name: String, spirit: ModSpirit, favorCost: Int) : ItemBladeTalisman(name, spirit, favorCost) {
    override fun performActionServer(context: ItemStack, player: EntityPlayer, target: EntityLivingBase) {
        val damage = player.divinePlayerData.vengefulBladeData.lastDamage
        target.attackEntityFrom(DamageSource.causePlayerDamage(player), damage)
    }

    companion object {
        @SubscribeEvent
        fun onEntityDamaged(event: LivingDamageEvent) {
            val player = event.entity as? EntityPlayer ?: return
            player.divinePlayerData.vengefulBladeData.lastDamage = event.amount
        }
    }
}
