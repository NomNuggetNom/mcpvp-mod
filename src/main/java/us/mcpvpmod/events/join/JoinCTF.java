package us.mcpvpmod.events.join;

import us.mcpvpmod.events.chat.ChatCTF;

public class JoinCTF {

	public static void onJoin() {
		ChatCTF.chatBlock.clear();
		ChatCTF.oldChat.clear();
	}
	
}
