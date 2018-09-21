package simple.network.server.client;

import simple.network.shared.ClientHandler;

import java.util.HashMap;
import java.util.Map;

public class ClientManager {

    private final Map<ClientHandler, Client> clientMap = new HashMap<>();
    private static ClientManager instance;

    private ClientManager() {
    }

    public static ClientManager getInstance() {
        if (instance == null) instance = new ClientManager();
        return instance;
    }

    public void addClient(ClientHandler clientHandler) {
        clientMap.put(clientHandler, new Client(clientHandler));
    }

    public void removeClient(ClientHandler clientHandler) {
        clientMap.remove(clientHandler);
    }

    public Client getClient(ClientHandler clientHandler) {
        return clientMap.get(clientHandler);
    }

    public int getClientsOnline() {
        return clientMap.size();
    }
}
