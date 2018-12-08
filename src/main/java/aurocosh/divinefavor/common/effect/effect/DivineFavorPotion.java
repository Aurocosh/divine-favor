package aurocosh.divinefavor.common.effect.effect;

import aurocosh.divinefavor.common.constants.LibMisc;
import aurocosh.divinefavor.common.util.UtilTextureRender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class DivineFavorPotion extends Potion {

    enum EffectType {
        TIMED,
        PERMAMENT
    }

    private ResourceLocation icon;
    private boolean beneficial;
    private EffectType effectType;

    public DivineFavorPotion(String name, boolean b, int potionColor) {
        super(false, potionColor);
        this.beneficial = b;
        this.setIcon(new ResourceLocation(LibMisc.MOD_ID, "textures/potions/" + name + ".png"));
        this.setPotionName(name);
        effectType = EffectType.TIMED;
    }

    @Override
        public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, EntityLivingBase entityLivingBaseIn, int amplifier, double health) {
        super.affectEntity(source, indirectSource, entityLivingBaseIn, amplifier, health);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isBeneficial() {
    return this.beneficial;//decides top or bottom row
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc) {
        UtilTextureRender.drawTextureSquare(getIcon(), x + 6, y + 6, LibMisc.SQ - 2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderHUDEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc, float alpha) {
    if (mc.gameSettings.showDebugInfo == false)//dont texture on top of the debug text
        UtilTextureRender.drawTextureSquare(getIcon(), x + 4, y + 4, LibMisc.SQ - 2);
    }

    public ResourceLocation getIcon() {
    return icon;
    }

    public void setIcon(ResourceLocation icon) {
    this.icon = icon;
    }

    public void tick(EntityLivingBase entity) {}

    @Override
    public List<ItemStack> getCurativeItems()
    {
        ArrayList<ItemStack> ret = new java.util.ArrayList<>();
        ret.add(new ItemStack(Items.MILK_BUCKET));
        return ret;
    }
}
