/**
 * @author pridc7921
 */
public class Vessel {
    
    //Text-y stuff
    String sName;
    String sORFaction;
    String sMarinetype;
    
    
    //static traits
    int nFaction;
    int nHulllimit;
    int nCrewlimit;
    int nMarinelimit;
    int nPowerlimit;
    int nPowergen;
    int nFieldlimit;
    int nBeams;
    int nRails;    
    int nSubweapons;
    int nCost;
    int nMaxspeed;
    int nMarineskill;
    
    //dynaminc traits
    int nCrewtasks = 0;
    int nCurrentformation;
    int nRequisition = -1;
    int nHull;
    int nCrew;
    int nMarines; 
    int nPower;
    int nFields; 
    int nSpeed;
    int nEnginedamage;
    int nGeneratordamage;
    int nSpecialdamage;
    int nTSdamage;
    
    //booleans
    boolean isplayer;
    boolean isdead;
    boolean opt3 = true;
    
    Vessel DeepCopy(){ //DeepCopy, so ships have unique stats
        Vessel vessel = new Vessel();
        vessel.sORFaction = sORFaction;
        vessel.nBeams = nBeams;
        vessel.nCrew = nCrew;
        vessel.nCrewlimit = nCrewlimit;
        vessel.nCurrentformation = nCurrentformation;
        vessel.nFaction = nFaction;
        vessel.nFieldlimit = nFieldlimit;
        vessel.nFields = nFields;
        vessel.nHull = nHull;
        vessel.nHulllimit = nHulllimit;
        vessel.isdead = isdead;
        vessel.isplayer = isplayer;
        vessel.nMarinelimit = nMarinelimit;
        vessel.nMarines = nMarines;
        vessel.sName = sName;
        vessel.nPower = nPower;
        vessel.nPowergen = nPowergen;
        vessel.nPowerlimit = nPowerlimit;
        vessel.nRails = nRails;
        vessel.nRequisition = nRequisition;
        vessel.nSpeed = nSpeed;
        vessel.nSubweapons = nSubweapons;
        vessel.nCost = nCost;
        vessel.nMaxspeed = nMaxspeed;
        vessel.nMarineskill = nMarineskill;
        vessel.sMarinetype = sMarinetype;
        return vessel;
    }
}
