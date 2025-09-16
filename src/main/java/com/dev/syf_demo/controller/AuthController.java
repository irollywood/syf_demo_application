package com.dev.syf_demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/crsf-token")
    public CsrfToken getToken(HttpServletRequest request)
    {
        var csrfToken = (CsrfToken) request.getAttribute("_csrf");
        return csrfToken;
    }

}
