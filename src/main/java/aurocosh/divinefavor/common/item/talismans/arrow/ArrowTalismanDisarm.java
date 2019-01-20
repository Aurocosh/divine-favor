package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.util.InventoryIndexes;
import aurocosh.divinefavor.common.util.SlotData;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class ArrowTalismanDisarm extends ItemArrowTalisman {
    public ArrowTalismanDisarm() {
        super("disarm", 15, Color.orange.getRGB(), 0, false, true, ArrowType.SPELL_ARROW);
    }

    @Override
    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
        if (target instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) target;
            moveStackToMainInventory(player, player.inventory.currentItem);
            moveStackToMainInventory(player, InventoryIndexes.Offhand.getValue());
        }
    }

    private void moveStackToMainInventory(EntityPlayer player, int slot) {
        ItemStack stack = player.inventory.getStackInSlot(slot);
        if (stack.isEmpty())
            return;
        SlotData slotData = UtilPlayer.findStackInMainInventory(player, ItemStack::isEmpty);
        if (slotData.isValid())
            UtilPlayer.swapStacks(player, slot, slotData.slotIndex);
    }
}
