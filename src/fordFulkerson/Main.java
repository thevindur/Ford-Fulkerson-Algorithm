package fordFulkerson;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private final static Files fileNames = new Files();
    public static void main(String[] args) {
        String main = null;
        do{
            try {
                int userInput;
                String fileName;
                String filePath;
                Scanner fileSc = null;

                /*---------------------------------------------------------

                 *  Printing the CLI Menu and reading the file accordingly.

                 *---------------------------------------------------------*/

                Scanner sc = new Scanner(System.in);
                System.out.println("************ Ford Fulkerson Algorithm **************\n");
                System.out.println("Following Options are Available:");
                System.out.println("\t1. Run with the File mentioned in CLI arguments.");
                System.out.println("\t2. Run with the Files provided for Benchmarks.");
                System.out.println("\t3. Run with a file Added by you for Testing.\n");
                System.out.print("Please Select an Option : ");
                userInput = sc.nextInt();

                if (userInput == 1) {
                    //Accessing the file required as an argument.
                    fileSc = new Scanner(new File(args[0]));
                } else if (userInput == 2) {
                    int selectedNo, actualNo;
                    int i = 1;
                    System.out.println("\nFiles Provided for Benchmarks.");
                    for (String name : fileNames.fileNameArray) {
                        if (i < 10) {
                            System.out.println("\t " + i + ". " + name);
                        } else {
                            System.out.println("\t" + i + ". " + name);
                        }
                        i++;
                    }
                    System.out.print("\nPlease Choose a File and Enter the Number :");
                    selectedNo = sc.nextInt();
                    actualNo = selectedNo - 1;
                    fileName = fileNames.fileNameArray.get(actualNo);
                    filePath = "Files/Benchmarks/" + fileName;
                    fileSc = new Scanner(new File(filePath));
                } else if (userInput == 3) {
                    System.out.print("Enter the FileName : ");
                    fileName = sc.next();
                    filePath = "Files/Inputs/" + fileName + ".txt";
                    fileSc = new Scanner(new File(filePath));
                } else {
                    System.out.println("There is no such option Available.");
                }


                /*---------------------------------------------------------

                 *  Adding the relevant nodes and displaying the output.

                 *---------------------------------------------------------*/

                //Getting the number of nodes from the file and printing it.
                int nodes = 0;
                if (fileSc != null) nodes = fileSc.nextInt();
                System.out.println("\nNumber of Nodes : " + nodes + "\n");
                int sourceNode = 0;     //Source node of the graph.
                int targetNode = nodes - 1;     //Target node of the graph

                FordFulkerson solver = new FordFulkerson(nodes, sourceNode, targetNode);

                if (fileSc != null) while (fileSc.hasNext()) {
                    int a = fileSc.nextInt();
                    int b = fileSc.nextInt();
                    int c = fileSc.nextInt();
                    solver.addEdge(a, b, c);
                }

                System.out.println("Maximum Flow is : " + solver.getMaxFlow() + "\n");

                List<Edge>[] resultGraph = solver.getGraph();

                // Displays all edges part of the resulting residual graph.
                for (List<Edge> edges : resultGraph)
                    for (Edge e : edges) System.out.println(e.toString(sourceNode, targetNode));

                if (fileSc != null) fileSc.close();

                System.out.print("\nEnter 'R' to run again or Enter 'Q' to Exit : ");
                main = sc.next();
            } catch (Exception e) {
                System.out.println("Invalid Input !");
            }
        }while (Objects.requireNonNull(main).equalsIgnoreCase("r"));
    }
}
