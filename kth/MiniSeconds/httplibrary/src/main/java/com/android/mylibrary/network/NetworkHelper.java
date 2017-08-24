package com.android.mylibrary.network;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkHelper {
    public String GetLocalIPAddress(){
        try {
            
            for( Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();){
                
                NetworkInterface intf = en.nextElement();
                
                for( Enumeration<InetAddress> IpAddr = intf.getInetAddresses();IpAddr.hasMoreElements();) {
                    
                    InetAddress inetAddress = IpAddr.nextElement();
                    
                    if( !inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }                
            }
        } catch(SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
