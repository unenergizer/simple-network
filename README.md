# simple-network
Simple client/server network. Created by Robert Brown & Joseph Rugh.

Code originally written for a game client and authoritative server combination.
The code has been extracted from the old source and cleaned up for archive here.

# usage

Contents of the shared package (simple.network.shared) must be included in both the client program and server program.
Contents of the client package (simple.network.client) can be copied to your client program (possible game client).
Contents of the server package (simple.network.server) can be copied to your server program (possible authoritative game server).

To add a new listener make sure you:
1. Assign variable name and opcode in package simple.network.shared.Opcodes
2. Register your custom listeners (simple.network.shared.PacketListener) on both the client and server programs.
3. Put custom listeners into the simple.network.server/client.packet.in/out respectively.

Server specific:
Modify the ClientManager class for your server as a “PlayerManager” class.  You can treat the Client class as a player class for games.

