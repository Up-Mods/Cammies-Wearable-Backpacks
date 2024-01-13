package dev.cammiescorner.camsbackpacks.neoforge.network;

import com.mojang.logging.LogUtils;
import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.neoforge.network.c2s.NFEquipBackpackPacket;
import dev.cammiescorner.camsbackpacks.neoforge.network.c2s.NFOpenBackpackScreenPacket;
import dev.cammiescorner.camsbackpacks.neoforge.network.c2s.NFPlaceBackpackPacket;
import dev.cammiescorner.camsbackpacks.neoforge.network.s2c.NFUpdateConfigurationPacket;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;

public class NetworkHandler {

    private static final Logger logger = LogUtils.getLogger();
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            CamsBackpacks.id("main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerMessages() {

        int id = 0;

        INSTANCE.messageBuilder(NFEquipBackpackPacket.class, id++)
                .encoder(NFEquipBackpackPacket::encode)
                .decoder(NFEquipBackpackPacket::decode)
                .consumerMainThread(NFEquipBackpackPacket::handle)
                .add();

        INSTANCE.messageBuilder(NFOpenBackpackScreenPacket.class, id++)
                .encoder(NFOpenBackpackScreenPacket::encode)
                .decoder(NFOpenBackpackScreenPacket::decode)
                .consumerMainThread(NFOpenBackpackScreenPacket::handle)
                .add();

        INSTANCE.messageBuilder(NFPlaceBackpackPacket.class, id++)
                .encoder(NFPlaceBackpackPacket::encode)
                .decoder(NFPlaceBackpackPacket::decode)
                .consumerMainThread(NFPlaceBackpackPacket::handle)
                .add();

        INSTANCE.messageBuilder(NFUpdateConfigurationPacket.class, id++)
                .encoder(NFUpdateConfigurationPacket::encode)
                .decoder(NFUpdateConfigurationPacket::decode)
                .consumerMainThread(NFUpdateConfigurationPacket::handle)
                .add();

        logger.debug("Registered {} messages", id);
    }
}
