package tracker;

import tracker.Controller.LearningPlatform;
import tracker.domain.CourseStatistics;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TextUI {

    private Scanner scanner;
    private LearningPlatform learningPlatform;
    final private Pattern emailPATTERN = Pattern.compile("\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    final private Pattern firstNamePATTERN = Pattern.compile("^[A-Za-z]([-']?[A-Za-z])+$");
    final private Pattern lastNamePATTERN = Pattern.compile("^[A-Za-z]([-' ]?[A-Za-z])+$");

    public TextUI() {
        this.learningPlatform = new LearningPlatform();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Learning Progress Tracker");

        while (true) {
            String input = scanner.nextLine();

            if (input.isEmpty() || input.isBlank()) {
                System.out.println("No input.");
            } else if (input.equals("exit")) {
                System.out.println("Bye!");
                break;
            } else if (input.equals("back")) {
                System.out.println("Enter 'exit' to exit the program.");
            } else if (input.equals("add students")) {
                addStudents();
            } else if (input.equals("list")) {
                listStudent();
            } else if (input.equals("add points")) {
                addPoints();
            } else if (input.equals("find")) {
                find();
            } else if (input.equals("statistics")) {
                getStats();
            } else if (input.equals("notify")) {
                notifyStudents();
            } else {
                System.out.println("Error: unknown command!");
            }
        }
    }


    public void addStudents() {
        int studentCount = 0;
        System.out.println("Enter student credentials or 'back' to return:");
        String input, email, firstName, lastName;

        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
            if (input.equals("back")) break;

            String[] inputs = input.split(" ");
            if (inputs.length < 3) {
                System.out.println("Incorrect credentials.");
            } else {
                email = inputs[inputs.length-1];
                firstName = inputs[0];
                lastName = String.join(" ", Arrays.copyOfRange(inputs, 1, inputs.length - 1));

                if (!firstNamePATTERN.matcher(firstName).matches()) {
                    System.out.println("Incorrect first name.");
                } else if (!lastNamePATTERN.matcher(lastName).matches()) {
                    System.out.println("Incorrect last name.");
                } else if (!emailPATTERN.matcher(email).matches()) {
                    System.out.println("Incorrect email.");
                } else if (!learningPlatform.uniqueEmailChecker(email)) {
                    System.out.println("This email is already taken.");
                } else {
                    learningPlatform.addStudentIntoPlatform(firstName, lastName, email);
                    System.out.println("The student has been added.");
                    studentCount++;
                }
            }
        }
        System.out.println(String.format("Total %s students have been added.", studentCount));
    }

    public void listStudent() {
        if (learningPlatform.getStudents() == null || learningPlatform.getStudents().size() == 0) {
            System.out.println("No students found");
        } else {
            System.out.println("Students:");
            for (int id : learningPlatform.getStudents().keySet()) {
                System.out.println(id);
            }
        }
    }

    public void addPoints() {
        System.out.println("Enter an id and points or 'back' to return");
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (input.equals("back")) break;

            String[] inputs = input.split(" ");
            if (inputs.length != 5) {
                System.out.println("Incorrect points format");
            } else {
                learningPlatform.addPointsToCourse(inputs);
            }
        }
    }

    public void find() {
        System.out.println("Enter an id or 'back' to return");
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (input.equals("back")) break;

            try {
                int inputID = Integer.parseInt(input);
                learningPlatform.findStudentCourseDetails(inputID);
            } catch (NumberFormatException e) {
                System.out.printf("No student is found for id=%s.\n", input);
            }
        }
    }

    public void getStats() {
        System.out.println("Type the name of a course to see details or 'back' to quit");
        learningPlatform.showAllCourseStats();

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (input.equals("back")) break;
            learningPlatform.showCourseLearners(input);
        }
    }

    private void notifyStudents() {
        learningPlatform.notifyCompletedStudent();
    }

}