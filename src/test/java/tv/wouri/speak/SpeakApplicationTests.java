package tv.wouri.speak;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import tv.wouri.speak.models.Langue;
import tv.wouri.speak.models.Role;
import tv.wouri.speak.models.User;
import tv.wouri.speak.service.LangueService;
import tv.wouri.speak.service.RoleService;
import tv.wouri.speak.service.UserService;


//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//@EnableJpaRepositories(basePackages = {"com.adc.sygem.repositories"})
@SpringBootTest(classes = SpeakApplicationTests.class)
class SpeakApplicationTests {

	@Test
	void contextLoads() {
	}

/*	@Test
	public void testCreateRole() throws Exception {
		Role role = new Role();
		role.setLibelle("ADMINISTRATEUR");
		roleService.save(role);
	}

	@Test
	public void testCreateUser() throws Exception {
		Role role = roleService.get(2L);
		User user = new User();
		user.setLogin("tsumbang@outlook.com");
		user.setNom("SUMBANG");
		user.setPrenom("Christian");
		user.setStatus(true);
		user.setCountry("CM");
		user.setActivated(true);
		user.setPassword(passwordEncoder.encode("123456"));
		user.setRole(role);
		userService.save(user);
	}

	@Test
	public void testCreateLangue() throws Exception {
		Langue langue = new Langue();
		langue.setLibelle("Ghomala");
		langueService.save(langue);
	}*/
}
