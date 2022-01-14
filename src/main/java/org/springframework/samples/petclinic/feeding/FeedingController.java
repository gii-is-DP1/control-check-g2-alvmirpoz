package org.springframework.samples.petclinic.feeding;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FeedingController {

    @Autowired
    private FeedingService feedingService;
    @Autowired
    private PetService petService;

    @GetMapping(path = "feeding/create")
    public String initCreationForm(ModelMap model){
        model.put("feeding", new Feeding());
        model.put("feedingTypes", this.feedingService.getAllFeedingTypes());
        model.put("pets", this.petService.getAllPets());
        return "feedings/createOrUpdateFeedingForm";
    }

    @PostMapping(path = "feeding/create")
    public String processCreationForm(@Valid Feeding feeding, BindingResult result, ModelMap model){
        if(result.hasErrors()){
            model.put("feeding", feeding);
            model.put("feedingTypes", this.feedingService.getAllFeedingTypes());
            model.put("pets", this.petService.getAllPets());
            return "feedings/createOrUpdateFeedingForm";
        }
        else{
            try{
                this.feedingService.save(feeding);
            }
            catch (UnfeasibleFeedingException ex){
                result.reject("La mascota seleccionada no se le puede asignar el plan de alimentación especificado");
                return "feedings/createOrUpdateFeedingForm";
            }
            
        }
        return "redirect:/welcome";
    }
}