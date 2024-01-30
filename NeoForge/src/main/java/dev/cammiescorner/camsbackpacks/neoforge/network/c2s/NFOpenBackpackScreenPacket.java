package dev.cammiescorner.camsbackpacks.neoforge.network.c2s;

import dev.cammiescorner.camsbackpacks.network.c2s.OpenBackpackScreenPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.NetworkEvent;

public class NFOpenBackpackScreenPacket {

    public void encode(FriendlyByteBuf buf) {
        // NO-OP
    }

    public static NFOpenBackpackScreenPacket decode(FriendlyByteBuf buf) {
        return new NFOpenBackpackScreenPacket();
    }

    public void handle(NetworkEvent.Context ctx) {
        OpenBackpackScreenPacket.handle(ctx.getSender());
    }
}
