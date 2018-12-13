package aurocosh.divinefavor.common.network.message;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.favors.ModFavors;
import aurocosh.divinefavor.common.network.base.NetworkAutoMessage;
import aurocosh.divinefavor.common.player_data.favor.IFavorHandler;
import aurocosh.divinefavor.common.registry.ModRegistries;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;

import static aurocosh.divinefavor.common.player_data.favor.FavorDataHandler.CAPABILITY_FAVOR;

public class MessagePartialDataSync extends NetworkAutoMessage {

    public NBTTagCompound cmp;

    public MessagePartialDataSync() {
    }

    public MessagePartialDataSync(EntityPlayer player, Collection<ModFavor> favotsToSync) {
        IFavorHandler favorHandler = player.getCapability(CAPABILITY_FAVOR, null);
        if (favorHandler == null)
            return;
        cmp = new NBTTagCompound();
        for (ModFavor favor : favotsToSync) {
            int value = favorHandler.getFavor(favor.id);
            cmp.setInteger(favor.tag,value);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage handleMessage(MessageContext context) {
        DivineFavor.proxy.addScheduledTaskClient(this::handle);
        return null;
    }

    private void handle() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        IFavorHandler favorHandler = player.getCapability(CAPABILITY_FAVOR, null);
        if (favorHandler == null)
            return;

        for (ModFavor favor : ModRegistries.favors.getValues()) {
            if(!cmp.hasKey(favor.tag))
                continue;
            int value = cmp.getInteger(favor.tag);
            favorHandler.setFavor(favor.id,value);
        }
    }
}