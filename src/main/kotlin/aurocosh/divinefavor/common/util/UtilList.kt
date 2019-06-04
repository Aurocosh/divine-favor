package aurocosh.divinefavor.common.util

import java.util.*

object UtilList {
    @SafeVarargs
    fun <T> unite(vararg lists: List<T>): List<T> {
        if(lists.isEmpty())
            return emptyList()

        var size = 0
        for (list in lists)
            size += list.size

        val list = ArrayList<T>(size)
        for (subList in lists)
            list.addAll(subList)

        return list
    }
}
