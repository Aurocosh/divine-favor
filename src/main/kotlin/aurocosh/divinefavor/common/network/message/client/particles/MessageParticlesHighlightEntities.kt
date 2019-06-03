package aurocosh.divinefavor.common.network.message.client.particles

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.talismans.spell.sense.BlockHighlighter
import aurocosh.divinefavor.common.item.talismans.spell.sense.SenseEntitiesPredicate
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.network.message.base.DivineClientMessage
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.passive.IAnimals
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import javax.vecmath.Color3f

class MessageParticlesHighlightEntities : DivineClientMessage {
    var radius: Int = 0
    var particles: Int = 0
    var dimensionId: Int = 0
    var position: BlockPos = BlockPos.ORIGIN

    var minShift: Float = 0f
    var maxShift: Float = 0f
    var color3f: Color3f = Color3f(1f, 1f, 1f)

    var entitiesPredicate: SenseEntitiesPredicate = SenseEntitiesPredicate.ALL

    constructor() {}

    constructor(particles: Int, radius: Int, dimensionId: Int, position: BlockPos, minShift: Float, maxShift: Float, color3f: Color3f, entitiesPredicate: SenseEntitiesPredicate) {
        this.particles = particles
        this.radius = radius
        this.dimensionId = dimensionId
        this.position = position
        this.minShift = minShift
        this.maxShift = maxShift
        this.color3f = color3f
        this.entitiesPredicate = entitiesPredicate
    }

    @SideOnly(Side.CLIENT)
    override fun handleSafe() {
        val player = DivineFavor.proxy.clientPlayer
        val world = player.world
        if (dimensionId != world.provider.dimension)
            return

        val predicate = getPredicate(player)
        val boundingBox = UtilCoordinates.getBoundingBox(player.positionVector, radius.toDouble())
        val livingBaseList = world.getEntitiesWithinAABB(EntityLivingBase::class.java, boundingBox, predicate)
        val vec3dList = livingBaseList.S.map { livingBase -> livingBase.getPositionEyes(0f) }.toList()

        BlockHighlighter.spawnParticles(color3f, maxShift, minShift, particles, world, vec3dList)
    }

    @SideOnly(Side.CLIENT)
    private fun getPredicate(player: EntityPlayer): (EntityLivingBase?) -> Boolean {
        when (entitiesPredicate) {
            SenseEntitiesPredicate.PASSIVE -> return { livingBase -> livingBase is IAnimals && livingBase !is IMob }
            SenseEntitiesPredicate.HOSTILE -> return { livingBase -> livingBase is IMob }
            SenseEntitiesPredicate.PLAYERS -> return { livingBase -> livingBase !== player && livingBase is EntityPlayer }
            SenseEntitiesPredicate.ALL -> return { livingBase -> livingBase !== player }
        }
    }
}
