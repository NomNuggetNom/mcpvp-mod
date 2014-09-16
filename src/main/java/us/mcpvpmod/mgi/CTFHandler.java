package us.mcpvpmod.mgi;

public class CTFHandler implements MGIEventHandler {

	@Override
	public void handle(MGIEvent event) {
		System.out.println("testmessage:" + event.getString("testmessage"));
		System.out.println("junknumber:" + event.getDouble("junknumber"));
	}

}
