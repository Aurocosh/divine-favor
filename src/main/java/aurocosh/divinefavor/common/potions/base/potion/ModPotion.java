package aurocosh.divinefavor.common.potions.base.potion;

import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.custom_data.living.potion_status.IPotionStatusHandler;
import aurocosh.divinefavor.common.util.UtilTextureRender;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
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

import static aurocosh.divinefavor.common.custom_data.living.potion_status.PotionStatusGillsDataHandler.CAPABILITY_POTION_STATUS;

public abstract class ModPotion extends Potion {
    protected ResourceLocation icon;

    protected boolean isCurse;
    protected boolean beneficial;

    public ModPotion(String name, boolean beneficial, int potionColor) {
        super(false, potionColor);
        isCurse = false;
        this.beneficial = beneficial;
        this.setIcon(new ResourceLocation(ConstMisc.MOD_ID, "textures/potions/" + name + ".png"));

        //potion.setIcon(potion.getIcon());

        ResourceLocation fullName = ResourceNamer.getFullName(name);
        this.setPotionName("potion." + fullName.toString() + ".name");
        setRegistryName(fullName);
    }

    public void setIsCurse(boolean curse) {
        beneficial = false;
        isCurse = curse;
    }

    @Override
    public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, EntityLivingBase entityLivingBaseIn, int amplifier, double health) {
        super.affectEntity(source, indirectSource, entityLivingBaseIn, amplifier, health);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isBeneficial() {
        return this.beneficial;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        UtilTextureRender.drawTextureSquare(getIcon(), x + 6, y + 6, ConstMisc.SQ - 2);
        renderCustomInvText(x, y, effect, mc);
    }

    @SideOnly(Side.CLIENT)
    public void renderCustomInvText(int x, int y, PotionEffect effect, Minecraft mc) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
        if (mc.gameSettings.showDebugInfo == false)//dont texture on top of the debug text
            UtilTextureRender.drawTextureSquare(getIcon(), x + 4, y + 4, ConstMisc.SQ - 2);
    }

    public ResourceLocation getIcon() {
        return icon;
    }

    public void setIcon(ResourceLocation icon) {
        this.icon = icon;
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        ArrayList<ItemStack> ret = new java.util.ArrayList<>();
        ret.add(new ItemStack(Items.MILK_BUCKET));
        return ret;
    }

    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase livingBase, AbstractAttributeMap attributeMap, int amplifier) {
        super.applyAttributesModifiersToEntity(livingBase, attributeMap, amplifier);
        onPotionAdded(livingBase);
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase livingBase, AbstractAttributeMap attributeMap, int amplifier) {
        super.removeAttributesModifiersFromEntity(livingBase, attributeMap, amplifier);
        onPotionRemoved(livingBase);
    }

    protected void onPotionAdded(EntityLivingBase livingBase){
        if(isCurse){
            IPotionStatusHandler handler = livingBase.getCapability(CAPABILITY_POTION_STATUS, null);
            assert handler != null;
            handler.addCurse();
        }
    }

    protected void onPotionRemoved(EntityLivingBase livingBase){
        if(isCurse){
            IPotionStatusHandler handler = livingBase.getCapability(CAPABILITY_POTION_STATUS, null);
            assert handler != null;
            handler.removeCurse();
        }
    }
}
