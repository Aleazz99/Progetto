import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Logic 
{
	private static final String CORNICE = "-----------------------";
	private static final String AGGIUNGI_RETE = "Aggiungi rete\n";
	private static final String VISUALIZZA_RETE = "Visualizza rete\n";
	private static final String ESCI = "Esci\n";
	private static final String[] VOCI_MENU1 = {AGGIUNGI_RETE, VISUALIZZA_RETE, ESCI};
	private static final String SCEGLI = "Fai la tua scelta";
	
	private static final String AGGIUNGI_POSTO = "Aggiungi posto\n";
	private static final String AGGIUNGI_TRANSIZIONE = "Aggiungi transizione\n";
	private static final String AGGIUNGI_ARCO = "Aggiungi arco\n";
	private static final String[] VOCI_MENU2 = {AGGIUNGI_POSTO, AGGIUNGI_TRANSIZIONE, AGGIUNGI_ARCO, ESCI};
	
	private static final String SCEGLI_DIREZIONE = "Scegli la direzione dell'arco";
	private static final String PXT = "Posto -> Transizione";
	private static final String TXP = "Transizione -> Posto";
	private static final String[] VOCI_DIREZIONE = {PXT, TXP};

	private static final String SCEGLI_NOME = "Scegli un nome";
	private static final String MESS_GIA_ESISTENTE = "Attenzione! elemento già presente\n";
	private static final String INSERISCI_POSTO = "Inserisci il nome del posto";	
	private static final String INSERISCI_TRANSIZIONE = "Inserisci il nome della transizione";	
	private static final String INSERISCI_ESISTENTE = "Attenzione! inserire un elemento esistente\n";

	private static final String RICHIESTA_SALVATAGGIO= "Vuoi salvare questa rete? (si/no)->";
	private static final String STATO_SALVATAGGIO= "\nSTATO RETE E SALVATAGGIO:";

	private static final String REQUISITI_NON_SODDISFATTI = "La rete verrà rimossa in quanto non soddisfa i requisiti necessari";
	private static final String ERRORE_GENERICO_RETE = "ERRORE nella rete passata";
	
	private static final String MESS_VISUALIZZA_RETE = "Inserisci il nome della rete da visualizzare";
	private static final String RETE_NON_TROVATA = "rete non trovata";
	
	private static final String MESS_ALMENO_UNO = "Attenzione! inserire almeno una transizione e un posto";
	private static final String CONNESSIONE_OK = "La rete %s è correttamente connessa";
	private static final String CONNESSIONE_NOK = "Non tutti i posti/transizioni della rete %s sono connessi\n";
	
	private static final String MESS_RIPETIZIONE = "Attenzione! la coppia è già presente";
	private static final String MESS_CHIUSURA = "Chiusura programma in corso...";
	
	private static final String fileName = "\\Users\\bolgi\\git\\repository\\Versione1\\file.cvs";
	
	//C:\\Users\\azzin\\git\\repository\\Versione1\\file.cvs
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
			 System.out.println(CORNICE);
			 for(int i = 0; i < VOCI_MENU1.length; i++) {
				 System.out.println( (i+1) + "\t" + VOCI_MENU1[i]);
			 }
			 System.out.println(SCEGLI);
			 System.out.println(CORNICE);
			 
			 scelta = in.nextInt();
			 switch(scelta) {
			 case 1:
				 AggiungiRete();
				 break;
			 case 2:
				 VisualizzaRete();
				 break;
			 case 3:
				 System.out.println(MESS_CHIUSURA);
				 break;
		 }
		 }while(scelta != 3);
	}
	
	//secondo menù per l'aggiunta della rete
	private static void AggiungiRete() throws IOException 
	{
		int scelta2 = 0;
		boolean exit;
		String nomeRete = null;
		
		do {
			exit = true;
			System.out.println(SCEGLI_NOME);
			nomeRete = in.next(); 
			
			for(Net n: nets) {
	    		if(n.getName().equals(nomeRete)) {
	    			exit = false;
	    			System.out.println(MESS_GIA_ESISTENTE);
	    			break;
	    		}
	    	}
			
		}while(exit!=true);
		
		Net n = new Net(nomeRete);
		nets.add(n);
		
		do {
			System.out.println(CORNICE);
			 for(int i = 0; i < VOCI_MENU2.length; i++) {
				 System.out.println( (i+1) + "\t" + VOCI_MENU2[i]);
			 }
			 System.out.println(SCEGLI);
			 System.out.println(CORNICE);
			
			scelta2 = in.nextInt();
			
			switch(scelta2) {
			case 1:
				System.out.println(SCEGLI_NOME);
				String nomePosto = in.next();
				
			    if(n.cercaPostoByName(nomePosto)) {
			    	System.out.println(MESS_GIA_ESISTENTE); 
			    }
			    else {
			    	Place p = n.place(nomePosto); 
			    }
			    
				break;
				
			case 2:
				System.out.println(SCEGLI_NOME);
				String nomeTransizione = in.next();
				
				if(n.cercaTransizioneByName(nomeTransizione)) {
					System.out.println(MESS_GIA_ESISTENTE); 
				}
				else {
					Transition t = n.transition(nomeTransizione);
				}
				
				break;
				
			case 3:
				Place posto = null;
				Transition transizione = null;
				Arc a = null;
				int direzione = 0;
				
				System.out.println(SCEGLI_NOME);
				String nomeA = in.next();
				
				if(n.cercaArcoByName(nomeA)) {
					System.out.println(MESS_GIA_ESISTENTE);
					break;
				}
				
				do {
					System.out.println(INSERISCI_POSTO);
					String nomeP = in.next();
					posto = n.creaPostoByName(nomeP);
					
					if(posto == null) 
						System.out.println(INSERISCI_ESISTENTE);
				}while(posto == null);
				
				do {
					System.out.println(INSERISCI_TRANSIZIONE);
					String nomeT = in.next();
					transizione = n.creaTransizioneByName(nomeT);
					
					if(transizione == null)
						System.out.println(INSERISCI_ESISTENTE);
				}while(transizione == null);
				
				do {
					System.out.println(SCEGLI_DIREZIONE);
					for(int i = 0; i < VOCI_DIREZIONE.length; i++) {
						 System.out.println( (i+1) + "\t" + VOCI_DIREZIONE[i]);
					 }
					direzione = in.nextInt();
				}while((direzione != 1) && (direzione != 2));
				
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
				
				scelta2 = ControllaAlmenoUno(n);
				break;		
			}
			
		}while(scelta2 != 4);
		
		SalvataggioRete(n);
	}

	private static void SalvataggioRete(Net daSalvare) throws IOException {
		
		System.out.println(RICHIESTA_SALVATAGGIO);
		String risposta = in.next();
		
		if(risposta.equals("si")) {
			System.out.println(STATO_SALVATAGGIO);
			
			//controlli sulla rete prima di salvarla su file
			if(ControllaRete(daSalvare) && ControllaUnicita(daSalvare))
				Scrittura(daSalvare);
			else{
				System.out.println(REQUISITI_NON_SODDISFATTI);
				nets.remove(daSalvare);
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
			System.out.println(ERRORE_GENERICO_RETE);
		}
		
	}
	
	//trova la rete da visualizzare e la stampa a video
	private static void VisualizzaRete() 
	{	
		System.out.println(MESS_VISUALIZZA_RETE);
		String rete = in.next();
		Net net = CercaReteByName(rete);
		if(net == null)
			
			System.out.println(RETE_NON_TROVATA);
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
	
	//controllo: ogni rete deve contenere almeno un posto e una transizione
	private static int ControllaAlmenoUno(Net n) {
		int s = 0;
		
		if((n.transitions).isEmpty() || (n.places).isEmpty())
			System.out.println(MESS_ALMENO_UNO);
		else s = 4;
		
		return s;
	}
	
	//controllo se la rete è correttamente connessa
	private static boolean ControllaRete(Net n) 
	{
		boolean stato=false;
				
			if(n != null) {
				if(n.controllaConnessione()) {
					System.out.printf(CONNESSIONE_OK, n.getName());
					stato = true;
				}else {
					System.out.printf(CONNESSIONE_NOK, n.getName());
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
			
			if(!(ControllaPXT(daControllare)) && !(ControllaTXP(daControllare))) 
				System.out.print(" ma la rete è già esistente\n");
			else
				stato = true;	
		}
		
		return stato;
	}
	
	//controllo che non esistano già coppie posto-transizione uguali in altre reti12
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
		return transizioni1.equals(transizioni2);
		 
	}
	
	private static boolean ControllaValoriTXP(HashMap<Transition,Place> r1, HashMap<Transition,Place> r2) {
		List<Place> posti1 = new ArrayList<Place>(r1.values());
		List<Place> posti2 = new ArrayList<Place>(r2.values());
		return posti1.equals(posti2);
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
