package com.myaudiolibary.web.controller;

import com.myaudiolibary.web.exception.ConflictException;
import com.myaudiolibary.web.model.Album;
import com.myaudiolibary.web.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/albums")
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value ="", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Album NewAlbum(@RequestBody Album Album) throws ConflictException {
        if (albumRepository.existsByTitle(Album.getTitle()) == true) {
            throw new ConflictException("Le nom de l'artiste " + Album.getTitle() + "  est deja utilisé");
        } else {
            return albumRepository.save(Album);
        }
    }


    @RequestMapping(method = RequestMethod.DELETE, value ="/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    public void SuppAlbum(@PathVariable Long id){

        albumRepository.deleteById(id);
    }


}
