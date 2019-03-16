package aurocosh.divinefavor.common.item.soul_shards;

import aurocosh.divinefavor.common.favor.ModFavors;

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
        shard_end = new ItemSoulShard("end", ModFavors.endererer);
        shard_mind = new ItemSoulShard("mind", ModFavors.romol);
        shard_nether = new ItemSoulShard("nether", ModFavors.neblaze);
        shard_peace = new ItemSoulShard("peace", ModFavors.arbow);
        shard_will = new ItemSoulShardPlayer("will",ModFavors.redwind);
        shard_undeath = new ItemSoulShard("undeath", ModFavors.loon);
        shard_water = new ItemSoulShard("water", ModFavors.blizrabi);
        shard_wild = new ItemSoulShard("wild", ModFavors.squarefury);
        shard_wither = new ItemSoulShard("wither", ModFavors.timber);
    }
}