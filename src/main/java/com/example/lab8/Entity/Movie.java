package com.example.lab8.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @Column(name = "id_movie", nullable = false)
    private Integer id;

    @ManyToOne( optional = false)
    @JoinColumn(name = "id_user", nullable = false)
    private User idUser;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "overview", nullable = false, length = 45)
    private String overview;

    @Column(name = "popularidad", nullable = false, length = 45)
    private String popularidad;

    @Column(name = "fecha_lanzamiento", nullable = false, length = 45)
    private String fechaLanzamiento;

}