
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriel
 */
public class KombatServerExec implements Runnable{    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Starting...");
        KombatServerExec kse = new KombatServerExec();
        kse.waitForPlayer();        
    }
    
    @Override
    public void run() {
        try {
            while(true){
                Thread.sleep(30);
                if(btR){
                    player.x += SPEED;
                    out.println(player.x + "_" + player.y + "_" 
                        + player.w + "" + player.h);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void waitForPlayer(){
        try {
            player = new Player();
            ServerSocket ss = new ServerSocket(8880);
            Socket s = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);
            
            Thread th = new Thread(this);
            th.start();
            
            String command = "";            
            while(!(command = in.readLine()).equals("exit")){
                if(command.equals("PR_R")){
                    btR = true;
                }
                
                if(command.equals("RE_R")){
                    btR = false;
                }                
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static final int SPEED = 8;
    public Player player;
    boolean btR = false, btL = false, btU = false, btD = false;
    PrintWriter out;   
}
