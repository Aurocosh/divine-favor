package aurocosh.divinefavor.common.spirit.punishment;

import aurocosh.divinefavor.common.lib.DistributedRandomList;
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance;
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment;
import aurocosh.divinefavor.common.util.*;
import aurocosh.divinefavor.common.util.tasks.base.ServerSideTask;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;
import java.util.Map;

public class LoonPunishment extends SpiritPunishment {
    @Override
    public void execute(EntityPlayer player, World world, BlockPos pos, IBlockState state, MultiBlockInstance instance) {
        LoonPunishmentTask task = new LoonPunishmentTask(player);
        task.start();
    }

    public static class LoonPunishmentTask extends ServerSideTask {
        public static int MOBS_TO_SUMMON = 4;
        public static int SPAWN_ATTEMPTS = MOBS_TO_SUMMON * 10;
        private final int SPAWN_RADIUS = 10;
        private static final Map<String, Double> summonedEnemies = new HashMap<>();
        static {
            summonedEnemies.put("minecraft:skeleton",0.3d);
            summonedEnemies.put("minecraft:zombie",0.5d);
            summonedEnemies.put("minecraft:spider",0.1d);
            summonedEnemies.put("minecraft:creeper",0.1d);
        }

        private static final DistributedRandomList<Class<? extends Entity>> possibleEnemies = new DistributedRandomList<>();

        static {
            for (Map.Entry<String, Double> entry : summonedEnemies.entrySet()) {
                Class<? extends Entity> entityClass = EntityList.getClassFromName(entry.getKey());
                if (entityClass != null)
                    possibleEnemies.add(entityClass, entry.getValue());
            }
        }

        private static final int minEnemiesPerWave = 2;
        private static final int maxEnemiesPerWave = 5;

        private static final int minWaveCount = 2;
        private static final int maxWaveCount = 5;

        private static final int minWaveDelay = UtilTick.secondsToTicks(15);
        private static final int maxWaveDelay = UtilTick.minutesToTicks(2);

        private final EntityPlayer player;

        private int waveCount;
        private int waveDelay;

        public LoonPunishmentTask(EntityPlayer player) {
            super(player.world);

            this.player = player;
            waveCount = UtilRandom.nextInt(minWaveCount, maxWaveCount);
            waveDelay = 0;
        }

        @SubscribeEvent
        public void tickEvent(TickEvent.WorldTickEvent event) {
            if (!isSameDimension(event.world))
                return;

            if (waveDelay-- > 0)
                return;
            waveDelay = UtilRandom.nextInt(minWaveDelay, maxWaveDelay);

            int mobsToSpawn = UtilRandom.nextInt(minEnemiesPerWave, maxEnemiesPerWave);
            BlockPos playerPosition = player.getPosition();
            UtilAlgoritm.repeatUntilSuccessful(() -> spawnMob(world, playerPosition), mobsToSpawn, SPAWN_ATTEMPTS);

            if (waveCount-- <= 0)
                finish();
        }

        @SubscribeEvent
        public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
            if (!(event.player instanceof EntityPlayerMP))
                return;
            if (event.player == player)
                finish();
        }

        private boolean spawnMob(World world, BlockPos pos) {
            BlockPos spawnPos = UtilCoordinates.getRandomNeighbour(pos, SPAWN_RADIUS, 0, SPAWN_RADIUS);
            spawnPos = UtilCoordinates.findPlaceToSpawn(spawnPos, world, SPAWN_RADIUS);
            if (spawnPos != null) {
                Class<? extends Entity> clazz = possibleEnemies.getRandom();
                return UtilEntity.spawnEntity(world, spawnPos, clazz);
            }
            return false;
        }
    }
}
