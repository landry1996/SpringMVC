package ma.enset.spring_mvc.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.spring_mvc.entities.Patient;
import ma.enset.spring_mvc.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;
@GetMapping("/index")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "0") int page,
                        @RequestParam(name = "size",defaultValue = "4") int size,
                        @RequestParam(name = "keyword",defaultValue = "") String kw)
    {
        // pagination
        Page<Patient> pagePatients = patientRepository.findByNameContains(kw,PageRequest.of(page,size));
        model.addAttribute("listPatients",pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);
        return "patients";
    }
   @GetMapping("/delete")
    public String delete(Long id, String keyword, int page){
          patientRepository.deleteById(id);
          return "redirect:/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/")
    public String home(){
    return "redirect:/index";
    }
    @GetMapping("/formPatient")
    public String formPatient(Model model){
    model.addAttribute("patient",new Patient());
     return "formPatient";
    }
    @PostMapping("/savePatient")
    public String savePatient(@Valid Patient patient, BindingResult bindingResult){
    if (bindingResult.hasErrors())
    {
        return "redirect:/index?keyword="+patient.getName();
    }
    patientRepository.save(patient);
    return "formPatient";
    }
    @GetMapping("/editPatient")
    public String editPatient(Model model,@RequestParam(name = "id") Long id){
    var patient = patientRepository.findById(id).get();
        model.addAttribute("patient",patient);
        return "editPatient";
    }
}
