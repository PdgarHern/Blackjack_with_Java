package servidor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloParaAntenderUnCliente extends Thread{
    Socket sk;
    public HiloParaAntenderUnCliente(Socket sk){
        this.sk = sk;
    }

    @Override
    public void run() {
        InputStream is = null;
        OutputStream os = null;

        int[] cartValues = new int []{11,2,3,4,5,6,7,8,9,10,10,10,10};


        try {
            is = sk.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            os = sk.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            
            Inet4Address ip = (Inet4Address) sk.getInetAddress();
            String laIP = ip.getHostAddress();
            
            while(true){
                System.out.println("Esperando algo");
                String linea = br.readLine();


                System.out.println(laIP +": " + linea);
                if (linea.toUpperCase().equals("S")) {
                    int num11 = (int) (Math.random() * 13 + 1);
                    int num21 = (int) (Math.random() * 13 + 1);
                    int num12 = (int) (Math.random() * 13 + 1);
                    int num22 = (int) (Math.random() * 13 + 1);

                    int crupier = cartValues[num11-1] + cartValues[num12-1];
                    int user = cartValues[num21-1] + cartValues[num22-1];

                    if (crupier == 21 || user == 21) {
                        bw.write("Crupier: "+crupier+" | User: "+user+" END");
                        bw.newLine();
                        bw.flush();

                    } else {
                        if (crupier < 14) {
                            int num1e = (int) (Math.random() * 13 + 1);
                            crupier += cartValues[num1e-1];
                        }

                        if (crupier >= 21) {
                            bw.write("Crupier: "+crupier+" | User: "+user+" END");
                            bw.newLine();
                            bw.flush();

                        } else {
                            bw.write("Crupier: "+cartValues[num11-1]+" | User: "+user);
                            bw.newLine();
                            bw.flush();

                            System.out.println("Esperando algo");
                            String linea2 = br.readLine();


                            System.out.println(laIP +": " + linea2);

                            if (linea2.toUpperCase().equals("S")) {

                                int num2e = (int) (Math.random() * 13 + 1);
                                user += cartValues[num2e-1];

                                bw.write("Crupier: "+crupier+" | User: "+user+" END");
                                bw.newLine();
                                bw.flush();

                            } else {
                                bw.write("Crupier: "+crupier+" | User: "+user+" END");
                                bw.newLine();
                                bw.flush();

                            }

                        }

                    }

                } else {
                    bw.write("Ok");
                    bw.newLine();
                    bw.flush();

                }

            }
            
        } catch (IOException ex) {
            Logger.getLogger(HiloParaAntenderUnCliente.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(is != null) is.close();

            } catch (IOException ex) {
                Logger.getLogger(HiloParaAntenderUnCliente.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
    }
    
    
}
