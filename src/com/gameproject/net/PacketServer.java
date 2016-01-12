package com.gameproject.net;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class PacketServer extends Packet{

	@Override
	public void handlePacket(DatagramPacket packet) {
		
	}
	
	public DatagramPacket constructPacket(byte packetType, byte[] data, Connection conn){
		
		byte[] newData = new byte[data.length + 3];
		newData[0] = Packet.GAME_VERIFY_ID;
		newData[1] = conn.getNextPacketID();
		newData[2] = packetType;
		for(int i = 0; i < data.length; i++){
			newData[3 + i] = data[i];
		}
		
		return new DatagramPacket(newData, newData.length, conn.getIP(), conn.getPort());
		
	}
	
	@Override
	public DatagramPacket constructPacket(byte packetType,byte[] data, InetAddress ip, int port){
		byte[] newData = new byte[data.length + 3];
		newData[0] = Packet.GAME_VERIFY_ID;
		newData[1] = Client.getNextPacketID();
		newData[2] = packetType;
		for(int i = 0; i < data.length; i++){
			newData[3 + i] = data[i];
		}
		return new DatagramPacket(newData, newData.length, ip, port);
	}
	
}
