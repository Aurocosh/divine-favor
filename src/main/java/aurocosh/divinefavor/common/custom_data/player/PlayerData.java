package aurocosh.divinefavor.common.custom_data.player;

import aurocosh.divinefavor.common.custom_data.player.capability.IPlayerDataHandler;
import net.minecraft.entity.player.EntityPlayer;

import static aurocosh.divinefavor.common.custom_data.player.capability.PlayerDataDataHandler.CAPABILITY_PLAYER_DATA;

// The default implementation of the capability. Holds all the logic.
public class PlayerData {
    public static IPlayerDataHandler get(EntityPlayer player){
        return player.getCapability(CAPABILITY_PLAYER_DATA, null);
    }
}
