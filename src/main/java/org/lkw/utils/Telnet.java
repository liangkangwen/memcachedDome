package org.lkw.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Telnet {
	
    public static void main(String args[]) {
        String[] keys = allkeys("192.168.206.128", 11211,null);
        Arrays.sort(keys);
        for(String s : keys){
            System.out.println(s);
        }
        System.out.println(telnet("192.168.206.128", 11211, "stats"));
    }
    
    public static String[] allkeys(String host, int port, SocketAddress socketAddress){
        StringBuffer r = new StringBuffer();
        try {
        	Socket socket = null;
        	if (host != null) {
        		socket = new Socket(host, port);
        	} else {
        		socket = new Socket();
        		socket.connect(socketAddress);
        	}
            PrintWriter os = new PrintWriter(socket.getOutputStream());
            BufferedReader is = new BufferedReader(new InputStreamReader( socket.getInputStream()));
            os.println("stats items");
            os.flush();
            String l ;
            while (!(l = is.readLine()).equals("END")) {
                r.append(l).append("\n");
            }
            String rr = r.toString();
            Set<String> ids = new HashSet<String>();
            if(rr.length() > 0){
                r = new StringBuffer();//items 
                rr.replace("STAT items", "");
                for(String s : rr.split("\n")){
                    ids.add(s.split(":")[1]);
                }
                if (ids.size() > 0){
                    r = new StringBuffer();//
                    for(String s : ids){
                        os.println("stats cachedump "+ s +" 0");
                        os.flush();
                        while (!(l = is.readLine()).equals("END")) {
                            r.append(l.split(" ")[1]).append("\n");
                        }
                    }
                }
            }
             
            os.close();
            is.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        return r.toString().split("\n");
    }
    
    public static String telnet(String host, int port, String cmd){
        StringBuffer r = new StringBuffer();
        try {
            Socket socket = new Socket(host, port);
            PrintWriter os = new PrintWriter(socket.getOutputStream());
            BufferedReader is = new BufferedReader(new InputStreamReader( socket.getInputStream()));
            os.println(cmd);
            os.flush();
            String l ;
            while (!(l = is.readLine()).equals("END")) {
                r.append(l).append("\n");
            }
            os.close();
            is.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        return r.toString();
    }
    
    public static String[] allkeys(SocketAddress socketAddress) {
    	return allkeys(null,0,socketAddress);
    }
}
