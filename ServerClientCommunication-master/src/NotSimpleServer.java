import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class NotSimpleServer {
            private static ArrayList<String> packets = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        System.out.println("hi");
        // Hard code in port number if necessary:
        args = new String[]{"30121"};

//		if (args.length != 1) {
//			System.err.println("Usage: java EchoServer <port number>");
//			System.exit(1);
//		}

        int portNumber = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
             Socket clientSocket1 = serverSocket.accept();
             PrintWriter responseWriter1 = new PrintWriter(clientSocket1.getOutputStream(), true);
             BufferedReader requestReader1 = new BufferedReader(new InputStreamReader(clientSocket1.getInputStream()));
        ) {
            String usersRequest;

            while ((usersRequest = requestReader1.readLine()) != null) {

                if (!usersRequest.startsWith("DROPPED")) {
                    packets = new ArrayList<>();
                    System.out.println("first test");
                    System.out.println("\"" + usersRequest + "\" received");
                    String response = getQuote(usersRequest);
                    System.out.println("Responding: \"" + response + "\"");
                    packets = breakUpStringIntoPackets(response);
                    sendPackets(responseWriter1, packets);
                } else {
                    System.out.println("ELSE");
                    String[] droppedPackets = usersRequest.split("\\$");
                    ArrayList<String> resendPackets = new ArrayList<>();
                    System.out.println("\"" + usersRequest + "\" received for dropped packets");
                    // Start loop with 1 since first index in droppedPackets is "DROPPED"
                    resendDroppedPackets(responseWriter1,  droppedPackets);
                }
            }
        } catch (IOException e) {
            System.out.println(
                    "Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
        System.out.println("Code ended");

    }

    private static void sendPackets(PrintWriter responseWriter1, ArrayList<String > arrayList) {
        int totalPackets = arrayList.size();
        for (int i = 0; i < arrayList.size(); i++) {

            int idNum = i + 1;
            if (Math.random() < .8) {
                responseWriter1.println(idNum + "$" + totalPackets + "$" + arrayList.get(i));
            }
        }
        responseWriter1.println("***ALL PACKETS SENT***");
    }

    private static void resendDroppedPackets(PrintWriter responseWriter1, String[] droppedPackets){
        // Start loop with 1 since first index in droppedPackets is "DROPPED"
        for (int i = 1; i < droppedPackets.length; i++){
            if (Math.random() < .8) {
                responseWriter1.println(droppedPackets[i] + "$" + (droppedPackets.length-1) + "$" +
                        packets.get(Integer.parseInt(droppedPackets[i])));
            }
        }
        responseWriter1.println("***ALL PACKETS SENT***");
    }

    private static String getQuote(String input) {
        switch (input.trim().toLowerCase()) {
            case "faustus" -> {
                return quotes[0];
            }
            case "tom" -> {
                return quotes[1];
            }
            case "agamemnon" -> {
                return quotes[2];
            }
            case "hamlet" -> {
                return quotes[3];
            }
            case "macbeth" -> {
                return quotes[4];
            }
            case "romeo" -> {
                return quotes[5];
            }
            default -> {
                return "Please choose one of the following characters soliloquies: " +
                        "faustus" +
                        "tom" +
                        "agamemnon" +
                        "hamlet" +
                        "macbeth" +
                        "romeo";
            }

        }
    }

    private static ArrayList<String> breakUpStringIntoPackets(String string) {
        int length = string.length();
        ArrayList<String > packets;
        if (length >= 20) {
            packets = new ArrayList<>();
            int packetLength = length / 20;
            for (int i = 0; i < 20; i++) {
                packets.add(string.substring(i * packetLength, (i + 1) * packetLength));
            }
            // Get any remaining characters
            if (length % 20 != 0) {
                String lastString = packets.get(19) + string.substring(20 * packetLength, length);
                packets.set(19, lastString); //[19] += string.substring(20 * packetLength, length);
            }

        } else {
            packets = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                packets.set(i, String.valueOf(string.charAt(i)));// = String.valueOf(string.charAt(i));
            }
        }
        return packets;
    }

    private static final String[] quotes = {"FAUSTUS: Ah, Faustus." +
            "" +
            "Now hast thou but one bare hour to live," +
            "" +
            "And then thou must be damned perpetually!" +
            "" +
            "Stand still, you ever-moving spheres of heaven," +
            "" +
            "That time may cease, and midnight never come;" +
            "" +
            "Fair Nature’s eye, rise, rise again, and make" +
            "" +
            "Perpetual day; or let this hour be but" +
            "" +
            "A year, a month, a week, a natural day," +
            "" +
            "That Faustus may repent and save his soul!",
            "TOM: I descended the steps of this fire escape for a " +
                    "last time and followed, from then on, in my father’s footsteps, attempting to find in motion what was " +
                    "lost in space. . . . I would have stopped, but I was pursued by something. . . . I pass the lighted window" +
                    " of a shop where perfume is sold. The window is filled with pieces of colored glass, tiny transparent bottles " +
                    "in delicate colors, like bits of a shattered rainbow.",
            "I pray the gods to quit me of my toils," +
                    "" +
                    "To close the watch I keep, this livelong year;" +
                    "" +
                    "For as a watch-dog lying, not at rest," +
                    "" +
                    "Propped on one arm, upon the palace-roof" +
                    "" +
                    "Of Atreus’ race, too long, too well I know" +
                    "" +
                    "The starry conclave of the midnight sky," +
                    "" +
                    "Too well, the splendours of the firmament," +
                    "" +
                    "The lords of light, whose kingly aspect shows-" +
                    "" +
                    "What time they set or climb the sky in turn-" +
                    "" +
                    "The year’s divisions, bringing frost or fire.",
            "To be, or not to be, that is the question:" +
                    "" +
                    "Whether ’tis nobler in the mind to suffer" +
                    "" +
                    "The slings and arrows of outrageous fortune," +
                    "" +
                    "Or to take arms against a sea of troubles" +
                    "" +
                    "And by opposing end them. To die – to sleep," +
                    "" +
                    "No more; and by a sleep to say we end" +
                    "" +
                    "The heart-ache and the thousand natural shocks" +
                    "" +
                    "That flesh is heir to: ’tis a consummation" +
                    "" +
                    "Devoutly to be wish’d. To die, to sleep;" +
                    "" +
                    "To sleep, perchance to dream – ay, there’s the rub…",
            "Is this a dagger which I see before me," +
                    "" +
                    "The handle toward my hand? Come, let me clutch thee." +
                    "" +
                    "I have thee not, and yet I see thee still." +
                    "" +
                    "Art thou not, fatal vision, sensible" +
                    "" +
                    "To feeling as to sight? or art thou but" +
                    "" +
                    "A dagger of the mind, a false creation," +
                    "" +
                    "Proceeding from the heat-oppressed brain?",
            "Eyes, look your last!" +
                    "" +
                    "Arms, take your last embrace! and, lips, O you" +
                    "" +
                    "The doors of breath, seal with a righteous kiss" +
                    "" +
                    "A dateless bargain to engrossing death!" +
                    "" +
                    "Come, bitter conduct, come, unsavoury guide!" +
                    "" +
                    "Thou desperate pilot, now at once run on" +
                    "" +
                    "The dashing rocks thy sea-sick weary bark!" +
                    "" +
                    "Here’s to my love!" +
                    "" +
                    "[Drinks]"
    };

}

