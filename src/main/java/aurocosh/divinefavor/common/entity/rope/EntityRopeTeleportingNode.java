package aurocosh.divinefavor.common.entity.rope;

import aurocosh.divinefavor.common.entity.rope.base.EntityRopeNodeBase;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.util.SlotData;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilEntity;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityRopeTeleportingNode extends EntityRopeNodeBase {
    public EntityRopeTeleportingNode(World world) {
        super(world);
    }

    @Override
    protected void registerPickUp(EntityPlayer player) {
        UtilPlayer.addStackToInventoryOrDrop(player, new ItemStack(ModItems.INSTANCE.getRope_teleporting(), 1));
    }

    @Override
    protected boolean canDropNewNode(EntityPlayer player) {
        SlotData slotData = UtilPlayer.findStackInInventory(player, element -> !element.isEmpty() && element.getItem() == ModItems.INSTANCE.getRope_teleporting());
        if (slotData.getSlotIndex() == -1)
            return false;
        slotData.getStack().shrink(1);
        player.inventory.setInventorySlotContents(slotData.getSlotIndex(), slotData.getStack().getCount() > 0 ? slotData.getStack() : ItemStack.EMPTY);
        return true;
    }

    @Override
    protected EntityRopeNodeBase makeNewNode(World world) {
        return new EntityRopeTeleportingNode(world);
    }

    @Override
    protected Class<? extends EntityRopeNodeBase> getEntityClass() {
        return EntityRopeTeleportingNode.class;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    @Override
    public void applyEntityCollision(Entity entityIn) {
        if (!(entityIn instanceof EntityLivingBase))
            return;
        EntityLivingBase livingBase = (EntityLivingBase) entityIn;
        EntityRopeNodeBase lastConnectedNode = getLastConnectedNode(this);
        if (lastConnectedNode == this)
            return;
        BlockPos placeToStand = UtilCoordinates.INSTANCE.findPlaceToStand(lastConnectedNode.getPosition(), world, 8);
        UtilEntity.INSTANCE.teleport(livingBase, placeToStand);
    }
}