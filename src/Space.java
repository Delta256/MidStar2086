
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
        TextIO.createAndShowGUI();

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

        TextIO.setTextNL("Create formations? (y/n)");

        Loop:
        {
            while (true) {

                TextIO.Hold();
                switch (TextIO.returntext()) {


                    case "n":
                        break Loop;

                    case "y":
                        Formation formation = new Formation();


                        TextIO.setTextNL("Set name:");

                        TextIO.Hold();
                        formation.name = TextIO.returntext();
                        

                        formations.add(formation);
                        TextIO.setTextNL("Create another formation?[y/n]");

                        break;
                }
            }
        }

        TextIO.setTextNL("Add vessels?");

        Loop:
        {
            while (true) {
                TextIO.Hold();
                switch (TextIO.returntext()) {

                    case "n":
                        break Loop;

                    case "y":
                        Formation formation;

                        int IS;
                        int IR;

                        for (IR = 0; IR < formations.size(); IR++) {

                            formation = formations.get(IR);

                            TextIO.setTextNL(IR + "{" + formation.name + "}");
                        }

                        TextIO.setTextNL("Choose formation to add ships to:");

                        TextIO.Hold();
                        IR = Integer.parseInt(TextIO.returntext());
                        

                        formation = formations.get(IR);

                        //Print lists of vessels
                        for (IS = 0; IS < VesselList.size(); IS++) {

                            vessel = VesselList.get(IS);

                            TextIO.setTextNL(IS + "{" + vessel.sName + "} Faction:" + "[" + vessel.sORFaction + "]" + " Crew Compliment " + vessel.nCrewlimit + " Max Velocity(in open space):" + vessel.nSpeed);
                        }

                        TextIO.setTextNL("Choose vessel");

                        TextIO.Hold();
                        IS = Integer.parseInt(TextIO.returntext());


                        //Vessel vessel;
                        vessel = VesselList.get(IS).DeepCopy();

                        //Set starting resources for each ship
                        vessel.nCrew = vessel.nCrewlimit;
                        vessel.nPower = vessel.nPowerlimit;
                        vessel.nMarines = vessel.nMarinelimit;
                        vessel.nHull = vessel.nHulllimit;
                        vessel.nFields = vessel.nFieldlimit;
                        vessel.nMaxspeed = vessel.nSpeed;
                        vessel.nCurrentformation = IR;
                        vessel.isdead = false;
                        vessel.isplayer = false;

                        TextIO.setTextNL("{" + vessel.sName + "}" + " Has been added to " + formation.name);
                        formation.ships.add(vessel);
                        TextIO.setTextNL(" Add more ships? (y/n)");

                        break;
                }
            }
        }
        TextIO.setTextNL("Select player-controlled? (y/n)");
        Loop:
        {
            for (;;) {
                TextIO.Hold();
                switch (TextIO.returntext()) {

                    case "n":
                        break Loop;

                    case "y":
                        //Vessel select
                        //Will be used later to define player-controlled ships for psudo-multiplayer.
                        listFNV(formations);

                        TextIO.setTextNL("Choose your formation");
                        
                        TextIO.Hold();
                        int i = Integer.parseInt(TextIO.returntext());

                        TextIO.setTextNL("Choose your ship");
                        
                        TextIO.Hold();
                        int j = Integer.parseInt(TextIO.returntext());
                        

                        TextIO.setTextNL(formations.get(i).ships.get(j).sName);
                        formations.get(i).ships.get(j).isplayer = true;
                        TextIO.setTextNL("Readying vessel");
                        TextIO.setTextNL("Any more? (y/n)");
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

                            TextIO.setTextNL(playervessel.sName + " " + (l + 1) + " Formation " + "{" + playervessel.nCurrentformation + "}");
                            TextIO.setTextNL("");
                            TextIO.setTextNL("Input Commands:");
                            TextIO.setTextNL("");
                            TextIO.setTextNL("[1] Ship Status");
                            TextIO.setTextNL("[2] Hostile actions");
                            if (playervessel.opt3 == true) {
                                TextIO.setTextNL("[3] Power Management");
                            }
                            TextIO.setTextNL("[4] Crew management");
                            TextIO.setTextNL("[5] Boarding actions");

                            

                            TextIO.Hold();
                            nPlayerchoice = Integer.parseInt(TextIO.returntext());
                            

                            if (nPlayerchoice == 1) { //Status report
                                TextIO.setTextNL("");
                                printVessel(playervessel);
                                TextIO.Hold();
                                TextIO.setTextNL("");
                                if (TextIO.returntext() != null) {
                                    break;
                                }
                            }

                            if (nPlayerchoice == 2) { //Attack command

                                Vessel targetvessel = choosevessel(formations);
                                attack(playervessel, targetvessel);

                                TextIO.setTextNL("Press Enter to continue");// End turn, generate power
                                TextIO.Hold();
                                if (TextIO.returntext() != null) {
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
                                    TextIO.setTextNL("Shields on both ships must be lowered first");
                                    TextIO.returntext();
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

            TextIO.setTextNL("Your ship has " + playervessel.nMarines + " " + playervessel.sMarinetype + " on standby");
            TextIO.setTextNL("Enemy ship has " + targetvessel.nMarines + " " + targetvessel.sMarinetype + " onboard");


        } else {
            TextIO.setTextNL("Shields on both ships must be lowered first");
        }
    }

    public static void crewmanage(Vessel playervessel) throws InterruptedException {
        Scanner sin = new Scanner(System.in);
        int pref;
        TextIO.setTextNL("Choose crew priority");
        TextIO.setTextNL("[0] Repairs");
        TextIO.setTextNL("[1] Weapon systems");
        TextIO.setTextNL("[2] Power Generation");
        TextIO.setTextNL("[3] Field maintainence");
        TextIO.setTextNL("[4] Sekret Powar");

        TextIO.Hold();
        pref = Integer.parseInt(TextIO.returntext());
        if (pref >= 4) {
            playervessel.nCrewtasks = pref;
        } else {
            TextIO.setTextNL("BORK BORK BORK!");
        }
    }

    public static Vessel choosevessel(List<Formation> formations) throws InterruptedException {
        Scanner sin = new Scanner(System.in);
        Vessel targetvessel;
        printformations(formations);
        TextIO.setTextNL("Choose target formation");
        TextIO.Hold();
        int i = Integer.parseInt(TextIO.returntext());
        printvessels(formations.get(i));
        TextIO.setTextNL("Choose target ship");
        TextIO.Hold();
        int j = Integer.parseInt(TextIO.returntext());
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
        TextIO.clearText();
    }

    public static void listFNV(List<Formation> formations) {

        for (int i = 0; i < formations.size(); i++) {

            TextIO.setTextNL("{" + i + "} " + formations.get(i).name);

            for (int j = 0; j < formations.get(i).ships.size(); j++) {

                TextIO.setTextNL("    " + "{" + j + "} " + formations.get(i).ships.get(j).sName + " ");
                double nPercentage = (((double) formations.get(i).ships.get(j).nHull / (double) formations.get(i).ships.get(j).nHulllimit) * 100);
                if (nPercentage >= 90) {
                    TextIO.setTextNL("        (Undamaged)");
                } else if (nPercentage >= 70) {
                    TextIO.setTextNL("            Slight damage");
                } else if (nPercentage >= 50) {
                    TextIO.setTextNL("            Moderate damage");
                } else if (nPercentage >= 30) {
                    TextIO.setTextNL("            Heavy Damage");
                } else if (nPercentage >= 10) {
                    TextIO.setTextNL("            Critical Damage");
                } else if (nPercentage >= 0) {
                    TextIO.setTextNL("            ##Disabled##");
                }
                TextIO.setText("         |Fields:" + formations.get(i).ships.get(j).nFields + "|");
                TextIO.setText(" |Velocity:" + formations.get(i).ships.get(j).nSpeed + "|");
                TextIO.setTextNL("");

            }
        }
    }

    public static void printformations(List<Formation> formations) {

        for (int i = 0; i < formations.size(); i++) {

            TextIO.setTextNL("{" + i + "} " + formations.get(i).name);

        }
    }

    public static void fieldmanage(Vessel playervessel) throws InterruptedException {
        Scanner sin = new Scanner(System.in);
        double shieldgen = ((playervessel.nPowergen * 0.10) + (playervessel.nCrew * 2) + (playervessel.nFieldlimit / 4));
        double shieldcost = shieldgen * 1.20;
        int nPlayerchoice;
        while (true) {

            TextIO.setTextNL("Available power: " + playervessel.nPower + "/" + playervessel.nPowerlimit);
            TextIO.setTextNL("Field Integrity: " + playervessel.nFields + "/" + playervessel.nFieldlimit);
            TextIO.setTextNL("Structural Integrity: " + playervessel.nHull + "/" + playervessel.nHulllimit);
            TextIO.setTextNL("");
            TextIO.setTextNL("Input Commands:");
            TextIO.setTextNL("");
            TextIO.setTextNL("[1] Divert power to fields" + " COST:" + shieldcost + "|" + "GENERATES:" + shieldgen);
            TextIO.setTextNL("[2] Drop fields");
            TextIO.Hold();
            nPlayerchoice = Integer.parseInt(TextIO.returntext());
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

            TextIO.setTextNL("{" + i + "} " + formation.ships.get(i).sName);
            TextIO.setTextNL("     Current Velocity: " + formation.ships.get(i).nSpeed);
            TextIO.setTextNL("     Fields: " + formation.ships.get(i).nFields);


        }
    }

    public static void printVessel(Vessel playervessel) {
        TextIO.setTextNL("SITREP");
        TextIO.setTextNL(playervessel.sName + ":");
        TextIO.setTextNL("Faction: " + playervessel.sORFaction);
        TextIO.setTextNL("Crewmembers onboard: " + playervessel.nCrew);
        TextIO.setTextNL("Boarding teams: " + playervessel.nMarines);
        TextIO.setTextNL("Structural integrity: " + playervessel.nHull + "/" + playervessel.nHulllimit);
        TextIO.setTextNL("Field integrity: " + playervessel.nFields + "/" + playervessel.nFieldlimit);
        TextIO.setTextNL("Power available: " + playervessel.nPower + "/" + playervessel.nPowerlimit + " Generating:" + playervessel.nPowergen);
        TextIO.setText(playervessel.nCurrentformation);
        TextIO.setTextNL("Resources available: " + playervessel.nRequisition);
        TextIO.setTextNL("");
    }

    public static void attack(Vessel playervessel, Vessel targetvessel) throws InterruptedException {
        Scanner sin = new Scanner(System.in);
        int nPlayerchoice;
        int nDmg;
        int powercost;
        Random rand = new Random();


        if (playervessel.isplayer = true) {
            while (true) {

                TextIO.setTextNL("Attack " + targetvessel.sName + " with");

                //Flavour text
                if (playervessel.nFaction == 1) { //Earth forces

                    TextIO.setTextNL("[1] Beamgun turrets" + "(" + playervessel.nBeams + ")" + " POWER NEEDED: " + playervessel.nBeams * 40);
                    TextIO.setTextNL("[2] Railguns" + "(" + playervessel.nRails + ")" + " POWER NEEDED: " + playervessel.nRails * 35);
                    TextIO.setTextNL("[3] Torpedoes" + "(" + playervessel.nSubweapons + ")" + " POWER NEEDED: " + playervessel.nSubweapons * 50);
                } else if (playervessel.nFaction == 2) { //Midyian Sovereignty

                    TextIO.setTextNL("[1] Phase Cannons" + "(" + playervessel.nBeams + ")" + " POWER NEEDED: " + playervessel.nBeams * 40);
                    TextIO.setTextNL("[2] Phaserail Turrets" + "(" + playervessel.nRails + ")" + " POWER NEEDED: " + playervessel.nRails * 35);
                    TextIO.setTextNL("[3] Beamgun Coil" + "(" + playervessel.nSubweapons + ")" + " POWER NEEDED: " + playervessel.nSubweapons * 50);
                } else if (playervessel.nFaction == 3) { //Midyian Conglomerate

                    TextIO.setTextNL("[1] Pulse Hardpoints" + "(" + playervessel.nBeams + ")" + " POWER NEEDED: " + playervessel.nBeams * 40);
                    TextIO.setTextNL("[2] Mass Cannons" + "(" + playervessel.nRails + ")" + " POWER NEEDED: " + playervessel.nRails * 35);
                    TextIO.setTextNL("[3] Rocket arrays" + "(" + playervessel.nSubweapons + ")" + " POWER NEEDED: " + playervessel.nSubweapons * 50);
                }

                TextIO.setTextNL("Power Available: " + playervessel.nPower);
                TextIO.Hold();
                nPlayerchoice = Integer.parseInt(TextIO.returntext());

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
                        TextIO.setTextNL("Damage: " + nDmg);
                        if (playervessel.nCrewtasks == 1) {
                            nDmg *= 1.25;
                            TextIO.setTextNL("NewDamage: " + nDmg);
                        }

                        if (targetvessel.nFields <= nDmg) {//Checks if shields can be brought down

                            TextIO.setText(targetvessel.nFields);
                            nDmg = nDmg - targetvessel.nFields;
                            targetvessel.nFields = 0;

                            if (targetvessel.nHull <= nDmg) { //checks if ship is killable

                                nDmg = 0;
                                targetvessel.nHull = 0;
                                targetvessel.isdead = true;
                                TextIO.setTextNL("Target Neutralised");

                            } else if (targetvessel.nHull >= nDmg) { //Damages hull

                                TextIO.setText(targetvessel.nHull);
                                targetvessel.nHull = (int) (targetvessel.nHull - (nDmg * 1.10));
                                TextIO.setText(targetvessel.nHull);

                                TextIO.setTextNL("We've hit their hull.");


                            }
                        } else if (targetvessel.nFields >= nDmg) { //Does shield damage

                            TextIO.setText(targetvessel.nFields);
                            targetvessel.nFields = targetvessel.nFields - nDmg;
                            TextIO.setText(targetvessel.nFields);

                            TextIO.setTextNL("We've hit their energy barriers.");
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
                        TextIO.setTextNL("Damage: " + nDmg);
                        if (playervessel.nCrewtasks == 1) {
                            nDmg *= 1.25;
                            TextIO.setTextNL("NewDamage: " + nDmg);
                        }

                        if (targetvessel.nFields <= nDmg) {//Checks if shields can be brought down

                            TextIO.setText(targetvessel.nFields);
                            nDmg = nDmg - targetvessel.nFields;
                            targetvessel.nFields = 0;

                            if (targetvessel.nHull <= nDmg) { //checks if ship is killable

                                nDmg = 0;
                                targetvessel.nHull = 0;
                                targetvessel.isdead = true;
                                TextIO.setTextNL("Target Neutralised");

                            } else if (targetvessel.nHull >= nDmg) { //Damages hull

                                TextIO.setText(targetvessel.nHull);
                                targetvessel.nHull = (int) (targetvessel.nHull - (nDmg * 1.10));
                                TextIO.setText(targetvessel.nHull);

                                TextIO.setTextNL("We've hit their hull.");


                            }
                        } else if (targetvessel.nFields >= nDmg) { //Does shield damage

                            TextIO.setText(targetvessel.nFields);
                            targetvessel.nFields = targetvessel.nFields - nDmg;
                            TextIO.setText(targetvessel.nFields);

                            TextIO.setTextNL("We've hit their energy barriers.");

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
                        TextIO.setTextNL("Damage: " + nDmg);
                        if (playervessel.nCrewtasks == 1) {
                            nDmg *= 1.25;
                            TextIO.setTextNL("NewDamage: " + nDmg);
                        }


                        if (targetvessel.nFields <= nDmg) {//Checks if shields can be brought down

                            TextIO.setText(targetvessel.nFields);
                            nDmg = nDmg - targetvessel.nFields;
                            targetvessel.nFields = 0;

                            if (targetvessel.nHull <= nDmg) { //checks if ship is killable

                                nDmg = 0;
                                targetvessel.nHull = 0;
                                targetvessel.isdead = true;
                                TextIO.setTextNL("Target Neutralised");

                            } else if (targetvessel.nHull >= nDmg) { //Damages hull

                                TextIO.setText(targetvessel.nHull);
                                targetvessel.nHull = (int) (targetvessel.nHull - (nDmg * 1.10));
                                TextIO.setText(targetvessel.nHull);

                                TextIO.setTextNL("We've hit their hull.");


                            }
                        } else if (targetvessel.nFields >= nDmg) { //Does shield damage

                            TextIO.setText(targetvessel.nFields);
                            targetvessel.nFields = targetvessel.nFields - nDmg;
                            TextIO.setText(targetvessel.nFields);

                            TextIO.setTextNL("We've hit their energy barriers.");

                        }
                        break;
                    }
                }
            }
        }
    }
}