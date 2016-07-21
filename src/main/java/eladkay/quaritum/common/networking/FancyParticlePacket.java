package eladkay.quaritum.common.networking;

import eladkay.quaritum.common.Quaritum;
import eladkay.quaritum.common.core.LogHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class FancyParticlePacket extends MessageBase<FancyParticlePacket> {
    private double x;
    private double y;
    private double z;
    private int amount = 100;
    @Override
    public void fromBytes(ByteBuf buf) {

        /*x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        amount = buf.readInt();*/
        NBTTagCompound tag = ByteBufUtils.readTag(buf);
        x = tag.getDouble("x");
        y = tag.getDouble("y");
        z = tag.getDouble("z");
        amount = tag.getInteger("amount");
        LogHelper.logDebug("x = " + x + " y = " + y + " z = " + z);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setDouble("x", x);
        tag.setDouble("y", y);
        tag.setDouble("z", z);
        tag.setInteger("amount", amount);
        ByteBufUtils.writeTag(buf, tag);
        LogHelper.logDebug("x = " + x + " y = " + y + " z = " + z);
    }

    public FancyParticlePacket() {
        x = 0;
        y = 0;
        z = 0;
    }
    public FancyParticlePacket(double xc, double yc, double zc, int amount) {
        x = xc;
        y = yc;
        z = zc;
        this.amount = amount;
        LogHelper.logDebug("x = " + x + " y = " + y + " z = " + z);
    }

    @Override
    public void handleClientSide(FancyParticlePacket message, EntityPlayer player) {
        LogHelper.logDebug("x = " + x + " y = " + y + " z = " + z);

        //do packety stuff
        for (int i = 0; i < message.amount; i++) {
            //world, x, y, z
            if(Minecraft.getMinecraft().theWorld != null)
                Quaritum.proxy.spawnStafflikeParticles(
                        Minecraft.getMinecraft().theWorld, //world
                        message.x + (Minecraft.getMinecraft().theWorld.rand.nextFloat()) * (Minecraft.getMinecraft().theWorld.rand.nextDouble()), //x
                        message.y + Minecraft.getMinecraft().theWorld.rand.nextDouble() - 0.5D, //y
                        message.z + (Minecraft.getMinecraft().theWorld.rand.nextFloat()) * (Minecraft.getMinecraft().theWorld.rand.nextDouble()) //z
                );
            //world, x, y, z, motion, rgb
            /*Quaritum.proxy.spawnParticleMagixFX(
                    Minecraft.getMinecraft().theWorld, //world
                    x + 2 * (Minecraft.getMinecraft().theWorld.rand.nextFloat() - 0.5), //x
                    y + 2.0 * (Minecraft.getMinecraft().theWorld.rand.nextFloat() - 0.5) + 1.0, //y
                    z + 2.0 * (Minecraft.getMinecraft().theWorld.rand.nextFloat() - 0.5), //z
                    x, y + 1.0, z,  //motion
                    255, 0, 0); //rgb*/
        }
        //Player x, y, z, partial ticks (Use 1 for now), TE x, y, z, total ticks (use world time), Item x, y, z

    }

    @Override
    public void handleServerSide(FancyParticlePacket message, EntityPlayer player) {
        LogHelper.logDebug("x = " + x + " y = " + y + " z = " + z);
    }
}
