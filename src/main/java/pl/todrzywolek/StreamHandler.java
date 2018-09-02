package pl.todrzywolek;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class StreamHandler {
    private PersonRepository personRepository;

    public StreamHandler(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @JmsListener(destination = "update_person")
    public void updatePerson(@Payload String name) {
        Person entity = personRepository.findByName(name);
        entity.setAge(entity.getAge() + 1);
        personRepository.save(entity);

    }
}
