package aurocosh.divinefavor.common.network.message.client.particles;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.network.base.WrappedClientMessage;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageParticlesWave extends WrappedClientMessage {
    public EnumParticleTypes particleType;
    public Vec3d position;

    public MessageParticlesWave() {
    }

    public MessageParticlesWave(EnumParticleTypes particleType, Vec3d position) {
        this.particleType = particleType;
        this.position = position;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();

        double speedRange = 3;
        for (int i = 0; i < ConfigSpells.heatWave.particleCount; i++) {
            double xSpeed = UtilRandom.INSTANCE.nextDouble(-speedRange, speedRange);
            double ySpeed = UtilRandom.INSTANCE.nextDouble(-speedRange, speedRange);
            double zSpeed = UtilRandom.INSTANCE.nextDouble(-speedRange, speedRange);

            player.world.spawnParticle(particleType, position.x, position.y, position.z, xSpeed, ySpeed, zSpeed);
        }
    }
}
