package com.csv.process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.csv.model.CarrierRange;
import com.csv.model.CarrierZipTerminal;
import com.csv.model.CommonCSVInterface;
import com.csv.model.MasterZip;

public class CSVTest {

	private static String destinationCSVFile = "C:/jars/convertedChris_Feb12.csv";
	
	private static String carrierRangeCSV = "C:/3G-TM/TransitFiles/Carrier Range.csv";
	private static String masterZipsCSV = "C:/3G-TM/TransitFiles/Master Zips.csv";
	private static String PDIBZipsCSV = "C:/3G-TM/TransitFiles/PDIBzips.csv";
	private static String SEKWZipsCSV = "C:/3G-TM/TransitFiles/SEKWzips.csv";
	private static String preOutput = "C:/3G-TM/TransitFiles/output/pre_output.csv";
	private static List<CarrierRange> carrierrangeZips;
	private static List<MasterZip> masterZips;
	private static List<CarrierZipTerminal> sekwZips;
	private static List<CarrierZipTerminal> pdibZips;
	
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";

	private static final String FILE_HEADER = "ZIPCODE,ZONE";

	public static void main(String[] args) throws IOException {

		processCSV();


	}

	private static void processCSV() {
		List<ZipCodeZone> zipszones = readZipsFromCSV("C:/jars/Chris_Feb12.csv");
		//find a way to sort based on zips - java versions causing issues
		//Collections.sort(zipszones);
		List<ZipZoneResult> result = writeResult(zipszones);
		System.out.println("converting and printing");
		convertAndPrint(result);
		/*
		 * for (ZipZoneResult zipzone : result){
		 * //System.out.println("in main for loop"); System.out.println("Zip " +
		 * zipzone.getZipCode() + " Zone " + zipzone.getZone()); }
		 */
	}

	private static void convertAndPrint(List<ZipZoneResult> result) {
		try {
			FileWriter fileWriter = new FileWriter(destinationCSVFile);
			fileWriter.append(FILE_HEADER.toString());
			fileWriter.append(NEW_LINE_SEPARATOR);

			for (ZipZoneResult zipzone : result) {
				fileWriter.append(zipzone.getZipCode());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(zipzone.getZone());
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			System.out.println("dest file created" + destinationCSVFile);

			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static List<ZipZoneResult> writeResult(List<ZipCodeZone> zipszones) {
		List<ZipZoneResult> result = new ArrayList<ZipZoneResult>();
		ZipZoneResult resultNode;
		ZipCodeZone current;
		ZipCodeZone next;
		// ZipCodeZone start = result.get(0);
		// List<ZipCodeZone> temp = new ArrayList<ZipCodeZone>();
		int min = zipszones.get(0).getZipCode();
		int max = zipszones.get(0).getZipCode();
		System.out.println("size of zipzones " + zipszones.size());
		for (int i = 0; i < zipszones.size() - 1; i++) {
			current = zipszones.get(i);
			next = zipszones.get(i + 1);
			// System.out.println("current "+ current.getZone() + "next" +
			// next.getZone());
			if (current.getZone().equalsIgnoreCase(next.getZone())) {
				// if(next.getZipCode() > max){
				max = next.getZipCode();
				if (i == zipszones.size() - 2) {
					resultNode = new ZipZoneResult();
					String range = leadZero(min) + ":" + leadZero(max);
					resultNode.setZipCode(range);
					resultNode.setZone(current.getZone());
					result.add(resultNode);
				}
				// }
				// System.out.println("*********************************** SAME ************************");
				// System.out.println("min " + min + " max" + max);
			} else {
				// System.out.println("***********************************NOT SAME ************************");
				// System.out.println("min " + min + " max" + max);
				resultNode = new ZipZoneResult();
				if (min == max) {
					// System.out.println("min equals max");
					resultNode
							.setZipCode(leadZero(current.getZipCode()));
				} else {
					// System.out.println("minmax are diff");
					String range = leadZero(min) + ":" + leadZero(max);
					resultNode.setZipCode(range);
					// System.out.println("range "+ range);
				}
				resultNode.setZone(current.getZone());
				result.add(resultNode);
				max = next.getZipCode();
				min = next.getZipCode();
			}
			/*
			 * if(max>98000 || min > 98000){ System.out.println("max " + max +
			 * " min " + min); }
			 */
		}
		ZipCodeZone last = zipszones.get(zipszones.size() - 1);
		if (last.getZipCode() != max) {
			System.out.println("last node");
			resultNode = new ZipZoneResult();
			resultNode.setZipCode(leadZero(last.getZipCode()));
			resultNode.setZone(last.getZone());
			result.add(resultNode);
		}
		return result;
	}

	private static List<ZipCodeZone> readZipsFromCSV(String filename) {
		// System.out.println("in readZipsFrom");
		List<ZipCodeZone> zipszones = new ArrayList<ZipCodeZone>();
		Path pathTofile = Paths.get(filename);
		try (BufferedReader br = Files.newBufferedReader(pathTofile,
				StandardCharsets.US_ASCII)) {
			String line = br.readLine();
			while (line != null) {
				String[] attributes = line.split(",");
				// System.out.println("attributes" + attributes[5] + " "+
				// attributes[7]);
				ZipCodeZone zone = createZone(attributes);
				// System.out.println("adding to list");
				if(zone != null){
					zipszones.add(zone);
					line = br.readLine();
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return zipszones;
	}

	private static ZipCodeZone createZone(String[] metadata) {
		if(NumberUtils.isDigits(metadata[0])){
			int zipcode = Integer.parseInt(metadata[0]);
			System.out.println("zip code" + zipcode);
			String zone = metadata[1];
			return new ZipCodeZone(zipcode, zone);
		}
		return null;
	}
	
	private static String leadZero(int zip){
		String leadZip;
		if(Integer.toString(zip).length() < 5){
		if(Integer.toString(zip).length() == 4){
			leadZip = "0" + zip;
		}else{
			
			leadZip = "00" + zip;
		}
		}else{
			leadZip= Integer.toString(zip);
		}
		return leadZip;
	}
	
	private static void readCSV() throws IOException{
		
		Reader in;
		Iterable<CSVRecord> records;
		//parsing CSV for carrier range
		 in = new FileReader(carrierRangeCSV);
		 records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		
		/* reader = Files.newBufferedReader(Paths.get(carrierRangeCSV), Charset.defaultCharset());
		 csvParser = new CSVParser(reader, CSVFormat.RFC4180.withHeader("Low Range", "High Range", "Carrier")
				.withIgnoreHeaderCase()
				.withTrim());*/
		carrierrangeZips = new ArrayList<>();
		for(CSVRecord record: records){
			//accessing values by names assigned to each column
			CarrierRange range = new CarrierRange(Integer.parseInt(record.get(0)), Integer.parseInt(record.get(1)), record.get(2));
			carrierrangeZips.add(range);
		}
		//csvParser.close();
		
		//parsing CSV for master zip
		 in = new FileReader(masterZipsCSV);
		 records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		/* reader = Files.newBufferedReader(Paths.get(masterZipsCSV), Charset.defaultCharset());
		 csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("Postal Code")
				 .withIgnoreHeaderCase()
					.withTrim());*/
		 masterZips = new ArrayList<>();
		for(CSVRecord record: records){
			//accessing values by names assigned to each column
			MasterZip range = new MasterZip(Integer.parseInt(record.get(0)));
			masterZips.add(range);
		}
		//csvParser.close();
		
		//parsing CSV for carrier zip terminal SEKW
		in = new FileReader(SEKWZipsCSV);
		 records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		 /*reader = Files.newBufferedReader(Paths.get(SEKWZipsCSV), Charset.defaultCharset());
		 csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("ServZipLo","ServZipHi","ServTerm", "ServDorIOut"
				 , "ServDaysOut", "ServDaysIn", "ServDorIIn")
				 .withIgnoreHeaderCase()
					.withTrim());*/
		 sekwZips = new ArrayList<>();
		 for(CSVRecord record: records){
			//accessing values by names assigned to each column
			 if(StringUtils.isNumeric(record.get(0)) && StringUtils.isNumeric(record.get(1))){
				CarrierZipTerminal range = new CarrierZipTerminal(Integer.parseInt(record.get(0)),
						Integer.parseInt(record.get(1)),
								record.get(2),
										record.get(3),
												Integer.parseInt(record.get(4)),
														Integer.parseInt(record.get(5)),
																record.get(6));
				sekwZips.add(range);
			 }
		}
		//csvParser.close();
		
		
		//parsing CSV for carrier zip terminal PDIB
			in = new FileReader(PDIBZipsCSV);
			records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		 /*reader = Files.newBufferedReader(Paths.get(PDIBZipsCSV), Charset.defaultCharset());
		 csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("ServZipLo","ServZipHi","ServTerm", "ServDorIOut"
				 , "ServDaysOut", "ServDaysIn", "ServDorIIn")
				 .withIgnoreHeaderCase()
					.withTrim());*/
		 pdibZips = new ArrayList<>();
		 for(CSVRecord record: records){
			//accessing values by names assigned to each column
			 if(StringUtils.isNumeric(record.get(0)) && StringUtils.isNumeric(record.get(1))){
				CarrierZipTerminal range = new CarrierZipTerminal(Integer.parseInt(record.get(0)),
						Integer.parseInt(record.get(1)),
								record.get(2),
										record.get(3),
												Integer.parseInt(record.get(4)),
														Integer.parseInt(record.get(5)),
																record.get(6));
				pdibZips.add(range);
			 }
		}
		//csvParser.close();
		
		
	}

}
