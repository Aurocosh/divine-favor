package aurocosh.divinefavor.common.effect.effect;

import aurocosh.divinefavor.common.effect.base.ModPotion;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PotionLiquidWalking extends ModPotion {
    private final Block block;

    public PotionLiquidWalking(String name, Block block) {
        super(name, true, 0x7FB8A4);
        this.block = block;
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBase, int amplifier) {
        tickLiquidWalk(entityLivingBase, block);
    }

    private void tickLiquidWalk(EntityLivingBase entityLiving, Block liquid) {
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

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
