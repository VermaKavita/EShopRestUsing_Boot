package com.ecom.shopping_cart.repository;

import com.ecom.shopping_cart.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CustomerRepository extends JpaRepository<User, Integer>{

	Optional<User> findByEmail(String email);
	User findById(int cid);

	Boolean existsByEmail(String email);
	 Boolean existsByUsername(String username);
	@Query(value="select * FROM user inner join  user_roles on user.c_id= user_roles.user_id and roles=:role order by username", nativeQuery = true)
	public List<User> findByRole(String role);
	@Query(value = "delete from user_roles where role=:role and user_id=:cid",nativeQuery = true)
	public Optional<User> deleteByRoles(String role,int cid);
}
