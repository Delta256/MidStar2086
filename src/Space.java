
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
    public static void main(String[] args) throws FileNotFoundException, IOException {

        List<Formation> formations = new ArrayList<>();
        List<Vessel> VesselList = new ArrayList<>();

        int firstrun = 1;
        int playerchoice;
        Scanner fin = new Scanner(new FileReader("shiptypes.mid"));
        Scanner sin = new Scanner(System.in);

        Vessel vessel;
        
        TextDemo.createAndShowGUI();

        while (firstrun == 1) {
            String text = fin.nextLine();

            if (text.equals(".")) { //Break loop when EoF is reached

                firstrun = 0; //Stops the game from trying to load vessels 
                break;        //after is has done so already

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

        System.out.println("Create formations? (y/n)");

        Loop:
        {
            while (true) {
                switch (sin.nextLine()) {

                    case "n":
                        break Loop;

                    case "y":
                        Formation formation = new Formation();

                        System.out.println("Set name:");
                        formation.name = sin.nextLine();

                        formations.add(formation);
                        System.out.println("Create another formation?[y/n]");

                        break;
                }
            }
        }

        System.out.println("Add vessels?");

        Loop:
        {
            while (true) {
                switch (sin.nextLine()) {

                    case "n":
                        break Loop;

                    case "y":
                        Formation formation;

                        int i;

                        for (i = 0; i < formations.size(); i++) {

                            formation = formations.get(i);

                            System.out.println(i + "{" + formation.name + "}");
                        }

                        System.out.println("Choose formation to add ships to:");

                        i = Integer.parseInt(sin.nextLine());
                        formation = formations.get(i);

                        //Print lists of vessels
                        for (i = 0; i < VesselList.size(); i++) {

                            vessel = VesselList.get(i);

                            System.out.println(i + "{" + vessel.name + "} Faction:" + "[" + vessel.ORFaction + "]" + " Crew Compliment " + vessel.crewlimit + " Max Velocity(in open space):" + vessel.speed);
                        }

                        System.out.println("Choose vessel");

                        i = Integer.parseInt(sin.nextLine());

                        Vessel vesselTBA;
                        vesselTBA = VesselList.get(i).DeepCopy();

                        //Set starting resources for each ship
                        vesselTBA.crew = vesselTBA.crewlimit;
                        vesselTBA.power = vesselTBA.powerlimit;
                        vesselTBA.marines = vesselTBA.marinelimit;
                        vesselTBA.hull = vesselTBA.hulllimit;
                        vesselTBA.fields = vesselTBA.fieldlimit;
                        vesselTBA.maxspeed = vesselTBA.speed;
                        vesselTBA.currentformation = i;
                        vesselTBA.isdead = false;
                        vesselTBA.isplayer = false;

                        System.out.println("{" + vesselTBA.name + "}" + " Has been added to " + formation.name);
                        formation.ships.add(vesselTBA);
                        System.out.println(" Add more ships? (y/n)");

                        break;
                }
            }
        }
        System.out.println("Select player-controlled? (y/n)");
        Loop:
        {
            for (;;) {
                switch (sin.nextLine()) {

                    case "n":
                        break Loop;

                    case "y":
                        //Vessel select
                        //Will be used later to define player-controlled ships for psudo-multiplayer.
                        listFNV(formations);

                        System.out.println("Choose your formation");
                        int i = Integer.parseInt(sin.nextLine());

                        System.out.println("Choose your ship");
                        int j = Integer.parseInt(sin.nextLine());
                        System.out.println(formations.get(i).ships.get(j).name);
                        formations.get(i).ships.get(j).isplayer = true;
                        System.out.println("Readying vessel");
                        System.out.println("Any more? (y/n)");
                        break;
                }
            }
        }

        while (true) { //turnloop, goes through all ships.

            for (int k = 0; k < formations.size(); k++) {

                //System.out.println("{" + k + "} " + formations.get(k).name);

                for (int l = 0; l < formations.get(k).ships.size(); l++) {

                    //System.out.println("    " + "{" + l + "} " + formations.get(k).ships.get(l).name);
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

                            System.out.println(playervessel.name + " " + (l + 1) + " Formation " + "{" + playervessel.currentformation + "}");
                            System.out.println("");
                            System.out.println("Input Commands:");
                            System.out.println("");
                            System.out.println("[1] Ship Status");

                            System.out.println("[2] Hostile actions");
                            System.out.println("[4] Crew management");
                            System.out.println("[5] Boarding actions");

                            if (playervessel.opt3 == true) {
                                System.out.println("[3] Power Management");
                            }

                            playerchoice = Integer.parseInt(sin.nextLine());

                            if (playerchoice == 1) { //Status report
                                System.out.println("");
                                printVessel(playervessel);
                                System.out.println("");
                                System.out.println("Press Enter when ready.");
                                sin.nextLine();
                            }

                            if (playerchoice == 2) { //Attack command

                                Vessel targetvessel = choosevessel(formations);
                                attack(playervessel, targetvessel);

                                System.out.println("Press Enter to continue");// End turn, generate power
                                if (sin.nextLine() != null) {
                                    endturn(playervessel);
                                    break;
                                }
                            }

                            if (playerchoice == 3 && playervessel.opt3 == true) { //Energy stuff
                                fieldmanage(playervessel);
                            }

                            if (playerchoice == 4) { //Crew prefs
                                crewmanage(playervessel);
                            }

                            if (playerchoice == 5) { //Boarding
                                if (playervessel.fields == 0) {
                                    Vessel targetvessel = choosevessel(formations);
                                    boarding(playervessel, targetvessel);

                                } else {
                                    System.out.println("Shields on both ships must be lowered first");
                                    sin.nextLine();
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
            
            System.out.println("Your ship has " + playervessel.marines + " " + playervessel.marinetype + " on standby");
            System.out.println("Enemy ship has " + targetvessel.marines + " " + targetvessel.marinetype + " onboard");
        
        
        } else {
            System.out.println("Shields on both ships must be lowered first");
        }
    }

    public static void crewmanage(Vessel playervessel) {
        Scanner sin = new Scanner(System.in);
        int pref;
        System.out.println("Choose crew priority");
        System.out.println("[0] Repairs");
        System.out.println("[1] Weapon systems");
        System.out.println("[2] Power Generation");
        System.out.println("[3] Field maintainence");
        System.out.println("[4] Sekret Powar");
        pref = Integer.parseInt(sin.nextLine());
        if (pref >= 4) {
            playervessel.crewtasks = pref;
        } else {
            System.out.println("BORK BORK BORK!");
        }
    }

    public static Vessel choosevessel(List<Formation> formations) {
        Scanner sin = new Scanner(System.in);
        Vessel targetvessel;
        printformations(formations);
        System.out.println("Choose target formation");
        int i = Integer.parseInt(sin.nextLine());
        printvessels(formations.get(i));
        System.out.println("Choose target ship");
        int j = Integer.parseInt(sin.nextLine());
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
    }

    public static void listFNV(List<Formation> formations) {

        for (int i = 0; i < formations.size(); i++) {

            System.out.println("{" + i + "} " + formations.get(i).name);

            for (int j = 0; j < formations.get(i).ships.size(); j++) {

                System.out.println("    " + "{" + j + "} " + formations.get(i).ships.get(j).name + " ");
                double percentage = (((double) formations.get(i).ships.get(j).hull / (double) formations.get(i).ships.get(j).hulllimit) * 100);
                if (percentage >= 90) {
                    System.out.print("        (Undamaged)");
                } else if (percentage >= 70) {
                    System.out.println("            Slight damage");
                } else if (percentage >= 50) {
                    System.out.println("            Moderate damage");
                } else if (percentage >= 30) {
                    System.out.println("            Heavy Damage");
                } else if (percentage >= 10) {
                    System.out.println("            Critical Damage");
                } else if (percentage >= 0) {
                    System.out.println("            ##Disabled##");
                }
                System.out.print(" |Fields:" + formations.get(i).ships.get(j).fields+"|");
                System.out.print(" |Velocity:" + formations.get(i).ships.get(j).speed+"|");
                System.out.println("");

            }
        }
    }

    public static void printformations(List<Formation> formations) {

        for (int i = 0; i < formations.size(); i++) {

            System.out.println("{" + i + "} " + formations.get(i).name);

        }
    }

    public static void fieldmanage(Vessel playervessel) {
        Scanner sin = new Scanner(System.in);
        double shieldgen = ((playervessel.powergen * 0.10) + (playervessel.crew * 2) + (playervessel.fieldlimit / 4));
        double shieldcost = shieldgen * 1.20;
        int playerchoice;
        while (true) {

            System.out.println("Available power: " + playervessel.power + "/" + playervessel.powerlimit);
            System.out.println("Field Integrity: " + playervessel.fields + "/" + playervessel.fieldlimit);
            System.out.println("Structural Integrity: " + playervessel.hull + "/" + playervessel.hulllimit);
            System.out.println("");
            System.out.println("Input Commands:");
            System.out.println("");
            System.out.println("[1] Divert power to fields" + " COST:" + shieldcost + "|" + "GENERATES:" + shieldgen);
            System.out.println("[2] Drop fields");
            playerchoice = Integer.parseInt(sin.nextLine());
            if (playerchoice == 1 && playervessel.power >= shieldcost) {

                playervessel.fields = (int) (playervessel.fields + shieldgen);

                if (playervessel.fields >= playervessel.fieldlimit) {
                    playervessel.fields = playervessel.fieldlimit;
                }
                playervessel.opt3 = false;
                break;
            }

            if (playerchoice == 2 && playervessel.power >= shieldcost) {
                playervessel.power = playervessel.power + playervessel.fields;
                playervessel.fields = 0;
                playervessel.opt3 = false;
                break;
            }
        }
    }

    public static void printvessels(Formation formation) {

        for (int i = 0; i < formation.ships.size(); i++) {

            System.out.println("{" + i + "} " + formation.ships.get(i).name);
            System.out.println("     Current Velocity: " + formation.ships.get(i).speed);
            System.out.println("     Fields: " + formation.ships.get(i).fields);
            

        }
    }
    
    public static void printVessel(Vessel playervessel) {
        System.out.println("SITREP");
        System.out.println(playervessel.name + ":");
        System.out.println("Faction: " + playervessel.ORFaction);
        System.out.println("Crewmembers onboard: " + playervessel.crew);
        System.out.println("Boarding teams: " + playervessel.marines);
        System.out.println("Structural integrity: " + playervessel.hull + "/" + playervessel.hulllimit);
        System.out.println("Field integrity: " + playervessel.fields + "/" + playervessel.fieldlimit);
        System.out.println("Power available: " + playervessel.power + "/" + playervessel.powerlimit + " Generating:" + playervessel.powergen);
        System.out.println(playervessel.currentformation);
        System.out.println("Resources available: " + playervessel.requisition);
        System.out.println("");
    }

    public static void attack(Vessel playervessel, Vessel targetvessel) {
        Scanner sin = new Scanner(System.in);
        int playerchoice;
        int dmg;
        int powercost;
        Random rand = new Random();


        if (playervessel.isplayer = true) {
            while (true) {

                System.out.println("Attack " + targetvessel.name + " with");

                //Flavour text
                if (playervessel.faction == 1) { //Earth forces

                    System.out.println("[1] Beamgun turrets" + "(" + playervessel.beams + ")" + " POWER NEEDED: " + playervessel.beams * 40);
                    System.out.println("[2] Railguns" + "(" + playervessel.rails + ")" + " POWER NEEDED: " + playervessel.rails * 35);
                    System.out.println("[3] Torpedoes" + "(" + playervessel.subweapons + ")" + " POWER NEEDED: " + playervessel.subweapons * 50);
                } else if (playervessel.faction == 2) { //Midyian Sovereignty

                    System.out.println("[1] Phase Cannons" + "(" + playervessel.beams + ")" + " POWER NEEDED: " + playervessel.beams * 40);
                    System.out.println("[2] Phaserail Turrets" + "(" + playervessel.rails + ")" + " POWER NEEDED: " + playervessel.rails * 35);
                    System.out.println("[3] Beamgun Coil" + "(" + playervessel.subweapons + ")" + " POWER NEEDED: " + playervessel.subweapons * 50);
                } else if (playervessel.faction == 3) { //Midyian Conglomerate

                    System.out.println("[1] Pulse Hardpoints" + "(" + playervessel.beams + ")" + " POWER NEEDED: " + playervessel.beams * 40);
                    System.out.println("[2] Mass Cannons" + "(" + playervessel.rails + ")" + " POWER NEEDED: " + playervessel.rails * 35);
                    System.out.println("[3] Rocket arrays" + "(" + playervessel.subweapons + ")" + " POWER NEEDED: " + playervessel.subweapons * 50);
                }

                System.out.println("Power Available: " + playervessel.power);
                playerchoice = Integer.parseInt(sin.nextLine());

                if (playerchoice == 1) {//Beams are consistant, and do bonus vs. hull.

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
                        System.out.println("Damage: " + dmg);
                        if (playervessel.crewtasks == 1) {
                            dmg *= 1.25;
                            System.out.println("NewDamage: " + dmg);
                        }

                        if (targetvessel.fields <= dmg) {//Checks if shields can be brought down

                            System.out.println(targetvessel.fields);
                            dmg = dmg - targetvessel.fields;
                            targetvessel.fields = 0;

                            if (targetvessel.hull <= dmg) { //checks if ship is killable

                                dmg = 0;
                                targetvessel.hull = 0;
                                targetvessel.isdead = true;
                                System.out.println("Target Neutralised");

                            } else if (targetvessel.hull >= dmg) { //Damages hull

                                System.out.println(targetvessel.hull);
                                targetvessel.hull = (int) (targetvessel.hull - (dmg * 1.10));
                                System.out.println(targetvessel.hull);

                                System.out.println("We've hit their hull.");


                            }
                        } else if (targetvessel.fields >= dmg) { //Does shield damage

                            System.out.println(targetvessel.fields);
                            targetvessel.fields = targetvessel.fields - dmg;
                            System.out.println(targetvessel.fields);

                            System.out.println("We've hit their energy barriers.");
                        }
                        break;
                    }
                }

                if (playerchoice == 2) {//Railguns are harder to predict...

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
                        System.out.println("Damage: " + dmg);
                        if (playervessel.crewtasks == 1) {
                            dmg *= 1.25;
                            System.out.println("NewDamage: " + dmg);
                        }

                        if (targetvessel.fields <= dmg) {//Checks if shields can be brought down

                            System.out.println(targetvessel.fields);
                            dmg = dmg - targetvessel.fields;
                            targetvessel.fields = 0;

                            if (targetvessel.hull <= dmg) { //checks if ship is killable

                                dmg = 0;
                                targetvessel.hull = 0;
                                targetvessel.isdead = true;
                                System.out.println("Target Neutralised");

                            } else if (targetvessel.hull >= dmg) { //Damages hull

                                System.out.println(targetvessel.hull);
                                targetvessel.hull = (int) (targetvessel.hull - (dmg * 1.10));
                                System.out.println(targetvessel.hull);

                                System.out.println("We've hit their hull.");


                            }
                        } else if (targetvessel.fields >= dmg) { //Does shield damage

                            System.out.println(targetvessel.fields);
                            targetvessel.fields = targetvessel.fields - dmg;
                            System.out.println(targetvessel.fields);

                            System.out.println("We've hit their energy barriers.");

                        }
                        break;
                    }
                }


                if (playerchoice == 3) {//Subweapons are devestating...

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
                        System.out.println("Damage: " + dmg);
                        if (playervessel.crewtasks == 1) {
                            dmg *= 1.25;
                            System.out.println("NewDamage: " + dmg);
                        }


                        if (targetvessel.fields <= dmg) {//Checks if shields can be brought down

                            System.out.println(targetvessel.fields);
                            dmg = dmg - targetvessel.fields;
                            targetvessel.fields = 0;

                            if (targetvessel.hull <= dmg) { //checks if ship is killable

                                dmg = 0;
                                targetvessel.hull = 0;
                                targetvessel.isdead = true;
                                System.out.println("Target Neutralised");

                            } else if (targetvessel.hull >= dmg) { //Damages hull

                                System.out.println(targetvessel.hull);
                                targetvessel.hull = (int) (targetvessel.hull - (dmg * 1.10));
                                System.out.println(targetvessel.hull);

                                System.out.println("We've hit their hull.");


                            }
                        } else if (targetvessel.fields >= dmg) { //Does shield damage

                            System.out.println(targetvessel.fields);
                            targetvessel.fields = targetvessel.fields - dmg;
                            System.out.println(targetvessel.fields);

                            System.out.println("We've hit their energy barriers.");

                        }
                        break;
                    }
                }
            }
        }
    }
}