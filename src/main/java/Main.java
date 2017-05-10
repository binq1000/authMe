import JMS.ClientAppGateway;
import JMS.ServerAppGateway;

/**
 * Created by Nekkyou on 3-5-2017.
 */
public class Main {
	public static void main(String [ ] args)
	{
		ClientAppGateway clientAppGateway = new ClientAppGateway();
		ServerAppGateway serverAppGateway = new ServerAppGateway();

		clientAppGateway.sendMessage("test");
	}

	private void killProcess(String process) {

	}
}
