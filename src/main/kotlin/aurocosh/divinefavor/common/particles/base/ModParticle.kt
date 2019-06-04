package aurocosh.divinefavor.common.particles.base

import net.minecraft.client.particle.Particle
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

open class ModParticle : Particle {
    val isDead: Boolean
        get() = isExpired

    protected constructor(world: World, position: Vec3d) : super(world, position.x, position.y, position.z)

    protected constructor(worldIn: World, position: Vec3d, motion: Vec3d) : super(worldIn, position.x, position.y, position.z, motion.x, motion.y, motion.z)
}
