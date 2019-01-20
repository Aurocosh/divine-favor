package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.util.InventoryIndexes;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;

import java.awt.*;

public class ArrowTalismanHandSwap extends ItemArrowTalisman {
    public ArrowTalismanHandSwap() {
        super("hand_swap", 15, Color.orange.getRGB(), 0, false, true, ArrowType.SPELL_ARROW);
    }

    @Override
    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
        if (target instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) target;
            UtilPlayer.swapStacks(player, player.inventory.currentItem, InventoryIndexes.Offhand.getValue());
        }
    }
}
