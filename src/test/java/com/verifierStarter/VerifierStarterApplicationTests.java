package com.verifierStarter;

import com.homeWorkVerifier.dto.SecurityProperties;
import myVerifier.Verifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import static org.mockito.Mockito.when;

@SpringBootTest
class VerifierStarterApplicationTests {

	@MockBean
	SecurityProperties securityProperties;

	@Autowired
	public ApplicationContext context;

	@Autowired
	public Verifier verifier;

	@Test
	void contextLoads() {
	}

	@DisplayName("Check Verifier in context")
	@Test
	void shouldGetVerifierInstant(){
		Verifier verifier = context.getBean(Verifier.class);
	}

	@Test
	void verifierTest(){
		String correct = verifier.checkUpOfLoginAndPassword("test@", "test");
		String inCorrectLogin = verifier.checkUpOfLoginAndPassword("123", "321");
		String inCorrectPassword = verifier.checkUpOfLoginAndPassword("test@mail.ru", "567");

		Assertions.assertEquals(correct, "Доступ разрешен");
		Assertions.assertEquals(inCorrectLogin, "Неверный логин");
		Assertions.assertEquals(inCorrectPassword, "Неверный пароль");
	}

	@Test
	void shouldThrowNoSuchBeanDefinitionException(){
		Assertions.assertThrows(NoSuchBeanDefinitionException.class,
				() -> context.getBean(NoBeanService.class));
	}


	@Test
	void testVerifierServiceMock() {
		when(securityProperties.getIsAvailable()).thenReturn(Boolean.TRUE);

		String result = verifier.checkUpOfLoginAndPassword("test@", "test");
		Assertions.assertEquals("Доступ разрешен", result);
	}
}
