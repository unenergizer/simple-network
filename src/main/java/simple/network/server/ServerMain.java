package simple.network.server;

import simple.network.server.packet.in.PingIn;

class ServerMain {

    public static void main(String[] args) {
        new ServerMain();
    }

    private ServerMain() {
        initializeNetwork();
    }

    /**
     * Initializes the network. Starts listening for client connections
     * and registers network event listeners.
     */
    private void initializeNetwork() {
        System.out.println("\nInitializing network...");
        ServerConnection.getInstance().openServer((eventBus) -> {
            eventBus.registerListener(new PingIn());
        });
    }
}
