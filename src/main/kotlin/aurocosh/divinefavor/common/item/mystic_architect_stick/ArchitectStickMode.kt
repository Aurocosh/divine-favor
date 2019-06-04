package aurocosh.divinefavor.common.item.mystic_architect_stick

enum class ArchitectStickMode(val value: Int, val description: String) {
    SELECT_MULTIBLOCK_BASE(0, "Select multiblock base"),
    SELECT_CONTROLLER(1, "Select controller"),
    SELECT_AIR_BLOCK(2, "Select air block"),
    CREATE_TEMPLATE(3, "Create template");

    companion object {

        // Optimization
        val VALUES = values()

        fun clampModeIndex(mode: Int): Int {
            return when {
                mode > 3 -> 0
                mode < 0 -> 3
                else -> mode
            }
        }
    }
}
