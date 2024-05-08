package com.zvaryyka.motelwebapplication;

import com.zvaryyka.motelwebapplication.controllers.SuperUserController;
import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.services.EmailSenderService;
import com.zvaryyka.motelwebapplication.services.PersonDetailsService;
import com.zvaryyka.motelwebapplication.services.RegistrationService;
import com.zvaryyka.motelwebapplication.util.validation.PersonValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class SuperUserControllerTest {

    @Mock
    private PersonDetailsService personDetailsService;

    @InjectMocks
    private SuperUserController superUserController;

    @Mock
    private RegistrationService registrationService;

    @Mock
    private EmailSenderService emailSenderService;



    @Test
    void getSuperUserPanelTest() {
        // Arrange
        Principal principal = mock(Principal.class);
        Model model = mock(Model.class);
        Person person = new Person();
        person.setLogin("testUser");

        // Stubbing the behavior of personDetailsService
        when(principal.getName()).thenReturn("testUser");
        when(personDetailsService.findByLogin("testUser")).thenReturn(Optional.of(person));

        // Act
        String viewName = superUserController.getSuperUserPanel(principal, model);

        // Assert
        assertEquals("super-user-panel", viewName);
        verify(model).addAttribute("person", person);
    }

}
