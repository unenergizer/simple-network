package simple.network.server.packet.in;

import simple.network.server.packet.out.PingOut;
import simple.network.shared.ClientHandler;
import simple.network.shared.Opcode;
import simple.network.shared.Opcodes;
import simple.network.shared.PacketListener;

public class PingIn implements PacketListener {

    @Opcode(getOpcode = Opcodes.PING)
    public void onIncomingPing(ClientHandler clientHandler) {
        System.out.println("Pong!");
        new PingOut(clientHandler).sendPacket();
    }
}
