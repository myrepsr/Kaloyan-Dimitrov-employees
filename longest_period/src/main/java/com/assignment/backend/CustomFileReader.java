package com.assignment.backend;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CustomFileReader extends BufferedReader {
    private static CustomFileReader customFileReader;
    private static Reader reader;
    private static String pathToFile;
    private static CustomDateTimeFormatter customDateTimeFormatter;
    public CustomFileReader(Reader in) {
        super(in);
    }

    public static CustomFileReader getInstance(String pathToFile) throws FileNotFoundException {
        if(customFileReader == null) {
            CustomFileReader.pathToFile = pathToFile;
            reader = new FileReader(pathToFile);
            customFileReader = new CustomFileReader(reader);
            customDateTimeFormatter = new CustomDateTimeFormatter();
        }

        return customFileReader;
    }

    public List<DataRecord> readFile() throws IOException {
        long lines = 0;
        String line = "";

        Path path = Paths.get(pathToFile);
        lines = Files.lines(path).parallel().count();

        List<DataRecord> dataRecordList = new ArrayList<>((int) lines);

        LocalDate dateFrom;
        LocalDate dateTo;

        readLine();

        int i = 0;
        while((line = readLine()) != null){
            String[] row = line.split(",");
            dateFrom = customDateTimeFormatter.parseFromStringToDate(row[2]);
            System.out.println(i + ". " + "Date From: " + dateFrom);

            if(row[3].equals("NULL")) {
                dateTo = LocalDate.now();
                System.out.println(i + ". " + "Date To: " + dateTo);
            }
            else {
                dateTo = customDateTimeFormatter.parseFromStringToDate(row[3]);
                System.out.println(i + ". " + "Date To: " + dateTo);
            }

            DataRecord dataRecord = new DataRecord(Integer.parseInt(row[0]),
                    Integer.parseInt(row[1]), dateFrom, dateTo);

            dataRecordList.add(dataRecord);
            i++;
        }

        dataRecordList.forEach(System.out::println);

        return dataRecordList;
    }
}
