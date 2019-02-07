package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.spell.pearl_crumbs.PearlCrumbsData;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.spell.base.CastType;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.lib.GlobalBlockPos;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.EnumSet;

public class SpellTalismanPearlCrumbs extends ItemSpellTalisman {
    public SpellTalismanPearlCrumbs(String name, ModFavor favor, int favorCost, EnumSet<SpellOptions> options) {
        super(name, favor, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        EntityPlayer player = context.player;
        PearlCrumbsData crumbsData = PlayerData.get(player).getPearlCrumbsData();
        if (context.castType == CastType.UseCast)
            crumbsData.pushGlobalPosition(new GlobalBlockPos(context.pos, context.player.dimension));
        else if (crumbsData.hasPositions())
            UtilEntity.teleport(context.player, crumbsData.popGlobalPosition());
    }

    @Override
    public boolean isConsumeCharge(TalismanContext context) {
        if(context.castType == CastType.UseCast)
            return false;
        EntityPlayer player = context.player;
        PearlCrumbsData crumbsData = PlayerData.get(player).getPearlCrumbsData();
        return crumbsData.hasPositions();
    }
}
