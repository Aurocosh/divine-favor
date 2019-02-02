package aurocosh.divinefavor.common.spirit.punishment;

import aurocosh.divinefavor.common.lib.SuccessCounter;
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlizrabiPunishment extends SpiritPunishment {
    public static int WOLFS_TO_SUMMON = 9;
    public static int SPAWN_ATTEMPTS = WOLFS_TO_SUMMON * 10;
    private final int SPAWN_RADIUS = 10;

    public static int SLOWNESS_DURATION = UtilTick.minutesToTicks(3);
    public static int SLOWNESS_AMPLIFIER = 3;

    public static int WEAKNESS_DURATION = UtilTick.minutesToTicks(3);
    public static int WEAKNESS_AMPLIFIER = 3;

    @Override
    public void execute(EntityPlayer player, World world, BlockPos pos, IBlockState state, MultiBlockInstance instance) {
        player.addPotionEffect(new ModEffect(MobEffects.SLOWNESS, SLOWNESS_DURATION, SLOWNESS_AMPLIFIER).setIsCurse());
        player.addPotionEffect(new ModEffect(MobEffects.WEAKNESS, WEAKNESS_DURATION, WEAKNESS_AMPLIFIER).setIsCurse());

        BlockPos playerPosition = player.getPosition();
        SuccessCounter counter = new SuccessCounter(WOLFS_TO_SUMMON, SPAWN_ATTEMPTS);
        while (counter.attemptOnceMore())
            if (spawnWolf(player, world, playerPosition))
                counter.registerSuccess();
    }

    private boolean spawnWolf(EntityPlayer player, World world, BlockPos pos) {
        BlockPos spawnPos = UtilCoordinates.getRandomNeighbour(pos, SPAWN_RADIUS, 0, SPAWN_RADIUS);
        spawnPos = UtilCoordinates.findPlaceToSpawn(spawnPos, world, SPAWN_RADIUS);
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
