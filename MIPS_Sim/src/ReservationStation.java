import java.util.ArrayList;

public class ReservationStation {

	ArrayList<String> instructionsRS=new ArrayList<String>();
	
	public void addInstructions(String instruction)
	{
		instructionsRS.add(instruction);
	}
	
	public String toString()
	{
		String str = "RS:\n";
		int size = instructionsRS.size();
		for(int i = 0; i < size; i++){
			str += "[" + instructionsRS.get(i).toString() + "] ";
			str += "\n";
		}
		return str;
	}
}
