package jsettlers.graphics.startscreen.interfaces;

/**
 * This is a list item of players that joined or can join the game.
 * 
 * @author michael
 * @author Andreas Eberle
 */
public interface IMultiplayerPlayer {

	/**
	 * @return Returns the id of the player.
	 */
	public String getId();

	/**
	 * Gets the name of the player, may return null if the channel is free.
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * If the player is ready for start.
	 * 
	 * @return
	 */
	public boolean isReady();

	/* getTeam() */
}
