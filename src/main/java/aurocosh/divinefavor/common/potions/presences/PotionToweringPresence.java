package aurocosh.divinefavor.common.potions.presences;

import aurocosh.divinefavor.common.config.common.ConfigPresence;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.presence.towering.ToweringPresenceData;
import aurocosh.divinefavor.common.item.calling_stones.ModCallingStones;
import aurocosh.divinefavor.common.lib.DistributedRandomList;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PotionToweringPresence extends ModPotion {
    private static final DistributedRandomList<ModPotion> possibleCurses = new DistributedRandomList<>();

    static {
        possibleCurses.add(ModCurses.armor_corrosion, 0.1);
        possibleCurses.add(ModCurses.crawling_mist, 0.1);
        possibleCurses.add(ModCurses.cripple, 0.1);
        possibleCurses.add(ModCurses.fiery_mark, 0.1);
        possibleCurses.add(ModCurses.fill_lungs, 0.1);
        possibleCurses.add(ModCurses.hollow_leg, 0.1);
        possibleCurses.add(ModCurses.limp_leg, 0.1);
        possibleCurses.add(ModCurses.petrification, 0.1);
        possibleCurses.add(ModCurses.roots, 0.1);
        possibleCurses.add(ModCurses.skyfall, 0.1);
        possibleCurses.add(ModCurses.suffocating_fumes, 0.1);
        possibleCurses.add(ModCurses.wind_leash, 0.1);
        possibleCurses.add(ModCurses.yummy_smell, 0.1);
    }

    public PotionToweringPresence() {
        super("towering_presence", true, 0x7FB8A4);
    }

    @Override
    protected void onPotionAdded(EntityLivingBase livingBase) {
        super.onPotionAdded(livingBase);
        if (!(livingBase instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) livingBase;
        ToweringPresenceData presenceData = PlayerData.get(player).getToweringPresenceData();
        presenceData.reset();
    }

    @Override
    protected void onPotionRemoved(EntityLivingBase livingBase) {
        super.onPotionRemoved(livingBase);
        EntityPlayer player = (EntityPlayer) livingBase;
        player.addItemStackToInventory(new ItemStack(ModCallingStones.calling_stone_timber));
    }

    @Override
    public void performEffect(EntityLivingBase livingBase, int amplifier) {
        EntityPlayer player = (EntityPlayer) livingBase;
        ToweringPresenceData presenceData = PlayerData.get(player).getToweringPresenceData();
        if (!presenceData.tick())
            return;

        int curseTime = UtilRandom.nextInt(ConfigPresence.toweringPresence.minCurseTime, ConfigPresence.toweringPresence.maxCurseTime);
        ModPotion curse = possibleCurses.getRandom();
        livingBase.addPotionEffect(new ModEffect(curse, curseTime).setIsCurse());
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
