package aurocosh.divinefavor.common.network.message.client.syncing

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.custom_data.living.LivingData
import aurocosh.divinefavor.common.network.base.WrappedClientMessage
import net.minecraft.util.math.Vec3d
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class MessageSyncWindLeash : WrappedClientMessage {
    var x: Double = 0.0
    var z: Double = 0.0

    constructor() {}

    constructor(vec3d: Vec3d) {
        this.x = vec3d.x
        this.z = vec3d.z
    }

    @SideOnly(Side.CLIENT)
    override fun handleSafe() {
        val player = DivineFavor.proxy.clientPlayer
        val windLeash = LivingData[player].windLeashData
        windLeash.vector = Vec3d(x, 0.0, z)
    }
}
