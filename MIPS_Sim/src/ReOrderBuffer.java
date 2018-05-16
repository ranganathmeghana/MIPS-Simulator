import java.util.ArrayList;

public class ReOrderBuffer {
	
	ArrayList<String> instructionsROB=new ArrayList<String>();
	
	public void addInstruction(String instruction){
		instructionsROB.add(instruction);
	}
	

	public String toString()
	{
		String str = "ROB:\n";
		int size = instructionsROB.size();
		for(int i = 0; i < size; i++){
			str += "[" + instructionsROB.get(i).toString() + "] ";
			str += "\n";
		}
		return str;
	}

}
