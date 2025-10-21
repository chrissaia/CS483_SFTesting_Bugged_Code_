import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;
import java.util.Scanner;


class A3BToolkitTests {    
	// MENU TESTS
    @Test
    void menu_test_if_b_works() {
        assertThrows(InputMismatchException.class, () -> {
            System.setIn(new ByteArrayInputStream("b\n".getBytes()));
            GradeToolkit.main(new String[0]);
        });
    }

    @Test
    void menu_test_if_option3_works() {
        assertDoesNotThrow(() -> {
            System.setIn(new ByteArrayInputStream("3\n78\n".getBytes()));
            GradeToolkit.main(new String[0]);
        });
    }




    // OPTION 1: GPA TESTS
    @Test
    void option1_basic_test() {
        String input = String.join("\n", "10", "60", "100", "30", "-1") + "\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertDoesNotThrow(() -> GradeToolkit.calculateGPA(sc));
    }

    @Test
    void option1_test_GPA_accuracy() {
        // grades: 50, 60, 100, 30, then stop
        String input = String.join("\n", "50", "60", "100", "30", "-1") + "\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));

        assertEquals(60.0, GradeToolkit.calculateGPA(sc));
    }

    @Test
    void option1_enter_zero_grades() {
        Scanner sc = new Scanner(new ByteArrayInputStream("-1\n".getBytes()));
        assertThrows(IllegalArgumentException.class, () -> GradeToolkit.calculateGPA(sc));
    }

    @Test
    void option1_enter_one_grade() {
        Scanner sc = new Scanner(new ByteArrayInputStream("1\n-1\n".getBytes()));
        assertDoesNotThrow(() -> GradeToolkit.calculateGPA(sc));
    }






    // OPTION 2: PASS/FAIL TESTS
    @Test
    void option2_test_negative() {
        Scanner sc = new Scanner(new ByteArrayInputStream("-100\n".getBytes()));
        assertDoesNotThrow(() -> GradeToolkit.PassOrFail(sc));
    }

    @Test
    void option2_out_of_bounds() {
        String input = String.join("\n", "90", "70", "100", "120", "60", "10") + "\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> GradeToolkit.PassOrFail(sc));
    }

        @Test
    void option2_basic_test() {
        String input = String.join("\n", "90", "70", "100", "80", "60") + "\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        assertEquals("Pass", GradeToolkit.PassOrFail(sc));
    }

    @Test
    void option2_test_fail_case() {
        String input = String.join("\n", "10", "20", "30", "40", "50") + "\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        assertEquals("Fail", GradeToolkit.PassOrFail(sc));
    }





    // OPTION 3: GRADE TO LETTER TESTS
    @Test
    void option3_basic_test() {
        Scanner sc = new Scanner(new ByteArrayInputStream("78\n".getBytes()));
        assertDoesNotThrow(() -> GradeToolkit.grade_To_letter(sc));
    }
    
    @Test
    void option3_letter_grades() {
        assertEquals("A", GradeToolkit.grade_To_letter(new Scanner(new ByteArrayInputStream("95".getBytes()))));
        assertEquals("B", GradeToolkit.grade_To_letter(new Scanner(new ByteArrayInputStream("85".getBytes()))));
        assertEquals("C", GradeToolkit.grade_To_letter(new Scanner(new ByteArrayInputStream("75".getBytes()))));
        assertEquals("D", GradeToolkit.grade_To_letter(new Scanner(new ByteArrayInputStream("65".getBytes()))));
        assertEquals("F", GradeToolkit.grade_To_letter(new Scanner(new ByteArrayInputStream("40".getBytes()))));
    }

    @Test
    void option3_test_negatives() {
        assertEquals("F", GradeToolkit.grade_To_letter(new Scanner(new ByteArrayInputStream("-10".getBytes())))); // documents current wrong behavior
    }

    @Test
    void option3_test_above_100() {
        assertEquals("A", GradeToolkit.grade_To_letter(new Scanner(new ByteArrayInputStream("100000".getBytes())))); // documents current wrong behavior
    }





    //OPTION 4: WEIGHTED GRADE TESTS
    @Test
    void option4_basic_test() {
        String input = String.join("\n", ".5", ".5", "90", "90") + "\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertDoesNotThrow(() -> GradeToolkit.calculate_weighted_grade(sc));
    }

    @Test
    void option4_test_invalid_float() {
        String input = String.join("\n", ".1", ".5", ".05.") + "\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertThrows(IllegalArgumentException.class, () -> GradeToolkit.calculate_weighted_grade(sc));
    }

    @Test
    void option4_test_weighted_average_accuracy() {
        // weights: 0.5, 0.5 | grades: 80, 90 = 0.5*80 + 0.5*90 = 85.0
        String input = String.join("\n", ".5", ".5", "80", "90") + "\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));

        assertEquals(85.0, GradeToolkit.calculate_weighted_grade(sc));
    }





    // OPTION 5: QPA TESTS

    @Test
    void option5_basic_test() {
        String input = String.join("\n", "3", "A", "3", "B", "4", "C", "3") + "\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertDoesNotThrow(() -> GradeToolkit.calculate_qpa(sc));
    }

    @Test
    void option5_test_qpa_accuracy() {
        
        /*
          A(4)*4 + B(3)*5 + C(2)*3 + C(2)*4 = 16+15+6+8 = 45
          total credits = 16
          expected = 45/16 = 2.8125
        */

        String input = String.join("\n", "4", "A", "4", "B", "5", "C", "3", "C", "4") + "\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        // current program prints 0.0; after fix it should return 2.8125
        assertEquals(2.8125, GradeToolkit.calculate_qpa(sc));
    }

    @Test
    void option5_test_invalid_grade() {
        String input = String.join("\n", "2", "Z", "A", "3", "?", "C", "4") + "\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertDoesNotThrow(() -> GradeToolkit.calculate_qpa(sc));
    }






    // OPTION 6: FINAL GRADE TESTS
    @Test
    void option6_basic_test() {
        String input = String.join("\n", "83", "30", "90") + "\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        assertDoesNotThrow(() -> GradeToolkit.calculate_finals_grade_needed(sc));
    }

    @Test
    void option6_test_over100_case() {
        String input = String.join("\n", "83", "30", "90") + "\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));

        // current=83, weight=30%, desired=90 -> over 100% needed
        assertEquals(100, GradeToolkit.calculate_finals_grade_needed(sc));
    }

    @Test
    void option6_test_already_reached_case() {
        String input = String.join("\n", "95", "10", "80") + "\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        // current=95, weight=10%, desired=80 -> already reached
        assertEquals(0, GradeToolkit.calculate_finals_grade_needed(sc));
    }

    @Test
    void option6_test_exact_goal_case() {
        String input = String.join("\n", "80", "25", "85") + "\n";
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        // current=80, weight=25%, desired=85 -> (85 - 80*0.75)/0.25 = 100
        assertEquals(100.0, GradeToolkit.calculate_finals_grade_needed(sc));
    }
}

