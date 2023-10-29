import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Main {
    static String[] packetsReceived2 = new String[20];
    static String dropped2;
    static String[] resendingDrops = new String[20];
    static boolean wasDropped2 = false;
    static ArrayList<Integer> nums = new ArrayList<>();
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            nums.add(i);
        }
        System.out.println("Hello world!");
        String[] packets = new String[21];
        System.out.println(quotes[0].length());
        String s = "DROPPED$45$3$2$56";
        packets = breakUpStringIntoPackets(s);
        do {
            for (int i = 0; i < packetsReceived2.length - 3; i++) {
                String temp = (i + 1) + "$20$filler";
                parsePacket(temp);
            }
            System.out.println(Arrays.toString(packetsReceived2));
            checkedForDroppedPackets();
            System.out.println(dropped2);
//        for (String p : packets) {
//            System.out.print(p);
//        }
            String[] droppedPackets2 = dropped2.split("\\$");
            String[] replacements = resendDroppedPackets(droppedPackets2);
            String[] temp;
            for (int i = 0; i < replacements.length; i++) {
                if (!Objects.equals(replacements[i], "temp")) {
                    temp = replacements[i].split("\\$");

                    packetsReceived2[Integer.parseInt(temp[0])] = temp[2];
                }
            }
            System.out.println("resutl:::::");
            System.out.println(Arrays.toString(packetsReceived2));
        }while (wasDropped2);
    }
    private static String[] resendDroppedPackets(String[] droppedPackets){
       // resendingDrops = new ArrayList<>();
        Arrays.fill(resendingDrops, "temp");
        for (int i = 1; i < droppedPackets.length; i++){
            if (Math.random() < .8) {
                resendingDrops[i] = (droppedPackets[i] + "$" + (droppedPackets.length-1) + "$text" +
                        nums.get(Integer.parseInt(droppedPackets[i])));
            }
        }
//        responseWriter1.println("***ALL PACKETS SENT***");
        return resendingDrops;
    }
    private static void checkedForDroppedPackets() {
        wasDropped2 = false;
        dropped2 = "DROPPED";
        for (int i = 0; i < packetsReceived2.length; i++) {
            // for (int i = 0; i < 20; i++){
//
            if (packetsReceived2[i] == null) {
                dropped2 += "$" + i;
                wasDropped2 = true;
            }
        }
    }
    private static void parsePacket(String packet) {
        System.out.println("packet: " + packet);
        String[] parsed = packet.split("\\$");
        for (int i = 0; i < parsed.length; i++) {
            System.out.println(parsed[i]);
        }
        int index = Integer.parseInt(parsed[0]) - 1;
        packetsReceived2[index] = parsed[2];
        //received[index] = true;
    }

    private static String[] breakUpStringIntoPackets(String string) {
        int length = string.length();
        String[] packets;
        if (string.startsWith("DROPPED")){
            String[] dp = string.split("\\$");
            for (String s : dp){
                System.out.println(s);
            }
        }
        if (length >= 20) {
            packets = new String[20];
            int packetLength = length / 20;
            for (int i = 0; i < 20; i++) {
                packets[i] = string.substring(i * packetLength, (i + 1) * packetLength);
            }
            // Get any remaining characters
            if (length % 20 != 0) {
                packets[19] += string.substring(20 * packetLength, length);
            }

        } else {
            packets = new String[length];
            for (int i = 0; i < length; i++) {
                packets[i] = String.valueOf(string.charAt(i));
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
            "Fair Nature's eye, rise, rise again, and make\n" +
            "\n" +
            "Perpetual day; or let this hour be but\n" +
            "\n" +
            "A year, a month, a week, a natural day,\n" +
            "\n" +
            "That Faustus may repent and save his soul!",
            "TOM: I descended the steps of this fire escape for a " +
                    "last time and followed, from then on, in my father's footsteps, attempting to find in motion what was " +
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
                    "Of Atreus' race, too long, too well I know\n" +
                    "\n" +
                    "The starry conclave of the midnight sky,\n" +
                    "\n" +
                    "Too well, the splendours of the firmament,\n" +
                    "\n" +
                    "The lords of light, whose kingly aspect shows-\n" +
                    "\n" +
                    "What time they set or climb the sky in turn-\n" +
                    "\n" +
                    "The year's divisions, bringing frost or fire.",
            "To be, or not to be, that is the question:\n" +
                    "\n" +
                    "Whether 'tis nobler in the mind to suffer\n" +
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
                    "That flesh is heir to: 'tis a consummation\n" +
                    "\n" +
                    "Devoutly to be wish'd. To die, to sleep;\n" +
                    "\n" +
                    "To sleep, perchance to dream – ay, there's the rub…",
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
                    "Here's to my love!\n" +
                    "\n" +
                    "[Drinks]"
    };

}
