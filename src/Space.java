
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Space {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {

        List<Formation> formations = new ArrayList<>();
        List<Vessel> VesselList = new ArrayList<>();

        int nFirstrun = 1;
        int nPlayerchoice;
        Scanner fin = new Scanner(new FileReader("shiptypes.mid"));
        Scanner sin = new Scanner(System.in);
        Vessel vessel;
        TextDemo.createAndShowGUI();

        while (nFirstrun == 1) {
            String text = fin.nextLine();

            if (text.equals(".")) { //Break loop when EoF is reached

                nFirstrun = 0; //Stops the game from trying to load vessels 
                break;        //after it has done so already

            }

            //Tear stats from shiptypes.mid
            vessel = new Vessel();
            vessel.sName = text.split(",")[0];
            vessel.sORFaction = text.split(",")[1];
            vessel.nFaction = Integer.parseInt(text.split(",")[2]);
            vessel.nHulllimit = Integer.parseInt(text.split(",")[3]);
            vessel.nCrewlimit = Integer.parseInt(text.split(",")[4]);
            vessel.nMarinelimit = Integer.parseInt(text.split(",")[5]);
            vessel.nPowerlimit = Integer.parseInt(text.split(",")[6]);
            vessel.nPowergen = Integer.parseInt(text.split(",")[7]);
            vessel.nFieldlimit = Integer.parseInt(text.split(",")[8]);
            vessel.nSpeed = Integer.parseInt(text.split(",")[9]);
            vessel.nBeams = Integer.parseInt(text.split(",")[10]);
            vessel.nRails = Integer.parseInt(text.split(",")[11]);
            vessel.nSubweapons = Integer.parseInt(text.split(",")[12]);
            vessel.nCost = Integer.parseInt(text.split(",")[13]);
            vessel.nMarineskill = Integer.parseInt(text.split(",")[14]);
            vessel.sMarinetype = text.split(",")[15];
            VesselList.add(vessel);
        }

        TextDemo.setText("Create formations? (y/n)");

        Loop:
        {
            while (true) {

                TextDemo.Hold();
                switch (TextDemo.returntext()) {


                    case "n":
                        break Loop;

                    case "y":
                        Formation formation = new Formation();


                        TextDemo.setText("Set name:");


                        formation.name = TextDemo.returntext();
                        TextDemo.Hold();

                        formations.add(formation);
                        TextDemo.setText("Create another formation?[y/n]");

                        break;
                }
            }
        }

        TextDemo.setText("Add vessels?");

        Loop:
        {
            while (true) {
                TextDemo.Hold();
                switch (TextDemo.returntext()) {

                    case "n":
                        break Loop;

                    case "y":
                        Formation formation;

                        int i;

                        for (i = 0; i < formations.size(); i++) {

                            formation = formations.get(i);

                            TextDemo.setText(i + "{" + formation.name + "}");
                        }

                        TextDemo.setText("Choose formation to add ships to:");

                        TextDemo.Hold();
                        i = Integer.parseInt(TextDemo.returntext());
                        

                        formation = formations.get(i);

                        //Print lists of vessels
                        for (i = 0; i < VesselList.size(); i++) {

                            vessel = VesselList.get(i);

                            TextDemo.setText(i + "{" + vessel.sName + "} Faction:" + "[" + vessel.sORFaction + "]" + " Crew Compliment " + vessel.nCrewlimit + " Max Velocity(in open space):" + vessel.nSpeed);
                        }

                        TextDemo.setText("Choose vessel");

                        TextDemo.Hold();
                        i = Integer.parseInt(TextDemo.returntext());


                        //Vessel vessel;
                        vessel = VesselList.get(i).DeepCopy();

                        //Set starting resources for each ship
                        vessel.nCrew = vessel.nCrewlimit;
                        vessel.nPower = vessel.nPowerlimit;
                        vessel.nMarines = vessel.nMarinelimit;
                        vessel.nHull = vessel.nHulllimit;
                        vessel.nFields = vessel.nFieldlimit;
                        vessel.nMaxspeed = vessel.nSpeed;
                        vessel.nCurrentformation = i;
                        vessel.isdead = false;
                        vessel.isplayer = false;

                        TextDemo.setText("{" + vessel.sName + "}" + " Has been added to " + formation.name);
                        formation.ships.add(vessel);
                        TextDemo.setText(" Add more ships? (y/n)");

                        break;
                }
            }
        }
        TextDemo.setText("Select player-controlled? (y/n)");
        Loop:
        {
            for (;;) {
                TextDemo.Hold();
                switch (TextDemo.returntext()) {

                    case "n":
                        break Loop;

                    case "y":
                        //Vessel select
                        //Will be used later to define player-controlled ships for psudo-multiplayer.
                        listFNV(formations);

                        TextDemo.setText("Choose your formation");
                        
                        TextDemo.Hold();
                        int i = Integer.parseInt(TextDemo.returntext());

                        TextDemo.setText("Choose your ship");
                        
                        TextDemo.Hold();
                        int j = Integer.parseInt(TextDemo.returntext());
                        

                        TextDemo.setText(formations.get(i).ships.get(j).sName);
                        formations.get(i).ships.get(j).isplayer = true;
                        TextDemo.setText("Readying vessel");
                        TextDemo.setText("Any more? (y/n)");
                        break;
                }
            }
        }

        while (true) { //turnloop, goes through all ships.

            for (int k = 0; k < formations.size(); k++) {

                //TextDemo.setText("{" + k + "} " + formations.get(k).name);

                for (int l = 0; l < formations.get(k).ships.size(); l++) {

                    //TextDemo.setText("    " + "{" + l + "} " + formations.get(k).ships.get(l).name);
                    Vessel playervessel = formations.get(k).ships.get(l);
                    if (k == formations.size()) {
                        k = 0;
                    }

                    if (playervessel.isplayer == true) {

                        while (true) { //Player control.

                            if (playervessel.isdead == true) { //Checks if ur ded bro.
                                playervessel.isplayer = false;
                                break;
                            }

                            TextDemo.setText(playervessel.sName + " " + (l + 1) + " Formation " + "{" + playervessel.nCurrentformation + "}");
                            TextDemo.setText("");
                            TextDemo.setText("Input Commands:");
                            TextDemo.setText("");
                            TextDemo.setText("[1] Ship Status");

                            TextDemo.setText("[2] Hostile actions");
                            TextDemo.setText("[4] Crew management");
                            TextDemo.setText("[5] Boarding actions");

                            if (playervessel.opt3 == true) {
                                TextDemo.setText("[3] Power Management");
                            }

                            TextDemo.Hold();
                            nPlayerchoice = Integer.parseInt(TextDemo.returntext());
                            

                            if (nPlayerchoice == 1) { //Status report
                                TextDemo.setText("");
                                printVessel(playervessel);
                                TextDemo.setText("");
                                if (TextDemo.returntext() != null) {
                                    break;
                                }
                            }

                            if (nPlayerchoice == 2) { //Attack command

                                Vessel targetvessel = choosevessel(formations);
                                attack(playervessel, targetvessel);

                                TextDemo.setText("Press Enter to continue");// End turn, generate power
                                TextDemo.Hold();
                                if (TextDemo.returntext() != null) {
                                    endturn(playervessel);
                                    break;
                                }
                            }

                            if (nPlayerchoice == 3 && playervessel.opt3 == true) { //Energy stuff
                                fieldmanage(playervessel);
                            }

                            if (nPlayerchoice == 4) { //Crew prefs
                                crewmanage(playervessel);
                            }

                            if (nPlayerchoice == 5) { //Boarding
                                if (playervessel.nFields == 0) {
                                    Vessel targetvessel = choosevessel(formations);
                                    boarding(playervessel, targetvessel);

                                } else {
                                    TextDemo.setText("Shields on both ships must be lowered first");
                                    TextDemo.returntext();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void boarding(Vessel playervessel, Vessel targetvessel) {
        if (targetvessel.nFields == 0) {

            TextDemo.setText("Your ship has " + playervessel.nMarines + " " + playervessel.sMarinetype + " on standby");
            TextDemo.setText("Enemy ship has " + targetvessel.nMarines + " " + targetvessel.sMarinetype + " onboard");


        } else {
            TextDemo.setText("Shields on both ships must be lowered first");
        }
    }

    public static void crewmanage(Vessel playervessel) throws InterruptedException {
        Scanner sin = new Scanner(System.in);
        int pref;
        TextDemo.setText("Choose crew priority");
        TextDemo.setText("[0] Repairs");
        TextDemo.setText("[1] Weapon systems");
        TextDemo.setText("[2] Power Generation");
        TextDemo.setText("[3] Field maintainence");
        TextDemo.setText("[4] Sekret Powar");

        TextDemo.Hold();
        pref = Integer.parseInt(TextDemo.returntext());
        if (pref >= 4) {
            playervessel.nCrewtasks = pref;
        } else {
            TextDemo.setText("BORK BORK BORK!");
        }
    }

    public static Vessel choosevessel(List<Formation> formations) throws InterruptedException {
        Scanner sin = new Scanner(System.in);
        Vessel targetvessel;
        printformations(formations);
        TextDemo.setText("Choose target formation");
        TextDemo.Hold();
        int i = Integer.parseInt(TextDemo.returntext());
        printvessels(formations.get(i));
        TextDemo.setText("Choose target ship");
        TextDemo.Hold();
        int j = Integer.parseInt(TextDemo.returntext());
        targetvessel = formations.get(i).ships.get(j);
        return (targetvessel);
    }

    public static void endturn(Vessel playervessel) {
        playervessel.nPower = playervessel.nPower + playervessel.nPowergen;
        playervessel.opt3 = true;
        if (playervessel.nPower >= playervessel.nPowerlimit) {
            playervessel.nPower = playervessel.nPowerlimit;
        }
        if (playervessel.nCrewtasks == 0) {
            playervessel.nHull = playervessel.nHull + (playervessel.nCrew * 2);
            if (playervessel.nHull >= playervessel.nHulllimit) {
                playervessel.nHull = playervessel.nHulllimit;
            }
        }
        TextDemo.clearText();
    }

    public static void listFNV(List<Formation> formations) {

        for (int i = 0; i < formations.size(); i++) {

            TextDemo.setText("{" + i + "} " + formations.get(i).name);

            for (int j = 0; j < formations.get(i).ships.size(); j++) {

                TextDemo.setText("    " + "{" + j + "} " + formations.get(i).ships.get(j).sName + " ");
                double nPercentage = (((double) formations.get(i).ships.get(j).nHull / (double) formations.get(i).ships.get(j).nHulllimit) * 100);
                if (nPercentage >= 90) {
                    TextDemo.setText("        (Undamaged)");
                } else if (nPercentage >= 70) {
                    TextDemo.setText("            Slight damage");
                } else if (nPercentage >= 50) {
                    TextDemo.setText("            Moderate damage");
                } else if (nPercentage >= 30) {
                    TextDemo.setText("            Heavy Damage");
                } else if (nPercentage >= 10) {
                    TextDemo.setText("            Critical Damage");
                } else if (nPercentage >= 0) {
                    TextDemo.setText("            ##Disabled##");
                }
                TextDemo.setText(" |Fields:" + formations.get(i).ships.get(j).nFields + "|");
                TextDemo.setText(" |Velocity:" + formations.get(i).ships.get(j).nSpeed + "|");
                TextDemo.setText("");

            }
        }
    }

    public static void printformations(List<Formation> formations) {

        for (int i = 0; i < formations.size(); i++) {

            TextDemo.setText("{" + i + "} " + formations.get(i).name);

        }
    }

    public static void fieldmanage(Vessel playervessel) throws InterruptedException {
        Scanner sin = new Scanner(System.in);
        double shieldgen = ((playervessel.nPowergen * 0.10) + (playervessel.nCrew * 2) + (playervessel.nFieldlimit / 4));
        double shieldcost = shieldgen * 1.20;
        int nPlayerchoice;
        while (true) {

            TextDemo.setText("Available power: " + playervessel.nPower + "/" + playervessel.nPowerlimit);
            TextDemo.setText("Field Integrity: " + playervessel.nFields + "/" + playervessel.nFieldlimit);
            TextDemo.setText("Structural Integrity: " + playervessel.nHull + "/" + playervessel.nHulllimit);
            TextDemo.setText("");
            TextDemo.setText("Input Commands:");
            TextDemo.setText("");
            TextDemo.setText("[1] Divert power to fields" + " COST:" + shieldcost + "|" + "GENERATES:" + shieldgen);
            TextDemo.setText("[2] Drop fields");
            TextDemo.Hold();
            nPlayerchoice = Integer.parseInt(TextDemo.returntext());
            if (nPlayerchoice == 1 && playervessel.nPower >= shieldcost) {

                playervessel.nFields = (int) (playervessel.nFields + shieldgen);

                if (playervessel.nFields >= playervessel.nFieldlimit) {
                    playervessel.nFields = playervessel.nFieldlimit;
                }
                playervessel.opt3 = false;
                break;
            }

            if (nPlayerchoice == 2 && playervessel.nPower >= shieldcost) {
                playervessel.nPower = playervessel.nPower + playervessel.nFields;
                playervessel.nFields = 0;
                playervessel.opt3 = false;
                break;
            }
        }
    }

    public static void printvessels(Formation formation) {

        for (int i = 0; i < formation.ships.size(); i++) {

            TextDemo.setText("{" + i + "} " + formation.ships.get(i).sName);
            TextDemo.setText("     Current Velocity: " + formation.ships.get(i).nSpeed);
            TextDemo.setText("     Fields: " + formation.ships.get(i).nFields);


        }
    }

    public static void printVessel(Vessel playervessel) {
        TextDemo.setText("SITREP");
        TextDemo.setText(playervessel.sName + ":");
        TextDemo.setText("Faction: " + playervessel.sORFaction);
        TextDemo.setText("Crewmembers onboard: " + playervessel.nCrew);
        TextDemo.setText("Boarding teams: " + playervessel.nMarines);
        TextDemo.setText("Structural integrity: " + playervessel.nHull + "/" + playervessel.nHulllimit);
        TextDemo.setText("Field integrity: " + playervessel.nFields + "/" + playervessel.nFieldlimit);
        TextDemo.setText("Power available: " + playervessel.nPower + "/" + playervessel.nPowerlimit + " Generating:" + playervessel.nPowergen);
        TextDemo.setText(playervessel.nCurrentformation);
        TextDemo.setText("Resources available: " + playervessel.nRequisition);
        TextDemo.setText("");
    }

    public static void attack(Vessel playervessel, Vessel targetvessel) throws InterruptedException {
        Scanner sin = new Scanner(System.in);
        int nPlayerchoice;
        int nDmg;
        int powercost;
        Random rand = new Random();


        if (playervessel.isplayer = true) {
            while (true) {

                TextDemo.setText("Attack " + targetvessel.sName + " with");

                //Flavour text
                if (playervessel.nFaction == 1) { //Earth forces

                    TextDemo.setText("[1] Beamgun turrets" + "(" + playervessel.nBeams + ")" + " POWER NEEDED: " + playervessel.nBeams * 40);
                    TextDemo.setText("[2] Railguns" + "(" + playervessel.nRails + ")" + " POWER NEEDED: " + playervessel.nRails * 35);
                    TextDemo.setText("[3] Torpedoes" + "(" + playervessel.nSubweapons + ")" + " POWER NEEDED: " + playervessel.nSubweapons * 50);
                } else if (playervessel.nFaction == 2) { //Midyian Sovereignty

                    TextDemo.setText("[1] Phase Cannons" + "(" + playervessel.nBeams + ")" + " POWER NEEDED: " + playervessel.nBeams * 40);
                    TextDemo.setText("[2] Phaserail Turrets" + "(" + playervessel.nRails + ")" + " POWER NEEDED: " + playervessel.nRails * 35);
                    TextDemo.setText("[3] Beamgun Coil" + "(" + playervessel.nSubweapons + ")" + " POWER NEEDED: " + playervessel.nSubweapons * 50);
                } else if (playervessel.nFaction == 3) { //Midyian Conglomerate

                    TextDemo.setText("[1] Pulse Hardpoints" + "(" + playervessel.nBeams + ")" + " POWER NEEDED: " + playervessel.nBeams * 40);
                    TextDemo.setText("[2] Mass Cannons" + "(" + playervessel.nRails + ")" + " POWER NEEDED: " + playervessel.nRails * 35);
                    TextDemo.setText("[3] Rocket arrays" + "(" + playervessel.nSubweapons + ")" + " POWER NEEDED: " + playervessel.nSubweapons * 50);
                }

                TextDemo.setText("Power Available: " + playervessel.nPower);
                TextDemo.Hold();
                nPlayerchoice = Integer.parseInt(TextDemo.returntext());

                if (nPlayerchoice == 1) {//Beams are consistant, and do bonus vs. hull.

                    powercost = playervessel.nBeams * 40;
                    if (playervessel.nCrewtasks == 1) {
                        powercost *= 1.20;
                    }

                    if (playervessel.nPower >= powercost) {

                        playervessel.nPower = playervessel.nPower - powercost;

                        //Damage Calc
                        int nBase = playervessel.nBeams * 50;
                        int nPercent = (int) (nBase * 0.25);
                        double ndRandplusminus = (1 - (rand.nextDouble() * 2)); //Determines whether damage will be + or - 25% of nBase
                        nDmg = nBase + (int) (nPercent * ndRandplusminus);
                        TextDemo.setText("Damage: " + nDmg);
                        if (playervessel.nCrewtasks == 1) {
                            nDmg *= 1.25;
                            TextDemo.setText("NewDamage: " + nDmg);
                        }

                        if (targetvessel.nFields <= nDmg) {//Checks if shields can be brought down

                            TextDemo.setText(targetvessel.nFields);
                            nDmg = nDmg - targetvessel.nFields;
                            targetvessel.nFields = 0;

                            if (targetvessel.nHull <= nDmg) { //checks if ship is killable

                                nDmg = 0;
                                targetvessel.nHull = 0;
                                targetvessel.isdead = true;
                                TextDemo.setText("Target Neutralised");

                            } else if (targetvessel.nHull >= nDmg) { //Damages hull

                                TextDemo.setText(targetvessel.nHull);
                                targetvessel.nHull = (int) (targetvessel.nHull - (nDmg * 1.10));
                                TextDemo.setText(targetvessel.nHull);

                                TextDemo.setText("We've hit their hull.");


                            }
                        } else if (targetvessel.nFields >= nDmg) { //Does shield damage

                            TextDemo.setText(targetvessel.nFields);
                            targetvessel.nFields = targetvessel.nFields - nDmg;
                            TextDemo.setText(targetvessel.nFields);

                            TextDemo.setText("We've hit their energy barriers.");
                        }
                        break;
                    }
                }

                if (nPlayerchoice == 2) {//Railguns are harder to predict...

                    powercost = playervessel.nRails * 35;
                    if (playervessel.nCrewtasks == 1) {
                        powercost *= 1.20;
                    }

                    if (playervessel.nPower >= powercost) {

                        playervessel.nPower = playervessel.nPower - powercost;

                        //Damage Calc
                        int nBase = playervessel.nBeams * 40;
                        int nPercent = (int) (nBase * 0.30);
                        double dRandplusminus = (1 - (rand.nextDouble() * 2)); //Determines whether damage will be + or - 25% of nBase
                        nDmg = nBase + (int) (nPercent * dRandplusminus);
                        TextDemo.setText("Damage: " + nDmg);
                        if (playervessel.nCrewtasks == 1) {
                            nDmg *= 1.25;
                            TextDemo.setText("NewDamage: " + nDmg);
                        }

                        if (targetvessel.nFields <= nDmg) {//Checks if shields can be brought down

                            TextDemo.setText(targetvessel.nFields);
                            nDmg = nDmg - targetvessel.nFields;
                            targetvessel.nFields = 0;

                            if (targetvessel.nHull <= nDmg) { //checks if ship is killable

                                nDmg = 0;
                                targetvessel.nHull = 0;
                                targetvessel.isdead = true;
                                TextDemo.setText("Target Neutralised");

                            } else if (targetvessel.nHull >= nDmg) { //Damages hull

                                TextDemo.setText(targetvessel.nHull);
                                targetvessel.nHull = (int) (targetvessel.nHull - (nDmg * 1.10));
                                TextDemo.setText(targetvessel.nHull);

                                TextDemo.setText("We've hit their hull.");


                            }
                        } else if (targetvessel.nFields >= nDmg) { //Does shield damage

                            TextDemo.setText(targetvessel.nFields);
                            targetvessel.nFields = targetvessel.nFields - nDmg;
                            TextDemo.setText(targetvessel.nFields);

                            TextDemo.setText("We've hit their energy barriers.");

                        }
                        break;
                    }
                }


                if (nPlayerchoice == 3) {//Subweapons are devestating...

                    powercost = playervessel.nSubweapons * 60;
                    if (playervessel.nCrewtasks == 1) {
                        powercost *= 1.20;
                    }

                    if (playervessel.nPower >= powercost) {

                        playervessel.nPower = playervessel.nPower - powercost;

                        //Damage Calc
                        int nBase = playervessel.nSubweapons * 55;
                        int nPercent = (int) (nBase * 0.50);
                        double dRandplusminus = (1 - (rand.nextDouble() * 2)); //Determines whether damage will be + or - 25% of nBase
                        nDmg = nBase + (int) (nPercent * dRandplusminus);
                        TextDemo.setText("Damage: " + nDmg);
                        if (playervessel.nCrewtasks == 1) {
                            nDmg *= 1.25;
                            TextDemo.setText("NewDamage: " + nDmg);
                        }


                        if (targetvessel.nFields <= nDmg) {//Checks if shields can be brought down

                            TextDemo.setText(targetvessel.nFields);
                            nDmg = nDmg - targetvessel.nFields;
                            targetvessel.nFields = 0;

                            if (targetvessel.nHull <= nDmg) { //checks if ship is killable

                                nDmg = 0;
                                targetvessel.nHull = 0;
                                targetvessel.isdead = true;
                                TextDemo.setText("Target Neutralised");

                            } else if (targetvessel.nHull >= nDmg) { //Damages hull

                                TextDemo.setText(targetvessel.nHull);
                                targetvessel.nHull = (int) (targetvessel.nHull - (nDmg * 1.10));
                                TextDemo.setText(targetvessel.nHull);

                                TextDemo.setText("We've hit their hull.");


                            }
                        } else if (targetvessel.nFields >= nDmg) { //Does shield damage

                            TextDemo.setText(targetvessel.nFields);
                            targetvessel.nFields = targetvessel.nFields - nDmg;
                            TextDemo.setText(targetvessel.nFields);

                            TextDemo.setText("We've hit their energy barriers.");

                        }
                        break;
                    }
                }
            }
        }
    }
}