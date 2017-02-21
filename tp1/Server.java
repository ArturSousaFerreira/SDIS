import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Utilizador on 16/02/2017.
 */
public class Server {

    private ServerSocket serverSocket;
    private Socket acceptSocket;
    private PrintStream output;
    private File file = new File("Registo.txt");


    public static void main(String args[]) throws IOException {

        Server server = new Server();
        server.run();
    }

    public void run() throws IOException {

        String receivefromclient, sendtoclient, line;

        serverSocket = new ServerSocket(1234);
        acceptSocket = serverSocket.accept();
        Scanner sc = new Scanner(acceptSocket.getInputStream());
        receivefromclient = sc.nextLine();

        output = new PrintStream(acceptSocket.getOutputStream());

        file.createNewFile();
        PrintWriter writer = new PrintWriter(new FileWriter(file, true));
        BufferedReader reader = new BufferedReader(new FileReader(file));

        if (receivefromclient.contains("REGISTER") || receivefromclient.contains("register")) {
            boolean exists = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains(receivefromclient.substring(9, 17))) {
                    exists = true;
                    output.println("-> ERROR! This registration plate already exists!");
                }
            }

            if (!exists) {
                writer.write(receivefromclient.substring(9, 17));
                writer.write(receivefromclient.substring(19, receivefromclient.length() - 1));
                writer.println();

                writer.close();
                output.print("-> Registed!!      ");
                output.print("Registration : ");
                output.print(receivefromclient.substring(9, 17));
                output.print(" | Name : ");
                output.print(receivefromclient.substring(19, receivefromclient.length() - 1));
            }

        } else if (receivefromclient.contains("LOOKUP") || receivefromclient.contains("lookup")) {

            while ((line = reader.readLine()) != null) {
                if (line.contains(receivefromclient.substring(7, 15)))
                    output.println("-> Owner : " + line.substring(9, line.length()));
            }

            output.println("Invalid");

        } else {
            output.println("REGISTER(XX-XX-XX - NOME COMPLETO)/LOOKUP(XX-XX-XX):");
        }
    }

}
