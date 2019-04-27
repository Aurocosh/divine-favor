package aurocosh.divinefavor.common.network.message.client.particles;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.item.talismans.spell.sense.BlockHighlighter;
import aurocosh.divinefavor.common.item.talismans.spell.sense.SenseEntitiesPredicate;
import aurocosh.divinefavor.common.network.base.WrappedClientMessage;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.vecmath.Color3f;
import java.util.List;
import java.util.function.Predicate;

public class MessageParticlesHighlightEntities extends WrappedClientMessage {
    public int radius;
    public int particles;
    public int dimensionId;
    public BlockPos position;

    public float minShift;
    public float maxShift;
    public Color3f color3f;

    public SenseEntitiesPredicate entitiesPredicate;

    public MessageParticlesHighlightEntities() {
    }

    public MessageParticlesHighlightEntities(int particles, int radius, int dimensionId, BlockPos position, float minShift, float maxShift, Color3f color3f, SenseEntitiesPredicate entitiesPredicate) {
        this.particles = particles;
        this.radius = radius;
        this.dimensionId = dimensionId;
        this.position = position;
        this.minShift = minShift;
        this.maxShift = maxShift;
        this.color3f = color3f;
        this.entitiesPredicate = entitiesPredicate;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        World world = player.world;
        if (dimensionId != world.provider.getDimension())
            return;
        Predicate<EntityLivingBase> predicate = getPredicate(player);
        if (predicate == null)
            return;

        AxisAlignedBB boundingBox = UtilCoordinates.INSTANCE.getBoundingBox(player.getPositionVector(), radius);
        List<EntityLivingBase> livingBaseList = world.getEntitiesWithinAABB(EntityLivingBase.class, boundingBox, predicate::test);
        List<Vec3d> vec3dList = UtilList.process(livingBaseList, livingBase -> livingBase.getPositionEyes(0));

        BlockHighlighter.INSTANCE.spawnParticles(color3f, maxShift, minShift, particles, world, vec3dList);
    }

    @SideOnly(Side.CLIENT)
    private Predicate<EntityLivingBase> getPredicate(EntityPlayer player) {
        switch (entitiesPredicate) {
            case PASSIVE:
                return livingBase -> livingBase instanceof IAnimals && !(livingBase instanceof IMob);
            case HOSTILE:
                return livingBase -> livingBase instanceof IMob;
            case PLAYERS:
                return livingBase -> livingBase != player && livingBase instanceof EntityPlayer;
            case ALL:
                return livingBase -> livingBase != player;
        }
        return null;
    }
}
