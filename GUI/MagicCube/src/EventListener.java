import controlP5.ControlEvent;
import controlP5.ControlListener;
import core.CoreAPI;
import core.UserManager;
import cubeUser.IsingUser;


public class EventListener implements ControlListener, EventCodes {
	UserManager manager;
	CoreAPI cube;
	int tempi,tempj,tempk;
	
	public EventListener(UserManager manager, CoreAPI mcube){
		this.manager=manager;
		this.cube=mcube;
	}
	
	@Override
	public void controlEvent(ControlEvent event) {
		switch(event.controller().id()){
			case MILLER_START_ID:
				/*---MillerUser not yet created.---*/
				//MillerUser tempUser = new MillerUser(tempi, tempj, tempk);
				//tempUser.setCube(this.cube);
				//manager.adduser(tempUser, "Miller");
				//manager.toggleToUser("Miller");
				break;
			case IBOX_ID:
				tempi=Integer.decode(((controlP5.Textfield)event.controller()).getText());
				break;
			case JBOX_ID:
				tempj=Integer.decode(((controlP5.Textfield)event.controller()).getText());
				break;
			case KBOX_ID:
				tempk=Integer.decode(((controlP5.Textfield)event.controller()).getText());
				break;
			case ISING_SLIDER_ID:
				((IsingUser)manager.getCurrentUser()).setTemp(event.value());
				break;
			case ISING_START_ID:
				manager.toggleToUser("Ising");
				break;
		}
		
	}

}
