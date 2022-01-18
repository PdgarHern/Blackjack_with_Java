package cliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
    final static int PORT = 40080;
    final static String HOST = "localhost";
    public static void main(String[] args) {

        try {
            Socket sk = new Socket(HOST, PORT);
            
            enviarMensajesAlServidor(sk);

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void enviarMensajesAlServidor(Socket sk) {
        OutputStream os = null;
        InputStream is = null;

        try {
            os = sk.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            
            is = sk.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            Scanner sc = new Scanner(System.in);
            String linea;

            while(true){
                System.out.println("Pulsa 'S' para empezar.");
                linea = sc.nextLine();
                bw.write(linea);
                bw.newLine();
                bw.flush();

                linea = br.readLine();

                System.out.println(linea);

                String[] carts = linea.split(" ");

                Integer c = Integer.parseInt(carts[1]);
                Integer u = Integer.parseInt(carts[4]);

                if (carts[carts.length - 1].equals("END")) {
                    if ((c > u && c <= 21) || u > 21) {
                        System.out.println("LOSE");
                    } else if (c > 21) {
                        System.out.println("VICTORY");
                    } else if (c == u) {
                        System.out.println("DRAW");
                    } else {
                        System.out.println("VICTORY");
                    }

                } else {
                    System.out.println("¿Pedir carta? (S/N): ");
                    linea = sc.nextLine();

                    if (linea.toUpperCase().equals("S")) {
                        bw.write(linea);
                        bw.newLine();
                        bw.flush();

                        linea = br.readLine();

                        String[] carts2 = linea.split(" ");

                        Integer c2 = Integer.parseInt(carts2[1]);
                        Integer u2 = Integer.parseInt(carts2[4]);

                        System.out.println(linea);

                        if ((c2 > u2 && c2 <= 21) || u2 > 21) {
                            System.out.println("LOSE");
                        } else if (c2 == u2) {
                            System.out.println("DRAW");
                        } else {
                            System.out.println("VICTORY");
                        }

                    } else {
                        bw.write(linea);
                        bw.newLine();
                        bw.flush();

                        linea = br.readLine();

                        String[] carts2 = linea.split(" ");

                        Integer c2 = Integer.parseInt(carts2[1]);
                        Integer u2 = Integer.parseInt(carts2[4]);

                        System.out.println(linea);

                        if ((c2 > u2 && c2 <= 21) || u2 > 21) {
                            System.out.println("LOSE");
                        } else if (c2 == u2) {
                            System.out.println("DRAW");
                        } else {
                            System.out.println("VICTORY");
                        }

                    }

                }

            }

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(os != null) os.close();
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
