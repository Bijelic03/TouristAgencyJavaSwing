package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.TuristickaAgencija;

public class UpravljanjeTuristickimAgencijama {
    private static ArrayList<TuristickaAgencija> agencije;

    public UpravljanjeTuristickimAgencijama() {
        agencije = new ArrayList<TuristickaAgencija>();
    }

    public void ucitajAgencije() {
        try {
            File agencijeFile = new File("podaci/agencije.txt");
            try (BufferedReader reader = new BufferedReader(new FileReader(agencijeFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] lineSplit = line.split("\\|");
                    String id = lineSplit[0];
                    long idLong = Long.parseLong(id);
                    String ime = lineSplit[1];
                    String adresa = lineSplit[2];
                    String brojTelefona = lineSplit[3];

                    TuristickaAgencija agencija = new TuristickaAgencija(idLong, ime, adresa, brojTelefona);
                    agencije.add(agencija);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Greska prilikom ucitavanja datoteke: " + e.getMessage());
        }
    }

    public static ArrayList<TuristickaAgencija> getAgencije() {
        return agencije;
    }

    public static String[][] getPodaciOAgencijama() {
        if (agencije != null) {
            String[][] podaci = new String[agencije.size()][4];
            for (int i = 0; i < agencije.size(); i++) {
                TuristickaAgencija agencija = agencije.get(i);
                podaci[i][0] = agencija.getId() + "";
                podaci[i][1] = agencija.getIme();
                podaci[i][2] = agencija.getAdresa();
                podaci[i][3] = agencija.getBrojTelefona();
            }
            return podaci;
        } else {
            return null;
        }
}}