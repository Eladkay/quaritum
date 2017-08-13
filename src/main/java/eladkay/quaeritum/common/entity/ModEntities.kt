package eladkay.quaeritum.common.entity

import eladkay.quaeritum.common.Quaeritum
import eladkay.quaeritum.common.lib.LibNames
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.registry.EntityRegistry

object ModEntities {
    private var id: Int = 0

    fun init() {
        registerModEntityWithEgg(EntityChaosborn::class.java, LibNames.CHAOSBORN, 15451, 45615)
        registerModEntity(EntityCircleOfTheFinalMoment::class.java, LibNames.CIRCLE)
        registerModEntity(EntityFirebolt::class.java, "firebolt", 3, true)
        registerModEntity(EntityFrostshock::class.java, "frostshock", 3, true)
    }

    fun registerModEntityWithEgg(parEntityClass: Class<out Entity>, parEntityName: String, parEggColor: Int, parEggSpotsColor: Int, updateFrequency: Int = 3, sendsVelocity: Boolean = false) {
        EntityRegistry.registerModEntity(ResourceLocation("quaeritum:" + parEntityName), parEntityClass, parEntityName, ++id,
                Quaeritum.instance, 80, updateFrequency, sendsVelocity)
        EntityRegistry.registerEgg(ResourceLocation("quaeritum:" + parEntityName), parEggColor, parEggSpotsColor)
    }

    fun registerModEntity(parEntityClass: Class<out Entity>, parEntityName: String, updateFrequency: Int = 3, sendsVelocity: Boolean = false) {
        EntityRegistry.registerModEntity(ResourceLocation("quaeritum:" + parEntityName), parEntityClass, parEntityName, ++id,
                Quaeritum.instance, 80, updateFrequency, sendsVelocity)
    }
}
