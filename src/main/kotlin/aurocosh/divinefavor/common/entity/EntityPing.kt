package aurocosh.divinefavor.common.entity

import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.lib.SimpleCounter
import aurocosh.divinefavor.common.particles.ModParticles
import aurocosh.divinefavor.common.particles.particles.MobileParticle
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import javax.vecmath.Color3f

class EntityPing(world: World) : Entity(world) {

    private val despawnTimer = SimpleCounter(ConfigSpells.ping.duration)

    init {
        setSize(0.1f, 0.1f)
    }

    override fun entityInit() {}

    override fun onEntityUpdate() {
        if (world.isRemote)
            spawnParticles()
        else
            processDespawn()
    }

    @SideOnly(Side.CLIENT)
    fun spawnParticles() {
        for (i in 0 until ConfigSpells.ping.particleIntensity) {
            val distance = UtilRandom.nextFloat(1f, 5f).toDouble()
            val direction = UtilRandom.nextDirection()
            val pointOnSphere = direction.scale(distance)
            val pointInWorld = pointOnSphere.add(positionVector)
            val speed = direction.scale(-0.15)
            ModParticles.normal.createParticle(pointInWorld) { MobileParticle(world, pointInWorld, speed, COLOR_3_F) }
        }
    }

    private fun processDespawn() {
        if (world.isRemote)
            return
        if (despawnTimer.count())
            setDead()
    }

    override fun processInitialInteract(player: EntityPlayer?, hand: EnumHand?): Boolean {
        if (world.isRemote)
            return true
        setDead()
        return true
    }

    @SideOnly(Side.CLIENT)
    override fun isInRangeToRenderDist(distance: Double): Boolean {
        return distance < RENDER_DISTANCE_SQ
    }

    override fun readEntityFromNBT(compound: NBTTagCompound) {}

    override fun writeEntityToNBT(compound: NBTTagCompound) {}

    override fun isGlowing(): Boolean {
        return true
    }

    companion object {
        private val RENDER_DISTANCE_SQ = ConfigSpells.ping.renderDistance * ConfigSpells.ping.renderDistance
        private val COLOR_3_F = Color3f(0.7f, 0.7f, 0f)
    }
}