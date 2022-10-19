package com.dbms.scs.service;

import com.dbms.scs.domain.Vendors;
import com.dbms.scs.model.VendorsDTO;
import com.dbms.scs.repos.VendorsRepository;
import com.dbms.scs.util.WebUtils;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class VendorsService {

    private final VendorsRepository vendorsRepository;

    public VendorsService(final VendorsRepository vendorsRepository) {
        this.vendorsRepository = vendorsRepository;
    }

    public List<VendorsDTO> findAll() {
        return vendorsRepository.findAll(Sort.by("vendorID"))
                .stream()
                .map(vendors -> mapToDTO(vendors, new VendorsDTO()))
                .collect(Collectors.toList());
    }

    public VendorsDTO get(final Integer vendorID) {
        return vendorsRepository.findById(vendorID)
                .map(vendors -> mapToDTO(vendors, new VendorsDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final VendorsDTO vendorsDTO) {
        final Vendors vendors = new Vendors();
        mapToEntity(vendorsDTO, vendors);
        return vendorsRepository.save(vendors).getVendorID();
    }

    public void update(final Integer vendorID, final VendorsDTO vendorsDTO) {
        final Vendors vendors = vendorsRepository.findById(vendorID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(vendorsDTO, vendors);
        vendorsRepository.save(vendors);
    }

    public void delete(final Integer vendorID) {
        vendorsRepository.deleteById(vendorID);
    }

    private VendorsDTO mapToDTO(final Vendors vendors, final VendorsDTO vendorsDTO) {
        vendorsDTO.setVendorID(vendors.getVendorID());
        vendorsDTO.setVendorName(vendors.getVendorName());
        vendorsDTO.setAddress(vendors.getAddress());
        vendorsDTO.setContactNo(vendors.getContactNo());
        vendorsDTO.setItem(vendors.getItem());
        return vendorsDTO;
    }

    private Vendors mapToEntity(final VendorsDTO vendorsDTO, final Vendors vendors) {
        vendors.setVendorName(vendorsDTO.getVendorName());
        vendors.setAddress(vendorsDTO.getAddress());
        vendors.setContactNo(vendorsDTO.getContactNo());
        vendors.setItem(vendorsDTO.getItem());
        return vendors;
    }

    @Transactional
    public String getReferencedWarning(final Integer vendorID) {
        final Vendors vendors = vendorsRepository.findById(vendorID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!vendors.getVendorbilledbyBillss().isEmpty()) {
            return WebUtils.getMessage("vendors.bills.oneToMany.referenced", vendors.getVendorbilledbyBillss().iterator().next().getBillID());
        }
        return null;
    }

}
