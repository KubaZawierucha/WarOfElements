import java.util.Random;
import java.util.Scanner;

// enumeration contains every element available in the game
enum Element {
    // fire < water < rock < tornado < fire < ...
    FIRE, WATER, ROCK, TORNADO
}

public class WarOfElements {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // round counter
        int round = 1;
        int roundNumber;
        int allPlayers;
        int humanPlayers;

        System.out.println("Game settings:\n" +
                "Rounds number: ");
        roundNumber = input.nextInt();
        System.out.println("Total number of players: ");
        allPlayers = input.nextInt();
        System.out.println("Number of human players: ");
        humanPlayers = input.nextInt();

        System.out.println("\nSettings:\n" +
                "Rounds number: " + roundNumber + "\n" +
                "Total number of players: " + allPlayers + "\n" +
                "Number of human players: " + humanPlayers + "\n");

        // which element player1 chose - playerChoice[0], player2 0 playerChoice[1], ..., playerN - playerChoice[n-1]
        int[] playerChoice = new int[allPlayers];
        // how many points do players have
        int[] points = new int[allPlayers];
        // what and how many elements are currently in the round
        int[] elementsInRound = new int[4];

        resetArray(points);

        while (round <= roundNumber) {
            // at the start of the round, the number of each element = 0
            resetArray(elementsInRound);

            System.out.println("Round " + round + "\n");

            // for each player
            for (int i = 0; i < humanPlayers; i++) {
                System.out.println("[Player " + (i+1) + "]\n" +
                        "Choose your element:\n" +
                        "[1] Fire\n" +
                        "[2] Water\n" +
                        "[3] Rock\n" +
                        "[4] Tornado");

                while ((playerChoice[i] = input.nextInt()) < 1 || playerChoice[i] > 4) {
                    System.out.println("You can only choose: 1, 2, 3 or 4.");
                }

                // add correct element to our list
                elementsInRound[playerChoice[i] - 1]++;

                System.out.print("[Player " + (i+1) + "]\nYou chose: ");
                showChoice(playerChoice[i]);
                System.out.println();
            }

            // for computer players
            for (int i = humanPlayers; i < allPlayers; i++) {
                // each computer draws 1 from 4 elements
                playerChoice[i] = generateInteger(1, 4);

                // add correct element to our list
                elementsInRound[playerChoice[i]-1]++;

                System.out.println("Choice of computer [" + (i+1) + "]: ");
                showChoice(playerChoice[i]);
            }

            System.out.println();
            System.out.println("Elements in the round: Fire: " + elementsInRound[0] + " Water: " + elementsInRound[1] +
                    " Rock: " + elementsInRound[2] + " Tornado: " + elementsInRound[3] + "\n");

            // in each round we have 25% chance to exclude one of the elements from the round
            int elementToExclude = 0;
            int chanceForRandomEvent = generateInteger(1, 4);
            if (chanceForRandomEvent == 1) {
                System.out.println("Random event!");
                elementToExclude = generateInteger(1, 4);
                switch (elementToExclude) {
                    case 1:
                        System.out.println("Fire! Fire will not be taken into account in the scoring.");
                        break;
                    case 2:
                        System.out.println("Flood! Water will not be taken into account in the scoring.");
                        break;
                    case 3:
                        System.out.println("Earthquake! Rock will not be taken into account in the scoring.");
                        break;
                    case 4:
                        System.out.println("Storm! Tornado will not be taken into account in the scoring.");
                        break;
                    default:
                        break;
                }
                System.out.println();
            }

            // game's logic
            for (int i = 0; i < allPlayers; i++) {
                switch (playerChoice[i]) {
                    // if the fire was chosen
                    case 1:
                        if (elementToExclude != 1) {
                            if (elementToExclude != 4) {
                                points[i] += 2;
                            }

                            if (elementToExclude != 2) {
                                points[i] -= 1;
                            }
                        }
                        break;

                    // if the water was chosen
                    case 2:
                        if (elementToExclude != 2) {
                            if (elementToExclude != 1) {
                                points[i] += 2;
                            }

                            if (elementToExclude != 3) {
                                points[i] -= 1;
                            }
                        }
                        break;

                    // if the rock was chosen
                    case 3:
                        if (elementToExclude != 3) {
                            if (elementToExclude != 2) {
                                points[i] += 2;
                            }

                            if (elementToExclude != 4) {
                                points[i] -= 1;
                            }
                        }
                        break;

                    // if the tornado was chosen
                    case 4:
                        if (elementToExclude != 4) {
                            if (elementToExclude != 3) {
                                points[i] += 2;
                            }

                            if (elementToExclude != 1) {
                                points[i] -= 1;
                            }
                        }
                        break;

                    default:
                        break;
                }
            }

            showResults(points);
            System.out.println("-------------------------------------------------------");

            round++;
        }

        System.out.println("Game Over!");
        showResults(points);
    }

    private static int generateInteger(int start, int stop) {
        Random generator = new Random();
        return generator.nextInt(stop - start + 1) + start;
    }

    private static void showChoice(int choice) {
        switch (choice) {
            case 1:
                System.out.println(Element.FIRE);
                break;
            case 2:
                System.out.println(Element.WATER);
                break;
            case 3:
                System.out.println(Element.ROCK);
                break;
            case 4:
                System.out.println(Element.TORNADO);
                break;
            default:
                break;
        }
    }

    private static void showResults(int[] points) {
        if (points != null) {
            System.out.println("Points after a round:");
            for (int i = 0; i < points.length; i++) {
                System.out.println("Player " + (i + 1) + ": " + points[i]);
            }
            System.out.println("\n");
        }
    }

    private static void resetArray(int[] array) {
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                array[i] = 0;
            }
        }
    }
}
