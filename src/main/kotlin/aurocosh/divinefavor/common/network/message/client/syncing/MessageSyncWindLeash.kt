package aurocosh.divinefavor.common.network.message.client.syncing

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.extensions.divineLivingData
import aurocosh.divinefavor.common.network.message.base.DivineClientMessage
import net.minecraft.util.math.Vec3d
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class MessageSyncWindLeash : DivineClientMessage {
    var x: Double = 0.0
    var z: Double = 0.0

    constructor()

    constructor(vec3d: Vec3d) {
        this.x = vec3d.x
        this.z = vec3d.z
    }

    @SideOnly(Side.CLIENT)
    override fun handleSafe() {
        if (!DivineFavor.proxy.hasClientPlayer)
            return
        val player = DivineFavor.proxy.clientPlayer
        val windLeash = player.divineLivingData.windLeashData
        windLeash.vector = Vec3d(x, 0.0, z)
    }
}
