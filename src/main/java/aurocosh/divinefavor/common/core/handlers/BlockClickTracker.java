package aurocosh.divinefavor.common.core.handlers;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.interaction_handler.InteractionData;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class BlockClickTracker {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        World world = event.getWorld();
        if(world.isRemote)
            return;
        EntityPlayer player = event.getEntityPlayer();
        InteractionData interactionData = PlayerData.get(player).getInteractionData();
        interactionData.recordLastClickedPosition(new Vector3i(event.getPos()));
    }

    public static boolean wasBlockLeftClicked(EntityPlayer player, BlockPos pos) {
        InteractionData interactionData = PlayerData.get(player).getInteractionData();
        return interactionData.wasPositionClicked(new Vector3i(pos));
    }
}