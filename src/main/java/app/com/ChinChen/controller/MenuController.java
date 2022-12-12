package app.com.ChinChen.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.com.ChinChen.repository.MenuRepository1;
import app.com.ChinChen.repository.MenuRepository2;
import app.com.ChinChen.security.jwt.JwtUtils;

@RestController
@RequestMapping(path = {"/"})
public class MenuController {

	@Autowired
	JwtUtils jwtUtils;

	@Autowired 
	MenuRepository1 menuRepo1;
	
	@Autowired 
	MenuRepository2 menuRepo2;

    @GetMapping("menu1")
    public ResponseEntity<?> getMenu1(HttpServletRequest request){

 		String headerAuth = request.getHeader("Authorization");

		if (headerAuth.startsWith("Bearer ")) {
			headerAuth = headerAuth.substring(7, headerAuth.length());
		}

		String emp_id = jwtUtils.getAccountFromJwtToken(headerAuth);
		emp_id = emp_id.split("@@@")[0];

		return ResponseEntity.ok().body(menuRepo1.getMenu1(emp_id));
    }

	@GetMapping("menu2")
    public ResponseEntity<?> getMenu2(HttpServletRequest request){

		String headerAuth = request.getHeader("Authorization");

		if (headerAuth.startsWith("Bearer ")) {
			headerAuth = headerAuth.substring(7, headerAuth.length());
		}

		String emp_id = jwtUtils.getAccountFromJwtToken(headerAuth);
		emp_id = emp_id.split("@@@")[0];

		return ResponseEntity.ok().body(menuRepo2.getMenu2(emp_id));
    }
}