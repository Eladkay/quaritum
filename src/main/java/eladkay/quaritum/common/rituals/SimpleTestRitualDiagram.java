package eladkay.quaritum.common.rituals;

import com.google.common.collect.Lists;
import com.sun.istack.internal.Nullable;
import eladkay.quaritum.api.rituals.IDiagram;
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

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class SimpleTestRitualDiagram implements IDiagram {
    @Override
    public String getUnlocalizedName() {
        return "test";
    }

    @Nonnull
    @Override
    public boolean run(@Nonnull World world, @Nullable EntityPlayer player, @Nonnull BlockPos pos) {
        player.addChatComponentMessage(new TextComponentString("WORKING."));
        player.addChatComponentMessage(new TextComponentString("Blue Chalk Blockstate:" + ModBlocks.chalk.getStateFromMeta(EnumDyeColor.BLUE.getMetadata())));
        player.addChatComponentMessage(new TextComponentString("Blue Chalk Meta:" + EnumDyeColor.BLUE.getMetadata()));
        player.addChatComponentMessage(new TextComponentString("Black Chalk Blockstate:" + ModBlocks.chalk.getStateFromMeta(EnumDyeColor.BLACK.getMetadata())));
        player.addChatComponentMessage(new TextComponentString("Black Chalk Meta:" + EnumDyeColor.BLACK.getMetadata()));
        return true;
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
    public void buildChalks(@Nonnull List<PositionedChalk> chalks) {

    }

}