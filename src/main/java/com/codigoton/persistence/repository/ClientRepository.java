package com.codigoton.persistence.repository;

import com.codigoton.persistence.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("select c from Client c where (c.location = ?1 or c.location is not null) and (c.type = ?2 or c.type is not null)")
    List<Client> findByLocationAndType(String location, int type);
}
