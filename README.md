## JUnit Small Project Documentation
This project is designed to provide examples of using JUnit for unit testing in Java. It includes various test cases that cover different functionalities and scenarios to help you understand the basics and some advanced features of JUnit.

# Understanding the Tests
The StudentManagerTest class contains various test methods demonstrating different JUnit functionalities. Below is an overview of the key parts:

Basic Test Setup and Teardown
java
Copy code
@BeforeAll
public static void setupAll() {
    System.out.println("Should Print Before All Tests");
}

@BeforeEach
public void setup() {
    studentManager = new StudentManager();
}

@AfterEach
public void tearDown() {
    System.out.println("Should Execute After Each Test");
}

@AfterAll
public static void tearDownAll() {
    System.out.println("Should be Execute at the end of the Test");
}
Simple Test Example
java
Copy code
@Test
void shouldCreateStudent() {
    studentManager.addStudent(123456, "John", "Doe", "0123456789");
    Assertions.assertFalse(studentManager.getAllStudentsInfo().isEmpty());
    Assertions.assertEquals(1, studentManager.getAllStudentsInfo().size());
    Assertions.assertTrue(studentManager.getAllStudentsInfo().stream()
            .filter(student -> student.getStudentID() == 123456 && 
                    student.getFirstName().equals("John") &&
                    student.getLastName().equals("Doe") &&
                    student.getPhoneNumber().equals("0123456789"))
            .findAny()
            .isPresent());
}
Conditional and Parameterized Tests
java
Copy code
@Test
@DisplayName("Should Create Student Only on MAC OS")
@EnabledOnOs(value = OS.MAC, disabledReason = "Enabled only on MAC OS")
void shouldCreateStudentOnlyOnMac() {
    studentManager.addStudent(123456, "John", "Doe", "0123456789");
    Assertions.assertFalse(studentManager.getAllStudentsInfo().isEmpty());
    Assertions.assertEquals(1, studentManager.getAllStudentsInfo().size());
    Assertions.assertTrue(studentManager.getAllStudentsInfo().stream()
            .filter(student -> student.getStudentID() == 123456 && 
            student.getFirstName().equals("John") &&
            student.getLastName().equals("Doe") &&
            student.getPhoneNumber().equals("0123456789"))
            .findAny()
            .isPresent());
}

@ParameterizedTest
@ValueSource(strings = {"0123456789", "0123456798", "0123456897"})
@DisplayName("CSV Source Case - Phone Number should match the required Format")
void shouldTestStudentCreationUsingCSVSource(String phoneNumber) {
    studentManager.addStudent(123456, "John", "Doe", "0123456789");
    assertFalse(studentManager.getAllStudentsInfo().isEmpty());
    assertEquals(1, studentManager.getAllStudentsInfo().size());
}
Nested Tests
java
Copy code
@Nested
class RepeatedNestedTest {
    @DisplayName("Repeat Student Creation Test 5 Times")
    @RepeatedTest(value = 5, name = "Repeating Student Creation Test {currentRepetition} of {totalRepetitions}")
    void shouldTestStudentCreationRepeatedly() {
        studentManager.addStudent(123456, "John", "Doe", "0123456789");
        assertFalse(studentManager.getAllStudentsInfo().isEmpty());
        assertEquals(1, studentManager.getAllStudentsInfo().size());
    }
}
Disabled Tests
java
Copy code
@Test
@DisplayName("Test Should Be Disabled")
@Disabled
public void shouldBeDisabled() {
   throw new RuntimeException("Test Should Not be executed");
}
