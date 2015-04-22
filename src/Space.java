
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

//
public class Space {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {

        List<Formation> formations = new ArrayList<>();
        List<Vessel> VesselList = new ArrayList<>();
        Random rand = new Random();

        int firstrun = 1;
        Scanner fin = new Scanner(new FileReader("shiptypes.mid"));
        Scanner sin = new Scanner(System.in);

        Vessel vessel;

        while (firstrun == 1) {
            String text = fin.nextLine();

            if (text.equals(".")) { //Break loop when EoF is reached
                firstrun = 0;
                break;
            }

            //Tear stats from Tankdefs


            vessel = new Vessel();
            vessel.name = text.split(",")[0];
            vessel.ORFaction = text.split(",")[1];
            vessel.hulllimit = Integer.parseInt(text.split(",")[2]);
            vessel.crewlimit = Integer.parseInt(text.split(",")[4]);
            vessel.marinelimit = Integer.parseInt(text.split(",")[5]);
            vessel.powerlimit = Integer.parseInt(text.split(",")[6]);
            vessel.powergen = Integer.parseInt(text.split(",")[7]);
            vessel.fieldlimit = Integer.parseInt(text.split(",")[8]);
            vessel.speed = Integer.parseInt(text.split(",")[9]);
            vessel.beams = Integer.parseInt(text.split(",")[9]);
            vessel.rails = Integer.parseInt(text.split(",")[10]);
            vessel.subweapons = Integer.parseInt(text.split(",")[11]);
            VesselList.add(vessel);
        }

        //print stats
        for (int i = 0; i < VesselList.size(); i++) {
            vessel = VesselList.get(i);

            System.out.println(i + "{" + vessel.name + "} Faction:" + "[" + vessel.ORFaction + "]" + " Crew Compliment " + vessel.crewlimit + " Max Velocity(in open space):" + vessel.speed);
        }

        System.out.println("Create formations?");

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

                        System.out.println("Set starting requisition");
                        formation.requisition = Integer.parseInt(sin.nextLine());

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

                        vessel = new Vessel();

                        //Print lists of vessels

                        for (i = 0; i < VesselList.size(); i++) {
                            vessel = VesselList.get(i);

                            System.out.println(i + "{" + vessel.name + "} Faction:" + "[" + vessel.ORFaction + "]" + " Crew Compliment " + vessel.crewlimit + " Max Velocity(in open space):" + vessel.speed);
                        }

                        System.out.println("Choose vessel");

                        vessel = VesselList.get(Integer.parseInt(sin.nextLine()));


                        System.out.println("{" + vessel.name + "} Faction:" + "[" + vessel.ORFaction + "]" + " Crew Compliment " + vessel.crewlimit + " Max Velocity(in open space):" + vessel.speed);
                        System.out.println("Has been added to");
                        System.out.println(formation.name);


                        formation.ships.add(vessel);

                        break;
                }
            }
        }
        for (int i = 0; i < formations.size(); i++) {

            System.out.println("{" + i + "} " + formations.get(i).name);


            for (int j = 0; j < formations.get(i).ships.size(); j++) {

                System.out.println("{" + j + "} " + formations.get(i).ships.get(j).name + " " + formations.get(i).ships.get(j).hull);



            }
        }

        //Combat sorta.

        while (true) {

            System.out.println("Choose your formation");

            int i = Integer.parseInt(sin.nextLine());
            formations.get(i).isplayer = true;

            System.out.println("Choose your ship");

            int j = Integer.parseInt(sin.nextLine());
            formations.get(i).ships.get(j).isplayer = true;
            Vessel playervessel = formations.get(i).ships.get(j);

            System.out.println("Readying vessel");
            System.out.println("");
            System.out.println("SITREP");

            System.out.println(playervessel.name + ":");
            System.out.println("Faction: " + playervessel.ORFaction);
            System.out.println("Crewmembers onboard: " + playervessel.crew);
            System.out.println("Boarding teams: " + playervessel.marines);
            System.out.println("Structural integrity: " + playervessel.hull + "/" + playervessel.hulllimit);
            System.out.println("Field integrity: " + playervessel.fields + "/" + playervessel.fieldlimit);
            System.out.println("Power available" + playervessel.power + "/" + playervessel.powerlimit + " Generating:" + playervessel.powergen);





        }
    }
}