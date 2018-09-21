package simple.network.server.client;

import lombok.Getter;
import simple.network.shared.ClientHandler;
import simple.network.shared.Write;

public class Client {

    @Getter
    private final ClientHandler clientHandler;

    Client(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public void sendPacket(byte opcode, Write writeCallback) {
        clientHandler.write(opcode, writeCallback);
    }
}
