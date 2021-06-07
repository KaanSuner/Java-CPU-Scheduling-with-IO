
import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 *
 * @author Kaan Suner
 * @version 1.8.0_111
 */
class Job {

    String ID;
    ArrayList<String> BurstList;

    int CycleTime = 0, WaitTime = 0;

    Job(String ID, ArrayList<String> Burst) {
        this.ID = ID;
        this.BurstList = Burst;
    }
}

public class tp_20160807018 {

    static String input_file_name;
    int TIME = 0;

    ArrayList<Job> Running_Jobs = new ArrayList<>();
    ArrayList<Job> Finished_Jobs = new ArrayList<>();

    public static void main(String[] args) {

        tp_20160807018 object = new tp_20160807018();
        if (args.length == 1) {
            input_file_name = args[0];
            object.readFile(input_file_name);
            object.execute();
            object.output();

        } else {
            System.out.println("Please enter a valid argument!");
            System.out.println("Run format: java tp_20160807018 your_file_name");
            System.exit(0);
        }
    }

    void readFile(String input) {

        try {

            File file = new File(input);
            Scanner inputFile = new Scanner(file);

            while (inputFile.hasNext()) {
                String next = inputFile.next();
                String[] inputs = next.split(":");
                String ID = inputs[0];
                String[] burst_inputs = inputs[1].split(";");
                ArrayList<String> bursts_times = new ArrayList<>();
                bursts_times.addAll(Arrays.asList(burst_inputs));
                Job job = new Job(ID, bursts_times);
                Running_Jobs.add(job);

            }

            inputFile.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Queue.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void execute() {
        for (int i = 0; i < Running_Jobs.size(); i++) {

            String[] burst = Running_Jobs.get(i).BurstList.get(0).split(",");
            int CPU_BURST = Integer.parseInt(burst[0].substring(1));

            int IO_BURST = Integer.parseInt(burst[1]
                    .substring(0, burst[1].length() - 1));

            Running_Jobs.get(i).WaitTime += TIME - Running_Jobs.get(i).CycleTime;

            if (IO_BURST == -1) {
                Running_Jobs.get(i).CycleTime = TIME + CPU_BURST;
                Finished_Jobs.add(Running_Jobs.get(i));
                Running_Jobs.remove(i);
            } else {
                Running_Jobs.get(i).CycleTime = TIME
                        + CPU_BURST + IO_BURST;
                Running_Jobs.get(i).BurstList.remove(0);
            }

            TIME += CPU_BURST;

        }
        while (!Running_Jobs.isEmpty()) {

            for (int i = 0; i < Running_Jobs.size();) {
                if (Running_Jobs.get(i).CycleTime <= TIME) {
                    String[] burst = Running_Jobs.get(i).BurstList.get(0).split(",");
                    int CPU_BURST = Integer.parseInt(burst[0].substring(1));

                    int IO_BURST = Integer.parseInt(burst[1]
                            .substring(0, burst[1].length() - 1));

                    Running_Jobs.get(i).WaitTime += TIME - Running_Jobs.get(i).CycleTime;

                    if (IO_BURST == -1) {
                        Running_Jobs.get(i).CycleTime = TIME + CPU_BURST;
                        Finished_Jobs.add(Running_Jobs.get(i));
                        Running_Jobs.remove(i);
                    } else {
                        Running_Jobs.get(i).CycleTime = TIME
                                + CPU_BURST + IO_BURST;
                        Running_Jobs.get(i).BurstList.remove(0);
                    }
                    TIME += CPU_BURST;

                } else {
                    i++;
                }

            }
            Iterator<Job> it = Running_Jobs.iterator();
            while (it.hasNext()) {
                if (it.next().CycleTime <= TIME) {
                    break;
                } else {
                    TIME++;
                }
            }
        }
    }

    void output() {

        int turnAroundSum = 0;
        int waitingTimeSum = 0;

        for (int i = 0; i < Finished_Jobs.size(); i++) {
            turnAroundSum += Finished_Jobs.get(i).CycleTime;
            waitingTimeSum += Finished_Jobs.get(i).WaitTime;
        }
        double finishedsize = Finished_Jobs.size();
        double AverageTTA = turnAroundSum / finishedsize;
        double AverageWT = waitingTimeSum / finishedsize;

        System.out.println("\nAverage TurnAround Time: "
                + String.format("%.2f", AverageTTA)
                + "\nAverage Waiting Time: "
                + String.format("%.2f", AverageWT));
    }
}
