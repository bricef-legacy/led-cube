import controlP5.ControlEvent;
import controlP5.ControlListener;
import core.UserManager;
import cubeUser.CubeUser;
import cubeUser.IsingUser;
import cubeUser.MillerUser;


public class EventListener implements ControlListener, EventCodes {
	UserManager manager;
	CubeSimulation sim;
	int tempi,tempj,tempk;
	
	public EventListener(CubeSimulation sim, UserManager manager){
		this.manager=manager;
		this.sim=sim;
	}
	
	@Override
	public void controlEvent(ControlEvent event) {
		System.out.println("[LISTENER]: received event: "+event.toString());
		if(event.isTab()){
			if(event.tab().id()==HOME_TAB_EVENT){
				System.out.println("[LISTENER]: Returning to hometab, Starting randomiser");
				manager.adduser(new CubeUser(), "Random");
				manager.toggleToUser("Random");
			}
		}
		switch(event.controller().id()){
			case DEMO_TOGGLE:
				manager.adduser(new CubeUser(), "Random");
				manager.toggleToUser("Random");
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
				MillerUser tempUser = new MillerUser(tempi, tempj, tempk);
				manager.adduser(tempUser, "Miller");
				manager.toggleToUser("Miller");
				break;
			case IBOX_ID:
				tempi=Integer.decode(((controlP5.Textfield)event.controller()).getText());
				//System.out.println(tempi);
				break;
			case JBOX_ID:
				tempj=Integer.decode(((controlP5.Textfield)event.controller()).getText());
				break;
			case KBOX_ID:
				tempk=Integer.decode(((controlP5.Textfield)event.controller()).getText());
				break;
			case ISING_SLIDER_ID:
				if(manager.getCurrentuserName().equals("Ising")){
					((IsingUser)manager.getCurrentUser()).setTemp(event.value());
				}else{
					System.out.println("[LISTENER]: Ising sim not running. Ignoring Ising slider event.");
				}
				break;
			case ISING_START_ID:
				manager.toggleToUser("Ising");
				break;
		}
		
	}

}
