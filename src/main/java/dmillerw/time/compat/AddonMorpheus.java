package dmillerw.time.compat;

import cpw.mods.fml.common.Loader;
import net.quetzi.morpheus.api.INewDayHandler;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author dmillerw
 */
public class AddonMorpheus {

    public static void initialize() {
        if (Loader.isModLoaded("Morpheus")) {
            try {
                Class morpheusRegistry = Class.forName("net.quetzi.morpheus.MorpheusRegistry");
                Field registry = morpheusRegistry.getDeclaredField("registry");
                registry.setAccessible(true);

                Map<Integer, INewDayHandler> registryMap = (Map<Integer, INewDayHandler>) registry.get(morpheusRegistry);
                registryMap.remove(0);
                registryMap.put(0, new TMTOverworldHandler());

                registry.set(morpheusRegistry, registryMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
