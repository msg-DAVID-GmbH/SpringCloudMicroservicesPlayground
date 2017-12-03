package mafoe.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple web-mvc controller.<br/>
 * @see <a href="http://localhost:8080/">Link on localhost</a>
 */
@RestController
public class HelloController {
    
    @RequestMapping("/")
    public String index() {
        return "'Democracy Dies in Darkness', says the Washington Post";
    }
}
