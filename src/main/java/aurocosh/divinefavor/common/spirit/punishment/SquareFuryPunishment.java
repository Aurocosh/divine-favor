package aurocosh.divinefavor.common.spirit.punishment;

import aurocosh.divinefavor.common.damage_source.ModDamageSources;
import aurocosh.divinefavor.common.muliblock.MultiBlockInstance;
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SquareFuryPunishment extends SpiritPunishment {
    public static float DAMAGE = 10;

    @Override
    public void execute(EntityPlayer player, World world, BlockPos pos, IBlockState state, MultiBlockInstance instance) {
        player.attackEntityFrom(ModDamageSources.divineDamage, DAMAGE);
    }
}
