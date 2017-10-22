package org.kelex.loans;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kelex.loans.core.entity.CustomerEntity;
import org.kelex.loans.core.service.CustomerService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Bootstrap.class)
public class LendingSystemWebApplicationTests {

	@Inject
	private CustomerService customerService;

	@Test
	public void contextLoads() {
//		List<TestEntity> test = customerService.test();
//		try {
//			for(int i=0, l=100; i<l; i++){
//				System.out.println(SystemUtils.id("dwqdwqdqdqdq"));
//			}


		this.hashCode();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
	}
}
