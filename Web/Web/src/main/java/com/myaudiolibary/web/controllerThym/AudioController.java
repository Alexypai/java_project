package com.myaudiolibary.web.controllerThym;

import com.myaudiolibary.web.exception.ConflictException;
import com.myaudiolibary.web.model.Album;
import com.myaudiolibary.web.model.Artist;
import com.myaudiolibary.web.repository.AlbumRepository;
import com.myaudiolibary.web.repository.ArtistRepository;
import com.myaudiolibary.web.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Controller
@RequestMapping(value = "thym/artists")
public class AudioController {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ArtistService artistService;


    @GetMapping(value = "/{id}")
    public String id(final ModelMap model, @PathVariable Long id) {
        if (!artistRepository.existsById(id)) {
            throw new EntityNotFoundException("L'identifiant " + id + " n'a pas été trouvé !");
        } else {

            Optional<Artist> ArtistOptional = artistRepository.findById(id);

            model.put("artist", ArtistOptional.get());

            return "detailArtist";
        }
    }


    @GetMapping(value = "")
    public String listesArtist(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "name") String sortProperty,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            final ModelMap Liste) {

            PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(sortDirection), sortProperty);
            Page<Artist> pageArtist = artistRepository.findAll(pageRequest);

            Liste.put("artists", pageArtist);
            Liste.put("pageNumber", page + 1);
            Liste.put("previousPage", page - 1);
            Liste.put("currentPage", page);
            Liste.put("nextPage", page + 1);
            Liste.put("start", page * size + pageArtist.getNumberOfElements());
            Liste.put("end", (page + 1) * size);


            return "listeArtists";
        }

    @GetMapping(value = "", params = "name")
    public String searchArtistsbyName(@RequestParam(required = true) String name, final ModelMap model) {
        Artist Nartist = artistRepository.findByName(name);
        if (!artistRepository.existsByName(name)) {
            throw new EntityNotFoundException("Le nom de l'artiste " + name + " n'a pas été trouvé !");
        } else {
            model.put("artist", Nartist);
            return "detailArtist";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView createOrSaveArtist(Artist artist) throws ConflictException {
        if (artistRepository.existsByName(artist.getName()) == true) {
            throw new ConflictException("Le nom de l'artiste " + artist.getName() + "  est deja utilisé");
        } else if (artist.getName() == "") {
            throw new IllegalArgumentException("Le champs ajouté est vide");
        } else {
            return saveArtist(artist);
        }
    }

    private RedirectView saveArtist(Artist artist) {
        artist = artistRepository.save(artist);
        return new RedirectView("/thym/artists/" + artist.getId());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/new/artist")
    public String newArtist(final ModelMap model) {

        model.put("artist", new Artist());

        return "detailArtist";

    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public RedirectView deleteArtist(@PathVariable Long id) {
        if (!artistRepository.existsById(id)) {
            throw new EntityNotFoundException("L'employé d'identifiant " + id + " n'a pas été trouvé !");
        } else {

            artistRepository.deleteById(id);

            return new RedirectView("/thym/artists?page=0&size=10&sortDirection=ASC&sortProperty=name");
        }
    }



}

//@Controller

//@RequestMapping(value = "/thymeleaf")

//public class ThymeleafController {

// toute les @request ici auront dabord Thymeleaf/le-reste-de-la-route

//}