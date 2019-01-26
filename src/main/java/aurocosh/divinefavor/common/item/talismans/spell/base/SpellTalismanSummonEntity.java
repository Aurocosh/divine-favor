package aurocosh.divinefavor.common.item.talismans.spell.base;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SpellTalismanSummonEntity<T extends EntityLiving> extends ItemSpellTalisman {
    private final Class<? extends T> clazz;

    public SpellTalismanSummonEntity(String name, int uses, boolean castOnUse, boolean castOnRightClick, Class<? extends T> clazz) {
        super(name, uses, castOnUse, castOnRightClick);
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

    protected void postProcessEntity(T entityLiving, TalismanContext context) {
    }
}
