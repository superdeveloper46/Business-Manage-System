package app.com.ChinChen.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.com.ChinChen.domain.Employee;
import app.com.ChinChen.domain_temp.Menu1;
import app.com.ChinChen.domain_temp.Menu2;
import app.com.ChinChen.library.AES;
import app.com.ChinChen.payload.response.JwtResponse;
import app.com.ChinChen.payload.response.MessageResponse;
import app.com.ChinChen.repository.AuthRepositry;
import app.com.ChinChen.repository.MenuRepository1;
import app.com.ChinChen.repository.MenuRepository2;
import app.com.ChinChen.security.jwt.JwtUtils;
import app.com.ChinChen.security.services.EmployeeDetailService;
import app.com.ChinChen.security.services.EmployeeDetailsImpl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@RestController
@RequestMapping(path = { "/api/auth" })
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private EmployeeDetailService employeeDetailsService;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	private AuthRepositry employeeRepo;

	@Autowired
	MenuRepository1 menuRepo1;

	@Autowired
	MenuRepository2 menuRepo2;

	@Autowired
	private JavaMailSender javaMailSender;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> login_data) {

		EmployeeDetailsImpl employeeDetails = employeeDetailsService.loadEmployeeByAccount(login_data.get("account"));

		if (employeeDetails == null) {
			return ResponseEntity.ok(new MessageResponse(1, "User doesn't exist"));
		}

		AES crypt = new AES();
		if (!crypt.encode(login_data.get("password")).toString().equals(employeeDetails.getPassword())) {
			return ResponseEntity.ok(new MessageResponse(0, "password error"));
		}

		String jwt = jwtUtils.generateJwtToken(employeeDetails);

		List<String> roles = employeeDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		String emp_id = employeeDetails.getId();
		List<Menu1> menu1 = this.get_menu1(emp_id);
		List<Menu2> menu2 = this.get_menu2(emp_id);

		return ResponseEntity.ok(new JwtResponse(jwt,
				employeeDetails.getId(),
				employeeDetails.getUsername(),
				employeeDetails.getEmail(),
				roles,
				menu1,
				menu2));
	}

	@GetMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "/";
	}

	@PostMapping("/forgotpassword")
	public ResponseEntity<?> forgotpassword(@RequestBody Map<String, String> forgot_data) {

		Employee employee = employeeRepo.findByAccountAndPersonalNo(forgot_data.get("account"),
				forgot_data.get("personalNo"));

		if (employee == null) {
			return ResponseEntity.ok(new MessageResponse(1, "Email or PersonalNo is Incorrect"));
		}

		AES crypt = new AES();
		String password = crypt.decode(employee.getPassword());
		String email = employee.getEmail();

		sendEmail(email, password);
		return ResponseEntity.ok(new MessageResponse(0, "Email has sent successfully. \n Please check your email."));
	}

	public void sendEmail(String email, String password) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);

		// msg.setFrom("欽成營造系統");
		msg.setSubject("忘記密碼通知信");
		msg.setText("欽成營造系統通知，您的系統密碼為：" + password + ",\n 謝謝您");

		javaMailSender.send(msg);
	}

	public List<Menu1> get_menu1(String emp_id) {
		return this.menuRepo1.getMenu1(emp_id);
	}

	public List<Menu2> get_menu2(String emp_id) {
		return this.menuRepo2.getMenu2(emp_id);
	}
}