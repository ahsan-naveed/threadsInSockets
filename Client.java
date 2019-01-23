// client

import java.util.Scanner;
import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] args) throws IOException {
        try {
            Scanner scn = new Scanner(System.in);

            // getting localhost ip
            InetAddress ip = InetAddress.getByName("localhost");

            // establishing the connection on port 5056
            Socket s = new Socket(ip, 5056);

            // obtaining input and output streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            // the following loop performs the exchange of
            // information between client and client handler
            while(true) {
                System.out.println(dis.readUTF());
                String toSend = scn.nextLine();
                dos.writeUTF(toSend);

                // if the client sends exit close the connection
                // and then break from the while loop
                if(toSend.equals("Exit")) {
                    System.out.println("Closing the connection: " + s);
                    s.close();
                    System.out.println("Connection closed");
                    break;
                }

                // printing date or time as requested by client
                String received = dis.readUTF();
                System.out.println(received);
            }

            // closing resources
            scn.close();
            dis.close();
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}         

                     
    
    
    
    
    
    
    
    