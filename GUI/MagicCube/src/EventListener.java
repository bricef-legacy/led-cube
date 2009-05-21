
import utils.EventCodes;
import controlP5.ControlEvent;
import controlP5.ControlListener;
import core.UserManager;
import cubeUser.BariumTitanateUser;
import cubeUser.CubeUser;
import cubeUser.IsingUser;
import cubeUser.LoadUser;
import cubeUser.MillerUser;
import cubeUser.CrystalUser;
import cubeUser.SaveUser;
import cubeUser.SerialTalk;

/**
 * This event listener handles all events thrown by the gui. an inner switch statement decides on the 
 * appropriate action to take. This method of handling events is neither clean nor correct, 
 * but is by far the easiest and simplest.
 * <p />
 * Requires access to a UserManager to work properly. All action on User threads are carried out by the Manager.
 * @author Brice
 *
 */
public class EventListener implements ControlListener, EventCodes {
	UserManager manager;
	CubeSimulation sim;
	int tempi,tempj,tempk;
	
	/**
	 * Event listener constructor. Sets internal references.
	 * @param sim The CubeSimulation object creating the listener. needed to get to the simualtion's gui via the CubeSimulation.getGui() method.
	 * @param manager The user manager. All thread actions goes through this manager.
	 * @see CubeSimulation#getGui()
	 * @author Brice
	 */
	public EventListener(CubeSimulation sim, UserManager manager){
		this.manager=manager;
		this.sim=sim;
	}
	
	/**
	 * Event Handler method.
	 * @see controlP5.ControlListener#controlEvent(controlP5.ControlEvent)
	 * @author Brice
	 */
	@Override
	public void controlEvent(ControlEvent event) {
		//System.out.println("[LISTENER]: received event: "+event.toString());
		//System.out.println(event.controller().id());
		switch(event.controller().id()){
			case DEMO_TOGGLE:
				manager.toggleToUser(new CubeUser());
				break;
		
			case MILLER_START_ID:
				try{
					tempi=Integer.decode(this.sim.getGui().getIBox().getText());
					tempj=Integer.decode(this.sim.getGui().getJBox().getText());
					tempk=Integer.decode(this.sim.getGui().getKBox().getText());
				}catch(NumberFormatException e){
					System.out.println("[LISTENER]: Invald format for the miller indices. please enter integers");
					break;
				}
				manager.toggleToUser(new MillerUser(tempi, tempj, tempk));
				break;
			case ISING_SLIDER_ID:
				if(manager.getCurrentUser().getClass().equals(IsingUser.class)){
					((IsingUser)manager.getCurrentUser()).setTemp(event.value());
				}else{
					System.out.println("[LISTENER]: Ising sim not running. Ignoring Ising slider event.");
				}
				break;
			case ISING_START_ID:
				manager.toggleToUser(new IsingUser());
				break;
				
			case CRYSTAL_SC:
				manager.toggleToUser(new CrystalUser(CRYSTAL_SC));
				break;
				
			case CRYSTAL_FCC:
				manager.toggleToUser(new CrystalUser(CRYSTAL_FCC));
				break;
				
			case CRYSTAL_FCC_XL:
				manager.toggleToUser(new CrystalUser(CRYSTAL_FCC_XL));
				break;
				
			case CRYSTAL_BCC:
				manager.toggleToUser(new CrystalUser(CRYSTAL_BCC));
				break;
				
			case CRYSTAL_BCC_XL:
				manager.toggleToUser(new CrystalUser(CRYSTAL_BCC_XL));
				break;
			case CRYSTAL_BARIUM:
				manager.toggleToUser(new BariumTitanateUser());
				break;

				
			case LOAD_BUTTON_ID:
				manager.toggleToUser(new LoadUser());
				break;
				
			case SAVE_BUTTON_ID:
				manager.toggleToUser(new SaveUser());
				break;

			case WRITE_SERIAL:
				manager.startTalker(new SerialTalk("COM4", sim));
				break;
			case WRITE_SERIAL_NO:
				manager.stopTalker();
				break;

		}
		
	}

}
