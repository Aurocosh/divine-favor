package aurocosh.divinefavor.common.item.wishing_stones;

import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.network.common.NetworkHandler;
import aurocosh.divinefavor.common.network.message.MessageSyncFavor;
import aurocosh.divinefavor.common.player_data.favor.IFavorHandler;
import aurocosh.divinefavor.common.registry.interfaces.IVariant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.registries.IForgeRegistryEntry;

import static aurocosh.divinefavor.common.player_data.favor.FavorDataHandler.CAPABILITY_FAVOR;

public class WishingStone extends IForgeRegistryEntry.Impl<WishingStone> implements IVariant {
    public final String name;
    private final ModFavor favor;
    private final int favorCount;

    public WishingStone(String name, ModFavor favor, int favorCount) {
        this.name = name;
        this.favor = favor;
        this.favorCount = favorCount;
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean use(EntityPlayer playerIn) {
        IFavorHandler favorHandler = playerIn.getCapability(CAPABILITY_FAVOR, null);
        assert favorHandler != null;

        int favorValue = favorHandler.addFavor(favor.id, favorCount);
        if(playerIn instanceof EntityPlayerMP) {
            MessageSyncFavor message = new MessageSyncFavor(favor.id,favorValue);
            NetworkHandler.INSTANCE.sendTo(message, (EntityPlayerMP) playerIn);
        }
        return true;
    }
}