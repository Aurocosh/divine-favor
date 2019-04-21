package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.lib.distributed_random.DistributedRandomList;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public class SpellTalismanNetherSurge extends ItemSpellTalisman {
    private static final int CYCLE_LIMIT = 5000;

    private final DistributedRandomList<Block> possibleBlocks = new DistributedRandomList<>();
    private final DistributedRandomList<Class<? extends EntityLiving>> possibleEnemies = new DistributedRandomList<>();

    public SpellTalismanNetherSurge(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options) {
        super(name, spirit, favorCost, options);

        for (Map.Entry<String, Double> entry : ConfigSpells.netherSurge.possibleBlocks.entrySet()) {
            Block block = Block.getBlockFromName(entry.getKey());
            if (block == null) {
                //TODO warn
            }
            else
                possibleBlocks.add(block, entry.getValue());
        }

        possibleEnemies.add(EntityPigZombie.class, 0.65d);
        possibleEnemies.add(EntityBlaze.class, 0.15d);
        possibleEnemies.add(EntityWitherSkeleton.class, 0.1d);
        possibleEnemies.add(EntityGhast.class, 0.1d);
        possibleEnemies.add(EntityWither.class, 0.0001d);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        int blocksSelect = UtilRandom.nextInt(ConfigSpells.netherSurge.minBlocksToReplace, ConfigSpells.netherSurge.maxBlocksToReplace);
        List<BlockPos> blocksToReplace = UtilCoordinates.getRandomNeighbours(context.pos, blocksSelect, ConfigSpells.netherSurge.minNeighboursToAdd, ConfigSpells.netherSurge.maxNeighboursToAdd, CYCLE_LIMIT, pos -> true);

        for (BlockPos pos : blocksToReplace)
            UtilBlock.replaceBlock(context.player, context.world, pos, possibleBlocks.getRandom());

        int mobsToSpawn = UtilRandom.nextInt(ConfigSpells.netherSurge.minEnemiesToSpawn, ConfigSpells.netherSurge.maxEnemiesToSpawn);
        for (int i = 0; i < mobsToSpawn; i++)
            spawnNetherMob(context);
    }

    private void spawnNetherMob(TalismanContext context) {
        int spawnRadius = ConfigSpells.netherSurge.spawnRadius;
        BlockPos spawnPos = UtilCoordinates.getRandomNeighbour(context.pos, spawnRadius, 0, spawnRadius);
        spawnPos = UtilCoordinates.findPlaceToStand(spawnPos, context.world, spawnRadius);
        if (spawnPos == null)
            return;

        try {
            Class<? extends EntityLiving> clazz = possibleEnemies.getRandom();
            Constructor<? extends EntityLiving> constructor = clazz.getConstructor(World.class);
            EntityLiving entityLiving = constructor.newInstance(context.world);
            entityLiving.setLocationAndAngles(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 0, 0.0F);
            context.world.spawnEntity(entityLiving);
        }
        catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
