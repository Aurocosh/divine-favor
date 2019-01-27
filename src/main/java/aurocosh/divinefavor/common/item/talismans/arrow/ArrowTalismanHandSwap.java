package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.util.InventoryIndexes;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;

import java.util.EnumSet;

public class ArrowTalismanHandSwap extends ItemArrowTalisman {
    public ArrowTalismanHandSwap(String name, ModFavor favor, int favorCost, int color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super(name, favor, favorCost, color, arrowDamage, options, arrowType);
    }

    @Override
    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
        if (target instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) target;
            UtilPlayer.swapStacks(player, player.inventory.currentItem, InventoryIndexes.Offhand.getValue());
        }
    }
}
