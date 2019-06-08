package aurocosh.divinefavor.common.item.gems.soul_shards

import aurocosh.divinefavor.common.spirit.ModSpirits

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
        shard_mind = ItemSoulShard("mind", ModSpirits.romol)
        shard_nether = ItemSoulShard("nether", ModSpirits.neblaze)
        shard_peace = ItemSoulShard("peace", ModSpirits.arbow)
        shard_will = ItemSoulShardPlayer("will", ModSpirits.redwind)
        shard_undeath = ItemSoulShard("undeath", ModSpirits.loon)
        shard_water = ItemSoulShard("water", ModSpirits.blizrabi)
        shard_wild = ItemSoulShard("wild", ModSpirits.squarefury)
        shard_wither = ItemSoulShard("wither", ModSpirits.timber)
    }
}