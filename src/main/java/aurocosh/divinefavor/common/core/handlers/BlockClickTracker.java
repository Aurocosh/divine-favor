package aurocosh.divinefavor.common.core.handlers;

import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.player_data.interaction_handler.IInteractionHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static aurocosh.divinefavor.common.player_data.interaction_handler.InteractionDataHandler.CAPABILITY_INTERACTION;

@Mod.EventBusSubscriber
public class BlockClickTracker {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        World world = event.getWorld();
        if(world.isRemote)
            return;

        EntityPlayer player = event.getEntityPlayer();
        IInteractionHandler interactionHandler = player.getCapability(CAPABILITY_INTERACTION,null);
        if(interactionHandler == null)
            return;

        Vector3i vector = Vector3i.convert(event.getPos());
        interactionHandler.recordLastClickedPosition(vector);
    }

    public static boolean wasBlockLeftClicked(EntityPlayer player, BlockPos pos) {
        IInteractionHandler interactionHandler = player.getCapability(CAPABILITY_INTERACTION,null);
        if(interactionHandler == null)
            return false;
        Vector3i vector = Vector3i.convert(pos);
        return interactionHandler.wasPositionClicked(vector);
    }
}