package com.dbms.scs.service;

import com.dbms.scs.domain.Bills;
import com.dbms.scs.domain.Vendors;
import com.dbms.scs.model.BillsDTO;
import com.dbms.scs.repos.BillsRepository;
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
public class BillsService {

    private final BillsRepository billsRepository;
    private final VendorsRepository vendorsRepository;

    public BillsService(final BillsRepository billsRepository,
            final VendorsRepository vendorsRepository) {
        this.billsRepository = billsRepository;
        this.vendorsRepository = vendorsRepository;
    }

    public List<BillsDTO> findAll() {
        return billsRepository.findAll(Sort.by("billID"))
                .stream()
                .map(bills -> mapToDTO(bills, new BillsDTO()))
                .collect(Collectors.toList());
    }

    public BillsDTO get(final Integer billID) {
        return billsRepository.findById(billID)
                .map(bills -> mapToDTO(bills, new BillsDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final BillsDTO billsDTO) {
        final Bills bills = new Bills();
        mapToEntity(billsDTO, bills);
        return billsRepository.save(bills).getBillID();
    }

    public void update(final Integer billID, final BillsDTO billsDTO) {
        final Bills bills = billsRepository.findById(billID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(billsDTO, bills);
        billsRepository.save(bills);
    }

    public void delete(final Integer billID) {
        billsRepository.deleteById(billID);
    }

    private BillsDTO mapToDTO(final Bills bills, final BillsDTO billsDTO) {
        billsDTO.setBillID(bills.getBillID());
        billsDTO.setDateOfBilling(bills.getDateOfBilling());
        billsDTO.setPurpose(bills.getPurpose());
        billsDTO.setStatus(bills.getStatus());
        billsDTO.setVendorbilledby(bills.getVendorbilledby() == null ? null : bills.getVendorbilledby().getVendorID());
        return billsDTO;
    }

    private Bills mapToEntity(final BillsDTO billsDTO, final Bills bills) {
        bills.setDateOfBilling(billsDTO.getDateOfBilling());
        bills.setPurpose(billsDTO.getPurpose());
        bills.setStatus(billsDTO.getStatus());
        final Vendors vendorbilledby = billsDTO.getVendorbilledby() == null ? null : vendorsRepository.findById(billsDTO.getVendorbilledby())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "vendorbilledby not found"));
        bills.setVendorbilledby(vendorbilledby);
        return bills;
    }

    @Transactional
    public String getReferencedWarning(final Integer billID) {
        final Bills bills = billsRepository.findById(billID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!bills.getMembersresponsblebillsSCSMemberss().isEmpty()) {
            return WebUtils.getMessage("bills.sCSMembers.manyToOne.referenced", bills.getMembersresponsblebillsSCSMemberss().iterator().next().getMemberId());
        }
        return null;
    }

}
