package eladkay.quaritum.common.rituals;

import com.google.common.collect.Lists;
import eladkay.quaritum.api.rituals.EnumRitualDuration;
import eladkay.quaritum.api.rituals.EnumRitualType;
import eladkay.quaritum.api.rituals.IRitual;
import eladkay.quaritum.api.rituals.PositionedChalk;
import eladkay.quaritum.common.block.ModBlocks;
import eladkay.quaritum.common.block.tile.TileEntityBlueprint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.ArrayList;

public class SimpleTestRitualDiagram implements IRitual {
    @Override
    public String getUnlocalizedName() {
        return "test";
    }

    @Override
    public EnumRitualType getRitualType() {
        return EnumRitualType.DIAGRAM;
    }

    @Override
    public EnumRitualDuration getRitualDuration() {
        return EnumRitualDuration.INSTANT;
    }

    @Override
    public boolean runOnce(World world, EntityPlayer player, BlockPos pos) {
        player.addChatComponentMessage(new TextComponentString("WORKING."));
        return true;

    }

    @Override
    public boolean runDurable(World world, EntityPlayer player, BlockPos pos) {
        return false;
    }

    @Override
    public boolean canRitualRun(World world, EntityPlayer player, BlockPos pos, TileEntity tile) {
        return ((TileEntityBlueprint) tile).debug2;
    }

    @Override
    public ArrayList<ItemStack> getRequiredItems() {
        ArrayList list = Lists.newArrayList();
        list.add(new ItemStack(Items.apple));
        list.add(new ItemStack(Items.slime_ball));
        return list;
    }

    @Override
    public String getCanonicalName() {
        return "Test";
    }

    @Override
    public ArrayList<PositionedChalk> getRequiredPositionedChalk() {
        ArrayList list = Lists.newArrayList();
        list.add(new PositionedChalk(EnumDyeColor.BLUE, 1, 0, 0).setState(ModBlocks.chalk.getStateFromMeta(EnumDyeColor.BLACK.getMetadata())));
        return list;
    }
}
