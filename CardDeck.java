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
            System.out.println("\nChoose an option:");
            System.out.println("1. Shuffle Deck");
            System.out.println("2. Sort Deck (Merge Sort)");
            System.out.println("3. Sort Deck (TimSort)");
            System.out.println("4. View Current Deck");
            System.out.println("5. Exit");

            System.out.print("Enter your choice (1-5): ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    Collections.shuffle(deck);
                    System.out.println("\nDeck is shuffled.");
                    break;

                case "2":
                    long mergeStart = System.nanoTime();
                    deck = mergeSort(deck);
                    long mergeEnd = System.nanoTime();
                    System.out.printf("\nDeck was sorted using Merge Sort (%.4f ms).\n",
                                      (mergeEnd - mergeStart) / 1_000_000.0);
                    break;

                case "3":
                    long timStart = System.nanoTime();
                    deck.sort((c1, c2) -> {
                        int suitCompare = suitOrder(c1.getSuit()) - suitOrder(c2.getSuit());
                        if (suitCompare != 0) return suitCompare;
                        return rankOrder(c1.getRank()) - rankOrder(c2.getRank());
                    });
                    long timEnd = System.nanoTime();
                    System.out.printf("\nDeck was sorted using TimSort (%.4f ms).\n",
                                      (timEnd - timStart) / 1_000_000.0);
                    break;

                case "4":
                    printDeck(deck);
                    break;

                case "5":
                    running = false;
                    System.out.println("Thank you for playing! \nMade by Edrich Silva");
                    break;

                default:
                    System.out.println("Invalid input. Please enter 1-5.");
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

    private static List<Card_Shuffle_and_Sort> mergeSort(List<Card_Shuffle_and_Sort> deck) {
        if (deck.size() <= 1) return deck;

        int mid = deck.size() / 2;
        List<Card_Shuffle_and_Sort> left = mergeSort(deck.subList(0, mid));
        List<Card_Shuffle_and_Sort> right = mergeSort(deck.subList(mid, deck.size()));

        return merge(left, right);
    }

    private static List<Card_Shuffle_and_Sort> merge(List<Card_Shuffle_and_Sort> left, List<Card_Shuffle_and_Sort> right) {
        List<Card_Shuffle_and_Sort> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (compareCards(left.get(i), right.get(j)) <= 0) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }

        while (i < left.size()) result.add(left.get(i++));
        while (j < right.size()) result.add(right.get(j++));

        return result;
    }

    private static int compareCards(Card_Shuffle_and_Sort c1, Card_Shuffle_and_Sort c2) {
        int suitCompare = suitOrder(c1.getSuit()) - suitOrder(c2.getSuit());
        if (suitCompare != 0) return suitCompare;
        return rankOrder(c1.getRank()) - rankOrder(c2.getRank());
    }

    private static int suitOrder(String suit) {
        return switch (suit) {
            case "Spades" -> 0;
            case "Hearts" -> 1;
            case "Diamonds" -> 2;
            case "Clubs" -> 3;
            default -> 4;
        };
    }

    private static int rankOrder(String rank) {
        return Arrays.asList(RANKS).indexOf(rank);
    }

    private static void printDeck(List<Card_Shuffle_and_Sort> deck) {
        System.out.println("\nCurrent Deck:\n");
        int count = 0;
        for (Card_Shuffle_and_Sort card : deck) {
            System.out.printf("%-20s", card);
            if (++count % 4 == 0) System.out.println();
        }
    }
}