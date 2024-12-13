import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileListMaker {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<String> list = new ArrayList<>();
    private static boolean needsToBeSaved = false;
    private static String currentFilename = null;

    public static void main(String[] args) {
        String option;
        do {
            printMenu();
            option = scanner.nextLine().toUpperCase();
            switch (option) {
                case "A":
                    addItem();
                    break;
                case "D":
                    deleteItem();
                    break;
                case "I":
                    insertItem();
                    break;
                case "M":
                    moveItem();
                    break;
                case "O":
                    openFile();
                    break;
                case "S":
                    saveFile();
                    break;
                case "C":
                    clearList();
                    break;
                case "V":
                    viewList();
                    break;
                case "Q":
                    quitProgram();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (!option.equals("Q"));
    }

    private static void printMenu() {
        System.out.println("\nFile List Maker Menu:");
        System.out.println("A - Add an item");
        System.out.println("D - Delete an item");
        System.out.println("I - Insert an item");
        System.out.println("M - Move an item");
        System.out.println("O - Open a list file from disk");
        System.out.println("S - Save the current list file to disk");
        System.out.println("C - Clear the current list");
        System.out.println("V - View the list");
        System.out.println("Q - Quit");
        System.out.print("Choose an option: ");
    }

    private static void addItem() {
        System.out.print("Enter item to add: ");
        String item = scanner.nextLine();
        list.add(item);
        needsToBeSaved = true;
    }

    private static void deleteItem() {
        viewList();
        System.out.print("Enter the index of the item to delete: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index >= 0 && index < list.size()) {
            list.remove(index);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index.");
        }
    }

    private static void insertItem() {
        viewList();
        System.out.print("Enter the index to insert at: ");
        int index = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter item to insert: ");
        String item = scanner.nextLine();
        if (index >= 0 && index <= list.size()) {
            list.add(index, item);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index.");
        }
    }

    private static void moveItem() {
        viewList();
        System.out.print("Enter the index of the item to move: ");
        int oldIndex = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the index to move it to: ");
        int newIndex = Integer.parseInt(scanner.nextLine());
        if (oldIndex >= 0 && oldIndex < list.size() && newIndex >= 0 && newIndex <= list.size()) {
            String item = list.remove(oldIndex);
            list.add(newIndex, item);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index.");
        }
    }

    private static void openFile() {
        if (needsToBeSaved) {
            System.out.print("You have unsaved changes. Save before loading? (Y/N): ");
            String choice = scanner.nextLine().toUpperCase();
            if (choice.equals("Y")) {
                saveFile();
            }
        }
        System.out.print("Enter filename to open: ");
        String filename = scanner.nextLine();
        try {
            list = Files.readAllLines(Paths.get(filename));
            currentFilename = filename;
            needsToBeSaved = false;
            System.out.println("File loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error opening file: " + e.getMessage());
        }
    }

    private static void saveFile() {
        if (currentFilename == null) {
            System.out.print("Enter filename to save as: ");
            currentFilename = scanner.nextLine();
            if (!currentFilename.endsWith(".txt")) {
                currentFilename += ".txt";
            }
        }
        try {
            Files.write(Paths.get(currentFilename), list);
            needsToBeSaved = false;
            System.out.println("File saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private static void clearList() {
        System.out.print("Are you sure you want to clear the list? (Y/N): ");
        String choice = scanner.nextLine().toUpperCase();
        if (choice.equals("Y")) {
            list.clear();
            needsToBeSaved = true;
        }
    }

    private static void viewList() {
        if (list.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + ". " + list.get(i));
            }
        }
    }

    private static void quitProgram() {
        if (needsToBeSaved) {
            System.out.print("You have unsaved changes. Save before quitting? (Y/N): ");
            String choice = scanner.nextLine().toUpperCase();
            if (choice.equals("Y")) {
                saveFile();
            }
        }
        System.out.println("Goodbye!");
    }
}

