package aurocosh.divinefavor.common.entity.rope;

import aurocosh.divinefavor.common.entity.rope.base.EntityRopeNodeBase;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.util.SlotData;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityRopeInertNode extends EntityRopeNodeBase implements IClimbable {
    private static final float climbingSpeed = 0.25f;
    private static final float climbingDistance = 2.5f;
    private static final float climbingDistanceSq = climbingDistance * climbingDistance;

    public EntityRopeInertNode(World world) {
        super(world);
    }

    @Override
    protected void registerPickUp(EntityPlayer player) {
        UtilPlayer.addStackToInventoryOrDrop(player, new ItemStack(ModItems.rope_inert, 1));
    }

    @Override
    protected boolean canDropNewNode(EntityPlayer player) {
        SlotData slotData = UtilPlayer.findStackInInventory(player, element -> !element.isEmpty() && element.getItem() == ModItems.rope_inert);
        if (slotData.slotIndex == -1)
            return false;
        slotData.stack.shrink(1);
        player.inventory.setInventorySlotContents(slotData.slotIndex, slotData.stack.getCount() > 0 ? slotData.stack : ItemStack.EMPTY);
        return true;
    }

    @Override
    protected EntityRopeNodeBase makeNewNode(World world) {
        return new EntityRopeInertNode(world);
    }

    @Override
    protected Class<? extends EntityRopeNodeBase> getEntityClass() {
        return EntityRopeInertNode.class;
    }

    @Override
    protected boolean isMobile() {
        return false;
    }

    @Override
    public float getClimbingSpeed() {
        return climbingSpeed;
    }

    @Override
    public boolean canClimb(Entity entityIn) {
        return entityIn.getDistanceSq(this) <= climbingDistanceSq;
    }
}