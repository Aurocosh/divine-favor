package aurocosh.divinefavor.common.network.message.client.syncing

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.network.message.base.DivineClientMessage
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraftforge.fml.relauncher.ReflectionHelper
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class MessageSyncPotionCharge : DivineClientMessage {
    var potionId: Int = 0
    var charges: Int = 0

    constructor()

    constructor(potion: Potion, charges: Int) {
        this.potionId = Potion.getIdFromPotion(potion)
        this.charges = charges
    }

    @SideOnly(Side.CLIENT)
    override fun handleSafe() {
        val potion = Potion.getPotionById(potionId) ?: return
        val player = DivineFavor.proxy.clientPlayer
        val effect = player.getActivePotionEffect(potion) ?: return

        ReflectionHelper.setPrivateValue(PotionEffect::class.java, effect, charges, 3)
    }
}