package ma.fstg.security.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/accueil")
  public String accueil() {
    return "accueil";
  }

  @GetMapping("/manager/tableau-de-bord")
  public String managerDashboard() {
    return "manager-dashboard";
  }

  @GetMapping("/employe/espace")
  public String employeDashboard() {
    return "employe-espace";
  }
}