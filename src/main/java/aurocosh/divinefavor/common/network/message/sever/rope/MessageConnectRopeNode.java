package aurocosh.divinefavor.common.network.message.sever.rope;

import aurocosh.divinefavor.common.entity.rope.EntityExplosiveChargeNode;
import aurocosh.divinefavor.common.entity.rope.EntityRopeNodeBase;
import aurocosh.divinefavor.common.network.base.NetworkWrappedServerMessage;
import aurocosh.divinefavor.common.util.UtilEntity;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.List;

public class MessageConnectRopeNode extends NetworkWrappedServerMessage {
    public MessageConnectRopeNode() {
    }

    @Override
    protected void handleSafe(EntityPlayerMP player) {
        List<EntityExplosiveChargeNode> connectedNodes = UtilEntity.getNearbyEntities(EntityExplosiveChargeNode.class, player, 32, rope -> rope.getNextNodeByUUID() == player);
        if (!connectedNodes.isEmpty()) {
            for (EntityExplosiveChargeNode rope : connectedNodes)
                rope.setNextNode(null);
        }
        else {
            List<EntityExplosiveChargeNode> unconnectedRopeNodes = UtilEntity.getNearbyEntities(EntityExplosiveChargeNode.class, player,
                    EntityRopeNodeBase.ROPE_LENGTH, rope -> rope.getDistanceSq(player) <= EntityRopeNodeBase.ROPE_LENGTH_SQ && rope.getNextNodeByUUID() == null);
            if (!unconnectedRopeNodes.isEmpty()) {
                EntityExplosiveChargeNode closest = UtilList.pickBest(unconnectedRopeNodes, (best, node) -> node.getDistanceSq(player) < best.getDistanceSq(player));
                closest.setNextNode(player);
            }
        }
    }
}