package com.qualityplus.pets.persistance.data;

import com.qualityplus.pets.api.pet.Pets;
import com.qualityplus.pets.base.pet.Pet;
import eu.okaeri.persistence.document.Document;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public final class PetsData extends Document {
    private Map<String, Integer> level = new HashMap<>();
    private Map<String, Double> xp = new HashMap<>();

    public void fillIfEmpty(){
        Pets.values().stream().map(Pet::getId).forEach(pet -> level.putIfAbsent(pet, 0));
        Pets.values().stream().map(Pet::getId).forEach(pet -> xp.putIfAbsent(pet, 0D));
    }
}
