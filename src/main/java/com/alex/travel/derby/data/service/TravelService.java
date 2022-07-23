package com.alex.travel.derby.data.service;

import com.alex.travel.derby.data.entity.Travel;
import com.alex.travel.derby.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TravelService {
    TravelRepository travelRepository;
    public TravelService(TravelRepository travelRepository){
        this.travelRepository=travelRepository;
    }
    public Page<Travel> list(Pageable pageable) {
        return travelRepository.findAll(pageable);
    }
    public List<Travel> getAll() {
        return travelRepository.findAll();
    }
    public Optional<Travel> get(Long id) {
        return travelRepository.findById(id);
    }

    public Travel update(Travel entity) {
        return travelRepository.save(entity);
    }
    public List<Travel> findInTag(String contains){
        return travelRepository.findByTagsContaining(contains);
    }
    public void delete(Long id) {
        travelRepository.deleteById(id);
    }
    public int count() {
        return (int) travelRepository.count();
    }
}
