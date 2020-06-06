package com.covid19.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.covid19.model.LocationStats;
import com.covid19.service.CoronaVirusDataService;
import com.covid19.service.CovidService;

@Controller
public class CovidController {
	
	@Autowired
	CoronaVirusDataService coronaVirusDataService;
	
	@Autowired
    CovidService service;
	
	@RequestMapping("/")
	public ModelAndView firstPage() {
		return new ModelAndView("covid");
	}
	
	@RequestMapping(value="/login", method= RequestMethod.GET)
	public ModelAndView loginPage(ModelMap model) {
		return new ModelAndView("login");
	}
		
	@RequestMapping(value="/login", method = RequestMethod.POST)
    public String showWelcomePage(ModelMap model, @RequestParam String name, @RequestParam String password){

        boolean isValidUser = service.validateUser(name, password);

        if (!isValidUser) {
            model.put("errorMessage", "Invalid Credentials");
            return "login";
        }
        
        model.put("name", name);
        model.put("password", password);

        return "welcome";
    }
		
	    @GetMapping("/")
	    public String home(Model model) {
	        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
	        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
	        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
	        model.addAttribute("locationStats", allStats);
	        model.addAttribute("totalReportedCases", totalReportedCases);
	        model.addAttribute("totalNewCases", totalNewCases);

	        return "home";
	    }
}
