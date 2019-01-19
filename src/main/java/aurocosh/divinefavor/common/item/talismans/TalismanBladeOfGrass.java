package aurocosh.divinefavor.common.item.talismans;

import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.item.talismans.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class TalismanBladeOfGrass extends ItemTalisman {
    private final double RADIUS = 10;
    private final int SLOWNESS_TIME = UtilTick.secondsToTicks(15);
    private final float DAMAGE = 2;
    private static final int USES = 2;

    public TalismanBladeOfGrass() {
        super("blade_of_grass", USES, true, true);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        EntityPlayer player = context.player;
        Vector3i origin = Vector3i.convert(player.getPosition());
        AxisAlignedBB axis = new AxisAlignedBB(origin.x - RADIUS, origin.y - RADIUS, origin.z - RADIUS, origin.x + RADIUS, origin.y + RADIUS, origin.z + RADIUS);
        List<Entity> list = context.world.getEntitiesWithinAABB(Entity.class, axis, (Entity e) -> isValid(e, player, origin));

        for (Entity entity : list) {
            List<BlockPos> positions = new ArrayList<>();
            BlockPos pos = entity.getPosition().down();
            positions.add(pos);
            positions.add(pos.east());
            positions.add(pos.west());
            positions.add(pos.north());
            positions.add(pos.south());

            if (consumeGrass(positions, context.player, context.world)) {
                EntityLivingBase base = (EntityLivingBase) entity;
                base.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, SLOWNESS_TIME));
                base.attackEntityFrom(DamageSource.causePlayerDamage(player), DAMAGE);
            }
        }
    }

    private boolean isValid(Entity e, EntityPlayer player, Vector3i origin) {
        if (!(e instanceof EntityLivingBase))
            return false;
        if (e == player)
            return false;
        if (!isInRadius(origin, e))
            return false;
        return !((EntityLivingBase) e).isPotionActive(MobEffects.SLOWNESS);
    }

    private boolean isInRadius(Vector3i origin, Entity entity) {
        Vector3i entityVec = Vector3i.convert(entity.getPosition());
        return origin.isDistanceLessThen(entityVec, RADIUS);
    }

    private boolean consumeGrass(List<BlockPos> positions, EntityPlayer player, World world){
        for (BlockPos pos : positions) {
            IBlockState state = world.getBlockState(pos);
            if(state.getMaterial() != Material.GRASS)
                return false;
            if(!UtilBlock.canBreakBlock(player,world,pos,false))
                return false;
        }
        for (BlockPos pos : positions)
            world.setBlockState(pos, Blocks.DIRT.getDefaultState());
        return true;
    }
}
