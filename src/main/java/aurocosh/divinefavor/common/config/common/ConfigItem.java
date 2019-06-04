package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.entries.items.AwakenedBoneDagger;
import aurocosh.divinefavor.common.config.entries.items.BathingBlendPotion;
import aurocosh.divinefavor.common.config.entries.items.BoneDagger;
import aurocosh.divinefavor.common.config.entries.items.Contract;
import net.minecraftforge.common.config.Config;

@Config(modid = DivineFavor.MOD_ID, name = DivineFavor.MOD_ID + "/item")
public class ConfigItem {
    @Config.Name("Bone dagger")
    public static BoneDagger boneDagger = new BoneDagger();
    @Config.Name("Awakened bone dagger")
    public static AwakenedBoneDagger awakenedBoneDagger = new AwakenedBoneDagger();

    @Config.Name("Contract capacity minor")
    public static Contract contractCapacityMinor = new Contract(0, 0, 100, false);
    @Config.Name("Contract capacity major")
    public static Contract contractCapacityMajor = new Contract(0, 0, 500,false);
    @Config.Name("Contract regen minor")
    public static Contract contractRegenMinor = new Contract(0, 20, 0, false);
    @Config.Name("Contract regen major")
    public static Contract contractRegenMajor = new Contract(0, 200, 0,false);
    @Config.Name("Inform")
    public static Contract contractInform = new Contract(0, 0, 0, true);
    @Config.Name("Creative")
    public static Contract contractCreative = new Contract(3000, 2000, 6000, true);

    @Config.Name("Nether charcoal blend")
    public static BathingBlendPotion blendCharcoal = new BathingBlendPotion(20, 4, 24);
    @Config.Name("Distorted pearl blend")
    public static BathingBlendPotion blendEnderPearl = new BathingBlendPotion(20, 4, 24);
    @Config.Name("Calm feather blend")
    public static BathingBlendPotion blendFeathers = new BathingBlendPotion(20, 4, 24);
    @Config.Name("WIld flesh blend")
    public static BathingBlendPotion blendFleshy = new BathingBlendPotion(20, 4, 24);
    @Config.Name("Tool flint blend")
    public static BathingBlendPotion blendFlint = new BathingBlendPotion(20, 4, 24);
    @Config.Name("Undead lapis blend")
    public static BathingBlendPotion blendLapis = new BathingBlendPotion(20, 4, 24);
    @Config.Name("Mystic redstone blend")
    public static BathingBlendPotion blendRedstone = new BathingBlendPotion(20, 4, 24);
    @Config.Name("Evercold snow blend")
    public static BathingBlendPotion blendSnow = new BathingBlendPotion(20, 4, 24);
    @Config.Name("Cursed wood blend")
    public static BathingBlendPotion blendWood = new BathingBlendPotion(20, 4, 24);

    @Config.Name("Minor wishing stone favor value")
    public static int minorWishingStoneFavorValue = 100;
}