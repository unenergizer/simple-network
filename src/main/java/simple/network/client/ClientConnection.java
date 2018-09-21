package simple.network.client;

import lombok.Getter;
import simple.network.client.packet.out.PingOut;
import simple.network.shared.ClientHandler;
import simple.network.shared.EventBus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.function.Consumer;

class ClientConnection {

    private static ClientConnection instance;
    private final EventBus eventBus = new EventBus();
    private final int SECONDS_TO_TIMEOUT = 10;
    @Getter
    private ClientHandler clientHandler;
    private boolean connected;

    private ClientConnection() {
    }

    /**
     * Gets the main instance of this class.
     *
     * @return A singleton instance of this class.
     */
    static ClientConnection getInstance() {
        if (instance == null) instance = new ClientConnection();
        return instance;
    }

    /**
     * Attempts to establish a connection with the server.
     *
     * @param address           The address of the remote server we want to connect to.
     * @param port              The port of the remote server.
     * @param registerListeners Packets that we will listen for from the server.
     */
    void openConnection(final String address, final short port, final Consumer<EventBus> registerListeners) {
        System.out.println("\nAttempting network connection...");

        new Thread(() -> {
            Socket socket = null;

            try {

                socket = new Socket();
                socket.connect(new InetSocketAddress(address, port), 1000 * SECONDS_TO_TIMEOUT);

                System.out.println("Socket established!");

            } catch (IOException e) {

                // Failed to openConnection
                if (e instanceof ConnectException) {
                    System.out.println("Failed to openConnection to server!");
                    return;
                } else {
                    e.printStackTrace();
                }
            }

            connected = true;

            registerListeners.accept(eventBus);

            receivePackets(socket);

        }, "Connection").start();
    }

    /**
     * Attempt to open up an object input and output stream via the socket.
     *
     * @param socket The connection to the remote server.
     */
    private void receivePackets(Socket socket) {
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;

        // Try to establish a input and output streams.
        try {

            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());

            System.out.println("Input/output streams established!");

        } catch (IOException e1) {
            e1.printStackTrace();
        }

        // Create our server handler
        clientHandler = new ClientHandler(socket, outputStream, inputStream);

        System.out.println("Connection established! Receiving packets!");

        new Thread(() -> {
            while (connected) {
                try {

                    eventBus.publish(clientHandler.getInputStream().readByte(), clientHandler);

                } catch (IOException e) {
                    // Socket closed
                    if (!(e instanceof SocketException && !connected)) {

                        closeConnection();
                        break;
                    }
                }
            }
        }, "receive_packets").start();

        new PingOut(clientHandler).sendPacket();
    }

    /**
     * Safely closes a network connection.
     */
    private void closeConnection() {
        System.out.println("Closing network connection.");

        connected = false;
        clientHandler.closeConnection();
    }
}
