import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Logic 
{
	private static final String MESS_RIPETIZIONE = "ATTENZIONE: la coppia è già presente";
	private static final String fileName = "C:\\Users\\bolgi\\git\\repository\\Versione1\\file.cvs";
	//C:\\Users\\azzin\\OneDrive\\Desktop\\reti.cvs
	//C:\\Users\\bolgi\\git\\repository\\Versione1\\file.cvs
	
	//token Ale: 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
	//token Bolgiani: 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
	static Scanner in = new Scanner(System.in);
	static List<Net> nets = new ArrayList<Net>();
	
	//metodo iniziale del primo menù
	public void start() throws IOException 
	{
		CaricaReti();
		int scelta = 0;
		 do { 
			 System.out.println("\n-------MENU-------\n");
			 System.out.println("1)Aggiungi rete\n");
			 System.out.println("2)Visualizza rete\n");
			 System.out.println("3)Exit\n");
			 
			 System.out.println("fai la tua scelta");
			 
			 scelta = in.nextInt();
			 switch(scelta) {
			 case 1:
				 AggiungiRete();
				 break;
			 case 2:
				 VisualizzaRete();
				 break;
			 case 3:
				 System.out.println("Chiusura programma in corso...");
				 break;
		 }
		 }while(scelta != 3);
	}
	
	//secondo menù per l'aggiunta della rete
	private static void AggiungiRete() throws IOException 
	{
		boolean nomeok = true;
		String nomeRete = null;
		
		do {
			System.out.println("Scegli il nome della rete: ");
			nomeRete = in.next(); 
			
			for(Net n: nets) {
	    		if(n.getName().equals(nomeRete)) {
	    			nomeok = false;
	    			System.out.println("Nome già presente");
	    		}
	    	}
			
		}while(!nomeok);
		
		Net n = new Net(nomeRete);
		nets.add(n);
		
		int scelta2 = 0;
		do {
			System.out.println("------MENU---------\n");
			System.out.println("1)Aggiungi posto\n");
			System.out.println("2)Aggiungi transizione\n");
			System.out.println("3)Aggiungi arco\n");
			System.out.println("4)Esci\n");
			
			scelta2 = in.nextInt();
			
			switch(scelta2) {
			case 1:
				System.out.println("inserisci il nome del posto");
				String nomePosto = in.next();
				Place p = n.place(nomePosto); 
				break;
				
			case 2:
				System.out.println("inserisci il nome della transizione");
				String nomeTransizione = in.next();
				Transition t = n.transition(nomeTransizione);
				break;
				
			case 3:
				System.out.println("Inserisci il nome dell'arco");
				String nomeA = in.next();
				System.out.println("Inserisci il nome del posto");
				String nomeP = in.next();
				System.out.println("Inserisci il nome della transizione");
				String nomeT = in.next();
				
				Transition transizione = n.cercaTransizioneByName(nomeT);
				Place posto = n.cercaPostoByName(nomeP);
			
				System.out.println("Scegli la direzione:");
				System.out.println("1)posto -> transizione");
				System.out.println("2)transizione -> posto:");
				int direzione = in.nextInt();
				Arc a;
				
				//direzione=1 -> posto-transione
				//direzione=2 -> transizione-posto
				
				if(direzione == 1) {
					//controllo se è una ripetizione
					if(!n.controllaRipetizioni(posto, transizione)) {
						a = n.arc(nomeA, posto, transizione );
					}
					else
						System.out.println(MESS_RIPETIZIONE);
					
				}
				else if(direzione == 2) {
					//controllo se è una ripetizione
					if(!n.controllaRipetizioni(transizione, posto)) {
						a = n.arc(nomeA, transizione, posto);
					}	
					else
						System.out.println(MESS_RIPETIZIONE);
				}
				break;
				
			case 4: 
				//controllo: ogni rete deve contenere almeno un posto e una transizione
				if((n.transitions).isEmpty() || (n.places).isEmpty()) {
					System.out.println("Attenzione! inserire almeno una transizione e un posto");
					scelta2 = 0;
				}
				break;		
			}
		}while(scelta2 != 4);
		
		System.out.println("Vuoi salvare questa rete? (si/no)->");
		String risposta = in.next();
		if(risposta.equals("si")) {
			System.out.println("\nSTATO RETE E SALVATAGGIO:");
			//controlli sulla rete prima di salvarla su file
			if(ControllaRete(n) && ControllaUnicita(n))
				Scrittura(n);
			else{
				System.out.println("La rete verrà rimossa in quanto non soddisfa i requisiti necessari");
				nets.remove(n);
			}
		}
		System.out.println();
	}

	//scrittura della nuova rete su file
	private static void Scrittura(Net n) throws IOException 
	{
		if(n != null) {
			FileWriter fw = null;
			BufferedWriter bw = null;
			PrintWriter pw = null;
			try {
				fw = new FileWriter(fileName, true);
				bw = new BufferedWriter(fw);
				pw = new PrintWriter(bw);
				
				pw.printf(n.getName());
				pw.print(",");
				int count = 0;
				for(Arc a: n.arcs) {
					pw.printf(a.getName());
					pw.print(":");
					pw.printf(a.place.getName());
					pw.print(":");
					pw.printf(a.transition.getName());
					pw.print(":");
					pw.printf(a.direction.toString());
					count++;
					if(count != n.arcs.size())
						pw.print(",");
				}
				pw.println();
			
				}finally {
					try {
						pw.close();
						fw.close();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
		     
				}	
		}
		else {
			System.out.println("ERRORE nella rete passata");
		}
		
	}
	
	//trova la rete da visualizzare e la stampa a video
	private static void VisualizzaRete() 
	{	
		System.out.println("Inserisci il nome della rete da visualizzare");
		String rete = in.next();
		Net net = CercaReteByName(rete);
		if(net == null)
			System.out.println("rete non trovata");
		else {
			List<Arc> arcsList = net.getArcs();
			
			for(Arc ar: arcsList) {	
				System.out.printf("Nome rete: %s", net.getName());
				System.out.printf("----------%s----------", ar.getName());
				System.out.printf("\nArco: %s, ", ar.getName());
				System.out.printf("Posto: %s, ", ar.place.getName());
				System.out.printf("Transizione: %s\n", ar.transition.getName());
			}
		}
	}
	
	//questo metodo carica direttamente tutte le reti scritte nel file della lista delle reti
	private static void CaricaReti () throws IOException
	{			         
		BufferedReader reader = null;
		String line = "";
		try {
			reader = new BufferedReader(new FileReader(fileName));
		
			System.out.println(fileName.length());
				while((line = reader.readLine()) != null ) {
					String[] row = line.split(",");
					
					Net nuovaRete = null;
					Place p = null;
					Transition t = null;
					Arc a = null;
				    nuovaRete = new Net(row[0]);
				    
					for(String o : row) {
						if(!o.equals(row[0])){
							String[] obje = o.split(":");
							p = nuovaRete.place(obje[1]);	
							t = nuovaRete.transition(obje[2]);
							
							if(obje[3].equals("PLACE_TO_TRANSITION"))
								a = nuovaRete.arc(obje[0], p, t);
							else
								a = nuovaRete.arc(obje[0], t, p);
						}
					}
					//rete aggiunta alla lista di reti
					nets.add(nuovaRete);
				}
			
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			reader.close();
		}
	}
	
	//controllo se la rete è correttamente connessa
	private static boolean ControllaRete(Net n) 
	{
		boolean stato=false;
				
			if(n != null) {
				if(n.controllaConnessione()) {
					System.out.printf("La rete %s è correttamente connessa", n.getName());
					stato = true;
				}else {
					System.out.printf("Non tutti i posti/transizioni della rete %s sono connessi\n", n.getName());
					System.out.println();
					stato = false;
				}
			}	
			
		return stato;
	}
		
	//controllo che la rete sia unica
	private static boolean ControllaUnicita(Net daControllare) {
		boolean stato = false;
		
		if(daControllare != null) {
			if(nets.size() == 1)
				stato = true;
			
			if(!ControllaPXT(daControllare) && !ControllaTXP(daControllare)) 
				System.out.print(" ma la rete è già esistente\n");
			else
				stato = true;	
		}
		
		return stato;
	}
	
	//controllo che non esistano già coppie posto-transizione uguali in altre reti
	private static boolean ControllaPXT(Net net1) {
		boolean stato = true;
		HashMap<Place,Transition> rete1PXT = net1.getPXT();
		
		for(Net net2 : nets) {
			HashMap<Place, Transition> rete2PXT = net2.getPXT();
			
			if(rete1PXT.keySet().equals(rete2PXT.keySet())) {
				if(!(ControllaValoriPXT(rete1PXT, rete2PXT))) {
					stato = false;
					break;
				}
			}
		}
		
		return stato;
	}
	
	//controllo che non esista già coppie transizione-posto uguali in altre reti
	private static boolean ControllaTXP(Net net1) {
		boolean stato = true;
		HashMap<Transition,Place> rete1TXP = net1.getTXP();
		
		for(Net net2 : nets) {
			HashMap<Transition, Place> rete2TXP = net2.getTXP();
			
			if(rete1TXP.keySet().equals(rete2TXP.keySet())) 
				if(!(ControllaValoriTXP(rete1TXP, rete2TXP))) {
					stato = false;
					break;
				}
		}
		
		return stato;
	}
	
	private static boolean ControllaValoriPXT(HashMap<Place,Transition> r1, HashMap<Place,Transition> r2) {
		List<Transition> transizioni1 = new ArrayList<Transition>(r1.values());
		List<Transition> transizioni2 = new ArrayList<Transition>(r2.values());
		return !(transizioni1.equals(transizioni2));
	}
	
	private static boolean ControllaValoriTXP(HashMap<Transition,Place> r1, HashMap<Transition,Place> r2) {
		List<Place> posti1 = new ArrayList<Place>(r1.values());
		List<Place> posti2 = new ArrayList<Place>(r2.values());
		return !(posti1.equals(posti2));
	}
	
	private static Net CercaReteByName(String name) 
	{
    	for(Net n: nets) {
    		if(n.getName().equals(name))
    			return n;
    	}
    	return null;
    }
	
}
