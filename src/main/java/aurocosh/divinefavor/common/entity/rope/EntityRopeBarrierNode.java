package aurocosh.divinefavor.common.entity.rope;

import aurocosh.divinefavor.common.config.common.ConfigRope;
import aurocosh.divinefavor.common.entity.rope.base.EntityRopeNodeBase;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.util.SlotData;
import aurocosh.divinefavor.common.util.UtilEntity;
import aurocosh.divinefavor.common.util.UtilList;
import aurocosh.divinefavor.common.util.UtilPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class EntityRopeBarrierNode extends EntityRopeNodeBase {
    private static final int RADIUS_SQ = ConfigRope.barrierRope.repulsionRadius * ConfigRope.barrierRope.repulsionRadius;
    private int durability;

    public EntityRopeBarrierNode(World world) {
        super(world);
        durability = ConfigRope.barrierRope.durability;
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();

        if (world.isRemote)
            return;

        List<EntityLivingBase> livingBases = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(getPosition()).grow(ConfigRope.barrierRope.repulsionRadius));
        List<EntityLivingBase> affectedMobs = UtilList.select(livingBases, element -> !(element instanceof EntityPlayer) && element.getDistanceSq(this) <= RADIUS_SQ);

        for (EntityLivingBase affectedMob : affectedMobs) {
            Vec3d direction = affectedMob.getPositionVector().subtract(this.getPositionVector());
            UtilEntity.addVelocity(affectedMob, direction, ConfigRope.barrierRope.repulsionForce);
            durability--;
        }
        if (durability <= 0)
            setDead();
    }

    @Override
    protected void registerPickUp(EntityPlayer player) {
        UtilPlayer.addStackToInventoryOrDrop(player, new ItemStack(ModItems.rope_barrier, 1));
    }

    @Override
    protected boolean canDropNewNode(EntityPlayer player) {
        SlotData slotData = UtilPlayer.findStackInInventory(player, element -> !element.isEmpty() && element.getItem() == ModItems.rope_barrier);
        if (slotData.slotIndex == -1)
            return false;
        slotData.stack.shrink(1);
        player.inventory.setInventorySlotContents(slotData.slotIndex, slotData.stack.getCount() > 0 ? slotData.stack : ItemStack.EMPTY);
        return true;
    }

    @Override
    protected EntityRopeNodeBase makeNewNode(World world) {
        return new EntityRopeBarrierNode(world);
    }

    @Override
    protected Class<? extends EntityRopeNodeBase> getEntityClass() {
        return EntityRopeBarrierNode.class;
    }
}