import java.util.Comparator;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Main {

  static String filename = "db.csv";

  // =========add============
  public static void add(String[] parts) {
    if (parts.length != 2) {
      System.out.println("wrong field count");
      return;
    }

    String[] text2 = parts[1].split(";");

    if (text2.length != 6) {
      System.out.println("wrong field count");
      return;
    }

    int id;
    try {
      id = Integer.parseInt(text2[0]);
    } catch (NumberFormatException e) {
      System.out.println("wrong id");
      return;
    }
    if (id < 100 || id > 999) {
      System.out.println("wrong id");
      return;
    }

    try {
      Scanner scanner = new Scanner(new File(filename));
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] fields = line.split(";");
        if (fields[0].equals(text2[0])) {
          System.out.println("wrong id");
          return;
        }
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      // if file "db.csv" doesn't exist, continue adding
    }

    String city = text2[1];
    city = city.toLowerCase();
    String[] words = city.split("\\s+");
    StringBuilder sb = new StringBuilder();
    for (String word : words) {
      sb.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
    }
    city = sb.toString().trim();

    if (!text2[2].matches("\\d{2}/\\d{2}/\\d{4}")) {
      System.out.println("wrong date");
      return;
    }
    Date date;
    try {
      DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
      dateFormat.setLenient(false);
      date = dateFormat.parse(text2[2]);
    } catch (ParseException e) {
      System.out.println("wrong date");
      return;
    }

    int days;
    try {
      days = Integer.parseInt(text2[3]);
    } catch (NumberFormatException e) {
      System.out.println("wrong day count");
      return;
    }

    double price;
    try {
      price = Double.parseDouble(text2[4]);
    } catch (NumberFormatException e) {
      System.out.println("wrong price");
      return;
    }

    String vehicle = text2[5].toUpperCase();
    if (!vehicle.equals("TRAIN") && !vehicle.equals("PLANE") && !vehicle.equals("BUS") && !vehicle.equals("BOAT")) {
      System.out.println("wrong vehicle");
      return;
    }

    String record = id + ";" + city + ";" + text2[2] + ";" + days + ";" + String.format("%.2f", price) + ";" + vehicle;

    try {
      FileWriter writer = new FileWriter(filename, true);
      writer.write(record + "\n");
      writer.close();
    } catch (IOException e) {
      System.out.println("Error writing to file");
      return;
    }

    System.out.println("added");
  }

  // =========del============
  public static void del(String[] parts) {
    int id;
    try {
      id = Integer.parseInt(parts[1]);
    } catch (NumberFormatException e) {
      System.out.println("wrong id");
      return;
    }

    if (id < 100 || id > 999) {
      System.out.println("wrong id");
      return;
    }

    boolean found = false;
    try {
      Scanner scanner = new Scanner(new File(filename));
      List<String> lines = new ArrayList<>();
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] fields = line.split(";");
        if (fields[0].equals(parts[1])) {
          found = true;
        } else {
          lines.add(line);
        }
      }
      scanner.close();

      if (!found) {
        System.out.println("wrong id");
        return;
      }

      FileWriter writer = new FileWriter(filename);
      for (String line : lines) {
        writer.write(line + "\n");
      }
      writer.close();

      System.out.println("deleted");
    } catch (FileNotFoundException e) {
      System.out.println("Database file not found");
    } catch (IOException e) {
      System.out.println("Error while writing to database file");
    }
  }

  // =========edit============
  public static void edit(String[] parts) {
    if (parts.length != 2) {
      System.out.println("wrong field count");
      return;
    }

    String[] text2 = parts[1].split(";");

    if (text2.length != 6) {
      System.out.println("wrong field count");
      return;
    }

    int id;
    try {
      id = Integer.parseInt(text2[0]);
    } catch (NumberFormatException e) {
      System.out.println("wrong id");
      return;
    }
    if (id < 100 || id > 999) {
      System.out.println("wrong id");
      return;
    }

    boolean idFound = false;
    StringBuilder newRecord = new StringBuilder();

    try {
      Scanner scanner = new Scanner(new File(filename));
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] fields = line.split(";");
        if (fields[0].equals(text2[0])) {
          idFound = true;

          newRecord.append(text2[0]).append(";");
          for (int i = 1; i < text2.length; i++) {
            if (text2[i].isEmpty()) {
              newRecord.append(fields[i]).append(";");
            } else {
              if (i == 1) { // capitalize city name
                String city = text2[1];
                city = city.toLowerCase();
                String[] words = city.split("\\s+");
                StringBuilder sb = new StringBuilder();
                for (String word : words) {
                  sb.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
                }
                newRecord.append(sb.toString().trim()).append(";");
              } else if (i == 5) { // convert vehicle name to uppercase
                newRecord.append(text2[i].toUpperCase()).append(";");
              } else {
                newRecord.append(text2[i]).append(";");
              }
            }
          }
          newRecord.setLength(newRecord.length() - 1);
          newRecord.append("\n");
        } else {
          newRecord.append(line).append("\n");
        }
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      // if file "db.csv" doesnt exist, continue editing
    }

    if (!idFound) {
      System.out.println("wrong id");
      return;
    }

    String vehicle = text2[5].toUpperCase();
    if (!vehicle.equals("TRAIN") && !vehicle.equals("PLANE") && !vehicle.equals("BUS") && !vehicle.equals("BOAT")) {
      System.out.println("wrong vehicle");
      return;
    }

    try {
      FileWriter writer = new FileWriter(filename);
      writer.write(newRecord.toString());
      writer.close();
    } catch (IOException e) {
      System.out.println("Error writing to file");
      return;
    }

    System.out.println("changed");
  }

  // =========print============
  public static void print(String[] parts) {
    String line;
    String csvSplitBy = ";";
    String header = "------------------------------------------------------------\n"
        + "ID  City                 Date         Days     Price Vehicle\n"
        + "------------------------------------------------------------\n";
    System.out.print(header);

    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

      while ((line = br.readLine()) != null) {
        String[] travel = line.split(csvSplitBy);
        System.out.format("%-4s%-21s%-11s%6s%10s %-7s\n", travel[0], travel[1], travel[2], travel[3], travel[4],
            travel[5]);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    String footer = "------------------------------------------------------------";
    System.out.println(footer);
  }

  // =========sort============
  public static void sort(String[] parts) {
    List<String> records = new ArrayList<>();
    try {
      Scanner scanner = new Scanner(new File(filename));
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        records.add(line);
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      // if file "db.csv" doesnt exist, continue sorting
    }

    Collections.sort(records, new Comparator<String>() {
      DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

      public int compare(String o1, String o2) {
        try {
          Date date1 = dateFormat.parse(o1.split(";")[2]);
          Date date2 = dateFormat.parse(o2.split(";")[2]);
          return date1.compareTo(date2);
        } catch (ParseException e) {
          e.printStackTrace();
        }
        return 0;
      }
    });

    try {
      FileWriter writer = new FileWriter(filename);
      for (String record : records) {
        writer.write(record + "\n");
      }
      writer.close();
    } catch (IOException e) {
      System.out.println("Error writing to file");
      return;
    }

    System.out.println("sorted");
  }

  // =========find============
  public static void find(String[] parts) {
    if (parts.length != 2) {
      System.out.println("Usage: java FindPriceCommand <price>");
      return;
    }
    double price;
    try {
      price = Double.parseDouble(parts[1]);
    } catch (NumberFormatException e) {
      System.out.println("wrong price");
      return;
    }
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      String line;
      String header = "------------------------------------------------------------\n"
          + "ID  City                 Date         Days     Price Vehicle\n"
          + "------------------------------------------------------------\n";
      System.out.print(header);
      while ((line = br.readLine()) != null) {
        String[] fields = line.split(";");
        double tripPrice = Double.parseDouble(fields[4]);
        if (tripPrice <= price) {
          System.out.printf("%-4s%-21s%-11s%6s%10s %-7s\n", fields[0], fields[1], fields[2], fields[3], fields[4],
              fields[5]);
        }
      }
      System.out.println("------------------------------------------------------------");
    } catch (IOException e) {
      System.out.println("Error reading file: " + filename);
      e.printStackTrace();
    }
  }

  // =========avg============
  public static void avg(String[] parts) {
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      String line;
      double sum = 0;
      int count = 0;
      while ((line = br.readLine()) != null) {
        String[] fields = line.split(";");
        sum += Double.parseDouble(fields[4]);
        count++;
      }
      double avg = sum / count;
      System.out.printf("average=%.2f\n", avg);
    } catch (IOException e) {
      System.out.println("Error reading file: " + filename);
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    String[] parts;
    Scanner sc = new Scanner(System.in);
    String input;

    while (sc.hasNextLine()) {
      input = sc.nextLine();
      parts = input.split(" ");
      if (parts[0].equals("add")) {
        add(parts);
      } else if (parts[0].equals("del")) {
        del(parts);
      } else if (parts[0].equals("edit")) {
        edit(parts);
      } else if (parts[0].equals("print")) {
        print(parts);
      } else if (parts[0].equals("sort")) {
        sort(parts);
      } else if (parts[0].equals("find")) {
        find(parts);
      } else if (parts[0].equals("avg")) {
        avg(parts);
      } else if (parts[0].equals("exit")) {
        break;
      } else {
        System.out.println("wrong command");
      }
    }
    sc.close();
  }
}
