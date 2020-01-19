import java.io.*;
import java.util.Scanner;

public class Shell {
    static boolean readCommand = false;

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("---------------Welcome to the Process Manager------------");
        Manager manager = new Manager();
//        System.out.println("*Go ahead and enter your file path");
//        Scanner input = new Scanner(System.in);
//        String s = input.next();
        Scanner file;
        BufferedWriter output;
        int count = 1;
        try {
            file = new Scanner(new FileReader("/Users/chanyuehu/Desktop/Untitled.txt"));
            output = new BufferedWriter(new FileWriter("./output.txt"));
            while (file.hasNext()) {
                String s = file.next();
                if (s.equals("in")) {
                    System.out.println("*Reinitializing new manager........");
                    manager = new Manager(); // initialize the system
                    if (count != 1) {
                        output.write('\n');
                    }
                    count++;
                    output.write(String.valueOf(manager.currentP.getPid()));
                    output.write(" ");
                }
                else if (s.equals("to")) {
                    manager.timeout();
                    output.write(String.valueOf(manager.currentP.getPid()));
                    output.write(" ");
                    System.out.println("*Process " + manager.currentP.getPid() + " is running");
                }
                else if (s.equals("cr")) {
                    int priority = file.nextInt();
                    if (priority > 2) {
                        System.out.println("*Priority only can be 0-2");
                        output.write(String.valueOf(-1));
                        output.write('\n');
                        count = 1;
                    }
                    else {
                        if (manager.create(priority) == 0) {
                            output.write(String.valueOf(manager.currentP.getPid()));
                            output.write(" ");
                            System.out.println("*Process " + manager.currentP.getPid() + " is running");
                        }
                        else {
                            output.write(String.valueOf(-1));
                            output.write('\n');
                            count = 1;
                            System.out.println("Error: PCB IS FULL!!!");
                        }
                    }
                }
                else if (s.equals("de")) {
                    int pid = file.nextInt();
                    int result = manager.destroy(pid);
                    if (result == 0) {
                        output.write(String.valueOf(manager.currentP.getPid()));
                        output.write(" ");
                        System.out.println("*Process " + manager.currentP.getPid() + " is running");
                    }
                    else {
                        output.write(String.valueOf(-1));
                        output.write('\n');
                        count = 1;
                    }
                }
                else if (s.equals("rq")) {

                    int resource = file.nextInt();
                    int units = file.nextInt();
                    if(checkPair(resource, units)){
                        if (manager.request(resource, units) == 0){
                            output.write(String.valueOf(manager.currentP.getPid()));
                            output.write(" ");
                            System.out.println("*Process " + manager.currentP.getPid() + " is running");
                        }
                        else{
                            output.write(String.valueOf(-1));
                            output.write('\n');
                            count = 1;
                        }
                    }
                    else {
                        output.write(String.valueOf(-1));
                        output.write('\n');
                        count = 1;
                    }
                }
                else if (s.equals("rl")) {
                    int resource = file.nextInt();
                    int units = file.nextInt();
                    if (checkPair(resource, units)) {
                        if (manager.release(resource, units) == 0) {
                            output.write(String.valueOf(manager.currentP.getPid()));
                            output.write(" ");
                            System.out.println("*Process " + manager.currentP.getPid() + " is running");
                        } else {
                            output.write(String.valueOf(-1));
                            output.write('\n');
                            count = 1;
                        }
                    }
                    else {
                        output.write(String.valueOf(-1));
                        output.write('\n');
                        count = 1;
                    }
                }
                else{
                    continue;
                }
            }
            file.close();
            output.close();
        } catch (IOException e) {
            System.out.println("FileWriting problem: " + e);
        }

    }

    private static boolean checkPair(int resource, int units) {
        if (resource == 0) {
            return units == 1;
        }
        if (resource == 1) {
            return units == 1;
        }
        if (resource == 2) {
            return units < 3 && units > 0;
        }
        if (resource == 3) {
            return units < 4 && units > 0;
        }
        System.out.println("Wrong input (Resource: 0, 1, 2, 3; units: 1, 1, [1,2], [1,2,3]");
        return false;
    }
}
