package simple.network.client;

import simple.network.client.packet.in.PingIn;
import simple.network.shared.ServerConstants;

class ClientMain {

    public static void main(String[] args) {
        new ClientMain();
    }

    private ClientMain() {
        initializeNetwork();
    }

    /**
     * Initialize connection to the remote server.
     */
    private void initializeNetwork() {
        ClientConnection.getInstance().openConnection(
                ServerConstants.SERVER_ADDRESS,
                (short) ServerConstants.SERVER_PORT,
                clientEventBus -> clientEventBus.registerListener(new PingIn()));
    }
}
