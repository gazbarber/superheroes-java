package com.buildcircle.superheroes;

import com.buildcircle.superheroes.battle.BattleController;
import com.buildcircle.superheroes.battle.BattleService;
import com.buildcircle.superheroes.battle.InvalidBattleException;
import com.buildcircle.superheroes.characters.Character;
import com.buildcircle.superheroes.characters.CharactersProvider;
import com.buildcircle.superheroes.characters.CharactersResponse;
import com.buildcircle.superheroes.characters.InvalidCharacterException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static com.buildcircle.superheroes.characters.CharacterType.hero;
import static com.buildcircle.superheroes.characters.CharacterType.villain;

@ExtendWith(MockitoExtension.class)
class BattleUnitTests {

    @Mock
    static CharactersProvider mockCharacterProvider;

    static BattleService battleService;
    BattleController battleController;


    @Test
    void whenBatmanBattlesJokerWeaknessVillainBatmanLoses() throws IOException, InterruptedException {

        Character[] responses = new Character[2];
        responses[0] = new Character("Batman",8.3, hero, "Joker");
        responses[1] = new Character("Joker",8.2, villain, null);

        CharactersResponse charactersResponse = new CharactersResponse(responses);
        Mockito.when(mockCharacterProvider.getCharacters()).thenReturn(charactersResponse);
        battleService = new BattleService(mockCharacterProvider);
        battleController = new BattleController(battleService);

        //Given
        String hero = responses[0].name();
        String villain = responses[1].name();


        //When
        Character actual = battleController.battle(hero,villain);

        //Then
        Assert.assertEquals(responses[1], actual);
    }

    @Test
    void whenSupermanBattlesJokerWeaknessVillainSupermanWins() throws IOException, InterruptedException {

        Character[] responses = new Character[2];
        responses[0] = new Character("Superman",9.3, hero, "Lex Luthor");
        responses[1] = new Character("Lex Luthor",8.2, villain, null);

        CharactersResponse charactersResponse = new CharactersResponse(responses);
        Mockito.when(mockCharacterProvider.getCharacters()).thenReturn(charactersResponse);
        battleService = new BattleService(mockCharacterProvider);
        battleController = new BattleController(battleService);

        //Given
        String hero = responses[0].name();
        String villain = responses[1].name();

        //When
        Character actual = battleController.battle(hero,villain);

        //Then
        Assert.assertEquals(responses[0], actual);
    }

    @Test
    void whenTwoOfTheSameTypeBattleResultsInBadRequest() throws IOException, InterruptedException {

        Character[] responses = new Character[4];
        responses[0] = new Character("Batman",8.3, hero, "Joker");
        responses[1] = new Character("Superman",9.2, hero, "Lex");
        responses[2] = new Character("Joker",9.2, villain, null);
        responses[3] = new Character("Lex",9.2, villain, null);

        CharactersResponse charactersResponse = new CharactersResponse(responses);
        Mockito.when(mockCharacterProvider.getCharacters()).thenReturn(charactersResponse);
        battleService = new BattleService(mockCharacterProvider);
        battleController = new BattleController(battleService);


        //Given
        String hero1 = "Batman";
        String hero2 = "Superman";
        String villain1 = "Joker";
        String villain2 = "Lex";
        //When
        Assert.assertThrows(InvalidBattleException.class, () -> battleController.battle(hero1, hero2));
        Assert.assertThrows(InvalidBattleException.class, () -> battleController.battle(villain1 , villain2));
    }
    @Test
    void whenUnknownVillainResutlsInBadRequest() throws IOException, InterruptedException {

        Character[] responses = new Character[2];
        responses[0] = new Character("Batman", 8.3, hero, "Joker");
        responses[1] = new Character("Superman", 9.2, hero, "Lex");

        CharactersResponse charactersResponse = new CharactersResponse(responses);
        Mockito.when(mockCharacterProvider.getCharacters()).thenReturn(charactersResponse);
        battleService = new BattleService(mockCharacterProvider);
        battleController = new BattleController(battleService);

        String hero = "Batman";
        String uknownVillain = "Scarecrow";

        Assert.assertThrows(InvalidCharacterException.class, () -> battleController.battle(hero , uknownVillain));
    }
}
