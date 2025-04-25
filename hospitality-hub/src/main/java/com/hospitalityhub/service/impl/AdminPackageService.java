package com.hospitalityhub.service.impl;

import com.hospitalityhub.dto.AdminPackageDTO;
import com.hospitalityhub.entity.AdminPackage;
import com.hospitalityhub.repository.AdminPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Boolean.TRUE;

@Service
public class AdminPackageService {
    @Autowired
    private AdminPackageRepository adminPackageRepository;

    public void createPackageByAdmin(AdminPackageDTO dto) {
        AdminPackage adminPackage = new AdminPackage();
        adminPackage.setPackageName(dto.getPackageName());
        adminPackage.setPackagePrice(dto.getPackagePrice());
        adminPackage.setDescription(dto.getDescription());
        adminPackage.setTestType(dto.getTestType());
        adminPackageRepository.save(adminPackage);
    }

    public List<AdminPackageDTO> adminPackageDTOList() {
        List<AdminPackage> all = adminPackageRepository.findAll();
        return all.stream()
                .map(adminPackage -> {
                    AdminPackageDTO adminPackageDTO = new AdminPackageDTO();
                    adminPackageDTO.setPackageId(adminPackage.getPackageId());
                    adminPackageDTO.setPackageName(adminPackage.getPackageName());
                    adminPackageDTO.setPackagePrice(adminPackage.getPackagePrice());
                    adminPackageDTO.setDescription(adminPackage.getDescription());
                    adminPackageDTO.setTestType(adminPackage.getTestType());
                    return adminPackageDTO;
                })
                .collect(Collectors.toList());
    }

    public void deletePackage(@RequestParam Integer packageId) {
        Optional<AdminPackage> adminPackage = adminPackageRepository.findById(packageId);
        if (adminPackage.isPresent()) {
            adminPackageRepository.deleteById(packageId);
        }
    }
}