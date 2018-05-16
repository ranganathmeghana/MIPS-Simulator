
import java.io.FileInputStream;
import java.io.IOException;

public class DisassemblerClass {
	String finalOutput;

	public DisassemblerClass() {
		finalOutput = "";

	}

	public String disassemble(String inputFile) throws IOException {

		FileInputStream inputData = new FileInputStream(inputFile);
		byte[] byteInput = new byte[4];
		// reading input data into a byte array
		int size = 4;
		boolean indicator = false;

		int memoryCounter = 600;
		while (indicator == false) {
			size = inputData.read(byteInput);

			if (size < 4) {
				inputData.close();
				return "error";
			}

			String binaryOutput = "";

			for (int i = 0; i < 4; i++)

			{
				int unsigned = byteInput[i] & 0xFF;
				String binary = Integer.toBinaryString(unsigned);

				while (binary.length() < 8)
					binary = new StringBuilder(binary).insert(0, "0").toString();

				binaryOutput += binary;
			}

			String inputInstruction = binaryOutput;

			//splits the instruction into opcode, register and immediate values
			String instructionSplit[] = new String[6];
			instructionSplit[0] = inputInstruction.substring(0, 6);
			instructionSplit[1] = inputInstruction.substring(6, 11);
			instructionSplit[2] = inputInstruction.substring(11, 16);
			instructionSplit[3] = inputInstruction.substring(16, 21);
			instructionSplit[4] = inputInstruction.substring(21, 26);
			instructionSplit[5] = inputInstruction.substring(26);

			//for (int i = 0; i < 6; i++)

//				finalOutput += instructionSplit[i] + " ";
	//		finalOutput += memoryCounter + " ";
			memoryCounter += 4;

			indicator = disassembleInstruction(instructionSplit);
			finalOutput += "\n";

		}
		while (true) {
			size = inputData.read(byteInput);
			if (size != 4)
				break;

			String binaryWord = "";

			for (int i = 0; i < 4; i++)

			{
				int unsigned = byteInput[i] & 0xFF;
				String binaryOne = Integer.toBinaryString(unsigned);

				while (binaryOne.length() < 8)
					binaryOne = new StringBuilder(binaryOne).insert(0, "0").toString();

				binaryWord += binaryOne;
			}

//			finalOutput += binaryWord + " " + memoryCounter + " " + Integer.parseInt(binaryWord, 2);
			memoryCounter += 4;
			finalOutput += "\n";

		}
		inputData.close();
		return (finalOutput);
	}

	public boolean disassembleInstruction(String[] instructionSplit) {
		boolean indicator = false;
		int regOne, regTwo, regThree = 0;
		String foo;
		int opcode = Integer.parseInt(instructionSplit[0], 2);

		switch (opcode) {
		case 0:
			
			//finds the corresponding opcode
			if (instructionSplit[5].equals("001101")) {
				indicator = true;
				finalOutput += "BREAK";
			}
			if (instructionSplit[5].equals("000000") && instructionSplit[2].equals("00000")) {
				regThree = Integer.parseInt(instructionSplit[3], 2); //assigns the register value
				if (regThree == 0)
					finalOutput += "NOP";
			}
			if (instructionSplit[5].equals("100001")) {
				finalOutput += "ADDU ";
				regOne = Integer.parseInt(instructionSplit[1], 2);
				regTwo = Integer.parseInt(instructionSplit[2], 2);
				regThree = Integer.parseInt(instructionSplit[3], 2);
				finalOutput += "R" + regThree + ", R" + regOne + ", R" + regTwo;
			}
			if (instructionSplit[5].equals("100000")) {
				finalOutput += "ADD ";
				regOne = Integer.parseInt(instructionSplit[1], 2);
				regTwo = Integer.parseInt(instructionSplit[2], 2);
				regThree = Integer.parseInt(instructionSplit[3], 2);
				finalOutput += "R" + regThree + ", R" + regOne + ", R" + regTwo;
			}
			if (instructionSplit[5].equals("100100")) {
				finalOutput += "AND ";
				regOne = Integer.parseInt(instructionSplit[1], 2);
				regTwo = Integer.parseInt(instructionSplit[2], 2);
				regThree = Integer.parseInt(instructionSplit[3], 2);
				finalOutput += "R" + regThree + ", R" + regOne + ", R" + regTwo;
			}
			if (instructionSplit[5].equals("100101")) {
				finalOutput += "OR ";
				regOne = Integer.parseInt(instructionSplit[1], 2);
				regTwo = Integer.parseInt(instructionSplit[2], 2);
				regThree = Integer.parseInt(instructionSplit[3], 2);
				finalOutput += "R" + regThree + ", R" + regOne + ", R" + regTwo;
			}
			if (instructionSplit[5].equals("100110")) {
				finalOutput += "XOR ";
				regOne = Integer.parseInt(instructionSplit[1], 2);
				regTwo = Integer.parseInt(instructionSplit[2], 2);
				regThree = Integer.parseInt(instructionSplit[3], 2);
				finalOutput += "R" + regThree + ", R" + regOne + ", R" + regTwo;
			}
			if (instructionSplit[5].equals("100111")) {
				finalOutput += "NOR ";
				regOne = Integer.parseInt(instructionSplit[1], 2);
				regTwo = Integer.parseInt(instructionSplit[2], 2);
				regThree = Integer.parseInt(instructionSplit[3], 2);
				finalOutput += "R" + regThree + ", R" + regOne + ", R" + regTwo;
			}
			if (instructionSplit[5].equals("100010")) {
				finalOutput += "SUB ";
				regOne = Integer.parseInt(instructionSplit[1], 2);
				regTwo = Integer.parseInt(instructionSplit[2], 2);
				regThree = Integer.parseInt(instructionSplit[3], 2);
				finalOutput += "R" + regThree + ", R" + regOne + ", R" + regTwo;
			}
			if (instructionSplit[5].equals("100011")) {
				finalOutput += "SUBU ";
				regOne = Integer.parseInt(instructionSplit[1], 2);
				regTwo = Integer.parseInt(instructionSplit[2], 2);
				regThree = Integer.parseInt(instructionSplit[3], 2);
				finalOutput += "R" + regThree + ", R" + regOne + ", R" + regTwo;
			}

			if (instructionSplit[5].equals("000000")) {

				regOne = Integer.parseInt(instructionSplit[4], 2);
				regTwo = Integer.parseInt(instructionSplit[2], 2);
				regThree = Integer.parseInt(instructionSplit[3], 2);
				if (regTwo != 0) {
					finalOutput += "SLL ";
					finalOutput += "R" + regThree + ", R" + regTwo + ", #" + regOne;
				}
			}
			if (instructionSplit[5].equals("000010")) {
				finalOutput += "SRL ";
				regOne = Integer.parseInt(instructionSplit[4], 2);
				regTwo = Integer.parseInt(instructionSplit[2], 2);
				regThree = Integer.parseInt(instructionSplit[3], 2);
				finalOutput += "R" + regThree + ", R" + regTwo + ", #" + regOne;
			}
			if (instructionSplit[5].equals("000011")) {
				finalOutput += "SRA ";
				regOne = Integer.parseInt(instructionSplit[4], 2);
				regTwo = Integer.parseInt(instructionSplit[2], 2);
				regThree = Integer.parseInt(instructionSplit[3], 2);
				finalOutput += "R" + regThree + ", R" + regTwo + ", #" + regOne;
			}
			if (instructionSplit[5].equals("101010")) {
				finalOutput += "SLT ";
				regOne = Integer.parseInt(instructionSplit[1], 2);
				regTwo = Integer.parseInt(instructionSplit[2], 2);
				regThree = Integer.parseInt(instructionSplit[3], 2);
				finalOutput += "R" + regThree + ", R" + regOne + ", R" + regTwo;
			}
			if (instructionSplit[5].equals("101011")) {
				finalOutput += "SLTU ";
				regOne = Integer.parseInt(instructionSplit[1], 2);
				regTwo = Integer.parseInt(instructionSplit[2], 2);
				regThree = Integer.parseInt(instructionSplit[3], 2);
				finalOutput += "R" + regThree + ", R" + regOne + ", R" + regTwo;
			}

			break;

		case 1:

			regOne = Integer.parseInt(instructionSplit[1], 2);
			regTwo = Integer.parseInt(instructionSplit[2], 2);
			foo = instructionSplit[3] + instructionSplit[4] + instructionSplit[5];
			if (regTwo == 1) {
				foo += "00";
				finalOutput += "BGEZ ";
				finalOutput += "R" + regOne + ", #" + Integer.parseInt(foo, 2);
			}
			if (regTwo == 0) {
				foo += "00";
				finalOutput += "BLTZ ";
				finalOutput += "R" + regOne + ", #" + Integer.parseInt(foo, 2);
			}
			break;
			
		case 2:
			finalOutput += "J #";
			regOne = Integer.parseInt(instructionSplit[1], 2);
			regTwo = Integer.parseInt(instructionSplit[2], 2);
			foo = instructionSplit[1] + instructionSplit[2] + instructionSplit[3] + instructionSplit[4]
					+ instructionSplit[5];
			foo += "00";
			finalOutput += Integer.parseInt(foo, 2);
			break;
		
		case 4:
			finalOutput += "BEQ ";
			regOne = Integer.parseInt(instructionSplit[1], 2);
			regTwo = Integer.parseInt(instructionSplit[2], 2);
			foo = instructionSplit[3] + instructionSplit[4] + instructionSplit[5];
			foo += "00";
			finalOutput += "R" + regOne + ", R" + regTwo + ", #" + Integer.parseInt(foo, 2);
			break;
		
		case 5:
			finalOutput += "BNE ";
			regOne = Integer.parseInt(instructionSplit[1], 2);
			regTwo = Integer.parseInt(instructionSplit[2], 2);
			foo = instructionSplit[3] + instructionSplit[4] + instructionSplit[5];
			foo += "00";
			finalOutput += "R" + regOne + ", R" + regTwo + ", #" + Integer.parseInt(foo, 2);
			break;
		
		case 6:

			regOne = Integer.parseInt(instructionSplit[1], 2);
			regTwo = Integer.parseInt(instructionSplit[2], 2);
			foo = instructionSplit[3] + instructionSplit[4] + instructionSplit[5];

			foo += "00";
			finalOutput += "BLEZ ";
			if (foo.startsWith("0"))
				finalOutput += "R" + regOne + ", #" + Integer.parseInt(foo, 2);
			else
				finalOutput += "R" + regOne + ", #" + (Integer.parseInt(foo, 2) - 262144);

			break;

		case 7:

			regOne = Integer.parseInt(instructionSplit[1], 2);
			regTwo = Integer.parseInt(instructionSplit[2], 2);
			foo = instructionSplit[3] + instructionSplit[4] + instructionSplit[5];

			foo += "00";
			finalOutput += "BGTZ ";
			finalOutput += "R" + regOne + ", #" + Integer.parseInt(foo, 2);

			break;
			
		case 8:
			finalOutput += "ADDI ";
			regOne = Integer.parseInt(instructionSplit[1], 2);
			regTwo = Integer.parseInt(instructionSplit[2], 2);
			foo = instructionSplit[3] + instructionSplit[4] + instructionSplit[5];
			regThree = Integer.parseInt(foo, 2);
			if (foo.startsWith("1"))
				regThree = regThree - 65536;
			finalOutput += "R" + regTwo + ", R" + regOne + ", #" + regThree;
			break;

		case 9:
			finalOutput += "ADDIU ";
			regOne = Integer.parseInt(instructionSplit[1], 2);
			regTwo = Integer.parseInt(instructionSplit[2], 2);
			foo = instructionSplit[3] + instructionSplit[4] + instructionSplit[5];
			regThree = Integer.parseInt(foo, 2);
			if (foo.startsWith("1"))
				regThree = regThree - 65536;
			finalOutput += "R" + regTwo + ", R" + regOne + ", #" + regThree;
			break;
		
			
		case 10:
			finalOutput += "SLTI ";
			regOne = Integer.parseInt(instructionSplit[1], 2);
			regTwo = Integer.parseInt(instructionSplit[2], 2);
			foo = instructionSplit[3] + instructionSplit[4] + instructionSplit[5];
			regThree = Integer.parseInt(foo, 2);
			if (foo.startsWith("1"))
				regThree = regThree - 65536;
			finalOutput += "R" + regTwo + ", R" + regOne + ", #" + regThree;
			break;

		case 35:
			finalOutput += "LW ";
			regOne = Integer.parseInt(instructionSplit[1], 2);
			regTwo = Integer.parseInt(instructionSplit[2], 2);
			foo = instructionSplit[3] + instructionSplit[4] + instructionSplit[5];

			finalOutput += "R" + regTwo + ", " + Integer.parseInt(foo, 2) + "(R" + regOne + ")";
			break;
		
		case 43:
			finalOutput += "SW ";
			regOne = Integer.parseInt(instructionSplit[1], 2);
			regTwo = Integer.parseInt(instructionSplit[2], 2);
			foo = instructionSplit[3] + instructionSplit[4] + instructionSplit[5];

			finalOutput += "R" + regTwo + ", " + Integer.parseInt(foo, 2) + "(R" + regOne + ")";
			break;

		default:
			
			break;
		}

		if (instructionSplit[0].equals("000000") && instructionSplit[5].equals("001101")) {
			indicator = true;

		}
		return indicator;

	}

}
