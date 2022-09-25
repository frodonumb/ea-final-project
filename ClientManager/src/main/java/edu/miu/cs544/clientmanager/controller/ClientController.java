package edu.miu.cs544.clientmanager.controller;

import edu.miu.cs544.clientmanager.dto.ClientDto;
import edu.miu.cs544.clientmanager.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clients")
public class ClientController {
    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAll() {
        return ResponseEntity.ok().body(clientService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> get(@PathVariable UUID id) {
        return ResponseEntity.ok().body(clientService.get(id));
    }

    @PostMapping
    public ResponseEntity<ClientDto> create(@RequestBody ClientDto clientDto) {
        return ResponseEntity.ok().body(clientService.create(clientDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> update(@PathVariable UUID id, @RequestBody ClientDto clientDto) {
        return ResponseEntity.ok().body(clientService.update(id, clientDto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        clientService.delete(id);
    }

}
