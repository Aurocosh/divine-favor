package aurocosh.divinefavor.common.item.soul_shards;

import aurocosh.divinefavor.common.spirit.ModSpirits;

public final class ModSoulShards {
    public static ItemSoulShard shard_end;
    public static ItemSoulShard shard_mind;
    public static ItemSoulShard shard_nether;
    public static ItemSoulShard shard_peace;
    public static ItemSoulShard shard_undeath;
    public static ItemSoulShard shard_water;
    public static ItemSoulShard shard_wild;
    public static ItemSoulShard shard_wither;
    public static ItemSoulShardPlayer shard_will;

    public static void preInit() {
        shard_end = new ItemSoulShard("end", ModSpirits.endererer);
        shard_mind = new ItemSoulShard("mind", ModSpirits.romol);
        shard_nether = new ItemSoulShard("nether", ModSpirits.neblaze);
        shard_peace = new ItemSoulShard("peace", ModSpirits.arbow);
        shard_will = new ItemSoulShardPlayer("will",ModSpirits.redwind);
        shard_undeath = new ItemSoulShard("undeath", ModSpirits.loon);
        shard_water = new ItemSoulShard("water", ModSpirits.blizrabi);
        shard_wild = new ItemSoulShard("wild", ModSpirits.squarefury);
        shard_wither = new ItemSoulShard("wither", ModSpirits.timber);
    }
}