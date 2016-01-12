package com.gameproject.net;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class PacketClient extends Packet{

	@Override
	public void handlePacket(DatagramPacket packet) {
		if(packet.getData()[0] == Packet.NOTIFY_PACKET_ID){
			System.out.println(getSubData(packet.getData(), 3, packet.getLength()));
		}
		
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
