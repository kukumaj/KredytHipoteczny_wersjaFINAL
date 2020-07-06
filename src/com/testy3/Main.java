package com.testy3;

import java.text.NumberFormat;
import java.util.Scanner;

public class Main {
    final static byte MIES_W_ROKU = 12;
    final static byte PROCENT = 100;

    public static void main(String[] args) {
        int kw_kredytu = (int) czytaj_Dane("Kwota_Kredytu: ", 1000, 1_000_000);
        float opr_rocz = (float) czytaj_Dane("Oprocentowanie_Roczne: ", 1, 30);
        byte lata = (byte) czytaj_Dane("Okres(w latach): ", 1, 30);

        drukuj_hipoteke(kw_kredytu, opr_rocz, lata);
        drukuj_harmonogra_platnosci(kw_kredytu, opr_rocz, lata);
    }

    private static void drukuj_hipoteke(int kw_kredytu, float opr_rocz, byte lata) {
        double hipoteka = wylicz_hipoteke(kw_kredytu, opr_rocz, lata);
        String hipotekaformatowanie = NumberFormat.getCurrencyInstance().format(hipoteka);
        System.out.println();
        System.out.println("HIPOTEKA");
        System.out.println("--------");
        System.out.println("Kredyt jeszcze do splacenia: " + hipotekaformatowanie);
    }

    private static void drukuj_harmonogra_platnosci(int kw_kredytu, float opr_rocz, byte lata) {
        System.out.println();
        System.out.println("HARMONOGRAM PLATNOSCI");
        System.out.println("----------------");
        for (short miesiac = 1; miesiac <= lata * MIES_W_ROKU; miesiac++) {
            double balans = wylicz_Balans(kw_kredytu, opr_rocz, lata, miesiac);
            System.out.println(NumberFormat.getCurrencyInstance().format(balans));
        }
    }

    public static double czytaj_Dane(String podpowiedz_tekst, double min, double max) {
        Scanner wczyt_dane = new Scanner(System.in);
        double wartosc;
        while (true) {
            System.out.print(podpowiedz_tekst);
            wartosc = wczyt_dane.nextFloat();
            if (wartosc >= min && wartosc <= max)
                break;
            System.out.println("Wprowadz wartosc od " + min + " do " + max);
        }
        return wartosc;
    }

    public static double wylicz_Balans(
            int kw_kredytu,
            float opr_rocz,
            byte lata,
            short ilosc_splat_dokonanych
    ) {
        float opr_mies = opr_rocz / PROCENT / MIES_W_ROKU;
        float ilosc_splat = lata * MIES_W_ROKU;

        double balans = kw_kredytu
                * (Math.pow(1 + opr_mies, ilosc_splat) - Math.pow(1 + opr_mies, ilosc_splat_dokonanych))
                / (Math.pow(1 + opr_mies, ilosc_splat) - 1);

        return balans;
    }

    public static double wylicz_hipoteke(
            int kw_kredytu,
            float opr_rocz,
            byte lata) {

        float opr_mies = opr_rocz / PROCENT / MIES_W_ROKU;
        float ilosc_splat = lata * MIES_W_ROKU;

        double hipoteka = kw_kredytu
                * (opr_mies * Math.pow(1 + opr_mies, ilosc_splat))
                / (Math.pow(1 + opr_mies, ilosc_splat) - 1);

        return hipoteka;
    }
}