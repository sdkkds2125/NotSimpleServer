import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class NotSimpleClient {
    private static String[] packetsReceived = new String[20];
    //private static boolean[] received = new boolean[20];
    private static String dropped = "DROPPED";
    private static boolean wasDropped = false;

    public static void main(String[] args) throws IOException {
//        for (int i = 0; i < 20; i++) {
//            received[i] = false;
//        }
        // Hardcode in IP and Port here if required
        args = new String[]{"127.0.0.1", "30121"};

//        if (args.length != 2) {
//            System.err.println(
//                "Usage: java EchoClient <host name> <port number>");
//            System.exit(1);
//        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
                Socket clientSocket = new Socket(hostName, portNumber);
                PrintWriter requestWriter = // stream to write text requests to server
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader responseReader = // stream to read text response from server
                        new BufferedReader(
                                new InputStreamReader(clientSocket.getInputStream()));
                BufferedReader stdIn = // standard input stream to get user's requests
                        new BufferedReader(
                                new InputStreamReader(System.in))
        ) {
            String userInput;
            String serverResponse;

            while ((userInput = stdIn.readLine()) != null) {
                requestWriter.println(userInput); // send request to server
               // while (!wasDropped) {
                    while (!Objects.equals(serverResponse = responseReader.readLine(), "***ALL PACKETS SENT***")) {
                        parsePacket(serverResponse);
                    }
                    checkedForDroppedPackets(requestWriter,responseReader);
//                    if (wasDropped) {
//                        System.out.println("sending dropped");
//                        requestWriter.println(dropped);
//                        wasDropped = false;
//                    } else {
                        System.out.println(Arrays.toString(packetsReceived));
                        System.out.println(serverResponse);
                 //   }

              //  }

            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }

    private static void parsePacket(String packet) {
        System.out.println("in parse");
        System.out.println("packet: " + packet);
        String[] parsed = packet.split("\\$");
        for (int i = 0; i < parsed.length; i++) {
            System.out.println(parsed[i]);
        }
        int index = Integer.parseInt(parsed[0]);
        packetsReceived[index] = parsed[2];
        //received[index] = true;
    }

//    private static void checkedForDroppedPackets() {
//        System.out.println("in check");
//        wasDropped = false;
//        dropped = "DROPPED";
//        for (int i = 0; i < packetsReceived.length; i++) {
//            // for (int i = 0; i < 20; i++){
////
//            if (packetsReceived[i] == null) {
//                dropped += "$" + i;
//                wasDropped = true;
//            }
//        }
//    }
    private static void checkedForDroppedPackets(PrintWriter requestWriter,BufferedReader responseReader ) throws IOException {
        wasDropped = false;
        String serverResponse;
        for (int i = 0; i < packetsReceived.length; i++) {
            // for (int i = 0; i < 20; i++){
//
            if (packetsReceived[i] == null) {
                dropped += "$" + i;
                wasDropped = true;
            }
        }
        if (wasDropped){
            requestWriter.println(dropped);
            while (!Objects.equals(serverResponse = responseReader.readLine(), "***ALL PACKETS SENT***")) {
                parsePacket(serverResponse);
            }
            checkedForDroppedPackets(requestWriter,responseReader);
        }
    }
}

