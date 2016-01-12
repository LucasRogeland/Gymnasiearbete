package com.gameproject.net;

import java.net.DatagramPacket;
import java.net.InetAddress;

public abstract class Packet {
	
	public static final byte GAME_VERIFY_ID = 0b1101001;
	
	public static final byte LOGIN_PACKET_ID   = 0b00000000;
	public static final byte LOGOUT_PACKET_ID  = 0b00000001;
	public static final byte MESSAGE_PACKET_ID = 0b00000010;
	public static final byte SEARCHM_PACKET_ID = 0b00000011;
	public static final byte FOUNDM_PACKET_ID  = 0b00000100;
	public static final byte GAMEDAT_PACKET_ID = 0b00000101;
	public static final byte NOTIFY_PACKET_ID  = 0b00000110;
	
	public abstract void handlePacket(DatagramPacket packet);
	
	public abstract DatagramPacket constructPacket(byte packetType,byte[] data, InetAddress ip, int port);
	
	public byte[] getSubData(byte[] inData, int start, int length){
		byte[] outData = new byte[length];
		for(int i = 0; i < length; i++){
			outData[i] = inData[start + i];
		}
		return outData;
	}
}
