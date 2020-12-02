package com.myaudiolibary.web.controllerThym;

import com.myaudiolibary.web.model.Artist;
import com.myaudiolibary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping(value = "thym/artists")
public class AudioController {

    @Autowired
    private ArtistRepository artistRepository;


    @GetMapping(value = "/{id}")
    public String id(final ModelMap model, @PathVariable Long id) {

        Optional<Artist> ArtistOptional = artistRepository.findById(id);

        model.put("artist", ArtistOptional.get());

        return "detailArtist";
    }



    @GetMapping( value ="")
    public String listesArtist(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "ASC") String sortProperty,
            @RequestParam(defaultValue = "name") String sortDirection,
            final ModelMap Liste){

        PageRequest pageRequest = PageRequest.of(page,size, Sort.Direction.valueOf(sortDirection),sortProperty);
        Page<Artist> pageArtist = artistRepository.findAll(pageRequest);

        Liste.put("artists", pageArtist);
        Liste.put("pageNumber",page + 1);
        Liste.put("previousPage",page - 1);
        Liste.put("currentPage",page);
        Liste.put("nextPage",page + 1);
        Liste.put("start",page * size + pageArtist.getNumberOfElements());
        Liste.put("end",(page + 1) * size);


        return "listeArtists";
    }

    @GetMapping(value = "", params = "name")
    public String searchArtistsbyName(@RequestParam(required = true) String name, final ModelMap model){
        Artist Nartist = artistRepository.findByName(name);
        //Ici il faudrait gérer l'erreur 404 !
        model.put("artist", Nartist);
        return "detailArtist";
    }

}

//@Controller

//@RequestMapping(value = "/thymeleaf")

//public class ThymeleafController {

// toute les @request ici auront dabord Thymeleaf/le-reste-de-la-route

//}