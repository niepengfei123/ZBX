package com.jy.jz.zbx.dms;

import android.util.Log;

import com.jy.jz.zbx.utils.APPUtils;
import com.jy.jz.zbx.utils.ConfigUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

/**
 * 用于配网成功后发现设备
 *
 * @author Administrator
 */
public class ConfigUdpBroadcast {

    private InetAddress inetAddressbroadcast;
    private DatagramSocket socket;
    private DatagramPacket packetToSendbroadcast;
    private DatagramPacket dataPacket;
    private byte receiveByte[] = new byte[512];
    private String broadCastIP;
    public String TAG = "ConfigUdpBroadcast";
    private ConnectCallBack callback;
    boolean isconncting = false;
    private Set<String> successMacSet = new HashSet<String>();

    public ConfigUdpBroadcast(String broadCastIP, ConnectCallBack callback, boolean isconncting) {
        try {
            // 255.255.255.255
            this.broadCastIP = broadCastIP;
            this.callback = callback;
            this.isconncting = isconncting;
            inetAddressbroadcast = InetAddress.getByName(this.broadCastIP);
            Log.e("inetAddressbroadcast", this.broadCastIP);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void open() {
        try {
            socket = new DatagramSocket();
            socket.setBroadcast(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (socket != null) {
            socket.close();
        }
    }

    public void sendFindCmd() {
        Log.i(TAG, "sendFindCmd");
        packetToSendbroadcast = new DatagramPacket(APPUtils.UDP_FIND_MOUDLE,
                APPUtils.UDP_FIND_MOUDLE.length,
                inetAddressbroadcast, ConfigUtils.udpSendPort);
        try {
            if (packetToSendbroadcast != null && socket != null) {
                socket.send(packetToSendbroadcast);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(byte[] data) {
        packetToSendbroadcast = new DatagramPacket(data, data.length,
                inetAddressbroadcast, ConfigUtils.udpSendPort);
        try {
            if (socket != null) {
                socket.send(packetToSendbroadcast);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receive() {
        dataPacket = new DatagramPacket(receiveByte, receiveByte.length);
        new Thread() {
            public void run() {
                while (isconncting && null != socket) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    try {
                        if (!socket.isClosed()) {
                            socket.receive(dataPacket);
                            int len = dataPacket.getLength();
                            if (len > 0) {
                                processReceivedUpdData(len);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    /**
     * 1:设备wifi连接成功后的回应<br/>
     * 2:设备标示UPD广播回应
     */
    private void processReceivedUpdData(int len) throws IOException {
        byte[] dataZone = new byte[len];
        for (int i = 0; i < len; i++) {
            dataZone[i] = receiveByte[i];
        }

        ModuleInfo mi = new ModuleInfo(dataZone);
//		cc00000100003d802800dd0100365bbbba0517e14e29a4224c0d98232765280000000000000000000000000000000000000000c0a8016aaccf233c3eda05000033000936
        mi.getSn();
        mi.getProductType();
        mi.getIp();
        mi.getMac();
        mi.getToken();
        if (!successMacSet.contains(mi.getSn())) {
            successMacSet.add(mi.getSn());
            callback.onConnect(mi);
        }
    }

    public void stopReceive() {
        Log.e(TAG, "stopReceive");
        isconncting = false;
        successMacSet.clear();
        callback.findFilish();
    }

    public boolean isIsconncting() {
        return isconncting;
    }

    public void setIsconncting(boolean isconncting) {
        this.isconncting = isconncting;
    }


    public interface ConnectCallBack {

        void onConnect(ModuleInfo mi);

        void onConnectTimeOut();

        void onConnectOk();

        void findFilish();
    }
}
