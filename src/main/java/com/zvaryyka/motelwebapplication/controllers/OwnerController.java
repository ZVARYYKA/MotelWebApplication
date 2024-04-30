package com.zvaryyka.motelwebapplication.controllers;

import com.zvaryyka.motelwebapplication.dto.PersonDTO;
import com.zvaryyka.motelwebapplication.dto.RoomDTO;
import com.zvaryyka.motelwebapplication.models.Person;
import com.zvaryyka.motelwebapplication.models.Rooms;
import com.zvaryyka.motelwebapplication.models.TypeOfRooms;
import com.zvaryyka.motelwebapplication.services.PersonDetailsService;
import com.zvaryyka.motelwebapplication.services.RegistrationService;
import com.zvaryyka.motelwebapplication.services.RoomTypeService;
import com.zvaryyka.motelwebapplication.util.validation.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class OwnerController {
    private final RegistrationService registrationService;
    private final PersonValidator personValidator;
    private final PersonDetailsService personDetailsService;

    private final RoomTypeService roomTypeService;


    @Autowired
    public OwnerController(RegistrationService registrationService, PersonValidator personValidator, PersonDetailsService personDetailsService, RoomTypeService roomTypeService) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
        this.personDetailsService = personDetailsService;
        this.roomTypeService = roomTypeService;
    }
    @GetMapping("/owner")
    public String getOwnerPanel(Principal principal, Model model) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("person", person);


        return "ownerPanel";
    }

    @GetMapping("/owner/workers")
    public String getOwnerWorkers(Principal principal, Model model) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("person", person);
        model.addAttribute("personDTO",new PersonDTO());
        model.addAttribute("workers",personDetailsService.showAllWorkers());

        return "owner-workers";
    }
    @PostMapping("/owner/workers/regNewWorker")
    public String createNewWorker(@ModelAttribute("personDTO") @Valid PersonDTO personDTO,
                                  BindingResult bindingResult) {
        personValidator.validate(registrationService.convertToPerson(personDTO), bindingResult);

        if (bindingResult.hasErrors()) {
            return "owner-workers";
        }
        registrationService.regWorker(personDTO);
        return "redirect:/owner/workers";

    }
    @PostMapping("/owner/workers/changeWorker/{id}")
    public String changeWorker(@ModelAttribute("personDTO") @Valid PersonDTO personDTO,
                               BindingResult bindingResult, @PathVariable("id") int id) {
        personValidator.validate(registrationService.convertToPerson(personDTO), bindingResult);

        if (bindingResult.hasErrors()) {
            return "owner-workers";
        }
        registrationService.changeWorker(personDTO,id);
        return "redirect:/owner/workers";

    }
    @PostMapping("/owner/workers/deleteWorker/{id}")
    public String deleteWorker(@PathVariable("id") int id) {
        personDetailsService.delete(id);
        return "redirect:/owner/workers";
    }

    @GetMapping("/owner/statistics")
    public String getStatisticsPage() {
        return "graphic";
    }

    @GetMapping("/owner/rooms")
    public String getOwnerRooms(Principal principal, Model model) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("person", person);
        model.addAttribute("rooms",roomTypeService.getAllRoomDTO());
        model.addAttribute("roomDTO",new RoomDTO());
        model.addAttribute("typeOfRooms", roomTypeService.allTypeOfRooms());


        return "owner-rooms";
    }
    @PostMapping("/owner/rooms/addNewRoom")
    public String addNewRoom(@ModelAttribute("room") RoomDTO roomDTO) {

        roomTypeService.addNewRoom(roomDTO);



        return "redirect:/owner/rooms";

    }
    @GetMapping("/owner/typeOfRooms")
    public String getOwnerTypeOfRooms(Principal principal, Model model) {
        Person person = personDetailsService.findByLogin(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("person", person);
        model.addAttribute("typeOfRoomList",roomTypeService.getAllTypeOfRooms());
        model.addAttribute("createdTypeOfRoom", new TypeOfRooms());


        return "owner-type-of-rooms";
    }
    @PostMapping("/owner/typeOfRooms/createNewTypeOfRooms")
    public String createNewTypeOfRooms(@ModelAttribute("createdTypeOfRoom") TypeOfRooms typeOfRooms) {

        roomTypeService.addNewTypeOfRoom(typeOfRooms);


        return "redirect:/owner/typeOfRooms";
    }

}
