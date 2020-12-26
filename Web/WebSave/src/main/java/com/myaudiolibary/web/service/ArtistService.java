package com.myaudiolibary.web.service;

import com.myaudiolibary.web.model.Album;
import com.myaudiolibary.web.model.Artist;
import com.myaudiolibary.web.repository.AlbumRepository;
import com.myaudiolibary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    public void deleteAlbum(Long idArtist, Long idAlbum) {
        Optional<Artist> a = artistRepository.findById(idArtist);

        Optional<Album> al = albumRepository.findById(idAlbum);


        Artist artist = a.get();
        Album album = al.get();


        artist.getAlbums().remove(album);
        artistRepository.save(artist);

        album.setArtist(null);
        albumRepository.save(album);
    }
}
