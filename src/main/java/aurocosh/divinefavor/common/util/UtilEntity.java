package aurocosh.divinefavor.common.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class UtilEntity {
    public static void addVelocity(Entity entity, Vec3d direction, float velocity){
        Vec3d motion = direction.normalize().scale(velocity);
        entity.motionX += motion.x;
        entity.motionY += motion.y;
        entity.motionZ += motion.z;
    }

    public static void tickLiquidWalk(EntityLivingBase entityLiving, Block liquid) {
        World world = entityLiving.getEntityWorld();
        BlockPos pos = entityLiving.getPosition();

        if (world.getBlockState(pos.down()).getBlock() != liquid)
            return;
        if (!world.isAirBlock(pos))
            return;
        if(entityLiving.motionY >= 0)
            return;

        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLiving;
            if (player.isSneaking())
                return; // let them slip down into it
        }

        entityLiving.motionY = 0; // stop falling
        entityLiving.onGround = true; // act as if on solid ground
        entityLiving.setAIMoveSpeed(0.1F); // walking and not sprinting is this
    }
}
