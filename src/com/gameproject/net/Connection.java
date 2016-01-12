package com.gameproject.net;

import java.net.InetAddress;

public class Connection{
	
	private String username;
	private InetAddress ip;
	private int port;
	private byte lastSentPacket;
	
	public static final Connection INVALID = new Connection("INVALID", new byte[] {0,0}, 0000);
	
	public Connection(String username, InetAddress ip, int port){
		this.username = username;
		this.ip = ip;
		this.port = port;
		com.gameproject.util.Devutil.prnt("Connected " + username + "!");
		lastSentPacket = 0;
	}
	
	public Connection(String username, byte[] ip, int port){
		com.gameproject.util.Devutil.prnt("Connected " + username + "!");
		lastSentPacket = 0;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void sendPacket(byte packetType, byte[] data){
		Server.sendPacket(packetType, data, this);
		lastSentPacket = getNextPacketID();
	}
	
	public byte getNextPacketID(){
		if(lastSentPacket == 0b1111111)
			return 0b00000001;
		return (byte)(lastSentPacket + 0b00000001);
	}
	
	public InetAddress getIP(){
		return ip;
	}
	
	public int getPort(){
		return port;
	}
	
}
