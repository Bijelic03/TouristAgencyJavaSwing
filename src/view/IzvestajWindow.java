package view;

import java.awt.BorderLayout;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import model.Aranzman;
import model.Rezervacija;
import model.StatusRezervacije;
import service.CitanjeAranzmana;
import service.CitanjeRezervacija;
import service.UpravljanjeAranzmanima;
import service.UpravljanjeKorisnicima;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

public class IzvestajWindow extends JFrame {
	private double ukupnaZarada;
	private String[][] sortiraniAranzmaniTabela;
	private String[] aranzmaniColumnNames = { "Id", "Broj rezervisanih mesta" };

	public IzvestajWindow(Date datumOd, Date datumDo) {
		setTitle("Izveštaj");
		setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		obradaPodatakaZaIzvestaj(datumOd, datumDo);

		JLabel zaradaLabel = new JLabel("Ukupna zarada: " + ukupnaZarada);
		add(zaradaLabel, BorderLayout.NORTH);

		TableGenerator aranzmaniTable = new TableGenerator(sortiraniAranzmaniTabela, aranzmaniColumnNames);
		add(aranzmaniTable, BorderLayout.CENTER);
	}

	public void obradaPodatakaZaIzvestaj(Date datumOd, Date datumDo) {
		Map<Aranzman, Integer> mapaAranzmana = new HashMap<>();
		for (Aranzman aranzman : CitanjeAranzmana.ucitajAranzmane()) {
			if (aranzman.getTuristickiAgent() == UpravljanjeKorisnicima.prijavljenaOsoba) {
				mapaAranzmana.put(aranzman, 0);
			}
		}

		for (Rezervacija rezervacija : CitanjeRezervacija.ucitajRezervacije()) {
			Date convertedDatumRez = Date
					.from(rezervacija.getDatumkreiranja().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

			int datumPre = convertedDatumRez.compareTo(datumOd);
			int datumPosle = convertedDatumRez.compareTo(datumDo);
			if (datumPre > 0 && datumPosle < 0) {
				if (rezervacija.getStatusRezervacije() == StatusRezervacije.Zavrsena && rezervacija.getAranzman()
						.getTuristickiAgent().getId() == UpravljanjeKorisnicima.prijavljenaOsoba.getId()) {
					ukupnaZarada += rezervacija.getCena();
					mapaAranzmana.put(rezervacija.getAranzman(),
							mapaAranzmana.getOrDefault(rezervacija.getAranzman(), 0) + rezervacija.getBrojPutnika());
				}
			}
		}

		// Stvaranje liste aranžmana sortiranih prema broju rezervisanih putnika
		List<Map.Entry<Aranzman, Integer>> sortiraniAranzmani = new ArrayList<>(mapaAranzmana.entrySet());
		Collections.sort(sortiraniAranzmani, new Comparator<Map.Entry<Aranzman, Integer>>() {
			@Override
			public int compare(Map.Entry<Aranzman, Integer> entry1, Map.Entry<Aranzman, Integer> entry2) {
				// Usporedba po broju rezervisanih putnika - od najvećeg do najmanjeg
				return entry2.getValue().compareTo(entry1.getValue());
			}
		});

		// Ispis sortirane liste aranžmana
		sortiraniAranzmaniTabela = pretvoriUStringArray(sortiraniAranzmani);

	}

	public String[][] pretvoriUStringArray(List<Map.Entry<Aranzman, Integer>> sortiraniAranzmani) {
		int size = sortiraniAranzmani.size();
		String[][] rezultat = new String[size][2]; // Pretpostavljamo da želimo imati 2 kolone (Id aranžmana i broj
													// rezervisanih putnika)

		int index = 0;
		for (Map.Entry<Aranzman, Integer> entry : sortiraniAranzmani) {
			Aranzman aranzman = entry.getKey();
			int brojPutnika = entry.getValue();

			rezultat[index][0] = String.valueOf(aranzman.getId());
			rezultat[index][1] = String.valueOf(brojPutnika);

			index++;
		}

		return rezultat;
	}

}
