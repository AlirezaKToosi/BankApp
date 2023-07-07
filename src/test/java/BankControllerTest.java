import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.novare.bankApp.controller.BankController;
import com.novare.bankApp.model.BankModel;
import com.novare.bankApp.view.BankView;
import com.novare.bankApp.data.User;

public class BankControllerTest {
    @Mock
    private BankModel model;
    @Mock
    private BankView view;
    private BankController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new BankController(model, view);
    }

    @Test
    public void testCreateUser() {
        //Mocking user input
        when(view.getInput(anyString())).thenReturn("Alireza");
        when(view.getSocialId(anyString())).thenReturn("1985-Ali");

        // Mocking model behavior
        when(model.addUser(anyString(), anyString(), anyString())).thenReturn(true);

        // Perform the createUser() method
        controller.createUser();

        // Verify that the displayMessage method was called with the expected message
        verify(view).displayMessage("User created successfully!");
    }

    @Test
    public void testInvalidLogin() {
        // Mocking user input
        when(view.getSocialId(anyString())).thenReturn("1985-Ali");
        when(view.getInput(anyString())).thenReturn("123456");

        // Mocking model behavior
        when(model.authenticateUser(anyString(), anyString())).thenReturn(false);

        // Perform the login() method
        controller.login();

        // Verify that the displayMessage method was called with the expected message
        verify(view).displayErrorMessage("Invalid username or password.");
    }

    @Test
    public void testBalance() {
        // Test user
        User user = new User("Alireza", "1985-Ali", "123456", "");
        user.deposit(500.0);

        // Set the currentUser in the controller
        controller.setCurrentUser(user);
        controller.balance();

        // Verifying
        verify(view).displayAccountDetails(user.getAccountDetails());
    }

    @Test
    public void testDeposit() {
        // Prepare a test user
        User user = new User("John Doe", "2000-abc", "password", "");

        // Set the currentUser in the controller
        controller.setCurrentUser(user);

        // Mocking user input
        when(view.getAmountInput(anyString())).thenReturn(100.0);


        // Perform the deposit() method
        controller.deposit();

        // Verifying
        verify(model).deposit(user, 100.0);
        verify(view).displayAccountDetails(user.getAccountDetails());
    }

    @Test
    public void testWithdraw() {
        // Test user
        User user = new User("Alireza", "1985-Ali", "123456", "");

        // Set the currentUser in the controller
        controller.setCurrentUser(user);

        // Mocking user input
        when(view.getAmountInput(anyString())).thenReturn(200.0);
        when(model.withdraw(any(User.class), anyDouble())).thenReturn(true);

        controller.withdraw();

        // Verifying
        verify(model).withdraw(user, 200.0);
        verify(view).displayAccountDetails(user.getAccountDetails());
    }

    @Test
    public void testTransfer() {
        // Prepare test users
        User sender = new User("Alireza", "1985-Ali", "123456", "");
        User recipient = new User("Ali", "1986-Ali", "654321", "");


        // Set the currentUser in the controller
        controller.setCurrentUser(sender);

        // Mocking user input
        when(view.getSocialId(anyString())).thenReturn("1986-Ali");
        when(view.getAmountInput(anyString())).thenReturn(100.0);

        // Mocking model behavior
        when(model.getUser(anyString())).thenReturn(recipient);
        when(model.transfer(any(User.class), any(User.class), anyDouble())).thenReturn(true);

        controller.transfer();
        // Verifying
        verify(model).transfer(sender, recipient, 100.0);
        verify(view).displayAccountDetails(sender.getAccountDetails());
    }
}