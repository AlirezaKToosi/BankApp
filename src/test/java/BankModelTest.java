import com.novare.bankApp.data.User;
import com.novare.bankApp.model.BankModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BankModelTest {
    @Mock
    private User mockedUser;

    private BankModel bankModel;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bankModel = new BankModel();
    }

    @Test
    public void testAddUser_Success() {

        String username = "Alireza";
        String socialId = "1985-Ali";
        String password = "123456";

        when(mockedUser.getUsername()).thenReturn(username);
        when(mockedUser.getSocialId()).thenReturn(socialId);
        when(mockedUser.getPassword()).thenReturn(password);

        assertTrue((Year.now().getValue() - Integer.parseInt(socialId.substring(0, 4))) >= BankModel.VALIDAGE);
        assertNull(bankModel.getUser(socialId));
    }

    @Test
    public void testAddUser_InvalidAge() {

        String username = "Alireza";
        String socialId = "2015-Ali";
        String password = "123456";

        when(mockedUser.getUsername()).thenReturn(username);
        when(mockedUser.getSocialId()).thenReturn(socialId);
        when(mockedUser.getPassword()).thenReturn(password);

        assertFalse((Year.now().getValue() - Integer.parseInt(socialId.substring(0, 4))) >= BankModel.VALIDAGE);
        assertNull(bankModel.getUser(socialId));
    }
}