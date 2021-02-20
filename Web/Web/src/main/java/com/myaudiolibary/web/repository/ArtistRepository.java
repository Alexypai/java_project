package com.myaudiolibary.web.repository;

import com.myaudiolibary.web.model.Album;
import com.myaudiolibary.web.model.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {


    Artist findByName(String Name);

    Page<Artist> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    boolean existsByName(String name);


    void deleteByAlbums(Album album);
}