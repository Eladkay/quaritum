package eladkay.quaritum.common.entity

import eladkay.quaritum.common.Quaritum
import eladkay.quaritum.common.lib.LibNames
import net.minecraft.entity.Entity
import net.minecraftforge.fml.common.registry.EntityRegistry

object ModEntities {
    private var id: Int = 0

    fun init() {
        registerModEntityWithEgg(EntityChaosborn::class.java, LibNames.CHAOSBORN, 15451, 45615)
        registerModEntity(EntityCircleOfTheFinalMoment::class.java, LibNames.CIRCLE)
    }

    fun registerModEntityWithEgg(parEntityClass: Class<out Entity>, parEntityName: String, parEggColor: Int, parEggSpotsColor: Int) {
        EntityRegistry.registerModEntity(parEntityClass, parEntityName, ++id,
                Quaritum.instance, 80, 3, false)
        EntityRegistry.registerEgg(parEntityClass, parEggColor, parEggSpotsColor)
    }

    fun registerModEntity(parEntityClass: Class<out Entity>, parEntityName: String) {
        EntityRegistry.registerModEntity(parEntityClass, parEntityName, ++id,
                Quaritum.instance, 80, 3, false)
    }
}
