package aurocosh.divinefavor.common.custom_data.player.data.favor

import aurocosh.divinefavor.common.custom_data.CapabilityHelper
import aurocosh.divinefavor.common.item.contract.ItemContract
import aurocosh.divinefavor.common.item.contract.ItemFavorContract
import aurocosh.divinefavor.common.item.contract_binder.ItemContractBinder
import aurocosh.divinefavor.common.lib.extensions.asSequence
import aurocosh.divinefavor.common.lib.extensions.isNotEmpty
import aurocosh.divinefavor.common.registry.mappers.ModMappers
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.items.ItemStackHandler
import java.util.Arrays.asList

// The default implementation of the capability. Holds all the logic.
class SpiritData {

    private val favorValues: IntArray
    private val favorInfinite: BooleanArray
    private val spiritStatuses: Array<SpiritStatus>
    private val contractsStackHandlers: Array<ItemStackHandler>

    init {
        val spirits = ModMappers.spirits.values
        favorValues = IntArray(spirits.size) { 0 }
        favorInfinite = BooleanArray(spirits.size) { false }

        spiritStatuses = Array(spirits.size) { i -> SpiritStatus(spirits[i]) }
        contractsStackHandlers = Array(spirits.size) {
            object : ItemStackHandler(CONTRACT_SLOT_COUNT) {
                override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
                    val item = stack.item
                    return item is ItemContract || item is ItemContractBinder
                }
            }
        }
    }

    fun getContractHandler(spiritId: Int): ItemStackHandler {
        return contractsStackHandlers[spiritId]
    }

    fun serializeContracts(): NBTTagCompound {
        val contractsCompound = NBTTagCompound()
        for (i in contractsStackHandlers.indices)
            contractsCompound.setTag(i.toString(), contractsStackHandlers[i].serializeNBT())
        return contractsCompound
    }

    fun deserializeContracts(compound: NBTTagCompound) {
        for (i in contractsStackHandlers.indices)
            contractsStackHandlers[i].deserializeNBT(compound.getCompoundTag(i.toString()))
        refreshContracts()
    }

    fun isInform(spiritId: Int): Boolean {
        return spiritStatuses[spiritId].isInformActivity
    }

    fun isFavorInfinite(spiritId: Int): Boolean {
        return favorInfinite[spiritId]
    }

    fun setFavorInfinite(spiritId: Int, value: Boolean) {
        favorInfinite[spiritId] = value
    }

    fun toggleFavorInfinite(spiritId: Int) {
        favorInfinite[spiritId] = !favorInfinite[spiritId]
    }

    fun getFavor(spiritId: Int): Int {
        return favorValues[spiritId]
    }

    fun setFavor(spiritId: Int, value: Int) {
        favorValues[spiritId] = spiritStatuses[spiritId].clamp(value)
    }

    fun addFavor(spiritId: Int, value: Int) {
        favorValues[spiritId] = spiritStatuses[spiritId].clamp(favorValues[spiritId] + value)
    }

    fun consumeFavor(spiritId: Int, value: Int): Boolean {
        if (favorInfinite[spiritId])
            return true
        val favorValue = favorValues[spiritId]
        if (favorValue < value)
            return false
        favorValues[spiritId] = spiritStatuses[spiritId].clamp(favorValues[spiritId] - value)
        return true
    }

    fun getMaxFavor(spiritId: Int): Int {
        return spiritStatuses[spiritId].maxLimit
    }

    fun regenerateFavor(spiritId: Int): Boolean {
        val status = spiritStatuses[spiritId]
        val regen = status.regen
        if (regen == 0)
            return false
        favorValues[spiritId] = status.clamp(favorValues[spiritId] + regen)
        return true
    }

    fun getFavorValues(): IntArray {
        return favorValues
    }

    fun setFavorValues(values: IntArray) {
        System.arraycopy(values, 0, favorValues, 0, values.size)
        refreshValues()
    }

    fun getFavorInfinite(): BooleanArray {
        return favorInfinite
    }

    fun setFavorInfinite(values: BooleanArray) {
        System.arraycopy(values, 0, favorInfinite, 0, values.size)
    }

    private fun getContractsFromStack(stack: ItemStack): List<ItemStack> {
        if (stack.item is ItemContract)
            return asList(stack)
        if (stack.item is ItemContractBinder) {
            val handler = CapabilityHelper.getItemHandler(stack) ?: return emptyList()
            return handler.asSequence().filter(ItemStack::isNotEmpty).toList()
        }
        return emptyList()
    }

    fun refreshContracts() {
        spiritStatuses.forEach(SpiritStatus::reset)

        for (i in contractsStackHandlers.indices) {
            val status = spiritStatuses[i]
            val stackHandler = contractsStackHandlers[i]
            stackHandler.asSequence()
                    .filter(ItemStack::isNotEmpty)
                    .map(this::getContractsFromStack)
                    .flatten()
                    .map(ItemStack::getItem)
                    .filterIsInstance<ItemFavorContract>()
                    .forEach(status::addStats)
        }
        refreshValues()
    }

    private fun refreshValues() {
        for (i in favorValues.indices) {
            val status = spiritStatuses[i]
            favorValues[i] = status.clamp(favorValues[i])
        }
    }

    companion object {
        const val CONTRACT_SLOT_COUNT = 22
    }
}
