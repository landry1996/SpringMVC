package ma.enset.spring_mvc;

import lombok.AllArgsConstructor;
import ma.enset.spring_mvc.entities.Patient;
import ma.enset.spring_mvc.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
@AllArgsConstructor
public class SpringMvcApplication {
   private PatientRepository patientRepository;
    public static void main(String[] args) {
        SpringApplication.run(SpringMvcApplication.class, args);
    }
    @Bean
    CommandLineRunner start(){
        return args -> {
            Stream.of("Hassan","Willy","Patson","David","Vida")
                    .forEach(pt->{
                        var p = new Patient();
                        p.setName(pt);
                        p.setBirthday(new Date());
                        if (Math.random()>0.5){
                            p.setSick(false);
                        }else {
                            p.setSick(true);
                        }
                        if (Math.random()>0.5){
                            p.setScore(100);
                        }else {
                            p.setScore(150);
                        }

                        patientRepository.save(p);

                    });
            Stream.of("Landry","Placide","Joel")
                    .forEach(pa->{
                        var p =Patient.builder()
                                .name(pa)
                                .birthday(new Date())
                                .sick(false)
                                .score(155)
                                .build();
                        patientRepository.save(p);

                    });
            patientRepository.save(new Patient(null,"David",new Date(),false,170));
            patientRepository.save(new Patient(null,"Jonathan",new Date(),true,200));
            patientRepository.save(new Patient(null,"Gabriel",new Date(),false,140));
            patientRepository.save(new Patient(null,"Samir",new Date(),true,110));
        };
    }

}
