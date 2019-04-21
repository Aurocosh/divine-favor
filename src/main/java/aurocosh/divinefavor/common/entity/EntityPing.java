package aurocosh.divinefavor.common.entity;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.lib.SimpleCounter;
import aurocosh.divinefavor.common.particles.ModParticles;
import aurocosh.divinefavor.common.particles.particles.MobileParticle;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.vecmath.Color3f;

public class EntityPing extends Entity {
    private static final int RENDER_DISTANCE_SQ = ConfigSpells.ping.renderDistance * ConfigSpells.ping.renderDistance;
    private static final Color3f COLOR_3_F = new Color3f(0.7f, 0.7f, 0);

    private SimpleCounter despawnTimer = new SimpleCounter(ConfigSpells.ping.duration);

    public EntityPing(World world) {
        super(world);
        setSize(0.1f, 0.1f);
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public void onEntityUpdate() {
        if (world.isRemote)
            spawnParticles();
        else
            processDespawn();
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticles() {
        for (int i = 0; i < ConfigSpells.ping.particleIntensity; ++i) {
            double distance = UtilRandom.nextFloat(1, 5);
            Vec3d direction = UtilRandom.nextDirection();
            Vec3d pointOnSphere = direction.scale(distance);
            Vec3d pointInWorld = pointOnSphere.add(getPositionVector());
            Vec3d speed = direction.scale(-0.15f);
            ModParticles.normal.createParticle(pointInWorld, () -> new MobileParticle(world, pointInWorld, speed, COLOR_3_F));
        }
    }

    private void processDespawn() {
        if (world.isRemote)
            return;
        if (despawnTimer.count())
            setDead();
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        if (world.isRemote)
            return true;
        setDead();
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        return distance < RENDER_DISTANCE_SQ;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
    }

    @Override
    public boolean isGlowing() {
        return true;
    }
}