package com.buildcircle.superheroes.battle;

import com.buildcircle.superheroes.characters.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BattleController {

    private final BattleService battleService;

    public BattleController(@Autowired BattleService battleService)
    {
        this.battleService = battleService;
    }

    @GetMapping(value = "/battle", produces = "application/json")
    public Character battle(@RequestParam(value = "hero")  final String hero,
                            @RequestParam(value = "villain") final String villain) {
            return battleService.battle(hero, villain);
    }

}
