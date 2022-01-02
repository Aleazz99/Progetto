import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Net extends NetObject{
	private static final String acapo = "\n";
	List<Place> places = new ArrayList<Place>();
	List<Transition> transitions = new ArrayList<Transition>();
	List <Arc> arcs = new ArrayList<Arc>();
	HashMap<Place, Transition> pxt = new HashMap();
	HashMap<Transition, Place> txp = new HashMap();
	
	public Net(String name) {
		super(name);
	}
	
    public Transition transition(String name) {
        Transition t = new Transition(name);
        transitions.add(t);
        return t;
    }
    
    public Place place(String name) {
    	
    	for(Place posto : places) {
    		if(name.equals(posto.getName()))
    			return posto;
    	}
    	
		Place p = new Place(name);
		places.add(p);
	
        return p;
    }
    
    public Arc arc(String name, Place p, Transition t) {
        Arc arc = new Arc(name, p, t);
        arcs.add(arc);
        
        pxt.put(p, t);
        return arc;
    }
    
    public Arc arc(String name, Transition t, Place p) {
        Arc arc = new Arc(name, t, p);
        arcs.add(arc);
        
        txp.put(t, p);
        return arc;
    }
    
    public Place cercaPostoByName(String name) {
    	for(Place p: places) {
    		if((p.getName()).equals(name))
    			return p;
    	}
    	return null;
    }
    
    public Transition cercaTransizioneByName(String name) {
    	for(Transition t: transitions) {
    		if((t.getName()).equals(name))
    			return t;
    	}
    	return null;
    }
    
    public boolean controllaConnessione() {
    	//controllo:tutti i posti e tutte le transizioni devono essere connesse
    	
    	int i;
    	int j;
    	for(Transition t: transitions) {
    		i = 0;
    		for(Place p: places) {
    			if(pxt.get(p) == t)
    				i++;
    		}
    		
    		if(i == 0) return false;
    	}
    	
    	for(Place p: places) {
    		j = 0;
    		for(Transition t: transitions) {
    			if(txp.get(t) == p)
    				j++;
    		}
    		
    		if(j == 0) return false;
    	}
    	
    	return true;
    }
    
    public boolean controllaRipetizioni(Place p, Transition t) {
    	if(pxt.get(p) == t)
    		return true;
    	return false;
    }
    
    public boolean controllaRipetizioni(Transition t, Place p) {
    	if(txp.get(t) == p)
    		return true;
    	return false;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Petrinet ");
        sb.append(super.toString()).append(acapo);
        sb.append("---Transitions---").append(acapo);
        for (Transition t : transitions) {
            sb.append(t).append(acapo);
        }
        sb.append("---Places---").append(acapo);
        for (Place p : places) {
            sb.append(p).append(acapo);
        }
        
//        sb.append("---Arcs---").append(acapo);
//        for(Arc a : arcs) {
//        	sb.append(a).append(acapo);
//        }
        
        return sb.toString();
    }
    
    public HashMap<Transition, Place> getTXP(){
    	return txp;
    }
    
    public HashMap<Place, Transition> getPXT(){
    	return pxt;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public List<Arc> getArcs() {
        return arcs;
    }

}
