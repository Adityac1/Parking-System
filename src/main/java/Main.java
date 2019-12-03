import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilterReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.Adi.Exception.ErrorCode;
import com.Adi.Exception.ParkingException;
import com.Adi.Processor.AbstractProcessor;
import com.Adi.Processor.RequestProcessor;
import com.Adi.Service.Impl.ParkingServiceImpl;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		AbstractProcessor processor = new RequestProcessor();
		processor.setService(new ParkingServiceImpl());
		BufferedReader bufferReader = null;
		String input = null;
		
		try {
			System.out.println("\n\n\n");
			System.out.println("Adi parking lot");
			System.out.println("\n\n\n");
			printusage();
			switch (args.length) {
			case 0:
			{
				System.out.println("Please enter 'exit' to end execution ");
				System.out.println("Input:");
				while(true) {
					try {
						bufferReader = new BufferedReader(new InputStreamReader(System.in)); 
						input = bufferReader.readLine().trim();
						if(input.equalsIgnoreCase("exit")) {
							break;
						} else {
							if(processor.validate(input)) {
								try {
									processor.execute(input.trim());
								} catch (Exception e) {
									// TODO: handle exception
									System.out.println(e.getMessage());
								}
							}
							else {
								printusage();
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
						throw new ParkingException(ErrorCode.Invalid_Request.getMessage(), e);
					}
				}
				break;
			}
			case 1:
				File inputFile = new File(args[0]);
				try {
					bufferReader = new BufferedReader(new FileReader(inputFile));
					int lineno = 1;
					while((input = bufferReader.readLine()) != null) {
						input = input.trim();
						if(processor.validate(input)) {
							try {
								processor.execute(input);
							} catch (Exception e) {
								// TODO: handle exception
								System.out.println(e.getMessage());
							}
						}
						else {
							System.out.println("Incorrect command found at line :" +lineno+ "Input:" +input);
							lineno++;
						}
					}
				}catch (Exception e) {
					// TODO: handle exception
					throw new ParkingException(ErrorCode.Invalid_File.getMessage(), e);
				}
				break;

			default:
				System.out.println("Invalid input. Usage Style: java -jar <jar_file_path> <input_file_path>");
			}
		} catch (ParkingException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		finally {
			try {
				if(bufferReader != null) {
					bufferReader.close();
				}
			} catch (IOException e) {
				// TODO: handle exception
			}
		}
		
	}

	private static void printusage() {
		// TODO Auto-generated method stub
		StringBuffer buffer = new StringBuffer();
		buffer = buffer.append("-------Please enter any of the below command-----------").append("\n");
		buffer = buffer.append("A) For creating parking lot of size n ---> create_parking_lot {capacity}").append("\n");
		buffer = buffer.append("B) To park a car ---> parking a car{car_color}").append("\n");
		buffer = buffer.append("c) To Remove a car ----> car is going out from parking{slotno}").append("\n");
		buffer = buffer.append("D) To print the Ticket ----> Print the allocated ticket").append("\n");
		buffer = buffer.append("E) Fetch car RegistartionNo with color -----> going to print the registartionno using color{car_color}").append("\n");
		buffer = buffer.append("F) Get the slotNo using car color --> going to fetch the slot no using car color{car_color}").append("\n");
		buffer = buffer.append("G) Get the slotno using car RegistrationNo --> going to fetch the slot no using car registration no{car_number}").append("\n");
		System.out.println(buffer.toString());
	}

}
