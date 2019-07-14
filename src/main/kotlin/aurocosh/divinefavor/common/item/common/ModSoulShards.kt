package aurocosh.divinefavor.common.item.common

import aurocosh.divinefavor.common.item.gems.soul_shards.ItemSoulShard
import aurocosh.divinefavor.common.item.gems.soul_shards.ItemSoulShardPlayer
import aurocosh.divinefavor.common.spirit.ModSpirits
import net.minecraftforge.oredict.OreDictionary

object ModSoulShards {
    lateinit var shard_end: ItemSoulShard
    lateinit var shard_mind: ItemSoulShard
    lateinit var shard_nether: ItemSoulShard
    lateinit var shard_peace: ItemSoulShard
    lateinit var shard_undeath: ItemSoulShard
    lateinit var shard_water: ItemSoulShard
    lateinit var shard_wild: ItemSoulShard
    lateinit var shard_wither: ItemSoulShard
    lateinit var shard_will: ItemSoulShardPlayer

    fun preInit() {
        shard_end = ItemSoulShard("end", ModSpirits.endererer)
        shard_mind = ItemSoulShard("mind", ModSpirits.romol, ModSpirits.materia)
        shard_nether = ItemSoulShard("nether", ModSpirits.neblaze)
        shard_peace = ItemSoulShard("peace", ModSpirits.arbow)
        shard_will = ItemSoulShardPlayer("will", ModSpirits.redwind, ModSpirits.materia)
        shard_undeath = ItemSoulShard("undeath", ModSpirits.loon)
        shard_water = ItemSoulShard("water", ModSpirits.blizrabi)
        shard_wild = ItemSoulShard("wild", ModSpirits.squarefury)
        shard_wither = ItemSoulShard("wither", ModSpirits.timber)
    }

    fun init() {
        OreDictionary.registerOre("soulShard", shard_end)
        OreDictionary.registerOre("soulShard", shard_mind)
        OreDictionary.registerOre("soulShard", shard_nether)
        OreDictionary.registerOre("soulShard", shard_peace)
        OreDictionary.registerOre("soulShard", shard_undeath)
        OreDictionary.registerOre("soulShard", shard_water)
        OreDictionary.registerOre("soulShard", shard_wild)
        OreDictionary.registerOre("soulShard", shard_wither)
        OreDictionary.registerOre("soulShard", shard_will)
    }
}