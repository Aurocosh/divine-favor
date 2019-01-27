package aurocosh.divinefavor.common.item.talismans.spell.base;

import aurocosh.divinefavor.common.favor.ModFavor;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.EnumSet;

public class SpellTalismanSummonEntity<T extends EntityLiving> extends ItemSpellTalisman {
    private final Class<? extends T> clazz;

    public SpellTalismanSummonEntity(String name, ModFavor favor, int favorCost, EnumSet<SpellOptions> options, Class<? extends T> clazz) {
        super(name, favor, favorCost, options);
        this.clazz = clazz;
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        try {
            World world = context.world;
            BlockPos spawnPos = getPosition(context);

            Constructor<? extends T> constructor = clazz.getConstructor(World.class);
            T entityLiving = constructor.newInstance(world);
            entityLiving.setLocationAndAngles(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 0, 0.0F);

            DifficultyInstance difficulty = entityLiving.world.getDifficultyForLocation(entityLiving.getPosition());
            entityLiving.onInitialSpawn(difficulty, null);

            preProcessEntity(entityLiving, context);
            world.spawnEntity(entityLiving);
            postProcessEntity(entityLiving, context);
        }
        catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected BlockPos getPosition(TalismanContext context) {
        return context.pos.offset(context.facing);
    }

    protected void preProcessEntity(T entityLiving, TalismanContext context) {
    }

    protected void postProcessEntity(T entityLiving, TalismanContext context) {
    }
}
