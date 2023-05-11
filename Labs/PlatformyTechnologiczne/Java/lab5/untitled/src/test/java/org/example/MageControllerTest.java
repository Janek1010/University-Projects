package org.example;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

class MageControllerTest {
    @Test
    void testDeleteNotFound() {
        // próba usunięcia nieistniejącego obiektu powoduje zwrócenie obiektu String o wartości done,
        MageRepository mageRepository = mock(MageRepository.class);
        MageController mageController = new MageController(mageRepository);
        doThrow(IllegalArgumentException.class).when(mageRepository).delete(anyString());

        assertThat(mageController.delete("bleh")).isEqualTo("Not Found");
        verify(mageRepository, times(1)).delete("bleh");

    }
    @Test
    void testDeleteDone() {
        // próba usunięcia istniejącego obiektu powoduje zwrócenie obiektu String o wartości done,
        MageRepository mageRepository = mock(MageRepository.class);
        MageController mageController = new MageController(mageRepository);

        assertThat(mageController.delete("bleh")).isEqualTo("Done");
        verify(mageRepository, times(1)).delete("bleh");

    }
    @Test
    void testFindFound(){
        // próba pobrania nieistniejącego obiektu powoduje zwrócenie obiektu String o wartości not found,
        MageRepository mageRepository = mock(MageRepository.class);
        MageController mageController = new MageController(mageRepository);
        Mage mage = new Mage("bartek", 10);

        when(mageRepository.find(mage.getName())).thenReturn(Optional.of(mage));

        assertThat(mageController.find("bartek")).isEqualTo(mage.toString());
        verify(mageRepository, times(1)).find("bartek");

    }
    @Test
    void testFindNotFound(){
        // próba pobrania nieistniejącego obiektu powoduje zwrócenie obiektu String o wartości not found,
        MageRepository mageRepository = mock(MageRepository.class);
        MageController mageController = new MageController(mageRepository);
        Mage mage = new Mage("bartek", 10);

        when(mageRepository.find("mirek")).thenReturn(Optional.empty());

        assertThat(mageController.find("mirek")).isEqualTo("Not Found");
        verify(mageRepository, times(1)).find("mirek");

    }

    @Test
    void testSaveProper(){
//        próba zapisania nowego obiektu skutkuje wywołaniem metody z serwisu z poprawnym parametrem i zwróceniem obiektu String o wartości done,
        MageRepository mageRepository = mock(MageRepository.class);
        MageController mageController = new MageController(mageRepository);
        Mage mage = new Mage("mietek", 132);
        assertThat(mageController.save(mage.getName(), String.valueOf(mage.getLevel()))).isEqualTo("Done");

        verify(mageRepository, times(1)).save(mage);

    }

    @Test
    void testSaveBadRequest(){
//        próba zapisania nowego obiektu, którego klucz główny znajduje się już w repozytorium powoduje zwrócenie obiektu String o wartości bad request.
        MageRepository mageRepository = mock(MageRepository.class);
        MageController mageController = new MageController(mageRepository);
        Mage mage = new Mage("jurek", 121);
        doThrow(IllegalArgumentException.class).when(mageRepository).save(mage);

        assertThat(mageController.save(mage.getName(), String.valueOf(mage.getLevel()))).isEqualTo("Bad Request");
        verify(mageRepository, times(1)).save(mage);

    }

}

