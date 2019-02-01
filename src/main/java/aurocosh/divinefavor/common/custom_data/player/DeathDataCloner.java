package aurocosh.divinefavor.common.custom_data.player;

import aurocosh.divinefavor.common.custom_data.player.capability.IPlayerDataHandler;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class DeathDataCloner {
    @SubscribeEvent
    public void clonePlayer(PlayerEvent.Clone event) {
        IPlayerDataHandler originalData = PlayerData.get(event.getOriginal());
        IPlayerDataHandler cloneData = PlayerData.get(event.getEntityPlayer());

        cloneData.getContractsData().getStackHandler().deserializeNBT(originalData.getContractsData().getStackHandler().serializeNBT());
        cloneData.getFavorData().setFavorValues(originalData.getFavorData().getFavorValues());
        cloneData.getGrudgeData().setMobTypeId(originalData.getGrudgeData().getMobTypeId());
        cloneData.getInteractionData().setLastClickedPositions(originalData.getInteractionData().getLastClickedPositions());
        cloneData.getPearlCrumbsData().setAllPositions(originalData.getPearlCrumbsData().getAllPositions());
    }
}