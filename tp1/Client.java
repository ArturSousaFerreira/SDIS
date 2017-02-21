import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Scanner;
import java.net.Socket;

/**
 * Created by Utilizador on 16/02/2017.
 */
public class Client {

    private Socket clientSocket;
    private Scanner scan = new Scanner(System.in);
    private PrintStream output;
    private BufferedReader input;

    public static void main(String args[]) throws IOException {

        Client client = new Client();
        client.run();
    }

    public void run() throws IOException {

        String sendtoserver, receivefromserver;

        clientSocket = new Socket("127.0.0.1", 1234); // criar socket com ip e porta

        Scanner scan2 = new Scanner(clientSocket.getInputStream());

        System.out.println("REGISTER(XX-XX-XX - NOME COMPLETO)/LOOKUP(XX-XX-XX):");
        sendtoserver = scan.nextLine();

        output = new PrintStream(clientSocket.getOutputStream());
        output.println(sendtoserver);

        //aceitar o resultado do user
        if ((receivefromserver = scan2.nextLine()) != null)
            System.out.println(receivefromserver);


    }

}
