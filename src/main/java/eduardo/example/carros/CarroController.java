package eduardo.example.carros;

import ch.qos.logback.core.model.INamedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carros")
class CarroController {
    private List<Carro> carros = new ArrayList<>();

    public CarroController() {
        carros.addAll(List.of(
                new Carro(1, "HRV", "HONDA", "BRANCO"),
                new Carro(2, "X4", "BMW", "AZUL"),
                new Carro(3, "A3", "AUDI", "AZUL"),
                new Carro(4, "JETTA", "VOLKSWAGEM", "PRETA")
        ));
    }

    @GetMapping
    Iterable<Carro> getCarros() {
        return carros;
    }

    @GetMapping("/{id}")
    Optional<Carro> getCarro(@PathVariable Integer id) {
        for (Carro carro : carros) {
            if (carro.getId().equals(id)) {
                return Optional.of(carro);
            }
        }
        return Optional.empty();
    }

    @PostMapping
    Carro postCarro(@RequestBody Carro carro) {
        carros.add(carro);
        return carro;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carro> putCarro(@PathVariable Integer id, @RequestBody Carro carro) {
        int i = 0;
        for (Carro c : carros) {
            if (c.getId().equals(id)) {
                i = carros.indexOf(c);
                carros.set(i, carro);
            }

        }
        return (i == -1) ?
                new ResponseEntity<>(postCarro(carro), HttpStatus.CREATED) :
                new ResponseEntity<>(carro, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    void deleteCarro(@PathVariable int id) {
        carros.removeIf(carro -> carro.getId().equals(id));
    }


}
