
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
    
    
    

    
    //dynaminc traits
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
}
