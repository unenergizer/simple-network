package simple.network.server.packet.out;

import simple.network.shared.ClientHandler;
import simple.network.shared.Opcodes;
import simple.network.shared.PacketOut;

import java.io.ObjectOutputStream;

public class PingOut extends PacketOut {

    public PingOut(ClientHandler clientHandler) {
        super(Opcodes.PING, clientHandler);
    }

    @Override
    protected void createPacket(ObjectOutputStream write) {
        System.out.println("Ping!");
    }
}
