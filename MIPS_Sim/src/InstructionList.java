import java.awt.List;
import java.util.ArrayList;
import java.util.Queue;

public class InstructionList {
	
	
	ArrayList<String> instructionsList=new ArrayList<String>();
	
	
	public void addInstructions(String instructions){
		instructionsList.add(instructions);
	}
	
	public String toString()
	{
		String str = "IQ:\n";
		int size = instructionsList.size();
		for(int i = 0; i < size; i++){
			str += "[" + instructionsList.get(i).toString() + "] ";
			str += "\n";
		}
		return str;
	}
	

	public void remove()
	{
		instructionsList.remove(0);
	}
	
}
