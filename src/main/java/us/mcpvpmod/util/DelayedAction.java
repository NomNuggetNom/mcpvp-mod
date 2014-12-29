package us.mcpvpmod.util;

import us.mcpvpmod.Main;

public class DelayedAction {

	/** Will be executed after the delay. */
	public void execute() {}
	
	/**
	 * Starts a new thread which will eventually call {@link #execute()}.
	 * @param milli The amount of time in milliseconds to sleep before executing.
	 */
	public void executeIn(final long milli) {
		new Thread("DelayedAction") {
			@Override
			public void run() {
				try {
					Thread.currentThread().sleep(milli);
					execute();
				} catch (InterruptedException e) {
					Main.w("Error executing DelayedAction!");
					e.printStackTrace();
				}
			}
		}.start();
	}
	
}
