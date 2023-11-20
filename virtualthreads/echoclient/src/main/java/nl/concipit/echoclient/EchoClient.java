package nl.concipit.echoclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Echo client as defined on <a href="https://docs.oracle.com/en/java/javase/21/core/virtual-threads.html#">Oracle docs</a>
 */
public class EchoClient {

    /**
     * Main application
     *
     * @param args 0: hostname, 1: Port number
     * @throws IOException Thrown if something goes wrong
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: java EchoClient <hostname> <port>");
            System.exit(1);
        }
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        try (Socket echoSocket = new Socket(hostName, portNumber); PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()))) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("echo: " + in.readLine());
                if (userInput.equals("bye")) break;
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}