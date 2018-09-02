package pl.todrzywolek

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jms.core.JmsTemplate
import spock.lang.Specification

@SpringBootTest
class StreamHandlerSpec extends Specification {

    @Autowired
    JmsTemplate jmsTemplate

    @Autowired
    PersonRepository personRepository

    final NAME = 'Fred'
    final AGE = 23

    def setup() {
        personRepository.save(new Person(NAME, AGE))
        println('Person saved')
    }

    def "UpdatePerson"() {
        when: "message is send"
        jmsTemplate.convertAndSend("update_person", NAME)
        then: "person age is updated"
        def result = personRepository.findByName(NAME)
        result.getAge() == AGE + 1
    }
}
