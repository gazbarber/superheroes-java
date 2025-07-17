package com.buildcircle.superheroes.characters;


public record Character(
        String name,
        double score,
        CharacterType type,
        String weakness) {

}