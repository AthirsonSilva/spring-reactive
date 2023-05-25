package com.javatechie.reactive.controller;

import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.RedirectView;

@Controller
public class RootController {

	@GetMapping("/")
	@Description("Redirects to Swagger UI at /swagger-ui.html")
	public RedirectView redirectToAnotherPage() {
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("/swagger-ui.html");

		return redirectView;
	}
}
