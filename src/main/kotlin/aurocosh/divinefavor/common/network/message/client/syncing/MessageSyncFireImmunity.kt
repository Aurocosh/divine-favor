package aurocosh.divinefavor.common.network.message.client.syncing

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.network.base.WrappedClientMessage
import net.minecraft.entity.Entity
import net.minecraftforge.fml.relauncher.ReflectionHelper
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class MessageSyncFireImmunity : WrappedClientMessage {
    var immuneToFire: Boolean = false

    constructor() {}

    constructor(immuneToFire: Boolean) {
        this.immuneToFire = immuneToFire
    }

    @SideOnly(Side.CLIENT)
    override fun handleSafe() {
        val player = DivineFavor.proxy.clientPlayer
        ReflectionHelper.setPrivateValue(Entity::class.java, player, immuneToFire, 54)
    }
}