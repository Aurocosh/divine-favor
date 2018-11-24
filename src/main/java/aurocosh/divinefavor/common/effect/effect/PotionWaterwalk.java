package aurocosh.divinefavor.common.effect.effect;

import aurocosh.divinefavor.common.lib.LibEffectNames;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class PotionWaterwalk extends PotionBase {

  public PotionWaterwalk() {
      super(LibEffectNames.WATERWALK, true, 0x7FB8A4);
  }

  @Override
  public void tick(EntityLivingBase entityLiving) {
    tickLiquidWalk(entityLiving, Blocks.WATER);
  }

  private void tickLiquidWalk(EntityLivingBase entityLiving, Block liquid) {
    World world = entityLiving.getEntityWorld();
    if (world.getBlockState(entityLiving.getPosition().down()).getBlock() == liquid && world.isAirBlock(entityLiving.getPosition()) && entityLiving.motionY < 0) {
      if (entityLiving instanceof EntityPlayer) {
        EntityPlayer p = (EntityPlayer) entityLiving;
        if (p.isSneaking())
          return;// let them slip down into it
      }
      entityLiving.motionY = 0;// stop falling
      entityLiving.onGround = true; // act as if on solid ground
      entityLiving.setAIMoveSpeed(0.1F);// walking and not sprinting is this
    }
  }
}
