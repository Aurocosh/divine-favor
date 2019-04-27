package aurocosh.divinefavor.common.spirit.punishment;

import aurocosh.divinefavor.common.config.common.ConfigPunishments;
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment;
import aurocosh.divinefavor.common.util.UtilAlgoritm;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlizrabiPunishment extends SpiritPunishment {
    @Override
    public void execute(EntityPlayer player, World world, BlockPos pos, IBlockState state, MultiBlockInstance instance) {
        player.addPotionEffect(new ModEffect(MobEffects.SLOWNESS, ConfigPunishments.blizrabi.slownessDuration, ConfigPunishments.blizrabi.slownessAmplifier).setIsCurse());
        player.addPotionEffect(new ModEffect(MobEffects.WEAKNESS, ConfigPunishments.blizrabi.weaknessDuration, ConfigPunishments.blizrabi.weaknessAmplifier).setIsCurse());

        int wolfsToSpawn = ConfigPunishments.blizrabi.wolfsToSpawn.random();
        int spawnAttempts = wolfsToSpawn * 10;
        BlockPos playerPosition = player.getPosition();
        UtilAlgoritm.repeatUntilSuccessful(() -> spawnWolf(player, world, playerPosition), wolfsToSpawn, spawnAttempts);
    }

    private boolean spawnWolf(EntityPlayer player, World world, BlockPos pos) {
        int spawnRadius = ConfigPunishments.blizrabi.spawnRadius;
        BlockPos spawnPos = UtilCoordinates.INSTANCE.getRandomNeighbour(pos, spawnRadius, 0, spawnRadius);
        spawnPos = UtilCoordinates.INSTANCE.findPlaceToStand(spawnPos, world, spawnRadius);
        if (spawnPos == null)
            return false;

        EntityWolf entityWolf = new EntityWolf(world);
        entityWolf.setLocationAndAngles(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 0, 0.0F);

        entityWolf.setAttackTarget(player);
        entityWolf.setAngry(true);
        world.spawnEntity(entityWolf);
        return true;
    }
}
