
// multi-client server
import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        // server is listening on port 5056
        ServerSocket ss = new ServerSocket(5056);

        // running infinite loop for getting
        // client requests
        while (true) {
            Socket s = null;

            try {
                // socket object to receive incoming client requests
                s = ss.accept();

                System.out.println("A new client is connected " + s);

                // obtaining input and output streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread to this client...");

                /*
                 * Note : Every request will always have a triplet of socket, input stream and
                 * output stream. This ensures that each object of this class writes on one
                 * specific stream rather than on multiple streams.
                 */

                // creating a new thread object
                Thread t = new ClientHandler(s, dis, dos);

                // Invoking the start method
                t.start();

            } catch (Exception e) {
                s.close();
                e.printStackTrace();
            }
        }
    }
}

class ClientHandler extends Thread {
    DateFormat forDate = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat forTime = new SimpleDateFormat("hh:mm:ss");

    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;

    // constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    /**
     * it performs three operations: request the user to specify whether time or
     * date needed, read the answer from input stream object and accordingly write
     * the output on the output stream object.
     */
    @Override
    public void run() {
        String received, toreturn;

        while (true) {
            try {
                // ask user what he wants
                dos.writeUTF("What do you want [Date | Time]?\n" + "Type 'Exit' to terminate connection.");

                // receive the answer from client
                received = dis.readUTF();

                if (received.equals("Exit")) {
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection...");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }

                // creating new date object
                Date date = new Date();

                // write on the output stream based
                // on the answer from the client
                switch (received) {
                case "Date":
                    toreturn = forDate.format(date);
                    dos.writeUTF(toreturn);
                    break;

                case "Time":
                    toreturn = forTime.format(date);
                    dos.writeUTF(toreturn);
                    break;

                default:
                    dos.writeUTF("Invalid input");
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // closing resources
            this.dis.close();
            this.dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}