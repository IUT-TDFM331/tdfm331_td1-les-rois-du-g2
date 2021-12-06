package fr.iut.tdfm331.td1.service;

import fr.iut.tdfm331.td1.model.Employee;
import fr.iut.tdfm331.td1.model.Meeting;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThat;

/**
 * Unit test file to test ListApiService class
 */
public class ListApiServiceTest
{

	private ListApiService service;

	@Before
	public void setupService()
	{
		service = new ListApiService();
	}

	/**
	 * Test to check if list of Meeting is ∞correctly generated
	 */
	@Test
	public void getListMeetingWithSuccess()
	{
		List<Meeting> listMeetings = service.getListMeetings();
		List<Meeting> expectedListMeetings = ListMeetingsGenerator.LIST_MEETINGS;
		assertThat(listMeetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedListMeetings.toArray()));
	}

	/**
	 * Test to check it list of Employee is correctly generated
	 */
	@Test
	public void getListEmployeeWithSuccess()
	{
		List<Employee> listEmployees = service.getListEmployees();
		List<Employee> expectedListEmployees = ListEmployeesGenerator.LIST_EMPLOYEES;
		assertThat(listEmployees, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedListEmployees.toArray()));

	}

	/**
	 * Test to check if a new Meeting object is correctly added to the list
	 */
	@Test
	public void addNewMeetingWithSuccess()
	{

		// Create list Employee
		List<Employee> listEmployees = Arrays.asList(new Employee("Baptiste", "baptiste@lamzone.com", 4), new Employee("Fanny", "fanny@lamzone.com", 10), new Employee("Vincent", "vincent@lamzone.com", 22));

		// Create list Meeting
		Meeting newMeeting = new Meeting("Réunion d'avancement", "Planck", "12/11/20", "15:30", "16:00", "Revues des dernières actions", listEmployees);

		// Add Meeting
		service.addMeeting(newMeeting);
		Assert.assertTrue(service.getListMeetings().contains(newMeeting));
	}

	/**
	 * Test to check if a selected Meeting is correctly removed from list
	 */
	@Test
	public void removeMeetingWithSuccess()
	{
		// Get first Meeting from list
		Meeting meetingToRemove = service.getListMeetings().get(0);
		service.getListMeetings().remove(meetingToRemove);
		Assert.assertFalse(service.getListMeetings().contains(meetingToRemove));
	}

	/**
	 * When the meeting exists, find by object
	 */
	@Test
	public void meetingDoesExist()
	{
		// Create list Meeting
		List<Employee> listEmployees = Arrays.asList(new Employee("Baptiste", "baptiste@lamzone.com", 4), new Employee("Fanny", "fanny@lamzone.com", 10), new Employee("Vincent", "vincent@lamzone.com", 22));

		Meeting newMeeting = new Meeting("Réunion d'avancement", "Planck", "12/11/20", "15:30", "16:00", "Revues des dernières actions", listEmployees);

		// Add Meeting
		service.addMeeting(newMeeting);

		try {
			Meeting found = service.findByObject("Réunion d'avancement");
			Assert.assertEquals(found, newMeeting);
		}
		catch (MeetingNotFound e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * When the meeting does not exist, find by object
	 */
	@Test
	public void meetingDoesNotExist()
	{
		// Message
		String m = "";

		// Create list Meeting
		List<Employee> listEmployees = Arrays.asList(new Employee("Baptiste", "baptiste@lamzone.com", 4), new Employee("Fanny", "fanny@lamzone.com", 10), new Employee("Vincent", "vincent@lamzone.com", 22));

		Meeting newMeeting = new Meeting("Réunion d'avancement", "Planck", "12/11/20", "15:30", "16:00", "Revues des dernières actions", listEmployees);

		// Add Meeting
		service.addMeeting(newMeeting);

		try
		{
			Meeting found = service.findByObject("Réunion d'avancement");
		}
		catch (MeetingNotFound e)
		{
			m = e.getMessage();
		}

		Assert.assertEquals(m, new MeetingNotFound().getMessage());
	}
}
