package aurocosh.divinefavor.common.block_operations.`do`

import aurocosh.divinefavor.common.block_operations.undo.UndoBuild
import aurocosh.divinefavor.common.block_operations.undo.UndoOperation
import aurocosh.divinefavor.common.block_templates.TemplateFinalBlockState
import aurocosh.divinefavor.common.tasks.TemplatePlacingTask
import aurocosh.divinefavor.common.tasks.base.BaseTask
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

class TemplateBuildOperation(private val template: List<TemplateFinalBlockState>) : BuildOperation() {
    override fun getBuildingTask(player: EntityPlayer, world: World): Pair<BaseTask, UndoOperation> {
        val finalTemplate = template.shuffled()
        val coordinates = finalTemplate.map(TemplateFinalBlockState::pos)
        val undoBuild = UndoBuild(coordinates, this, Int.MAX_VALUE)
        val placingTask = TemplatePlacingTask(finalTemplate, player, Int.MAX_VALUE)
        return Pair(placingTask, undoBuild)
    }
}