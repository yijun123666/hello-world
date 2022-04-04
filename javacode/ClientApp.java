package javacode;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;

public class ClientApp {

	static {
		System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "false");
	}

	public static String main(String[] command) throws Exception {
		// Load a file system based wallet for managing identities.
		Path walletPath = Paths.get("C:\\Users\\lenovo\\Desktop\\FYP\\ticketSystem\\wallet");
		Wallet wallet = Wallets.newFileSystemWallet(walletPath);
		// load a CCP
	 	Path networkConfigPath = Paths.get("C:\\Users\\lenovo\\Desktop\\FYP\\ticketSystem\\connection.json");
		Gateway.Builder builder = Gateway.createBuilder();
		builder.identity(wallet, "Org1 Admin").networkConfig(networkConfigPath).discovery(true);
		byte[] result=new byte[] {78,85,76,76};
		// create a gateway connection
		try (Gateway gateway = builder.connect()) {

			// get the network and contract
			Network network = gateway.getNetwork("mychannel");
			Contract contract = network.getContract("testing");

			if(command[0].contentEquals("createTicket")) {
				result=contract.submitTransaction("createTicket", command[1],command[2],command[3],command[4],command[5],command[6],command[7]);
			}
			else if(command[0].contentEquals("queryTicket")) {
				result = contract.evaluateTransaction("queryTicket", command[1]);
			}
			else if(command[0].contentEquals("changeTicketOwner")) {
			result = contract.submitTransaction("changeTicketOwner",command[1], command[2]);
			}
//			System.out.println(new String(result));
			gateway.close();
		}
		return new String(result);
	}

}
