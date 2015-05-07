
import java.util.List;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pridc7921
 */
public class Vessel {
    
    //Text-y stuff
    String name;
    String ORFaction;
    
    
    //static traits
    int faction;
    int hulllimit;
    int crewlimit;
    int marinelimit;
    int powerlimit;
    int powergen;
    int fieldlimit;
    int beams;
    int rails;    
    int subweapons;
    int cost;
    
    
    

    
    //dynaminc traits
    int crewtasks = 0;
    int currentformation;
    int requisition = -1;
    int hull;
    int crew;
    int marines; 
    int power;
    int fields; 
    int speed;    
    boolean isplayer;
    boolean isdead;
    
    Vessel DeepCopy(){
        Vessel vessel = new Vessel();
        vessel.ORFaction = ORFaction;
        vessel.beams = beams;
        vessel.crew = crew;
        vessel.crewlimit = crewlimit;
        vessel.currentformation = currentformation;
        vessel.faction = faction;
        vessel.fieldlimit = fieldlimit;
        vessel.fields = fields;
        vessel.hull = hull;
        vessel.hulllimit = hulllimit;
        vessel.isdead = isdead;
        vessel.isplayer = isplayer;
        vessel.marinelimit = marinelimit;
        vessel.marines = marines;
        vessel.name = name;
        vessel.power = power;
        vessel.powergen = powergen;
        vessel.powerlimit = powerlimit;
        vessel.rails = rails;
        vessel.requisition = requisition;
        vessel.speed = speed;
        vessel.subweapons = subweapons;
        vessel.cost = cost;
        
                
        
        
        return vessel;
    }
}
