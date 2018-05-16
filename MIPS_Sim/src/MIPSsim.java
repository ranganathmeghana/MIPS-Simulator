
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MIPSsim {

	private static PrintWriter pw = null;
	static {
		try {
			pw = new PrintWriter("output.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static Instruction instructionFetch = null;
	static Instruction instructionDecode = null;
	static Instruction instructionExecute = null;
	static Instruction instructionWriteBack = null;
	static Instruction instructionCommit = null;

	static List registers = new ArrayList<Integer>();
	static List dataSegment = new ArrayList<Integer>();
	static InstructionList instructionListObject = new InstructionList();
	Buffers buffers = new Buffers();

	public static void main(String[] args) {

		int cycle = 1;
		String instructions = null;
		ReservationStation reservationStation = new ReservationStation();
		ReOrderBuffer reOrderBuffer = new ReOrderBuffer();
		MIPSsim mipSsim = new MIPSsim();

		// instructionFetch=new Instruction();
		// instructionFetch.setInstruction(null);
		// instructionDecode=new Instruction();
		// instructionDecode.setInstruction(null);
		// instructionExecute=new Instruction();
		// instructionExecute.setInstruction(null);
		// instructionWriteBack=new Instruction();
		// instructionWriteBack.setInstruction(null);
		// instructionCommit=new Instruction();
		// instructionCommit.setInstruction(null);

		if (args.length == 3 && args[2].equals("sim") && !args[1].equals(null) && !args[0].equals(null))
			try {
				DisassemblerClass decoder = new DisassemblerClass();
				// PrintWriter writer = new PrintWriter(args[1], "UTF-8");
				// //writing to the output file
				instructions = decoder.disassemble(args[0]);
				String[] instructionList = instructions.split("\\r?\\n");

				for (int i = 0; i < instructionList.length; i++) {

					if (cycle == 1) {

						pw.println("CYCLE" + "<" + cycle + ">");
						// pw.flush();
						instructionListObject.addInstructions(instructionList[i]);
						pw.println(instructionListObject.toString());
						pw.println(reservationStation.toString());
						pw.println(reOrderBuffer.toString());
						for (int x = 0; x < 32; x++) {
							registers.add(0);
						}
						pw.println("R00:");
						for (int j = 0; j < 8; j++) {
							pw.print(registers.get(j));
						}
						pw.println("\n");
						pw.println("R08:");
						for (int k = 8; k < 16; k++) {
							pw.print(registers.get(k));
						}
						pw.println("\n");
						pw.println("R16:");
						for (int l = 16; l < 24; l++) {
							pw.print(registers.get(l));
						}
						pw.println("\n");
						pw.println("R24:");
						for (int m = 24; m < 32; m++) {
							pw.print(registers.get(m));
						}
						pw.println("\n");
						for (int y = 0; y < 10; y++) {
							dataSegment.add(0);
						}
						pw.println("\n");
						pw.println("716: ");
						for (int z = 0; z < 10; z++) {
							pw.print(dataSegment.get(z));
						}

						instructionFetch = new Instruction();
						instructionFetch.setInstruction(instructionList[i]);
						instructionDecode = new Instruction();
						instructionDecode.setInstruction(instructionFetch.getInstruction());
						instructionListObject.remove();
						// instructionFetch = "New Instruction";
					} else {
						pw.println("\n");
						pw.println("CYCLE" + "<" + cycle + ">");
						mipSsim.instructionFetch(instructionList[i]);
						reservationStation.addInstructions(instructionList[i - 1]);
						pw.println(reservationStation.toString());
						reOrderBuffer.addInstruction(instructionList[i - 1]);
						pw.println(reOrderBuffer.toString());
						mipSsim.instructionDecode();
						if (instructionExecute == null) {
							mipSsim.instructionExecute(null);
						} else {
							mipSsim.instructionExecute(instructionExecute.getInstruction());
						}
						mipSsim.instructionWriteback();
						mipSsim.instructionCommit();

						// mipSsim.instructionDecode(instructionList[i]);
						// mipSsim.instructionExecute(instructionList[i]);
						// mipSsim.instructionWriteBack(instructionList[i]);
						// mipSsim.instructionCommit(instructionList[i]);
						instructionCommit = instructionWriteBack;
						instructionWriteBack = instructionExecute;
						instructionExecute = instructionDecode;
						instructionDecode = instructionFetch;
						// instructionFetch = "New Instruction"

					}
					cycle++;

					// instructionListObject.addInstructions(instructionList[i]);
					// instructionListObject.toString();
					// cycle++;
					//
					//
					// while(cycle>1){
					// instructionListObject.addInstructions(instructionList[i+1]);
					// reservationStation.addInstructions(instructionList[i]);
					// reOrderBuffer.addInstruction(instructionList[i]);
					// i++;
					// }

				}

			} catch (IOException e) {

				e.printStackTrace();
			}
		else
			pw.println("Incorrect input");
	}

	public void instructionFetch(String instruction) {

		instructionListObject.addInstructions(instruction);
		pw.println(instructionListObject.toString());
		instructionListObject.remove();
		instructionFetch.setInstruction(instruction);
		instructionFetch = instructionFetch;
	}

	public void instructionDecode() {

		if (instructionDecode.getInstruction() != null) {
			instructionDecode.setInstruction(instructionDecode.getInstruction());
			instructionDecode = instructionDecode;
		} else {
			instructionDecode.setInstruction(null);
			instructionDecode = null;
		}
		// System.out.println(instructionDecode);
	}

	public void instructionExecute(String instruction) {

		if (instruction != null) {

			String delims = "[ ,#()]+";
			String[] tokens = instruction.split(delims);

			String op1 = tokens[1];
			String op2 =null;
			String op3 = null;
			if(tokens.length > 1){
			op1 = tokens[1];
			}
			if (tokens.length > 2) {
				op2 = tokens[2];
				op3 = tokens[3];
			}

			switch (tokens[0]) {

			case "ADDI":
				if (op1.startsWith("R") && op2.startsWith("R")) {
					pw.println("starts");
					// registers.add(Integer.parseInt(Character.toString(op1.charAt(1))),
					// ((Integer)(registers.get(Integer.parseInt(String.valueOf(op2.charAt(1))))))
					// + Integer.parseInt(op3));
					// buffers.setQj((Integer)(registers.get(Integer.parseInt(String.valueOf(op2.charAt(1))))));
					// buffers.setQk(Integer.parseInt(op3));
					// buffers.setVj(buffers.getQj()+buffers.getQk());
					// System.out.println("hi");
					instructionExecute.setInstruction(instruction);
					instructionExecute
							.setQj((Integer) (registers.get(Integer.parseInt(String.valueOf(op2.charAt(1))))));
					instructionExecute.setQk(Integer.parseInt(op3));
					instructionExecute.setVj(instructionExecute.getQj() + instructionExecute.getQk());
				}
			case "ADD":
				if (op1.startsWith("R") && op2.startsWith("R") && op3.startsWith("R")) {
					// registers.add(Integer.parseInt(Character.toString(op1.charAt(1))),
					// ((Integer)(registers.get(Integer.parseInt(String.valueOf(op2.charAt(1)))))
					// +
					// (Integer)(registers.get(Integer.parseInt(String.valueOf(op3.charAt(1)))))));
					// buffers.setQj((Integer)(registers.get(Integer.parseInt(String.valueOf(op2.charAt(1))))));
					// buffers.setQk((Integer)(registers.get(Integer.parseInt(String.valueOf(op3.charAt(1))))));
					// buffers.setVj(buffers.getQj()+buffers.getQk());
					instructionExecute.setInstruction(instruction);
					instructionExecute
							.setQj((Integer) (registers.get(Integer.parseInt(String.valueOf(op2.charAt(1))))));
					instructionExecute
							.setQk((Integer) (registers.get(Integer.parseInt(String.valueOf(op3.charAt(1))))));
					instructionExecute.setVj(instructionExecute.getQj() + instructionExecute.getQk());
				}
			case "SW":
				if (op1.startsWith("R") && op3.startsWith("R")) {
					// DOUBT
					// dataSegment.add(Integer.parseInt(Character.toString(op2.charAt(1)))
					// +
					// (Integer)(registers.get(Integer.parseInt(String.valueOf(op3.charAt(1))))),
					// (Integer)(registers.get(Integer.parseInt(String.valueOf(op3.charAt(1))))));
					// buffers.setQj((Integer)(registers.get(op2.charAt(1))));
					// buffers.setQk((Integer)(registers.get(op3.charAt(1))));
					// buffers.setVj(buffers.getQj()+buffers.getQk());
					// instructionExecute.setInstruction(instruction);
					// instructionExecute.setQj(Integer.parseInt(Character.toString(op2.charAt(1))));
					// instructionExecute.setQk((Integer)(registers.get(Integer.parseInt(String.valueOf(op3.charAt(1))))));
					// instructionExecute.setVj(instructionExecute.getQj()+instructionExecute.getQk());
				}
			case "LW":
				if (op1.startsWith("R") && op3.startsWith("R")) {
					// registers.add(Integer.parseInt(Character.toString(op1.charAt(1))),Integer.parseInt(Character.toString(op2.charAt(1)))
					// +
					// (Integer)(registers.get(Integer.parseInt(String.valueOf(op3.charAt(1))))));
					// buffers.setQj(Integer.parseInt(Character.toString(op2.charAt(1))));
					// buffers.setQk((Integer)(registers.get(Integer.parseInt(String.valueOf(op3.charAt(1))))));
					// buffers.setVj(buffers.getQj()+buffers.getQk());

					instructionExecute.setInstruction(instruction);
					instructionExecute.setQj(Integer.parseInt(Character.toString(op2.charAt(1))));
					instructionExecute
							.setQk((Integer) (registers.get(Integer.parseInt(String.valueOf(op3.charAt(1))))));
					instructionExecute.setVj(instructionExecute.getQj() + instructionExecute.getQk());
				}

			case "NOP":

			case "J":
				if (true) {

				}
			case "BREAK": {
				break;
			}
			case "SUB":
				if (op1.startsWith("R") && op2.startsWith("R") && op3.startsWith("R")) {

					instructionExecute.setInstruction(instruction);
					instructionExecute
							.setQj((Integer) (registers.get(Integer.parseInt(String.valueOf(op2.charAt(1))))));
					instructionExecute
							.setQk((Integer) (registers.get(Integer.parseInt(String.valueOf(op3.charAt(1))))));
					instructionExecute.setVj(instructionExecute.getQj() - instructionExecute.getQk());
				}

			case "ADDU":
				if (op1.startsWith("R") && op2.startsWith("R") && op3.startsWith("R")) {

					instructionExecute.setInstruction(instruction);
					instructionExecute
							.setQj((Integer) (registers.get(Integer.parseInt(String.valueOf(op2.charAt(1))))));
					instructionExecute
							.setQk((Integer) (registers.get(Integer.parseInt(String.valueOf(op3.charAt(1))))));
					instructionExecute.setVj(instructionExecute.getQj() + instructionExecute.getQk());

				}

			}

			instructionExecute = instructionExecute;
		} else {

			instructionExecute = null;
		}
	}

	public void instructionWriteback() {

		if (instructionWriteBack != null) {

			String delims = "[ ,#()]+";
			String[] tokens = instructionWriteBack.getInstruction().split(delims);

			String op1 = tokens[1];
			String op2 =null;
			String op3 = null;
			if(tokens.length > 1){
			op1 = tokens[1];
			}
			if (tokens.length > 2) {
				op2 = tokens[2];
				op3 = tokens[3];
			}

			switch (tokens[0]) {

			case "ADDI":
				if (op1.startsWith("R")) {
					registers.add(Integer.parseInt(Character.toString(op1.charAt(1))), instructionWriteBack.getVj());

				}
			case "ADD":
				if (op1.startsWith("R")) {
					registers.add(Integer.parseInt(Character.toString(op1.charAt(1))), instructionWriteBack.getVj());
				}
			case "SW":
				if (op1.startsWith("R")) {
					// DOUBT
					// dataSegment.add(Integer.parseInt(Character.toString(op2.charAt(1)))
					// + (Integer)(registers.get(op3.charAt(1))),
					// (Integer)(registers.get(op3.charAt(1))));
					// buffers.setQj((Integer)(registers.get(op2.charAt(1))));
					// buffers.setQk((Integer)(registers.get(op3.charAt(1))));
					// buffers.setVj(buffers.getQj()+buffers.getQk());
					// dataSegment.add(instructionWriteBack.getVj(),(Integer)(registers.get(op3.charAt(1))));

				}
			case "LW":
				if (op1.startsWith("R")) {
					registers.add(Integer.parseInt(Character.toString(op1.charAt(1))), instructionWriteBack.getVj());
				}

			case "NOP":
				if (true) {
					// System.out.println("no operation");
				}

			case "J":
				if (true) {

				}
			case "BREAK":
				break;
			case "SUB":
				if (op1.startsWith("R")) {
					registers.add(Integer.parseInt(Character.toString(op1.charAt(1))), instructionWriteBack.getVj());
				}

			case "ADDU":
				if (op1.startsWith("R")) {
					registers.add(Integer.parseInt(Character.toString(op1.charAt(1))), instructionWriteBack.getVj());
				}

			}

			instructionWriteBack = instructionWriteBack;
		} else {

			instructionWriteBack = null;
		}
	}

	// System.out.println("got instruction");
	// String instructionWriteBack="after instructionExecute";

	public void instructionCommit() {

		pw.println("\n");
		pw.println("R00:");
		for (int j = 0; j < 8; j++) {
			pw.print(registers.get(j));
		}

		pw.println("\n");
		pw.println("R08:");
		pw.flush();
		for (int k = 8; k < 16; k++) {
			pw.print(registers.get(k));
		}

		pw.println("\n");
		pw.println("R16:");
		pw.flush();
		for (int l = 16; l < 24; l++) {
			pw.print(registers.get(l));
		}

		pw.println("\n");
		pw.println("R24:");
		pw.flush();
		for (int m = 24; m < 32; m++) {
			pw.print(registers.get(m));
		}

		pw.println("\n");
		pw.println("716: ");
		pw.flush();
		for (int z = 0; z < 10; z++) {
			pw.print(dataSegment.get(z));
		}
		pw.flush();

	}
	// System.out.println("got instruction");
	// String instructionCommit="after instructionCommit";

}
