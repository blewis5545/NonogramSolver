package Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InputController {

    @RequestMapping("/")
    public String index(){
        return "Greetings from spring boot";
    }
}
