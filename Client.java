import java.io.*;
import java.net.*;

public class Client{
	public static void main(String[] arg){
		try{
			DataObject myObject = new DataObject();

			myObject.setMessage("new message");

			System.out.println("Message sent : " + myObject.getMessage());

			Socket socketToServer = new Socket("afs1.njit.edu", 5555);

			ObjectOutputStream myOutputStream =
				new ObjectOutputStream(socketToServer.getOutputStream());

			ObjectInputStream myInputStream =
				new ObjectInputStream(socketToServer.getInputStream());

			myOutputStream.writeObject(myObject);

			myObject = (DataObject)myInputStream.readObject();

                        		System.out.println("Messaged received : " + myObject.getMessage());

			myOutputStream.close();
			
			myInputStream.close();

            socketToServer.close();
	
		}
		catch(Exception e){
			System.out.println(e);
        		}
	}
}