package aurocosh.divinefavor.common.stack_actions

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.Lambdas
import aurocosh.divinefavor.common.stack_actions.interfaces.IActionAccessor
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

open class StackActionHandler(private val parentName: String) : IActionAccessor {
    private val actionList = ArrayList<StackAction>()
    private val actionMap = HashMap<String, StackAction>()

    override val list get() = actionList

    override fun get(index: Int) = actionList[index]
    override operator fun get(name: String) = actionMap[name]

    override fun exist(index: Int): Boolean = (index > 0 && index < actionList.size)
    override fun exist(name: String) = actionMap.containsKey(name)

    fun registerAction(name: String, clientAction: (EntityPlayer, ItemStack) -> Unit = Lambdas::emptyLambda, serverAction: (EntityPlayer, ItemStack) -> Unit = Lambdas::emptyLambda, orderIndex: Int = 0): StackAction {
        val action = StackAction(name, clientAction, serverAction, orderIndex)
        if (actionMap.containsKey(action.name)) {
            DivineFavor.logger.error("Talisman action conflict in $parentName. Conflicting action name ${action.name}")
        } else {
            actionMap[action.name] = action
            actionList.add(action)
            actionList.sortBy { it.orderIndex }
        }
        return action
    }
}