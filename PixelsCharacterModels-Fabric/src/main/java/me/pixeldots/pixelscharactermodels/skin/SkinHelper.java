package me.pixeldots.pixelscharactermodels.skin;

import java.nio.file.Paths;
import java.util.UUID;

import lain.mods.skins.api.SkinProviderAPI;
import me.pixeldots.pixelscharactermodels.PCMClient;

public class SkinHelper {
    
    public static void registerProviders(boolean reload) {
        SkinProviderAPI.SKIN.clearProviders();
        SkinProviderAPI.CAPE.clearProviders();
        
        if (!reload) {
            SkinProviderAPI.SKIN.registerProvider(new PCMSkinProvider(Paths.get(".", "PCM")));
            SkinProviderAPI.CAPE.registerProvider(new PCMCapeProvider(Paths.get(".", "PCM")));
        } else {
            SkinProviderAPI.SKIN.registerProvider(new PCMSkinProvider());
            SkinProviderAPI.CAPE.registerProvider(new PCMCapeProvider());
        }
    }
        
    public static void reloadSkins() {
        registerProviders(true);
    }

    public static void setSkinSuffix(UUID uuid, String suffix) {
        PCMClient.PlayerSkinList.put(uuid, suffix);
    }

    public static void clearSkins() {
        PCMClient.PlayerSkinList.clear();
        reloadSkins();
    }

}
