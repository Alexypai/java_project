package com.myaudiolibary.web.model;

import javax.persistence.*;

@Entity
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="AlbumId")
    private Long id;

    @Column(name="Title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "ArtistId", nullable = false)
    private Artist artist;

    public Album() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}

