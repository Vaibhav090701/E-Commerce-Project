package com.project.ecommerce.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.ecommerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {
	
	 @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :search, '%'))")
	    List<Product> findByNameOrBrandContaining(@Param("search") String search);

}
