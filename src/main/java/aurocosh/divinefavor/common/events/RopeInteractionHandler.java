package aurocosh.divinefavor.common.events;

import aurocosh.divinefavor.common.entity.rope.IClimbableNode;
import aurocosh.divinefavor.common.entity.rope.base.EntityRopeNodeBase;
import aurocosh.divinefavor.common.util.UtilEntity;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

@Mod.EventBusSubscriber
public class RopeInteractionHandler {
    private static final int RADIUS = 3;
    private static final int RADIUS_SQ = RADIUS * RADIUS;

    @SubscribeEvent
    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        if (!player.isSneaking())
            return;

        List<EntityRopeNodeBase> livingBases = player.world.getEntitiesWithinAABB(EntityRopeNodeBase.class, new AxisAlignedBB(player.getPosition()).grow(3));
        EntityRopeNodeBase affectedMobs = UtilList.findFirst(livingBases, node -> node instanceof IClimbableNode && node.getDistanceSq(player) <= RADIUS_SQ);

        if (affectedMobs != null)
            UtilEntity.setVelocity(player, player.getLookVec(), 0.3f);
    }
}
