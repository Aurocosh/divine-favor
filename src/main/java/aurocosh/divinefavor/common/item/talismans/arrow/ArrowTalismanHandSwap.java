package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.InventoryIndexes;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.EnumSet;

public class ArrowTalismanHandSwap extends ItemArrowTalisman {
    public ArrowTalismanHandSwap(String name, ModSpirit spirit, int favorCost, Color color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super(name, spirit, favorCost, color, arrowDamage, options, arrowType);
    }

    @Override
    protected boolean performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntitySpellArrow spellArrow, BlockPos blockPos, EnumFacing sideHit) {
        if (target instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) target;
            UtilPlayer.swapStacks(player, player.inventory.currentItem, InventoryIndexes.Offhand.getValue());
        }
        return true;
    }
}
