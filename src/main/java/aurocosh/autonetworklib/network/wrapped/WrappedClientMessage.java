package aurocosh.autonetworklib.network.wrapped;

import aurocosh.autonetworklib.network.IWrapperProvider;
import aurocosh.autonetworklib.network.message.NetworkClientMessage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public abstract class WrappedClientMessage extends NetworkClientMessage implements IWrapperProvider {
    public void sendTo(EntityPlayer player) {
        if (player instanceof EntityPlayerMP)
            getWrapper().sendTo(this, (EntityPlayerMP) player);
    }

    public void sendTo(EntityLivingBase player) {
        if (player instanceof EntityPlayerMP)
            getWrapper().sendTo(this, (EntityPlayerMP) player);
    }

    public void sendToAllAround(World world, BlockPos pos, int range) {
        getWrapper().sendToAllAround(this, new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), range));
    }

    public void sendToAll() {
        getWrapper().sendToAll(this);
    }
}
