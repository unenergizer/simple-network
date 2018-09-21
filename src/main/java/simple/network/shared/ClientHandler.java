package simple.network.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@AllArgsConstructor
@Getter
public class ClientHandler {
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    @FunctionalInterface
    private interface Reader {
        void accept() throws IOException;
    }

    public String readString() {
        try {
            return inputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int readInt() {
        readIn(() -> inputStream.readInt());
        return 0;
    }

    public int readChar() {
        readIn(() -> inputStream.readChar());
        return 0;
    }

    private void readIn(Reader reader) {
        try {
            reader.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public byte readByte() {
        try {
            return inputStream.readByte();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0x0;
    }

    /**
     * This is used to send the entity packet data.
     *
     * @param opcode        The code that defines what this packet contents will contain.
     * @param writeCallback The data we will be sending to the client.
     */
    public void write(byte opcode, Write writeCallback) {
        try {
            outputStream.writeByte(opcode);
            writeCallback.accept(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnects this client from the server.
     */
    public void closeConnection() {
        try {
            if (socket != null) socket.close();
            if (outputStream != null) outputStream.close();
            if (inputStream != null) inputStream.close();
            socket = null;
            outputStream = null;
            inputStream = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
