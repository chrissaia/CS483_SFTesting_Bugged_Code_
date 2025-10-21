import java.util.Scanner;
import java.util.ArrayList;

public class GradeToolkit {
	
    /*
    Purpose: Calculate GPA based on user-entered grades
    input: grades from user
    output: GPA
    */
    static double calculateGPA(Scanner input) {
        int count = 0;
        double sum = 0;

        System.out.println("Enter grades (enter -1 to stop):");
        while (true) {
            System.out.print("Grade " + (count + 1) + ": ");
            int g = input.nextInt();
            if (g == -1) break;
            sum += g;
            count++;
        }

        if (count == 0) {
            System.out.println("No grades entered. GPA = 0.00");
            return 0.0;
        }

        double gpa = sum / count;
        System.out.printf("You entered %d grades. GPA = %.2f%n", count, gpa);
        return gpa;
    }



    
    /*
    Purpose: Enter assignment scores and determine pass/fail
    input: assignment scores from users
    output: pass/fail status
    */
    static String PassOrFail(Scanner input) {
        int[] assignments = new int[5];
        double total = 0;

        System.out.println("Enter five assignment scores:");
        for (int i = 0; i < 5; i++) {
            System.out.print("Assignment " + (i + 1) + ": ");
            assignments[i] = input.nextInt();
        }

        System.out.println("Your assignments:");
        for (int i = 0; i < 5; i++) {
            System.out.print(assignments[i] + " ");
            total += assignments[i];
        }
        System.out.println();

        double average = total / 5.0;
        if (average < 60) {
            System.out.println("Fail");
            return "Fail";
        } else {
            System.out.println("Pass");
            return "Pass";
        }
    }


   
    
    /**
     * Purpose: Convert numeric grade to letter grade
     * Input: Numeric grade from user
     * Output: Letter grade
     */
    static String grade_To_letter(Scanner input) {
        System.out.print("Enter numeric grade (out of 100): ");
        int grade = input.nextInt();

        if (grade >= 90) return "A";
        if (grade >= 80) return "B";
        if (grade >= 70) return "C";
        if (grade >= 60) return "D";
        return "F";
    }
    
    
    
    /*
     * Purpose: Calculate total grade with assignment categories of different weight.
     * Input: Assignment scores & weights from user
     * Output: Total/final grade
     */ 
    static float calculate_weighted_grade(Scanner input) {
        System.out.println("Weights of categories should be between 0-1, enter -1 to restart.");
        ArrayList<Float> weights = new ArrayList<>();
        float totalWeight = 0f;
        int i = 1;

        // collect weights until total is exactly 1.0
        while (true) {
            System.out.println("Enter weight of category " + i + ":");
            float w = input.nextFloat();                 // throws InputMismatchException on bad token (as expected by tests)
            if (w == -1f) {                              // restart
                weights.clear();
                totalWeight = 0f;
                i = 1;
                continue;
            }
            if (w < 0f || w > 1f || totalWeight + w > 1f) {
                System.out.println("Total of weights is greater than 1, invalid.");
                weights.clear();
                totalWeight = 0f;
                i = 1;
                continue;
            }
            weights.add(w);
            totalWeight += w;
            if (Math.abs(totalWeight - 1f) < 1e-6) break; // stop when sum is 1
            i++;
        }

        float totalGrade = 0f;
        if (!weights.isEmpty()) {
            for (int x = 0; x < weights.size(); x++) {
                System.out.println("Enter grade for category " + (x + 1) + " or -1 to restart:");
                float grade = input.nextFloat();
                if (grade == -1f) {
                    totalGrade = 0f;
                    x = -1; // restart grades from first category
                    continue;
                }
                totalGrade += grade * weights.get(x);
            }
            System.out.println("Your total grade is: " + totalGrade);
        } else {
            System.out.println("No values entered.");
        }
        return totalGrade;
    } 



    
    
    /*
     * Purpose: Calculate cumulative grade point average with a weight on credits
     * Input: Semester number, grade, credits
     * Output: Cumulative GPA.
     */
    static float calculate_qpa(Scanner input) {
        int totalCredits = 0;
        int totalQualityPoints = 0;

        System.out.println("Enter number of classes taken:");
        int classes = input.nextInt();

        for (int i = 1; i <= classes; i++) {
            System.out.println("Enter grade for class " + i + ": (A-F)");
            String grade = input.next(); // use next() to avoid newline issues

            while (!grade.equalsIgnoreCase("A") &&
                   !grade.equalsIgnoreCase("B") &&
                   !grade.equalsIgnoreCase("C") &&
                   !grade.equalsIgnoreCase("D") &&
                   !grade.equalsIgnoreCase("F")) {
                System.out.println("Grade entered is invalid. Reenter: (A-F)");
                grade = input.next();
            }

            int points;
            switch (grade.toUpperCase()) {
                case "A": points = 4; break;
                case "B": points = 3; break;
                case "C": points = 2; break;
                case "D": points = 1; break;
                default:  points = 0; break; // F
            }

            System.out.println("Enter credits for class " + i + ":");
            int credits = input.nextInt();
            while (credits <= 0) {
                System.out.println("Credits entered invalid. Reenter:");
                credits = input.nextInt();
            }

            totalCredits += credits;
            totalQualityPoints += points * credits;
        }

        float qpa = totalCredits == 0 ? 0f : (float) totalQualityPoints / (float) totalCredits;
        System.out.println("Your QPA is: " + qpa);
        System.out.println("Your total credits are: " + totalCredits);
        return qpa;
    }



    
    
	/*
     * Purpose: Calculate the grade needed on the final exam to achieve a desired overall course grade.
     * Input: Current grade, weight of final exam, desired overall grade
     * Output: Required final exam grade
     */
    public static double calculate_finals_grade_needed(Scanner input) {
        System.out.print("Enter your current grade as a number: ");
        double currentGrade = input.nextDouble();

        System.out.print("Enter the weight % of your final exam as a number: ");
        double finalWeight = input.nextDouble();

        System.out.print("Enter your desired overall grade as a number: ");
        double desiredGrade = input.nextDouble();

        finalWeight = finalWeight / 100.0; // Convert percentages to decimal weights
        double currentWeight = 1 - finalWeight;

        double requiredFinal = (desiredGrade - (currentGrade * currentWeight)) / finalWeight;

        if (requiredFinal > 100) {
            System.out.println("You’ll need over 100% on the final to reach your goal.");
            return 100;
        } else if (requiredFinal < 0) {
            System.out.println("You have already reached your desired grade! Even a 0 on the final would be fine.");
            return 0;
        } else {
            System.out.printf("You need to score at least %.2f%% on the final exam to get a %.2f%% overall grade.%n", requiredFinal, desiredGrade);
            return requiredFinal;
        }
    }
    
    
    
    
    
    

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to your school toolkit!\n Please select an option:\n 1. Calculate GPA\n 2. Pass or Fail\n 3. Grade to Letter \n 4. Calculate Weighted Grade\n 5. Calculate QPA\n 6. Calculate Final's Grade Needed");
        System.out.println("------------------------------");
        System.out.print("Enter option: ");

        int option = scanner.nextInt();
        
        switch (option) {  
            case 1:
                calculateGPA(scanner);
                break;
            case 2:
                PassOrFail(scanner);
                break;
            case 3:
                grade_To_letter(scanner);
                break;
            case 4:
                calculate_weighted_grade(scanner);
                break;
            case 5:
                calculate_qpa(scanner);
                break;
            case 6:
                calculate_finals_grade_needed(scanner);
                break;
            default:
                System.out.println("Invalid option. Please select 1, 2, 3, 4, 5, or 6.");
                break;
        }
    }
}

