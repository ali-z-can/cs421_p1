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
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),"US-ASCII") );
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
        DataInputStream in2 = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));


        for(int j = 0; j < 3; j++){

            String resp = "";
            for(int i =0;i<4;i++){
                resp += (char)in.read();
            }

            ////////burada size okunacak

            byte[] data_length = new byte[3];
            for(int m = 0; m<3; m++){
                data_length[m] = (byte)in.read();
            }
            //in2.read(data_length);
            int val = ((data_length[0] & 0xff) << 16) | ((data_length[1] & 0xff) << 8) | (data_length[2] & 0xf)  ;

            ////////olurda size okunursa mesajın geri kalanı burada byte array üzerinden dosyaya yazılacak
            byte[] data = new byte[val];
            in2.read(data);
            System.out.println();


        }

        String resps[];
        resps = new String[3];

        for(int i = 0; i < 3; i++){
            resps[i] = "god help me";
        }
        return resps;


    }

    public static void main(String[] args) throws IOException {
        imageLabeler client = new imageLabeler();
        client.startConnection("127.0.0.1", 60000);
        String resps[] = new String[3];

        String response = client.sendMessage("USER "+client.username+client.newLine);
        System.out.println("Response: " + response);

        response = client.sendMessage("PASS "+client.pass+client.newLine);
        System.out.println("Response: " + response);

        //todo çalışmıyo amk ?_?
        resps = client.send_iget("IGET"+client.newLine);
        for(int i = 0; i<3;i++){
            System.out.println("Response["+i+"]: " + resps[i]);
        }
        String[] labels = new String[3];
        //todo label and send accurate labels

        response = client.sendMessage("ILBL " +"cat,dog,bear"+client.newLine);
        System.out.println("Response: " + response);

        response = client.sendMessage("EXIT"+client.newLine);
        System.out.println("Response: " + response);


        client.stopConnection();
    }
}