package aurocosh.divinefavor.common.network.message.client.syncing;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageSyncFireImmunity extends NetworkWrappedClientMessage {
    public boolean immuneToFire;

    public MessageSyncFireImmunity() {
    }

    public MessageSyncFireImmunity(boolean immuneToFire) {
        this.immuneToFire = immuneToFire;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        ReflectionHelper.setPrivateValue(Entity.class,player,immuneToFire,54);
    }
}