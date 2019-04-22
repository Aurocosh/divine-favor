package aurocosh.divinefavor.common.network.message.sever.rope;

import aurocosh.divinefavor.common.entity.rope.EntityRopeExplosiveNode;
import aurocosh.divinefavor.common.entity.rope.base.EntityRopeNodeBase;
import aurocosh.divinefavor.common.network.base.WrappedServerMessage;
import aurocosh.divinefavor.common.util.UtilEntity;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.List;

public class MessageConnectRopeNode extends WrappedServerMessage {
    public MessageConnectRopeNode() {
    }

    @Override
    protected void handleSafe(EntityPlayerMP player) {
        List<EntityRopeExplosiveNode> connectedNodes = UtilEntity.getNearbyEntities(EntityRopeExplosiveNode.class, player, 32, rope -> rope.getNextNodeByUUID() == player);
        if (!connectedNodes.isEmpty()) {
            for (EntityRopeExplosiveNode rope : connectedNodes)
                rope.setNextNode(null);
        }
        else {
            List<EntityRopeExplosiveNode> unconnectedRopeNodes = UtilEntity.getNearbyEntities(EntityRopeExplosiveNode.class, player,
                    EntityRopeNodeBase.ROPE_LENGTH, rope -> rope.getDistanceSq(player) <= EntityRopeNodeBase.ROPE_LENGTH_SQ && rope.getNextNodeByUUID() == null);
            if (!unconnectedRopeNodes.isEmpty()) {
                EntityRopeExplosiveNode closest = UtilList.pickBest(unconnectedRopeNodes, (best, node) -> node.getDistanceSq(player) < best.getDistanceSq(player));
                closest.setNextNode(player);
            }
        }
    }
}