package aurocosh.divinefavor.common.entity.rope;

import aurocosh.divinefavor.common.entity.rope.base.EntityRopeNodeBase;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.util.SlotData;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityRopeGuideNode extends EntityRopeNodeBase implements IClimbable {
    public EntityRopeGuideNode(World world) {
        super(world);
    }

    @Override
    protected void registerPickUp(EntityPlayer player) {
        UtilPlayer.addStackToInventoryOrDrop(player, new ItemStack(ModItems.rope_guide, 1));
    }

    @Override
    protected boolean canDropNewNode(EntityPlayer player) {
        SlotData slotData = UtilPlayer.findStackInInventory(player, element -> !element.isEmpty() && element.getItem() == ModItems.rope_guide);
        if (slotData.slotIndex == -1)
            return false;
        slotData.stack.shrink(1);
        player.inventory.setInventorySlotContents(slotData.slotIndex, slotData.stack.getCount() > 0 ? slotData.stack : ItemStack.EMPTY);
        return true;
    }

    @Override
    protected EntityRopeNodeBase makeNewNode(World world) {
        return new EntityRopeGuideNode(world);
    }

    @Override
    protected Class<? extends EntityRopeNodeBase> getEntityClass() {
        return EntityRopeGuideNode.class;
    }

    @Override
    protected boolean isEmittingLight() {
        return true;
    }

    @Override
    protected boolean isMobile() {
        return true;
    }
}