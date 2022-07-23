package com.alex.travel.derby.data.service;

import com.alex.travel.derby.data.entity.Role;
import com.alex.travel.derby.data.entity.User;
import com.alex.travel.derby.views.about.AboutView;
import com.alex.travel.derby.views.treavel.TreavelView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class AuthService {
    private final UserRepository userRepository;
    public record AuthorizedRote(String route, String name,Class<? extends Component> view){

    }
    AuthService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    public void register(String userName, String password) throws Exception {
        if(this.userRepository.getByUserName(userName)!=null){
            throw new Exception ("User exist");
        }

        User u=new User(userName,password,Role.USER);
        this.userRepository.save(u);
    }
    public void authentificate(String userName, String password) throws Exception {
        User u=userRepository.getByUserName(userName);
        if(u !=null && u.checkPassword(password)){
            createRoutes(u.getRole());
            VaadinSession.getCurrent().setAttribute(User.class,u);
        }else{
            throw new Exception("Incorect auth");
        }
    }
    private void createRoutes(Role role){
        getAuthorizedRote(role);//.stream().forEach(x-> RouteConfiguration.forSessionScope().setRoute(x.route,x.view, MainLayout.class));
    }
    public List<AuthorizedRote> getAuthorizedRote(Role role){
        ArrayList<AuthorizedRote> authorizedRote = new ArrayList<>();
         if(role == Role.GUEST){
             authorizedRote.add(new AuthorizedRote("about","About", AboutView.class));
             authorizedRote.add(new AuthorizedRote("travel","Travel", TreavelView.class));

         }
        if(role == Role.USER){
            authorizedRote.add(new AuthorizedRote("about","About", AboutView.class));
            authorizedRote.add(new AuthorizedRote("travel","Travel", TreavelView.class));



        }
        if(role == Role.STAFF){
            authorizedRote.add(new AuthorizedRote("about","About", AboutView.class));
            authorizedRote.add(new AuthorizedRote("travel","Travel", TreavelView.class));


        }
        if(role == Role.ADMIN){
            authorizedRote.add(new AuthorizedRote("about","About", AboutView.class));
            authorizedRote.add(new AuthorizedRote("travel","Travel", TreavelView.class));


        }
        return authorizedRote;
    }
}
