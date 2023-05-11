package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MageRepositoryTest {
    MageRepository mageRepository;

    @BeforeEach
    void init() {
        mageRepository = new MageRepository();
    }

    @Test
    void Should_ReturnEmptyOptional_When_RecordIsNotInRepository() {
        Optional<Mage> result = mageRepository.find("dzban");

        assertThat(result).isEmpty();
    }

    @Test
    void Should_ReturnOptionalWithValue_When_FindingProperObject() {
        /**
         * próba pobrania istniejącego obiektu zwraca obiekt Optional z zawartością,
         */
        Mage mage = new Mage("mirek", 15);
        mageRepository.save(mage);
        Optional<Mage> result = mageRepository.find("mirek");

        assertThat(result).hasValue(mage);
        assertThat(result.get().getName()).isEqualTo("mirek");
        assertThat(result.get().getLevel()).isEqualTo(15);
    }
    @Test
    void testFind() {
        assertFalse((new MageRepository()).find("Name").isPresent());
    }

    @Test
    void Should_NotBeEmpty_When_FindingProperObject() {
        mageRepository.save(new Mage("mirek", 15));
        Optional<Mage> result = mageRepository.find("mirek");

        assertThat(result).isNotEmpty();
    }

    @Test
    void testDelete_GENERATED() {
        assertThrows(IllegalArgumentException.class, () -> (new MageRepository()).delete("Name"));
    }

    @Test
    void testSave_GENERATED() {
        MageRepository mageRepository = new MageRepository();
        mageRepository.save(new Mage("Name", 1));
        assertEquals(1, mageRepository.getCollection().size());
    }


    @Test
    void Should_ThrowIllegalArgumentException_When_DeletingNotExistingObject() {
        //próba usunięcia nieistniejącego obiektu powoduje IllegalArgumentException,
        assertThrows(IllegalArgumentException.class, () -> mageRepository.delete("paadsad"));

    }

    @Test
    void Should_ThrowIllegalArgumentException_When_SavingSameKey() {
        //         próba zapisania obiektu, którego klucz główny już znajduje się w repozytorium powoduje IllegalArgumentException.

        mageRepository.save(new Mage("marek", 18));

        assertThrows(IllegalArgumentException.class, () -> mageRepository.save(new Mage("marek", 14)));

    }
}