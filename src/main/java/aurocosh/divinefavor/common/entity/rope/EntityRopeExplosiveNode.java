package aurocosh.divinefavor.common.entity.rope;

import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.util.SlotData;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityRopeExplosiveNode extends EntityRopeNodeBase {
    public EntityRopeExplosiveNode(World world) {
        super(world);
        setSize(0.2f, 0.2f);
    }

    @Override
    protected void registerPickUp(EntityPlayer player) {
        UtilPlayer.addStackToInventoryOrDrop(player, new ItemStack(ModItems.rope_explosive, 1));
    }

    @Override
    protected boolean canDropNewNode(EntityPlayer player) {
        SlotData slotData = UtilPlayer.findStackInInventory(player, element -> !element.isEmpty() && element.getItem() == ModItems.rope_explosive);
        if (slotData.slotIndex == -1)
            return false;
        slotData.stack.shrink(1);
        player.inventory.setInventorySlotContents(slotData.slotIndex, slotData.stack.getCount() > 0 ? slotData.stack : ItemStack.EMPTY);
        return true;
    }

    @Override
    protected EntityRopeNodeBase makeNewNode(World world) {
        return new EntityRopeExplosiveNode(world);
    }

    @Override
    protected Class<? extends EntityRopeNodeBase> getEntityClass() {
        return EntityRopeExplosiveNode.class;
    }
}