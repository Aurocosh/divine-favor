package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.lib.extensions.toBlockPos
import aurocosh.divinefavor.common.lib.extensions.toVec3d
import aurocosh.divinefavor.common.particles.ModParticles
import aurocosh.divinefavor.common.particles.particles.ImmobileParticle
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilEntity
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.awt.Color
import java.util.*
import javax.vecmath.Color3f

class SpellTalismanBlink(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val blinkDistance: StackPropertyInt = propertyHandler.registerIntProperty("blink_distance", ConfigSpell.blink.maxDistance)

    override fun getApproximateFavorCost(itemStack: ItemStack): Int {
        return favorCost * blinkDistance.getValue(itemStack);
    }

    public override fun validate(context: TalismanContext): Boolean {
        val world = context.world
        val target = getBlinkTarget(context, 0.0)
        val pos = target.toBlockPos()
        return world.isAirBlock(pos) && world.isAirBlock(pos)
    }

    override fun performActionServer(context: TalismanContext) {
        val target = getBlinkTarget(context, 0.0)
        UtilEntity.teleport(context.player, target)
    }

    private fun getBlinkTarget(context: TalismanContext, shift: Double): Vec3d {
        val player = context.player
        val pos = player.positionVector
        val look = player.lookVec
        val distance = blinkDistance.getValue(context.stack)
        return pos.add(look.scale(distance.toDouble())).add(0.0, shift, 0.0)
    }

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        val target = getBlinkTarget(context, context.player.eyeHeight.toDouble())
        val isAir = context.world.isAirBlock(target.toBlockPos())
        if (!isAir)
            return

        val nextRadius = UtilRandom.nextDouble(0.2, 1.2)
        val shift = UtilRandom.nextDirection().scale(nextRadius)
        ModParticles.normal.createParticle(target) { ImmobileParticle(context.world, target.add(shift), Color3f(particleColor), 3) }
    }

    companion object {
        val particleColor = Color.MAGENTA.darker()
    }
}
