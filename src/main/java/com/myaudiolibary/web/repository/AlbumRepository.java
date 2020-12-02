package com.myaudiolibary.web.repository;

import com.myaudiolibary.web.model.Album;
import com.myaudiolibary.web.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

    @Repository
    public interface AlbumRepository extends JpaRepository<Album, Long> {

        boolean existsByTitle(String title);

        boolean existsByArtistAndTitle(Artist artist, String title);
    }
