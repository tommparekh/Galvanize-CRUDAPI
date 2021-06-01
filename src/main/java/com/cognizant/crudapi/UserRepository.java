package com.cognizant.crudapi;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    public User findById(int id);
    public Iterable<User> deleteById(int id);

    public User findByEmail(String email);
}
