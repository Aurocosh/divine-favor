package aurocosh.divinefavor.client.core.handler

import aurocosh.divinefavor.common.config.common.ConfigGeneral
import aurocosh.divinefavor.common.constants.ConstMisc
import aurocosh.divinefavor.common.entity.rope.IClimbable
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.client.Minecraft
import net.minecraft.entity.Entity
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = ConstMisc.MOD_ID, value = Side.CLIENT)
object ClimbingHandler {
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    fun onPlayerTickEvent(event: TickEvent.PlayerTickEvent) {
        if (event.phase != TickEvent.Phase.END)
            return
        if (event.side == Side.SERVER)
            return
        val player = Minecraft.getMinecraft().player
        if (!player.isSneaking)
            return

        val boundingBox = UtilCoordinates.getBoundingBox(player.position, ConfigGeneral.maxClimbingRadius.toDouble())
        val entities = player.world.getEntitiesWithinAABB(Entity::class.java,boundingBox)
        val climbable = entities.filterIsInstance<IClimbable>().firstOrNull { it.canClimb(player) } ?: return

        val direction = player.lookVec.scale(player.movementInput.moveForward.toDouble())
        UtilEntity.setVelocity(player, direction, climbable.climbingSpeed)
    }
}
