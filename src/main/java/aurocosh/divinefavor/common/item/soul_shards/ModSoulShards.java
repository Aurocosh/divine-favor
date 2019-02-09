package aurocosh.divinefavor.common.item.soul_shards;

public final class ModSoulShards {
    public static ItemSoulShard shard_end;
    public static ItemSoulShard shard_mind;
    public static ItemSoulShard shard_nether;
    public static ItemSoulShard shard_peace;
    public static ItemSoulShard shard_undeath;
    public static ItemSoulShard shard_water;
    public static ItemSoulShard shard_wild;
    public static ItemSoulShard shard_wither;
    public static ItemSoulShardPlayer shard_soul;

    public static void preInit() {
        shard_end = new ItemSoulShard("end");
        shard_mind = new ItemSoulShard("mind");
        shard_nether = new ItemSoulShard("nether");
        shard_peace = new ItemSoulShard("peace");
        shard_soul = new ItemSoulShardPlayer("soul");
        shard_undeath = new ItemSoulShard("undeath");
        shard_water = new ItemSoulShard("water");
        shard_wild = new ItemSoulShard("wild");
        shard_wither = new ItemSoulShard("wither");
    }
}