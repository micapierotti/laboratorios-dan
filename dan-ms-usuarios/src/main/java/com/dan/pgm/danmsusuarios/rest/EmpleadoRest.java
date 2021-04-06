package com.dan.pgm.danmsusuarios.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dan.pgm.danmsusuarios.domain.Cliente;
import com.dan.pgm.danmsusuarios.domain.Empleado;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/empleado")
@Api(value = "EmpleadoRest", description = "Permite gestionar los empleados de la empresa")
public class EmpleadoRest {
	
	private static final List<Empleado> listaEmpleados = new ArrayList<>();
    private static Integer ID_GEN = 1;
	 
	@PostMapping
	    public ResponseEntity<Empleado> crear(@RequestBody Empleado nuevo){
	    	System.out.println(" crear empleado "+nuevo);
	        nuevo.setId(ID_GEN++);
	        listaEmpleados.add(nuevo);
	        return ResponseEntity.ok(nuevo);
	    }
	
	 @PutMapping(path = "/{id}")
	    @ApiOperation(value = "Actualiza un empleado")
	    @ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Actualizado correctamente"),
	        @ApiResponse(code = 401, message = "No autorizado"),
	        @ApiResponse(code = 403, message = "Prohibido"),
	        @ApiResponse(code = 404, message = "El ID no existe")
	    })
	    public ResponseEntity<Empleado> actualizar(@RequestBody Empleado nuevo,  @PathVariable Integer id){
	        OptionalInt indexOpt =   IntStream.range(0, listaEmpleados.size())
	        .filter(i -> listaEmpleados.get(i).getId().equals(id))
	        .findFirst();

	        if(indexOpt.isPresent()){
	            listaEmpleados.set(indexOpt.getAsInt(), nuevo);
	            return ResponseEntity.ok(nuevo);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @DeleteMapping(path = "/{id}")
	    public ResponseEntity<Empleado> borrar(@PathVariable Integer id){
	        OptionalInt indexOpt =   IntStream.range(0, listaEmpleados.size())
	        .filter(i -> listaEmpleados.get(i).getId().equals(id))
	        .findFirst();

	        if(indexOpt.isPresent()){
	            listaEmpleados.remove(indexOpt.getAsInt());
	            return ResponseEntity.ok().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    
	    @GetMapping(path = "/{id}")
	    @ApiOperation(value = "Busca un empleado por id")
	    public ResponseEntity<Empleado> empleadoPorId(@PathVariable Integer id){

	        Optional<Empleado> c =  listaEmpleados
	                .stream()
	                .filter(unEmp -> unEmp.getId().equals(id))
	                .findFirst();
	        return ResponseEntity.of(c);
	    }
	    
	    @GetMapping
	    @ApiOperation(value = "Busca un empleado por nombre")
	    public ResponseEntity<Empleado> empleadoPorNombre(@RequestParam(name="nombre") String nombre){

	        Optional<Empleado> c =  listaEmpleados
	                .stream()
	                .filter(unEmp -> unEmp.getUser().getUser().equals(nombre))
	                .findFirst();
	        return ResponseEntity.of(c);
	    }
}
