package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.item.talismans.base.arrow.ArrowType;
import aurocosh.divinefavor.common.item.talismans.base.arrow.ItemArrowTalisman;
import aurocosh.divinefavor.common.util.InventoryIndexes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class ArrowTalismanHandSwap extends ItemArrowTalisman {
    public ArrowTalismanHandSwap() {
        super("hand_swap", 15, Color.orange.getRGB(), 0, false, true, ArrowType.SPELL_ARROW);
    }

    @Override
    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
        if (target instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) target;
            ItemStack stackMain = player.getHeldItemMainhand();
            ItemStack stackOff = player.getHeldItemOffhand();
            player.inventory.setInventorySlotContents(player.inventory.currentItem, stackOff);
            player.inventory.setInventorySlotContents(InventoryIndexes.Offhand.getValue(), stackMain);
        }
    }
}