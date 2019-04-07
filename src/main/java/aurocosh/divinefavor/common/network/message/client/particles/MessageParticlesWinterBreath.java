package aurocosh.divinefavor.common.network.message.client.particles;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageParticlesWinterBreath extends NetworkWrappedClientMessage {
    public Vec3d position;
    public Vec3d direction;

    public MessageParticlesWinterBreath() {
    }

    public MessageParticlesWinterBreath(Vec3d position, Vec3d direction) {
        this.position = position;
        this.direction = direction;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();

        double spread = 1;
        for (int i = 0; i < ConfigSpells.winterBreath.particleCount; i++) {
            double xSpeed = direction.x * 2 + UtilRandom.nextDouble(-spread, spread);
            double ySpeed = direction.y * 2 + UtilRandom.nextDouble(-spread, spread);
            double zSpeed = direction.z * 2 + UtilRandom.nextDouble(-spread, spread);

            player.world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, position.x, position.y, position.z, xSpeed, ySpeed, zSpeed);
        }
    }
}
