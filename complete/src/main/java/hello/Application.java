package hello;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;


@SpringBootApplication(exclude =
{ ErrorMvcAutoConfiguration.class })
public class Application
{

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(final String[] args)
	{
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner demo(final CustomerRepository repository)
	{
		return (args) -> {
			// save a couple of customers
			//repository.save(new Customer("Jack", "Bauer"));
			//repository.save(new Customer("Chloe", "O'Brian"));
			//repository.save(new Customer("Kim", "Bauer"));
			//repository.save(new Customer("David", "Palmer"));
			//repository.save(new Customer("Michelle", "Dessler"));

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (final Customer customer : repository.findAll())
			{
				log.info(customer.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			final Customer customer = repository.findOne(1L);
			log.info("Customer found with findOne(1L):");
			log.info("--------------------------------");
			log.info(customer.toString());
			log.info("");

			// fetch customers by last name
			log.info("Customer found with findByLastName('Bauer'):");
			log.info("--------------------------------------------");
			for (final Customer bauer : repository.findByLastName("Bauer"))
			{
				log.info(bauer.toString());
			}
			log.info("");
		};
	}


	@Bean
	public ViewResolver contentNegotiatingViewResolver(final ContentNegotiationManager manager)
	{
		final ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setContentNegotiationManager(manager);

		// Define all possible view resolvers
		final List<ViewResolver> resolvers = new ArrayList<ViewResolver>();

		resolvers.add(jaxb2MarshallingXmlViewResolver());
		resolvers.add(jsonViewResolver());

		resolver.setViewResolvers(resolvers);
		return resolver;
	}

	/*
	 * Configure View resolver to provide XML output Uses JAXB2 marshaller to marshall/unmarshall POJO's (with JAXB
	 * annotations) to XML
	 */
	@Bean
	public ViewResolver jaxb2MarshallingXmlViewResolver()
	{
		final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(Customer.class);
		return new Jaxb2MarshallingXmlViewResolver(marshaller);
	}

	/*
	 * Configure View resolver to provide JSON output using JACKSON library to convert object in JSON format.
	 */
	@Bean
	public ViewResolver jsonViewResolver()
	{
		return new JsonViewResolver();
	}
}
