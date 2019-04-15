package aurocosh.divinefavor.client.core;

import aurocosh.divinefavor.common.entity.rope.IClimbable;
import aurocosh.divinefavor.common.util.UtilEntity;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class RopeInteractionHandler {
    private static final int RADIUS = 3;
    private static final int RADIUS_SQ = RADIUS * RADIUS;
    private static final float CLIMBING_SPEED = 0.25f;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END)
            return;
        if (event.side == Side.SERVER)
            return;
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (!player.isSneaking())
            return;

        List<Entity> entities = player.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(player.getPosition()).grow(RADIUS));
        Entity climbable = UtilList.findFirst(entities, node -> node instanceof IClimbable && node.getDistanceSq(player) <= RADIUS_SQ);
        if (climbable == null)
            return;

        Vec3d direction = player.getLookVec().scale(player.movementInput.moveForward);
        UtilEntity.setVelocity(player, direction, CLIMBING_SPEED);
    }
}
