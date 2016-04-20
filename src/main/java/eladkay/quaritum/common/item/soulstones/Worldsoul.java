package eladkay.quaritum.common.item.soulstones;

import eladkay.quaritum.api.animus.ISoulstone;
import eladkay.quaritum.client.core.TooltipHelper;
import eladkay.quaritum.common.item.base.ItemMod;
import eladkay.quaritum.common.lib.LibNames;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

public class Worldsoul extends ItemMod implements ISoulstone {
    int counter = 0;

    public Worldsoul() {
        super(LibNames.STONE_OF_THE_WORLDSOUL);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        counter++;
        if (counter % 60 == 1) doPassive(stack);
    }

    @Override
    public void doPassive(ItemStack stack) {
        stack = addAnimus(stack, 1);
    }

    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
        if (itemStack.getTagCompound() != null) {
            if (GuiScreen.isShiftKeyDown()) {
                list.add("Animus: " + getAnimusLevel(itemStack));
            } else {
                list.add(TooltipHelper.local("misc.quaritum.shiftForInfo").replaceAll("&", "\u00a7"));
            }

        }
    }

    @Override
    public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
        itemStack.setTagCompound(new NBTTagCompound());
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, World world) {
        return Integer.MAX_VALUE;
    }


    @Override
    public int getAnimusLevel(ItemStack stack) {
        return stack.getTagCompound().getInteger(LibNames.TAG_ANIMUS);
    }

    @Override
    public ItemStack deductAnimus(ItemStack stack, int amount) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag.getInteger(LibNames.TAG_ANIMUS) - amount < 0) return stack;
        tag.setInteger(LibNames.TAG_ANIMUS, tag.getInteger(LibNames.TAG_ANIMUS) - amount);
        stack.setTagCompound(tag);
        return stack;
    }

    @Override
    public ItemStack addAnimus(ItemStack stack, int amount) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag.getInteger(LibNames.TAG_ANIMUS) + amount > getMaxAnimus()) return stack;
        tag.setInteger(LibNames.TAG_ANIMUS, tag.getInteger(LibNames.TAG_ANIMUS) + amount);
        stack.setTagCompound(tag);
        return stack;
    }

    @Override
    public int getMaxAnimus() {
        return 800;
    }

    @Override
    public boolean isRechargeable() {
        return true;
    }

}
