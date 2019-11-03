import java.io.*;
import java.net.*;

public class imageLabeler {
    private Socket clientSocket;
    private DataOutputStream out;
    private BufferedReader in;
    private String username = "bilkentstu";
    private String pass = "cs421f2019";
    private String newLine = "\r\n";


    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String msg) throws IOException {
        out.flush();
        out.writeBytes(msg);
        //out.println(msg);
        String resp = in.readLine();
        return resp;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public String[] send_iget(String msg) throws IOException {
        out.writeBytes( msg);
        String resps[];
        resps = new String[3];
        for(int i = 0; i < 3; i++){
            resps[i] = in.readLine();
        }
        return resps;


    }

    public static void main(String[] args) throws IOException {
        imageLabeler client = new imageLabeler();
        client.startConnection("127.0.0.1", 6666);
        String resps[] = new String[3];

        String response = client.sendMessage("USER "+client.username+client.newLine);
        System.out.println("Response: " + response);

        response = client.sendMessage("PASS "+client.pass+client.newLine);
        System.out.println("Response: " + response);

        //todo neden çalışmıyo amk ?_? encoding dene
        resps = client.send_iget("IGET"+client.newLine);
        for(int i = 0; i<3;i++){
            System.out.println("Response["+i+"]: " + resps[i]);
        }
        String[] labels = new String[3];
        //todo label and send accurate labels

        response = client.sendMessage("ILBL " +"cat,dog,bear"+client.newLine);
        System.out.println("Response: " + response);


        client.stopConnection();
    }
}