package br.com.paloma.forumaluraapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport //pegar dos paramtros da url os valores pra paginação
@EnableCaching //habilitar o uso de cache
public class ForumAluraApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumAluraApiApplication.class, args);
	}

}
