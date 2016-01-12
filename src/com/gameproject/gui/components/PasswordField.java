package com.gameproject.gui.components;

import com.gameproject.net.Client;
import com.gameproject.net.Packet;

public class PasswordField extends ActiveComponent{

	private TextField username;
	private String text = "";
	private String textShown = "";
	
	public PasswordField(TextField username, String tex, float x, float y, int width, int height) {
		super(tex, x, y, width, height);
		this.username = username;
	}


	@Override
	public void update() {
		if(isSelected()){
			selected();
		}
	}

	@Override
	public void selected() {
		
	}

	@Override
	public void activate() {
		
		Client.sendPacket(Packet.LOGIN_PACKET_ID, (username.getText() + "&" + text).getBytes());
		text = "";
		textShown = "";
		
	}

	@Override
	public void clicked() {
		setSelected(true);
	}

}
