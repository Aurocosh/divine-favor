package aurocosh.autonetworklib.network.wrapped;

import net.minecraft.entity.player.EntityPlayer;

public abstract class ConfigSyncClientMessage extends WrappedClientMessage {
    protected abstract void getServerConfigs();

    @Override
    public void sendTo(EntityPlayer player) {
        getServerConfigs();
        super.sendTo(player);
    }
}
