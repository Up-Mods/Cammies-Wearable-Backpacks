package dev.cammiescorner.camsbackpacks.neoforge;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.network.s2c.UpdateConfigurationPacket;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CamsBackpacks.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {

    @SubscribeEvent
    public static void onDatapackReload(OnDatapackSyncEvent event) {
        if(event.getPlayer() != null) {
            UpdateConfigurationPacket.sendTo(event.getPlayer());
        }
        else {
            UpdateConfigurationPacket.sendTo(event.getPlayerList().getPlayers());
        }
    }
}
