package com.gameproject;

import com.gameproject.net.Client;
import com.gameproject.net.Server;

import static com.gameproject.util.Devutil.*;

import javax.swing.JOptionPane;

import com.gameproject.game.Game;

@SuppressWarnings("unused")
public class Main {

	
	public Main(){
		String username = JOptionPane.showInputDialog("Username: ");
		new Thread(new Runnable(){
	
			@Override
			public void run(){
				Server.start();
	
			}
		}).start();
		
		Game g = new Game();
		g.start();
	}
	
	public static void main(String[] args){
		
		Main main = new Main();
		System.out.println("poop");
	}
	
	
}
