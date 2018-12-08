package aurocosh.divinefavor.common.requirements.cost.costs;

import aurocosh.divinefavor.common.favors.ModFavor;
import aurocosh.divinefavor.common.network.base.NetworkHandler;
import aurocosh.divinefavor.common.network.message.MessageSyncFavor;
import aurocosh.divinefavor.common.player_data.favor.IFavorHandler;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import net.minecraft.entity.player.EntityPlayerMP;

import static aurocosh.divinefavor.common.player_data.favor.FavorDataHandler.CAPABILITY_FAVOR;

public class CostFavor extends Cost {
    private int favorCount;
    private ModFavor favor;

    public CostFavor(ModFavor favor, int favorCount) {
        this.favor = favor;
        this.favorCount = favorCount;
    }

    @Override
    public boolean canClaim(SpellContext context) {
        IFavorHandler favorHandler = context.player.getCapability(CAPABILITY_FAVOR, null);
        if (favorHandler == null)
            return false;
        return favorHandler.getFavor(favor.id) >= favorCount;
    }

    @Override
    public boolean claim(SpellContext context) {
        IFavorHandler favorHandler = context.player.getCapability(CAPABILITY_FAVOR, null);
        if (favorHandler == null)
            return false;
        boolean consumed = favorHandler.consumeFavor(favor.id,favorCount);
        if(!consumed)
            return false;

        int favorValue = favorHandler.getFavor(favor.id);
        if(context.player instanceof EntityPlayerMP) {
            MessageSyncFavor message = new MessageSyncFavor(favor.id,favorValue);
            NetworkHandler.INSTANCE.sendTo(message, (EntityPlayerMP) context.player);
        }
        return true;
    }

    @Override
    public String getUsageInfo(SpellContext context) {
        int usesLeft = getUseCount(context);
        if (favorCount == -1)
            return "Unlimited use";
        return "Uses left: " + usesLeft;
    }

    @Override
    public int getUseCount(SpellContext context) {
        if (favorCount == 0)
            return -1;

        IFavorHandler favorHandler = context.player.getCapability(CAPABILITY_FAVOR, null);
        if (favorHandler == null)
            return 0;
        int favorsLeft = favorHandler.getFavor(favor.id);
        return favorsLeft / favorCount;
    }
}