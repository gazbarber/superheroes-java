package com.buildcircle.superheroes.battle;

import com.buildcircle.superheroes.characters.Character;
import com.buildcircle.superheroes.characters.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BattleService {

    private final Map<String, Character> characterMap;

    public BattleService( @Autowired CharactersProvider charactersProvider) throws IOException, InterruptedException {
        CharactersResponse characters = charactersProvider.getCharacters();
        characterMap = Arrays.stream(characters.getItems()).collect(Collectors.toMap(Character::name, c -> c));
    }

    private Character getCharacter(String name){
        if(characterMap.containsKey(name)) {
            return characterMap.get(name);
        }
        throw new InvalidCharacterException("No character with name:"+name);
    }

    public Character battle(String hero, String villain){
        Character heroChar = getCharacter(hero);
        Character villainChar = getCharacter(villain);
        if(heroChar.type()!= CharacterType.hero || villainChar.type()!=CharacterType.villain){
            throw new InvalidBattleException("Hero's must fight Villains!\nPow, zap!\nProvided with 'hero':"+hero+" and 'villain':"+villain);
        }

        return battle(heroChar, villainChar);
    }

    private Character battle(Character hero, Character villain){

        double heroRealScore = scoreAfterWeakness(hero, villain);
        double villainRealScore = scoreAfterWeakness(villain, hero);

        if(heroRealScore > villainRealScore)
        {
            return hero;
        }
        return villain;
    }

    private double scoreAfterWeakness(Character char1, Character char2) {
        if(null!=char1.weakness() && char1.weakness().equals(char2.name())){
            return char1.score() -1.0;
        }
        return char1.score();
    }

}