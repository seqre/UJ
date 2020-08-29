package uj.jwzp2019.hellospring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uj.jwzp2019.hellospring.model.Planet;
import uj.jwzp2019.hellospring.service.PlanetService;

@RestController
public class PlanetController {

    private final PlanetService planetService;

    @Autowired
    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @RequestMapping("/planet")
    public Planet getPlanetById(@RequestParam(value="id", defaultValue="1") int id) {
        return planetService.getPlanetById(id);
    }

    @RequestMapping("/planet/smallest")
    public Planet getSmallestPlanetInRange(@RequestParam(value="fromId", defaultValue="1") int fromId,
                                           @RequestParam(value="toId", defaultValue="3") int toId) {
        return planetService.getSmallestPlanetInRange(fromId, toId);
    }

}
