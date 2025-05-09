import java.util.*;

public class CardDeck {

    private static final String[] SUITS = {"Spades", "Hearts", "Diamonds", "Clubs"};
    private static final String[] RANKS = {"A", "2", "3", "4", "5", "6", "7",
                                           "8", "9", "10", "J", "Q", "K"};

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<Card_Shuffle_and_Sort> deck = createDeck();
        boolean running = true;

        while (running) {
            System.out.println("\n🎴 Deck Master - Choose an option:");
            System.out.println("1. Shuffle Deck");
            System.out.println("2. Sort Deck");
            System.out.println("3. View Current Deck");
            System.out.println("4. Exit");

            System.out.print("Enter your choice (1-4): ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    Collections.shuffle(deck);
                    System.out.println("\n✅ Deck shuffled.");
                    break;
                case "2":
                    sortDeck(deck);
                    System.out.println("\n✅ Deck sorted.");
                    break;
                case "3":
                    printDeck(deck);
                    break;
                case "4":
                    running = false;
                    System.out.println("👋 Goodbye!");
                    break;
                default:
                    System.out.println("❌ Invalid input. Please enter 1-4.");
            }
        }
    }

    private static List<Card_Shuffle_and_Sort> createDeck() {
        List<Card_Shuffle_and_Sort> deck = new ArrayList<>();
        for (String suit : SUITS) {
            for (String rank : RANKS) {
                deck.add(new Card_Shuffle_and_Sort(suit, rank));
            }
        }
        return deck;
    }

    private static void sortDeck(List<Card_Shuffle_and_Sort> deck) {
        deck.sort(new Comparator<Card_Shuffle_and_Sort>() {
            public int compare(Card_Shuffle_and_Sort c1, Card_Shuffle_and_Sort c2) {
                int suitCompare = suitOrder(c1.getSuit()) - suitOrder(c2.getSuit());
                if (suitCompare == 0) {
                    return rankOrder(c1.getRank()) - rankOrder(c2.getRank());
                }
                return suitCompare;
            }

            private int suitOrder(String suit) {
                return switch (suit) {
                    case "Spades" -> 0;
                    case "Hearts" -> 1;
                    case "Diamonds" -> 2;
                    case "Clubs" -> 3;
                    default -> 4;
                };
            }

            private int rankOrder(String rank) {
                return Arrays.asList(RANKS).indexOf(rank);
            }
        });
    }

    private static void printDeck(List<Card_Shuffle_and_Sort> deck) {
        System.out.println("\n🃏 Current Deck:\n");
        int count = 0;
        for (Card_Shuffle_and_Sort card : deck) {
            System.out.printf("%-20s", card);
            count++;
            if (count % 4 == 0) System.out.println();
        }
    }
}
