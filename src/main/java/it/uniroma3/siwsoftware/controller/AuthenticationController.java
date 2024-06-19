package it.uniroma3.siwsoftware.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siwsoftware.model.Utente;
import it.uniroma3.siwsoftware.service.UtenteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller 
public class AuthenticationController {
	@Autowired UtenteService utenteService;
	@Autowired PasswordEncoder passwordEncoder;


	@GetMapping("/login")
	public String formLoginUtente(Model model) {
		return "authentication/login.html";
	}

	@PostMapping("/login")
	public String loginUtente(@RequestParam("utente") Utente utente) {
		return "index.html";
	}

	@GetMapping("/register")
	public String formRegisterUtente(Model model) {
		model.addAttribute("utente", new Utente());
		return "authentication/register.html";
	}

	@PostMapping("/register")
	public String registerUtente(@Valid @ModelAttribute("utente") Utente utente) throws IOException {
		utente.setPassword(passwordEncoder.encode(utente.getPassword()));
		utenteService.save(utente);

		return "authentication/login.html";
	}

	@GetMapping("/success")
	public String success(Model model) {
			return "index.html";
	}
	
	@GetMapping("/login-error")
    public String loginError(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                errorMessage = ex.getMessage();
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        return "authentication/login.html";
    }

}
