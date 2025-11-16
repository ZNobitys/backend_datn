package org.example.components.controller;

import org.example.components.entity.Components;
import org.example.components.request.ComponentsRequest;
import org.example.components.service.ComponentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/components")
public class ComponentsController {

    @Autowired
    private ComponentsService componentsService;

    @PostMapping("/create")
    public ResponseEntity<?> createComponets(@RequestBody ComponentsRequest componentsRequest) {
        componentsService.createComponents(componentsRequest);
        return ResponseEntity.ok(componentsRequest);
    }

    @GetMapping("/getall")
    public List<Components> getAllComponents() {
        return componentsService.getAllComponents();
    }

    @GetMapping("/get/{componentsId}")
    Components getComponentById(@PathVariable("componentsId") Integer componentId) {
        return componentsService.getComponentsById(componentId);
    }

    @PutMapping("/update/{componentsId}")
    Components updateComponents(@RequestBody ComponentsRequest componentsRequest, @PathVariable("componentsId") Integer componentsId) {
        return componentsService.updateComponents(componentsId, componentsRequest);
    }

    @DeleteMapping("delete/{componentsId}")
    String deleteComponents(@PathVariable("componentsId") Integer componentsId) {
        componentsService.deleteComponents(componentsId);
        return "Đã xóa thành công";
    }


}
