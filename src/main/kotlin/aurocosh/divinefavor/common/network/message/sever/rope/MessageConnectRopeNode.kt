package aurocosh.divinefavor.common.network.message.sever.rope

import aurocosh.divinefavor.common.entity.rope.EntityRopeExplosiveNode
import aurocosh.divinefavor.common.entity.rope.base.EntityRopeNodeBase
import aurocosh.divinefavor.common.network.base.WrappedServerMessage
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.entity.player.EntityPlayerMP

class MessageConnectRopeNode : WrappedServerMessage() {

    override fun handleSafe(player: EntityPlayerMP) {
        val boundingBox = UtilCoordinates.getBoundingBox(player.position, 32.0)
        val connectedNodes = player.world.getEntitiesWithinAABB(EntityRopeExplosiveNode::class.java, boundingBox) { rope -> rope != null && rope.nextNodeByUUID === player }
        if (connectedNodes.isNotEmpty()) {
            for (rope in connectedNodes)
                rope.nextNode = null
        } else {
            val boundingBox2 = UtilCoordinates.getBoundingBox(player.position, EntityRopeNodeBase.ROPE_LENGTH)
            val unconnectedRopeNodes = player.world.getEntitiesWithinAABB(EntityRopeExplosiveNode::class.java, boundingBox2) { rope -> rope != null && rope.getDistanceSq(player) <= EntityRopeNodeBase.ROPE_LENGTH_SQ && rope.nextNodeByUUID == null }
            if (unconnectedRopeNodes.isNotEmpty()) {
                val closest = unconnectedRopeNodes.reduce { best, node -> if (node.getDistanceSq(player) < best.getDistanceSq(player)) node else best }
                closest.nextNode = player
            }
        }
    }
}