
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
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {

        List<Formation> formations = new ArrayList<>();
        List<Vessel> VesselList = new ArrayList<>();
        Random rand = new Random();

        int firstrun = 1;
        int playerchoice;
        Scanner fin = new Scanner(new FileReader("shiptypes.mid"));
        Scanner sin = new Scanner(System.in);

        Vessel vessel;

        while (firstrun == 1) {
            String text = fin.nextLine();

            if (text.equals(".")) { //Break loop when EoF is reached

                firstrun = 0; //Stops the game from trying to load vessels 
                break;        //after is has done so already

            }

            //Tear stats from Tankdefs


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
                        System.out.println("Create another team?[y/n]");

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

                        Vessel vesselTBA;
                        vesselTBA = VesselList.get(Integer.parseInt(sin.nextLine()));

                        //Set starting resources for each ship

                        //HERE LIES THE ISSUE!!@!1! I AM OF MASTER DEBUG!
                        //int n = vesselTBA.fieldlimit;
                        //vesselTBA.crew = vesselTBA.crewlimit;
                        //vesselTBA.power = vesselTBA.powerlimit;
                        //vesselTBA.marines = vesselTBA.marinelimit;
                        //vesselTBA.hull = vesselTBA.hulllimit;
                        //vesselTBA.fields = 256+i;
                        //vesselTBA.currentformation = i;
                        //vesselTBA.isdead = false;


                        System.out.println("{" + vesselTBA.name + "} Faction:" + "[" + vesselTBA.ORFaction + "]" + " Crew Compliment " + vesselTBA.crewlimit + " Max Velocity(in open space):" + vesselTBA.speed);
                        System.out.println("Has been added to " + formation.name);
                        Vessel Joe = new Vessel();
                        Joe = vesselTBA;
                        formation.ships.add(Joe);

                        break;
                }
            }
        }
        for (int i = 0; i < formations.size(); i++) {

            System.out.println("{" + i + "} " + formations.get(i).name);


            for (int j = 0; j < formations.get(i).ships.size(); j++) {

                System.out.println("    " + "{" + j + "} " + formations.get(i).ships.get(j).name);

            }
        }

        //Vessel select
        //Will be used later to define player-controlled ships for psudo-multiplayer.

        System.out.println("Choose your formation");
        int i = Integer.parseInt(sin.nextLine());

        System.out.println("Choose your ship");
        int j = Integer.parseInt(sin.nextLine());
        formations.get(i).ships.get(j).isplayer = true;
        Vessel playervessel = formations.get(i).ships.get(j);

        System.out.println("Readying vessel");
        System.out.println("");

        while (true) { //turnloop, goes through all ships.

            while (true) { //Player control.
                System.out.println("Input Commands:");
                System.out.println("");
                System.out.println("[1] Status report");
                System.out.println("[2] Hostile actions");

                playerchoice = Integer.parseInt(sin.nextLine());


                if (playerchoice == 1) { //Status report

                    printVessel(playervessel);
                    for (i = 0; i < VesselList.size(); i++) {
                        vessel = VesselList.get(i);
                        System.out.println(i + " " + vessel.name + "}" + vessel.fields + "/" + vessel.fieldlimit);
                    }

                }
                if (playerchoice == 2) { //Attack command

                    printformations(formations);
                    i = Integer.parseInt(sin.nextLine());
                    printvessels(formations.get(i));
                    j = Integer.parseInt(sin.nextLine());

                    Vessel targetvessel;
                    targetvessel = formations.get(i).ships.get(j);
                    attack(playervessel, targetvessel);


                    System.out.println("Press Enter to continue");// End turn
                    if (sin.nextLine() != null) {
                        break;
                    }
                }
            }
        }
    }

    public static void listFNV(List<Formation> formations) {

        for (int i = 0; i < formations.size(); i++) {

            System.out.println("{" + i + "} " + formations.get(i).name);


            for (int j = 0; j < formations.get(i).ships.size(); j++) {

                System.out.println("    " + "{" + j + "} " + formations.get(i).ships.get(j).name);

            }
        }
    }

    public static void printformations(List<Formation> formations) {

        for (int i = 0; i < formations.size(); i++) {

            System.out.println("{" + i + "} " + formations.get(i).name);

        }
    }

    public static void printvessels(Formation formation) {

        for (int i = 0; i < formation.ships.size(); i++) {

            System.out.println("{" + i + "} " + formation.ships.get(i).name);

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
    }

    public static void attack(Vessel playervessel, Vessel targetvessel) {
        Scanner sin = new Scanner(System.in);
        int playerchoice;
        int dmg;
        Random rand = new Random();

        if (playervessel.isplayer = true) {

            System.out.println("Attack " + targetvessel.name + " with");

            //Flavour text
            if (playervessel.faction == 1) { //Earth forces

                System.out.println("[1] Beamgun turrets" + "(" + playervessel.beams + ")");
                System.out.println("[2] Railguns" + "(" + playervessel.rails + ")");
                System.out.println("[3] Torpedoes" + "(" + playervessel.subweapons + ")");
            } else if (playervessel.faction == 2) { //Midyian Sovereignty

                System.out.println("[1] Phase Cannons" + "(" + playervessel.beams + ")");
                System.out.println("[2] Phaserail Turrets" + "(" + playervessel.rails + ")");
                System.out.println("[3] Beamgun Coil" + "(" + playervessel.subweapons + ")");
            } else if (playervessel.faction == 3) { //Midyian Conglomerate

                System.out.println("[1] Pulse Hardpoints" + "(" + playervessel.beams + ")");
                System.out.println("[2] Mass Cannons" + "(" + playervessel.rails + ")");
                System.out.println("[3] Rocket arrays" + "(" + playervessel.subweapons + ")");
            }

            playerchoice = Integer.parseInt(sin.nextLine());

            if (playerchoice == 1) {

                //Damage Calc
                int base = playervessel.beams * 50;
                int percent = (int) (base * 0.25);
                double randplusminus = (1 - (rand.nextDouble() * 2)); //Determines whether damage will be + or - 25% of base
                dmg = base + (int) (percent * randplusminus);

                System.out.println("Damage: " + dmg);


                if (targetvessel.fields <= dmg) {

                    System.out.println(targetvessel.fields);
                    dmg = dmg - targetvessel.fields;
                    System.out.println("Damage Remaining: " + dmg);
                    targetvessel.fields = 0;

                    if (targetvessel.hull <= dmg) { //checks if ship is killable

                        dmg = 0;
                        targetvessel.hull = 0;
                        targetvessel.isdead = true;
                        System.out.println("Got 'em!");

                    } else if (targetvessel.hull >= dmg) { //checks if ship is killable

                        System.out.println(targetvessel.hull);
                        targetvessel.hull = targetvessel.hull - dmg;
                        System.out.println(targetvessel.hull);

                        System.out.println("We've hit their hull.");

                    }
                } else if (targetvessel.fields >= dmg) { //Does shield damage

                    System.out.println(targetvessel.fields);
                    targetvessel.fields = targetvessel.fields - dmg;
                    System.out.println(targetvessel.fields);

                    System.out.println("We've hit their energy barriers.");

                }
            }

            if (playerchoice == 2) {

                System.out.println(targetvessel.hull);
                targetvessel.hull = targetvessel.hull - playervessel.rails;
                System.out.println(targetvessel.hull);

            }
            if (playerchoice == 3) {

                System.out.println(targetvessel.hull);
                targetvessel.hull = targetvessel.hull - playervessel.subweapons;
                System.out.println(targetvessel.hull);

            }

        } else { // TODO AI actions
        }
    }
}