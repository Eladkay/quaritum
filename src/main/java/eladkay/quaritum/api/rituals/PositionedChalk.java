package eladkay.quaritum.api.rituals;

import eladkay.quaritum.common.block.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;

public class PositionedChalk {
    public IBlockState state;
    public EnumDyeColor color;
    public int x;
    public int y;
    public int z;

    protected PositionedChalk(EnumDyeColor color, int x, int y, int z) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static PositionedChalk constructPosChalk(EnumDyeColor color, int x, int y, int z) {
        return new PositionedChalk(color, x, y, z).setState(ModBlocks.chalk.getStateFromMeta(color.getMetadata()));
    }

    public IBlockState getState() {
        return state;
    }

    public PositionedChalk setState(IBlockState state) {
        this.state = state;
        return this;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public EnumDyeColor getColor() {
        return color;
    }

    public void setColor(EnumDyeColor color) {
        this.color = color;
    }


}