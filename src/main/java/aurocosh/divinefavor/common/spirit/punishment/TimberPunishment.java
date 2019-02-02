package aurocosh.divinefavor.common.spirit.punishment;

import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TimberPunishment extends SpiritPunishment {
    public static int ROOTS_DURATION = UtilTick.minutesToTicks(5);
    public static int BLINDNESS_DURATION = UtilTick.secondsToTicks(30);

    @Override
    public void execute(EntityPlayer player, World world, BlockPos pos, IBlockState state, MultiBlockInstance instance) {
        player.addPotionEffect(new ModEffect(ModCurses.roots, ROOTS_DURATION).setIsCurse());
        player.addPotionEffect(new ModEffect(MobEffects.BLINDNESS, BLINDNESS_DURATION).setIsCurse());
    }
}
