
import com.novare.bankApp.view.BankView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BankViewTest {
    @Mock
    private BankView bankView;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGetInput() {
        String prompt = "Enter your name";
        String input = "Alireza";

        when(bankView.getInput(prompt)).thenReturn(input);

        String result = bankView.getInput(prompt);

        assertEquals(input, result.trim());
    }

    @Test
    public void testGetSocialId_ValidFormat() {
        String prompt = "Enter your socialId number (like this yyyy-abc) or 0 to exit";
        String input = "1985-Ali";

        when(bankView.getSocialId(prompt)).thenReturn(input);

        String result = bankView.getSocialId(prompt);

        assertEquals(input, result.trim());
    }

    @Test
    public void testGetSocialId_Exit() {
        BankView bankView = Mockito.mock(BankView.class);
        String prompt = "Enter your socialId number (like this yyyy-abc) or 0 to exit";
        String invalidInput = "0";

        when(bankView.getSocialId(prompt)).thenReturn(invalidInput);

        String result = bankView.getSocialId(prompt);

        assertEquals("0", result.trim());
    }
}