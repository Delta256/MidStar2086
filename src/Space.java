
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
            vessel.name = text.split(",")[0];
            vessel.ORFaction = text.split(",")[1];
            vessel.faction = Integer.parseInt(text.split(",")[2]);
            vessel.hulllimit = Integer.parseInt(text.split(",")[3]);
            vessel.crewlimit = Integer.parseInt(text.split(",")[4]);
            vessel.marinelimit = Integer.parseInt(text.split(",")[5]);
            vessel.powerlimit = Integer.parseInt(text.split(",")[6]);
            vessel.powergen = Integer.parseInt(text.split(",")[7]);
            vessel.fieldlimit = Integer.parseInt(text.split(",")[8]);
            vessel.speed = Integer.parseInt(text.split(",")[9]);
            vessel.beams = Integer.parseInt(text.split(",")[10]);
            vessel.rails = Integer.parseInt(text.split(",")[11]);
            vessel.subweapons = Integer.parseInt(text.split(",")[12]);
            vessel.cost = Integer.parseInt(text.split(",")[13]);
            vessel.marineskill = Integer.parseInt(text.split(",")[14]);
            vessel.marinetype = text.split(",")[15];
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

                            TextDemo.setText(i + "{" + vessel.name + "} Faction:" + "[" + vessel.ORFaction + "]" + " Crew Compliment " + vessel.crewlimit + " Max Velocity(in open space):" + vessel.speed);
                        }

                        TextDemo.setText("Choose vessel");

                        TextDemo.Hold();
                        i = Integer.parseInt(TextDemo.returntext());


                        //Vessel vessel;
                        vessel = VesselList.get(i).DeepCopy();

                        //Set starting resources for each ship
                        vessel.crew = vessel.crewlimit;
                        vessel.power = vessel.powerlimit;
                        vessel.marines = vessel.marinelimit;
                        vessel.hull = vessel.hulllimit;
                        vessel.fields = vessel.fieldlimit;
                        vessel.maxspeed = vessel.speed;
                        vessel.currentformation = i;
                        vessel.isdead = false;
                        vessel.isplayer = false;

                        TextDemo.setText("{" + vessel.name + "}" + " Has been added to " + formation.name);
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
                        

                        TextDemo.setText(formations.get(i).ships.get(j).name);
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

                            TextDemo.setText(playervessel.name + " " + (l + 1) + " Formation " + "{" + playervessel.currentformation + "}");
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
                                if (playervessel.fields == 0) {
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
        if (targetvessel.fields == 0) {

            TextDemo.setText("Your ship has " + playervessel.marines + " " + playervessel.marinetype + " on standby");
            TextDemo.setText("Enemy ship has " + targetvessel.marines + " " + targetvessel.marinetype + " onboard");


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
            playervessel.crewtasks = pref;
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
        playervessel.power = playervessel.power + playervessel.powergen;
        playervessel.opt3 = true;
        if (playervessel.power >= playervessel.powerlimit) {
            playervessel.power = playervessel.powerlimit;
        }
        if (playervessel.crewtasks == 0) {
            playervessel.hull = playervessel.hull + (playervessel.crew * 2);
            if (playervessel.hull >= playervessel.hulllimit) {
                playervessel.hull = playervessel.hulllimit;
            }
        }
        TextDemo.clearText();
    }

    public static void listFNV(List<Formation> formations) {

        for (int i = 0; i < formations.size(); i++) {

            TextDemo.setText("{" + i + "} " + formations.get(i).name);

            for (int j = 0; j < formations.get(i).ships.size(); j++) {

                TextDemo.setText("    " + "{" + j + "} " + formations.get(i).ships.get(j).name + " ");
                double percentage = (((double) formations.get(i).ships.get(j).hull / (double) formations.get(i).ships.get(j).hulllimit) * 100);
                if (percentage >= 90) {
                    TextDemo.setText("        (Undamaged)");
                } else if (percentage >= 70) {
                    TextDemo.setText("            Slight damage");
                } else if (percentage >= 50) {
                    TextDemo.setText("            Moderate damage");
                } else if (percentage >= 30) {
                    TextDemo.setText("            Heavy Damage");
                } else if (percentage >= 10) {
                    TextDemo.setText("            Critical Damage");
                } else if (percentage >= 0) {
                    TextDemo.setText("            ##Disabled##");
                }
                TextDemo.setText(" |Fields:" + formations.get(i).ships.get(j).fields + "|");
                TextDemo.setText(" |Velocity:" + formations.get(i).ships.get(j).speed + "|");
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
        double shieldgen = ((playervessel.powergen * 0.10) + (playervessel.crew * 2) + (playervessel.fieldlimit / 4));
        double shieldcost = shieldgen * 1.20;
        int nPlayerchoice;
        while (true) {

            TextDemo.setText("Available power: " + playervessel.power + "/" + playervessel.powerlimit);
            TextDemo.setText("Field Integrity: " + playervessel.fields + "/" + playervessel.fieldlimit);
            TextDemo.setText("Structural Integrity: " + playervessel.hull + "/" + playervessel.hulllimit);
            TextDemo.setText("");
            TextDemo.setText("Input Commands:");
            TextDemo.setText("");
            TextDemo.setText("[1] Divert power to fields" + " COST:" + shieldcost + "|" + "GENERATES:" + shieldgen);
            TextDemo.setText("[2] Drop fields");
            TextDemo.Hold();
            nPlayerchoice = Integer.parseInt(TextDemo.returntext());
            if (nPlayerchoice == 1 && playervessel.power >= shieldcost) {

                playervessel.fields = (int) (playervessel.fields + shieldgen);

                if (playervessel.fields >= playervessel.fieldlimit) {
                    playervessel.fields = playervessel.fieldlimit;
                }
                playervessel.opt3 = false;
                break;
            }

            if (nPlayerchoice == 2 && playervessel.power >= shieldcost) {
                playervessel.power = playervessel.power + playervessel.fields;
                playervessel.fields = 0;
                playervessel.opt3 = false;
                break;
            }
        }
    }

    public static void printvessels(Formation formation) {

        for (int i = 0; i < formation.ships.size(); i++) {

            TextDemo.setText("{" + i + "} " + formation.ships.get(i).name);
            TextDemo.setText("     Current Velocity: " + formation.ships.get(i).speed);
            TextDemo.setText("     Fields: " + formation.ships.get(i).fields);


        }
    }

    public static void printVessel(Vessel playervessel) {
        TextDemo.setText("SITREP");
        TextDemo.setText(playervessel.name + ":");
        TextDemo.setText("Faction: " + playervessel.ORFaction);
        TextDemo.setText("Crewmembers onboard: " + playervessel.crew);
        TextDemo.setText("Boarding teams: " + playervessel.marines);
        TextDemo.setText("Structural integrity: " + playervessel.hull + "/" + playervessel.hulllimit);
        TextDemo.setText("Field integrity: " + playervessel.fields + "/" + playervessel.fieldlimit);
        TextDemo.setText("Power available: " + playervessel.power + "/" + playervessel.powerlimit + " Generating:" + playervessel.powergen);
        TextDemo.setText(playervessel.currentformation);
        TextDemo.setText("Resources available: " + playervessel.requisition);
        TextDemo.setText("");
    }

    public static void attack(Vessel playervessel, Vessel targetvessel) throws InterruptedException {
        Scanner sin = new Scanner(System.in);
        int nPlayerchoice;
        int dmg;
        int powercost;
        Random rand = new Random();


        if (playervessel.isplayer = true) {
            while (true) {

                TextDemo.setText("Attack " + targetvessel.name + " with");

                //Flavour text
                if (playervessel.faction == 1) { //Earth forces

                    TextDemo.setText("[1] Beamgun turrets" + "(" + playervessel.beams + ")" + " POWER NEEDED: " + playervessel.beams * 40);
                    TextDemo.setText("[2] Railguns" + "(" + playervessel.rails + ")" + " POWER NEEDED: " + playervessel.rails * 35);
                    TextDemo.setText("[3] Torpedoes" + "(" + playervessel.subweapons + ")" + " POWER NEEDED: " + playervessel.subweapons * 50);
                } else if (playervessel.faction == 2) { //Midyian Sovereignty

                    TextDemo.setText("[1] Phase Cannons" + "(" + playervessel.beams + ")" + " POWER NEEDED: " + playervessel.beams * 40);
                    TextDemo.setText("[2] Phaserail Turrets" + "(" + playervessel.rails + ")" + " POWER NEEDED: " + playervessel.rails * 35);
                    TextDemo.setText("[3] Beamgun Coil" + "(" + playervessel.subweapons + ")" + " POWER NEEDED: " + playervessel.subweapons * 50);
                } else if (playervessel.faction == 3) { //Midyian Conglomerate

                    TextDemo.setText("[1] Pulse Hardpoints" + "(" + playervessel.beams + ")" + " POWER NEEDED: " + playervessel.beams * 40);
                    TextDemo.setText("[2] Mass Cannons" + "(" + playervessel.rails + ")" + " POWER NEEDED: " + playervessel.rails * 35);
                    TextDemo.setText("[3] Rocket arrays" + "(" + playervessel.subweapons + ")" + " POWER NEEDED: " + playervessel.subweapons * 50);
                }

                TextDemo.setText("Power Available: " + playervessel.power);
                TextDemo.Hold();
                nPlayerchoice = Integer.parseInt(TextDemo.returntext());

                if (nPlayerchoice == 1) {//Beams are consistant, and do bonus vs. hull.

                    powercost = playervessel.beams * 40;
                    if (playervessel.crewtasks == 1) {
                        powercost *= 1.20;
                    }

                    if (playervessel.power >= powercost) {

                        playervessel.power = playervessel.power - powercost;

                        //Damage Calc
                        int base = playervessel.beams * 50;
                        int percent = (int) (base * 0.25);
                        double randplusminus = (1 - (rand.nextDouble() * 2)); //Determines whether damage will be + or - 25% of base
                        dmg = base + (int) (percent * randplusminus);
                        TextDemo.setText("Damage: " + dmg);
                        if (playervessel.crewtasks == 1) {
                            dmg *= 1.25;
                            TextDemo.setText("NewDamage: " + dmg);
                        }

                        if (targetvessel.fields <= dmg) {//Checks if shields can be brought down

                            TextDemo.setText(targetvessel.fields);
                            dmg = dmg - targetvessel.fields;
                            targetvessel.fields = 0;

                            if (targetvessel.hull <= dmg) { //checks if ship is killable

                                dmg = 0;
                                targetvessel.hull = 0;
                                targetvessel.isdead = true;
                                TextDemo.setText("Target Neutralised");

                            } else if (targetvessel.hull >= dmg) { //Damages hull

                                TextDemo.setText(targetvessel.hull);
                                targetvessel.hull = (int) (targetvessel.hull - (dmg * 1.10));
                                TextDemo.setText(targetvessel.hull);

                                TextDemo.setText("We've hit their hull.");


                            }
                        } else if (targetvessel.fields >= dmg) { //Does shield damage

                            TextDemo.setText(targetvessel.fields);
                            targetvessel.fields = targetvessel.fields - dmg;
                            TextDemo.setText(targetvessel.fields);

                            TextDemo.setText("We've hit their energy barriers.");
                        }
                        break;
                    }
                }

                if (nPlayerchoice == 2) {//Railguns are harder to predict...

                    powercost = playervessel.rails * 35;
                    if (playervessel.crewtasks == 1) {
                        powercost *= 1.20;
                    }

                    if (playervessel.power >= powercost) {

                        playervessel.power = playervessel.power - powercost;

                        //Damage Calc
                        int base = playervessel.beams * 40;
                        int percent = (int) (base * 0.30);
                        double randplusminus = (1 - (rand.nextDouble() * 2)); //Determines whether damage will be + or - 25% of base
                        dmg = base + (int) (percent * randplusminus);
                        TextDemo.setText("Damage: " + dmg);
                        if (playervessel.crewtasks == 1) {
                            dmg *= 1.25;
                            TextDemo.setText("NewDamage: " + dmg);
                        }

                        if (targetvessel.fields <= dmg) {//Checks if shields can be brought down

                            TextDemo.setText(targetvessel.fields);
                            dmg = dmg - targetvessel.fields;
                            targetvessel.fields = 0;

                            if (targetvessel.hull <= dmg) { //checks if ship is killable

                                dmg = 0;
                                targetvessel.hull = 0;
                                targetvessel.isdead = true;
                                TextDemo.setText("Target Neutralised");

                            } else if (targetvessel.hull >= dmg) { //Damages hull

                                TextDemo.setText(targetvessel.hull);
                                targetvessel.hull = (int) (targetvessel.hull - (dmg * 1.10));
                                TextDemo.setText(targetvessel.hull);

                                TextDemo.setText("We've hit their hull.");


                            }
                        } else if (targetvessel.fields >= dmg) { //Does shield damage

                            TextDemo.setText(targetvessel.fields);
                            targetvessel.fields = targetvessel.fields - dmg;
                            TextDemo.setText(targetvessel.fields);

                            TextDemo.setText("We've hit their energy barriers.");

                        }
                        break;
                    }
                }


                if (nPlayerchoice == 3) {//Subweapons are devestating...

                    powercost = playervessel.subweapons * 60;
                    if (playervessel.crewtasks == 1) {
                        powercost *= 1.20;
                    }

                    if (playervessel.power >= powercost) {

                        playervessel.power = playervessel.power - powercost;

                        //Damage Calc
                        int base = playervessel.subweapons * 55;
                        int percent = (int) (base * 0.50);
                        double randplusminus = (1 - (rand.nextDouble() * 2)); //Determines whether damage will be + or - 25% of base
                        dmg = base + (int) (percent * randplusminus);
                        TextDemo.setText("Damage: " + dmg);
                        if (playervessel.crewtasks == 1) {
                            dmg *= 1.25;
                            TextDemo.setText("NewDamage: " + dmg);
                        }


                        if (targetvessel.fields <= dmg) {//Checks if shields can be brought down

                            TextDemo.setText(targetvessel.fields);
                            dmg = dmg - targetvessel.fields;
                            targetvessel.fields = 0;

                            if (targetvessel.hull <= dmg) { //checks if ship is killable

                                dmg = 0;
                                targetvessel.hull = 0;
                                targetvessel.isdead = true;
                                TextDemo.setText("Target Neutralised");

                            } else if (targetvessel.hull >= dmg) { //Damages hull

                                TextDemo.setText(targetvessel.hull);
                                targetvessel.hull = (int) (targetvessel.hull - (dmg * 1.10));
                                TextDemo.setText(targetvessel.hull);

                                TextDemo.setText("We've hit their hull.");


                            }
                        } else if (targetvessel.fields >= dmg) { //Does shield damage

                            TextDemo.setText(targetvessel.fields);
                            targetvessel.fields = targetvessel.fields - dmg;
                            TextDemo.setText(targetvessel.fields);

                            TextDemo.setText("We've hit their energy barriers.");

                        }
                        break;
                    }
                }
            }
        }
    }
}