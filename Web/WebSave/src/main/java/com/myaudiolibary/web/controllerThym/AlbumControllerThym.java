package com.myaudiolibary.web.controllerThym;

import com.myaudiolibary.web.exception.ConflictException;
import com.myaudiolibary.web.model.Album;
import com.myaudiolibary.web.model.Artist;
import com.myaudiolibary.web.repository.AlbumRepository;
import com.myaudiolibary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
@RequestMapping(value = "thym/albums")
public class AlbumControllerThym {

    @Autowired
    private AlbumRepository albumRepository;


    @RequestMapping(method = RequestMethod.DELETE, value ="/{id}")
    public RedirectView deleteAlbum(@PathVariable Long id,Artist artist) {

        albumRepository.deleteById(id);

        return new RedirectView("/thym/artists?page=0&size=10&sortDirection=ASC&sortProperty=name");
    }


    @RequestMapping(method = RequestMethod.POST, value = "", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView createOrSaveAlbum(Album album, Artist artist) throws ConflictException {
        if (albumRepository.existsByTitle(album.getTitle()) == true) {
            throw new ConflictException("Le nom de l'album " + album.getTitle() + "  est deja utilisé");
        } else if (album.getTitle() == "") {
            throw new IllegalArgumentException("Le champs ajouté est vide");
        } else {

            return saveAlbum(album, artist);
        }
    }


    private RedirectView saveAlbum(Album album, Artist artist) {
        albumRepository.save(album);

        return new RedirectView("/thym/artists/" + artist.getId());

    }




}
