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
                    for (int i = 1; i < droppedPackets.length; i++){
                        resendPackets.add(packets.get(Integer.parseInt(droppedPackets[i])));
                    }
                    sendPackets(responseWriter1, resendPackets);
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
          //  if (Math.random() < .8) { ****************************************************************
                responseWriter1.println(idNum + "$" + totalPackets + "$" + arrayList.get(i));

           // }
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
                return "Please choose one of the following characters soliloquies: \n" +
                        "faustus\n" +
                        "tom\n" +
                        "agamemnon\n" +
                        "hamlet\n" +
                        "macbeth\n" +
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

    private static final String[] quotes = {"FAUSTUS: Ah, Faustus.\n" +
            "\n" +
            "Now hast thou but one bare hour to live,\n" +
            "\n" +
            "And then thou must be damned perpetually!\n" +
            "\n" +
            "Stand still, you ever-moving spheres of heaven,\n" +
            "\n" +
            "That time may cease, and midnight never come;\n" +
            "\n" +
            "Fair Nature’s eye, rise, rise again, and make\n" +
            "\n" +
            "Perpetual day; or let this hour be but\n" +
            "\n" +
            "A year, a month, a week, a natural day,\n" +
            "\n" +
            "That Faustus may repent and save his soul!",
            "TOM: I descended the steps of this fire escape for a " +
                    "last time and followed, from then on, in my father’s footsteps, attempting to find in motion what was " +
                    "lost in space. . . . I would have stopped, but I was pursued by something. . . . I pass the lighted window" +
                    " of a shop where perfume is sold. The window is filled with pieces of colored glass, tiny transparent bottles " +
                    "in delicate colors, like bits of a shattered rainbow.",
            "I pray the gods to quit me of my toils,\n" +
                    "\n" +
                    "To close the watch I keep, this livelong year;\n" +
                    "\n" +
                    "For as a watch-dog lying, not at rest,\n" +
                    "\n" +
                    "Propped on one arm, upon the palace-roof\n" +
                    "\n" +
                    "Of Atreus’ race, too long, too well I know\n" +
                    "\n" +
                    "The starry conclave of the midnight sky,\n" +
                    "\n" +
                    "Too well, the splendours of the firmament,\n" +
                    "\n" +
                    "The lords of light, whose kingly aspect shows-\n" +
                    "\n" +
                    "What time they set or climb the sky in turn-\n" +
                    "\n" +
                    "The year’s divisions, bringing frost or fire.",
            "To be, or not to be, that is the question:\n" +
                    "\n" +
                    "Whether ’tis nobler in the mind to suffer\n" +
                    "\n" +
                    "The slings and arrows of outrageous fortune,\n" +
                    "\n" +
                    "Or to take arms against a sea of troubles\n" +
                    "\n" +
                    "And by opposing end them. To die – to sleep,\n" +
                    "\n" +
                    "No more; and by a sleep to say we end\n" +
                    "\n" +
                    "The heart-ache and the thousand natural shocks\n" +
                    "\n" +
                    "That flesh is heir to: ’tis a consummation\n" +
                    "\n" +
                    "Devoutly to be wish’d. To die, to sleep;\n" +
                    "\n" +
                    "To sleep, perchance to dream – ay, there’s the rub…",
            "Is this a dagger which I see before me,\n" +
                    "\n" +
                    "The handle toward my hand? Come, let me clutch thee.\n" +
                    "\n" +
                    "I have thee not, and yet I see thee still.\n" +
                    "\n" +
                    "Art thou not, fatal vision, sensible\n" +
                    "\n" +
                    "To feeling as to sight? or art thou but\n" +
                    "\n" +
                    "A dagger of the mind, a false creation,\n" +
                    "\n" +
                    "Proceeding from the heat-oppressed brain?",
            "Eyes, look your last!\n" +
                    "\n" +
                    "Arms, take your last embrace! and, lips, O you\n" +
                    "\n" +
                    "The doors of breath, seal with a righteous kiss\n" +
                    "\n" +
                    "A dateless bargain to engrossing death!\n" +
                    "\n" +
                    "Come, bitter conduct, come, unsavoury guide!\n" +
                    "\n" +
                    "Thou desperate pilot, now at once run on\n" +
                    "\n" +
                    "The dashing rocks thy sea-sick weary bark!\n" +
                    "\n" +
                    "Here’s to my love!\n" +
                    "\n" +
                    "[Drinks]"
    };

}

