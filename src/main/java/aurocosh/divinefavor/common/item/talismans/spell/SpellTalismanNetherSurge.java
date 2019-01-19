package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.item.talismans.base.spell.ItemSpellTalisman;
import aurocosh.divinefavor.common.lib.DistributedRandomList;
import aurocosh.divinefavor.common.item.talismans.base.spell.CastType;
import aurocosh.divinefavor.common.item.talismans.base.spell.TalismanContext;
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
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class SpellTalismanNetherSurge extends ItemSpellTalisman {
    private final int MIN_NEIGHBOURS_TO_ADD = 1;
    private final int MAX_NEIGHBOURS_TO_ADD = 3;

    private final int MIN_BLOCKS_TO_REPLACE = 150;
    private final int MAX_BLOCKS_TO_REPLACE = 300;

    private final int MIN_ENEMIES_TO_SPAWN = 3;
    private final int MAX_ENEMIES_TO_SPAWN = 10;
    private final int SPAWN_RADIUS = 10;

    private final int CYCLE_LIMIT = 500;

    private static final DistributedRandomList<Block> possibleBlocks = new DistributedRandomList<>();
    private static final DistributedRandomList<Class<? extends EntityLiving>> possibleEnemies = new DistributedRandomList<>();

    static {
        possibleBlocks.add(Blocks.NETHERRACK, 0.625d);
        possibleBlocks.add(Blocks.SOUL_SAND, 0.2d);
        possibleBlocks.add(Blocks.LAVA, 0.07d);
        possibleBlocks.add(Blocks.QUARTZ_BLOCK, 0.05d);
        possibleBlocks.add(Blocks.GLOWSTONE, 0.05d);
        possibleBlocks.add(Blocks.GOLD_BLOCK, 0.01d);
        possibleBlocks.add(Blocks.DIAMOND_BLOCK, 0.005d);

        possibleEnemies.add(EntityPigZombie.class, 0.65d);
        possibleEnemies.add(EntityBlaze.class, 0.15d);
        possibleEnemies.add(EntityWitherSkeleton.class, 0.1d);
        possibleEnemies.add(EntityGhast.class, 0.1d);
        possibleEnemies.add(EntityWither.class, 0.0001d);
    }

    private static final int USES = 10;

    public SpellTalismanNetherSurge() {
        super("nether_surge", USES, true, false);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        assert context.castType == CastType.ITEM_USE_CAST;

        int blocksSelect = UtilRandom.nextInt(MIN_BLOCKS_TO_REPLACE, MAX_BLOCKS_TO_REPLACE);
        List<BlockPos> blocksToReplace = UtilCoordinates.getRandomNeighbours(context.pos, context.world, blocksSelect, MIN_NEIGHBOURS_TO_ADD, MAX_NEIGHBOURS_TO_ADD, CYCLE_LIMIT, (world, pos) -> true);
        for (BlockPos pos : blocksToReplace)
            UtilBlock.replaceBlock(context.player, context.world, pos, possibleBlocks.getRandom());

        int mobsToSpawn = UtilRandom.nextInt(MIN_ENEMIES_TO_SPAWN, MAX_ENEMIES_TO_SPAWN);
        for (int i = 0; i < mobsToSpawn; i++)
            spawnNetherMob(context);
    }

    private void spawnNetherMob(TalismanContext context) {
        BlockPos spawnPos = UtilCoordinates.getRandomNeighbour(context.pos, SPAWN_RADIUS, 0, SPAWN_RADIUS);
        spawnPos = UtilCoordinates.findPlaceToSpawn(spawnPos, context.world, SPAWN_RADIUS);
        if(spawnPos == null)
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
