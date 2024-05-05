package se.ifmo.lab4.repo;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import se.ifmo.lab4.model.Element;

@Repository
public interface ElementRepository extends CrudRepository<Element, Long>{
    List<Element> findAllByCreator(String creator);
}