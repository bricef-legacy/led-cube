package core;

public interface OpCodes {
	/**
	 * OPCODES form PC to ARDUINO
	 */
	public final static int  INIT_IS_READY = 224;
	public final static int BEGIN_LAYER = 160;
	public final static int END_LAYER = 176;
	public final static int BEGIN_CUBE = 192;
	public final static int END_CUBE = 208;

	/**
	 * OPCODES from ARDUINO to PC
	 */
	public final static int LAYER_ACK_SUCCESS = 160;
	public final static int LAYER_ACK_FAIL = 176;
	public final static int INIT_OK = 240;
	public final static int CUBE_SUCCESS = 144;
	public final static int ERROR = 255;
}
