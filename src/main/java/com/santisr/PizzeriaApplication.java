package com.santisr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// implements CommandLineRunner
@SpringBootApplication
public class PizzeriaApplication  {

	// @Autowired
	// private IIngrediente ingredienteService;

	// @Autowired
	// private IPizza pizzaService;

	public static void main(String[] args) {
		SpringApplication.run(PizzeriaApplication.class, args);
	}

	// @Override
	// public void run(String... args) throws Exception {

	// 	for (Ingrediente ingrediente : ingredienteService.findAll(Sort.by("nombre"))) {
	// 		System.out.println(ingrediente.getNombre());
	// 	}

	// 	for (Pizza pizza : pizzaService.findAll()) {
	// 		List<Ingrediente> listIngredientes;
	// 		System.out.print("** Pizza: "+ pizza.getNombre());
	// 		System.out.printf(" Total: %4.2f€\n", pizza.getPrecio());
	// 		System.out.println("Ingredientes: ");
	// 		listIngredientes = pizza.getIngredientes();
	// 		if (listIngredientes.size() > 0)
	// 			for (Ingrediente ingrediente : listIngredientes) {
	// 				System.out.println("\t" + ingrediente.getNombre() + " " + ingrediente.getPrecio() + "€");
	// 			}
	// 		else
	// 			System.out.println("\tNO tiene ingredientes!!");
	// 	}

	// }

}
