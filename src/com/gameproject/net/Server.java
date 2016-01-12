package com.gameproject.net;

import static com.gameproject.util.Devutil.err;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

public class Server{
	
	private static DatagramSocket socket;
	private static DatabaseConnection db;
	private static boolean closeRequested;
	private static Map<String, Connection> connections;
	public static PacketServer packetHandler;
	
	//Creates a new server
	public static void start() {
		try {
			init();
			run();
		} catch (IOException e) {
			err("Failed to create server :(");
		}
	}
	
	//Run the server until requestClose is called
	private static void run(){
		System.out.println(closeRequested);
		while(!closeRequested){
			receivePacket();
		}
		cleanUp();
	}
	
	//Initializes the server and asks for the port
	private static void init() throws IOException{
		closeRequested = false;
		db = new DatabaseConnection("jdbc:mysql://localhost:3306/game", "root", "password");
		int port = 0;
		try{
			port = Integer.parseInt(JOptionPane.showInputDialog("Server Port"));
		}catch(Exception e){
			requestClose();
		}
			
		socket = new DatagramSocket(port);
		connections = new HashMap<>();
		packetHandler = new PacketServer();
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
	
	//Sends a packet to a client
	public static void sendPacket(byte packetType,byte[] data, Connection conn){
		try {
			socket.send(packetHandler.constructPacket(packetType, data, conn));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Sends a packet to a group of clients
	public static void sendPacket(byte packetType ,byte[] data, Connection...connections){
	
		for(Connection conn : connections){
			DatagramPacket packet = packetHandler.constructPacket(packetType, data, conn);
			try {
				socket.send(packet);
			} catch (IOException e) {
				err("Failed to send a packet");
			}
		}
		
	}
	
	//Create a new thread that handles the packet
	private static void handlePacket(DatagramPacket packet){
		
		new Thread(new Runnable(){
			@Override
			public void run(){
				packetHandler.handlePacket(packet);
			}
		}).start();

	}
	
	//Adds a newly connected user to a list of connections
	public static void addConnection(String username, String password, InetAddress ip, int port){
		String s = db.query("SELECT password FROM users WHERE username = \"" + username + "\";").split("\n")[0];
		Connection newConn = new Connection(username, ip, port);
		Connection prevConn;
		
		if(password.equals(s)){
			prevConn = connections.put(username, newConn);
			prevConn.sendPacket(Packet.NOTIFY_PACKET_ID, "Good Bye".getBytes());
		}else{
			System.out.println("Wrong username");
		}
	}
	
	public static Connection getConnection(String username){
		if(connections.containsKey(username))
			return connections.get(username);
		return Connection.INVALID;
	}
	
	public static void removeConnection(String username){
		connections.remove(username);
	}
	
	//Shutdown the server
	public static void requestClose(){
		closeRequested = true;
	}
	
	//Close the channel
	private static void cleanUp(){
		socket.close();
	}
}
