package aurocosh.divinefavor.common.spirit.punishment;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.common.ConfigPunishments;
import aurocosh.divinefavor.common.lib.DistributedRandomList;
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance;
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment;
import aurocosh.divinefavor.common.util.UtilAlgoritm;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilEntity;
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

import java.util.Map;

public class LoonPunishment extends SpiritPunishment {
    @Override
    public void execute(EntityPlayer player, World world, BlockPos pos, IBlockState state, MultiBlockInstance instance) {
        LoonPunishmentTask task = new LoonPunishmentTask(player);
        task.start();
    }

    public static class LoonPunishmentTask extends ServerSideTask {
        private static final DistributedRandomList<Class<? extends Entity>> possibleEnemies = new DistributedRandomList<>();

        static {
            for (Map.Entry<String, Double> entry : ConfigPunishments.loon.summonedEnemies.entrySet()) {
                String entityName = entry.getKey();
                Class<? extends Entity> entityClass = EntityList.getClassFromName(entityName);
                if (entityClass != null)
                    possibleEnemies.add(entityClass, entry.getValue());
                else
                    DivineFavor.logger.error("Loon punishment config error. Entity type not found: " + entityName);
            }
        }

        private final EntityPlayer player;

        private int waveCount;
        private int waveDelay;

        public LoonPunishmentTask(EntityPlayer player) {
            super(player.world);

            this.player = player;
            waveCount = ConfigPunishments.loon.waveCount.getRandom();
            waveDelay = 0;
        }

        @SubscribeEvent
        public void tickEvent(TickEvent.WorldTickEvent event) {
            if (!isSameDimension(event.world))
                return;

            if (waveDelay-- > 0)
                return;
            waveDelay = ConfigPunishments.loon.waveDelay.getRandom();

            int mobsToSpawn = ConfigPunishments.loon.enemiesPerWave.getRandom();
            int spawnAttempts = mobsToSpawn * 10;
            BlockPos playerPosition = player.getPosition();
            UtilAlgoritm.repeatUntilSuccessful(() -> spawnMob(world, playerPosition), mobsToSpawn, spawnAttempts);

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
            int spawnRadius = ConfigPunishments.loon.spawnRadius;
            BlockPos spawnPos = UtilCoordinates.getRandomNeighbour(pos, spawnRadius, 0, spawnRadius);
            spawnPos = UtilCoordinates.findPlaceToSpawn(spawnPos, world, spawnRadius);
            if (spawnPos != null && possibleEnemies.size() > 0) {
                Class<? extends Entity> clazz = possibleEnemies.getRandom();
                return UtilEntity.spawnEntity(world, spawnPos, clazz);
            }
            return false;
        }
    }
}
