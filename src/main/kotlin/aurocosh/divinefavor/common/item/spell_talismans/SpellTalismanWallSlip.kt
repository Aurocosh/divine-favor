package aurocosh.divinefavor.common.item.spell_talismans

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.item.spell_talismans.base.ItemSpellTalisman
import aurocosh.divinefavor.common.item.spell_talismans.base.SpellOptions
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.block.material.Material
import net.minecraft.util.EnumFacing
import java.util.*

class SpellTalismanWallSlip(name: String, spirit: ModSpirit, favorCost: Int, options: EnumSet<SpellOptions>) : ItemSpellTalisman(name, spirit, favorCost, options) {

    override fun performActionServer(context: CastContext) {
        val facing = context.facing
        if (facing == EnumFacing.DOWN || facing == EnumFacing.UP)
            return
        val world = context.world
        val targetPos = UtilCoordinates.findPlaceToTeleport(context.pos.down(), world, facing.opposite, ConfigSpell.wallSlip.maxDistance, false) ?: return
        val floorPos = UtilCoordinates.findPlaceToStandBelow(targetPos, world, ConfigSpell.wallSlip.maxDistance, true) ?: return

        val lavaIsClose = UtilCoordinates.getBlocksInSphere(targetPos, 2)
                .map(world::getBlockState)
                .any { it.material == Material.LAVA }
        val lavaIsOnFloor = UtilCoordinates.getBlocksInSphere(floorPos, 2)
                .map(world::getBlockState)
                .any { it.material == Material.LAVA }

        if (!lavaIsClose && !lavaIsOnFloor)
            UtilEntity.teleport(context.player, targetPos)
    }
}
