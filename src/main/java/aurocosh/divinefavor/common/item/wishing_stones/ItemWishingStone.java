package aurocosh.divinefavor.common.item.wishing_stones;

import aurocosh.divinefavor.common.core.DivineFavorCreativeTabGems;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.item.talismans.ItemTalisman;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncMaxSpellUses;
import aurocosh.divinefavor.common.player_data.spell_count.ISpellUsesHandler;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import static aurocosh.divinefavor.common.player_data.spell_count.SpellUsesDataHandler.CAPABILITY_SPELL_USES;

public class ItemWishingStone extends ModItem {
    private final ModSpirit spirit;

    private final ItemTalisman talisman;
    public ItemWishingStone(ModSpirit spirit, ItemTalisman talisman) {
        super("wishing_stone_" + talisman.getName(), "wishing_stones/" + spirit.getName());
        this.spirit = spirit;
        this.talisman = talisman;

        setMaxStackSize(1);
        setCreativeTab(DivineFavorCreativeTabGems.INSTANCE);
    }

    public ModSpirit getSpirit() {
        return spirit;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
        ItemStack itemStackIn = playerIn.getHeldItem(hand);
        if (worldIn.isRemote)
            return new ActionResult<>(EnumActionResult.PASS, itemStackIn);

        ItemStack stack = playerIn.getHeldItem(hand);
        if (!(stack.getItem() instanceof ItemWishingStone))
            return new ActionResult<>(EnumActionResult.PASS, itemStackIn);

        int count = playerIn.isSneaking() ? 8 : talisman.getStartingSpellUses();
        gainFavor(playerIn, count);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    public void gainFavor(EntityPlayer playerIn, int count) {
        ISpellUsesHandler usesHandler = playerIn.getCapability(CAPABILITY_SPELL_USES, null);
        assert usesHandler != null;

        int maxSpellUses = usesHandler.addMaxSpellUses(talisman.getId(), count);
        new MessageSyncMaxSpellUses(talisman.getId(), maxSpellUses).sendTo(playerIn);
    }
}