package tv.wouri.speak;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import tv.wouri.speak.models.Role;
import tv.wouri.speak.service.RoleService;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
//@EnableJpaRepositories(basePackages = {"com.adc.sygem.repositories"})
class SpeakApplicationTests {

	@Autowired
	RoleService roleService;

	@Test
	void contextLoads() {
	}

	@Test
	public void testCreateRole() throws Exception {
		Role role = new Role();
		role.setLibelle("ADMINISTRATEUR");
		roleService.save(role);
	}

}
