package aurocosh.divinefavor.common.receipes.dynamic

import aurocosh.divinefavor.common.item.common.ModItems
import net.minecraft.item.Item

class GemStabilizerRecipe : StabilizerRecipe("gem_stabilizer_recipe") {
    override fun isStabilizer(item: Item): Boolean {
        return item === ModItems.gem_stabilizer
    }

    override fun isUnstableGem(item: Item): Boolean {
        return item === ModItems.warp_gem || item === ModItems.invite_gem
    }

    override fun getStableGem(item: Item): Item {
        return when {
            item === ModItems.warp_gem -> ModItems.stable_warp_gem
            item === ModItems.invite_gem -> ModItems.stable_invite_gem
            else -> item
        }
    }
}