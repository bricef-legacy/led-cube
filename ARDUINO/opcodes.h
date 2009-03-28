/**
 * The Opcodes for the serial communication are defined here.
 * The file will eventually also include a macro for filtering the layer bytes for their preamble.
 */


//OPCODES FROM PC TO ARDUINO
#define INIT_IS_READY 224
#define BEGIN_LAYER 160
#define END_LAYER 176
#define BEGIN_CUBE 192
#define END_CUBE 208

//OPCODES FROM ARCDUINO TO PC
#define LAYER_ACK_SUCCESS 160
#define INIT_OK 240
#define LAYER_ACK_FAIL 176
