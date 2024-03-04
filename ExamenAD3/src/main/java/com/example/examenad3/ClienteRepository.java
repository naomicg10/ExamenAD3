package com.example.examenad3;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    @Query("select c from Cliente c where c.total>:cantidad")
    public List<Cliente> getCantidadMayor(@Param("cantidad")Long cantidad);
    @Query("select c from Cliente c where c.estado='inactivo' and c.total>0")
    public List<Cliente> getTotalVentasInactivo();
    public List<Cliente> getClienteByEstado(String estado);
}
