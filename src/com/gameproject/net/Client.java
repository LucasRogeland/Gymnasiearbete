package com.gameproject.net;

import static com.gameproject.util.Devutil.err;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JOptionPane;

public class Client {

	private static DatagramSocket socket;
	private static byte lastSentPacket;
	private static InetAddress serverIP;
	private static int serverPort;
	private static boolean closeRequested;
	private static Account account;
	public static PacketClient packetHandler;
	
	public static void start(){
		
		init();
		run();
	}
	
	//Create a new thread that handles server input
	private static void run(){
		sendPacket(Packet.LOGIN_PACKET_ID, "Gustav&bt78".getBytes());
		new Thread(new Runnable(){
			@Override
			public void run(){
				while (!closeRequested){
					receivePacket();
				}
			}
		}).start();
	}
	
	//Initialize the client
	private static void init(){
		
		try {
			socket = new DatagramSocket(0);
			lastSentPacket = 0;
			serverIP = InetAddress.getByName("127.0.0.1");
			serverPort = 3343;
			String username = JOptionPane.showInputDialog("Username: "); // Error code: 66 Corresponding to error in game line 44 Linux error only!! 
		} catch (Exception e) {
			System.exit(-1);
			System.out.println("Failed to init client");
		}
		packetHandler = new PacketClient();
	}
	
	//Wait for a packet to arrive
	private static void receivePacket(){
		byte[] data = new byte[256];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		
		try {
			socket.receive(packet);
			handlePacket(packet);
		} catch (IOException e) {
			err("Failed to receive a packet");
		}
	}
	
	//Create a new thread to handle a packet
	private static void handlePacket(DatagramPacket packet){
		
		new Thread(new Runnable(){
			@Override
			public void run(){
				packetHandler.handlePacket(packet);
			}
		}).start();

	}
	
	//Send a packet to the server
	public static void sendPacket(byte packetType, byte[] data){
		try {
			socket.send(packetHandler.constructPacket(packetType,  data, serverIP, serverPort));
			lastSentPacket = getNextPacketID();			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Return the username of the current logged in user
	public static byte[] getUsername(){
		return account.getUsername().getBytes();
	}
	
	//Get the packet id of the next packet sent
	public static byte getNextPacketID(){
		if(lastSentPacket == 0b1111111)
			return 0b00000001;
		return (byte)(lastSentPacket + 0b00000001);
	}
	
	//Request the client to be closed
	public static void requestClose(){
		closeRequested = true;
	}
	
}
