package se.ifmo.lab4.service;

import java.util.Collection;

import se.ifmo.lab4.model.Element;

public interface ElementService {
    Element create(Element element);
    Collection<Element> list(String username);
    Long delete(Long id);
    void clearTable(String username);
}