package com.hospitalityhub.service.impl;

import com.hospitalityhub.dto.AdminPackageDTO;
import com.hospitalityhub.entity.AdminPackage;
import com.hospitalityhub.repository.AdminPackageRepository;
import org.hibernate.dialect.pagination.LimitOffsetLimitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
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
        adminPackage.setStatus(FALSE);
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

    public void deletePackage(Integer packageId) {
        Optional<AdminPackage> adminPackage = adminPackageRepository.findById(packageId);
        if (adminPackage.isPresent()) {
            adminPackageRepository.deleteById(packageId);
        }
    }

    public void approvePackageByUser(Integer packageId){
        if (packageId!=null){
            Optional<AdminPackage> optionalAdminPackage = adminPackageRepository.findById(packageId);
            optionalAdminPackage.ifPresent(adminPackage -> adminPackage.setStatus(TRUE));
            optionalAdminPackage.ifPresent(adminPackage -> adminPackageRepository.save(adminPackage));
        }
    }
    public List<AdminPackageDTO> approvedPackageList(){
        List<AdminPackage> packageList = adminPackageRepository.findAll();
        return packageList.stream().filter(AdminPackage::isStatus).map(adminPackage -> {
            AdminPackageDTO adminPackageDTO = new AdminPackageDTO();
            adminPackageDTO.setPackageId(adminPackage.getPackageId());
            adminPackageDTO.setPackagePrice(adminPackage.getPackagePrice());
            adminPackageDTO.setPackageName(adminPackage.getPackageName());
            adminPackageDTO.setTestType(adminPackage.getTestType());
            adminPackageDTO.setDescription(adminPackage.getDescription());
           return adminPackageDTO;
        }).collect(Collectors.toList());

    }
}