package com.example.examenad3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping("insert")
    public ResponseEntity<Cliente> save(@RequestBody Cliente cliente){
        Cliente nuevoCliente = cliente;
        if(nuevoCliente!=null){
            return new ResponseEntity<>(clienteRepository.save(cliente), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("getClienteById/{id}")
    public ResponseEntity<Cliente>getClienteById(@PathVariable Long id){
        if(clienteRepository.existsById(id)){
            return new ResponseEntity<>(clienteRepository.findById(id).get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("getClienteTotalVentasMayor/{cantidad}")
    public ResponseEntity<Object> getClienteByCantidadMayor(@PathVariable Long cantidad){
        List<Cliente> clientes= clienteRepository.getCantidadMayor(cantidad);
        if(!clientes.isEmpty()){
            return new ResponseEntity<Object>(clientes,HttpStatus.OK);
        }else{
            return new ResponseEntity<Object>("No hay Clientes con ventas superiores a "+cantidad,HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("totalVentas")
    public ResponseEntity<Long>totalVentas(){
        List<Cliente> clientes= clienteRepository.findAll();
        Long sumarVentas = 0L;
        if(!clientes.isEmpty()){
            for(Cliente cliente:clientes){
                sumarVentas+=cliente.getTotal();
            }
            return new ResponseEntity<>(sumarVentas,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(0L,HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("promedio")
    public ResponseEntity<Double>promedio(){
        List<Cliente> clienteActivos = clienteRepository.getClienteByEstado("activo");
        Long sumarVentas=0L;
        if(!clienteActivos.isEmpty()){
            for(Cliente cliente:clienteActivos){
                sumarVentas+=cliente.getTotal();
            }
            Double promedio=(double)sumarVentas/clienteActivos.size();
            return new ResponseEntity<>(promedio,HttpStatus.OK);
        }else{
            return new ResponseEntity<>((double)clienteActivos.size(),HttpStatus.NOT_FOUND);
        }


    }
    @GetMapping("getClienteInactivoByTotalVentas")
    public ResponseEntity<Long> getCantidad(){
        List<Cliente> clientesInactivos= clienteRepository.getTotalVentasInactivo();

        if(!clientesInactivos.isEmpty()){
            return new ResponseEntity<>((long)clientesInactivos.size(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
