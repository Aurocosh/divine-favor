package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.TalismanContext
import aurocosh.divinefavor.common.particles.ModParticles
import aurocosh.divinefavor.common.particles.particles.ImmobileParticle
import aurocosh.divinefavor.common.stack_properties.StackPropertyInt
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilEntity
import aurocosh.divinefavor.common.util.UtilRandom
import net.minecraft.item.ItemStack
import net.minecraft.util.math.Vec3d
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.awt.Color
import java.util.*
import javax.vecmath.Color3f

class SpellTalismanWarp(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {
    private val blinkDistance: StackPropertyInt = propertyHandler.registerIntProperty("blink_distance", ConfigSpell.warp.maxDistance)

    override fun getApproximateFavorCost(itemStack: ItemStack): Int {
        return favorCost * blinkDistance.getValue(itemStack);
    }

    override fun performActionServer(context: TalismanContext) {
        val target = getTarget(context, 0.0)
        UtilEntity.teleport(context.player, target)
    }

    private fun getTarget(context: TalismanContext, shift: Double): Vec3d {
        val pos = context.player.positionVector
        val look = context.player.lookVec
        val distance = blinkDistance.getValue(context.stack)
        val target = pos.add(look.scale(distance.toDouble())).add(0.0, shift, 0.0)
        return target
    }

    @SideOnly(Side.CLIENT)
    override fun handleRendering(context: TalismanContext, lastEvent: RenderWorldLastEvent) {
        val target = getTarget(context, context.player.eyeHeight.toDouble())
        val nextRadius = UtilRandom.nextDouble(0.2, 1.2)
        val shift = UtilRandom.nextDirection().scale(nextRadius)
        ModParticles.noDepth.createParticle(target) { ImmobileParticle(context.world, target.add(shift), Color3f(particleColor), 3) }
    }

    companion object {
        val particleColor = Color.RED.darker()
    }
}
