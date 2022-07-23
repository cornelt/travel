package com.alex.travel.derby.data.generator;

import com.alex.travel.derby.data.entity.Role;
import com.alex.travel.derby.data.entity.Travel;
import com.alex.travel.derby.data.entity.User;
import com.alex.travel.derby.data.service.TravelRepository;
import com.alex.travel.derby.data.service.UserRepository;
import com.alex.travel.derby.views.treavel.TreavelViewCard;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository, TravelRepository travelRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());


            logger.info("Generating demo data");


            if(travelRepository.count()<1) {
                travelRepository.save(new Travel("images/travel/amsterdam.png",
                        "Text", "Amsterdam City breack", "200.4$", "Amsterdam New city ", "amsterdam europe"));

                travelRepository.save(new Travel("images/travel/bucharest.png",
                        "Text", "Bucharest city breack", "1000.4$", "bucharest New city ", "bucharest romania europe"));

                travelRepository.save(new Travel("images/travel/transfagarasean.png",
                        "Text", "Road trip in romania", "1000.4$", "Transfagarasean road trip", "transafagarasean romania europe"));

            }

            if(userRepository.getByUserName("alex")==null) {
                userRepository.save(new User("alex", "alex", Role.USER));
                userRepository.save(new User("alex_admin", "alex_admin", Role.ADMIN));
                userRepository.save(new User("alex_staff", "alex_staff", Role.STAFF));
            }

            logger.info("Generated demo data");
        };
    }

}