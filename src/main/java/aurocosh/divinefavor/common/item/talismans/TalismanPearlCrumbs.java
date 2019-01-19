package aurocosh.divinefavor.common.item.talismans;

import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.lib.GlobalBlockPos;
import aurocosh.divinefavor.common.player_data.pearl_crumbs.IPearlCrumbsHandler;
import aurocosh.divinefavor.common.item.talismans.base.CastType;
import aurocosh.divinefavor.common.item.talismans.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.entity.player.EntityPlayer;

import static aurocosh.divinefavor.common.player_data.pearl_crumbs.PearlCrumbsDataHandler.CAPABILITY_PEARL_CRUMBS;

public class TalismanPearlCrumbs extends ItemTalisman {
    private static final int USES = 10;

    public TalismanPearlCrumbs() {
        super("pearl_crumbs", USES, true, true);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        EntityPlayer player = context.player;
        IPearlCrumbsHandler crumbsHandler = player.getCapability(CAPABILITY_PEARL_CRUMBS, null);
        assert crumbsHandler != null;

        if (context.castType == CastType.ITEM_USE_CAST)
            crumbsHandler.pushGlobalPosition(new GlobalBlockPos(context.pos, context.player.dimension));
        else if (crumbsHandler.hasPositions())
            UtilEntity.teleport(context.player, crumbsHandler.popGlobalPosition());
    }

    @Override
    public boolean isConsumeCharge(TalismanContext context) {
        if(context.castType == CastType.ITEM_USE_CAST)
            return false;
        EntityPlayer player = context.player;
        IPearlCrumbsHandler crumbsHandler = player.getCapability(CAPABILITY_PEARL_CRUMBS, null);
        assert crumbsHandler != null;
        return crumbsHandler.hasPositions();
    }
}
