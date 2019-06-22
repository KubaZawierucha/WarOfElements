import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

// enumeracja przechowuje wszystkie żywioły, które biorą udział w rozgrywce
enum Zywiol {
    // ogien < woda < ziemia < wiatr < ogien...
    OGIEN, WODA, ZIEMIA, WIATR;
}

public class JavaProject {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // licznik rund
        int runda = 1;
        // tutaj przechowujemy wybór użytkownika
        int liczbaRund;
        int wszyscyGracze;
        int gracze;

        // pobieramy od użytkownika ustawienia rozgrywki
        System.out.println("Ustawienia rozgrywki:\n" +
                "Liczba rund: ");
        liczbaRund = input.nextInt();
        System.out.println("Calkowita ilosc graczy: ");
        // gracze + gracze komputerowi
        wszyscyGracze = input.nextInt();
        System.out.println("Ilosc ludzi: ");
        gracze = input.nextInt();

        System.out.println("\nUstawienia:\n" +
                "Liczba rund: " + liczbaRund + "\n" +
                "Calkowita ilosc graczy: " + wszyscyGracze + "\n" +
                "Ilosc ludzi: " + gracze + "\n");

        // inicializujemy talice przechowujące:
        // jaki żywioł wybrał 1 gracz - wyboryGraczy[0], 2 gracz - wyboryGraczy[1], ..., n gracz - wyboryGraczy[n-1]
        int[] wyboryGraczy = new int[wszyscyGracze];
        // ile punktów ma gracz 1, 2, ...; działa tak samo jak powyżej
        int[] punkty = new int[wszyscyGracze];
        // jakie żywioły są obecnie "na planszy"
        int[] zywiolyWRundzie = new int[4];

        // inicjalizuje tablicę z punktami, aby możliwa była inkrementacja - punkty[0]++...
        resetujTablice(punkty);

        // gra kończy się po wybranej ilości rund
        while (runda <= liczbaRund) {
            // w każdej rundzie zaczynamy z 0 wartością dla ilości każdego żywiołu "na planszy"
            resetujTablice(zywiolyWRundzie);

            System.out.println("Runda " + runda + "\n");

            // dla każdego gracza
            for (int i = 0; i < gracze; i++) {
                System.out.println("[Gracz " + (i+1) + "]\n" +
                        "Wybierz swoj zywiol:\n" +
                        "[1] Ogien\n" +
                        "[2] Woda\n" +
                        "[3] Ziemia\n" +
                        "[4] Wiatr");

                // pobieramy od użytkownika jego wybór (żywioł)
                // sprawdzamy czy jest on równy 1, 2, 3 lub 4 i dopóki jest zły pytamy o powtórzenie
                while ((wyboryGraczy[i] = input.nextInt()) < 1 || wyboryGraczy[i] > 4) {
                    System.out.println("Mozesz wybrac tylko: 1, 2, 3 lub 4.");
                }

                // zapisujemy jakie żywioły są obecnie na planszy
                // -1 ponieważ indeksy tablic zaczynają się od 0
                zywiolyWRundzie[wyboryGraczy[i] - 1]++;

                System.out.print("[Gracz " + (i+1) + "]\nWybrales: ");
                wyswietlWybor(wyboryGraczy[i]);
                System.out.println();
            }

            // dla każdego z pozostałych graczy (komputer)
            for (int i = gracze; i < wszyscyGracze; i++) {
                // każdy komputer losuje 1 z 4 żywiołów
                wyboryGraczy[i] = wylosujInt(1, 4);

                // zapisujemy jakie żywioły są obecnie na planszy
                // -1 ponieważ indeksy tablic zaczynają się od 0
                zywiolyWRundzie[wyboryGraczy[i]-1]++;

                System.out.println("Wybor komputera [" + (i+1) + "]: ");
                wyswietlWybor(wyboryGraczy[i]);
            }

            System.out.println();
            System.out.println("Zywioly w rundzie: Ogien: " + zywiolyWRundzie[0] + " Woda: " + zywiolyWRundzie[1] +
                    " Ziemia: " + zywiolyWRundzie[2] + " Wiatr: " + zywiolyWRundzie[3] + "\n");

            // mamy 25% szans na wystapienie zdarzenia losowego
            // losujemy z puli 4 liczb, wiec mamy 25% szans na wylosowanie 1
            int ktoryZywiolWyeliminowany = 0;
            int szansaNaZdarzenieLosowe = wylosujInt(1, 4);
            if (szansaNaZdarzenieLosowe == 1) {
                System.out.println("Zdarzenie losowe!");
                // losujemy zywiol, ktory nie bedzie brany pod uwage podczas punktowania
                ktoryZywiolWyeliminowany = wylosujInt(1, 4);
                switch (ktoryZywiolWyeliminowany) {
                    case 1:
                        System.out.println("Pozar! Ogien nie bierze udzialu w punktowaniu.");
                        break;
                    case 2:
                        System.out.println("Powodz! Woda nie bierze udzialu w punktowaniu.");
                        break;
                    case 3:
                        System.out.println("Trzesienie ziemi! Ziemia nie bierze udzialu w punktowaniu.");
                        break;
                    case 4:
                        System.out.println("Tornado! Wiatr nie bierze udzialu w punktowaniu.");
                        break;
                    default:
                        break;
                }
                System.out.println();
            }

            // logika gry
            // sprawdzamy wybór każdego z graczy
            for (int i = 0; i < wszyscyGracze; i++) {
                switch (wyboryGraczy[i]) {
                    // jeśli gracz wybrał ogień
                    case 1:
                        if (ktoryZywiolWyeliminowany != 1) {
                            if (ktoryZywiolWyeliminowany != 4) {
                                // + punkty za kazda wiatr w rundzie ^ 3
                                punkty[i] += Math.pow(zywiolyWRundzie[3], 3);
                            }

                            if (ktoryZywiolWyeliminowany != 2) {
                                // - pierwiastek z ilości wód w rundzie, usuwana jest część dziesiętna
                                punkty[i] -= (int) Math.sqrt(zywiolyWRundzie[1]);
                            }
                        }
                        break;

                    // jeśli gracz wybrał wodę
                    case 2:
                        if (ktoryZywiolWyeliminowany != 2) {
                            if (ktoryZywiolWyeliminowany != 1) {
                                // + punkty za kazda ogień w rundzie ^ 3
                                punkty[i] += Math.pow(zywiolyWRundzie[0], 3);
                            }

                            if (ktoryZywiolWyeliminowany != 3) {
                                // - pierwiastek z ilości ziem w rundzie, usuwana jest część dziesiętna
                                punkty[i] -= (int) Math.sqrt(zywiolyWRundzie[2]);
                            }
                        }
                        break;

                    // jeśli gracz wybrał ziemię
                    case 3:
                        if (ktoryZywiolWyeliminowany != 3) {
                            if (ktoryZywiolWyeliminowany != 2) {
                                // + punkty za każda wodę w rundzie ^ 3
                                punkty[i] += Math.pow(zywiolyWRundzie[1], 3);
                            }

                            if (ktoryZywiolWyeliminowany != 4) {
                                // - pierwiastek z ilości wiatrów w rundzie, usuwana jest część dziesiętna
                                punkty[i] -= (int) Math.sqrt(zywiolyWRundzie[3]);
                            }
                        }
                        break;

                    // jeśli gracz wybrał wiatr
                    case 4:
                        if (ktoryZywiolWyeliminowany != 4) {
                            if (ktoryZywiolWyeliminowany != 3) {
                                // + punkty za każda ziemię w rundzie ^ 3
                                punkty[i] += Math.pow(zywiolyWRundzie[2], 3);
                            }

                            if (ktoryZywiolWyeliminowany != 1) {
                                // - pierwiastek z ilości ogni w rundzie, usuwana jest część dziesiętna
                                punkty[i] -= (int) Math.sqrt(zywiolyWRundzie[0]);
                            }
                        }
                        break;

                    default:
                        break;
                }
            }

            wyswietlWynik(punkty);
            System.out.println("-------------------------------------------------------");

            runda++;
        }

        System.out.println("Koniec gry!");
        wyswietlWynik(punkty);

        // inicjalizujemy najlepszy wynik
        int najlepszyWynik = 0;
        // sprawdzamy wynik kazdego z graczy
        for (int i = 0; i < wszyscyGracze; i++) {
            // jesli wynik aktualnie sprawdzanego gracza jest wyzszy od obecnie najwyzszego, wtedy zaktualizuj
            // najlepszy wynik
            if (punkty[i] > najlepszyWynik) {
                najlepszyWynik = punkty[i];
            }
        }

        String listaNajlepszychGraczy = "Zwyciezcy: Gracze";

        // tworzymy listę, która przechowywać będzie wszystkich graczy z najlepszym wynikiem
        LinkedList najlepszyGracz = new LinkedList();
        for (int i = 0; i < wszyscyGracze; i++) {
            // dodajemy każdego gracza, którego wynik jest równy najlepszemu graczowi
            if (punkty[i] == najlepszyWynik) {
                najlepszyGracz.add(i+1);
            }
        }

        listaNajlepszychGraczy += najlepszyGracz;
        listaNajlepszychGraczy += " z wynikiem " + najlepszyWynik + "\n";

        System.out.println("Najlepszy wynik: " + najlepszyWynik);
        System.out.println(listaNajlepszychGraczy);

        String koniec = "Koniec Gry!";
        for (int i = 0; i < koniec.length(); i++) {
            for (int j = 0; j < i; j++) {
                System.out.print("  ");
            }
            System.out.println(koniec.charAt(i));
        }
    }

    // losuje liczbę całkowitą w zakresu <start, stop>
    private static int wylosujInt(int start, int stop) {
        Random generator = new Random();
        int liczba = generator.nextInt(stop - start + 1) + start;

        return liczba;
    }

    // wyświetla jaki żywioł wybrał gracz
    private static void wyswietlWybor(int wybor) {
        switch (wybor) {
            case 1:
                System.out.println(Zywiol.OGIEN);
                break;
            case 2:
                System.out.println(Zywiol.WODA);
                break;
            case 3:
                System.out.println(Zywiol.ZIEMIA);
                break;
            case 4:
                System.out.println(Zywiol.WIATR);
                break;
            default:
                break;
        }
    }

    private static void wyswietlWynik(int[] punkty) {
        if (punkty != null) {
            System.out.println("Punkty po rundzie:");
            for (int i = 0; i < punkty.length; i++) {
                System.out.println("Gracz " + (i + 1) + ": " + punkty[i]);
            }
            System.out.println("\n");
        }
    }

    private static void resetujTablice(int[] tablica) {
        if (tablica != null) {
            for (int i = 0; i < tablica.length; i++) {
                tablica[i] = 0;
            }
        }
    }
}
