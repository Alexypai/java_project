package com.myaudiolibary.web.controller;

import com.myaudiolibary.web.exception.ConflictException;
import com.myaudiolibary.web.model.Artist;
import com.myaudiolibary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/artists")
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepository;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/count")
    public Long CountArtist() {

        return artistRepository.count();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Artist> id(@PathVariable(value = "id") Long id) throws EntityNotFoundException{
        if (artistRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("L'identifiant " + id + " est incorrecte");
        } else {
            return artistRepository.findById(id);
        }
    }

    @RequestMapping(params = {"name"},method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value ="")
    public Page<Artist> SearchArtist(
            @RequestParam("name") String name,
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam String sortProperty,
            @RequestParam String sortDirection) {

        return artistRepository.findAllByNameContainingIgnoreCase(name,PageRequest.of(page,size, Sort.Direction.valueOf(sortDirection),sortProperty));

    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value ="")
    public Page<Artist> listArtist(
           @RequestParam Integer page,
           @RequestParam Integer size,
           @RequestParam String sortProperty,
           @RequestParam String sortDirection) {
        if (page < 0) {
            throw new IllegalArgumentException("Le parametre page doit etre positif ou nul");
        }
        if (size <= 0 || size > 50) {
            throw new IllegalArgumentException("Le parametre page doit etre compris entre 0 et 50");
        } else {
            return artistRepository.findAll(PageRequest.of(page, size, Sort.Direction.valueOf(sortDirection), sortProperty));
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value ="", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Artist NewArtist(@RequestBody Artist Artist) throws ConflictException {
        if (artistRepository.existsByName(Artist.getName()) == true) {
            throw new ConflictException("Le nom de l'artiste " + Artist.getName() + "  est deja utilisé");
        } else if (Artist.getName() == null) {
            throw new IllegalArgumentException ("Le champs ajouté est vide");
        } else {
            return artistRepository.save(Artist);
        }
    }
    

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, value ="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Artist ChangeArtist(@RequestBody Artist ChangeArtist){

        return artistRepository.save(ChangeArtist);
    }

    @RequestMapping(method = RequestMethod.DELETE, value ="/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    public void DelArtist(@PathVariable Long id){

        artistRepository.deleteById(id);
    }

}

