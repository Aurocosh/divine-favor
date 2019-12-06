package aurocosh.divinefavor.common.receipes.dynamic

import aurocosh.divinefavor.common.item.common.ModItems
import net.minecraft.item.Item

class PebbleStabilizerRecipe : StabilizerRecipe("pebble_stabilizer_recipe") {
    override fun isStabilizer(item: Item): Boolean {
        return item === ModItems.pebble_stabilizer
    }

    override fun isUnstableGem(item: Item): Boolean {
        return item === ModItems.warp_pebble || item === ModItems.invite_pebble || item === ModItems.storage_pebble
    }

    override fun getStableGem(item: Item): Item {
        return when {
            item === ModItems.warp_pebble -> ModItems.stable_warp_pebble
            item === ModItems.invite_pebble -> ModItems.stable_invite_pebble
            item === ModItems.storage_pebble -> ModItems.stable_storage_pebble
            else -> item
        }
    }
}