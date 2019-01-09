package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.lib.GlobalBlockPos;
import aurocosh.divinefavor.common.player_data.pearl_crumbs.IPearlCrumbsHandler;
import aurocosh.divinefavor.common.spell.base.CastType;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import static aurocosh.divinefavor.common.player_data.pearl_crumbs.PearlCrumbsDataHandler.CAPABILITY_PEARL_CRUMBS;

public class SpellBlink extends ModSpell {
    public final double BLINK_DISTANCE = 5;

    public SpellBlink() {
        super("blink");
    }

    @Override
    protected void performActionServer(SpellContext context) {
        EntityPlayer player = context.player;
        Vec3d pos = player.getPositionVector();
        Vec3d look = player.getLookVec();
        BlockPos target = new BlockPos(pos.add(look.scale(BLINK_DISTANCE)));
        UtilEntity.teleport(context.player, target.down());
    }

//    @Override
//    public boolean isConsumeCharge(SpellContext context) {
//        if(context.castType == CastType.ITEM_USE_CAST)
//            return false;
//        EntityPlayer player = context.player;
//        IPearlCrumbsHandler crumbsHandler = player.getCapability(CAPABILITY_PEARL_CRUMBS, null);
//        assert crumbsHandler != null;
//        return crumbsHandler.hasPositions();
//    }
}
