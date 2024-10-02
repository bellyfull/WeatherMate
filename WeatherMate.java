package assignment1;

import com.google.gson.Gson;  
import com.google.gson.JsonSyntaxException;
import java.io.File;                    
import java.io.FileReader;               
import java.io.IOException;              
import java.util.Scanner;  
import java.util.List;
import java.io.FileWriter; 


// *** the goodweather.json file already had Chicago as an entry, so I could not follow
// the assignment execution sample where it inputs Chicago's data
public class WeatherMate {
	
	class DataSet { //store each city's data in a list
		List<Data> data;
		//ex) if there are 3 cities, the list will have 3 Data objects. the list is called data
	}
	
	// data types
	class Data {
		
		String location;
		int temperature;
		int temperatureMin;
		int temperatureMax;
		int humidity;
		float pressureSeaLevel;
		float visibility; 
		float windSpeed;
		int windDirection;
		String condition;
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		// gson creates new objects from json file
		Gson gson = new Gson();
		WeatherMate mate = new WeatherMate();
		
		DataSet dataSet = null;
		String inputFile = null;
		
		while (dataSet == null) { // loop until valid file 
			System.out.println("please enter json file name: ");
			inputFile = scanner.nextLine();
			File file = new File(inputFile);
			
			//file exists? no ->file not found error
			if (file.exists() == false) {
				System.out.println("The file "+inputFile+" could not be found");
				continue;
			}
			
			System.out.println("The file has been properly read.");
			
			//parsing
			try(FileReader reader = new FileReader(file)) {
				//retrieving data from file and assigning them to data's data variables
				//storing it in DataSet class called dataSet which holds a list of Data objects
				dataSet = gson.fromJson(reader, DataSet.class);
				
				for (Data data : dataSet.data) { // set every location to lowercase so no there are no dupes later
					if (data.location != null) {
						data.location = data.location.toLowerCase();
					}
					else {
						System.out.println("missing location value");
						dataSet = null;
						break;
					}
				}
				
//				if (mate.validateData(dataSet) == false) {
//					dataSet = null;
//					System.out.println("");
//				}
				
//				File not found. done
//				• Data cannot be converted to the proper type as shown above. -> jsonsyntaxexception done
//				• There are too few parameters on one of the lines. -> every Data variable cannot be null
//				• Two locations have the same “location” field. (Case-insensitive) ->  already found error. lowercase everything first
			}
			catch (JsonSyntaxException e) {
				System.out.println("could not be converted into data type");
			}
			catch (IOException e) {
				System.out.println("couldn't read in file");
			}
				
		}


		boolean exit = false;
		while (exit == false) {
			System.out.println("Menu:");
            System.out.println("1) Display weather on all locations");
            System.out.println("2) Search for weather on a location");
            System.out.println("3) Add a new location");
            System.out.println("4) Remove a location");
            System.out.println("5) Sort locations");
            System.out.println("6) Exit");
            System.out.print("What would you like to do? ");
            
            String choice = scanner.nextLine();
    		// add "file has been properly read"
            
    		switch (choice) {
    		
    		case "1": 
    			// System.out.println("File has been properly read.");
    			displayLocations(dataSet);
    			break;
    			
    		case "2":
    			searchLocation(dataSet,scanner);
    			break;
    		
    		case "3":
    			addLocation(dataSet,scanner);
    			break;
    			
    		case "4":
    			removeLocation(dataSet,scanner);
    			break;
    		
    		case "5":
    			sortLocation(dataSet,scanner);
    			break;
    		case "6":
    			 exitMenu(dataSet, inputFile, scanner, gson);
    			exit = true;
    			break;
    			
    		default:
    			System.out.println("That is not a valid option");
    			break;
    		}
		}
	}

	public static void displayLocations(DataSet dataSet) {
		for (Data data : dataSet.data) {
			//1. ensure temperatureMin<=temperature<=temperatureMax, 0<=humidity<=100). Test not required/. As long as it is a valid positive type it is OK. 
			//2. 75 24fafdzsufdrsheutiu. No we will not trick test. But we will test "abc" when the required inout is integer, for example. 
			//3. We will not test input files with no data. At least 1 location, with all entries, will be used.
			if (data.temperatureMin<=data.temperature && data.temperature<=data.temperatureMax && 0<=data.humidity && data.humidity<=100) { // && data.temperature!=null && data.temperatureMin!=null) {
					System.out.println("Location: " + data.location);
					System.out.println("Temperature is " + data.temperature + " degrees Fahrenheit,");
					System.out.println("low temperature is " + data.temperatureMin + " degrees Fahrenheit,");
					System.out.println("high temperature is "+data.temperatureMax + " degrees Fahrenheit,");
					System.out.println("Humidity is "+data.humidity+"%.");
					System.out.println("Pressure is  "+data.pressureSeaLevel + " Inch Hg,");
					System.out.println("Visibility is "+data.visibility+ " miles,");
					System.out.println("Wind speed is "+data.windSpeed+" miles/hour,");
					System.out.println("Wind direction is  "+data.windDirection+" degrees,");
					System.out.println("Weather can be described as "+data.condition);
					
			}
				else {
					System.out.println("input file is missing some values or is out of range");
				}
				
		}
	}
	
	public static void searchLocation(DataSet dataSet, Scanner scanner) { // how did it find locations data?
			boolean found = false;
			Data data = null;
			
			while (found == false) {
				System.out.println("What is the location you would like to search for?");
				String loc = scanner.nextLine().toLowerCase();//lowercase to match what exists in our Data 
				
				for (Data d : dataSet.data) {
					if (d.location.equals(loc)) {
						found = true;
						data = d; // loc's data becomes d
						break;
					}
				}
				// if reaches here, couldnt match location
				if (found == false) { 
					System.out.println(loc + " could not be found");
				}
				
			} // dont need to print info for option 2
//			if (data != null) {
//				if (data.temperatureMin<=data.temperature && data.temperature<=data.temperatureMax && 0<=data.humidity && data.humidity<=100) { // && data.temperature!=null && data.temperatureMin!=null) {
//					System.out.println("Location: " + data.location);
//					System.out.println("Temperature: " + data.temperature);
//					System.out.println("Minimum temperature: " + data.temperatureMin);
//					System.out.println("Maximum temperature: "+data.temperatureMax);
//					System.out.println("Humidity: "+data.humidity);
//					System.out.println("Pressure at Sea Level: "+data.pressureSeaLevel);
//					System.out.println("Visibility: "+data.visibility);
//					System.out.println("Wind speed: "+data.windSpeed);
//					System.out.println("Wind direction: "+data.windDirection);
//					System.out.println("Condition: "+data.condition);
//					
//				}
//				
//			}
			boolean SubMenu = true;
			//only print this line once after location is called.
			System.out.println("I have info about the weather in " + data.location + ": ");
			while (SubMenu != false) {
				
		        System.out.println("1) Temperature");
		        System.out.println("2) High and low temperature");
		        System.out.println("3) Humidity");
		        System.out.println("4) Pressure");
		        System.out.println("5) Visibility");
		        System.out.println("6) Wind speed and direction");
		        System.out.println("7) Weather conditions");
		        System.out.println("8) Return to main menu");
		        System.out.print("What weather information do you want to know for " + data.location+ " ?");
		        
		        String submenuChoice = scanner.nextLine();
		        switch (submenuChoice) {
		        
		        
	            case "1":
	                System.out.println("The temperature in " + data.location + " is " + data.temperature + " degrees Fahrenheit");
	                break;
	            case "2":
	                System.out.println("The high temperature in " + data.location + " is " + data.temperatureMax + " degrees and the low is " + data.temperatureMin + " degrees Fahrenheit");
	                break;
	            case "3":
	                System.out.println("The humidity in " + data.location + " is " + data.humidity + "%");
	                break;
	            case "4":
	                System.out.println("The pressure at sea level in " + data.location + " is " + data.pressureSeaLevel + " Inch Hg.");
	                break;
	            case "5":
	                System.out.println("The visibility in " + data.location + " is " + data.visibility + " miles.");
	                break;
	            case "6":
	                System.out.println("The wind speed in " + data.location + " is " + data.windSpeed + " miles/hour, and the wind direction is " + data.windDirection + " degrees.");
	                break;
	            case "7":
	                System.out.println("The weather condition in " + data.location + " can be described as " + data.condition);
	                break;
	            case "8": // quit submenu here
	                SubMenu = false; 
	                break;
	            default:
	                System.out.println("Invalid. Please enter a number between 1-8");
		        }
			}
			
	}
	
	public static void addLocation(DataSet dataSet, Scanner scanner) {  
		Data addData = new WeatherMate().new Data();
		String newLocation;
		
		while (true) {
			System.out.println("What is the location you would like to add weather info? ");
			newLocation = scanner.nextLine().toLowerCase(); //lowercase
	
		    // check for redundance
			boolean redundant = false;
		    for (Data d : dataSet.data) {
		        if (d.location.equals(newLocation)) {
		            System.out.println("There is already an entry for " + d.location);
		            redundant = true;
		            break;
		        }
		    }
		    
		    if (redundant == false) {
		    	break;
		    }
		} // if left loop, there is no redundance
	    
	
	    
	    addData.location = newLocation; //if not redundant, new addData Data class will be instantiated
		
		
		System.out.println("Temperature: ");
		addData.temperature = Integer.parseInt(scanner.nextLine());
		
		
		System.out.println("Minimum temperature: ");
		addData.temperatureMin = Integer.parseInt(scanner.nextLine());
		
		System.out.println("Maximum temperature: ");
		addData.temperatureMax = Integer.parseInt(scanner.nextLine());
		 
		
		System.out.println("Humidity: ");
		addData.humidity = Integer.parseInt(scanner.nextLine());
		
		
		System.out.println("Pressure at Sea Level: ");
		addData.pressureSeaLevel = Float.parseFloat(scanner.nextLine());
		
		
		System.out.println("Visibility: ");
		addData.visibility = Float.parseFloat(scanner.nextLine());
		
		
		System.out.println("Wind speed: ");
		addData.windSpeed = Float.parseFloat(scanner.nextLine());
		
		
		
		System.out.println("Wind direction: ");
		addData.windDirection = Integer.parseInt(scanner.nextLine());
		
		
		System.out.println("Condition: ");
		addData.condition = scanner.nextLine();
		
		dataSet.data.add(addData);

		
		System.out.println("there is now a new entry for "+newLocation);
				
	}
		
	public static void removeLocation(DataSet dataSet, Scanner scanner) {
		System.out.println("here are the registered locations, enter a location to remove: ");
		// String location = scanner.nextLine().toLowerCase();
		
		for (int i=0; i<dataSet.data.size(); i++) {
//			if (dataSet.data.get(i) == location) {
//				dataSet.data.remove(i);
//				return; // cant use break or else will still print out the error message below
//			}
			System.out.println((i+1) + ")" + dataSet.data.get(i).location);
			
		} // prints dataset
		
		
		//System.out.println("could not find location");
		
		System.out.println("enter the location you would like to remove: ");
		String location = scanner.nextLine().toLowerCase();
		
		
	    // Loop through dataSet to find the matching location
	    for (int i = 0; i < dataSet.data.size(); i++) {
	        if (dataSet.data.get(i).location.equals(location)) {  // Compare location field
	            dataSet.data.remove(i);  // Remove the matched location
	            System.out.println("Location " + location + " is now removed.");
	            return;  // Exit after removal
	        }
	    }

	    // If no matching location is found
	    System.out.println("Could not find location: " + location);
		
		
		
	}
		
	public static void sortLocation(DataSet dataSet, Scanner scanner) {
	    System.out.println("1) A to Z");
	    System.out.println("2) Z to A");
		System.out.println("How would you like to sort by? ");
		String choice = scanner.nextLine();
		
		  switch (choice) {
	        case "1": // A TO Z
	        	for (int i=0; i<dataSet.data.size(); i++) {
	        	    for (int j=i+1; j<dataSet.data.size(); j++) {
	        	        if (dataSet.data.get(i).location.compareTo(dataSet.data.get(j).location)>0) {
	        	        	//swap
	        	        	Data tempData = dataSet.data.get(i);
	        	        	dataSet.data.set(i, dataSet.data.get(j));
	        	        	dataSet.data.set(j, tempData);

	        	        }
	        	    }
	        	} // success
	            System.out.println("Locations sorted from A to Z");
	            break;
	        case "2": // Z TO A
	        	for (int i=0; i<dataSet.data.size(); i++) {
	        	    for (int j=i + 1; j<dataSet.data.size(); j++) {
	        	        if (dataSet.data.get(i).location.compareTo(dataSet.data.get(j).location)<0) {
	        	        	//swap
	        	        	Data tempData = dataSet.data.get(i);
	        	        	dataSet.data.set(i, dataSet.data.get(j));
	        	        	dataSet.data.set(j, tempData); 

	        	        }
	        	    }
	        	}
	        	//success
	            System.out.println("Locations sorted from Z to A");
	            break;
	        default: // invalid
	            System.out.println("please choose only 1 or 2");
	            break;
	    }
		
	}
	public static void exitMenu(DataSet dataSet, String inputFile, Scanner scanner, Gson gson) {
	    System.out.println("1) Yes  2) No");
	    System.out.print("Would you like to save your edits?");
	    String choice = scanner.nextLine();

	    if (choice.equals("1")) { // need to override original file
	        try (FileWriter writer = new FileWriter(inputFile)) {
	            gson.toJson(dataSet, writer);
	            System.out.println("Your edits have been saved to " + inputFile);
	        } catch (IOException e) {
	            System.out.println("couldn't save file");
	        }
	    } 
	    
	    else if (choice.equals("2")) {
	        System.out.println("Exiting without saving changes");
	    } 
	    
	    else {
	        System.out.println("Invalid choice. Exiting without saving.");
	    }
	    
	    System.out.println("Thank you for using my program!");
	}
		
			
}
				
		






