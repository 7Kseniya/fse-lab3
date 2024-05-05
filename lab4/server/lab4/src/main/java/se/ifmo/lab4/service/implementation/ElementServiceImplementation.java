package se.ifmo.lab4.service.implementation;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import se.ifmo.lab4.model.Element;
import se.ifmo.lab4.repo.ElementRepository;
import se.ifmo.lab4.service.ElementService;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ElementServiceImplementation implements ElementService{
    private final ElementRepository elementRepository;

    @Override
    public Element create(Element element) {
        log.info("creating new element");
        return elementRepository.save(element);
    }

    @Override
    public Collection<Element> list(String username) {
        log.info("clearing table");
        List<Element> elems= elementRepository.findAllByCreator(username);

        log.info("Fetching all elements");

        return Lists.newArrayList(elems);
    }

    @Override
    public Long delete(Long id) {
        log.info("deleting element {}", id);
        elementRepository.deleteById(id);
        return id;
    }

    @Override
    public void clearTable(String username) {
        log.info("clearing table");
        List<Element> elems= elementRepository.findAllByCreator(username);
        for(Element el : elems){
            elementRepository.delete(el);
        }
    }
    
}
