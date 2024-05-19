package main.student_manager;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentManagerTest {
	
	StudentManager studentManager;
	
	@BeforeAll
	public static void setupAll() {
		System.out.println("Should Print Before All Tests");
	}
	
	@BeforeEach
	public void setup() {
		studentManager = new StudentManager();
	}

	@Test
	void shouldCreateStudent() {
		studentManager.addStudent(123456, "John", "Doe", "0123456789");
		Assertions.assertFalse(studentManager.getAllStudentsInfo().isEmpty());
		Assertions.assertEquals(1,studentManager.getAllStudentsInfo().size());
		Assertions.assertTrue(studentManager.getAllStudentsInfo().stream()
				.filter(student -> student.getStudentID() == 123456 && 
						student.getFirstName().equals("John") &&
						student.getLastName().equals("Doe") &&
						student.getPhoneNumber().equals("0123456789"))
				.findAny()
				.isPresent());
	}

	@Test
	@DisplayName("Should Not Create Student When First Name is Null")
	void shouldThrowRuntimeExceptionWhenFirstNameIsNull() {
		Assertions.assertThrows(RuntimeException.class, () ->{
			studentManager.addStudent(123456, null, "Doe", "0123456789");
		});
	}
	
	@Test
	@DisplayName("Should Not Create Student When Last Name is Null")
	void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
		Assertions.assertThrows(RuntimeException.class, () ->{
			studentManager.addStudent(123456, "John", null , "0123456789");
		});
	}
	
	@Test
	@DisplayName("Should Not Create Student When Phone Number is Null")
	void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull() {
		Assertions.assertThrows(RuntimeException.class, () ->{
			studentManager.addStudent(123456, "John", "Doe", null);
		});
	}
	
	@Test
	@DisplayName("Should Create Student Only on MAC OS")
	@EnabledOnOs( value= OS.MAC, disabledReason = "Enabled only on MAC OS")
	void shouldCreateStudentOnlyOnMac() {
		studentManager.addStudent(123456,"John", "Doe", "0123456789");
		Assertions.assertFalse(studentManager.getAllStudentsInfo().isEmpty());
		Assertions.assertEquals(1,studentManager.getAllStudentsInfo().size());
		Assertions.assertTrue(studentManager.getAllStudentsInfo().stream()
				.filter(student -> student.getStudentID() == 123456 && 
				student.getFirstName().equals("John") &&
				student.getLastName().equals("Doe") &&
				student.getPhoneNumber().equals("0123456789"))
				.findAny()
				.isPresent());
	}
	
	@Test
	@DisplayName("Should Create Student Only on Windows OS")
	@DisabledOnOs( value= OS.WINDOWS, disabledReason = "Disabled on Windows OS")
	void shouldCreateStudentOnlyOnWindow() {
		studentManager.addStudent(123456,"John", "Doe", "0123456789");
		Assertions.assertFalse(studentManager.getAllStudentsInfo().isEmpty());
		Assertions.assertEquals(1,studentManager.getAllStudentsInfo().size());
		Assertions.assertTrue(studentManager.getAllStudentsInfo().stream()
				.filter(student -> student.getStudentID() == 123456 && 
				student.getFirstName().equals("John") &&
				student.getLastName().equals("Doe") &&
				student.getPhoneNumber().equals("0123456789"))
				.findAny()
				.isPresent());
	}
	
	@Test
	@DisplayName("Test Student Creation on Developer Machine")
	 void shouldTestStudentCreationOnDEV() {
		Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")));
		studentManager.addStudent(123456, "John", "Doe", "0123456789");
		assertFalse(studentManager.getAllStudentsInfo().isEmpty());
		assertEquals( 1, studentManager.getAllStudentsInfo().size());
	}
	
	@Nested
	class RepeatedNestedTest {
		@DisplayName("Repeat Student Creation Test 5 Times")
		@RepeatedTest(value = 5, name = "Repeating Student Creation Test {currentRepetition} of {totalRepetitions}")
		 void shouldTestStudentCreationRepeatedly() {
			studentManager.addStudent(123456, "John", "Doe", "0123456789");
			assertFalse(studentManager.getAllStudentsInfo().isEmpty());
			assertEquals( 1, studentManager.getAllStudentsInfo().size());
		}
	}
	
	@Nested
	class ParameterizedNestedTest {
		@DisplayName("Repeat Student Creation Test 5 Times")
		@ParameterizedTest
		@ValueSource(strings = {"0123456789", "0123456789", "0123456789"})
		 void shouldTestStudentCreationUsingValueSource(String phoneNumber) {
			studentManager.addStudent(123456, "John", "Doe", "0123456789");
			assertFalse(studentManager.getAllStudentsInfo().isEmpty());
			assertEquals( 1, studentManager.getAllStudentsInfo().size());
		}
		
		@DisplayName("Method Source Case - StudentID should match the required Format")
		@ParameterizedTest
		@ValueSource(strings = {"123456", "123465", "213789"})
		 void shouldTestStudentCreationUsingMethodSource(int studentID) {
			studentManager.addStudent(123456, "John", "Doe", "0123456789");
			assertFalse(studentManager.getAllStudentsInfo().isEmpty());
			assertEquals(1, studentManager.getAllStudentsInfo().size());
		}
		
		@DisplayName("Method Source Case - Phone Number should match the required Format")
		@ParameterizedTest
		@MethodSource("phoneNumberList")
		@ValueSource(strings = {"0123456789", "0123456789", "0123456789"})
		 void shouldTestStudentCreationUsingMethodSource(String phoneNumber) {
			studentManager.addStudent(123456, "John", "Doe", "0123456789");
			assertFalse(studentManager.getAllStudentsInfo().isEmpty());
			assertEquals(1, studentManager.getAllStudentsInfo().size());
		}
		
		private static List<String> phoneNumberList(){
			return Arrays.asList("0123456789","0123456798","0123456897");
		}
		
		@DisplayName("CSV Source Case - Phone Number should match the required Format")
		@ParameterizedTest
		@CsvSource({"0123456789", "0123456798","0123456897"})
		 void shouldTestStudentCreationUsingCSVSource(String phoneNumber) {
			studentManager.addStudent(123456, "John", "Doe", "0123456789");
			assertFalse(studentManager.getAllStudentsInfo().isEmpty());
			assertEquals( 1, studentManager.getAllStudentsInfo().size());
		}
		
		@DisplayName("CSV File Source Case - Phone Number should match the required Format")
		@ParameterizedTest
		@CsvFileSource(resources = "data.csv")
		 void shouldTestStudentCreationUsingCSVFileSource(String phoneNumber) {
			studentManager.addStudent(123456, "John", "Doe", "0123456789");
			assertFalse(studentManager.getAllStudentsInfo().isEmpty());
			assertEquals( 1, studentManager.getAllStudentsInfo().size());
		}
	}
	
	@Test
	@DisplayName("Test Should Be Disabled")
	@Disabled
	public void shouldBeDisabled() {
	   throw new RuntimeException("Test Should Not be executed");
	}
	
	@AfterEach
	public void tearDown() {
		System.out.println("Should Execute After Each Test");
	}
	
	@AfterAll
	public static void tearDownAll() {
		System.out.println("Should be Execute at the end of the Test");
	}
}
