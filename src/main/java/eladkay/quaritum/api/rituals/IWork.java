package eladkay.quaritum.api.rituals;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public interface IWork {
    @Nonnull
    String getUnlocalizedName();

    boolean updateTick(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull TileEntity tile, long ticksExisted);

    boolean canRitualRun(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull TileEntity tile);
    //void buildPositions(@Nonnull List<PositionedBlocks> blocks);
}