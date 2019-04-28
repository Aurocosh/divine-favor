package aurocosh.divinefavor.common.entity.rope;

import aurocosh.divinefavor.common.config.common.ConfigRope;
import aurocosh.divinefavor.common.entity.rope.base.EntityRopeNodeBase;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.util.SlotData;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityRopeInertNode extends EntityRopeNodeBase implements IClimbable {
    private static final float climbingDistanceSq = ConfigRope.inertRope.climbingDistance * ConfigRope.inertRope.climbingDistance;

    public EntityRopeInertNode(World world) {
        super(world);
    }

    @Override
    protected void registerPickUp(EntityPlayer player) {
        UtilPlayer.addStackToInventoryOrDrop(player, new ItemStack(ModItems.INSTANCE.getRope_inert(), 1));
    }

    @Override
    protected boolean canDropNewNode(EntityPlayer player) {
        SlotData slotData = UtilPlayer.findStackInInventory(player, element -> !element.isEmpty() && element.getItem() == ModItems.INSTANCE.getRope_inert());
        if (slotData.getSlotIndex() == -1)
            return false;
        slotData.getStack().shrink(1);
        player.inventory.setInventorySlotContents(slotData.getSlotIndex(), slotData.getStack().getCount() > 0 ? slotData.getStack() : ItemStack.EMPTY);
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
        return ConfigRope.inertRope.climbingSpeed;
    }

    @Override
    public boolean canClimb(Entity entityIn) {
        return entityIn.getDistanceSq(this) <= climbingDistanceSq;
    }
}