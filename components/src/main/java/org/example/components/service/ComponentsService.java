package org.example.components.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.components.entity.Components;
import org.example.components.reponsitory.ComponentsReponsitory;
import org.example.components.request.ComponentsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComponentsService {

    @Autowired
    private ComponentsReponsitory componentsReponsitory;

    public Components createComponents(ComponentsRequest componentsRequest) {
        Components components = new Components();
        components.setComponentsName(componentsRequest.getComponentsName());
        components.setQuantity(componentsRequest.getQuantity());
        components.setPrice(componentsRequest.getPrice());
        components.setComponentsDescription(componentsRequest.getComponentsDescription());
        components.setImg(componentsRequest.getImg());
        return componentsReponsitory.save(components);
    }

    public Components getComponentsById(Integer componentsId) {
        return componentsReponsitory.findByComponentsId(componentsId)
                .orElseThrow(() -> new EntityNotFoundException("Sản phẩm không tồn tại"));
    }

    public void deleteComponents(Integer componentsId) {
        Components components = getComponentsById(componentsId);
        componentsReponsitory.delete(components);

    }

    public Components updateComponents(Integer componentsId, ComponentsRequest componentsRequest) {
        Components components = getComponentsById(componentsId);
        components.setComponentsName(componentsRequest.getComponentsName());
        components.setQuantity(componentsRequest.getQuantity());
        components.setPrice(componentsRequest.getPrice());
        components.setComponentsDescription(componentsRequest.getComponentsDescription());
        components.setImg(componentsRequest.getImg());
        return componentsReponsitory.save(components);
    }

    public Components getComponentsByName(String componentsName) {
        return componentsReponsitory.findByComponentsName(componentsName)
                .orElseThrow(() -> new RuntimeException("Không có sản phẩm nào phù hợp với tên" + componentsName));
    }

    public List<Components> getAllComponents() {
        return componentsReponsitory.findAll();
    }

}
