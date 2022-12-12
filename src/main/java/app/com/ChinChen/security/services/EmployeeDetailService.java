package app.com.ChinChen.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import app.com.ChinChen.domain.Employee;
import app.com.ChinChen.repository.AuthRepositry;

public class EmployeeDetailService implements UserDetailsService{
   @Autowired
   private AuthRepositry employeeRepo;

   public EmployeeDetailsImpl loadEmployeeByAccount(String account) throws UsernameNotFoundException {

        Employee employee =  employeeRepo.findByAccount(account);
        if(employee != null) {
            return EmployeeDetailsImpl.build(employee);
        }

        return null;
   }

    @Override
    public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
        return null;
    }
}
